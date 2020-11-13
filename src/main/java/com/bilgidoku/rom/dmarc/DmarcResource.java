package com.bilgidoku.rom.dmarc;

import java.util.HashMap;
import java.util.Map;

import com.bilgidoku.rom.shared.err.KnownError;

public class DmarcResource {

	private boolean subResource;

	private String rua;
	private Boolean policyReject;
	private Boolean subPolicyReject;
	private boolean adkimStrict = false;
	private boolean aspfStrict = false;
	private long reportInterval=86400;//seconds
	private String reportFormat="afrf";

	private String dnsRecord;
	

	public DmarcResource(boolean isSubResource, String s) throws KnownError {
		this.subResource = isSubResource;
		this.dnsRecord=s;

		Map<String, String> recs = new HashMap<String, String>();
		String[] ss = s.split(";");
		for (String string : ss) {
			String[] sss = string.trim().split("=");
			if (sss == null || ss.length == 0) {
				continue;
			}
			if (sss.length != 2) {
				throw new KnownError("Invalid dmarc record:" + s);
			}
			recs.put(sss[0].trim(), sss[1].trim());
		}
		this.rua = recs.get("rua");
		String policy = recs.get("p");
		if (policy == null) {
			throw new KnownError("Policy is required;" + s);
		}
		if (policy.equals("reject")) {
			policyReject = true;
		} else if (policy.equals("quarantine")) {
			policyReject = false;
		} else {
			policyReject = null;
		}

		subPolicyReject=policyReject;
		
		policy = recs.get("sp");
		if (policy != null) {
			if (policy.equals("reject")) {
				subPolicyReject = true;
			} else if (policy.equals("quarantine")) {
				subPolicyReject = false;
			} else {
				subPolicyReject = null;
			}
		}

		String adkim = recs.get("adkim");
		if (adkim != null && adkim.equals("s")) {
			adkimStrict = true;
		}
		String aspf = recs.get("aspf");
		if (aspf != null && aspf.equals("s")) {
			aspfStrict = true;
		}
		
		String ri=recs.get("ri");
		if(ri!=null){
			reportInterval=Long.parseLong(ri);
		}
		
		String rf=recs.get("rf");
		if(rf!=null){
			reportFormat=rf;
		}
	}


	/**
	 * 
	 * 
	 * @param spf
	 * @param dmarc
	 * @return true to reject
	 * 	false to quarantina
	 *  null to do nothing
	 */
	public DmarcResponse auth(Boolean spf, Boolean dkim) {
		Boolean pReject = (subResource?subPolicyReject:policyReject);
		String[] ret=new String[2];
		
		Boolean spfSuc=true;
		String spfExp="spf pass;";
		
		if(spf==null){
			if(aspfStrict){
				spfExp="spf failed strict policy;";
				spfSuc=false;
			}
		}else if(!spf){
			spfSuc=false;
			spfExp="spf failed relaxed policy;";
		}
		
		Boolean dkimSuc=true;
		String dkimExp="dkim pass;";
		if(dkim==null){
			if(adkimStrict){
				dkimSuc=false;
				dkimExp="dkim failed strict policy;";
			}
		}else if(!dkim){
			dkimSuc=false;
			dkimExp="dkim failed relaxed policy;";
		}

		return new DmarcResponse(spfSuc,dkimSuc,pReject,spfExp,dkimExp,dnsRecord);
	}

}
