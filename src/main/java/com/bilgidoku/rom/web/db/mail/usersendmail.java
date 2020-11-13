package com.bilgidoku.rom.web.db.mail;

import com.bilgidoku.rom.mail.EpostaGorevlisi;
import com.bilgidoku.rom.pg.dict.BeforeHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;

public class usersendmail extends BeforeHook {


	@Override
	public boolean hook(HookScope scope) throws KnownError, NotInlineMethodException, KnownError, ParameterError {
		scope.getHostId();
		scope.getUri();

		EpostaGorevlisi.tek().userSendMail(scope.getIntraHostId(), scope.getRequest().getSession().getUserName(),
				scope.getUri(), scope.getRequest().getSession().getUserEmail(), scope.getRequest().getRid());

		return true;
	}

	@Override
	public void undo(HookScope scope) throws KnownError, KnownError, ParameterError {
	}


}
