package com.bilgidoku.rom.site.yerel.writings;



import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.com.RomFrameHandler;
import com.bilgidoku.rom.gwt.client.util.com.RomFrameImpl;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window.Location;

/**
 * 
 * 
 * 
 * 
 * @author rompg
 *
 */
public class WritingFrame extends RomFrameImpl {

	public static WritingFrame create(String pageUri, String lang, boolean inFrame) {
		UrlBuilder builder = Location.createUrlBuilder().setPath(pageUri);
		ClientUtil.removeAllParameters(builder);
		builder.setParameter("pagelang", lang);
		builder.setParameter("lng", lang);
		builder.setParameter("rom.render", "htmledit");
		builder.setParameter("locale", RomEntryPoint.one.currentLocale);

		WritingFrame wf = new WritingFrame(builder, new RomFrameHandler() {

			@Override
			public void setItem(String cls, String uri) {
			}

			@Override
			public void ready() {
			}

			@Override
			public void focusItem(String cls, String url) {
				if (cls.equals("list")) {
					Ctrl.focusList(url);
				} else if (cls.equals("link")) {
					Ctrl.focusLink(url);
				} else if (cls.equals("media")) {
					Ctrl.focusMedia(url);
				} else if (cls.equals("search")) {
					Ctrl.focusSearch();
				} else if (cls.equals("images")) {
//					Ctrl.focusMediaContainer(url);
				} else if (cls.equals("writings")) {
					Ctrl.focusPageContainer(url);
				} else if (cls.equals("waitingcomments")) {
					Ctrl.focusWaitingComments(url);
				} else if (cls.equals("allcomments")) {
					Ctrl.focusAllComments(url);
				}

			}
		}, null);
		return wf;
	}

	public WritingFrame(UrlBuilder urlBuilder, RomFrameHandler rfh, String initialText) {
		super(urlBuilder, rfh, initialText);
		this.setWidth("99%");
		this.setHeight("99%");
	}

}
