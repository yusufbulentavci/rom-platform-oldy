package com.bilgidoku.rom.gwt.client.util.chat.webrtc.noserver;

import com.bilgidoku.rom.min.Sistem;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.media.client.Audio;
import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

public class MediaProfile {

	public final String name;
	public final boolean audio;
	public final boolean camera;
	public final boolean screen;
	public final Video elVideo;
	private final Audio elAudio;
	private Canvas canvas;
	final int width;
	final int height;

	public final Panel panel = new FlowPanel();
	final Button aBut = new Button("audio");
	final Button cBut = new Button("camera");
	final Button sBut = new Button("screen");

	public String toString() {
		return "MediaProfile/ audio:" + audio + " video:" + camera + " w:" + width + " h:" + height;
	}

	private MediaProfile(String name, boolean audio, boolean camera, boolean screen, int width, int height) {
		this.name = name;
		this.audio = audio;
		this.camera = camera;
		this.screen = screen;
		this.width = width;
		this.height = height;
		elVideo = camera||screen ? Video.createIfSupported() : null;
		if (elVideo != null) {
			elVideo.setControls(false);
			elVideo.setAutoplay(true);
			elVideo.setSize(width + "px", height + "px");

			this.canvas = Canvas.createIfSupported();
			canvas.setSize(width + "px", height + "px");
			canvas.setCoordinateSpaceWidth(width);
			canvas.setCoordinateSpaceHeight(height);
		}

		if (elVideo==null && audio) {
			elAudio = Audio.createIfSupported();
			elAudio.setAutoplay(true);
			elAudio.setVolume(1.0);
		} else {
			elAudio = null;
		}

		disableState();
		panel.add(aBut);
		panel.add(cBut);
		panel.add(sBut);
	}

	private void disableState() {
		aBut.setEnabled(false);
		cBut.setEnabled(false);
		sBut.setEnabled(false);
	}

	public static MediaProfile talkOnly() {
		return new MediaProfile("talk only", true, false, false, 0, 0);
	}

	public void setSrc(String localMedia) {
		if (elVideo != null)
			elVideo.setSrc(localMedia);

		// elVideo.setSrc("http://www.w3schools.com/tags/movie.mp4");

	}

	public void playAudio(String dataUrl) {
		Sistem.outln("PLAYING AUDIO");
		elAudio.setSrc(dataUrl);
		elAudio.play();
	}

	public static MediaProfile video() {
		return new MediaProfile("video", true, true, false, 800, 600);
	}

	public static MediaProfile photo() {
		return new MediaProfile("photo", false, true, false, 150, 120);
	}

	public static MediaProfile screen() {
		return new MediaProfile("screen", true, false, true, 800, 600);
	}

	public String snapShot() {
		Context2d c = canvas.getContext2d();
		c.drawImage(elVideo.getVideoElement(), 0, 0, width, height);
		Sistem.outln("WIDTH:"+width);
		return canvas.toDataUrl("image/jpeg");
	}

	public void ready() {
		if (audio)
			aBut.setEnabled(true);
		if (camera)
			cBut.setEnabled(true);
	}

	public void error() {
		disableState();
	}

	
	
}
