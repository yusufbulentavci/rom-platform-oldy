package com.bilgidoku.rom.gwt.client.util.chat.im;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TvCtrl extends VerticalPanel implements TvCb {

	CheckBox video = new CheckBox("Video");
	CheckBox image = new CheckBox("Image");
	CheckBox header = new CheckBox("Header");
	CheckBox text = new CheckBox("Text");
	Button hideMark = new Button("Hide mark");
	Button clear = new Button("Clear");

	private final Tv tv;
	private final DlgCmdSender cmdSender;

	public TvCtrl(Tv tv, final DlgCmdSender cmdSender) {
		this.tv = tv;
		this.cmdSender = cmdSender;

		String user = RomEntryPoint.com().get("user");
		if (user != null) {
			this.add(video);
			this.add(image);
			this.add(header);
			this.add(text);
			this.add(hideMark);
			this.add(clear);

			forShow(video, "V", "v");
			forShow(image, "I", "i");
			forShow(header, "H", "h");
			forShow(text, "T", "t");
			hideMark.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					cmdSender.postCommand("*dlg.tvshow", "m", null);
				}
			});
			hideMark.setEnabled(false);
			clear.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					cmdSender.postCommand("*dlg.tvshow", "c", null);
				}
			});
		}

	}

	private void forShow(final CheckBox m, final String show, final String hide) {
		m.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				m.setValue(!event.getValue());
				m.setEnabled(false);
				cmdSender.postCommand("*dlg.tvshow", m.getValue() ? hide : show, null);
			}
		});
		// m.addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// event.
		//
		// }
		// });
	}

	public void doit(Integer secs, String ctrl) {
		// TODO Auto-generated method stub

	}

	public void newVideo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void videoEnded() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTime(double currentTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void canPlay() {
		// TODO Auto-generated method stub

	}

	@Override
	public void textVisible(boolean v) {
		text.setValue(v);
		text.setEnabled(true);
	}

	@Override
	public void imageVisible(boolean v) {
		image.setValue(v);
		image.setEnabled(true);
	}

	@Override
	public void videoVisible(boolean v) {
		video.setValue(v);
		video.setEnabled(true);
	}

	@Override
	public void markActive(boolean b) {
		hideMark.setEnabled(b);
	}

	@Override
	public void headerVisible(boolean b) {
		header.setValue(b);
		header.setEnabled(true);
	}

}
