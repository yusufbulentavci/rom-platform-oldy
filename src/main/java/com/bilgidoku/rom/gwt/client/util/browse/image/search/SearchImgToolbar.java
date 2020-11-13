package com.bilgidoku.rom.gwt.client.util.browse.image.search;

import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.util.common.TopButton;
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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SearchImgToolbar extends Composite {
	
	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);
	
	final CheckBox pixabay = new CheckBox("Pixabay");
	final CheckBox pdp = new CheckBox("PDP");
	final TextBox txtPhrase = new TextBox();
	
	final CheckboxForSearch laySquare = new CheckboxForSearch(trans.square());
	final CheckboxForSearch layTall = new CheckboxForSearch(trans.tall());
	final CheckboxForSearch layWide = new CheckboxForSearch(trans.wide());


	final CheckboxForSearch sizeSmall = new CheckboxForSearch(trans.small());
	final CheckboxForSearch sizeMedium = new CheckboxForSearch(trans.medium());
	final CheckboxForSearch sizeLarge = new CheckboxForSearch(trans.large());

	final CheckboxForSearch colorColor = new CheckboxForSearch(trans.colorful());
	final CheckboxForSearch colorMono = new CheckboxForSearch(trans.monochrome());
	
	final CheckboxForSearch faceFace = new CheckboxForSearch(trans.face());
	final CheckboxForSearch facePortrait = new CheckboxForSearch(trans.portrait());
	final CheckboxForSearch faceOther = new CheckboxForSearch(trans.other());
	
	final CheckboxForSearch stylePhoto = new CheckboxForSearch(trans.photo());
	final CheckboxForSearch styleGraphics = new CheckboxForSearch(trans.graphics());

	final CheckboxForSearch[] btnStyles = {styleGraphics, stylePhoto};
	final CheckboxForSearch[] btnSizes = {sizeLarge, sizeMedium, sizeSmall};
	final CheckboxForSearch[] btnColors = {colorColor, colorMono};
	final CheckboxForSearch[] btnFaces = {faceOther, faceFace, facePortrait};
	final CheckboxForSearch[] btnLays = {laySquare, layTall, layWide};

	
	final CheckboxForSearch layAll = new CheckboxForSearch(trans.all(), btnLays);
	final CheckboxForSearch sizeAll = new CheckboxForSearch(trans.all(), btnSizes);	
	final CheckboxForSearch colorAll = new CheckboxForSearch(trans.all(), btnColors);
	final CheckboxForSearch faceAll = new CheckboxForSearch(trans.all(), btnFaces);
	final CheckboxForSearch styleAll = new CheckboxForSearch(trans.all(), btnStyles);
	
	final CheckBox chkExplicit = new CheckBox();
	
	
	final ListBox providers = new ListBox();	
	final ListBox readyMediaTypes = new ListBox();
	final TopButton btnSearch = new TopButton("/_public/images/bar/search_.png", "", trans.search(), "");
	final PnlImageSearch caller;
	final DisclosurePanel opt = new DisclosurePanel(trans.options());
	
	private SiteButton btnPrev = new SiteButton("/_public/images/bar/go_left.png", "", trans.previousPage(), "");
	private SiteButton btnNext = new SiteButton("/_public/images/bar/go_right.png", "", trans.nextPage(), "");
	
	private HTML pageInfo = new HTML();
	

	public SearchImgToolbar(PnlImageSearch caller1) {
		this.caller = caller1;
		btnPrev.addStyleName("btn-sml");
		btnNext.addStyleName("btn-sml");
		
		
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

		forNext();
		forPrev();

		forChangeProvider();
		// providers.addItem("google", Data.google+"");
		// providers.addItem("fotolia", Data.fotolia+"");
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
				caller.newSearch(getParams());
			}
		});
	}

	private void forTxtPhrase() {
		txtPhrase.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				int k = event.getNativeKeyCode();
				if (k == 13) {
					caller.newSearch(getParams());
				}
			}
		});
	}

	private Widget ui() {
		txtPhrase.setWidth("100px");
		// opts.setSize("250px", "100%");
		chkExplicit.setName("filter_explicit");

		HorizontalPanel f = new HorizontalPanel();
		f.setStyleName("site-padding");
		f.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
//		f.add(providers);
//		f.add(readyMediaTypes);
		f.add(txtPhrase);
		f.add(btnSearch);		
		f.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;"));
		f.add(btnPrev);
		f.add(pageInfo);
		f.add(btnNext);


		FlexTable opts = new FlexTable();
		
		opts.setHTML(0, 0, trans.size());		
		opts.setHTML(0, 1, trans.layout());
		opts.setHTML(0, 2, trans.people());
		opts.setHTML(0, 3, trans.colorful());
		opts.setHTML(0, 4, trans.style());
		opts.setHTML(0, 5, trans.publicDomain());
		

		opts.setWidget(1, 0, getSizePanel());
		opts.setWidget(1, 1, getLayoutPanel());		
		opts.setWidget(1, 2, getFacePanel());
		opts.setWidget(1, 3, getColorPanel());		
		opts.setWidget(1, 4, getStylePanel());
		opts.setWidget(1, 5, getPublicPanel());
		

		opt.setContent(opts);
		opt.setAnimationEnabled(true);
		opt.getElement().getStyle().setZIndex(1000);

		
		VerticalPanel vp = new VerticalPanel();
		vp.add(f);
		vp.add(opt);
		return vp;

	}

	private Widget getPublicPanel() {		
		VerticalPanel pnl = new VerticalPanel();
		pnl.setWidth("90px");
		pnl.add(pixabay);
		pnl.add(pdp);
		return pnl;
	}

	private void forPrev() {
		btnPrev.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {				 
				if (caller.offset - caller.PAGESIZE < 0) {
					return;
				}
				caller.offset = caller.offset - caller.PAGESIZE;
				caller.makeSearch();
			}
		});

	}

	private void forNext() {
		btnNext.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				caller.offset = caller.offset + caller.PAGESIZE;
				caller.makeSearch();
			}
		});
	}

	private VerticalPanel getColorPanel() {
		colorAll.setValue(true);
		final CheckboxForSearch[] all = {colorAll};
		for (int i = 0; i < btnColors.length; i++) {
			btnColors[i].setMutuals(all);
		}
		VerticalPanel colorPanel = new VerticalPanel();
		// colorPanel.setWidth("90px");
		colorPanel.add(colorAll);
		colorPanel.add(colorColor);
		colorPanel.add(colorMono);
		return colorPanel;
	}

	private VerticalPanel getFacePanel() {
		faceAll.setValue(true);
		
		final CheckboxForSearch[] all = {faceAll};
		for (int i = 0; i < btnFaces.length; i++) {
			btnFaces[i].setMutuals(all);
		}

		
		VerticalPanel facePanel = new VerticalPanel();
		facePanel.setWidth("90px");
		facePanel.add(faceAll);
		facePanel.add(faceFace);
		facePanel.add(facePortrait);
		facePanel.add(faceOther);
		return facePanel;
	}

	private VerticalPanel getStylePanel() {
		styleAll.setValue(true);
		final CheckboxForSearch[] all = {styleAll};
		for (int i = 0; i < btnStyles.length; i++) {
			btnStyles[i].setMutuals(all);
		}
		VerticalPanel stylePanel = new VerticalPanel();
		stylePanel.setWidth("90px");
		stylePanel.add(styleAll);
		stylePanel.add(stylePhoto);
		stylePanel.add(styleGraphics);
		return stylePanel;
	}

	private VerticalPanel getLayoutPanel() {
		layAll.setValue(true);
		final CheckboxForSearch[] all = {layAll};
		for (int i = 0; i < btnLays.length; i++) {
			btnLays[i].setMutuals(all);
		}
		VerticalPanel layoutPanel = new VerticalPanel();
		layoutPanel.setWidth("90px");
		layoutPanel.add(layAll);
		layoutPanel.add(laySquare);
		layoutPanel.add(layTall);
		layoutPanel.add(layWide);
		return layoutPanel;
	}

	private VerticalPanel getSizePanel() {
		sizeSmall.setValue(true);
		final CheckboxForSearch[] all = {sizeAll};
		for (int i = 0; i < btnSizes.length; i++) {
			btnSizes[i].setMutuals(all);
		}
		
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

	public void hidePager() {
		pageInfo.setVisible(false);
		btnPrev.setVisible(false);
		btnNext.setVisible(false);
	}

	public void showPager(String string) {
		pageInfo.setHTML(string);
		pageInfo.setVisible(true);
		btnPrev.setVisible(true);
		btnNext.setVisible(true);
	}
	
	public boolean isPixabay() {
		return pixabay.getValue();
	}

	public boolean isPdp() {
		return pdp.getValue();
	}

}
