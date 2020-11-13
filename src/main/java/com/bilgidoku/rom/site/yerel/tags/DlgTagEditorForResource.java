package com.bilgidoku.rom.site.yerel.tags;

import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.gwt.client.common.resp.ArrayResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.site.yerel.common.content.HasTags;
import com.bilgidoku.rom.site.yerel.subpanels.PnlTags;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DlgTagEditorForResource extends ActionBarDlg implements HasTags {

	private String path;

	String[] parentTags;
	final ListBox parentTagsList = new ListBox();

	private PnlTags pnlTags;

	public DlgTagEditorForResource(String path) {
		super("Etiket", "/_local/images/common/tag.png", "ok");
		this.path = path;
		pnlTags = new PnlTags(this);
	}

	protected void initData() {
		ResourcesDao.parentaltags(path, new ArrayResponse<String>() {

			@Override
			public void ready(String[] value) {
				parentTags = value;
				parentTagsList.setEnabled(true);
				if (value == null) {
					return;
				}
				for (String string : value) {
					parentTagsList.addItem(string);
				}

			}

		});

		ResourcesDao.getrtags(path, new ArrayResponse<String>() {

			@Override
			public void ready(String[] value) {
				if (value == null) {
					return;
				}
				pnlTags.loadData(value);
			}

		});

	}

	@Override
	public Widget ui() {
		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(4);
		parentTagsList.setVisibleItemCount(5);
		parentTagsList.setWidth("130px");
		parentTagsList.setMultipleSelect(false);
		parentTagsList.setEnabled(false);
		vp.add(parentTagsList);
		vp.add(pnlTags);

		return vp;
	}

	@Override
	public void cancel() {

	}

	@Override
	public void ok() {

	}

	@Override
	public void tagsChanged() {
		ResourcesDao.setrtag(pnlTags.getTags(), path, new StringResponse() {

		});
	}

}
