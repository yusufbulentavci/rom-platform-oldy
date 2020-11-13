package com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver;

import com.bilgidoku.rom.gwt.client.util.common.SiteToolbarButton;
import com.bilgidoku.rom.min.Sistem;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PushToTalkButton extends VerticalPanel implements LocalMediaCb {
	final static Image downImage = new Image("/_public/images/mic_stop.png");
	final static Image upImg = new Image("/_public/images/mic_start.png");
	
	public class InPushButton extends PushButton{
		public InPushButton(){			
			super(upImg, downImage);
		}

		@Override
		protected void onClick() {
			super.onClick();
			stopTalking();
		}

		@Override
		protected void onClickStart() {
			super.onClickStart();
			startTalking();
		}		
	}
	

	private boolean talking=false;
	
	private final MediaProfile mediaProfile=MediaProfile.talkOnly();
	private final InPushButton talkbutton=new InPushButton();
	private final SiteToolbarButton enable = new SiteToolbarButton("/_public/images/mic_prepare.png", "Talk", "Talk", "");
	
	private LocalMedia localMedia;

	private final MediaReady mediaReady;		
	
	public PushToTalkButton(MediaReady mediaReady) {
		this.mediaReady=mediaReady;
		talkbutton.setVisible(false);		
		this.add(enable);
		this.add(talkbutton);
		this.enable.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				localMedia=new LocalMedia(PushToTalkButton.this, mediaProfile);
				localMedia.start();
				enable.setEnabled(false);
			}
		});
		
	}
	
	


	
	private void stopTalking() {
		if(!this.talking)
			return;
		Sistem.outln("Stop talking");
		
		this.talking=false;
		localMedia.talkEnd();
	}

	private void startTalking() {
		if(this.talking)
			return;
		
		Sistem.outln("Start talking");
		this.talking=true;
		
		localMedia.talkBegin();
		
	}



	@Override
	public void busy() {
	}



	@Override
	public void mediaReady() {
		this.enable.setVisible(false);
		this.talkbutton.setVisible(true);
	}



	@Override
	public void error(String s) {
		problem();
	}





	private void problem() {
		this.enable.setVisible(true);
		this.enable.setEnabled(true);
		this.talkbutton.setVisible(false);
		Sistem.errln("Problem");
	}



	@Override
	public void snapShotReady(String dataUrl) {
	}



	@Override
	public void talking() {
	}



	@Override
	public void talkReady(String dataUrl) {
//		mediaProfile.playAudio(dataUrl);
		mediaReady.mediaReady(dataUrl);
	}



	@Override
	public void notSupported() {
		problem();
	}





	public void startSpeak() {
		startTalking();
	}





	public void endSpeak() {
		stopTalking();
	}

}
