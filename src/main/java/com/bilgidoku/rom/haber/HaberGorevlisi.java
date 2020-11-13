package com.bilgidoku.rom.haber;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.gunluk.GunlukGorevlisi;
import com.bilgidoku.rom.haber.fastQueue.Cmd;
import com.bilgidoku.rom.haber.fastQueue.FastQueue;
import com.bilgidoku.rom.haber.world.Worlder;
import com.bilgidoku.rom.ilk.gorevli.Dir;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.Genel;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.run.KosuGorevlisi;
import com.bilgidoku.rom.run.ThreadRun;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.gorevli.Gorevli;
import com.bilgidoku.rom.shared.gorevli.GorevliIzle;

import io.netty.channel.nio.NioEventLoopGroup;

public class HaberGorevlisi extends GorevliDir implements Runnable, GorevliIzle, MsgStatus, CmdInfoProvider{
	
public static final int NO=23;
	
	public static HaberGorevlisi tek(){
		if(tek==null) {
			synchronized (HaberGorevlisi.class) {
				if(tek==null) {
					tek=new HaberGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static HaberGorevlisi tek;
	private HaberGorevlisi() {
		super("Haber", NO);
		dir = safeDataDir().getPath()+("/queue");
		Dir.existDir(dir);
	}
	

	private static final MC mc = new MC(HaberGorevlisi.class);


	private final Map<String, ServerCmd> cmds = new HashMap<String, ServerCmd>();

	private FastQueue queue;
	private Worlder worlder;
	private UdpServer udpServer;

	private NioEventLoopGroup talkerListen;
	private NioEventLoopGroup talkerChild;
	private NioEventLoopGroup talkerConnect;

	private ThreadRun talkerThread;

	private long watchLocal;
	private final String dir;

	private NioEventLoopGroup talkerUdpServer;

	
	
	public void selfDescribe(JSONObject jo) {
//		jo.safePut("queue", queue.report());
//		jo.safePut("worlder", worlder.report());
	}

	@Override
	protected void kur() throws KnownError {

		this.talkerListen = new NioEventLoopGroup(1, KosuGorevlisi.tek().threadGroup("talker-listen"));
		this.talkerChild = new NioEventLoopGroup(2, KosuGorevlisi.tek().threadGroup("talker-child"));
		this.talkerConnect = new NioEventLoopGroup(1, KosuGorevlisi.tek().threadGroup("talker-connect"));
		this.talkerUdpServer = new NioEventLoopGroup(1, KosuGorevlisi.tek().threadGroup("talker-udpserver"));
		try {
			queue = new FastQueue(this, new File(dir), 5000);
			worlder = new Worlder(this, Genel.getHostName(), talkerListen, talkerChild, talkerConnect);
			udpServer=new UdpServer();
			
			talkerUdpServer.execute(udpServer);
			
		} catch (KnownError e) {
			throw new RuntimeException("Talker can not be constructed", e);
		}
		try {
			queue.start();
			worlder.start();
		} catch (KnownError e) {
			throw new RuntimeException("Talker can not be constructed", e);
		}
		startQueueProcess();
		mc.out("Started TalkService");

	}

	@Override
	protected void bitir(boolean dostca) {
		if (worlder != null)
			worlder.terminate();
		if (queue != null)
			queue.terminate();
		if (this.talkerThread != null)
			this.talkerThread.interrupt();
	}


	public void startQueueProcess() {
		this.talkerThread = KosuGorevlisi.tek().thread(this, "talker-queue");
		this.talkerThread.start();
	}

	

	private TalkResult talking(JSONObject msg) throws KnownError {
		try {
			String cmd = msg.getString("c");
			ServerCmd s = cmds.get(cmd);
			if (s == null) {
				throw new KnownError("Cmd not found:" + cmd).badRequest();
			}
			return s.execute(msg);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | JSONException e) {
			Sistem.printStackTrace(e, msg.toString());
			throw new KnownError(e);
		}

	}

	private void failed(JSONObject msg) throws KnownError {
		try {
			String cmd = msg.getString("c");
			ServerCmd s = cmds.get(cmd);
			if (s == null) {
				throw new KnownError("Cmd not found:" + cmd).badRequest();
			}
			s.err(msg);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | JSONException e) {
			Sistem.printStackTrace(e, msg.toString());
			throw new KnownError(e);
		}
	}

	public void addCommandProcessor(String cmd, ServerCmd sc) {
		cmds.put(cmd, sc);
	}


	public byte getRetryPattern(String cmd) {
		ServerCmd s = cmds.get(cmd);
		return s.getRetryPattern();
	}

	private static final Astate processed = mc.c("processed");
	private static final Astate suc = mc.c("success");
	private static final Astate retry = mc.c("retry");
	private static final Astate fail = mc.c("fail");

	@Override
	public void run() {

		while (mayi()) { // Safety while
			try { // Catch unexpected exception
				while (mayi()) { // Per message queue

					Cmd cmd = queue.pop();
					if (cmd == null) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
						}
						continue;
					}

					JSONObject msg = null;
					try {
						processed.more();
						msg = cmd.getJo();
						if (TalkUtil.isLocal(msg)) {
							TalkResult ret;
							ret = talking(msg);
							if (ret.equals(TalkResult.success)) {
								suc.more();
								queue.success(cmd);
							} else if (ret.equals(TalkResult.retry)) {
								retry.more();
								queue.retry(cmd);
							} else {
								fail.more();
								queue.failed(cmd);
							}
						} else {
							worlder.send(cmd);
						}
					} catch (KnownError e) {
						processed.failed();
						if (msg == null) {
							throw e;
						}
						Sistem.printStackTrace(e, "Dictate cmd failed:" + cmd);
						if (e.isTemporary()) {
							try {
								if (!queue.retry(cmd)) {
									failed(msg);
								}
							} catch (KnownError en) {
								Sistem.printStackTrace(e, "Talker.retry");
							}
						} else {
							try {
								queue.failed(cmd);
								failed(msg);
							} catch (KnownError e1) {
								Sistem.printStackTrace(e1, "Talker.failed");
							}
						}
					}
				}
			} catch (Exception e) {
				Sistem.printStackTrace(e, "Unexpected talker run error(ignoring):");
			}
		}
	}


	public void send(JSONObject jo, String rid) throws KnownError {
		if (!mayi())
			throw new KnownError("System is shutting down");

		boolean ani=TalkUtil.ani(jo);
		
		if(ani){
			talking(jo);
			return;
		}
		
		String ref = queue.push(jo);
		if (rid != null) {
			GunlukGorevlisi.tek().requestToQueue(rid, ref);
		}
	}

	public void tick() {
		queue.tick();
	}


	public void sendFailed(Cmd cmd) {
		try {
			queue.retry(cmd);
		} catch (KnownError e) {
			Sistem.printStackTrace(e);
		}
	}


	public void sendSuc(Cmd cmd) {
		try {
			queue.success(cmd);
		} catch (KnownError e) {
			Sistem.printStackTrace(e);
		}
	}

	private static final Astate newmsg = mc.c("newmsg");


	public void newMsg(JSONObject msg, String rid) {
		try {
			newmsg.more();
			send(msg, rid);
		} catch (KnownError e) {
			newmsg.failed();
			Sistem.printStackTrace(e, "Can not queue world msg:" + msg.toString());
		}
	}

	public void watch(String from, long val) {
		if (from.equals(Genel.getHostName())) {
			this.watchLocal = val;
		} else {
			this.worlder.watch(from, val);
		}
	}

	public void broadcastWatch(JSONObject jo) {
		try {
			long cat;
			try {
				cat = jo.getLong("c");
			} catch (JSONException e) {
				throw new KnownError(jo.toString());
			}

			if ((watchLocal & cat) != 0) {
				talking(new Msg("ws.watch").to(Genel.getHostName()).data(jo).ani().line().msg());
				// sendWatch(jo,Genel.getHostName());
			}
			worlder.sendWatch(jo, cat);
		} catch (KnownError e) {
			Sistem.printStackTrace(e);
		}
	}


	


	
	public List<ServerCmd> canYouTalk(Gorevli c) {
		List<ServerCmd> li = new ArrayList<ServerCmd>();

		for (Method m : c.getClass().getMethods()) {
			NodeTalkMethod a = m.getAnnotation(NodeTalkMethod.class);
			if (a == null)
				continue;
			if (a.err()) {
				continue;
			}
			li.add(new ServerCmd(a.cmd(), c, m, a.retrypattern(), a.alarmlevel()));
		}

		for (Method m : c.getClass().getMethods()) {
			NodeTalkMethod a = m.getAnnotation(NodeTalkMethod.class);
			if (a == null)
				continue;
			if (!a.err()) {
				continue;
			}
			boolean found = false;
			for (ServerCmd serverCmd : li) {
				if (serverCmd.getCmd().equals(a.cmd())) {
					serverCmd.err(m);
					found = true;
					break;
				}
			}
//			if (!found)
//				com.bilgidoku.rom.templating.Sistem.errln("Err method couldnt be set for cmd:" + a.cmd());
		}
		return li;
	}

	@Override
	public void yeniGorevli(Gorevli o) {
		// TODO Auto-generated method stub
		List<ServerCmd> cs = canYouTalk(o);
		if (cs != null) {
			for (ServerCmd serverCmd : cs) {
				this.addCommandProcessor(serverCmd.getCmd(), serverCmd);
			}
		}
		
	}

	


}
