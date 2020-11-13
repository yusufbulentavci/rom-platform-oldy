package com.bilgidoku.rom.gwt.client.util.common;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SiteArrayBox extends Composite {
	private final Button btnAdd = new Button("Add Item");
	private final TextBox tbItems = new TextBox();
	private List<String[]> allItems;
	ListBox lb = new ListBox();
	String picked = null;

	public SiteArrayBox() {
		tbItems.addStyleName("grey");
		tbItems.setWidth("100px");

		btnAdd.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final DlgPick pick = new DlgPick();
				pick.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						if (tbItems.getValue().isEmpty())
							tbItems.setValue(picked);
						else
							tbItems.setValue(tbItems.getValue() + "," + picked);						
						
					}
				});
				
			}
		});

		lb.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				picked = lb.getSelectedValue();
			}
		});

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(btnAdd);
		hp.add(tbItems);
		initWidget(hp);

	}

//	public String[] getValueArr() {
//		String[] tags = null;
//		if (tbItems.getValue() != null && !tbItems.getValue().trim().isEmpty()) {
//			tags = tbItems.getValue().split(",");
//		}
//		return tags;
//	}

	public String getValue() {
		return tbItems.getValue();
	}

	public String[] getValueArr() {
		if (tbItems.getValue().isEmpty())
			return null;
		return tbItems.getValue().split(",");
	}

	private void setValueArr(String[] rtags) {
		if (rtags == null)
			return;
		String s = "";
		for (int i = 0; i < rtags.length; i++) {
			if (!s.isEmpty())
				s = s + "," + rtags[i];
			else
				s = rtags[i];
		}
		tbItems.setValue(s);
	}
	
	public void load(String[] items, List<String[]> all) {
		this.allItems = all;
		setValueArr(items);
		for (int i = 0; i < allItems.size(); i++) {
			lb.addItem(allItems.get(i)[1], allItems.get(i)[0]);
		}
	}


	private class DlgPick extends ActionBarDlg {
		public DlgPick() {
			super("Pick", null, null);
			run();
			show();
			center();
		}

		@Override
		public Widget ui() {
			return lb;
		}

		@Override
		public void cancel() {
		}

		@Override
		public void ok() {
		}
	}

}
