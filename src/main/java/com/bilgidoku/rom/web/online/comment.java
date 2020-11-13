package com.bilgidoku.rom.web.online;

import com.bilgidoku.rom.pg.dict.AfterHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;

public class comment extends AfterHook{

	
	@Override
	public void hook(HookScope scope, Object resp) throws KnownError,
			NotInlineMethodException, ParameterError {
		int hostId=scope.getHostId();
		String uri=scope.getUri();
		OnlineGorevlisi.tek().comment(hostId, uri, resp.toString());
	}

	@Override
	public void undo(HookScope scope) throws KnownError {
		// TODO Auto-generated method stub
		
	}

}
