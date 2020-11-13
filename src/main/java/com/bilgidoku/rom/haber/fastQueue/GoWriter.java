package com.bilgidoku.rom.haber.fastQueue;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class GoWriter extends RepoFileBase {
	final int maxSize;

	DataOutputStream out;
	int index;
	public int fileIndex;

	public GoWriter(File base, int maxSize) {
		super(base);
		int lst = lastIndex();
		fileIndex=lst+1;
		this.maxSize = maxSize;
	}
	
	public void start() throws FileNotFoundException{
		File f = new File(base, fileIndex + "");
		if (f.exists()) {
			index = (int) f.length();
		}
		out = new DataOutputStream(new FileOutputStream(f, true));
	}

	public synchronized void write(Go go) throws IOException {
		go.write(out);
		index++;
		checkRotate();
	}

	private void checkRotate() throws IOException, FileNotFoundException {
		if (index >= maxSize) {
			out.close();
			++fileIndex;
			index = 0;
			out = new DataOutputStream(new FileOutputStream(new File(base, fileIndex + ""), false));
		}
	}

	

	public void close() {
		IOUtils.closeQuietly(out);
	}
}
