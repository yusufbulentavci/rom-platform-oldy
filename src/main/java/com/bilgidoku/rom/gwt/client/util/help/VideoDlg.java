package com.bilgidoku.rom.gwt.client.util.help;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.util.video.VideoSource;
import com.bilgidoku.rom.gwt.client.util.video.VideoSource.VideoType;
import com.bilgidoku.rom.gwt.client.util.video.VideoWidget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;

public class VideoDlg extends DialogBox{
	VideoWidget videoPlayer = new VideoWidget(false, true, null);
	
	public VideoDlg(String title, String[] uris) {
		
		Button btnClose = new Button("Close");
		btnClose.setStyleName("site-closebutton");
		forClose(btnClose);
		
		List<VideoSource> sources = new ArrayList<VideoSource>();
		for (int i = 0; i < uris.length; i++) {
			sources.add(new VideoSource("http://home.tlos.net/f/videos/help" + uris[i], VideoType.MP4));
		}
        videoPlayer.setSources(sources);
        videoPlayer.setAutoPlay(true);
        videoPlayer.setPixelSize(800, 600);	
	
        FlowPanel fp = new FlowPanel();
        fp.add(videoPlayer);
        fp.add(btnClose);
        
        this.setTitle(title);
        this.setText(title);        
		this.setWidget(fp);
		this.setAutoHideEnabled(true);
		this.setStyleName("site-helpdlg");
		this.setModal(false);
		this.show();		
		this.center();
	}

	private void forClose(Button btnClose) {
		btnClose.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				VideoDlg.this.hide();
			}
		});
	}

}
