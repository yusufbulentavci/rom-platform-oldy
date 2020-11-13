package com.bilgidoku.rom.web.richweb;


import com.bilgidoku.rom.cokluortam.CokluOrtamGorevlisi;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.internetapi.RichWebApi;
import com.bilgidoku.rom.internetapi.bing.BingApi;
import com.bilgidoku.rom.internetapi.fotolia.FotoliaAccess;
import com.bilgidoku.rom.internetapi.yaymicro.YaymicroApi;

public final class Provider {
	public static final int TLOS = 0;
	public static final int BING = 1;
	public static final int GOOGLE = 2;
	public static final int PIXABAY = 3;
	public static final int PUBLICDOMAINPICTURES = 4;
	public static final int YAYMICRO = 5;
	public static final int FOTOLIA = 6;
	public static final int FREESTOCKPHOTOS = 7;

	private final RichWebApi fotolia;
	private final RichWebApi bing;
	private final RichWebApi yaymicro;
	private final RichWebApi pixabay;
	private final RichWebApi pdp;

	private final RichWebApi[] access;

	private final boolean[] isPublic;
	
	
	
	public Provider(JSONObject provider) throws JSONException{
		fotolia = new FotoliaAccess(provider.getJSONObject("fotolia"));
		bing = new BingApi(provider.getJSONObject("bing"));
		yaymicro = new YaymicroApi(provider.getJSONObject("yaymicro"));
		pixabay=CokluOrtamGorevlisi.tek().local(PIXABAY);
		pdp=CokluOrtamGorevlisi.tek().local(PUBLICDOMAINPICTURES);

		access = new RichWebApi[] { null, bing, null, pixabay, pdp, yaymicro, fotolia };

		isPublic = new boolean[] { false, false, false, true, true, false, false };

		
		
	}
	
	
	

	public RichWebApi getAccess(int p_pr) {
		if (p_pr > access.length)
			return null;
		
		
		
		return access[p_pr];
	}

	public boolean isPublic(Integer pr) {
		return isPublic[pr];
	}

}
