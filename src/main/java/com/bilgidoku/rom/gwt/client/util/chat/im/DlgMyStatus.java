package com.bilgidoku.rom.gwt.client.util.chat.im;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.araci.client.service.OturumIciCagriDao;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.common.ActionBar;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.bilgidoku.rom.shared.util.Presence;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;

public class DlgMyStatus extends DialogBox {
	
	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);
	
	private final ListBox lb = new ListBox();
	public String status = null;
	private Messaging parent2;

	public DlgMyStatus(int i, int j, final Messaging parent) {		
		super(true);
		
		lb.setVisibleItemCount(5);
		parent2 = parent;
		lb.addItem(trans.visible(), Presence.Visible + "");
		lb.addItem(trans.dontDisturb(), Presence.DontDisturb + "");
		lb.addItem(trans.away(), Presence.Away + "");
		lb.addItem(trans.canChat(), Presence.CanChat + "");
		lb.addItem(trans.canCall(), Presence.CanCall + "");
		
		lb.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				final int pre = Integer.parseInt(lb.getSelectedValue());
				OturumIciCagriDao.rtpresence(lb.getSelectedItemText(), pre, new BooleanResponse() {
					@Override
					public void ready(Boolean value) {
						if (value) {
							RomEntryPoint.com().set("xmpp.presence",""+pre);
							parent2.updateStatusInfo();
						}
						DlgMyStatus.this.hide();
					}
				});
				
			}
		});
		
		Button btnCloseDlg = new Button("close");
		btnCloseDlg.setStyleName("site-closebutton");
		btnCloseDlg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				DlgMyStatus.this.hide();
			}

		});

		FlowPanel fp = new FlowPanel();
		fp.add(lb);
		fp.add(btnCloseDlg);
		
		this.setHTML(ActionBarDlg.getDlgCaption(null, "Status"));
		this.addStyleName("site-chatdlg");
		this.getElement().getStyle().setZIndex(ActionBar.enarkaUstu);
		this.setWidget(fp);
		this.setPopupPosition(i-80, j);
		this.setAutoHideEnabled(true);
		this.show();
	}
	
}
