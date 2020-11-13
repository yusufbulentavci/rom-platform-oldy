package com.bilgidoku.rom.session;

import com.bilgidoku.rom.shared.err.KnownError;

public class CommandLog {

	private final String command;
	private final String arg;
	private KnownError exception;
	private boolean important;
	private boolean endSession;
	private boolean suc;
	private String retCode;

	public CommandLog(String command, String arg) {
		this.command = command;
		this.arg = arg;
	}

	public void setException(KnownError e) {
		this.exception = e;
	}

	public void setResponse(String retCode, boolean success, boolean endSession, boolean important) {
		this.retCode = retCode;
		this.suc = success;
		this.endSession = endSession;
		this.important = important;
	}

	@Override
	public String toString() {
		StringBuilder jo = new StringBuilder();
		jo.append(command);
		if (retCode != null) {
			if (endSession)
				jo.append(" endsession");
			if (!suc) {
				jo.append(" failed");
			}

			if (important) {
				jo.append(" important");
			}

			jo.append(" ret:");
			jo.append(retCode);
		}
		
		if(exception!=null){
			jo.append(" exception:");
			jo.append(exception.getSummary());
		}

		if (arg != null) {
			jo.append(" arg:");
			if (arg.length() > 50) {
				jo.append(arg.substring(0, 50));
			} else {
				jo.append(arg);
			}
		}

		return jo.toString();
	}

}
