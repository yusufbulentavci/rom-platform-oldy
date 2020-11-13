package com.bilgidoku.rom.gwt.client.util.browse.image.search;


public final class Provider {
	public static final int TLOS = 0;
	public static final int BING = 1;
	public static final int GOOGLE = 2;
	public static final int PIXABAY = 3;
	public static final int PUBLICDOMAINPICTURES = 4;
	public static final int YAYMICRO = 5;
	public static final int FOTOLIA = 6;

	private static final boolean[] isPublic = new boolean[] { false, true, true, true, true, false, false };

	public static boolean isPublic(Integer pr) {
		return isPublic[pr];
	}

}