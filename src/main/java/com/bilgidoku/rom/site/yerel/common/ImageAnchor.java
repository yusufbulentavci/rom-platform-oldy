package com.bilgidoku.rom.site.yerel.common;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Image;

public class ImageAnchor extends Anchor {
	public ImageAnchor() {
		super();
	}

	public ImageAnchor(String uri) {
		super();
		changeResource(uri);
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