package com.bilgidoku.rom.pg.dict;

import java.text.ParseException;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.dict.types.TypeHolder;


public abstract class ArgMapper {
	protected boolean canBeNull;
	int index;
	protected TypeHolder sqlType;
	
	public ArgMapper(int index,boolean canBeNull){
		this.canBeNull=canBeNull;
		this.index=index;
	}
	
	public String getConversion(){
		return null;
	}
	
	public String callProto(){
		return "?";
	}
	
	public abstract void setValue(CallInteraction request, String self, DbThree ps) throws KnownError, JSONException, NotInlineMethodException, ParseException, KnownError, KnownError, ParameterError; 
	
	public abstract Object getValue(CallInteraction request, String self) throws JSONException, ParseException, KnownError, KnownError, ParameterError;
	public boolean isNullable(){
		return canBeNull;
	}
}
