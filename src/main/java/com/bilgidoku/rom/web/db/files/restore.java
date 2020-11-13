package com.bilgidoku.rom.web.db.files;

import com.bilgidoku.rom.kurum.KurumDosyaGorevlisi;
import com.bilgidoku.rom.pg.dict.BeforeHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;

public class restore extends BeforeHook {


	@Override
	public boolean hook(HookScope scope) throws KnownError {
		KurumDosyaGorevlisi.tek().restoreHost(scope.getHostId());
		return true;
	}

	@Override
	public void undo(HookScope scope) {
	}

	
}
