package com.bilgidoku.rom.pg.dict;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.CRoleMask;


public class RoleMask extends CRoleMask {

	public static String[] roleNames = new String[] { "guest", "contact", "owner", "group", "related", "user", "admin", "editor", "designer", "desk",
			"deskhelper", "author", "manager" };
	
	

	public static void checkGuestAccess(long mask, long accessMode) throws KnownError {
		if ((GUESTMASK & mask & accessMode) == 0)
			throw new KnownError().forbidden();
	}

	
	public static boolean checkGuestAccessProblem(long mask, long accessMode) throws KnownError {
		return  ((GUESTMASK & mask & accessMode) == 0);
		
	}
	public static void checkAccess(long mask, long userMask, long accessMode) throws KnownError {
		if (mask == 0)
			return;
		if ((userMask & mask & accessMode) == 0){
			Sistem.errln("UserMask("+userMask+"):"+RoleMask.maskToString(userMask));
			Sistem.errln("ResourceMask("+mask+"):"+RoleMask.maskToString(mask));
			Sistem.errln("MethodMask("+accessMode+"):"+RoleMask.maskToString(accessMode));
			throw new KnownError().forbidden();
		}
	}
	
	/**
	 * 
	 * true if we have a access problem
	 * 
	 * @param mask
	 * @param userMask
	 * @param accessMode
	 * @return
	 * @throws KnownError
	 */
	public static boolean accessProblem(long mask, long userMask, long accessMode) throws KnownError {
		if (mask == 0)
			return false;
		if ((userMask & mask & accessMode) == 0){
			Sistem.errln("UserMask("+userMask+"):"+RoleMask.maskToString(userMask));
			Sistem.errln("ResourceMask("+mask+"):"+RoleMask.maskToString(mask));
			Sistem.errln("MethodMask("+accessMode+"):"+RoleMask.maskToString(accessMode));
			return true;
		}
		return false;
	}


	public static boolean deskHelperRole(int roles) {
		return (roles & deskhelper) != 0;
	}

	public static boolean hasRole(int roles, int role) {
		return (roles & role) != 0;
	}

	public static String roleToString(int roles) {
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<roleNames.length;i++){
			if( (roles&(1<<i)) !=0){
				sb.append(" ");
				sb.append(roleNames[i]);
			}
		}
		return sb.toString();
	}
	
	public static String maskToString(long mask) {
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<roleNames.length;i++){
			int token=getMaskTokenOfRole(mask,i);
			if(token==0)
				continue;
			sb.append(" ");
			sb.append(roleNames[i]);
			sb.append(":");
			sb.append(tokenString(token));
		}
		return sb.toString();
	}
	
	public static String tokenString(int x){
		String ret="";
		if((x&1)!=0)
			ret+="E";
		if((x&2)!=0)
			ret+="W";
		if((x&4)!=0)
			ret+="R";
		return ret;
	}
	

}
