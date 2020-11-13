package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.widgets.ImageBox;
import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.gwt.client.widgets.InputChangedHandler;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBoxRegistry;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

class ImageContextRegistry extends InputBoxRegistry {

	@Override
	public boolean iCanInput(Att att) {
		return att.getContext() == Att.CONTEXT_IMG;
	}

	@Override
	public InputBox create(Att att, String value) {
		return new ImageContextBox(att, value);
	}

}

public class ImageContextBox extends InputBox {

	private final Widget textBox;

	protected ImageContextBox(Att att, String value) {
		textBox = getBox(att.getNamed(), value);
		initWidget(textBox);
	}

	public static ImageContextRegistry getRegistry() {
		return new ImageContextRegistry();
	}

	private Widget getBox(final String name, String value) {
		final TextBox tf = new TextBox();
		// tf.setTitle(name);
		tf.setValue(value);
		tf.addStyleName("mini-TextBox");

		JSONObject item = new JSONObject(hackItem());
		if (item.get("0") != null) {
			item = new JSONObject(hackItem());
		}
		final String sml = ClientUtil.getString(item.get("icon"));
		final String med = ClientUtil.getString(item.get("medium_icon"));
		final String lar = ClientUtil.getString(item.get("large_icon"));

		Image img1 = new Image(sml);
		img1.setWidth("70px");
		img1.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				tf.setValue(sml);
				ImageContextBox.this.fireEvent(new InputChanged(name, sml));
			}
		});

		Image img2 = new Image(med);
		img2.setWidth("70px");
		img2.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				tf.setValue(med);
				ImageContextBox.this.fireEvent(new InputChanged(name, med));
			}
		});

		Image img3 = new Image(lar);
		img3.setWidth("70px");
		img3.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				tf.setValue(lar);
				ImageContextBox.this.fireEvent(new InputChanged(name, lar));
			}
		});

		final ImageBox ib = new ImageBox();
		ib.setImage(value);

		ib.addHandler(new InputChangedHandler() {
			@Override
			public void changed(InputChanged event) {
				tf.setValue(ib.getImgPath());
				ImageContextBox.this.fireEvent(new InputChanged(name, ib.getImgPath()));

			}
		}, InputChanged.TYPE);

		tf.setWidth("300px");

		tf.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				ImageContextBox.this.fireEvent(new InputChanged(name, tf.getValue()));
			}
		});

		final FlowPanel sizes = new FlowPanel();
		final RadioButton rb = new RadioButton("s", Ctrl.trans.noSizing());
		final RadioButton rb1 = new RadioButton("s", Ctrl.trans.thumb());
		final RadioButton rb2 = new RadioButton("s", Ctrl.trans.small());
		final RadioButton rb3 = new RadioButton("s", Ctrl.trans.medium());
		final RadioButton rb4 = new RadioButton("s", Ctrl.trans.large());

		final RadioButton[] btns = { rb, rb1, rb2, rb3, rb4 };
		for (int i = 0; i < btns.length; i++) {

			final int j = i;
			sizes.add(btns[j]);
			btns[j].addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (btns[j].equals(rb)) {
						tf.setValue(ClientUtil.removeParameter(tf.getValue(), "romthumb"));
					} else if (btns[j].equals(rb1)) {
						tf.setValue(ClientUtil.addParameter(tf.getValue(), "romthumb", "t"));
					} else if (btns[j].equals(rb2)) {
						tf.setValue(ClientUtil.addParameter(tf.getValue(), "romthumb", "s"));
					} else if (btns[j].equals(rb3)) {
						tf.setValue(ClientUtil.addParameter(tf.getValue(), "romthumb", "m"));
					} else if (btns[j].equals(rb4)) {
						tf.setValue(ClientUtil.addParameter(tf.getValue(), "romthumb", "l"));
					}
					ImageContextBox.this.fireEvent(new InputChanged(name, tf.getValue()));
				}
			});

		}

		FlexTable ft = new FlexTable();
		ft.setHTML(0, 0, "Sayfa İkonu");
		ft.setHTML(0, 1, "Sayfa Resmi");
		ft.setHTML(0, 2, "Sayfa Büyük Resmi");
		ft.setHTML(0, 3, "Seçiniz");

		ft.setWidget(1, 0, img1);
		ft.setWidget(1, 1, img2);
		ft.setWidget(1, 2, img3);
		ft.setWidget(1, 3, ib);

		ft.setWidget(2, 0, tf);
		ft.setWidget(3, 0, sizes);
		ft.getFlexCellFormatter().setColSpan(2, 0, 4);
		ft.getFlexCellFormatter().setColSpan(3, 0, 4);

		// HorizontalPanel hp = new HorizontalPanel();
		// hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		// hp.add(tf);
		// hp.add(img);
		// hp.add(cb);

		return ft;
	}

	private static native JavaScriptObject hackItem()
	/*-{
		return $wnd.itm;
	}-*/;

}
