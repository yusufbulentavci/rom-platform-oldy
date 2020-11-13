package com.bilgidoku.rom.gwt.client.util.com;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Visibility;

public class PassEventHandler {
	
//	public static void passEvent() {
//		BodyElement html = Document.get().getBody();
//		List<Element> romans=new ArrayList<Element>();
//		findClasses(html, "roman", romans);
//		
//		for (Element element : romans) {
//			
//			String romanpass=element.getAttribute("romanpass");
//			if(romanpass==null){
//				element.setAttribute("romanpass", "1");
//				continue;
//			}
//			
//			String cls=element.getClassName();
//			char c=cls.charAt(5);
//			handle(c, element);
//		}
//	}
	
	private static void handle(char c, Element element) {
		switch(c){
		case 'h':
			handleHide(element);
			break;
		case 'c':
			handleClear(element);
			break;
		case 'r':
			handleRemove(element);
			break;
		case 'z':
			handleZoom(element);
			break;
		}
	}

	private static void handleRemove(Element element) {
		element.removeFromParent();
	}

	private static void handleZoom(Element element) {
		
	}

	private static void handleClear(Element element) {
		element.setInnerHTML("");
	}

	private static void handleHide(Element element) {
		element.getStyle().setVisibility(Visibility.VISIBLE.HIDDEN);
	}

	public static void findClasses(Element par, String cls, List<Element> ret) {
		String elcls = par.getClassName();
		
		if(elcls!=null && elcls.startsWith(cls)){
			if(elcls.length()==6)
				ret.add(par);
		}
		
		if(par.getChildCount()==0)
			return;
		
		
		
		for(int i=0; i<par.getChildCount(); i++){
			try {
				Element el = (Element)par.getChild(i);
				findClasses(el, cls, ret);
		     } catch (ClassCastException exception) {
		     }
		}
	}

}
