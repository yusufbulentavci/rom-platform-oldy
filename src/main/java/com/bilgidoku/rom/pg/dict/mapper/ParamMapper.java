package com.bilgidoku.rom.pg.dict.mapper;

import java.text.ParseException;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.dict.ArgMapper;
import com.bilgidoku.rom.pg.dict.CallInteraction;
import com.bilgidoku.rom.pg.dict.types.TypeHolder;

public class ParamMapper extends ArgMapper{

	private String paramName;
	
	public ParamMapper(short i, String varName, TypeHolder sqlType, boolean canBeNull) {
		super(i,canBeNull);
		this.paramName=varName;
//		if(this.paramName.equals("lng")){
//			conversion="rom.langs";
//		}
		this.sqlType=sqlType;
	}

	public String toString(){
		return "ParamMapper:"+" name:"+this.paramName+" sqlType:"+sqlType.toString();
	}

	public String getConversion(){
		if(sqlType.needCast()){
			return sqlType.getSqlDefinition();
		}
		return null;
	}

	@Override
	public void setValue(CallInteraction  request, String self, DbThree ps) throws KnownError, JSONException, ParseException, KnownError, ParameterError {
//		if(this.sqlType.isArray()){
//			String[] val=request.getParams(paramName, !this.canBeNull);
//			syso("Param assignment for array;paramName:"+this.paramName+" Value:"+val);
//			this.sqlType.setValue(ps,  val);
//		}else{
			String val=request.getParam(paramName, null, null, !this.canBeNull);
			this.sqlType.setValue(ps, val);
//			syso("Param assignment ;paramName:"+this.paramName+" Value:"+val);
//		}
		
	}

	@Override
	public Object getValue(CallInteraction request, String self)
			throws JSONException, ParseException, KnownError, ParameterError {
		String val=request.getParam(paramName, null, null, !this.canBeNull);
		return this.sqlType.fromString(val);
	}
	
	public String callProto(){
		return sqlType.callProtoPart();
	}
	
}
