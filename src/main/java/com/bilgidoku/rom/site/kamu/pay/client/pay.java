package com.bilgidoku.rom.site.kamu.pay.client;

import java.util.List;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.JsonResponse;
import com.bilgidoku.rom.gwt.araci.client.service.OturumIciCagriDao;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompBase;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
import com.bilgidoku.rom.site.kamu.pay.client.constants.paytrans;
import com.bilgidoku.rom.site.kamu.pay.client.img.Images;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Image;


public class pay extends RomEntryPoint {
	
	public static final CompInfo info = new CompInfo("+pay", 50, new String[] {},
			new String[] {"isauth"}, null);

	private CompFactory factory = new CompFactory() {

		@Override
		public CompInfo info() {
			return info;
		}

		@Override
		public Comp create() {
			return comp;
		}

	};

	private CompBase comp = new CompBase() {
		
		public void initial() {
			autoDesk = Location.getParameter("autodesk");
			String uiType = Location.getParameter("tab");
			
			OturumIciCagriDao.userAgent(new JsonResponse() {
							@Override
				public void ready(Json value) {
					userAgent = value.getValue().isObject();
				}
			});


			RomEntryPoint.one.clear();
			if (uiType == null) {
				RomEntryPoint.one.addToRealRootPanel(new ContactUI());
				return;
			}

			if (!uiType.equals("flow")) {
				RomEntryPoint.one.addToRealRootPanel(new ContactUI());
			} else  {
				RomEntryPoint.one.addToRealRootPanel(new PayFlow());
			}	
		}

		@Override
		public CompInfo compInfo() {
			return info;
		}

	};


	public static Images img = (Images) GWT.create(Images.class);
	public static paytrans trans = (paytrans) GWT.create(paytrans.class);
	public static String autoDesk;
	
	JSONObject userAgent;

	
	public pay() {
		super("Rom Server Contact Application", false, null, true, true);
	}
	


	

	
	public static Anchor getImageAnchor(ImageResource imageResource) {
		Anchor anc = new Anchor();

		Image img = new Image(imageResource);
		anc.getElement().removeAllChildren();
		anc.getElement().appendChild(img.getElement());

		return anc;
	}
	

	@Override
	public List<CompFactory> factory() {
		List<CompFactory> f = super.factory();
		f.add(factory);
		return f;
	}
}
