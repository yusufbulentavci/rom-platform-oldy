package com.bilgidoku.rom.dmarc;

public class DmarcResponse {

	public final Boolean spfSuc;
	public final Boolean dkimSuc;
	public final Boolean policyReject;
	public String explain;
	private boolean suc;

	public DmarcResponse(Boolean spfSuc, Boolean dkimSuc, Boolean pReject, String spfExp, String dkimExp, String dnsRecord) {
		this.spfSuc=spfSuc;
		this.dkimSuc=dkimSuc;
		this.policyReject=pReject;
		this.suc=((spfSuc==null || spfSuc) && (dkimSuc==null || dkimSuc));
		StringBuilder sb=new StringBuilder();
		if(suc){
			sb.append("dmarc pass;");
		}else{
			sb.append("dmarc failed;");
		}
		if(policyReject==null){
			sb.append("policy:none;");
		}else if(policyReject){
			sb.append("policy:reject;");
		}else{
			sb.append("policy:quarantine;");
		}
		sb.append(spfExp);
		sb.append(dkimExp);
		sb.append("dns:");
		sb.append(dnsRecord);
		this.explain=sb.toString();
	}


	public boolean isSuc() {
		return suc;
	}

	public String policy() {
		if(policyReject==null)
			return "none";
		return policyReject?"reject":"quarantine";
	}

}
