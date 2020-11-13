package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.util.common.SiteMessage;
import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.gwt.client.widgets.PasteEvent;
import com.bilgidoku.rom.gwt.client.widgets.PasteHandler;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.ImageAnchor;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBoxRegistry;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

class YoutubeRegistry extends InputBoxRegistry {

	@Override
	public boolean iCanInput(Att att) {
		return att.getContext() == Att.CONTEXT_YOUTUBE;
	}

	@Override
	public InputBox create(Att att, String value) {
		return new YoutubeBox(att, value);
	}

}

public class YoutubeBox extends InputBox {

	private final Widget textBox;

	protected YoutubeBox(Att att, String value) {
		textBox = getBox(att.getNamed(), value);
		initWidget(textBox);
	}

	public static YoutubeRegistry getRegistry() {
		return new YoutubeRegistry();
	}

	private Widget getBox(final String name, String value) {

		ImageAnchor anc = new ImageAnchor("/_local/images/question.png");
		anc.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				new SiteMessage(Ctrl.trans.youtubeDesc(), Ctrl.trans.desc());
			}
		});

		final TextBox tf = new TextBox();
		tf.setTitle(name);
		tf.setValue(value);

		tf.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String id = getVideoId(tf.getValue());
				tf.setValue(id);
				fireEvent(new InputChanged(name, id));
			}
		});

		tf.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				String id = getVideoId(tf.getValue());
				tf.setValue(id);
				fireEvent(new InputChanged(name, id));

			}
		}, PasteEvent.TYPE);

		tf.setWidth("350px");

		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(tf);
		hp.add(anc);
		return hp;
	}

	public static String getVideoId(String val) {
		String id = val;
		if (val.indexOf("youtube") <0)
			return id;
		
		
		if (val.indexOf("watch") > 0) {
			if (val.indexOf("&") > 0)
				id = val.substring(val.indexOf("v=") + 2, val.indexOf("&"));
			else
				id = val.substring(val.indexOf("v=") + 2);
			
		} else if (val.indexOf("embed") > 0){
			
			id = val.substring(val.lastIndexOf("/") + 1);
			
		}
		
		
		return id;
	}

	

}
