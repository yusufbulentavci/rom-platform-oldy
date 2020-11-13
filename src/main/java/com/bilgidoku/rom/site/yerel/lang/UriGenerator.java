package com.bilgidoku.rom.site.yerel.lang;

import java.util.HashMap;
import java.util.Map;

public class UriGenerator {
	static final Map<String,Character[]> MAPPINGS=new HashMap<String,Character[]>();
	static final Character[] TR_MAPPINGS={
		new Character('ğ'),new Character('g'),
		new Character('ş'),new Character('s'),
		new Character('ı'),new Character('i'),
		new Character('ü'),new Character('u'),
		new Character('ö'),new Character('o'),
		new Character('ç'),new Character('c')};
	
	static final Character[] EN_MAPPINGS={
	};

	static{
		MAPPINGS.put("tr",TR_MAPPINGS);
		MAPPINGS.put("en",EN_MAPPINGS);
	}
	
	
	static public String generateUri(String contentLang,String name, boolean isPath){
		StringBuilder sb=new StringBuilder();
		String dn=name.toLowerCase().trim();
		Character[] m=MAPPINGS.get(contentLang);
		for(int i=0;i<dn.length();i++){
			char c=dn.charAt(i);
			if(c>='a'&&c<='z'){
				sb.append(c);
				continue;
			}
			if(isPath && c=='/'){
				sb.append('/');
				continue;
			}
			boolean done=false;
			for(int j=0;j<m.length;j+=2){
				if(m[j]==c){
					sb.append(m[j+1]);
					done=true;
					break;
				}
			}
			if(!done)
				sb.append('_');
		}
		return sb.toString();
	}
	
}
