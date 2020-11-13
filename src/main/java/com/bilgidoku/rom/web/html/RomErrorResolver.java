package com.bilgidoku.rom.web.html;

/**
 *	 
 * @author avci
 */
public class RomErrorResolver {
	private final static String ERR="ERROR: sign:";
	private final static String CODE=ERR+"XYZTCCC:";
	
	public RomError resolve(String desc){
		if(!desc.startsWith(ERR))
			return null;
		String code=desc.substring(ERR.length(), CODE.length()-1);
		return new RomError(code,desc.substring(CODE.length()));
	}
	
}
