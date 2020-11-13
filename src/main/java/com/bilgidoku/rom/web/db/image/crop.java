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

public class crop extends ImageBase{
	private static final MC mc=new MC(crop.class);

	private static final Astate bu=mc.c("undo");


	@Override
	public void undo(HookScope scope) throws KnownError{
		bu.more();
		KurumDosyaGorevlisi.tek().undeleteFile(scope.getHostId(), scope.getUri(), true);
	}

	private static final Astate bd=mc.c("do");
	private static final Astate be=mc.c("crop-error");
	
	@Override
	public boolean hook(HookScope scope) throws KnownError, ParameterError{
		bd.more();
		Integer left=scope.getIntParam("left", true);
		Integer top=scope.getIntParam("top", true);
		Integer right=scope.getIntParam("right", true);
		Integer bottom=scope.getIntParam("bottom", true);
		
		
		
		File realFile=KurumDosyaGorevlisi.tek().getFile(scope.getHostId(), scope.getUri(), true, true);
		String formatName=getFormatName(realFile);

		File deleted=KurumDosyaGorevlisi.tek().deleteFile(scope.getHostId(), scope.getUri(), true);
		
		try {
			doit(left, top, right, bottom, realFile, formatName, deleted);
		} catch (IOException e) {
			be.more("IO Exception; hostId: "+scope.getHostId()+" self:"+scope.getUri());
			throw new KnownError(e).internalError();
		}
//		scope.done();
		return true;
	}


	private void doit(int left, int top, int right, int bottom,
			File to, String formatName, File from) throws IOException {
		BufferedImage bi = ImageIO.read(from);
		
		BufferedImage cropped=bi.getSubimage(left, top, bi.getWidth()-(left+right), bi.getHeight()-(top+bottom));
		ImageIO.write(cropped,formatName,to);
	}
	
	public static void main(String[] args) throws IOException{
		crop c=new crop();
		c.doit(4, 50, 7, 200, new File("/tmp/ff.jpg"), "jpeg", new File("/tmp/f.jpg"));
	}

}
