package com.bilgidoku.rom.web.db.files;

import com.bilgidoku.rom.kurum.KurumDosyaGorevlisi;
import com.bilgidoku.rom.pg.dict.BeforeHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;

public class undo extends BeforeHook {


	@Override
	public boolean hook(HookScope scope) throws KnownError  {
		KurumDosyaGorevlisi.tek().undeleteFile(scope.getHostId(), scope.getUri(), true);
		return true;
	}


	@Override
	public void undo(HookScope scope) throws KnownError {
		KurumDosyaGorevlisi.tek().deleteFile(scope.getHostId(), scope.getUri(), true);
	}
	
}
