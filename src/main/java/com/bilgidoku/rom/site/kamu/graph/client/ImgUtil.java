package com.bilgidoku.rom.site.kamu.graph.client;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.min.geo.Point;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class ImgUtil {

	static int incr = 0;

	// // static Point a3 = new Point(1754, 2480);
	// static Point a3 = new Point(1744, 2460); // give some padding
	// // static Point cup = new Point(377, 435); //10 x 12 cm
	// static Point cup = new Point(360, 420); // give some padding

	// public final static Point fitCup(Point val) {
	// if (val.getX() > cup.getX()) {
	// return new Point(cup.getX(), Math.round(((float) cup.getX() / val.getX())
	// * val.getY()));
	// }
	//
	// if (val.getY() > cup.getY()) {
	// return new Point(Math.round(((float) cup.getY() / val.getY()) *
	// val.getX()), cup.getY());
	// }
	// return val;
	// }

	public final static void colorAt(final String url, final int x, final int y, final AsyncCallback<int[]> cb) {
		final Image image = prepare(url);
		image.addLoadHandler(new LoadHandler() {

			@Override
			public void onLoad(LoadEvent event) {

				Canvas canvas = Canvas.createIfSupported();

				// Do nothing if canvas is not supported.
				if (canvas == null)
					return;

				// Size the canvas to fit the image.
				canvas.setCoordinateSpaceHeight(image.getHeight());
				canvas.setCoordinateSpaceWidth(image.getWidth());

				// Pull the image's underlying DOM element
				ImageElement img = ImageElement.as(image.getElement());

				// The 2D context does all the drawing work on the canvas
				Context2d context = canvas.getContext2d();

				context.drawImage(img, 0, 0); // Now the canvas contains the
												// image.

				// ImageData represents the canvas rgba data as an array of
				// bytes.
				ImageData imageData = context.getImageData(0, 0, image.getWidth(), image.getHeight());

				int[] ret = new int[4];
				ret[0] = imageData.getRedAt(x, y);
				ret[1] = imageData.getGreenAt(x, y);
				ret[2] = imageData.getBlueAt(x, y);
				ret[3] = imageData.getAlphaAt(x, y);
				cb.onSuccess(ret);

				image.removeFromParent();
			}
		});
	}

	public final static void size(String url, final AsyncCallback<Point> cb) {
		final Image image = prepare(url);
		image.addLoadHandler(new LoadHandler() {

			@Override
			public void onLoad(LoadEvent event) {
				cb.onSuccess(new Point(image.getWidth(), image.getHeight()));
				image.removeFromParent();
			}
		});

	}

	public static Image prepare(String url) {
		final Image image = new Image(url);
		final String id = "temp." + (incr++);
		image.getElement().setId(id);
		image.setVisible(false);
		RootPanel.get().add(image);
		return image;
	}

	public static Image load(String url, final AsyncCallback<Boolean> cb) {
		final Image image = new Image(url);
		final String id = "temp." + (incr++);
		image.getElement().setId(id);
		image.setVisible(false);
		RootPanel.get().add(image);
		image.addLoadHandler(new LoadHandler() {
			@Override
			public void onLoad(LoadEvent event) {
				cb.onSuccess(true);
			}
		});

		image.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(ErrorEvent event) {
				cb.onFailure(null);

			}
		});
		return image;
	}

	public static String toImage(Canvas canvas) {
		return canvas.toDataUrl("image/png");
	}

	public static Point fit(Point dims, Point result) {
		Point val = result.clone();

		if (val.getX() > dims.getX()) {
			return new Point(dims.getX(), Math.round(((float) dims.getX() / val.getX()) * val.getY()));
		}

		if (val.getY() > dims.getY()) {
			return new Point(Math.round(((float) dims.getY() / val.getY()) * val.getX()), dims.getY());
		}
		return val;
	}
}
