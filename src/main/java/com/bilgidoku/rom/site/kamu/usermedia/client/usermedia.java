package com.bilgidoku.rom.site.kamu.usermedia.client;

import com.bilgidoku.rom.gwt.client.util.PortabilityImpl;
import com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.LocalMedia;
import com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.LocalMediaCb;
import com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.MediaProfile;
import com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver.WebRtcSupCap;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.Portable;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class usermedia implements EntryPoint, LocalMediaCb{

	@Override
	public void onModuleLoad() {
		Portable.one = new PortabilityImpl();
		Portable.one.error("HOT");
//		MediaProfile vp = MediaProfile.video();
		MediaProfile vp = MediaProfile.screen();
		
		RootPanel.get().add(vp.panel);
		RootPanel.get().add(vp.elVideo);
		
		LocalMedia localMedia = new LocalMedia(this, vp);
		localMedia.start();
	
		
//		Sistem.outln(WebRtcSupCap.cap.toString());
		Sistem.outln(WebRtcSupCap.support.toString());
		
		
	}

	@Override
	public void busy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mediaReady() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void snapShotReady(String dataUrl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void talking() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void talkReady(String dataUrl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notSupported() {
		// TODO Auto-generated method stub
		
	}

}
