package com.bilgidoku.rom.web.db.domain;

import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.mail.EpostaGorevlisi;
import com.bilgidoku.rom.pg.dict.AfterHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.ParameterError;

public class adduser extends AfterHook {
	private static final MC mc = new MC(adduser.class);

	@Override
	public void undo(HookScope scope) throws KnownError {

	}


	@Override
	public void hook(HookScope scope, Object resp) throws KnownError, ParameterError {
		Integer hostId = scope.getHostId();
		String userName = scope.getParam("username", 1, 20, true);
		EpostaGorevlisi.tek().mailSubscription(hostId, userName);
	}

}
