package com.bilgidoku.rom.pg.dict;

public enum Net {
	ALL,INTER,INTRA,ONE;
	public static Net getEnum(String s){
        if(ALL.name().equalsIgnoreCase(s)){
            return ALL;
        }else if(INTER.name().equalsIgnoreCase(s)){
            return INTER;
        }else if(INTRA.name().equalsIgnoreCase(s)){
            return INTRA;
        }else if(ONE.name().equalsIgnoreCase(s)){
            return ONE;
        }
        throw new IllegalArgumentException("No Net Enum specified for this string:"+s);
    }
	
}
