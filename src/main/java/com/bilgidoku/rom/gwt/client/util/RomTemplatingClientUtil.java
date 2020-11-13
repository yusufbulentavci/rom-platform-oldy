package com.bilgidoku.rom.gwt.client.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.user.client.Window;

public class RomTemplatingClientUtil {
	
	public static Map<String, String> extractOParam(){
		Map<String, List<String>> pm = Window.Location.getParameterMap();
		Map<String,String> oParams=null;
		for (Entry<String, List<String>> it : pm.entrySet()) {
			if(it.getKey().startsWith("o_")){
				if(oParams==null)
					oParams=new HashMap<String,String>();
				if(it.getValue()==null||it.getValue().size()==0){
					oParams.put(it.getKey(), null);
				}
				oParams.put(it.getKey(), it.getValue().get(0));
			}
		}
		return oParams;
	}

}
