package com.bilgidoku.rom.gwt.client.util.com;
/**
 * name: frame ici/yerel ise _ alt cizgi ile baslar
 * 		
 * cmds: islenen komutlardir
 * 		* ile basliyorsa bu bir komuttur. 
 * depends: diger komponentlere ve state'e olan ihtiyactir
 * feature: key,value seklinde tutulur. Ornegin userneed->local seklinde
 * @author rompg
 */
public class CompInfo {
	public final String name;
	public final int weight;
	public final String[] cmds;
	public final String[] depends;
	public final String[] feature;
	
	public CompInfo(String name, int weight, String[] cmds, String[] depends, String[] feature) {
		super();
		this.name = name;
		this.weight = weight;
		this.cmds = cmds;
		this.feature = feature;
		this.depends = depends;
//		if(feature!=null)
//			Sistem.outln("FETSIZ:"+feature.length);
			
		if(feature!=null && feature.length%2==1){
			throw new RuntimeException("FEATURE SIZE must be factor of two. comp:"+name);
		}
		
//		Assert.error(b, s);
	}
	
	public String askFeature(String key){
		if(feature==null)
			return null;
		
		for(int i=0; i<feature.length; i=i+2){
			if(feature[i].equals(key))
				return feature[i+1];
		}
		return null;
	}

}
