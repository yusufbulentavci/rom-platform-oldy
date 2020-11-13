package com.bilgidoku.rom.haber.fastQueue;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Date;

import com.bilgidoku.rom.shared.err.KnownError;

public class Go extends CmdRef {
	static final int IN = 32000;
	static final int SUC = 32001;
	static final int FAILED = 32002;
	static final int FAILEDRETRY = 32003;
	static final int RETRY = 32004;

	final long time;
	final int go;

	public Go(long time, String file, int hostId, int go) throws KnownError {
		super(hostId, file);
		this.time = time;
		this.go = go;
		if(go>RETRY || go<IN){
			throw new KnownError("Invalid go:"+go);
		}
	}
	
	public synchronized void write(DataOutputStream out) throws IOException {
		out.writeLong(time);
		out.writeInt(hostId);
		out.writeUTF(file);
		out.writeInt(go);
	}

	
	public static Go read(DataInputStream in) throws KnownError, EOFException {
		try {
			long time = in.readLong();
			int hostId = in.readInt();
			String file = in.readUTF();
			int go = in.readInt();
			if(go>RETRY || go<IN){
				throw new KnownError("Invalid go:"+go+" date:"+new Date(time)+" hostId:"+hostId+" file:"+file);
			}
			
			return new Go(time, file, hostId, go);
		} catch(EOFException e){
			throw e;
		}catch (IOException e) {
			throw new KnownError("Go file reading",e);
		}
	}

	public boolean needRepo() {
		return (go==IN);
	}

	public boolean inThePool() {
		return (go==IN||go==RETRY);
	}
}
