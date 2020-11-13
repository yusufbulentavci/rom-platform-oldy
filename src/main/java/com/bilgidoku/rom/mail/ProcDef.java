package com.bilgidoku.rom.mail;


import com.bilgidoku.rom.shared.err.KnownError;


public class ProcDef {
	String name;
	Pipe pipe;

	public ProcDef(String name2) {
		this.name = name2;
	}

	void setLastPipe(Pipe p) {
		if (pipe == null) {
			pipe = p;
			return;
		}
		Pipe current = pipe;
		while (current.hasNext()) {
			current = current.getNext();
		}
		current.setNext(p);
	}

	public void service(Email mail) throws KnownError {
		pipe.service(mail);
	}
}