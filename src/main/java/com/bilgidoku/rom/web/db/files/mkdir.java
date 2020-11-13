package com.bilgidoku.rom.web.db.files;

import com.bilgidoku.rom.kurum.KurumDosyaGorevlisi;
import com.bilgidoku.rom.pg.dict.BeforeHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.ParameterError;

public class mkdir extends BeforeHook {	


	@Override
	public boolean hook(HookScope scope) throws KnownError, ParameterError {
		KurumDosyaGorevlisi.tek().mkdir(scope.getHostId(), getUri(scope), true);
		return true;
	}


	@Override
	public void undo(HookScope scope) throws KnownError, ParameterError {
		KurumDosyaGorevlisi.tek().rmdir(scope.getHostId(), getUri(scope), true);
	}
	
	private String getUri(HookScope scope) throws KnownError, ParameterError {
		return scope.getParam("uri", 1,250,false);
	}
}
