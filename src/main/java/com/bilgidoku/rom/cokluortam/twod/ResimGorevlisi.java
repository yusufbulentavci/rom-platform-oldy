package com.bilgidoku.rom.cokluortam.twod;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.lang.NotImplementedException;

import com.bilgidoku.rom.cokluortam.twod.image.ScaleFilter;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.shared.err.KnownError;

public class ResimGorevlisi extends GorevliDir {
	
	public static final int NO=22;
	
	public static ResimGorevlisi tek(){
		if(tek==null) {
			synchronized (ResimGorevlisi.class) {
				if(tek==null) {
					tek=new ResimGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static ResimGorevlisi tek;
	private ResimGorevlisi() {
		super("Resim", NO);
	}
	
	

	private static final MC mc = new MC(ResimGorevlisi.class);

	private static final Astate fc = mc.c("get-format");
	private static final Astate fce = mc.c("get-format-error");
	private static final Astate fcu = mc.c("get-format-unknown");


	
	/**
	 * Check for the performance Guessing from file extension is possible, Know
	 * image readers by name Find image reader from file extension Check with
	 * image reader...
	 * 
	 * @param o
	 * @return
	 * @throws RomRequestException
	 */
	private ImageReader getImageReader(File o) throws KnownError {
		fc.more();
		try {
			// Create an image input stream on the image
			ImageInputStream iis = ImageIO.createImageInputStream(o);

			// Find all image readers that recognize the image format
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {
				fcu.more(o.getName());
				return null;
			}

			// Use the first reader
			ImageReader reader = (ImageReader) iter.next();

			// Close stream
			iis.close();

			// Return the format name
			return reader;
		} catch (IOException e) {
			fce.more();
			throw err(e);
		}
	}


	public void filter(BufferedImageOp filter, File from, File to, String forceFormat) throws KnownError {
		destur();
		try {
			BufferedImage bi;
			bi = ImageIO.read(from);
			String formatName = (forceFormat == null) ? getImageReader(from).getFormatName() : forceFormat;
			// g = bi.createGraphics();
			// BufferedImage nim = g.getDeviceConfiguration()
			// .createCompatibleImage(bi.getWidth(), bi.getHeight());

			BufferedImage nim = filter.filter(bi, null);
			ImageIO.write(nim, formatName, to);
		} catch (IOException e) {
			throw err(e);
		}

	}


	public void filter(BufferedImageOp filter, File file) {
		// TODO Auto-generated method stub

	}


	public ImgInfo imgInfo(File file) throws KnownError {
		destur();

		ImgInfo ii;
		try {
			ImageReader ir = getImageReader(file);

			ii = new ImgInfo();
			ii.format = ir.getFormatName().toLowerCase();

			try (ImageInputStream in = ImageIO.createImageInputStream(file)) {
				ir.setInput(in);
				ii.width = ir.getWidth(0);
				ii.height = ir.getHeight(0);
			}
			return ii;
		} catch (IOException e) {
			throw err(e);
		}

	}

	private static final Astate _scale = mc.c("scale");


	public void scale(File f, File t, char s) throws KnownError {
		destur();
		_scale.more();

		try {
			ImgInfo i = imgInfo(f);
			int width;
			switch (s) {
			case 't':
				width = 100;
				break;
			case 's':
				width = 240;
				break;
			case 'm':
				width = 460;
				break;
			case 'l':
				width = 800;
				break;
			case 'x':
				width = 1024;
				break;
			default:
				throw new KnownError().badRequest();
			}
			int height = (int) (i.ratio() * width);

			filter(new ScaleFilter(width, height), f, t, null);

		} catch (KnownError e) {
			_scale.failed(e, f, t, s);
			throw err(e);
		}
	}


	public void selfDescribe(JSONObject jo) {
		// TODO Auto-generated method stub

	}


	public boolean compressImage(File from, File to) throws KnownError {
		if(!from.getPath().endsWith(".png"))
			return false;
		
		throw new NotImplementedException();

//		if(to==null){
//			return KosuGorevlisi.tek().exec(null, true, false, true, "pngquant", "-f", "--quality", "80-95", from.getPath());
//		}
//		
//		return KosuGorevlisi.tek().execShell(false, true, "pngquant", "--quality", "80-95", "-o", to.getPath(), from.getPath());
	}


	public boolean draftImage(File from, File to) throws KnownError {
		if(!from.getPath().endsWith(".png"))
			return false;
		
		throw new NotImplementedException();
		
//		if(to==null){
//			return KosuGorevlisi.tek().execShell(false, true, "pngquant", "-f", "--quality", "20-30", from.getPath());
//		}
//		
//		return KosuGorevlisi.tek().execShell(false, true, "pngquant", "--quality", "20-30", "-o", to.getPath(), from.getPath());
	}

}
