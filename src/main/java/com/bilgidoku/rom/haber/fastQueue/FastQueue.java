package com.bilgidoku.rom.haber.fastQueue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.io.FileUtils;

import com.bilgidoku.rom.haber.CmdInfoProvider;
import com.bilgidoku.rom.haber.TalkUtil;
import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.Playing;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.dbfs.DbfsGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;

/**
 * Rom permanent queue implementation
 * 
 * 
 * @author avci
 * 
 */
public class FastQueue implements Queue {
	
	/*
	 * In memory queue
	 */
	private final BlockingQueue<Cmd> queue = new LinkedBlockingQueue<Cmd>();

	private final File dirGo;

	private volatile int tick = 0;

	private final GoWriter goStream;

	private final DelayQueue min = new DelayQueue(1, false);
	private final DelayQueue min10 = new DelayQueue(10, false);
	private final DelayQueue min45 = new DelayQueue(45, true);
	private final DelayQueue hour2 = new DelayQueue(60 * 2, true);
	private final DelayQueue hour4 = new DelayQueue(60 * 4, true);
	private final DelayQueue hour6 = new DelayQueue(60 * 6, true);

	public static int DELAY_EMAIL = 0;
	private final DelayQueue[] all = { min, min10, min45, hour2, hour4, hour6 };
	private final DelayQueue[][] delayTimes = { { min, min10, min45, hour2, hour4, hour6 } };
	private boolean alive = true;
	private final CmdInfoProvider info;
	
	public JSONObject report(){
		JSONObject ret=new JSONObject();
			int l = queue.size();
			ret.safePut("size", l);
			ret.safePut("tick", tick);
			if(l>0){
				JSONArray top=new JSONArray();
				Cmd pcmd=queue.peek();
				if(pcmd!=null){
					top.put(pcmd.toString());
				}
				ret.safePut("top", top);
			}
		
		
		return ret;
	}

	public FastQueue(CmdInfoProvider info, File dir, int goFileSize) throws KnownError {
		this.info = info;
		dirGo = new File(dir, "go");
		goStream = new GoWriter(dirGo, goFileSize);
	}

	public void start() throws KnownError {
		if (!dirGo.exists())
			dirGo.mkdirs();
		try {
			goStream.start();
		} catch (IOException e) {
			throw new KnownError("FastQueue construction failed/ dir:" + dirGo.getPath());
		}

		try {
			resume();
		} catch (IOException | JSONException e) {
			throw new KnownError("Failed to resume", e);
		}
	}
	
	public void terminate() {
		try{
			close();
		}catch(Exception e){
			Sistem.printStackTrace(e, "Failed to terminate fast queue");
		}
	}

	public void close() {
		alive = false;
		goStream.close();
	}

	private void resume() throws IOException, KnownError, JSONException {
		GoResumer resumer = new GoResumer(dirGo, 2);

		// cmdRef(hostid;dbfs file) -> retrycount
		Map<CmdRef, Integer> inThePool = new HashMap<CmdRef, Integer>();
		Go go;
		while ((go = resumer.next()) != null) {
			if (go.inThePool()) {
				Integer retry = inThePool.get(go);
				if (retry == null) {
					inThePool.put(go, 0);
				} else {
					inThePool.put(go, retry.intValue() + 1);
				}
			} else {
				inThePool.remove(go);
			}
		}

		for (Entry<CmdRef, Integer> it : inThePool.entrySet()) {
			Cmd cmd = loadCmd(it.getKey());
			cmd.retryCount = (byte) it.getValue().intValue();
			if (it.getValue() == 0) {
				if (!cmd.ani()) {
					this.queue.add(cmd);
//					com.bilgidoku.rom.min.Sistem.outln("Adding:" + cmd.toString());
				}
			} else {
				int rp = info.getRetryPattern(cmd.getCmd());
				if (rp < 0 || rp >= delayTimes.length || cmd.retryCount >= delayTimes[rp].length) {
					cmd.report("Fastqueue invalid retryPattern or retryCount ignoring/ ");
					continue;
				}
				DelayQueue q = delayTimes[rp][cmd.retryCount];
				q.add(cmd);
			}
		}
		// this.queue.clear();

	}

	private void wakeUp(Cmd cmdRef) throws KnownError {
		File f = DbfsGorevlisi.tek().get(cmdRef.hostId, cmdRef.file);
		try {
			JSONObject jo = new JSONObject(FileUtils.readFileToString(f));
			cmdRef.wakeUp(jo);
		} catch (JSONException | IOException e) {
			throw new KnownError("Fast queue wake up failed/ hostId:" + cmdRef.hostId + " file:" + cmdRef.file, e);
		}
	}

	private Cmd loadCmd(CmdRef cmdRef) throws KnownError {
		File f = DbfsGorevlisi.tek().get(cmdRef.hostId, cmdRef.file);
		try {
			JSONObject jo = new JSONObject(FileUtils.readFileToString(f));
			return new Cmd(cmdRef.hostId, cmdRef.file, jo);
		} catch (JSONException | IOException e) {
			throw new KnownError("Fast queue load cmd failed/ hostId:" + cmdRef.hostId + " file:" + cmdRef.file, e);
		}
	}

	@Override
	public boolean retry(Cmd cmd) throws KnownError {
//		com.bilgidoku.rom.min.Sistem.outln("Queue retry:" + cmd.toString());
		DelayQueue dq = getNextDelay(cmd);
		if (dq == null) {
			failed(cmd);
			return false;
		}
		addToGo(cmd, Go.FAILEDRETRY);
		dq.add(cmd);
		return true;
	}

	@Override
	public void failed(Cmd cmd) throws KnownError {
//		com.bilgidoku.rom.min.Sistem.outln("Queue failed:" + cmd.toString());
		addToGo(cmd, Go.FAILED);
	}

	@Override
	public void success(Cmd cmd) throws KnownError {
//		com.bilgidoku.rom.min.Sistem.outln("Queue suc:" + cmd.toString());
		addToGo(cmd, Go.SUC);
	}

	private void addToGo(Cmd cmd, int goCode) throws KnownError {

		synchronized (dirGo) {
			try {
				Go go = new Go(Playing.millis(), cmd.file, cmd.hostId, goCode);
				this.goStream.write(go);
			} catch (IOException e) {
				Sistem.printStackTrace(e, "Add to go failed:");
			}
		}
	}

	@Override
	public Cmd pop() {
		while (alive) {
			try {
				Cmd l = null;
				while (l == null){
					l = queue.take();
				}
				return l;
			} catch (InterruptedException e) {
			}
		}
		return null;
	}

	@Override
	public String push(JSONObject jo) throws KnownError {
		int hostId;
		try {
			hostId = TalkUtil.hostId(jo);
		} catch (JSONException e) {
			throw new KnownError("host id not found for cmd:" + jo);
		}
		String file = DbfsGorevlisi.tek().make(hostId, jo.toString(), ".rcmd");
		Cmd cmd = new Cmd(hostId, file, jo);
		queue.add(cmd);
		addToGo(cmd, Go.IN);
		return cmd.ref();
	}

	public DelayQueue getNextDelay(Cmd cmd) throws KnownError {
		byte retryPattern;
		try {
			retryPattern = info.getRetryPattern(cmd.getCmd());
		} catch (JSONException e) {
			throw new KnownError("Cmd of message can not be found:" + cmd, e);
		}
		if (retryPattern < 0) {
			cmd.report("Process results but cmd do not have retry pattern");
			return null;
		}
		if (cmd.retryCount >= delayTimes[retryPattern].length) {
			return null;
		}

		return delayTimes[retryPattern][cmd.retryCount];
	}

	@Override
	public void tick() {
		tick=(getTick() + 1);
		for (DelayQueue it : all) {
			List<Cmd> lst = it.tick(getTick());
			if (lst == null)
				continue;
			for (Cmd cmd : lst) {
				cmd.retry();
				try {
					wakeUp(cmd);
				} catch (KnownError e) {
					Sistem.printStackTrace(e, "Failed to load cmd from repo:" + cmd.toString());
					continue;
				}
				try {
					addToGo(cmd, Go.RETRY);
				} catch (KnownError e) {
					Sistem.printStackTrace(e, "Cmd failed:" + cmd);
				}
				queue.add(cmd);
			}
		}
	}

	public int fidGo() {
		return goStream.fileIndex;
	}
	// private int findRetry(int retryPattern, DelayQueue it) {
	// DelayQueue[] k = delayTimes[retryPattern];
	// for (int i = 0; i < k.length; i++) {
	// if (k[i] == it) {
	// return i;
	// }
	// }
	// com.bilgidoku.rom.gunluk.Sistem.errln("Delay queue findRetry failed:" + retryPattern);
	// return k.length - 1;
	// }

	@Override
	public int size() {
		return queue.size();
	}

	public int getTick() {
		return tick;
	}

	

	

}
