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

public class sharpen extends ImageBase {

	private static final MC mc = new MC(sharpen.class);

	private static final Astate bu = mc.c("undo");

	@Override
	public void undo(HookScope scope) throws KnownError {
		bu.more();
		KurumDosyaGorevlisi.tek().undeleteFile(scope.getHostId(), scope.getUri(), true);
	}

	private static final Astate bd = mc.c("do");
	private static final Astate be = mc.c("sharpen-error");

	@Override
	public boolean hook(HookScope scope) throws KnownError, KnownError {
		bd.more();
		File realFile = KurumDosyaGorevlisi.tek().getFile(scope.getHostId(), scope.getUri(),
				true, true);
		String formatName = getFormatName(realFile);

		File deleted = KurumDosyaGorevlisi.tek().deleteFile(scope.getHostId(), scope.getUri(),
				true);

		try {
			doit(realFile, formatName, deleted);
		} catch (IOException e) {
			be.more();
			throw new KnownError(e).internalError();
		}
		// scope.done();
		return true;
	}

	private static final float[] SHARPEN3x3 = { 0.f, -1.f, 0.f, -1.f, 5.f,
			-1.f, 0.f, -1.f, 0.f };

	private void doit(File to, String formatName, File from) throws IOException {
		BufferedImage bi = ImageIO.read(from);
		Graphics2D g = bi.createGraphics();
		BufferedImage nim = g.getDeviceConfiguration().createCompatibleImage(
				bi.getWidth(), bi.getHeight());

		Kernel kernel = new Kernel(3, 3, SHARPEN3x3);
		ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP,
				null);
		convolve.filter(bi, nim);
		ImageIO.write(nim, formatName, to);
		g.dispose();
	}

	public static void main(String[] args) throws IOException {
		sharpen c = new sharpen();
		c.doit(new File("/tmp/ff.jpg"), "jpeg", new File("/tmp/f.jpg"));
	}

}
