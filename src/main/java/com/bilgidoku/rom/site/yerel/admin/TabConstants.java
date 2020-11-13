package com.bilgidoku.rom.site.yerel.admin;

import java.util.Set;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Trans;
import com.bilgidoku.rom.gwt.araci.client.rom.TransDao;
import com.bilgidoku.rom.gwt.araci.client.rom.TransResponse;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TabConstants extends Composite {

	private final ListBox lbKeys = new ListBox(false);
	protected JSONObject consts = null;
	protected CaptionPanel captForm = new CaptionPanel(Ctrl.trans.value());
	protected boolean modified = false;
	private Button saveButton = new Button(Ctrl.trans.saveAll());
	private Button delKeyButton = new Button(Ctrl.trans.removeSelected());
	private Button addKeyButton = new Button(Ctrl.trans.add(Ctrl.trans.word()));
	private final TextBox tbValue = new TextBox();

	public TabConstants() {
		loadKeys();
		initWidget(ui());
	}

	private Widget ui() {

		HorizontalPanel hp = new HorizontalPanel();
		delKeyButton.setEnabled(false);

		forSave();
		forAddKey();
		forDeleteKey();
		forSelect();

		hp.add(getKeysPanel());

		Button change = new Button(Ctrl.trans.save());
		forValueChange(change);

		HorizontalPanel form = new HorizontalPanel();
		form.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		form.add(new Label(Ctrl.trans.value()));
		form.add(tbValue);
		form.add(change);

		captForm.add(form);

		hp.add(captForm);

		return hp;
	}

	private void forValueChange(Button change2) {
		change2.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String key = lbKeys.getValue(lbKeys.getSelectedIndex());
				consts.put(key, new JSONString(tbValue.getValue()));
			}
		});
	}

	private void loadKeys() {
		TransDao.getall("/_/_trans", new TransResponse() {
			@Override
			public void ready(Trans value) {
				JSONValue val = value.constants.getValue();
				if (val != null && val.isObject() != null)
					consts = val.isObject();
				setModified(false, null, null);
				Set<String> keys = consts.keySet();
				for (String key : keys) {
					lbKeys.addItem(key);
				}
			}
		});

	}

	private void forSave() {
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (modified) {
					TransDao.changeconstants(new Json(consts), "/_/_trans", new StringResponse() {
						@Override
						public void ready(String value) {

							setModified(false, null, null);
						}
					});

				}
			}
		});

	}

	private void forAddKey() {
		addKeyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String key = Window.prompt(Ctrl.trans.word(), "");

				if (key != null && key.length() > 0 && !consts.containsKey(key)) {
					consts.put(key, new JSONString(""));
					setModified(true, key, null);
					lbKeys.insertItem(key, lbKeys.getItemCount());
					lbKeys.setSelectedIndex(lbKeys.getItemCount() - 1);
					delKeyButton.setEnabled(true);

				}
			}
		});
	}

	private void forSelect() {
		lbKeys.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String key = lbKeys.getValue(lbKeys.getSelectedIndex());
				delKeyButton.setEnabled(true);
				setModified(true, key, consts.get(key).isString().stringValue());
			}
		});

	}

	private void forDeleteKey() {
		delKeyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (lbKeys.getSelectedIndex() > 0) {
					String key = lbKeys.getValue(lbKeys.getSelectedIndex());
					lbKeys.removeItem(lbKeys.getSelectedIndex());
					consts.put(key, null);
					delKeyButton.setEnabled(false);
					setModified(true, null, null);
					captForm.setVisible(false);

				}
			}
		});

	}

	private VerticalPanel getKeysPanel() {
		lbKeys.setWidth("100%");
		lbKeys.setVisibleItemCount(20);

		Widget[] buttons = { saveButton, addKeyButton, delKeyButton };

		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(5);
		vp.setVerticalAlignment(VerticalPanel.ALIGN_TOP);
		vp.setStyleName("site-innerform", true);
		vp.setSize("340px", "50px");
		vp.add(ClientUtil.getToolbar(buttons, 4));
		vp.add(lbKeys);

		return vp;

	}

	private void setModified(boolean b, String key, String value) {
		modified = b;
		saveButton.setEnabled(b);
		if (b) {
			captForm.setVisible(true);
			captForm.setCaptionText(key);
			tbValue.setValue(value);
		} else {
			lbKeys.setSelectedIndex(-1);
			captForm.setVisible(false);
		}
	}

	public String getSelectedWord() {
		return lbKeys.getValue(lbKeys.getSelectedIndex());
	}

}
