package com.bilgidoku.rom.shared.util;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.konus.bicim.bilgisi.Unlu;

public class StringUtil {
	public static String replace(String str, int index, char replace){     
	    if(str==null){
	        return str;
	    }else if(index<0 || index>=str.length()){
	        return str;
	    }
	    char[] chars = str.toCharArray();
	    chars[index] = replace;
	    return String.valueOf(chars);       
	}
	public static String replaceLast(String str, char replace){
		return replace(str, str.length() - 1, replace);
	}
	
	public static char strdeSonUnlu(String son) throws KnownError {
		
		for(int i=son.length()-1; i>= 0; i--) {
			char ch = son.charAt(i);
			if(Unlu.unlumu(ch)) {
				return ch;
			}
		}
		
		throw new KnownError("sesli yok:"+son);
	}

}
