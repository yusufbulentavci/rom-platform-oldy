package com.bilgidoku.rom.site.yerel.boxing;

import com.bilgidoku.rom.gwt.client.util.Layer;
import com.bilgidoku.rom.gwt.client.util.common.HelpButton;
import com.bilgidoku.rom.gwt.client.util.help.Helpy;
import com.bilgidoku.rom.site.yerel.common.ImageAnchor;
import com.bilgidoku.rom.site.yerel.pagedlgs.BasePanel;

public class BoxerMenuButton extends ImageAnchor implements HelpButton {
	
	private Helpy helpy;
	private BasePanel menu;
	private String activeImg;
	private String img;
	private boolean isActive = false;

	public static class Builder {

		private final String img;
		private final String name;
		
		private String title;
		private String helpUri;
		private String[] helpUris;
		private BasePanel menu;
		private String activeImg;
		

		public Builder(String uri, String name) {
			this.img = uri;
			this.name = name;
		}

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setHelpUri(String deger) {
			this.helpUri = deger;
			return this;
		}

		public Builder setHelpUris(String[] deger) {
			this.helpUris = deger;
			return this;
		}

		public Builder setMenu(BoxerMenu menu) {
			this.menu = menu;
			return this;
		}

		public Builder setActiveImage(String img) {
			this.activeImg = img;
			return this;
		}

		public BoxerMenuButton build() {
			return new BoxerMenuButton(this);
		}
	}

	public BoxerMenuButton(Builder builder) {
		super(builder.img);
		this.img = builder.img;
		this.setName(builder.name);
		this.addStyleName("site-fixed site-gear");
		this.getElement().getStyle().setZIndex(Layer.layer1);

		
		if (builder.title != null)
			this.setTitle(builder.title);

		if (builder.helpUri != null) {
			String[] arr = { builder.helpUri };
			this.helpy = new Helpy(builder.title, arr, this);
		}
		
		if (builder.helpUris != null) {
			if (this.helpy != null)
				this.helpy.setUris(builder.helpUris);
			else
				this.helpy = new Helpy(builder.title, builder.helpUris, this);
				
		}
		
		if (builder.menu != null)
			this.menu = builder.menu;
		
		if (builder.activeImg != null)
			this.activeImg = builder.activeImg;

	}

	@Override
	public Helpy getHelpy() {
		return helpy;
	}

	public BasePanel getMenu() {
		return menu;
	}

	public void activate() {
		isActive = true;
		this.addStyleName("site-gearselected");
		changeResource(this.activeImg);
		
		if (this.menu != null)
			this.menu.setVisible(true);
	}

	public void deactivate() {
		isActive = false;
		this.removeStyleName("site-gearselected");
		changeResource(this.img);
		if (this.menu != null)
			this.menu.setVisible(false);
	}	
	
	public boolean isActive() {
		return isActive;
	}

}
