package com.bilgidoku.rom.haber.fastQueue;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.bilgidoku.rom.shared.err.KnownError;

public class GoResumer extends RepoFileBase {

	private boolean end;
	private int readingFileInd = -1;
	private int lastIndex;
	private DataInputStream in;

	GoResumer(File f, int days) throws IOException {
		super(f);
		lastIndex = lastIndex();
		if (lastIndex < 0) {
			end = true;
			return;
		}
		int first = firstIndex();

//		GregorianCalendar c=Playing.calendar();
		readingFileInd=first;
//		long till=c.getTimeInMillis();
//		for (int i = first; i <= lastIndex; i++) {
//			try (DataInputStream input = new DataInputStream(new FileInputStream(new File(f, i + "")));) {
//				long l = input.readLong();
////				if (l < till) {
//					readingFileInd = i;
////					break;
////				}
//			}catch(EOFException e){
//			}
//		}
		if (readingFileInd == Integer.MAX_VALUE)
			end = true;
	}

	public Go next() throws KnownError {
		if (end)
			return null;

		try {
			if (!checkReading())
				return null;
			Go gone = Go.read(in);
			return gone;
		} catch (EOFException e) {
			readingFileInd++;
			IOUtils.closeQuietly(in);
			in=null;
//			com.bilgidoku.rom.gunluk.Sistem.errln(readingFileInd+"-"+lastIndex);
			return next();
		} catch (IOException e) {
			throw new KnownError("Error in resuming/ file:" + base.getPath() + " readingFileInd:" + readingFileInd
					+ " lastIndex:"+lastIndex, e);
		}
	}

	public int size() throws KnownError{
		int ret=0;
		while(next()!=null){
			ret++;
		}
		return ret;
	}
	
	private boolean checkReading() throws FileNotFoundException {
		if (this.in == null) {
			if (readingFileInd > lastIndex) {
				end = true;
				return false;
			}
			this.in = new DataInputStream(new FileInputStream(new File(base, readingFileInd + "")));
		}
		return true;
	}

}
