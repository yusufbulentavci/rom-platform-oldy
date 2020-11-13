package com.bilgidoku.rom.mail;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;



class MailProcess {
	private static final MC mc = new MC(MailProcess.class);

	private final List<ProcDef> pipes = new ArrayList<ProcDef>();
	private ProcDef main;


	public void process(String pipeName, Email object) throws KnownError {
		for (ProcDef p : pipes) {
			if (p.name.equals(pipeName)) {
				p.pipe.service(object);
				return;
			}
		}

	}


	public void process(Email object) throws KnownError {
		main.service(object);
	}

	public void pipe(String name, Matcher matcher) {
		pipe(name, matcher, null, null);
	}

	public void pipe(String name, Matcher matcher, MailDo mailet) {
		pipe(name, matcher, mailet, null);
	}

	public void pipe(String name, Matcher matcher, MailDo mailet, String processor) {
		ProcDef main = getProcessor(name);
		ProcDef mp = processor == null ? null : getProcessor(processor);
		Pipe pipe = new Pipe(matcher, mailet, mp);
		main.setLastPipe(pipe);
	}

	public void defineProcessor(String name, boolean entry) {
		defineProcessor(name);
		if (entry)
			this.main = getProcessor(name);
	}

	public void defineProcessor(String name) {
		ProcDef pd = new ProcDef(name);
		this.pipes.add(pd);
	}

	private final static Astate upe = mc.c("unknown-processor");

	private ProcDef getProcessor(String name) {
		for (ProcDef p : pipes) {
			if (p.name.equals(name)) {
				return p;
			}
		}
		upe.more();
		throw new RuntimeException("Unknown processor:" + name);
	}

}