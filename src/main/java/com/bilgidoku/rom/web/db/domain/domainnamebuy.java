package com.bilgidoku.rom.web.db.domain;

import com.bilgidoku.rom.pg.dict.AfterHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;

public class domainnamebuy extends AfterHook{
	
//	final static private DomainService ds=ServiceDiscovery.getService(DomainService.class);

	@Override
	public void hook(HookScope scope, Object resp) throws KnownError{
//		try {
//			String p_domain = scope.getParam("domainname", 3, 400, true);
//			JSONObject registrant=scope.getJsonParam("registrant", 20, 600, true); 
//			Integer years=scope.getIntParam("years", false);
//			if(years==null){
//				years=1;
//			}
//			
//			if(years>10 || years<1){
//				throw new KnownError("Unexpected range for years; should be in 1-10");
//			}
//			
//			DomainAvailability dc = ds.domainCheck(p_domain);
//			
//			if(dc==null){
//				throw new KnownError("Service not available");
//			}
//			
//			if(!dc.isAvailable){
//				throw new KnownError("Domain name is not available");
//			}
//			
//			ds.domainCreate(scope.getIntraHostId(), p_domain, registrant, years);
//			
//			
//		} catch (ParameterError e) {
//			throw new KnownError(e);
//		}
		
	}

	@Override
	public void undo(HookScope scope) throws KnownError {
		// TODO Auto-generated method stub
		
	}

}
