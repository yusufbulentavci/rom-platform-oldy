package com.bilgidoku.rom.site.yerel.writings.wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.ArrayResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.service.InternetDao;
import com.bilgidoku.rom.gwt.araci.client.site.FilesDao;
import com.bilgidoku.rom.gwt.shared.ImageResp;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.user.client.Window.Location;

public class ApplyReadyImages {
	protected boolean pdpReady = false;
	protected boolean pixabayReady = false;
	static List<ImageResp> pageImage = new ArrayList<ImageResp>();
	private static int imgIndex = 0;

	public ApplyReadyImages() {

		InternetDao.searchimg(3, null, null, "3", null, null, null, null, null, new ArrayResponse<ImageResp>() {
			@Override
			public void ready(ImageResp[] value) {
				List<ImageResp> data3 = new ArrayList<ImageResp>(Arrays.asList(value));
				pageImage.addAll(data3);
				pdpReady = true;
				createGalleryImages();

			}
		});

		InternetDao.searchimg(4, null, null, "3", null, null, null, null, null, new ArrayResponse<ImageResp>() {
			@Override
			public void ready(ImageResp[] value) {
				List<ImageResp> data4 = new ArrayList<ImageResp>(Arrays.asList(value));
				pageImage.addAll(data4);
				pixabayReady = true;
				createGalleryImages();
			}
		});

	}

	public static String[] getGallery() {
		StringBuffer sb = new StringBuffer();
		if (pageImage.size() >= imgIndex + 4) {
			for (int i = imgIndex; i < imgIndex + 5; i++) {
				ImageResp img = pageImage.get(i);
				sb.append(",\"" + img.thumbpath.replace("_t.", "_m.") + "\"");
			}

			imgIndex = imgIndex + 4;

		} else {
			for (int i = imgIndex; i < pageImage.size(); i++) {
				ImageResp img = pageImage.get(imgIndex);
				sb.append(",\"" + img.thumbpath.replace("_t.", "_m.") + "\"");
			}
			imgIndex = 0;
		}
		String ret = sb.toString();
		if (ret.length() > 0)
			ret = ret.substring(1);
		return ret.split(",");
	}

	private void createGalleryImages() {
		if (!(pdpReady && pixabayReady))
			return;
		copyImages();
	}

	protected void copyImages() {
		final String[] gal = getGallery();
		final String loc = Location.getProtocol() + "//" + Location.getHost();

		for (int i = 0; i < gal.length; i++) {
			final int j = i;
			FilesDao.neww(Ctrl.infoLang(), "/f/images/readymedia", null, loc + gal[j].replace("\"", ""), null, null,
					null, "/f/images/readymedia", new StringResponse() {
						@Override
						public void ready(String value) {
						}

						@Override
						public void err(int statusCode, String statusText, Throwable exception) {
						}
					});

		}

	}

	public static String getNextReadyImage() {
		if (pageImage.size() > 0) {
			ImageResp img = pageImage.get(imgIndex);
			imgIndex++;

			if (pageImage.size() <= imgIndex)
				imgIndex = 0;
			return img.thumbpath;
		}
		return null;

	}

}
