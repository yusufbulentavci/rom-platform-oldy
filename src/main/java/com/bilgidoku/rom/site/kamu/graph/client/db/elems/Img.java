package com.bilgidoku.rom.site.kamu.graph.client.db.elems;

import com.bilgidoku.rom.min.geo.Point;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.bilgidoku.rom.site.kamu.graph.client.db.GraphicWaiting;
import com.bilgidoku.rom.site.kamu.graph.client.draw.CanvasContext;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class Img extends Elem {
	
	private ImageElement imageElement;
	private Image image;

	public Img(String img, int x, int y, int dx, int dy) {
		super('i', null, x, y);
		setRot(0d);
		setImg(img);
		setDp(new Point(dx,dy));
	}

	public Img(JSONObject el) throws RunException {
		super(Elem.IMAGE, el);
	}

	@Override
	public void draw(final CanvasContext c) {
		transformMatrix(c);
		c.beginPath();
		final Image image = new Image(getImg());
		imageElement=(ImageElement) image.getElement().cast();
		c.drawImage(imageElement, getDp().getX(), getDp().getY());
		c.closePath();
		
		c.resetTransform();
	}
	
	public void future(final GraphicWaiting waiting){
		
		if(imageElement!=null && imageElement.getSrc().equals(getImg()))
			return;
		waiting.graphicWait();
		image = new Image(getImg());
		image.setVisible(false);
		RootPanel.get().add(image);
		image.addLoadHandler(new LoadHandler() {
			@Override
			public void onLoad(LoadEvent event) {
				imageElement = (ImageElement) image.getElement().cast();
				waiting.graphicReady();
			}
		});
	}
	
	public void reloadImg(String href){
		imageElement=null;
		setImg(href);
	}

	
}
