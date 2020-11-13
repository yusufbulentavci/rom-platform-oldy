package com.bilgidoku.rom.site.yerel.subpanels;

import com.bilgidoku.rom.gwt.client.common.resp.IntegerResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.gwt.araci.client.site.ContentsDao;
import com.bilgidoku.rom.gwt.araci.client.site.Writings;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.widgets.ImageBox;
import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.gwt.client.widgets.InputChangedHandler;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.SiteIntegerBox;
import com.bilgidoku.rom.site.yerel.common.content.HasContent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class PnlContent extends Composite implements HasContent {

	private final PageLangList pnlLang;
	private TextBox txtTitle = new TextBox();
	public TextArea txtSummary = new TextArea();
	private SiteIntegerBox txtWeight = new SiteIntegerBox();
	private ImageBox imgIcon = new ImageBox();
	private ImageBox imgMedium = new ImageBox();
	private ImageBox imgLarge = new ImageBox();

	// private ImageBox imgMultiLang = new ImageBox();
	protected final HasContent caller;
	private boolean titleChanged = false;
	private boolean summChanged = false;
	private boolean smlImgChanged = false;
	private boolean medImgChanged = false;
	private boolean larImgChanged = false;
	private boolean weightChanged = false;

	private String[] langcodes;
	public String uri;

	public PnlContent(HasContent caller, boolean showLang, boolean hasImages, boolean hasHeader) {

		this.caller = caller;
		pnlLang = new PageLangList(this);

		forChangeTitle();
		forChangeSummary();
		forChangeSmallIcon();
		forChangeMediumIcon();
		forChangeLargeIcon();		
		forChangeWeight();

		txtTitle.setWidth("95%");
		txtWeight.setWidth("40px");
		txtSummary.setSize("95%", "50px");

		FlexTable ft = new FlexTable();

		if (hasHeader) {
			ft.setWidget(0, 0, ClientUtil.getHeader(Ctrl.trans.more(), "/_local/images/common/pencil.png"));
			ft.setStyleName("site-innerform");
			ft.addStyleName("site-padding");
		}

		if (showLang) {
			ft.setHTML(1, 0, Ctrl.trans.lang());
			ft.setWidget(1, 1, pnlLang);
		}

		ft.setHTML(2, 0, Ctrl.trans.title());
		ft.setWidget(3, 0, txtTitle);

		ft.setHTML(4, 0, Ctrl.trans.summary());
		ft.setWidget(5, 0, txtSummary);

		if (hasImages) {

			ft.setHTML(6, 0, Ctrl.trans.icon());
			ft.setHTML(6, 1, Ctrl.trans.mediumIcon());
			ft.setHTML(6, 2, Ctrl.trans.largeIcon());
			
			ft.setWidget(7, 0, imgIcon);			
			ft.setWidget(7, 1, imgMedium);			
			ft.setWidget(7, 2, imgLarge);

		}

		ft.setHTML(8, 0, Ctrl.trans.weight());
		ft.setWidget(8, 1, txtWeight);

		ft.getFlexCellFormatter().setWidth(0, 0, "80px");
		ft.getFlexCellFormatter().setColSpan(5, 0, 3);
		ft.getFlexCellFormatter().setColSpan(4, 0, 3);
		ft.getFlexCellFormatter().setColSpan(3, 0, 3);
		ft.getFlexCellFormatter().setColSpan(2, 0, 3);
		ft.getFlexCellFormatter().setColSpan(0, 0, 3);

		ft.setHeight("50px");

		initWidget(ft);
	}

	private void forChangeSmallIcon() {
		imgIcon.addChangedHandler(new InputChangedHandler() {			
			@Override
			public void changed(InputChanged event) {
				smlImgChanged = true;
				
				if (imgMedium.getImgPath() == null) {					
					imgMedium.setImage(imgIcon.getImgPath());
					medImgChanged = true;
				}
				
				if (imgLarge.getImgPath() == null) {					
					imgLarge.setImage(imgMedium.getImgPath());
					larImgChanged = true;
				}

			}
		});

	}

	private void forChangeWeight() {
		txtWeight.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				weightChanged = true;
			}
		});
	}

	private void forChangeLargeIcon() {
		imgLarge.addChangedHandler(new InputChangedHandler() {
			@Override
			public void changed(InputChanged event) {
				larImgChanged = true;

			}
		});
	}

	private void forChangeMediumIcon() {
		imgMedium.addChangedHandler(new InputChangedHandler() {
			@Override
			public void changed(InputChanged event) {
				medImgChanged = true;
				
				if (imgLarge.getImgPath() == null) {					
					imgLarge.setImage(imgMedium.getImgPath());
					larImgChanged = true;
				}

				
				caller.contentChanged();
			}
		});

	}

	private void forChangeSummary() {
		txtSummary.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				caller.contentChanged();
				summChanged = true;
			}
		});
	}

	private void forChangeTitle() {
		txtTitle.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				titleChanged = true;
				caller.contentChanged();
			}
		});

	}

	public void load(Writings writing) {
		imgIcon.setImage(writing.icon);
		imgMedium.setImage(writing.medium_icon);
		imgLarge.setImage(writing.large_icon);
		txtTitle.setValue(writing.title[0]);
		txtSummary.setValue(writing.summary[0]);
		txtWeight.setIntValue(writing.weight);
		uri = writing.uri;
		// ???
		langcodes = writing.langcodes;

	}

	public void load(String uri1, String medIcon, String larIcon, String icon, String title, String summary,
			String[] langcodes1, Integer weight) {
		uri = uri1;
		imgMedium.setImage(medIcon);
		imgLarge.setImage(larIcon);
		imgIcon.setImage(icon);
		txtTitle.setValue(title);
		txtSummary.setValue(summary);
		txtWeight.setIntValue(weight);
		pnlLang.loadLang(langcodes1[0], langcodes1, Ctrl.info().langcodes);
		langcodes = langcodes1;
	}

	public String getMedIcon() {
		return imgMedium.getImgPath();
	}

	public String getIcon() {
		return imgIcon.getImgPath();
	}

	public String getPageTitle() {
		return txtTitle.getValue();
	}

	public String getSummary() {
		return txtSummary.getValue();
	}

	public String getLargeIcon() {
		return imgLarge.getImgPath();
	}

	public void reset() {
		imgIcon.setImage(null);
		imgMedium.setImage(null);
		imgLarge.setImage(null);
		// imgMultiLang.setImage(null);
		txtTitle.setValue("");
		txtSummary.setValue("");
		txtWeight.setValue(null);

	}

	public void save(String uri, String lang) {
		if (titleChanged) {
			ContentsDao.title(lang, getPageTitle(), uri, new StringResponse() {
				@Override
				public void ready(String value) {
					titleChanged = false;
					Ctrl.setStatus("Title saved");
				}
			});
		}

		if (summChanged) {
			ContentsDao.summary(lang, getSummary(), uri, new StringResponse() {
				@Override
				public void ready(String value) {
					summChanged = false;
					Ctrl.setStatus("Summary saved");
				}
			});

		}

		if (medImgChanged) {
			ContentsDao.mediumicon(getMedIcon(), uri, new StringResponse() {
				@Override
				public void ready(String value) {
					medImgChanged = false;
					Ctrl.setStatus("Med Icon saved");
				}
			});
		}

		if (larImgChanged) {
			ContentsDao.largeicon(getLargeIcon(), uri, new StringResponse() {
				@Override
				public void ready(String value) {
					larImgChanged = false;
					Ctrl.setStatus("Large Icon saved");
				}
			});
		}

		if (smlImgChanged) {
			ContentsDao.icon(getIcon(), uri, new StringResponse() {
				@Override
				public void ready(String value) {
					smlImgChanged = false;
					Ctrl.setStatus("Small Icon saved");
				}
			});
		}


		if (weightChanged) {
			ResourcesDao.setweight(Integer.parseInt(txtWeight.getText()), uri, new IntegerResponse() {
				@Override
				public void ready(Integer value) {
					weightChanged = false;
					Ctrl.setStatus("Weight saved");
				}
			});
		}

	}

	public String getSelectedLang() {
		return pnlLang.getSelectedLang();
	}

	public void loadLang(String lang, String[] langcodes) {
		pnlLang.loadLang(lang, langcodes, Ctrl.info().langcodes);
	}

	public boolean changed() {
		return titleChanged || summChanged || medImgChanged || larImgChanged;
	}

	@Override
	public void contentChanged() {
		caller.contentChanged();
	}

	@Override
	public void langChanged(final String selectedLang) {
		if (ClientUtil.existInArray(langcodes, selectedLang)) {
			caller.langChanged(selectedLang);
		} else {
			ContentsDao.newlang(selectedLang, uri, new StringResponse() {
				@Override
				public void ready(String value) {
					String[] newLangcodes = new String[langcodes.length + 1];
					for (int i = 0; i < langcodes.length; i++) {
						newLangcodes[i] = langcodes[i];
					}
					newLangcodes[langcodes.length] = selectedLang;

					langcodes = newLangcodes;

					loadLang(selectedLang, langcodes);

				}
			});

		}

	}

}
