package com.bilgidoku.rom.site.kamu.graph.client.ui;

import com.bilgidoku.rom.site.kamu.graph.client.images.Images;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;

public class ImageSelector<T> extends Composite implements ClickHandler {
	private final SimplePanel showing = new SimplePanel();
	private SimpleChangeListener listener;
	private int current;
	private Image currentImage;
	private boolean selecting = false;
	
	private DialogBox dlg = null;
	private final int[] values = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
	
	private final Images imgs = GWT.create(Images.class);

	private final Image[] images = new Image[] { new Image(imgs.line_width1()), new Image(imgs.line_width2()),
			new Image(imgs.line_width3()), new Image(imgs.line_width4()), new Image(imgs.line_width5()),
			new Image(imgs.line_width6()), new Image(imgs.line_width7()), new Image(imgs.line_width8()) };


	public ImageSelector(int current) {
		for (Image i : images) {
			i.addClickHandler(this);
		}
		this.setValue(current);
		this.initWidget(showing);
	}

	public void setChangeListener(SimpleChangeListener t) {
		this.listener = t;
	}

	public void setValue(int c) {
		Image img = getCurrentImage(c);
		if (img == null)
			return;
		this.current = c;
		this.currentImage = img;
		showing.setWidget(currentImage);
	}

	private Image getCurrentImage(Integer c) {
		for (int i = 0; i < values.length; i++) {
			Integer t = values[i];
			if (t.equals(c))
				return images[i];
		}
		return null;
	}

//	public SimplePanel getShowing() {
//		return showing;
//	}

	@Override
	public void onClick(ClickEvent event) {
		if (selecting) {
			int old = current;
			Image selected = (Image) event.getSource();
			Panel fp = (Panel) dlg.getWidget();
			for (int i = 0; i < images.length; i++) {
				Image im = images[i];
				fp.remove(im);
				if (selected == im) {
					setValue(values[i]);
				}
			}
			dlg.remove(fp);
			dlg.hide(true);
			selecting = false;
			if (old!=current) {
				if (listener != null)
					listener.changed(ImageSelector.this, new Integer(old), new Integer(current));
			}
			return;
		}
		dlg = new DialogBox();
		FlowPanel fp = new FlowPanel();
		dlg.add(fp);
		dlg.setPopupPosition(currentImage.getAbsoluteLeft(), currentImage.getAbsoluteTop());
		dlg.setAutoHideEnabled(true);
		showing.remove(currentImage);
		for (Image im : images) {
			fp.add(im);
		}
		selecting = true;
		dlg.show();
	}

	public int getValue() {
		return current;
	}

}
