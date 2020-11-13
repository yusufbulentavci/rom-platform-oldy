package com.bilgidoku.rom.pg;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;

public class ExtractDirectorFromClasspath {

//	private static final String THE_DIRECTORY = "pg/";
//	private static final String THE_DIRECTORY = "com/bilgidoku/rom/service/osgi";
	
//	private static final String WRITE_DIRECTORY = "/tmp/to/";

//	public static void main(String argv[]) throws IOException {
//
//		new ExtractDirectorFromClasspath(THE_DIRECTORY, WRITE_DIRECTORY);
//	}

	/**
	 * constructor
	 * 
	 * @param sourceDirectory
	 *            directory (in a jar on the classpath) to extract
	 * @param inputDirectory
	 *            the location to extract to
	 * @throws IOException
	 *             if an IO exception occurs
	 */
//	public ExtractDirectorFromClasspath(String sourceDirectory, String writeDirectory) throws IOException {
//
//		// make sure write directory exists
//		new File(writeDirectory).mkdirs();
//		// extract into the write directory
////		extract(sourceDirectory, writeDirectory, "pg");
//		extract(sourceDirectory, writeDirectory);
//		
//	}

	
//	private static final String THE_DIRECTORY = "pg/";
//	private static final String THE_DIRECTORY = "com/bilgidoku/rom/service/osgi";
	
//	private static final String WRITE_DIRECTORY = "/tmp/to/";
	public static void extract(String sourceDirectory, String writeDirectory) throws IOException {

		Enumeration<URL> roots = ClassLoader.getSystemResources(sourceDirectory);
		URL dirURL = null;
		while (roots.hasMoreElements()) {
			if(dirURL!=null)
				throw new IOException("Multiple pg found in classpath");
			dirURL = roots.nextElement();
		}
		if (dirURL == null) {
			throw new IOException("Dir not found in classpath");
		}
//		System.out.println(dirURL.getPath());

//		final URL dirURL = getClass().getResource(sourceDirectory);
		

		File wd=new File(writeDirectory);
		if(wd.exists()){
			FileUtils.deleteDirectory(wd);
		}
		FileUtils.forceMkdir(wd);
		
		if (dirURL.getProtocol().equals("jar")) {
			final String path = sourceDirectory;
			final JarURLConnection jarConnection = (JarURLConnection) dirURL.openConnection();
			System.out.println("jarConnection is " + jarConnection);

			final ZipFile jar = jarConnection.getJarFile();

			final Enumeration<? extends ZipEntry> entries = jar.entries(); // gives
																			// ALL
																			// entries
																			// in
																			// jar

			while (entries.hasMoreElements()) {
				final ZipEntry entry = entries.nextElement();
				final String name = entry.getName();
				// System.out.println( name );
				if (!name.startsWith(path)) {
					// entry in wrong subdir -- don't copy
					continue;
				}
				final String entryTail = name.substring(path.length());

				final File f = new File(writeDirectory + File.separator + entryTail);
				if (entry.isDirectory()) {
					// if its a directory, create it
					final boolean bMade = f.mkdir();
					System.out.println((bMade ? "  creating " : "  unable to create ") + name);
				} else {
					System.out.println("  writing  " + name);
					final InputStream is = jar.getInputStream(entry);
					final OutputStream os = new BufferedOutputStream(new FileOutputStream(f));
					final byte buffer[] = new byte[4096];
					int readCount;
					// write contents of 'is' to 'os'
					while ((readCount = is.read(buffer)) > 0) {
						os.write(buffer, 0, readCount);
					}
					os.close();
					is.close();
				}
			}

		} else if (dirURL.getProtocol().equals("file")) {
//			System.out.println(dirURL.getPath());
//			final String path = sourceDirectory.substring(1);
			
			FileUtils.copyDirectory(new File(dirURL.getPath()), new File(writeDirectory));
		} else {
			throw new IOException("Unexpected protocol:"+dirURL);
		}
	}

}