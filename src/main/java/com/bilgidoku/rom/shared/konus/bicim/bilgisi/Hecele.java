package com.bilgidoku.rom.shared.konus.bicim.bilgisi;

import java.util.ArrayList;
import java.util.List;

public class Hecele {
	

	public static List<String> yap(String kelime){
		List<String> ret=new ArrayList<>();

		boolean[] bmp = olUnlumu(kelime);
		for(int i=0;i<bmp.length;i++) {
			int enyakinUnlu = enyakinUnlu(bmp, i);
			int sonraki=-1;
			if(enyakinUnlu!=-1) {
				sonraki=enyakinUnlu(bmp, enyakinUnlu+1);
			}
			if(enyakinUnlu==-1) {
				break;
			}
			if(sonraki==-1) {
				ret.add(kelime.substring(i));
				break;
			}
			
			if(sonraki-enyakinUnlu<3) {
				ret.add(kelime.substring(i, enyakinUnlu+1));
				i=enyakinUnlu;
			}else  {
				ret.add(kelime.substring(i, sonraki-1));
				i=sonraki-2;
			}
		}
		
		
		return ret;
	}

	private static int enyakinUnlu(boolean[] bmp, int ind) {
		for(int i=ind; i< bmp.length; i++) {
			if(bmp[i])
				return i;
		}
		return -1;
	}

	static boolean[] olUnlumu(String kelime) {
		boolean[] ret = new boolean[kelime.length()];
		for (int i = 0; i < kelime.length(); i++) {
			ret[i] = unlumu(kelime.charAt(i));
		}
		
		return ret;
	}

	static private boolean unlumu(char c) {
		return Unlu.unlumu(c);
	}
	
	

}
