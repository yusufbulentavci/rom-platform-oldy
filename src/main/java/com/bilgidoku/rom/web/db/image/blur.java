package com.bilgidoku.rom.web.db.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.kurum.KurumDosyaGorevlisi;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;

public class blur extends ImageBase {
	private static final MC mc = new MC(blur.class);

	private static final Astate bu = mc.c("undo");

	@Override
	public void undo(HookScope scope) throws KnownError {
		bu.more();
	}

	private static final Astate bd = mc.c("do");
	private static final Astate be = mc.c("blue-error");

	@Override
	public boolean hook(HookScope scope) throws KnownError {
		bd.more();
		File realFile = KurumDosyaGorevlisi.tek().getFile(scope.getHostId(), scope.getUri(),
				true, true);
		String formatName = getFormatName(realFile);

		File deleted = KurumDosyaGorevlisi.tek().deleteFile(scope.getHostId(), scope.getUri(),
				true);
		doit(realFile, formatName, deleted);
		return true;
	}

	private static final float[] BLUR3x3 = { 0.1f, 0.1f, 0.1f, 0.1f, 0.2f,
			0.1f, 0.1f, 0.1f, 0.1f };

	private void doit(File to, String formatName, File from) throws KnownError {
		BufferedImage bi;
		try {
			bi = ImageIO.read(from);
			Graphics2D g = bi.createGraphics();
			BufferedImage nim = g.getDeviceConfiguration()
					.createCompatibleImage(bi.getWidth(), bi.getHeight());

			Kernel kernel = new Kernel(3, 3, BLUR3x3);
			ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,
					null);
			convolve.filter(bi, nim);
			ImageIO.write(nim, formatName, to);
			g.dispose();
		} catch (IOException e) {
			be.more("to:" + to.getName() + " from:" + from.getName()
					+ " formatName:" + formatName);
			throw new KnownError(e).internalError();
		}
	}

	// public static void main(String[] args) throws IOException{
	// blur c=new blur();
	// c.doit(new File("/tmp/ff.jpg"), "jpeg", new
	// File("/tmp/f.jpg"));
	// }

}
