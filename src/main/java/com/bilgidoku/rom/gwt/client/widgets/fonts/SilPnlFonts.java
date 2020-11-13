package com.bilgidoku.rom.gwt.client.widgets.fonts;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.util.browse.image.search.SearchParams;
import com.bilgidoku.rom.gwt.client.util.common.SearchCallback;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SingleSelectionModel;

public class SilPnlFonts extends Composite {
	
	final TextBox ta = new TextBox();
	private final FontCell wdtCell = new FontCell();	
	private final CellList<String> fontList = new CellList<String>(wdtCell);
	private final SingleSelectionModel<String> lSelModel = new SingleSelectionModel<String>();
	private List<String> meys = createList();
	private String text = "Ön İzleme";
	public String font = null;
	private final int PAGESIZE = 10;
	private int offset = 0;
	private int fontCount = meys.size();

	private final Paging paging = new Paging(new SearchCallback() {
		
		@Override
		public void setOffsetForward() {
			if (offset + PAGESIZE > fontCount)
				return;
			offset = offset + PAGESIZE;			
		}
		
		@Override
		public void setOffsetBackward() {
			if (offset - PAGESIZE < 0) {
				return;
			}
			offset = offset - PAGESIZE;
		}
		
		@Override
		public void newSearch(SearchParams params) {			
			updateView();
		}

		@Override
		public void picked(String uri) {
			// TODO Auto-generated method stub
			
		}
	});

	public SilPnlFonts() {
		initWidget(ui());
	}

	protected void updateView() {
		List<String> curr = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			if (offset + i < fontCount)
				curr.add(meys.get(offset + i));
		}
		
		fontList.setRowCount(curr.size(), true);
		fontList.setRowData(0, curr);
		paging.showPager((offset/PAGESIZE + 1) + "/" + (fontCount/PAGESIZE + 1));
	}

	public Widget ui() {
		fontList.setStyleName("site-box");
		fontList.setSelectionModel(lSelModel);

//		TODO check!
//		lSelModel.addSelectionChangeHandler(new Handler() {
//			@Override
//			public void onSelectionChange(SelectionChangeEvent event) {
//				font = lSelModel.getSelectedObject();
//			}
//		});

		
		ta.setStyleName("site-box");
		ta.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				text = ta.getValue();
				updateView();
			}
		});

		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(new HTML("Metin:"));
		hp.add(ta);
		hp.add(paging);

		updateView();

		ScrollPanel sp = new ScrollPanel();
		sp.setSize("600px", "630px");
		sp.add(fontList);

		VerticalPanel vp = new VerticalPanel();
		vp.add(hp);
		vp.add(sp);

		vp.setStyleName("site-chatdlgin");

		return vp;
	}

	private List<String> createList() {
		List<String> allFonts = new ArrayList<String>();
		allFonts.add("Cutive-Regular");
		allFonts.add("FallingSkySeBd");
		allFonts.add("KGHAPPY");
		allFonts.add("PfefferSimpelgotischhalbfett");
		allFonts.add("WaitingfortheSunrise");
		allFonts.add("Akaju_demo");
		allFonts.add("Audiowide-Regular");
		allFonts.add("Devonshire-Regular");
		allFonts.add("FallingSky");
		allFonts.add("LaBelleAurore");
		allFonts.add("PfefferSimpelgotischnormal");
		allFonts.add("WarsawGothic3DObl");
		allFonts.add("Akaju_Outline_demo");
		allFonts.add("AutourOne-Regular");
		allFonts.add("DJBILoveaGinger");
		allFonts.add("FascinateInline-Regular");
		allFonts.add("Lobster");
		allFonts.add("Plaster-Regular");
		allFonts.add("WarsawGothic3D");
		allFonts.add("Akaju_Shine_demo");
		allFonts.add("AveFedan");
		allFonts.add("Dynalight-Regular");
		allFonts.add("Fascinate-Regular");
		allFonts.add("LovedbytheKing");
		allFonts.add("PressStart2P-Regular");
		allFonts.add("Akaju_Thin_demo");
		allFonts.add("Back-to-Black-Demo");
		allFonts.add("EagleLake-Regular");
		allFonts.add("Feathergraphy2");
		allFonts.add("LoveYaLikeASisterSolid");
		allFonts.add("Quando-Regular");
		allFonts.add("Alegreya-BlackItalic");
		allFonts.add("BerkshireSwash-Regular");
		allFonts.add("Eater-Regular");
		allFonts.add("Fondamento-Italic");
		allFonts.add("LoveYaLikeASister");
		allFonts.add("RammettoOne-Regular");
//		allFonts.add("WarsawGothicExt3DObl");
		allFonts.add("Alegreya-Black");
		allFonts.add("EmblemaOne-Regular");
		allFonts.add("Fondamento-Regular");
		allFonts.add("Margarine-Regular");
		allFonts.add("Ranchers-Regular");
//		allFonts.add("WarsawGothicExt3D");
		allFonts.add("Alegreya-BoldItalic");
		allFonts.add("BigelowRules-Regular");
		allFonts.add("Engagement-Regular");
		allFonts.add("Marmelad-Regular");
		allFonts.add("RibeyeMarrow-Regular");
		allFonts.add("WarsawGothicExtObl");
		allFonts.add("Alegreya-Bold");
		allFonts.add("BlackOpsOne-Regular");
		allFonts.add("Enriqueta-Bold");
		allFonts.add("FreckleFace-Regular");
		allFonts.add("McLaren-Regular");
		allFonts.add("Ribeye-Regular");
		allFonts.add("WarsawGothicExtOuLnObl");
		allFonts.add("Alegreya-Italic");
		allFonts.add("Bleeding-Cowboys");
		allFonts.add("Enriqueta-Regular");
		allFonts.add("Fujita-Ray");
		allFonts.add("MedievalSharp-BoldOblique");
		allFonts.add("Risque-Regular");
		allFonts.add("WarsawGothicExtOuLn");
		allFonts.add("Alegreya-Regular");
		allFonts.add("Boogaloo-Regular");
		allFonts.add("Fake-Smiths");
		allFonts.add("Galindo-Regular");
		allFonts.add("MedievalSharp-Bold");
		allFonts.add("RumRaisin-Regular");
		allFonts.add("WarsawGothicExtShdObl");
		allFonts.add("Bulgaria-Moderna-V3");
		allFonts.add("GiveYouGlory");
		allFonts.add("MedievalSharp-Oblique");
		allFonts.add("Sacramento-Regular");
		allFonts.add("WarsawGothicExtShd");
		allFonts.add("Allura-Regular");
		allFonts.add("FallingSkyBdObl");
		allFonts.add("GlassAntiqua-Regular");
		allFonts.add("MedievalSharp");
		allFonts.add("Sarina-Regular");
		allFonts.add("WarsawGothicExt");
		allFonts.add("Caveat-Bold");
		allFonts.add("FallingSkyBd");
		allFonts.add("gloriahallelujah");
		allFonts.add("Metamorphous-Regular");
		allFonts.add("Almendra-Bold");
		allFonts.add("CaveatBrush-Regular");
//		allFonts.add("FallingSkyBd+");
		allFonts.add("GrandHotel-Regular");
		allFonts.add("Milonga-Regular");
		allFonts.add("ShadowsIntoLight");
//		allFonts.add("WarsawGothicOuLnObl");
		allFonts.add("Caveat-Regular");
		allFonts.add("FallingSkyBlkObl");
		allFonts.add("GreatVibes-Regular");
		allFonts.add("Mochary");
		allFonts.add("Smokum-Regular");
//		allFonts.add("WarsawGothicOuLn");
		allFonts.add("Almendra-Regular");
		allFonts.add("Cedarville-Cursive");
		allFonts.add("FallingSkyBlk");
		allFonts.add("HeadlandOne-Regular");
		allFonts.add("Montez-Regular");
		allFonts.add("SonsieOne-Regular");
		allFonts.add("WarsawGothicShdObl");
		allFonts.add("Amarante-Regular");
		allFonts.add("Charakterny");
		allFonts.add("FallingSkyCondObl");
		allFonts.add("SpicyRice-Regular");
		allFonts.add("WarsawGothicShd");
		allFonts.add("AmaticSC-Bold");
		allFonts.add("ClickerScript-Regular");
		allFonts.add("FallingSkyCondOuObl");
		allFonts.add("MouseMemoirs-Regular");
		allFonts.add("StintUltraCondensed-Regular");
		allFonts.add("WarsawGothicSuExt3DObl");
		allFonts.add("AmaticSC-Regular");
		allFonts.add("Coda-Caption-Heavy");
		allFonts.add("FallingSkyCondOu");
		allFonts.add("NewRocker-Regular");
		allFonts.add("Stoke-Light");
		allFonts.add("WarsawGothicSuExt3D");
		allFonts.add("Andada-BoldItalic");
		allFonts.add("Coda-Heavy");
		allFonts.add("FallingSkyCond");
		allFonts.add("HussarGothic");
		allFonts.add("Nosifer-Regular");
		allFonts.add("Stoke-Regular");
		allFonts.add("WarsawGothicSuExtObl");
		allFonts.add("Andada-Bold");
		allFonts.add("Coda-Regular");
		allFonts.add("FallingSkyExBdObl");
		allFonts.add("IMFePIit29P");
		allFonts.add("Notera_PersonalUseOnly");
		allFonts.add("Sue-Ellen-Francisco");
		allFonts.add("WarsawGothicSuExtShdObl");
		allFonts.add("Andada-Italic");
		allFonts.add("Combo-Regular");
		allFonts.add("FallingSkyExBd");
		allFonts.add("IMFePIrm29P");
		allFonts.add("NothingYouCouldDo");
		allFonts.add("Sumana-Bold");
		allFonts.add("WarsawGothicSuExtShd");
		allFonts.add("Andada-Regular");
		allFonts.add("Comfortaa-Bold");
		allFonts.add("FallingSkyExtObl");
		allFonts.add("IMFePIsc29P");
		allFonts.add("Oldenburg-Regular");
		allFonts.add("WarsawGothicSuExt");
		allFonts.add("AndadaSC-BoldItalic");
		allFonts.add("Comfortaa-Light");
		allFonts.add("FallingSkyExtOuObl");
		allFonts.add("IndieFlower");
		allFonts.add("Oranienbaum");
		allFonts.add("SupermercadoOne-Regular");
		allFonts.add("WarsawGothic");
		allFonts.add("AndadaSC-Bold");
		allFonts.add("Comfortaa-Regular");
		allFonts.add("FallingSkyExtOu");
		allFonts.add("Isabella");
		allFonts.add("Oregano-Italic");
		allFonts.add("Swanky-and-Moo-Moo");
		allFonts.add("WarsawGothicWoodtypeObl");
		allFonts.add("AndadaSC-Italic");
		allFonts.add("FallingSkyExt");
		allFonts.add("JimNightshade-Regular");
		allFonts.add("Oregano-Regular");
		allFonts.add("THEDREAM");
		allFonts.add("WarsawGothicWoodtype");
		allFonts.add("AndadaSC-Regular");
		allFonts.add("comic");
		allFonts.add("FallingSkyLightObl");
		allFonts.add("JotiOne-Regular");
		allFonts.add("OriginalSurfer-Regular");
		allFonts.add("Ultra");
		allFonts.add("Wellfleet-Regular");
		allFonts.add("AnnieUseYourTelescope");
		allFonts.add("Corben-Bold");
		allFonts.add("FallingSkyLight");
		allFonts.add("JustAnotherHand");
		allFonts.add("OvertheRainbow");
		allFonts.add("UncialAntiqua-Regular");
		allFonts.add("Xiomara-Script");
		allFonts.add("ArbutusSlab-Regular");
		allFonts.add("Corben-Regular");
		allFonts.add("FallingSkyMedObl");
		allFonts.add("JustMeAgainDownHere");
		allFonts.add("PatrickHand-Regular");
		allFonts.add("UnifrakturMaguntia-Book");
		allFonts.add("Xolonium-Bold");
		allFonts.add("ArchitectsDaughter");
		allFonts.add("Coustard-Black");
		allFonts.add("FallingSkyMed");
		allFonts.add("Kalam-Light");
		allFonts.add("PatrickHandSC-Regular");
		allFonts.add("UrbanJungleDEMO");
		allFonts.add("Xolonium-Regular");
		allFonts.add("ArchivoBlack-Regular");
		allFonts.add("Coustard-Regular");
		allFonts.add("FallingSkyObl");
		allFonts.add("Kalam-Regular");
		allFonts.add("Peralta-Regular");
		allFonts.add("VesperLibre-Bold");
		allFonts.add("Yellowtail-Regular");
		allFonts.add("ArchivoNarrow-BoldItalic");
		allFonts.add("CoveredByYourGrace");
//		allFonts.add("FallingSkyOuObl");
		allFonts.add("KellySlab-Regular");
		allFonts.add("PfefferMediaeval");
		allFonts.add("VesperLibre-Heavy");
		allFonts.add("ArchivoNarrow-Bold");
		allFonts.add("CreteRound-Regular");
//		allFonts.add("FallingSkyOu");
		allFonts.add("KGHAPPYShadows");
		allFonts.add("PfefferSG-halbfett");
		allFonts.add("VesperLibre-Medium");
		allFonts.add("CroissantOne-Regular");
		allFonts.add("FallingSkySeBdObl");
		allFonts.add("KGHAPPYSolid");
		allFonts.add("PfefferSimpelgotischhalbfett");
		allFonts.add("VesperLibre-Regular");
//		allFonts.add("Arial");
//		allFonts.add("Arial Black");
//		allFonts.add("Bookman Old Style");
//		allFonts.add("Century Gothic");
//		allFonts.add("Comic Sans MS");
//		allFonts.add("Courier");
//		allFonts.add("Courier New");
//		allFonts.add("Garamond");
//		allFonts.add("Georgia");
//		allFonts.add("Impact");
//		allFonts.add("Lucida Console");
//		allFonts.add("Lucida Sans Unicode");
//		allFonts.add("MS Sans Serif");
//		allFonts.add("MS Serif");
//		allFonts.add("Palatino Linotype");
//		allFonts.add("Symbol");
//		allFonts.add("Tahoma");
//		allFonts.add("Times New Roman");
//		allFonts.add("Trebuchet MS");
//		allFonts.add("Verdana");
//		allFonts.add("Webdings");
//		allFonts.add("Wingdings");
		return allFonts;

	}

	private class FontCell extends AbstractCell<String> {
		@Override
		public void render(Context context, String row, SafeHtmlBuilder sb) {
			if (row == null) {
				return;
			}
			sb.appendHtmlConstant("<div style='width: 580px;position:relative;overflow:hidden;'>"
					+ "<div style=\"height:60px;line-height:60px;text-align:left;font-size:40px;font-family:'" + row
					+ "',monospace;\">" + text + "<span style='font-size:13px;'>  " + row + "</span></div></div>");

		}
	}

	public void setViewedText(String text2) {
		text = text2;
		ta.setValue(text2);

		
	}

	public String getSelectedFont() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
