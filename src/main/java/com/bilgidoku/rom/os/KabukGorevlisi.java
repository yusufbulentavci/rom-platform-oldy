package com.bilgidoku.rom.os;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.shared.err.KnownError;

public class KabukGorevlisi extends GorevliDir {
	public static final int NO = 52;

	public static KabukGorevlisi tek() {
		if (tek == null) {
			synchronized (KabukGorevlisi.class) {
				if (tek == null) {
					tek = new KabukGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static KabukGorevlisi tek;

	private KabukGorevlisi() {
		super("Kabuk", NO);
	}

	static String LOC = "/home/rompg/rom/phase8/java/rom/src/main/resources/kabuk/";

	public String exec(String... params) throws KnownError {
		params[0] = LOC + params[0];

		ProcessBuilder pb = new ProcessBuilder(params);
		pb.environment().put("LD_LIBRARY_PATH",
				"/home/rompg/rom/packs/geos/lib:/home/rompg/rom/packs/pg/lib:/home/rompg/rom/packs/proj/lib:/home/rompg/rom/packs/sfcgal/lib:/home/rompg/rom/packs/sfcgal/lib64:/home/rompg/rom/packs/gdal/lib");
//		System.out.println(pb.environment().entrySet().toString());
		String output;
		try {
			Process p = pb.start();
			DataInputStream is = new DataInputStream(p.getInputStream());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 400; i++) {
				if (is.available() > 0) {
					String o = is.readUTF();
					if (o != null) {
						sb.append(o);
						System.out.println(o);
					}
				}

				System.out.println(".");
				if (!p.isAlive())
					break;
				p.waitFor(1, TimeUnit.SECONDS);
			}
			output = sb.toString();
			if (p.exitValue() == 0) {
				return output;
			}
			if (output == null || output.length() == 0) {
				output = IOUtils.toString(p.getErrorStream(), Charset.defaultCharset());
			}
			throw new KnownError("Process exec failed:" + StringUtils.join(params, " ") + " Output:" + output);

		} catch (IOException | InterruptedException e) {
			throw new KnownError("Process exec failed:" + StringUtils.join(params, " "), e);

		}
	}

	public String command(String... params) throws KnownError {
		params[0] = params[0];

		ProcessBuilder pb = new ProcessBuilder(params);
		pb.environment().put("LD_LIBRARY_PATH",
				"/home/rompg/rom/packs/geos/lib:/home/rompg/rom/packs/pg/lib:/home/rompg/rom/packs/proj/lib:/home/rompg/rom/packs/sfcgal/lib:/home/rompg/rom/packs/sfcgal/lib64:/home/rompg/rom/packs/gdal/lib");
//		System.out.println(pb.environment().entrySet().toString());
		String output;
		try {
			Process p = pb.start();
			DataInputStream is = new DataInputStream(p.getInputStream());
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < 400; i++) {
				if(is.available()>0) {
					int ch;
					while((ch = is.read()) != -1) {
					    sb.append((char)ch);
					    System.out.print((char)ch);
					}
				}

				System.out.println(".");
				if (!p.isAlive())
					break;
				p.waitFor(1, TimeUnit.SECONDS);
			}
			output = sb.toString();

			if (p.exitValue() == 0) {
				return output;
			}
			if (output == null || output.length() == 0) {
				output = IOUtils.toString(p.getErrorStream(), Charset.defaultCharset());
			}
			throw new KnownError("Process exec failed:" + StringUtils.join(params, " ") + " Output:" + output);

		} catch (IOException | InterruptedException e) {
			throw new KnownError("Process exec failed:" + StringUtils.join(params, " "), e);

		}
	}

	public String cdCommand(String dir, String... params) throws KnownError {
		params[0] = params[0];

		ProcessBuilder pb = new ProcessBuilder(params);
		pb.directory(new File(dir));
		pb.environment().put("LD_LIBRARY_PATH",
				"/home/rompg/rom/packs/geos/lib:/home/rompg/rom/packs/pg/lib:/home/rompg/rom/packs/proj/lib:/home/rompg/rom/packs/sfcgal/lib:/home/rompg/rom/packs/sfcgal/lib64:/home/rompg/rom/packs/gdal/lib");
//		System.out.println(pb.environment().entrySet().toString());
		String output;
		try {
			Process p = pb.start();
			InputStream is=p.getInputStream();
			StringBuilder sb=new StringBuilder();
			
			for(int i=0; i<400; i++) {
				if(is.available()>0) {
					int ch;
					while((ch = is.read()) != -1) {
					    sb.append((char)ch);
					    System.out.print((char)ch);
					}
				}
					
				System.out.println(".");
				if(!p.isAlive())
					break;
				p.waitFor(1, TimeUnit.SECONDS);
			}
			output=sb.toString();
			if (p.exitValue() == 0) {
				return output;
			}
			if (output == null || output.length() == 0) {
				output = IOUtils.toString(p.getErrorStream(), Charset.defaultCharset());
			}
			throw new KnownError("Process exec failed:" + StringUtils.join(params, " ") + " Output:" + output);

		} catch (IOException | InterruptedException e) {
			throw new KnownError("Process exec failed:" + StringUtils.join(params, " "), e);

		}
	}

	public static void main(String[] args) throws KnownError {
		System.out.println(KabukGorevlisi.tek().exec("pg-exec.sh", "rom", "select true"));
	}

//	public static String exec(String... params) throws KnownError {
//	ProcessBuilder pb = new ProcessBuilder(params);
//	String output;
//	try {
//		Process p = pb.start();
//		p.waitFor(10, TimeUnit.SECONDS);
//		output = IOUtils.toString(p.getInputStream(), Charset.defaultCharset());
//		if (p.exitValue() == 0) {
//			return output;
//		}
//		if(output==null  || output.length()==0){
//			output=IOUtils.toString(p.getErrorStream(), Charset.defaultCharset());
//		}
//		throw new KnownError("Process exec failed:" + StringUtils.join(params, " ") + " Output:" + output);
//
//	} catch (IOException | InterruptedException e) {
//		throw new KnownError("Process exec failed:" + StringUtils.join(params, " "), e);
//
//	}
//}
}
