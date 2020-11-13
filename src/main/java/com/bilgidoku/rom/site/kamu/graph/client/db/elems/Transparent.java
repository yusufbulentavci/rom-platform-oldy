package com.bilgidoku.rom.site.kamu.graph.client.db.elems;

import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.bilgidoku.rom.site.kamu.graph.client.draw.CanvasContext;
import com.google.gwt.canvas.dom.client.ImageData;

public class Transparent extends Elem {

	public Transparent() {
		super(TRANSPARENT, null, 0, 0);
		this.setHidden(true);
	}

	@Override
	public void draw(final CanvasContext c) {
		
		c.applyRGBFilter(new RomRGBAImageFilter(){

			@Override
			public int filterRGBA(int x, int y, int rgba) {

				return 0;
			}
			
			
		});
		
	

	}

}
