package com.bilgidoku.rom.web.db.image;

import java.awt.Graphics2D;
import java.awt.Image;
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

public class resize extends ImageBase{
	
	private static final MC mc=new MC(resize.class);

	private static final Astate bu=mc.c("undo");

	@Override
	public void undo(HookScope scope) throws KnownError {
		bu.more();
		KurumDosyaGorevlisi.tek().undeleteFile(scope.getHostId(), scope.getUri(), true);
	}

	private static final Astate bd=mc.c("do");
	private static final Astate be=mc.c("resize-error");

	@Override
	public boolean hook(HookScope scope) throws KnownError, ParameterError{
		bd.more();
		Integer x=scope.getIntParam("x", true);
		Integer y=scope.getIntParam("y", true);
		
		
		
		File realFile=KurumDosyaGorevlisi.tek().getFile(scope.getHostId(), scope.getUri(), true, true);
		String formatName=getFormatName(realFile);

		File deleted=KurumDosyaGorevlisi.tek().deleteFile(scope.getHostId(), scope.getUri(), true);
		
		try {
			doit(x, y, realFile, formatName, deleted);
		} catch (IOException e) {
			be.more();
			throw new KnownError(e).internalError();
		}
//		scope.done();
		return true;
	}


	private void doit(int x, int y, 
			File to, String formatName, File from) throws IOException {
		BufferedImage bi = ImageIO.read(from);
		Graphics2D gg = bi.createGraphics();
		Image pi = bi.getScaledInstance(x, y, Image.SCALE_SMOOTH);
		
		BufferedImage nim = gg.getDeviceConfiguration().createCompatibleImage(x, y);
		
		Graphics2D g = nim.createGraphics();
		g.drawImage(pi,0,0,null);
		g.dispose();
		gg.dispose();
		
		ImageIO.write(nim,formatName,to);
	}
	
	public static void main(String[] args) throws IOException{
		resize c=new resize();
		c.doit(100, 100, new File("/tmp/ff.jpg"), "jpeg", new File("/tmp/f.jpg"));
	}

}
