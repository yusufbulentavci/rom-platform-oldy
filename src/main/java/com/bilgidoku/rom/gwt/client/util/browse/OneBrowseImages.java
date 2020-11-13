package com.bilgidoku.rom.gwt.client.util.browse;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.browse.image.BrowseCallback;
import com.bilgidoku.rom.gwt.client.util.browse.image.BrowseImageDlg;
import com.bilgidoku.rom.min.Sistem;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;

public class OneBrowseImages {

	private static BrowseCallback callback = null;

	private static BrowseImageDlg bi = getBrowseImages(callback);

	private static BrowseImageDlg getBrowseImages(BrowseCallback bc) {
		bi = new BrowseImageDlg();
		bi.show();
		bi.center();
		bi.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				if (callback != null) {
					callback.selected(bi.selectedImage);
					callback = null;
				}
			}
		});

		return bi;
	}

	public static void showMe(final BrowseCallback bc) {
		if (callback != null) {
			Sistem.outln("Baska bir browse açık");
			return;
		}
		
		callback = bc;
		bi.setResource(RomEntryPoint.one.comGetAttr("uri"));
		bi.show();

	}
}
