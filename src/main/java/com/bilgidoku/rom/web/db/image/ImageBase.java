package com.bilgidoku.rom.web.db.image;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.dict.BeforeHook;
import com.bilgidoku.rom.shared.err.KnownError;

public abstract class ImageBase extends BeforeHook {
	private static final MC mc = new MC(ImageBase.class);




	// public final static String JPEG = "jpeg";
	// public final static String JPG = "jpg";
	// public final static String BMP = "bmp";
	// public final static String PNG = "png";
	// public final static String GIF = "gif";

	private static final Astate fc = mc.c("get-format");
	private static final Astate fce = mc.c("get-format-error");
	private static final Astate fcu = mc.c("get-format-unknown");

	protected String getFormatName(File o) throws KnownError {
		fc.more();
		try (ImageInputStream iis = ImageIO.createImageInputStream(o);) {
			// Create an image input stream on the image

			// Find all image readers that recognize the image format
			Iterator iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {
				fcu.more(o.getName());
				throw new KnownError().notImplemented();
			}

			// Use the first reader
			ImageReader reader = (ImageReader) iter.next();

			// Return the format name
			return reader.getFormatName();
		} catch (IOException e1) {
			throw new KnownError(e1).internalError();
		}

	}
}
