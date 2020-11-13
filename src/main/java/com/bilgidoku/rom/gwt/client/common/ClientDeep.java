package com.bilgidoku.rom.gwt.client.common;



public class ClientDeep {
	public static ClientDeepNotification n;
	
	static void cookieChanged(){
		if(n!=null)
			n.cookieChanged();
	}
	

}
