package com.bilgidoku.rom.web.db.domain;

import com.bilgidoku.rom.pg.dict.BeforeHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;

public class domainnameavailable extends BeforeHook{
//	static private DomainService ds;

	public void start() {
//		super.start();
//		ds=ServiceDiscovery.getService(DomainService.class);
	}
	
	@Override
	public boolean hook(HookScope scope) throws KnownError{
//		String p_domain;
//		try {
//			p_domain = scope.getParam("domainname", 3, 400, true);
//			return ds.domainCheck(p_domain).isAvailable;
//		} catch (ParameterError e) {
//			throw new KnownError(e);
//		}
		return true;
	}

	@Override
	public void undo(HookScope scope) throws KnownError{
		// TODO Auto-generated method stub
		
	}

}
