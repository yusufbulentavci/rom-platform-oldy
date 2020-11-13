package com.bilgidoku.rom.pg.dict;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.pg.dict.RomResource;

/*
Access control
Return mime type
*/
public class MethodControl {

	private final long accessMode;
	private final String mime;

	public MethodControl(long am, String mime) {
		this.accessMode=am;
		this.mime=mime;
	}

	public long getAccessMode() {
		return accessMode;
	}

	
	// User has a role mask, resource has access control bits
	public boolean ableToLoginCheckAccess(RomResource res, boolean isGuest, long roleMask, String cid) throws KnownError {
		if(res.getMask()==null)
			return false;
		if(isGuest){
			return RoleMask.checkGuestAccessProblem(res.getMask(),accessMode);
		}
		long userMask=roleMask;
		if(res.getOwner()!=null &&res.getOwner().equals(cid)){
			userMask=RoleMask.setOwner(userMask);
		}
		if(res.getRelatedCids()!=null){
			String[] s=res.getRelatedCids();
			for (String string : s) {
				if(string.equals(cid)){
					userMask=RoleMask.setRelated(userMask);
					break;
				}
			}
		}
		return RoleMask.accessProblem(res.getMask(),userMask,accessMode);
	}
	
	// User has a role mask, resource has access control bits
	// User based(cid) access control for resource
	public void checkAccess(RomResource res, boolean isGuest, long roleMask, String cid) throws KnownError {
		if(res.getMask()==null)
			return;
		if(isGuest){
			RoleMask.checkGuestAccess(res.getMask(),accessMode);
			return;
		}
		long userMask=roleMask;
//		userMask=RoleMask.setContact(userMask);
//		if(user.isUser()){
//			userMask=RoleMask.setUser(userMask);
//		}
		if(res.getOwner()!=null &&res.getOwner().equals(cid)){
			userMask=RoleMask.setOwner(userMask);
		}
//		if(res.getGroup()!=null &&)
		if(res.getRelatedCids()!=null){
			String[] s=res.getRelatedCids();
			for (String string : s) {
				if(string.equals(cid)){
					userMask=RoleMask.setRelated(userMask);
					break;
				}
			}
		}
		RoleMask.checkAccess(res.getMask(),userMask,accessMode);
	}

	public String getMime() {
		return mime;
	}

}
