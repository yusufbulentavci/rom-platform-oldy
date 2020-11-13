package com.bilgidoku.rom.gwt.client.util.common;

import com.bilgidoku.rom.shared.CRoleMask;
import com.google.gwt.user.client.Cookies;

public class Role {
	
	public static boolean isUser() {
		String rolestr = Cookies.getCookie("roles");
		if (rolestr == null)
			return false;
		int roles = Integer.parseInt(rolestr);
		return CRoleMask.hasRole(roles, CRoleMask.user);
	}

	public static boolean isAdmin() {
		String rolestr = Cookies.getCookie("roles");
		if (rolestr == null)
			return false;
		int roles = Integer.parseInt(rolestr);
		return CRoleMask.hasRole(roles, CRoleMask.admin);
	}

	public static boolean isDesigner() {
		String rolestr = Cookies.getCookie("roles");
		if (rolestr == null)
			return false;
		int roles = Integer.parseInt(rolestr);
		return CRoleMask.hasRole(roles, CRoleMask.designer);
	}

	
}
