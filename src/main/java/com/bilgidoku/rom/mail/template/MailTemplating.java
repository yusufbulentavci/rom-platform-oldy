package com.bilgidoku.rom.mail.template;

import java.util.Map;
import java.util.Map.Entry;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;

import com.bilgidoku.rom.shared.err.KnownError;

public class MailTemplating {

	private STGroup group = new STGroupDir("mailTemplates", "UTF-8", '$', '$');

	public String[] doit(String lang, String templateName, Map<String, Object> params) throws KnownError {
		ST subject = group.getInstanceOf(lang + "-" + templateName+"-subject");
		if(subject==null){
			throw new KnownError("Template subject not found:"+lang + "-" + templateName);
		}
		ST body = group.getInstanceOf(lang + "-" + templateName);
		if(body==null){
			throw new KnownError("Template body not found:"+lang + "-" + templateName);
		}
		if (params != null) {
			Map<String, Object> ba = body.getAttributes();
			Map<String, Object> sa = subject.getAttributes();
			for (Entry<String, Object> it : params.entrySet()) {
				
				if(sa!=null && sa.containsKey(it.getKey())){
					subject.add(it.getKey(), it.getValue());
				}
				if(ba!=null && ba.containsKey(it.getKey())){
					body.add(it.getKey(), it.getValue());
				}

			}
		}
		
		String[] ret=new String[2];
		ret[0]=subject.render();
		ret[1]=body.render();
		return ret;
	}

	public static void main(String[] args) {
		System.out.println("hey");
		
		STGroup group = new STGroupDir("mailTemplates", "UTF-8", '$', '$');
		
		String lang="tr";
		String templateName="ContactForm";
		ST subject = group.getInstanceOf(lang + "-" + templateName+"-subject");
		if(subject==null){
			throw new RuntimeException("Template subject not found:"+lang + "-" + templateName);
		}
	}
	
}
