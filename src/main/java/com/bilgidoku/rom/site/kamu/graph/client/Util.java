package com.bilgidoku.rom.site.kamu.graph.client;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class Util {

	

	public static String imageItemHTML(ImageResource imageProto, String title) {
		if(title==null){
			return AbstractImagePrototype.create(imageProto).getHTML();
		}
		return AbstractImagePrototype.create(imageProto).getHTML() + " " + title;
	}

}
