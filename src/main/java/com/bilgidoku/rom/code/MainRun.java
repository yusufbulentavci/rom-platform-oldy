package com.bilgidoku.rom.code;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.pg.dict.HttpCallServiceDeclare;
import com.bilgidoku.rom.shared.gorevli.BilinenGorevli;
import com.bilgidoku.rom.shared.gorevli.GorevliSozlugu;


public class MainRun {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		List<Class> hostc=new ArrayList<Class>();
		List<Class> dirc=new ArrayList<Class>();
		
		List<BilinenGorevli> http = GorevliSozlugu.tek().anotasyonlariGetir(HttpCallServiceDeclare.class);
		for (BilinenGorevli bg : http) {
			Class class1 = bg.getCls();
			Annotation[] as = class1.getAnnotations();
			for (Annotation an : as) {
				if (an instanceof HttpCallServiceDeclare) {
					HttpCallServiceDeclare hd = (HttpCallServiceDeclare) an;
					System.out.println("HTTP:"+class1.getName()+" direct:"+hd.direct());
				
					if(((HttpCallServiceDeclare) an).direct()){
						dirc.add(class1);
					}else{
						hostc.add(class1);
					}
				
				}
			}
			
		}
		
		CodeGen cg=new CodeGen(hostc,dirc);
		cg.start();
	}

}
