package com.bilgidoku.rom.web.db.domain;

import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.dict.BeforeHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.ParameterError;

public class removeuser extends BeforeHook {
	private static final MC mc = new MC(removeuser.class);
//	private static MailService mailboxService;

	@Override
	public boolean hook(HookScope scope) throws KnownError, ParameterError {
//		Integer hostId = scope.getHostId();
//		String userName = scope.getParam("username", 1, 20, true);
//		mailboxService.mailUnsubscribe(hostId, userName);
		return true;

	}

	@Override
	public void undo(HookScope scope) throws KnownError {

	}

	@Override
	public void start() {
//		super.start();
//		mailboxService = ServiceDiscovery.getService(MailService.class);
	}

}
