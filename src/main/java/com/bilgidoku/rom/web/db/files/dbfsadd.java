package com.bilgidoku.rom.web.db.files;

import java.util.concurrent.atomic.AtomicInteger;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.dbfs.DbfsGorevlisi;
import com.bilgidoku.rom.pg.dict.BeforeHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;

import io.netty.handler.codec.http.multipart.FileUpload;

public class dbfsadd extends BeforeHook {
	private static final MC mc = new MC(dbfsadd.class);

	private static final AtomicInteger counter=new AtomicInteger();
	private static final String tempPrefix="/tmp/down";

	private static final Astate uc = mc.c("undo");
	private static final Astate ue = mc.c("undo-failed");

	@Override
	public void undo(HookScope scope) throws KnownError, ParameterError {
		uc.more();
		String downloadedUri;
		downloadedUri = scope.getParam("uri", 1, 400, true);
		if (downloadedUri == null)
			return;
		DbfsGorevlisi.tek().delete(scope.getHostId(), downloadedUri);
	}

	
	private static final Astate doc = mc.c("do");

	@Override
	public boolean hook(HookScope scope) throws KnownError, NotInlineMethodException, KnownError, ParameterError {
		doc.more();

		FileUpload item = scope.request.getFileParam("fn", true);

		String fn=DbfsGorevlisi.tek().make(scope.getHostId(), item);
					
		scope.paramOverride("fn", fn);

		return true;
	}
}
