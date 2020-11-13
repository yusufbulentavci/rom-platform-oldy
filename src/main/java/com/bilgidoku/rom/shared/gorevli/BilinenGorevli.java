package com.bilgidoku.rom.shared.gorevli;

import java.lang.annotation.Annotation;

public class BilinenGorevli {
	int id;
	private Class cls;
	
	public BilinenGorevli(int i, Class c) {
		id=i;
		cls=c;
	}
	
	
	public Annotation anotasyon(Class anotasyon) {
		Annotation[] as = cls.getAnnotations();
		for (Annotation an : as) {
			
			if (an.annotationType().equals(anotasyon)) {
				return an;
			}
		}
		return null;
	}
	
	public Class getCls() {
		return cls;
	}

}
