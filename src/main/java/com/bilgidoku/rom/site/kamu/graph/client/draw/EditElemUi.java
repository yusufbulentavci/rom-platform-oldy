package com.bilgidoku.rom.site.kamu.graph.client.draw;

import com.bilgidoku.rom.site.kamu.graph.client.images.Images;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.google.gwt.core.client.GWT;

public class EditElemUi {
	private final Images images = GWT.create(Images.class);
	public final SiteButton del = new SiteButton(images.del(), null, "Sil", "smllr");
	public final SiteButton edit = new SiteButton(images.pencil_sml(), null, "Düzenle", "smllr");
	public final SiteButton back = new SiteButton(images.layers(), null, "Arkaya", "smllr");
	public final SiteButton cp1 = new SiteButton(images.resize(), null, null, "smllr");
	public final SiteButton cp2 = new SiteButton(images.resize(), null, null, "smllr");

	public EditElemUi(){
	}
}
