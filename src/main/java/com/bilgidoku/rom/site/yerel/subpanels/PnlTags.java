package com.bilgidoku.rom.site.yerel.subpanels;

import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.gwt.araci.client.rom.Tags;
import com.bilgidoku.rom.gwt.araci.client.rom.TagsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.TagsResponse;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.content.HasTags;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;

public class PnlTags extends Composite {

	private final MultiWordSuggestOracle oracleTags = new MultiWordSuggestOracle();
	private final SuggestBox tagSuggest = new SuggestBox(oracleTags);
	private final SiteButton btnAddTag = new SiteButton("/_local/images/common/add.png", "",
			Ctrl.trans.add(Ctrl.trans.tag()), "");
	private final SiteButton btnRmvTag = new SiteButton("/_local/images/common/bin.png", "",
			Ctrl.trans.removeSelected(), "");
	private final ListBox listTags = new ListBox();
	private List<Tags> allTags = null;
	private String lang = Ctrl.infoLang();
	private HasTags caller;

	public PnlTags(HasTags caller) {

		this.caller = caller;
		forSelectFromList();
		forAddTag();
		forRemoveTag();

		listTags.setVisibleItemCount(10);
		tagSuggest.setWidth("130px");
		listTags.setWidth("140px");
		btnRmvTag.setEnabled(false);

		FlexTable hp = new FlexTable();
		hp.setWidget(0, 0, tagSuggest);
		hp.setWidget(0, 1, btnAddTag);
		hp.setWidget(1, 0, listTags);
		hp.setWidget(1, 1, btnRmvTag);
		hp.getFlexCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_MIDDLE);

		initWidget(hp);

		TagsDao.list(lang, Data.TAG_ROOT, new TagsResponse() {
			@Override
			public void array(List<Tags> value) {
				allTags = value;
				loadOracle(value);
			}
		});

	}

	public void loadData(String[] tags) {
		if (tags == null)
			return;

		listTags.clear();
		for (int i = 0; i < tags.length; i++) {
			listTags.addItem(tags[i]);
		}
	}

	private void forSelectFromList() {
		listTags.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (listTags.getSelectedIndex() < 0)
					btnRmvTag.setEnabled(false);
				else
					btnRmvTag.setEnabled(true);
			}
		});
	}

	private void forRemoveTag() {
		btnRmvTag.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (listTags.getSelectedIndex() < 0)
					return;
				listTags.removeItem(listTags.getSelectedIndex());
				btnRmvTag.setEnabled(false);
				caller.tagsChanged();
			}
		});
	}

	private void forAddTag() {
		btnAddTag.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final String addTag = tagSuggest.getValue();
				if (addTag == null || addTag.isEmpty())
					return;

				if (!allTagsContains(addTag)) {
					TagsDao.neww(lang, addTag, null, Data.TAG_ROOT, new StringResponse() {
						@Override
						public void ready(String value) {
							listTags.addItem(addTag);
							caller.tagsChanged();
						}
					});
				} else {
					listTags.addItem(addTag);
					caller.tagsChanged();
				}

			}
		});

	}

	private boolean allTagsContains(String addTag) {
		boolean contains = false;
		for (int i = 0; i < this.allTags.size(); i++) {
			if (this.allTags.get(i).title[0].equals(addTag)) {
				contains = true;
				break;
			}
		}
		return contains;
	}

	private void loadOracle(List<Tags> tagList) {
		for (int i = 0; i < tagList.size(); i++) {
			oracleTags.add(tagList.get(i).title[0]);
		}
	}

	public String[] getTags() {
		String str = "";
		for (int i = 0; i < listTags.getItemCount(); i++) {
			str = str + listTags.getItemText(i) + ",";
		}
		return removeLastChar(str).split(",");
	}

	public String removeLastChar(String str) {
		if (str.length() <= 0)
			return "";
		str = str.substring(0, str.length() - 1);
		return str;
	}

	public void setLang(String contentLang) {
		this.lang = contentLang;
	}

	public void save(String uri2) {
		ResourcesDao.setrtag(getTags(), uri2, new StringResponse() {
			@Override
			public void ready(String value) {
				Ctrl.setStatus("tags saved");
				super.ready(value);
			}
		});
	}
	
}
