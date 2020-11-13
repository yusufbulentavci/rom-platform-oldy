package com.bilgidoku.rom.site.yerel.data;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;

public class SampleSite {
	private int paletteNo;
	private String headerFont;
	private String textFont;
	private String backImage;
	private String backPattern;
	private String style;
	
	public SampleSite(String style, int paletteNo, String headerFont, String textFont, String backImage, String backPattern) {
		setPaletteNo(paletteNo);
		setHeaderFont(headerFont);
		setTextFont(textFont);
		setBackImage(backImage);
		setBackPattern(backPattern);
		setStyle(style);
	}

	public int getPaletteNo() {
		return paletteNo;
	}

	public void setPaletteNo(int paletteNo) {
		this.paletteNo = paletteNo;
	}

	public String getHeaderFont() {
//		String font = Css.SAFE_FONTS.get(headerFont);
//		if (font != null && !font.isEmpty())
//			return font;
		
//		font = Css.GOOGLE_FONTS.get(headerFont);
//		if (font != null && !font.isEmpty())
//			return font;

//		return Css.SAFE_FONTS.get("Arial");
		return "Arial";
	}

	public void setHeaderFont(String headerFont) {
		this.headerFont = headerFont;
	}

	public String getTextFont() {
		return textFont;
	}

	public void setTextFont(String textFont) {
		this.textFont = textFont;
	}

	public String getBackImage() {
		return backImage;
	}

	public void setBackImage(String backImage) {
		this.backImage = backImage;
	}

	public String getBackPattern() {
		//url("/_local/images/bg/ptn1.png")
		return backPattern;
	}

	public void setBackPattern(String backPattern) {
		this.backPattern = backPattern;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	public String getColor3() {
		String palette = Data.PAGECOLORS.get(paletteNo + "");
		String[] colors = palette.split(",");
		return colors[4];
	}

	public String getHeaderFont(String font) {
		return "";
	}

	public String getTextFont(String font) {
		return "";
	}

	public String getBack() {
		String palette = Data.PAGECOLORS.get(paletteNo + "");
		String[] colors = palette.split(",");
		return colors[0];
	}

	public String getBackMost() {
		String palette = Data.PAGECOLORS.get(paletteNo + "");
		String[] colors = palette.split(",");
		return colors[1];
	}

	public String getFore() {
		String palette = Data.PAGECOLORS.get(paletteNo + "");
		String[] colors = palette.split(",");
		return colors[2];
	}

	public String getForemost() {
		String palette = Data.PAGECOLORS.get(paletteNo + "");
		String[] colors = palette.split(",");
		return colors[3];
	}

	public String getTextColor() {
		String back = getBack();		
		return ClientUtil.getFontColor(back);
	}

	public String getHiliColor() {
		String back = getBack();		
		return ClientUtil.getDarkerFontColor(back);
	}

	public String getWeakColor() {
		String back = getBack();		
		return ClientUtil.getWeakFontColor(back);
	}

	public String getColorOnFore() {
		String back = getFore();		
		return ClientUtil.getFontColor(back);
	}

	public String getColorOnForeMost() {
		String back = getForemost();		
		return ClientUtil.getFontColor(back);
	}

	public String getColorOnColor3() {
		String back = getColor3();		
		return ClientUtil.getFontColor(back);
	}
	
}
