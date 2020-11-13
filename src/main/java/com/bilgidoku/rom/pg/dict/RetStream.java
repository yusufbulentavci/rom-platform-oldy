package com.bilgidoku.rom.pg.dict;

import java.io.File;
import java.io.InputStream;

public class RetStream {
	public RetStream(File f, String string) {
		file=f;
		fileName=string;
	}
	public File file;
	public InputStream stream;
	public String fileName;
}
