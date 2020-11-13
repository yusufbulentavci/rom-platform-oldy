package com.bilgidoku.rom.gwt.server.middle;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.pg.dbfs.DbfsGorevlisi;
import com.bilgidoku.rom.pg.dict.CallInteraction;
import com.bilgidoku.rom.pg.dict.DaoCall;

import io.netty.handler.codec.http.multipart.FileUpload;


public abstract class DaoBase extends DaoCall{

	
	protected String getFileItem(CallInteraction request,  String paramName)
			throws NotInlineMethodException, KnownError, ParameterError{
		FileUpload item = request.getFileParam(paramName, false);
		if(item==null)
			return null;
		
		return DbfsGorevlisi.tek().make(request.getHostId(), item);
	}
	
	protected KnownError errJson(JSONException e) {
		return new KnownError(e);
	}
}
