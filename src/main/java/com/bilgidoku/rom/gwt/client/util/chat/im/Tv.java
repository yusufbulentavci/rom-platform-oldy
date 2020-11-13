package com.bilgidoku.rom.gwt.client.util.chat.im;

import com.bilgidoku.rom.gwt.client.util.Layer;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.CanPlayThroughEvent;
import com.google.gwt.event.dom.client.CanPlayThroughHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.EndedEvent;
import com.google.gwt.event.dom.client.EndedHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;

public class Tv extends AbsolutePanel {

	Video video = Video.createIfSupported();
	Image img = new Image();
	HTML header = new HTML();
	HTML txt = new HTML();
	Canvas canvas = Canvas.createIfSupported();
	private int htmlWidth = 800;
	private int htmlHeight = 600;

	private Element root;

	protected boolean drawing;

	protected boolean maybeclick;
	private TvCb ctrl;
	private final DlgCmdSender cmdSender;

	public Tv(final DlgCmdSender cmdSender) {
		this.cmdSender = cmdSender;
		Context2d context = getContext();
		context.save();

		canvas.setSize(htmlWidth + "px", htmlHeight + "px");
		video.setSize(htmlWidth + "px", htmlHeight + "px");
		img.setSize(htmlWidth + "px", htmlHeight + "px");

		header.setWidth(htmlWidth + "px");
		txt.setWidth(htmlWidth + "px");

		canvas.setCoordinateSpaceWidth(htmlWidth);
		canvas.setCoordinateSpaceHeight(htmlHeight);

		canvas.getElement().getStyle().setZIndex(Layer.layer4);
		header.getElement().getStyle().setZIndex(Layer.layer3);
		txt.getElement().getStyle().setZIndex(Layer.layer3);
		video.getElement().getStyle().setZIndex(Layer.layer2);
		img.getElement().getStyle().setZIndex(Layer.layer2);

		// initial
		img.setUrl("/_static/img/etc/tv.jpeg");

		// this.root = Document.get().getElementById("");

		this.add(video, 0, 0);
		this.add(img, 0, 0);
		this.add(canvas, 0, 0);
		this.add(header, 0, 10);
		this.add(txt, 0, 400);

		this.setSize(htmlWidth + "px", htmlHeight + "px");

		forMouseClick();
	}

	protected Context2d getContext() {
		Context2d c = canvas.getContext2d();
		return c;

	}

	public void setVideo(String source) {
		video.setSrc(source);
		videoVisible(true);
	}

	public void pause() {
		video.pause();
	}

	public void resume() {
		videoVisible(true);
		video.play();
	}

	public void showImage(String source) {
		try {
			video.pause();
		} catch (Exception e) {
		}
		videoVisible(false);
		img.setUrl(source);
		imageVisible(true);
	}

	public void showVideo(String src) {
		video.setSrc(src);
		videoVisible(true);
		imageVisible(false);
		video.play();
	}

	private void videoVisible(boolean b) {
		video.setVisible(b);
		ctrl.videoVisible(b);
	}

	private void imageVisible(boolean b) {
		img.setVisible(b);
		ctrl.imageVisible(b);
	}

	private void textVisible(boolean b) {
		txt.setVisible(b);
		ctrl.textVisible(b);
	}

	private void headerVisible(boolean b) {
		header.setVisible(b);
		ctrl.headerVisible(b);
	}

	public void showText(String str) {
		txt.getElement().setInnerHTML(
				"<div style='font-size:30px;text-align:center; width:100%;background-color: white;opacity: 0.6;'>" + str
						+ "</div>");
		textVisible(true);
	}

	public void showHeader(String str) {
		header.getElement().setInnerHTML(
				"<div style='font-size:40px;text-align:left; width:100%;background-color: white;opacity: 0.6;'>" + str
						+ "</div>");
		headerVisible(true);
	}

	public void videoCtrl(Integer secs, String ctrl) {
		if (secs != null) {
			video.setCurrentTime(secs);
			return;
		}
		switch (ctrl) {
		case "pause":
			video.pause();
			break;
		case "resume":
			video.play();
			break;
		case "stop":
			video.setCurrentTime(0);
			video.pause();
			break;
		}
	}

	public void synchVideo(String source, int seconds) {

		String cs = video.getSrc();
		if (cs == null || !cs.equals(source)) {
			video.setSrc(source);
			video.setCurrentTime(seconds);
			return;
		}

		int time = (int) video.getCurrentTime();
		if (Math.abs(time - seconds) > 10) {
			video.setCurrentTime(seconds);
		}
	}

	private void forMouseClick() {
		canvas.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				event.preventDefault();
				int dx = event.getRelativeX(Tv.this.getElement());
				int dy = event.getRelativeY(Tv.this.getElement());

				cmdSender.postCommand("*dlg.tvmark", dx + "," + dy, null);
			}
		});
	}

	public void mark(int markx, int marky) {
		Context2d c = getContext();
		c.clearRect(0, 0, htmlWidth, htmlHeight);
		c.beginPath();
		c.setFillStyle("red");
		c.arc(markx, marky, 10, 0, 2 * Math.PI);
		c.fill();
		c.setLineWidth(5);
		c.setStrokeStyle("#003300");
		c.stroke();

		this.ctrl.markActive(true);
	}

	public void setVideoFb(final TvCb cb) {
		this.ctrl = cb;
		video.addEndedHandler(new EndedHandler() {

			@Override
			public void onEnded(EndedEvent event) {
				cb.videoEnded();
			}
		});

		video.addCanPlayThroughHandler(new CanPlayThroughHandler() {

			@Override
			public void onCanPlayThrough(CanPlayThroughEvent event) {
				cb.canPlay();
			}
		});

		video.addDomHandler(new TimeUpdateHandler() {

			@Override
			public void onTimeUpdate(TimeUpdateEvent event) {
				cb.updateTime(video.getCurrentTime());
			}
		}, TimeUpdateEvent.getType());
	}

	public void show(char str) {
		switch (str) {
		case 'V':
			videoVisible(true);
			break;
		case 'v':
			videoVisible(false);
			break;
		case 'H':
			headerVisible(true);
			break;
		case 'h':
			headerVisible(false);
			break;
		case 'T':
			textVisible(true);
			break;
		case 't':
			textVisible(false);
			break;
		case 'I':
			imageVisible(true);
			break;
		case 'i':
			imageVisible(false);
			break;
		case 'm':
			hideMark();
			break;
		case 'c':
			clearall();
			break;
		}

	}

	private void hideMark() {
		ctrl.markActive(false);
		Context2d c = getContext();
		c.clearRect(0, 0, htmlWidth, htmlHeight);
	}

	private void clearall() {
		hideMark();
		videoVisible(false);
		textVisible(false);
		headerVisible(false);
		imageVisible(false);
	}

	// private void forMouseDown() {
	// canvas.addMouseDownHandler(new MouseDownHandler() {
	//
	// @Override
	// public void onMouseDown(MouseDownEvent event) {
	// event.preventDefault();
	// int dx = event.getRelativeX(root);
	// int dy = event.getRelativeY(root);
	// drawing = true;
	// maybeclick = true;
	//
	// }
	//
	// });
	// }
	//
	// private void forMouseMove() {
	// canvas.addMouseMoveHandler(new MouseMoveHandler() {
	//
	// @Override
	// public void onMouseMove(MouseMoveEvent event) {
	//
	// event.preventDefault();
	// maybeclick = false;
	//
	// int dx = event.getRelativeX(root);
	// int dy = event.getRelativeY(root);
	//
	// }
	//
	// });
	//
	// }
	//
	// private void forMouseOut() {
	// canvas.addMouseOutHandler(new MouseOutHandler() {
	//
	// @Override
	// public void onMouseOut(MouseOutEvent event) {
	// event.preventDefault();
	// drawing = false;
	//
	// }
	//
	// });
	//
	// }
	//
	// private void forMouseOver() {
	// canvas.addMouseOverHandler(new MouseOverHandler() {
	// @Override
	// public void onMouseOver(MouseOverEvent event) {
	// event.preventDefault();
	// int dx = event.getRelativeX(root);
	// int dy = event.getRelativeY(root);
	//
	// }
	// });
	// }
	//
	// private void forMouseUp() {
	// canvas.addMouseUpHandler(new MouseUpHandler() {
	// @Override
	// public void onMouseUp(MouseUpEvent event) {
	// // event.preventDefault();
	//
	// int dx = event.getRelativeX(root);
	// int dy = event.getRelativeY(root);
	//
	// if (maybeclick) {
	// drawing = false;
	// // selectOn(dx, dy);
	// maybeclick = false;
	// // showMenu(event.getClientX(), event.getClientY() +
	// // Window.getScrollTop());
	// } else {
	// // end of resize ya da move
	// drawing = false;
	// // deactivate();
	// // unselect();
	// // idle();
	// // redraw();
	//
	// }
	// }
	// });
	//
	// }
	//

}

interface TimeUpdateHandler extends EventHandler {

	void onTimeUpdate(TimeUpdateEvent event);
}

class TimeUpdateEvent extends DomEvent<TimeUpdateHandler> {

	private static final Type<TimeUpdateHandler> TYPE = new Type<TimeUpdateHandler>("timeupdate",
			new TimeUpdateEvent());

	public static Type<TimeUpdateHandler> getType() {
		return TYPE;
	}

	protected TimeUpdateEvent() {
	}

	@Override
	public final Type<TimeUpdateHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(TimeUpdateHandler handler) {
		handler.onTimeUpdate(this);
	}
}

interface TvCb {

	void videoEnded();

	void updateTime(double currentTime);

	void canPlay();

	void headerVisible(boolean b);

	void textVisible(boolean v);

	void imageVisible(boolean v);

	void videoVisible(boolean v);

	void markActive(boolean v);

}
