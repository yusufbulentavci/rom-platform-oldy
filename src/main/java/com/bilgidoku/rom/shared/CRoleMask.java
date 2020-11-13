package com.bilgidoku.rom.shared;

import com.bilgidoku.rom.min.Sistem;

public class CRoleMask {
	public static final int candoall = 07;
	public static final int canread = 04;
	public static final int canwrite = 02;
	public static final int canreadwrite = 06;
	public static final int canx = 01;

	public static final long read = 0444444444444444444444L;
	public static final long write = 0222222222222222222222L;
	public static final long exec = 0111111111111111111111L;

	public static final int guest = 1 << 0;
	public static final int contact = 1 << 1;
	public static final int owner = 1 << 2;
	public static final int group = 1 << 3;
	public static final int related = 1 << 4;
	public static final int user = 1 << 5;
	public static final int admin = 1 << 6;
	public static final int designer = 1 << 7;
	public static final int desk = 1 << 8;
	public static final int deskhelper = 1 << 9;
	public static final int author = 1 << 10;
	public static final int manager = 1 << 11;

	public static final String[] rolesByName = { "guest", "contact", "owner", "group", "related", "user", "admin",
			"designer", "desk", "deskhelper", "author", "manager" };

//	public static final String[] maskWriting = { "admin author owner", "candoall", "user contact guest", "canread" };
	public static final String[] maskWriting = { "guest", "candoall"};

	public static final String[] candos={"cant","canx","canwrite","canxwrite","canread","canxread","canwriteread","candoall"};
	
	
	protected static final long GUESTMASK = makeRoleMask(guest, candoall);
	protected static final long CONTACTMASK = makeRoleMask(contact, candoall);
	protected static final long OWNERMASK = makeRoleMask(owner, candoall);
	protected static final long GROUPMASK = makeRoleMask(group, candoall);
	protected static final long RELATEDMASK = makeRoleMask(related, candoall);
	protected static final long USERMASK = makeRoleMask(user, candoall);

	// protected static final long CONTACTMASK=makeRoleMask(GUESTROLE);

	public static void preDeterminedMasks() {
		determinedMask(maskWriting);
	}
	
	public static int cando(String s){
		for(int i=0;i<candos.length;i++){
			if(candos[i].equals(s))
				return i;
		}
		throw new RuntimeException("Unknown cando:"+s);
	}

	private static long determinedMask(String[] maskwriting2) {
		int iroles=0;
		int cd=0;
		for(int i=0;i<maskwriting2.length; i+=2){
			String[] roles=maskwriting2[i].split(" ");
			for (String string : roles) {
				int rl=roleFromString(string);
				iroles=iroles|(1<<rl);
			}
			
			cd=cando(maskwriting2[i+1]);
		}
		Sistem.outln("roles:"+iroles);
		long msk=makeRoleMask(iroles, cd);
		Sistem.outln(msk);
		String bs = maskString(msk);
		Sistem.outln(bs);
		return msk;
	}

	private static int roleFromString(String s) {
		for(int i=0;i<rolesByName.length;i++){
			if(rolesByName[i].equals(s))
				return i;
		}
		throw new RuntimeException("Unknown role:"+s);
	}

	public static long makeRoleMask(long roleBitwise, long cando) {
		long ret = 0;
		for (int i = 0; i < 21; i++) {
			if ((roleBitwise & (1L << i)) == 0)
				continue;
			ret += (cando << (i * 3));
		}
		return ret;
	}

	public static String maskString(long mask) {
		int mode=3;
		StringBuilder sb = new StringBuilder();
		for (int i = 63; i >= 0; i--) {
			if (((i + 1) % mode) == 0) {
				sb.append("\n");
				int rid = i / mode;
				String rname;
				if (rid < rolesByName.length)
					rname = rolesByName[rid];
				else
					rname = "norole";
				sb.append("(" + rid + "-" + rname + ")");
			}
			if ((mask & (1L << i)) == 0) {
				sb.append("0");
			} else {
				sb.append("1");
			}
		}
		return sb.toString();
	}
	
	public static String roleString(long mask) {
		StringBuilder sb = new StringBuilder();
		for (int i = 20; i >= 0; i--) {
			String rname;
			if (i < rolesByName.length)
				rname = rolesByName[i];
			else
				rname = "norole";
			sb.append("(" + i + "-" + rname + ")");
			if ((mask & (1L << i)) == 0) {
				sb.append("0");
			} else {
				sb.append("1");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public static int getMaskTokenOfRole(long mask, int role) {
		return (int) ((mask >> (role * 3)) & 7);
	}

	public static long setContact(long userMask) {
		return userMask | CONTACTMASK;
	}

	public static long setOwner(long userMask) {
		return userMask | OWNERMASK;
	}

	public static long setGroup(long userMask) {
		return userMask | GROUPMASK;
	}

	public static long setRelated(long userMask) {
		return userMask | RELATEDMASK;
	}

	public static long setUser(long userMask) {
		return userMask | USERMASK;
	}

	public static boolean deskRole(int roles) {
		return (roles & desk) != 0;
	}

	public static boolean deskHelperRole(int roles) {
		return (roles & deskhelper) != 0;
	}

	public static boolean hasRole(int roles, int role) {
		return (roles & role) != 0;
	}

	public static boolean maskIsPublic(Long mask) {
		return (GUESTMASK & mask) != 0;
	}
	
	
	public static void main(String[] args) {
//		System.out.println(user);
//		System.out.println(GUESTMASK);
//		System.out.println(CONTACTMASK);
//		System.out.println(USERMASK);
		System.out.println(maskString(2059744));
		
	}
	
	public static long buildMask(int[] units){
//		System.out.println((0|1));
		long ret=0;
		for(int i=0; i<units.length; i++){
			ret=(ret | (units[i]<<(3*i)));
//			System.out.println(units[i]+"->"+(units[i]<<(3*i)));
//			System.out.println(ret);
		}
		return ret;
	}
}
