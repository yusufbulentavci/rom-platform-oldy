package com.bilgidoku.rom.site.yerel.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Trans;
import com.bilgidoku.rom.gwt.araci.client.rom.TransDao;
import com.bilgidoku.rom.gwt.araci.client.rom.TransResponse;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TabDictionary extends Composite {
	private final FlexTable changeValueForm = new FlexTable();
	private final ListBox lbKeys = new ListBox(false);
	protected Trans trans;
	protected CaptionPanel transCaption = new CaptionPanel("");
	protected boolean modified = false;
	private Button saveButton = new Button(Ctrl.trans.saveAll());
	private Button delKeyButton = new Button(Ctrl.trans.removeSelected());
	private Button addKeyButton = new Button(Ctrl.trans.add(Ctrl.trans.word()));
//	private Button addLangButton = new Button(Ctrl.trans.add(Ctrl.trans.lang()));

	public TabDictionary() {
		loadKeysWords();
		initWidget(ui());
	}

	public String getSelectedWord() {
		return lbKeys.getValue(lbKeys.getSelectedIndex());
	}

	private Widget ui() {
		HorizontalPanel hp = new HorizontalPanel();
		delKeyButton.setEnabled(false);
		forSelect();

		forSave();
		forAddKey();
		forDeleteKey();
//		forAddLang();
		hp.add(getKeysPanel());

		transCaption.setVisible(false);
		transCaption.add(changeValueForm);

		hp.add(transCaption);
		return hp;
	}

	private void loadKeysWords() {
		TransDao.getall("/_/_trans", new TransResponse() {
			@Override
			public void ready(Trans value) {
				trans = value;
				updateKeys();
				setModified(false);
			}
		});

	}

	private void forSave() {
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (modified)
					TransDao.changeall(trans.langcodes, trans.title, "/_/_trans", new StringResponse() {
						@Override
						public void ready(String value) {
							Ctrl.setStatus(Ctrl.trans.saved());
							setModified(false);
						}
					});
			}
		});

	}

	private void forAddKey() {
		addKeyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String key = Window.prompt(Ctrl.trans.word(), "");
				if (key != null && key.length() > 0) {
					lbKeys.insertItem(key, 0);
					lbKeys.setSelectedIndex(0);
					addKeyToData(key);

					setModified(true);
					showTranslationPanel(key);

				}
			}
		});
	}

	private void forSelect() {
		lbKeys.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String key = lbKeys.getValue(lbKeys.getSelectedIndex());
				showTranslationPanel(key);
				delKeyButton.setEnabled(true);
			}
		});

	}

	private void forDeleteKey() {
		delKeyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (lbKeys.getSelectedIndex() > -1) {
					String key = lbKeys.getValue(lbKeys.getSelectedIndex());
					lbKeys.removeItem(lbKeys.getSelectedIndex());
					setModified(true);
					transCaption.setVisible(false);
					rmvKeyFromData(key);
					delKeyButton.setEnabled(false);
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

	private void showTranslationPanel(String key) {
		
		changeValueForm.removeAllRows();
		changeValueForm.clear();

		transCaption.setCaptionText(key);
		for (int i = 0; i < trans.langcodes.length; i++) {
			String lang = trans.langcodes[i];
			Json tj = trans.title[i];
			JSONObject jo = tj.getValue().isObject();
			JSONValue val = jo.get(key);
			if (val == null)
				addLineToTranslations(lang, key, "");
			else
				addLineToTranslations(lang, key, val.isString().stringValue());
		}

		transCaption.setVisible(true);
	}

	protected void addLineToTranslations(String lang, String key, String value) {
		int i = changeValueForm.getRowCount();
		changeValueForm.setHTML(i + 1, 0, lang);
		changeValueForm.setWidget(i + 1, 1, getTextBox(lang, key, value));

	}

	private Widget getTextBox(final String lang, final String key, String value) {
		final TextBox tb = new TextBox();
		tb.setWidth("250px");
		tb.setValue(value);
		tb.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				modify(lang, key, tb.getValue());
			}
		});
		return tb;
	}

	protected void modify(String lang, String key, String value) {
		for (int i = 0; i < trans.langcodes.length; i++) {
			String l = trans.langcodes[i];
			if (lang.equals(l)) {
				Json tj = trans.title[i];
				JSONObject jo = tj.getValue().isObject();
				jo.put(key, new JSONString(value));
				setModified(true);
			}
		}
	}

	protected void addKeyToData(String key) {
		for (int i = 0; i < trans.langcodes.length; i++) {
			Json tj = trans.title[i];
			JSONObject jo = tj.getValue().isObject();
			jo.put(key, new JSONString(""));
		}
	}

	protected void rmvKeyFromData(String key) {
		for (int i = 0; i < trans.langcodes.length; i++) {
			Json tj = trans.title[i];
			JSONObject jo = tj.getValue().isObject();
			jo.put(key, null);
		}
	}

	protected void addLang(String lang) {
		String[] langcodes = new String[trans.langcodes.length + 1];
		Json[] json = new Json[trans.langcodes.length + 1];
		int i = 0;
		for (String l : trans.langcodes) {
			if (l.equals(lang))
				return;
			json[i] = trans.title[i];
			langcodes[i++] = l;
		}
		JSONObject jo = new JSONObject();
		for (int j = 0; j < lbKeys.getItemCount(); j++) {
			jo.put(lbKeys.getValue(j), new JSONString(""));
		}

		langcodes[i] = lang;
		json[i] = new Json(jo);

		trans.title = json;
		trans.langcodes = langcodes;
		setModified(true);

		transCaption.setVisible(false);

	}

	private void setModified(boolean b) {
		modified = b;
		saveButton.setEnabled(b);
	}

//	private void forAddLang() {
//		addLangButton.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				String lang = Window.prompt(Ctrl.trans.lang(), "");
//				if (lang != null && lang.length() == 2) {
//					addLang(lang);
//				}
//			}
//		});
//	}

	protected void updateKeys() {

		HashSet<String> set = new HashSet<String>();
		final List<String> keys = new ArrayList<String>();

		for (int i = 0; i < trans.langcodes.length; i++) {
			Json tj = trans.title[i];
			JSONObject jo = tj.getValue().isObject();
			for (String key : jo.keySet()) {
				set.add(key);
				if (!keys.contains(key))
					keys.add(key);
			}
		}

		Collections.sort(keys);

		for (String word : keys) {
			lbKeys.addItem(word);
		}

	}

}
