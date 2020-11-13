package com.bilgidoku.rom.web.db.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.kurum.KurumDosyaGorevlisi;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.ParameterError;

public class border extends ImageBase {
	private static final MC mc = new MC(border.class);

	private static final Astate bu = mc.c("undo");
	

	@Override
	public void undo(HookScope scope) throws KnownError {
		bu.more();
		KurumDosyaGorevlisi.tek().undeleteFile(scope.getHostId(), scope.getUri(), true);
	}

	private static final Astate bd = mc.c("do");
	private static final Astate be = mc.c("border-error");

	@Override
	public boolean hook(HookScope scope) throws KnownError, ParameterError {
		bd.more();
		Integer width = scope.getIntParam("width", true);
		Integer height = scope.getIntParam("height", true);
		Integer color = scope.getIntParam("color", true);

		File realFile = KurumDosyaGorevlisi.tek().getFile(scope.getHostId(), scope.getUri(),
				true, true);
		String formatName = getFormatName(realFile);

		File deleted = KurumDosyaGorevlisi.tek().deleteFile(scope.getHostId(), scope.getUri(),
				true);

		try {
			doit(width, height, color, realFile, formatName, deleted);
		} catch (IOException e) {
			be.more();
			throw new KnownError(e).internalError();
		}
		return true;
	}

	private void doit(int width, int height, int color, File to,
			String formatName, File from) throws IOException {
		BufferedImage bi = ImageIO.read(from);

		Graphics2D gg = bi.createGraphics();

		Color c = new Color(color, false);
		gg.setColor(c);

		gg.fillRect(0, 0, bi.getWidth(), height);
		gg.fillRect(0, bi.getHeight() - height, bi.getWidth(), bi.getHeight());

		gg.fillRect(0, 0, width, bi.getHeight());
		gg.fillRect(bi.getWidth() - width, 0, bi.getWidth(), bi.getHeight());

		gg.dispose();
		ImageIO.write(bi, formatName, to);
	}

	// public static void main(String[] args) throws IOException{
	// border c=new border();
	// c.doit(10, 40, 0, new File("/tmp/ff.jpg"), "jpeg", new
	// File("/tmp/f.jpg"));
	// }

}
