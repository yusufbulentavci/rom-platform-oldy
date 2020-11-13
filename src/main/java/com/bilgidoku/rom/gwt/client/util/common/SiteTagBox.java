package com.bilgidoku.rom.gwt.client.util.common;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Tags;
import com.bilgidoku.rom.gwt.araci.client.rom.TagsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.TagsResponse;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;

public class SiteTagBox extends Composite {
	private final MultiWordSuggestOracle oracleTags = new MultiWordSuggestOracle();
	private final SuggestBox tagSuggest = new SuggestBox(oracleTags);
	private final Button btnAdd = new Button("Add Tag to Selected");
	private final TextBox tbTags = new TextBox();
	private List<String> allTags;
	
	public SiteTagBox() {
		TagsDao.list("en", Data.TAG_ROOT, new TagsResponse() {
			@Override
			public void array(List<Tags> value) {
				if (value == null || value.size() <= 0)
					return;
				loadOracle(value);
			}
		});
		tbTags.addStyleName("grey");
		
		btnAdd.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final String tag = tagSuggest.getValue();
				if (tag.isEmpty())
					return;
				
				if (tbTags.getValue() == null || tbTags.getValue().isEmpty()) {
					tbTags.setValue(tag);
					return;
				}
				tbTags.setValue(tbTags.getValue() + "," + tag);
				tagSuggest.setValue(null);
				if (!allTags.contains(tag)) {
					TagsDao.neww("en", tag, null, "/_/tags", new StringResponse() {
						public void ready(String value) {
							allTags.add(tag);
						};
					});
				}
			}
		});
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(tagSuggest);
		hp.add(btnAdd);
		hp.add(tbTags);
		initWidget(hp);


	}
	
	private void loadOracle(List<Tags> tagList) {
		if (allTags == null)
			allTags = new ArrayList<String>();
		for (int i = 0; i < tagList.size(); i++) {
			oracleTags.add(tagList.get(i).title[0]);
			allTags.add(tagList.get(i).title[0]);
		}
	}

	public String[] getValue() {
		String[] tags = null;
		if (tbTags.getValue() != null && !tbTags.getValue().trim().isEmpty()) {
			tags = tbTags.getValue().split(",");
		}
		return tags;
	}

	public void setValue(String[] rtags) {
		if (rtags == null)
			return;
		String s = "";
		for (int i = 0; i < rtags.length; i++) {
			if (!s.isEmpty())
				s = s + "," + rtags[i];
			else
				s = rtags[i];
		}
		tbTags.setValue(s);
	}


}
