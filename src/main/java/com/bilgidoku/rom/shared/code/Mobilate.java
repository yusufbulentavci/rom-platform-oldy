package com.bilgidoku.rom.shared.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mobilate {
	
	
	int maxScrWidth=0;
	int maxScrHeight=0;
	
	int lastScrHeight=0;
	List<Mobil> mobils;
	private Integer srcHeight;
	
	public Mobilate(int bw, List<Code> children, Integer height) {
		srcHeight=height;
		this.mobils=new ArrayList<Mobil>();
		for (Code code : children) {
			if(!code.visibleInMobile)
				continue;
			Mobil mc = new Mobil(code);
			mobils.add(mc);
			if(maxScrWidth<mc.scrWidth()){
				maxScrWidth=mc.scrWidth();
			}
			if(maxScrHeight<mc.scrHeight()){
				maxScrHeight=mc.scrHeight();
			}
		}
		lastScrHeight=maxScrHeight;
		Collections.sort(mobils);
		
		for (Mobil mobil : mobils) {
			if(mobil.isToNewLine()){
				lastScrHeight=mobil.newLine(mobil, lastScrHeight);
			}
		}
		
		for(Mobil mobil: mobils){
			mobil.applyNew();
		}
	}

	public List<Mobil> getMobils() {
		return mobils;
	}
	
	
	public Integer newHeight(){
		if(lastScrHeight<srcHeight)
			return srcHeight;
		
		return lastScrHeight;
	}
	
	
	
}
