package com.bilgidoku.rom.gwt.client.widgets;

import com.bilgidoku.rom.gwt.client.util.browse.OneBrowseImages;
import com.bilgidoku.rom.gwt.client.util.browse.image.BrowseCallback;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ImageBox extends Composite {
	private SimplePanel cell = new SimplePanel();
	private String imgPath = "";

	public ImageBox() {
		initWidget(cell);
		setImage(null);
	}

	private Widget getImageCell(final String imageUri) {
		this.imgPath = imageUri;
		Widget img;
		if (imageUri == null || imageUri.isEmpty() || imageUri.trim().isEmpty()) {
			img = new SimplePanel();
			img.setSize("50px", "50px");
			img.getElement().getStyle().setBackgroundColor("#D6D2D0");
			img.setTitle("Değiştirmek için tıklayın");
		} else {
			img = new Image(imageUri);
			// img.setHeight("50px");
			img.setWidth("50px");
			img.setTitle(imageUri);
		}
		
		img.getElement().getStyle().setCursor(Cursor.POINTER);

		img.addDomHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				OneBrowseImages.showMe(new BrowseCallback() {					
					@Override
					public void selected(String selectedImg) {
						if (selectedImg != null) {
							ImageBox.this.setImage(selectedImg);
							ImageBox.this.fireEvent(new InputChanged("img", selectedImg));
						}						
					}
				});
			}
		}, ClickEvent.getType());

		HorizontalPanel hp = new HorizontalPanel();
		hp.setStyleName("site-img");
		hp.add(img);

		hp.getElement().getStyle().setBackgroundColor("white");
		hp.getElement().getStyle().setBorderColor("#CCCCCC");
		hp.getElement().getStyle().setBorderWidth(1, Unit.PX);
		hp.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		hp.getElement().getStyle().setPadding(4, Unit.PX);

		return hp;
	}

	public void setImage(String icon) {
		cell.clear();
		cell.add(getImageCell(icon));
	}

	public String getImgPath() {
		return imgPath;
	}

	public HandlerRegistration addChangedHandler(InputChangedHandler handler) {
		return this.addHandler(handler, InputChanged.TYPE);
	}

}
