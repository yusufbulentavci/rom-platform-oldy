package com.bilgidoku.rom.web.db.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.bilgidoku.rom.min.geo.Geo;

public class MakeTransparent {
	static void doit(int x, int y, File to, String formatName, File from, int kardeslik, int[] excludeCircles) throws IOException {
		BufferedImage bi = ImageIO.read(from);

		int cl = bi.getRGB(x, y);
		final Color color = new Color(cl);
		int redMin = kardeslik(color.getRed(), -kardeslik);
		int redMax = kardeslik(color.getRed(), kardeslik);
		int greenMin = kardeslik(color.getGreen(), -kardeslik);
		int greenMax = kardeslik(color.getGreen(), kardeslik);
		int blueMin = kardeslik(color.getBlue(), -kardeslik);
		int blueMax = kardeslik(color.getBlue(), kardeslik);

		ImageFilter filter = new RGBImageFilter() {

//			public int markerRGB = color.getRGB() | 0xFF000000;

			public final int filterRGB(int x, int y, int rgb) {
				
				if(excludeCircles!=null){
					for(int i=0; i<excludeCircles.length; i+=3){
						int cx = excludeCircles[i];
						int cy = excludeCircles[i+1];
						int cr = excludeCircles[i+2];
						
						if(Geo.insideCircle(cx, cy, cr, x, y)){
							return rgb;
						}
					}
				}
				
				int red = (rgb >> 16) & 0xFF;
				int green = (rgb >> 8) & 0xFF;
				int blue = (rgb >> 0) & 0xFF;

				if (red >= redMin && red <= redMax && green >= greenMin && green <= greenMax && blue >= blueMin
						&& blue <= blueMax) {
					// Mark the alpha bits as zero - transparent
					return 0x00FFFFFF & rgb;
				} else {
					// nothing to do
					return rgb;
				}

				// if ((rgb | 0xFF000000) == markerRGB) {
				// // Mark the alpha bits as zero - transparent
				// return 0x00FFFFFF & rgb;
				// } else {
				// // nothing to do
				// return rgb;
				// }
			}
		};

		ImageProducer ip = new FilteredImageSource(bi.getSource(), filter);

		Image trasparent = Toolkit.getDefaultToolkit().createImage(ip);

		BufferedImage toFileImage = imageToBufferedImage(trasparent);

		ImageIO.write(toFileImage, formatName, to);
	}

	private static int kardeslik(int red, int kardeslik) {
		red = red + kardeslik;
		if (red > 255)
			return 255;

		if (red < 0)
			return 0;

		return red;
	}

	private static BufferedImage imageToBufferedImage(Image image) {

		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();

		return bufferedImage;

	}

//	public static void main(String[] args) throws IOException {
//		MakeTransparent c = new MakeTransparent();
//		c.doit(1, 1, new File("/home/avci/Downloads/from-transparent.png"), "png",
//				new File("/home/avci/Downloads/from.png"), 5);
//	}

}
