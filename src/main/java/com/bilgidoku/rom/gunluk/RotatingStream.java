package com.bilgidoku.rom.gunluk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.Genel;
import com.bilgidoku.rom.run.timer.EveryMinute;
import com.bilgidoku.rom.shared.err.KnownError;

public class RotatingStream implements EveryMinute {
	private static final byte[] TOKENBEGIN = "->|".getBytes();
	private static final byte[] TOKENEND = "|<-".getBytes();
	protected final File dir;
	private File dirActivity;

	protected final String fn;
	protected final File f;

	PrintStackTrace stackTrace = new PrintStackTrace();
	PrintStream writer;

	private boolean konsola = true;

	protected final String fnActivity;
	protected final File fActivity;
	PrintStream writerActivity;

	public RotatingStream(File dir, String fn, File dirActivity, String fnActivity) throws KnownError {
		this.dir = dir;
		this.fn = fn;
		this.f = new File(dir, fn);

		this.dirActivity = dirActivity;

		this.fnActivity = fnActivity;
		this.fActivity = new File(dirActivity, fnActivity);

		Calendar c = Calendar.getInstance();
		rotate(true, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
	}

	public JSONObject describe() {
		JSONObject jo = new JSONObject();
		try {
			BasicFileAttributes attrs = Files.readAttributes(fActivity.toPath(), BasicFileAttributes.class);
			jo.safePut("creation", attrs.creationTime().toString());
			jo.safePut("size", attrs.size());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jo;
	}

	private void startLog() {
		log(LogCmds.logstart, true, 0, new JSONObject());
	}

	private synchronized void writeToFile(JSONObject it) {
		try {
			if (it == null) {
				System.err.println("WriteToFile:null");
				return;
			}
			String str = it.prettyPrint();
			if (str == null) {
				System.err.println("WriteToFile:prettyprint returned null");
				str = it.toString();
			}
			if (konsola) {
				System.out.println(str);
			} else {
				writer.write(TOKENBEGIN);
				writer.write(str.getBytes());
				writer.write(TOKENEND);
				writer.println();
			}
		} catch (IOException e) {
		}
	}

	public void rotate(boolean force, int year, int month, int day) throws KnownError {
		checkResetFile(force, year, month, day);
		checkResetActivity(force, year, month, day);
	}

	void checkResetActivity(boolean force, int year, int month, int day) throws KnownError {
		try {
			// if current message file not exist no need to rotate
			// create writer and return
			if (!fActivity.exists()) {
				resetActivity();
				return;
			}

			BasicFileAttributes attrs = Files.readAttributes(fActivity.toPath(), BasicFileAttributes.class);
			FileTime creationTime = attrs.creationTime();

			if (!force) {
				// if sameday with file
				if (Genel.sameDay(creationTime.toMillis(), day, month, year)) {
					// if writer already created just return
					if (writerActivity != null)
						return;
					// start of logging reset and return
					resetActivity();
					return;
				}
			}

			// if not same day or forced

			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(creationTime.toMillis());
			int oyear = c.get(Calendar.YEAR);
			int omonth = c.get(Calendar.MONTH);
			int oday = c.get(Calendar.DAY_OF_MONTH);

			// format file name as rom-2014-12-29.log and rename
			File to = new File(dirActivity,
					fnActivity + "-" + oyear + "-" + omonth + "-" + oday + "-" + System.currentTimeMillis() + ".log");
			fActivity.renameTo(to);

			resetActivity();
		} catch (IOException e) {
			throw new KnownError("Rotating activity file couldnt started:" + fActivity.getPath(), e);
		}
	}

	void checkResetFile(boolean force, int year, int month, int day) throws KnownError {
		try {
			// if current message file not exist no need to rotate
			// create writer and return
			if (!f.exists()) {
				resetLog();
				return;
			}

			BasicFileAttributes attrs = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
			FileTime creationTime = attrs.creationTime();

			if (!force) {
				// if sameday with file
				if (Genel.sameDay(creationTime.toMillis(), day, month, year)) {
					// if writer already created just return
					if (writer != null)
						return;
					// start of logging reset and return
					resetLog();
					return;
				}
			}

			// if not same day or forced

			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(creationTime.toMillis());
			int oyear = c.get(Calendar.YEAR);
			int omonth = c.get(Calendar.MONTH);
			int oday = c.get(Calendar.DAY_OF_MONTH);

			// format file name as rom-2014-12-29.log and rename
			File to = new File(dir,
					fn + "-" + oyear + "-" + omonth + "-" + oday + "-" + System.currentTimeMillis() + ".log");
			f.renameTo(to);

			resetLog();
		} catch (IOException e) {
			throw new KnownError("Rotating file couldnt started:" + f.getPath(), e);
		}
	}

	private synchronized void resetLog() throws FileNotFoundException {
		if (writer != null) {
			IOUtils.closeQuietly(writer);
		}

		writer = new PrintStream(new FileOutputStream(f, true));
		stackTrace.reset();
		startLog();
	}

	private synchronized void resetActivity() throws FileNotFoundException {
		if (writerActivity != null) {
			IOUtils.closeQuietly(writerActivity);
		}

		writerActivity = new PrintStream(new FileOutputStream(fActivity, true));
	}

	public void log(String cmd, boolean isGood, int priority, Object... args) {
		JSONObject jo = new JSONObject();

		for (int i = 0; i < args.length; i += 2) {
			String key = (String) args[i];
			Object o = args[i + 1];
			jo.putOpt(key, o);
		}

		log(cmd, isGood, priority, jo);
		// one.talker.broadcastWatch(jo);

	}

	public void log(String cmd, boolean isGood, int priority, JSONObject s) {

		s.putOpt(LogParams.cmd, cmd);
		s.putOpt(LogParams.isgood, isGood);
		s.putOpt(LogParams.priority, priority);
		s.putOpt(LogParams.time, System.currentTimeMillis());
		s.putOpt(LogParams.thread, Thread.currentThread().getName());
		s.putOpt(LogParams.where, getWhere());

		writeToFile(s);
	}

	private String getWhere() {
		StackTraceElement[] stt = Thread.currentThread().getStackTrace();
		for (int i = 1; i < stt.length; i++) {
			StackTraceElement s = stt[i];
			String cn = s.getClassName();
			if (cn.equals("com.bilgidoku.rom.min.Sistem") || cn.equals("com.bilgidoku.rom.gunluk.RotatingStream")
					|| cn.equals("com.bilgidoku.rom.gunluk.LogServiceImpl"))
				continue;

			String cln = cn;
			if (cln.startsWith("com.bilgidoku.rom.")) {
				cln = cln.substring("com.bilgidoku.rom.".length());
			}
			return cln + "->" + s.getMethodName() + ":" + s.getLineNumber();
		}

		return "unknown->unknown:0";
	}

	public List<JSONObject> getTokensOfCurrentFile() {
		List<JSONObject> ret = new ArrayList<JSONObject>();
		try (FileInputStream fin = new FileInputStream(this.f);) {
			Integer c1 = null;
			Integer c2 = null;
			Integer c0 = null;
			StringBuilder sb = new StringBuilder();
			while (true) {

				if (c2 != null && sb != null) {
					sb.append((char) c2.intValue());
				}
				c2 = c1;
				c1 = c0;
				c0 = fin.read();

				if (c0 == -1) {
					return ret;
				}
				if (c0 == TOKENBEGIN[2] && (c1 != null && c1 == TOKENBEGIN[1]) && (c2 != null && c2 == TOKENBEGIN[0])) {
					sb = new StringBuilder();
					c0 = null;
					c1 = null;
					c2 = null;
				} else if (c0 == TOKENEND[2] && (c1 != null && c1 == TOKENEND[1])
						&& (c2 != null && c2 == TOKENEND[0])) {
					if (sb == null) {
						System.err.println("Rotating stream; Not started stream ended");
					} else {
						JSONObject jo;
						try {
							jo = new JSONObject(sb.toString());
							ret.add(jo);
						} catch (JSONException e) {
							System.err.println("Rotating stream; Not a json object:" + sb.toString());
						}
						sb = null;
					}
					c0 = null;
					c1 = null;
					c2 = null;
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Rotating stream; file not found");
		} catch (IOException e) {
			System.err.println("Rotating stream; io exception");
			e.printStackTrace();
			;
		}
		return ret;

	}

	public void log(Throwable t, String extra) {

		IStackTrace sto = stackTrace.gotOne(t);

		StringBuilder sb = new StringBuilder();
		String msg = t.getMessage();
		if (msg != null)
			sb.append(msg);

		if (extra != null) {
			sb.append(" extra:");
			sb.append(extra);
		}

		if (t instanceof KnownError) {
			sb.append(" KE extra:");
			sb.append(((KnownError) t).getExtra());
		}

		sto.print(sb);
		List<IStackTrace> added = stackTrace.getAdded();
		if (added != null) {
			for (IStackTrace ad : added) {
				log(LogCmds.newstacktrace, false, 5, ad.toJson());
			}
		}

		JSONObject jo = new JSONObject();
//		try {
		jo.putOpt(LogParams.id, sto.id);
//		} catch (KnownError e) {
//			e.printStackTrace();
//		}
		log(LogCmds.stacktrace, false, 5, jo);
	}

	@Override
	public void everyMinute(int year, int month, int day, int hour, int minute) {
		try {
			rotate(false, year, month, day);
		} catch (KnownError e) {
			System.err.println("RotatingStream failed:" + e.getSummary());
			e.printStackTrace();
		}
	}

	public synchronized void request(String rid, String ip, Integer hostId, String userAddr, String command,
			String desc) {
		writerActivity.print(System.currentTimeMillis());
		writerActivity.print('\t');
		writerActivity.print(rid);
		writerActivity.print('\t');
		writerActivity.print('i');
		writerActivity.print('\t');
		writerActivity.print(ip);

		writerActivity.print('\t');
		writerActivity.print(hostId);

		writerActivity.print('\t');
		writerActivity.print(userAddr);
		writerActivity.print('\t');
		writerActivity.print(command);
		writerActivity.print('\t');
		writerActivity.println(desc);
	}

	public synchronized void response(String rid, boolean suc, String code) {
		writerActivity.print(System.currentTimeMillis());
		writerActivity.print('\t');
		writerActivity.print(rid);
		writerActivity.print('\t');
		writerActivity.print('o');
		writerActivity.print('\t');
		writerActivity.print(suc);
		writerActivity.print('\t');
		writerActivity.println(code);
	}

	public void requestToQueue(String rid, String ref) {
		writerActivity.print(System.currentTimeMillis());
		writerActivity.print('\t');
		writerActivity.print(rid);
		writerActivity.print('\t');
		writerActivity.print('q');
		writerActivity.print('\t');
		writerActivity.println(ref);
	}

}
