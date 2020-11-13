package com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver;

import com.bilgidoku.rom.gwt.client.util.common.ActionBar;
import com.bilgidoku.rom.min.Sistem;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;

public class TakePhotoPanel extends FlowPanel {
	private final Image img=new Image("http://www.w3schools.com/tags/smiley.gif");
	
	private final MediaProfile mediaProfile;
	private LocalMedia localMedia;
	protected boolean talking;
	
	private MediaReady readyCb;

	
	private LocalMediaCb cb = new LocalMediaCb() {
		

		@Override
		public void mediaReady() {
			Sistem.outln("TAKE PHOTO SUC READ");
			localMedia.snapshot();
		}


		@Override
		public void busy() {

		}

		@Override
		public void snapShotReady(String dataUrl) {
			Sistem.outln("TAKE PHOTO SNAP OK");
			img.setUrl(dataUrl);
			img.setVisible(true);
			mediaProfile.elVideo.setVisible(false);
			
			Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
				
				@Override
				public boolean execute() {
					img.setVisible(false);
					mediaProfile.elVideo.setVisible(true);
					
					Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
						
						@Override
						public boolean execute() {
							localMedia.snapshot();
							return false;
						}
					}, 15000);
					
					return false;
				}
			}, 2000);
			
			readyCb.mediaReady(dataUrl);
			
			
		}

		@Override
		public void talkReady(String dataUrl) {
			
		}

		@Override
		public void talking() {
//			btnKonus.changeHTML("/_local/images/microphone.png", "Stop");
//			btnKonus.setHTML("stop talking");
		}

		@Override
		public void error(String string) {
			localMedia=null;
			Sistem.outln("TAKE PHOTO ERR");
		}

		@Override
		public void notSupported() {
		}

	};

	

	

	public TakePhotoPanel(MediaReady mediaReady) {
		this.readyCb=mediaReady;
		this.addStyleName("gwt-TextArea");
		this.getElement().getStyle().setZIndex(ActionBar.enarkaUstu);
		
		img.setUrl("/_public/images/contact.gif");
		img.setVisible(true);

		
		mediaProfile=MediaProfile.photo();
		

		img.setSize(mediaProfile.width+"px", mediaProfile.height+"px");
		
		img.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(localMedia==null){
					start();
				}
			}
		});
		
		this.add(img);
		this.getElement().getStyle().setTextAlign(com.google.gwt.dom.client.Style.TextAlign.CENTER);

		start();
		
		
//		this.hide();
		
	}




	private void start() {
		localMedia = new LocalMedia(cb, mediaProfile);
		
		img.setVisible(true);
		Video elVideo = mediaProfile.elVideo;
		elVideo.setVisible(false);
		elVideo.setControls(false);
		elVideo.setAutoplay(true);
		add(elVideo);
		localMedia.start();
	}

	


	protected void closeMedia() {
	}

	public void close() {
		localMedia.close();
	}

	public void audio() {
		// au.
	}
	
	
	
}