package com.bilgidoku.rom.web.db.files;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64InputStream;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.kurum.KurumDosyaGorevlisi;
import com.bilgidoku.rom.pg.dict.BeforeHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;

public class pngtojpeg extends BeforeHook {
	private static final MC mc = new MC(pngtojpeg.class);

	private static final Astate uc = mc.c("undo");


	@Override
	public void undo(HookScope scope) throws KnownError, ParameterError {
		uc.more();
	}

	private static final Astate doc = mc.c("do");

	@Override
	public boolean hook(HookScope scope) throws KnownError, NotInlineMethodException, ParameterError {
		doc.more();

		String t = scope.request.getParam("text", 0, 5000000, true);
		// byte[] bytes = Base64.getDecoder().decode(t);

		// Base64InputStream stream=new Base64InputStream(new
		// ByteArrayInputStream(t.getBytes()));

		try (Base64InputStream stream = new Base64InputStream(new ByteArrayInputStream(t.getBytes()))) {
			BufferedImage bufferedImage = ImageIO.read(stream);
			BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
					BufferedImage.TYPE_INT_RGB);
			newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

			File f = KurumDosyaGorevlisi.tek().getFile(scope.getHostId(), scope.getUri(), true, true);

			ImageIO.write(newBufferedImage, "jpg", f);
			KurumDosyaGorevlisi.tek().realFileChanged(f.getPath());

		} catch (IOException e1) {
			throw new KnownError(e1);
		}

		return true;
	}
}
