package com.bilgidoku.rom.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.gorevli.GorevliYonetimi;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.run.Threads.ThreadUse.ThreadRunImpl;
import com.bilgidoku.rom.run.timer.EveryDay;
import com.bilgidoku.rom.run.timer.EveryHalfHour;
import com.bilgidoku.rom.run.timer.EveryHour;
import com.bilgidoku.rom.run.timer.EveryMinute;
import com.bilgidoku.rom.run.timer.EveryMonth;
import com.bilgidoku.rom.run.timer.EveryQuaterHour;
import com.bilgidoku.rom.run.timer.EverySixHours;
import com.bilgidoku.rom.run.timer.EveryTwelveHours;
import com.bilgidoku.rom.run.timer.EveryTwoHours;
import com.bilgidoku.rom.run.timer.EveryYear;
import com.bilgidoku.rom.run.timer.Timer;
import com.bilgidoku.rom.run.timer.ZamanlayiciYardim;

public class KosuGorevlisi extends GorevliDir implements ZamanlayiciYardim {
	public static final int NO=3;

	public static KosuGorevlisi tek() {
		if(tek==null) {
			synchronized (KosuGorevlisi.class) {
				if(tek==null) {
					tek=new KosuGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static KosuGorevlisi tek;
	private KosuGorevlisi() {
		super("Kos", NO);
	}
	
	private static final int MILI_TO_MIN = 1000 * 60;
	private static final String DELETED = "_deleted";

	private ExecutorService exec;
	private ScheduledThreadPoolExecutor scheduler;
	private Threads threads;
	private Timer timer;
	private Set<String> extraPaths = new HashSet<>();
	private Map<String, String> envo = new HashMap<>();
	/**
	 * Java.exec has a problem
	 * It cant search changed PATH envo variable for the command
	 * Executables should be called with full path
	 * So we overwrite first exec parameter if it is in executables map 
	 */
	private Map<String, String> executables=new HashMap<>();

	
	
	public void kur() {
		threads = new Threads();
		ThreadFactory tf = threadGroup("rom.schedular");
		scheduler = new ScheduledThreadPoolExecutor(2, tf);
		exec = Executors.newFixedThreadPool(3, threadGroup("rom.exec"));

		timer = new Timer(this);

		scheduler.schedule(this.timer, 1, TimeUnit.MINUTES);
	}
	
	/**
	 * Repeated without exact delay. Delay time is related to previous execution
	 * times
	 */
	
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
		// fixedRate.more();
		return scheduler.scheduleAtFixedRate(command, initialDelay, period, unit);
	}

	// private static Astate fixedDelay=mc.c("fixed-delay");
	/**
	 * Repeated with exact delay time
	 */
	
	public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
		// fixedDelay.more();
		return scheduler.scheduleWithFixedDelay(command, initialDelay, delay, unit);
	}

	
	public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
		return scheduler.schedule(command, delay, unit);
	}

	
	public ScheduledFuture<?> scheduleAlign(Runnable command, long period, TimeUnit unit, TimeUnit alignTo) {
		if (unit != TimeUnit.MINUTES && alignTo != TimeUnit.HOURS) {
			throw new RuntimeException("Not implemented");
		}
		long nowInMin = System.currentTimeMillis() / MILI_TO_MIN;
		long alignDif = nowInMin % 60;

		return scheduleWithFixedDelay(command, (alignDif == 0 ? 60 : alignDif), period, TimeUnit.MINUTES);
	}

	
	public ExecutorService executor(int n, String name) {
		ThreadFactory tf = threads.group(name);

		return Executors.newFixedThreadPool(n, tf);
	}

	
	public Executor executorCached(String name) {
		ThreadFactory tf = threads.group(name);
		return Executors.newCachedThreadPool(tf);
	}

	
	public ThreadRunImpl thread(Runnable r, String name) {
		return threads.thread(r, name);
	}

	
	public ThreadFactory threadGroup(String name) {
		return threads.group(name);
	}

	
	public void exec(Runnable run) {
		exec.execute(run);
	}

	
	public void waitMin(EveryMinute e) {
		timer.waitMin(e);
	}
	
	public void waitMinRemove(EveryMinute e) {
		timer.waitMinRemove(e);
	}

	
	public void waitHour(EveryHour e) {
		timer.waitHour(e);
	}

	
	public void waitDay(EveryDay e) {
		timer.waitDay(e);
	}

	
	public void waitMonth(EveryMonth e) {
		timer.waitMonth(e);
	}

	
	public void waitYear(EveryYear e) {
		timer.waitYear(e);
	}

	
	public void selfDescribe(JSONObject jo) {
		JSONObject je = new JSONObject();
		je.safePut("down", exec.isShutdown());
		je.safePut("term", exec.isTerminated());

		JSONObject js = new JSONObject();
		js.safePut("pool", scheduler.getPoolSize());
		js.safePut("active", scheduler.getActiveCount());
		js.safePut("task", scheduler.getTaskCount());
		js.safePut("active", scheduler.getActiveCount());
		js.safePut("completed", scheduler.getCompletedTaskCount());

		JSONObject jt = new JSONObject();
		jt.safePut("timer", timer.describe());

		jo.safePut("exec", je);
		jo.safePut("schedular", js);
		jo.safePut("timer", jt);

	}
	
	public void runInWorker(Runnable work) {
		exec(work);
	}

	
	public boolean exec(String dir, boolean inShell, boolean failSafe, boolean reportError, StringBuilder out,
			StringBuilder err, String... cs) throws KnownError {
		List<String> cmdsHasPath=new ArrayList<>();
		boolean get=true;
		for (String string : cs) {
			if(get){
				get=false;
				String found=executables.get(string);
				if(found!=null){
					cmdsHasPath.add(found);
					continue;
				}
			}
			cmdsHasPath.add(string);
			
		}
		
		ProcessBuilder pb;
		if (inShell) {
			List<String> cmds = new ArrayList<>();
			cmds.add("bash");
			for (String string : cmdsHasPath) {
				cmds.add(string);
			}
			pb = new ProcessBuilder(cmds);
		} else {
			pb = new ProcessBuilder(cmdsHasPath).directory(new File("/tmp"));
		}

		if (dir == null)
			dir = "/tmp";
		pb.directory(new File(dir));

		Map<String, String> env = pb.environment();
		for (Entry<String, String> e : envo.entrySet()) {
			if (e.getValue() == DELETED) {
				env.remove(e.getKey());
			} else {
				env.put(e.getKey(), e.getValue());
			}
		}
		if (extraPaths.size() > 0) {
			String path = env.get("PATH");
			if (path == null)
				path = "";
			for (String p : extraPaths) {
				path += ":" + p;
			}
			env.put("PATH", path);
		}

		Process p = null;
		try {
			p = pb.start();
			p.waitFor();

			int exitval = p.exitValue();
			if (out != null) {
				streamToString(p.getInputStream(), out);
			}

			if (err != null) {
				streamToString(p.getErrorStream(), err);
			}

			if (exitval != 0 && !failSafe) {
				if (reportError) {

					if (out == null) {
						out = new StringBuilder();
						streamToString(p.getInputStream(), out);
					}

					if (err == null) {
						err = new StringBuilder();
						streamToString(p.getInputStream(), out);
					}

					StringBuilder sb = new StringBuilder("Cmd:commands\n");
					sb.append("Exit value:");
					sb.append(exitval);
					sb.append("\n");

					sb.append("Out:\n");
					sb.append(out);
					sb.append("Out:\n");
					streamToString(p.getErrorStream(), sb);
					sb.append(err);
					Sistem.errln(sb.toString());
				}
			}

		} catch (IOException | InterruptedException ex) {
			throw new KnownError("Cant execute:" + cmdsHasPath, ex);
		} finally {
			if (p != null) {
				try {
					p.destroy();
				} catch (Exception e) {
				}
			}

		}
		return true;
	}

	private void streamToString(InputStream st, StringBuilder sb) throws IOException {
		BufferedReader bi = new BufferedReader(new InputStreamReader(st));
		do {
			String o = bi.readLine();
			if (o == null)
				break;
			sb.append(o);
			sb.append("\n");
		} while (true);

	}

	
	public void waitQuaterHour(EveryQuaterHour e) {
		timer.waitQuaterHour(e);
	}

	
	public void waitHalfHour(EveryHalfHour e) {
		timer.waitHalfHour(e);
	}

	
	public void waitTwoHours(EveryTwoHours e) {
		timer.waitTwoHours(e);
	}

	
	public void waitSixHours(EverySixHours e) {
		timer.waitSixHours(e);
	}

	
	public void waitTwelveHours(EveryTwelveHours e) {
		timer.waitTwelveHours(e);
	}

	
	public void setEnvo(String name, String value) {
		if (value == null) {
			envo.put(name, DELETED);
		} else {
			envo.put(name, value);
		}

	}

	
	public void setPathDir(String dir, boolean remove, String[] execs) {
		if (remove) {
			extraPaths.remove(dir);
			for (String string : execs) {
				executables.remove(string);
			}
		} else {
			extraPaths.add(dir);
			for (String string : execs) {
				executables.put(string, dir+"/"+string);
				File f=new File(dir+"/"+string);
				f.setExecutable(true);
			}
			
		}
	}

	
}
