package com.bilgidoku.rom.web.db.files;

import com.bilgidoku.rom.pg.dbfs.DbfsGorevlisi;
import com.bilgidoku.rom.pg.dict.BeforeHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.ParameterError;

public class dbfsdel extends BeforeHook {

	
	@Override
	public boolean hook(HookScope scope) throws KnownError, ParameterError {
		String fn=scope.getParam("fn", 4, 200, true);
		DbfsGorevlisi.tek().delete(scope.getHostId(), fn);
		return true;
	}


	@Override
	public void undo(HookScope scope) throws KnownError {
//		RunTime.dbfs().undeleteFile(scope.getHostId(), scope.getUri(), true);
	}
	
}
