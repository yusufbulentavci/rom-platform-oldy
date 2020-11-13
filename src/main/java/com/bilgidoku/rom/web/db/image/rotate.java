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
import com.bilgidoku.rom.shared.err.ParameterError;

public class rotate extends ImageBase {

	private static final MC mc=new MC(resize.class);

	private static final Astate bu=mc.c("undo");
	@Override
	public void undo(HookScope scope) throws KnownError  {
		bu.more();
		KurumDosyaGorevlisi.tek().undeleteFile(scope.getHostId(), scope.getUri(), true);
	}
	
	private static final Astate bd=mc.c("do");
	private static final Astate be=mc.c("rotate-error");
	@Override
	public boolean hook(HookScope scope) throws KnownError, ParameterError {
		bd.more();
		Integer angle = scope.getIntParam("angle", true);

		File realFile = KurumDosyaGorevlisi.tek().getFile(scope.getHostId(), scope.getUri(),
				true, true);
		String formatName = getFormatName(realFile);
		

		File deleted = KurumDosyaGorevlisi.tek().deleteFile(scope.getHostId(), scope.getUri(),
				true);

		try {
			doit(angle, realFile, formatName, deleted);
		} catch (IOException e) {
			be.more();
			throw new KnownError(e).internalError();
		}
		return true;
	}

//	private void doit(int angle, File to, String formatName, File from)
//			throws IOException {
//		double theta = Math.toRadians(angle);
//
//		BufferedImage bufferedImage = ImageIO.read(from);
//
//		AffineTransform tx = new AffineTransform();
//		tx.rotate(theta, bufferedImage.getWidth() / 2,
//				bufferedImage.getHeight() / 2);
//
//		AffineTransformOp op = new AffineTransformOp(tx,
//				AffineTransformOp.TYPE_BILINEAR);
//		bufferedImage = op.filter(bufferedImage, null);
//		ImageIO.write(bufferedImage, formatName, to);
//	}

//	private void doit(int angle, File to, String formatName, File from)
//			throws IOException {
//		BufferedImage bi = ImageIO.read(from);
//		AffineTransform tr = new AffineTransform();
//		double theta = Math.toRadians(angle);
//		tr.rotate(theta, 0, 0);
//
//		double sin = Math.abs(Math.sin(theta)), cos = Math.abs(Math.cos(theta));
//		int w = bi.getWidth(), h = bi.getHeight();
//		int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math
//				.floor(h * cos + w * sin);
//		
////		int dx=w-neww;
////		int dy=h-newh;
//		int dx=(neww<0)?neww:0;
//		int dy=(newh<0)?newh:0;
//		tr.translate(dx, dy);
//		AffineTransformOp op = new AffineTransformOp(tr,
//				AffineTransformOp.TYPE_BILINEAR);
//		syso("w:"+w+","+h);
//		syso("new:"+neww+","+newh);
//
//		BufferedImage result = bi.createGraphics().getDeviceConfiguration()
//				.createCompatibleImage(neww, newh);
//		result = op.filter(bi, result);
//		ImageIO.write(result, formatName, to);
//	}
	
	private void doit(int angle, File to, String formatName, File from)
			throws IOException {
		BufferedImage bi = ImageIO.read(from);
		int width = bi.getWidth();
		int height = bi.getHeight();
 
		BufferedImage rotated = new BufferedImage(height, width, bi.getType());
 
		if(angle==90){
			for(int i=0; i<width; i++)
				for(int j=0; j<height; j++)
					rotated.setRGB(j, width-i-1, bi.getRGB(i, j));
		}else{
			for(int i=0; i<width; i++)
				for(int j=0; j<height; j++)
					rotated.setRGB(height-1-j, i, bi.getRGB(i, j));
		}
		
		
		ImageIO.write(rotated, formatName, to);
	}

	public static void main(String[] args) throws IOException {
		rotate c = new rotate();
		c.doit(-90, new File("/tmp/ff.jpg"), "jpeg", new File("/tmp/f.jpg"));
	}

}
