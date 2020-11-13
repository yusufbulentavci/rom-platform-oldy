package com.bilgidoku.rom.epostatemel.javam.mail.internet;

import java.io.File;
import java.io.InputStream;

import com.bilgidoku.rom.shared.err.KnownError;



public interface AttachDeal {
	String make(InputStream is, String fileName) throws KnownError;

	File get(String dbfs) throws KnownError;

}
