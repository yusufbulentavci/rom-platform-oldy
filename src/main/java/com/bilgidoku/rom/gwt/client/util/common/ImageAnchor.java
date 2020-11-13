package com.bilgidoku.rom.gwt.client.util.common;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Image;

public class ImageAnchor extends Anchor {

	public ImageAnchor() {
		super();
	}

	public ImageAnchor(String string) {
		super();		
		changeResource(string);
	}

	public void changeResource(String imageResource) {
		Image img = new Image(imageResource);		
		this.getElement().removeAllChildren();
		this.getElement().appendChild(img.getElement());
		
	}
	
	public void changeResource(ImageResource imageResource) {
		Image img = new Image(imageResource);		
		this.getElement().removeAllChildren();
		this.getElement().appendChild(img.getElement());
		
	}

}