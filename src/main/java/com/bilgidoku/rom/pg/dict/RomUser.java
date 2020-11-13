package com.bilgidoku.rom.pg.dict;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.util.Assert;
import com.bilgidoku.rom.ilk.util.HostingUtils;
import com.bilgidoku.rom.shared.CRoleMask;

public class RomUser {
	private static final String GUEST_NAME = "_guest";
	private final String name;
	private int roles;
	private long roleMask;
	private final String credentials;
	private final boolean guest;
	private int passwordTry = 0;
	private String cname;
	private String cid;
	private final CommonSession session;
	private final RomDomain domain;
	private final int intraHostId;

	public final RomDomain getDomain() {
		return domain;
	}
	
	public final CommonSession getSession() {
		return session;
	}

	public RomUser(int intraHostId, CommonSession session, RomDomain domain, String name, String credentials2,
			int rolNames, String cid) throws KnownError {
		this.intraHostId = intraHostId;
		Assert.beTrue(HostingUtils.isIntra(intraHostId));

		this.session = session;
		this.domain = domain;

		// if(this.emailDomain==null){
		// throw new RuntimeException("Invalid state");
		// }
		this.name = name;
		this.cname = name;
		this.credentials = credentials2;
		this.guest = false;
		this.cid = cid;
		this.roles = rolNames;
		if (isUser()) {
			this.roles |= CRoleMask.user | CRoleMask.contact;
		} else if (isContact())
			this.roles |= CRoleMask.contact;
		this.roleMask = makeRoleMask(roles);
	}

	/**
	 * Guest olan obje user olamiyor, contact olabiliyor
	 */
	public RomUser() {
		this.intraHostId = -101;
		this.session = null;
		this.domain = null;
		this.name = GUEST_NAME;
		this.credentials = "";
		this.roles = 0;
		this.guest = true;
		this.roleMask = makeRoleMask(roles);
	}

	private long makeRoleMask(int roleBitwise) {
		long ret = 0;
		for (int i = 0; i < 21; i++) {
			if ((roleBitwise & (1L << i)) == 0)
				continue;
			ret += (07L << (i * 3));
		}
		return ret;
	}

	public String getEmail() {
		if (isGuest())
			return null;
		
		if(domain==null)
			return null;

		if (domain.getEmailDomain() == null)
			throw new RuntimeException("Invalid state:");
		return name + "@" + domain.getEmailDomain();
	}

	// public int getHostId() {
	// return host.getHostId();
	// }

	public boolean verifyPassword(String pass) {
		if (pass.equals(getCredentials())) {
			passwordTry = 0;
			return true;
		}
		passwordTry++;
		return false;
	}

	// public boolean hasRole(String role) {
	// for (String r : roles) {
	// if (role.equals(r))
	// return true;
	// }
	// return false;
	// }
	//
	// public boolean hasRole(String[] nrole) {
	// for (String r : roles) {
	// for (String nr : nrole) {
	// if (r.equals(nr))
	// return true;
	// }
	// }
	// return false;
	// }

	public boolean isGuest() {
		return guest && cid == null;
	}

	public boolean isUser() {
		return !guest;
	}

	public boolean isContact() {
		return cid != null;
	}

	public String getName() {
		return name;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCid() {
		return cid;
	}
	
	public Integer getCint() {
		return Integer.parseInt(cid.substring(cid.lastIndexOf('/') + 1));
	}
	
//	public static void main(String[] args) {
//		System.out.println(getCint());
//	}
	

	public void setCid(String cid) {
		this.cid = cid;
		if (cid == null) {
			this.roles = 0;
		} else {
			this.roles |= CRoleMask.contact;
			this.roleMask = makeRoleMask(roles);
		}
	}

	public boolean authenticated() {
		return cid != null;
	}

	public String getCredentials() {
		return credentials;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RomUser))
			return false;
		RomUser ru = (RomUser) obj;
		return ru.name.equals(name);
	}

	public long getRoleMask() {
		return roleMask;
	}

	public int getRole() {
		return roles;
	}

	public boolean deskRole() {
		return RoleMask.deskRole(roles);
	}

	public boolean deskHelperRole() {
		return RoleMask.deskHelperRole(roles);
	}

	public boolean hasRole(int role) {
		return RoleMask.hasRole(roles, role);
	}

	public int getIntraHostId() {
		return intraHostId;
	}

	public int getInterHostId() {
		return HostingUtils.hostIdInter(intraHostId);
	}

}
