package com.bilgidoku.rom.web.db.files;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;

public class Downloader {
	private static final MC mc = new MC(Downloader.class);

	
	private static final Astate _download=mc.c("download");

	public static boolean download(String uri, String file) {
		InputStream instream = null;
		FileOutputStream fos = null;
		_download.more();
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(uri);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				_download.fail(uri,file);
				return false;
			}
			fos = new FileOutputStream(file);
			instream = entity.getContent();
			int l;
			byte[] tmp = new byte[2048];
			while ((l = instream.read(tmp)) != -1) {
				fos.write(tmp, 0, l);
			}
			return true;
		} catch (Exception e) {
			_download.failed(e,uri,file);
			return false;
		} finally {
			if (instream != null)
				try {
					instream.close();
				} catch (Exception e) {
				}
			if (fos != null)
				try {
					fos.close();
				} catch (Exception e) {
				}
		}
	}
}
