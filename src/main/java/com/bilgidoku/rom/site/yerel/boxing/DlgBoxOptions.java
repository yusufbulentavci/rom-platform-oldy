package com.bilgidoku.rom.site.yerel.boxing;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DlgBoxOptions extends ActionBarDlg {
	private BoxHolder boxHolder;
	private String boxId;

	public DlgBoxOptions(BoxHolder boxHolder2, String id) {
		super(Ctrl.trans.options(), null, null);
		
		boxId = id;
		boxHolder = boxHolder2;
		run();

	}

	@Override
	public Widget ui() {
		Button edit = new SiteButton("/_local/images/common/pencil.png", Ctrl.trans.edit(), Ctrl.trans.edit(), "");
		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (boxHolder == null)
					return;

				boxHolder.editBox(boxId);
				DlgBoxOptions.this.hide();

			}
		});

		Button del = new SiteButton("/_local/images/common/bin.png", Ctrl.trans.delete(), Ctrl.trans.removeSelected(),
				"");
		del.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				boxHolder.deleteBox(boxId);
				boxHolder.idle(); // ????
				DlgBoxOptions.this.hide();
			}
		});

		final Button sty = new SiteButton("/_local/images/common/aperture.png", Ctrl.trans.style(), Ctrl.trans.style(),
				"");
		sty.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DlgBoxOptions.this.hide();
				boxHolder.showStyleDlg(boxId);
			}
		});

		final Button anim = new SiteButton("/_local/images/common/watch.png", Ctrl.trans.animation(),
				Ctrl.trans.animation(), "");
		anim.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DlgBoxOptions.this.hide();
				boxHolder.showAnimationDlg(boxId);
			}
		});

		edit.setWidth("90px");
		del.setWidth("90px");
		sty.setWidth("90px");
		anim.setWidth("90px");

		VerticalPanel vert = new VerticalPanel();
		vert.add(edit);
		vert.add(del);
		vert.add(sty);
		vert.add(anim);
		return vert;
	}

	@Override
	public void cancel() {
	}

	@Override
	public void ok() {
	}

}