package com.bilgidoku.rom.web.db.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.kurum.KurumDosyaGorevlisi;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.web.utils.Knife;

public class makepng extends ImageBase {
	private static final MC mc = new MC(border.class);
	
	private void doit(File from) throws IOException {
		BufferedImage bi = ImageIO.read(from);
		
		String toFileName=Knife.replaceExtension(from.getPath(), "png");
		
		ImageIO.write(bi, "png", new File(toFileName));
	}
	
   

	public static void main(String[] args) throws IOException {
		makepng c = new makepng();
		c.doit(new File("/home/avci/Downloads/from.jpg"));
	}

	private static final Astate bd = mc.c("do");
	private static final Astate be = mc.c("makepng-error");


	@Override
	public boolean hook(HookScope scope) throws KnownError, NotInlineMethodException, KnownError, ParameterError {
		bd.more();

		File realFile = KurumDosyaGorevlisi.tek().getFile(scope.getHostId(), scope.getUri(),
				true, true);
		String toUri=Knife.replaceExtension(scope.getUri(), "png");
		
		scope.request.paramOverride("uri", toUri);		

		try {
			doit(realFile);
		} catch (IOException e) {
			be.more();
			throw new KnownError(e).internalError();
		}
		return true;
	}



	@Override
	public void undo(HookScope scope) throws KnownError, KnownError, ParameterError {
	}

}
