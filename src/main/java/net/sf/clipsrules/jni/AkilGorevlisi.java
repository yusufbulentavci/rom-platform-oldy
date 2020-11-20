package net.sf.clipsrules.jni;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bilgidoku.rom.haber.NodeTalkMethod;
import com.bilgidoku.rom.haber.TalkResult;
import com.bilgidoku.rom.ilk.file.FromResource;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.run.KosuGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;


public class AkilGorevlisi extends GorevliDir {
	public static final int NO = 50;

	static {
		try {
//			loadDirect("libclips.so");
			loadDirect("/home/rompg/rom/phase8/java/rom/src/main/clips/build/cmake.debug.linux.x86_64/libclips.so");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static AkilGorevlisi tek() {
		if (tek == null) {
			synchronized (AkilGorevlisi.class) {
				if (tek == null) {
//					try {loadDirect("/home/rompg/backup/rom-22/phase8/java/rom/src/main/clips/build/cmake.debug.linux.x86_64/libclips.so");
////						loadSo("/clips/libCLIPSJNI.so");
//						loadDirect("/home/rompg/backup/rom-22/phase8/java/rom/src/main/clips/build/cmake.debug.linux.x86_64/libclips.so");
//					} catch (IOException e) {
//						throw new RuntimeException("Cant load clips library", e);
//					}
					tek = new AkilGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static AkilGorevlisi tek;
	private final AkilErisim clips;
	private Set<String> modules=new HashSet<String>();
	
	private AkilGorevlisi() {
		super("Akill", NO);
		this.clips = Environment.gen();
		this.clips.addRouter(new AkilClipsRouter());
		this.clips.addUserFunction("insert", "v", 2, 1000, null, new AkilInsertFunction(clips));
		this.clips.addUserFunction("delete", "v", 2, 1000, null, new AkilDeleteFunction(clips));
		this.clips.addUserFunction("update", "v", 2, 1000, null, new AkilUpdateFunction(clips));
		this.clips.addUserFunction("soyle", "v", 3, 1000, null, new AkilSoyledi());
		this.clips.addUserFunction("sembol", "l", 1, 1, null, new AkilSembolNo());
	}
	
	protected void kur() throws KnownError {
	};
	
	public static void main(String[] args) throws KnownError {
		AkilGorevlisi.tek().reset();
		AkilGorevlisi.tek().loadModule("hello");
		AkilGorevlisi.tek().run();
	}
	
	public synchronized void loadModule(String module) throws KnownError {
		if(modules.contains(module)) {
			return;
		}
		try {
			String buf = FromResource.loadString("clips/"+module+".clp");
			add(buf);
			modules.add(module);
		} catch (IOException e) {
			throw new KnownError("AkilGorevlisi can not load module:", e);
		}
	}
	

	public void add(String cmd) throws KnownError {
		try {
			System.out.println("!!!!!!!!!!!!!!!!!! CLIPS add:" + cmd);
			clips.loadFromString(cmd);
//			System.out.println("!!!!!!!!!!!!!!!!!! CLIPS DONE");
		} catch (CLIPSLoadException e) {
			throw new KnownError("Error in clips cmd:" + cmd, e);
		}
	}

	public void addFact(String cmd) throws KnownError {
		try {
			System.out.println("!!!!!!!!!!!!!!!!!! CLIPS addFact:" + cmd);
			clips.assertString(cmd);
//			System.out.println("!!!!!!!!!!!!!!!!!! CLIPS DONE");
		} catch (CLIPSException e) {
			throw new KnownError("Error in clips cmd:" + cmd, e);
		}
	}

	public void deleteById(String table, String id) throws KnownError {
//		StringBuilder sb=new StringBuilder();
//		sb.append("(do-for-fact ");
//		sb.append("((");
//		sb.append("?row ");
//		sb.append(table);
//		sb.append(")) ");
//		sb.append("(eq ?row:id ");
//		sb.append(id);
//		sb.append(") ");
//		sb.append("printout t ?row:id crlf)");
//		try {
//			clips.eval(sb.toString());
//		} catch (CLIPSException e) {
//			e.printStackTrace();
//			throw new KnownError("deleteById:"+id, e);
//		}
		try {
			List<FactAddressValue> ret = clips.findAllFacts("menu");
			for (FactAddressValue factAddressValue : ret) {
				System.out.println(factAddressValue);
			}

			FactAddressValue fv = clips.findFact("?f", table, "(eq ?f:id " + id + ")");
			if (fv == null)
				return;
			clips.retainFact(fv);
			System.out.println("--->Fact deleted: table:" + table + " id:" + id);
		} catch (CLIPSException e) {
			e.printStackTrace();
			throw new KnownError("deleteById:" + id, e);
		}

	}

	public void modifyById(String modify) throws KnownError {

		try {
			clips.eval(modify);
		} catch (CLIPSException e) {
			e.printStackTrace();
			throw new KnownError("modifyById:" + modify, e);
		}

	}

	public void reset() throws KnownError {
		try {
			System.out.println("!!!!!!!!!!!!!!!!!! CLIPS Reset");
			clips.reset();
		} catch (CLIPSException e) {
			throw new KnownError("Error in clips reset:", e);
		}
	}

	public void run() throws KnownError {
		try {
			System.out.println("!!!!!!!!!!!!!!!!!! CLIPS RUN");
			clips.run();
		} catch (CLIPSException e) {
			e.printStackTrace();
		}
			
//		KosuGorevlisi.tek().exec(new Runnable() {
//			public void run() {
//				while (true) {
//					try {
//						System.out.println("!!!!!!!!!!!!!!!!!! CLIPS RUN");
//						clips.run();
//					} catch (CLIPSException e) {
//						e.printStackTrace();
////					throw new KnownError("Error in clips run:", e);
//					}
//					try {
//						Thread.sleep(4000);
//					} catch (Exception e) {
//					}
//				}
//			}
//		});

	}

//	public static void main(String[] args) throws IOException {
////		loadSo("/clips/libCLIPSJNI.so");
//		loadDirect("/home/rompg/backup/rom-22/phase8/java/rom/src/main/clips/build/cmake.debug.linux.x86_64/libclips.so");
//		System.out.println("shared library loaded");
//		 try
//	        {
//			AkilErisim clips = new AkilErisim();
//
//			 clips.loadFromResource("/gelis.clp");
//	         
//	         clips.run();
//	        }
//	      catch (Exception e)
//	        {
//	         e.printStackTrace();
//	         System.exit(1);
//	        }
//	}
	private static void loadSo(String name) throws IOException {
		InputStream in = AkilGorevlisi.class.getResourceAsStream(name);
		byte[] buffer = new byte[1024];
		int read = -1;
		File temp = File.createTempFile(name, "");
		FileOutputStream fos = new FileOutputStream(temp);
		while ((read = in.read(buffer)) != -1) {
			fos.write(buffer, 0, read);
		}
		fos.close();
		in.close();

		System.load(temp.getAbsolutePath());
	}

	private static void loadDirect(String diredName) throws IOException {

		System.load(diredName);
	}

	public Boolean kisiSoyledi(int hostId, Integer cint, String eylem, JSONObject ext) throws KnownError {
		String s=AkilJsonFactTransform.jsonToFact(hostId, cint, eylem, ext);
		addFact(s);
		return true;
	}
//
//	private Boolean buildAddFact(String template, Object... strings) throws KnownError {
//		StringBuilder sb=new StringBuilder();
//		sb.append("( ");
//		sb.append(template);
//		for(int i=0; i<strings.length;i=i+2) {
//			sb.append("(");
//			sb.append(strings[i]);
//			sb.append(" ");
//			sb.append(strings[i+1]);
//			sb.append(") ");
//		}
//		sb.append(")");
//		addFact(sb.toString());
//		return true;
//	}
	
	// echo '{"from":"", "c":"akil.console","ani":true, "template":"watch", "inner":{}}' > /dev/udp/127.0.0.1/9876
	@NodeTalkMethod(cmd = "akil.console")
	public TalkResult console(JSONObject jo) throws KnownError {
		try {
			String template=jo.getString("template");
			if(template.equals("watch")) {
				System.out.println("All facts:");
				List<FactInstance> fs=clips.getFactList();
				for (FactInstance f : fs) {
					System.out.println(f.toString());
				}
				return TalkResult.success;
			}
			JSONObject inner=jo.getJSONObject("inner");
			String s=AkilJsonFactTransform.consoleJsonToFact(template, inner);
			addFact(s);
		} catch (JSONException e) {
			throw new KnownError("akil.console", e);
		}
		return TalkResult.success;
	}
}
