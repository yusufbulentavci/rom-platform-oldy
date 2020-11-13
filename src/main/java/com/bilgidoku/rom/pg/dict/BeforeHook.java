package com.bilgidoku.rom.pg.dict;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;

public abstract class BeforeHook {

	protected CGMethod romMethod;
	protected boolean started=false;
	
	public void initialize(CGMethod method) {
		this.romMethod = method;
	}

	abstract public boolean hook(HookScope scope) throws KnownError, NotInlineMethodException, KnownError, ParameterError;

	abstract public void undo(HookScope scope) throws KnownError, KnownError, ParameterError;

	public void start() {
		started=true;
	}

	public boolean getStarted() {
		// TODO Auto-generated method stub
		return false;
	}

	protected KnownError err(Exception e) {
		return new KnownError(e);
	}

}
