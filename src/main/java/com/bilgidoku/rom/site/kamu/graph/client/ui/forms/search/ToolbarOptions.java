package com.bilgidoku.rom.site.kamu.graph.client.ui.forms.search;

import com.bilgidoku.rom.gwt.client.util.browse.image.search.Provider;
import com.bilgidoku.rom.gwt.client.util.browse.image.search.SearchParams;
import com.bilgidoku.rom.gwt.client.util.common.SearchCallback;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ToolbarOptions extends Composite {
	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);
	
	final CheckBox layAll = new CheckBox(trans.all());
	final CheckBox laySquare = new CheckBox(trans.square());
	final CheckBox layTall = new CheckBox(trans.tall());
	final CheckBox layWide = new CheckBox(trans.wide());
	final TextBox txtPhrase = new TextBox();
	final CheckBox sizeAll = new CheckBox(trans.all());
	final CheckBox sizeSmall = new CheckBox(trans.small());
	final CheckBox sizeMedium = new CheckBox(trans.medium());
	final CheckBox sizeLarge = new CheckBox(trans.large());
	final CheckBox colorAll = new CheckBox(trans.all());
	final CheckBox colorColor = new CheckBox(trans.colorful());
	final CheckBox colorMono = new CheckBox(trans.monochrome());
	final CheckBox faceAll = new CheckBox(trans.all());
	final CheckBox faceFace = new CheckBox(trans.face());
	final CheckBox facePortrait = new CheckBox(trans.portrait());
	final CheckBox faceOther = new CheckBox(trans.other());
	final CheckBox styleAll = new CheckBox(trans.all());
	final CheckBox stylePhoto = new CheckBox(trans.photo());
	final CheckBox styleGraphics = new CheckBox(trans.graphics());
	final CheckBox chkExplicit = new CheckBox();
	final ListBox providers = new ListBox();
	final ListBox readyMediaTypes = new ListBox();
	final SiteButton btnSearch = new SiteButton("/_public/images/bar/search_.png", trans.search(), trans.search(), "");
	final DisclosurePanel opt = new DisclosurePanel(trans.options());
	
	private SearchCallback callback;	

	public ToolbarOptions(SearchCallback callback) {
		
		this.callback = callback;
		
		btnSearch.setStyleName("site-smlbtn");
		
		providers.addItem("bing", Provider.BING + "");
		providers.addItem("yaymicro", Provider.YAYMICRO + "");
		providers.addItem("pixabay", Provider.PIXABAY + "");
		providers.addItem("pdp", Provider.PUBLICDOMAINPICTURES + "");

		readyMediaTypes.setVisible(false);
		readyMediaTypes.addItem("icon", "1"); // OK
		readyMediaTypes.addItem("page", "3"); // OK
		readyMediaTypes.addItem("banner", "2"); // OK
		readyMediaTypes.addItem("background", "4"); // OK
		readyMediaTypes.addItem("back pattern", "5"); // ?

		forChangeProvider();
		forTxtPhrase();
		forSearch();
		initWidget(ui());
	}

	private void forChangeProvider() {
		providers.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				readyMediaTypes.setVisible(false);
				txtPhrase.setVisible(true);
				opt.setVisible(true);
				String val = providers.getValue(providers.getSelectedIndex());
				if (val.equals("3") || val.equals("4")) {
					readyMediaTypes.setVisible(true);
					txtPhrase.setVisible(false);
					opt.setVisible(false);
				}

			}
		});
	}

	private void forSearch() {
		btnSearch.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final String phrase = txtPhrase.getValue();
				String provider = providers.getValue(providers.getSelectedIndex());
				if (!(provider.equals("3") || provider.equals("4")))
					if (phrase == null || phrase.length() == 0) {
						Window.alert(trans.emptyValue(trans.phrase()));
						return;
					}
				callback.newSearch(getParams());
			}
		});
	}

	private void forTxtPhrase() {
		txtPhrase.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				int k = event.getNativeKeyCode();
				if (k == 13) {
					callback.newSearch(getParams());
				}
			}
		});
	}

	private Widget ui() {
		txtPhrase.setWidth("100px");
		txtPhrase.setStyleName("site-box");
		// opts.setSize("250px", "100%");
		chkExplicit.setName("filter_explicit");

		HorizontalPanel f = new HorizontalPanel();
		f.setStyleName("site-padding");
		f.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
//		f.add(providers);
//		f.add(readyMediaTypes);
		f.add(txtPhrase);
		f.add(btnSearch);


		FlexTable opts = new FlexTable();
		opts.setHTML(0, 0, trans.size());
		opts.setWidget(0, 1, getSizePanel());
		opts.setHTML(0, 2, trans.layout());
		opts.setWidget(0, 3, getLayoutPanel());

		opts.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
		opts.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		opts.getCellFormatter().setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_TOP);
		opts.getCellFormatter().setVerticalAlignment(0, 3, HasVerticalAlignment.ALIGN_TOP);

		opts.setHTML(0, 4, trans.colorful());
		opts.setWidget(0, 5, getColorPanel());
		opts.setHTML(0, 6, trans.people());
		opts.setWidget(0, 7, getFacePanel());

		opts.getCellFormatter().setVerticalAlignment(0, 4, HasVerticalAlignment.ALIGN_TOP);
		opts.getCellFormatter().setVerticalAlignment(0, 5, HasVerticalAlignment.ALIGN_TOP);
		opts.getCellFormatter().setVerticalAlignment(0, 6, HasVerticalAlignment.ALIGN_TOP);
		opts.getCellFormatter().setVerticalAlignment(0, 7, HasVerticalAlignment.ALIGN_TOP);

		opts.setHTML(0, 8, trans.style());
		opts.setWidget(0, 9, getStylePanel());
		opts.getCellFormatter().setVerticalAlignment(0, 8, HasVerticalAlignment.ALIGN_TOP);
		opts.getCellFormatter().setVerticalAlignment(0, 9, HasVerticalAlignment.ALIGN_TOP);

		opt.setContent(opts);
		opt.setAnimationEnabled(true);
		opt.getElement().getStyle().setZIndex(1000);
		opt.setOpen(true);
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(f);
		vp.add(opt);
		return vp;

	}

	private VerticalPanel getColorPanel() {
		colorMono.setValue(true);
		colorColor.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				colorAll.setValue(false);
			}
		});
		colorMono.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				colorAll.setValue(false);
			}
		});
		VerticalPanel colorPanel = new VerticalPanel();
		// colorPanel.setWidth("90px");
		colorPanel.add(colorAll);
		colorPanel.add(colorColor);
		colorPanel.add(colorMono);
		return colorPanel;
	}

	private VerticalPanel getFacePanel() {
		faceAll.setValue(true);
		faceFace.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				faceAll.setValue(false);
			}
		});
		facePortrait.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				faceAll.setValue(false);
			}
		});
		faceOther.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				faceAll.setValue(false);
			}
		});
		VerticalPanel facePanel = new VerticalPanel();
		facePanel.setWidth("110px");
		facePanel.add(faceAll);
		facePanel.add(faceFace);
		facePanel.add(facePortrait);
		facePanel.add(faceOther);
		return facePanel;
	}

	private VerticalPanel getStylePanel() {
		styleGraphics.setValue(true);
		stylePhoto.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				styleAll.setValue(false);
			}
		});
		styleGraphics.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				styleAll.setValue(false);
			}
		});
		VerticalPanel stylePanel = new VerticalPanel();
		stylePanel.setWidth("90px");
		stylePanel.add(styleAll);
		stylePanel.add(stylePhoto);
		stylePanel.add(styleGraphics);
		return stylePanel;
	}

	private VerticalPanel getLayoutPanel() {
		layAll.setValue(true);
		laySquare.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				layAll.setValue(false);
			}
		});
		layTall.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				layAll.setValue(false);
			}
		});
		layWide.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				layAll.setValue(false);
			}
		});
		VerticalPanel layoutPanel = new VerticalPanel();
		layoutPanel.setWidth("90px");
		layoutPanel.add(layAll);
		layoutPanel.add(laySquare);
		layoutPanel.add(layTall);
		layoutPanel.add(layWide);
		return layoutPanel;
	}

	private VerticalPanel getSizePanel() {
		sizeLarge.setValue(true);		
		sizeSmall.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				sizeAll.setValue(false);
			}
		});
		sizeMedium.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				sizeAll.setValue(false);
			}
		});
		sizeLarge.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				sizeAll.setValue(false);
			}
		});
		VerticalPanel sizePanel = new VerticalPanel();
		sizePanel.setWidth("90px");
		sizePanel.add(sizeAll);
		sizePanel.add(sizeSmall);
		sizePanel.add(sizeMedium);
		sizePanel.add(sizeLarge);
		return sizePanel;
	}

	public SearchParams getParams() {
		SearchParams sp = new SearchParams();
		sp.phrase = txtPhrase.getValue();		
		if (sizeSmall.getValue())
			sp.size = "Small";
		if (sizeMedium.getValue())
			sp.size = "Medium";
		if (sizeLarge.getValue())
			sp.size = "Large";

		if (laySquare.getValue())
			sp.aspect = "Square";
		if (layWide.getValue())
			sp.aspect = "Wide";
		if (layTall.getValue())
			sp.aspect = "Tall";

		if (colorColor.getValue())
			sp.color = "Color";
		if (colorMono.getValue())
			sp.color = "Monochrome";

		if (styleGraphics.getValue())
			sp.style = "Graphics";
		if (stylePhoto.getValue())
			sp.style = "Photo";

		if (faceFace.getValue())
			sp.face = "Face";
		if (facePortrait.getValue())
			sp.face = "Portrait";
		if (faceOther.getValue())
			sp.face = "Other";

		String provider = providers.getValue(providers.getSelectedIndex());

		sp.provider = Integer.parseInt(provider);

		if (provider.equals("3") || provider.equals("4")) {
			sp.phrase = readyMediaTypes.getValue(readyMediaTypes.getSelectedIndex());
		}

		return sp;

	}

	public void closeOptions() {
		opt.setOpen(false);
	}


}
