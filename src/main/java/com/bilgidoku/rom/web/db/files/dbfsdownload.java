package com.bilgidoku.rom.web.db.files;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.kurum.KurumDosyaGorevlisi;
import com.bilgidoku.rom.pg.dbfs.DbfsGorevlisi;
import com.bilgidoku.rom.pg.dict.BeforeHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;

public class dbfsdownload extends BeforeHook {
	private static final MC mc = new MC(dbfsdownload.class);

	private static final AtomicInteger counter = new AtomicInteger();
	private static final String tempPrefix = "/tmp/rom/down/";

	private static final Astate uc = mc.c("undo");
	private static final Astate ue = mc.c("undo-failed");

	@Override
	public void start() {
		if(!new File("/tmp/rom/down").exists()){
			new File("/tmp/rom/down").mkdirs();
		}
	}

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
	private static final Astate doe = mc.c("do-failed");

	@Override
	public boolean hook(HookScope scope) throws KnownError, NotInlineMethodException, KnownError, ParameterError {
		doc.more();

		String du = scope.request.getParam("download_uri", 5, 300, true);

		int li = du.lastIndexOf('/');
		if (li <= 0) {
			doe.more();
			throw new KnownError("Bad syntax:" + du).badRequest();
		}

		String fileName = du.substring(li + 1, du.length());
		fileName = KurumDosyaGorevlisi.tek().nameFix(fileName);
		
		String ftd=tempPrefix+fileName;
		if(new File(ftd).exists()){
			ftd=tempPrefix+counter.incrementAndGet()+fileName;
		}

		if (!Downloader.download(du, ftd)) {
			new File(ftd).delete();
			throw new KnownError().notFound(ftd);
		}

		String fn = DbfsGorevlisi.tek().make(scope.getHostId(), new File(ftd), true);

		scope.paramOverride("fn", fn);

		return true;
	}
}
