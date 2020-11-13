package com.bilgidoku.rom.cokluortam.twod;

public class ImgInfo {
	String format;
	int width;
	int height;
	
	public float ratio(){
		return (float)height/width;
	}
}
