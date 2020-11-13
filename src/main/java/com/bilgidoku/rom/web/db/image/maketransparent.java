package com.bilgidoku.rom.web.db.image;

import java.io.File;
import java.io.IOException;

import com.bilgidoku.rom.ilk.util.Assert;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.kurum.KurumDosyaGorevlisi;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.ParameterError;

public class maketransparent extends ImageBase {
	private static final MC mc = new MC(maketransparent.class);

	private static final Astate bu = mc.c("undo");

	@Override
	public void undo(HookScope scope) throws KnownError {
		bu.more();
	}

	private static final Astate bd = mc.c("do");
	private static final Astate be = mc.c("crop-error");

	@Override
	public boolean hook(HookScope scope) throws KnownError, ParameterError {
		bd.more();

		Integer left = scope.getIntParam("left", true);
		Assert.notNull(left);
		Integer top = scope.getIntParam("top", true);
		Assert.notNull(top);
		Integer closeind = scope.getIntParam("closeind", true);
		Assert.notNull(closeind);

		int[] excludes = scope.getIntParams("excludes", false);
		Assert.beTrue(excludes==null || excludes.length % 3 == 0);
		

		File realFile = KurumDosyaGorevlisi.tek().getFile(scope.getHostId(), scope.getUri(), true, true);
		String formatName = getFormatName(realFile);

		File deleted = KurumDosyaGorevlisi.tek().deleteFile(scope.getHostId(), scope.getUri(), true);

		try {
			doit(left, top, realFile, formatName, deleted, closeind, excludes);
		} catch (IOException e) {
			be.more("IO Exception; hostId: " + scope.getHostId() + " self:" + scope.getUri());
			throw new KnownError(e).internalError();
		}
		// scope.done();
		return true;
	}

	private void doit(int x, int y, File to, String formatName, File from, Integer closeind, int[] excludeCircles) throws IOException {
		MakeTransparent.doit(x, y, to, formatName, from, closeind, excludeCircles);
	}

}
