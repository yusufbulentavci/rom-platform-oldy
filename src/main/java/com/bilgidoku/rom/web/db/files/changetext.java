package com.bilgidoku.rom.web.db.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.kurum.KurumDosyaGorevlisi;
import com.bilgidoku.rom.pg.dict.BeforeHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;

public class changetext extends BeforeHook {
	private static final MC mc = new MC(changetext.class);

	private static final Astate uc = mc.c("undo");


	@Override
	public void undo(HookScope scope) throws KnownError, ParameterError {
		uc.more();
	}

	private static final Astate doc = mc.c("do");

	@Override
	public boolean hook(HookScope scope) throws KnownError, KnownError, NotInlineMethodException, ParameterError {
		doc.more();

		String text = scope.request.getParam("text", 0, 2000000, true);
		File f=KurumDosyaGorevlisi.tek().getFile(scope.getHostId(), scope.getUri(), true, true);
		try(FileWriter fw=new FileWriter(f)){
			fw.write(text);
		} catch (IOException e) {
			throw new KnownError("Files.changetext:f:"+f.getPath(), e);
		}
		
		KurumDosyaGorevlisi.tek().realFileChanged(f.getPath());
		
		return true;
	}
}
