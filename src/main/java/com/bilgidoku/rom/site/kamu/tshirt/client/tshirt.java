package com.bilgidoku.rom.site.kamu.tshirt.client;

import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.client.common.resp.JsonResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.gwt.araci.client.rom.Stocks;
import com.bilgidoku.rom.gwt.araci.client.service.OturumIciCagriDao;
import com.bilgidoku.rom.gwt.araci.client.site.FilesDao;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompBase;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.min.geo.Point;
import com.bilgidoku.rom.shared.RunException;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

interface TShirtFeedback {
	void stockChanged(String stockUri);

	void canvasChanged(StockCanvas stockCanvas);
}

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class tshirt extends RomEntryPoint {
	
	public static final CompInfo info = new CompInfo("+productcustom", 50, new String[] {},
			new String[] {"cid"}, null);

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

	@Override
	public List<CompFactory> factory() {		
		List<CompFactory> f = super.factory();
		f.add(factory);
		return f;
	}
	
	private CompBase comp = new CompBase() {
		
		public void initial() {
			tb = new TshirtToolbar(tshirt.this, new TShirtFeedback() {

				@Override
				public void stockChanged(String stockUri) {
					changeStock(stockUri);
				}

				@Override
				public void canvasChanged(StockCanvas canvas) {
					try {
						graphHolder.switchCanvas(canvas.name, canvas.backImg, new Point(canvas.backW, canvas.backH),
								new Point(canvas.x, canvas.y), new Point(canvas.width, canvas.height));
					} catch (RunException e) {
						Window.alert(e.getMessage());
						e.printStackTrace();
					}
				}
			});

			OturumIciCagriDao.userAgent(new JsonResponse() {
				@Override
				public void ready(Json value) {
					userAgent = value.getValue().isObject();
				}
			});

			FilesDao.userdir("/f/images", new StringResponse() {

				@Override
				public void ready(String myDir) {
					userDir = myDir;
					makeUserStocksDir(getUserStockDir());

					String stock = Location.getParameter("stock");
					if (stock == null)
						return;

					if (stock.indexOf("stocks") < 0)
						stock = "/_/_stocks/" + stock;

					ui();

					db = new StockDb();

					changeStock(stock);

				}
			});	
		}

		@Override
		public CompInfo compInfo() {
			return info;
		}

	};

	JSONObject userAgent;
	StockDb db;

	protected String parentStockId = null;
	protected String virtualStockId = null;
	protected String virtualTitle = null;
	

	public String userDir;

	private TshirtToolbar tb;
	GraphHolder graphHolder;

	private final int widgetSize = 820;

	public tshirt() {
		super("Rom Server Printable-Design Application", false, null, true, true);
	}


	private void changeStock(final String stockId) {
		db.changeFirstStock(stockId, new ProductReady() {
			@Override
			public void ready(final Stock s) {
				// isNewStock = true;
				Stock p = db.getStock(stockId);
				loadFonts(p);
				if (p.getStock().virtualsparent != null) {
					// this is a virtual stock and parent stock is
					virtualStockId = p.getStock().uri;
					virtualTitle = p.getStock().title;
					parentStockId = p.getStock().virtualsparent;
					
					Stocks parent =  db.getStock(parentStockId).getStock();
					if (parent.virtualsparent != null) {
						parentStockId = parent.virtualsparent;
						virtualTitle = parent.title; //first parents title
					}

				} else {
					// real stock
					parentStockId = stockId;
					virtualTitle = db.getStock(parentStockId).getStock().title;
				}
				
				graphHolder.loadCanvases(p);

				final Stock actualStock = db.getStock(parentStockId);
				
				// Window.alert("first stock" +
				// actualStock.getFirstStock().getStock().uri + " actual stock"
				// + actualStock.getStock().uri);

				showCanvas(actualStock, "front");

				db.listProductsWithSameFirstStock(actualStock, new Runnable() {
					@Override
					public void run() {
						
						tb.loadData(actualStock, actualStock.getColor(), actualStock.getSize(), actualStock.getFirstStock().getStock().tariff.getValue());
						
						db.listAlternatives(actualStock.getFirstStock(), new Runnable() {
							@Override
							public void run() {
								tb.loadAlternatives(actualStock);
							}
						});
					}
				});
				
//				if (top == null)
//					return;
//				top.hideSplash();

			}
		});
	}

	protected void loadFonts(Stock p) {
		try {
			JSONObject object = p.getStock().options.getValue().isObject();
			JSONObject object2 = object.get("canvas").isObject();
			if (object2.get("front") != null) {
				JSONObject object3 = object2.get("front").isObject();
				JSONArray array = object3.get("rgf").isObject().get("elms").isArray();
				for (int i = 0; i < array.size(); i++) {
					JSONObject object4 = array.get(i).isObject();
					if (object4.get("f") != null) {
						String font = object4.get("f").isString().stringValue();
						HTML h = new HTML("<span style='font-family:"+font+";opacity:0;'>test</span>");
						RootPanel.get().add(h);						
					} 
				}
			}

			if (object2.get("back") != null) {
				JSONObject object3 = object2.get("back").isObject();
				JSONArray array = object3.get("rgf").isObject().get("elms").isArray();
				for (int i = 0; i < array.size(); i++) {
					JSONObject object4 = array.get(i).isObject();
					if (object4.get("f") != null) {
						String font = object4.get("f").isString().stringValue();
						HTML h = new HTML("<span style='font-family:"+font+";opacity:0;'>test</span>");
						RootPanel.get().add(h);						
					} 
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	protected void ui() {

		tb.setStyleName("site-floatright");

		final FlowPanel pnlDesign = new FlowPanel();

		pnlDesign.setStyleName("site-holder");
		pnlDesign.setWidth(widgetSize + "px");

		pnlDesign.clear();

		graphHolder = new GraphHolder(userDir, this);

		pnlDesign.add(graphHolder);
		tb.getElement().getStyle().setFloat(Float.RIGHT);

		pnlDesign.add(tb);

		RomEntryPoint.one.addToRealRootPanel(pnlDesign);

	}

	protected void makeUserStocksDir(final String folder) {
		ResourcesDao.exists(folder, new BooleanResponse() {
			@Override
			public void ready(Boolean value) {
				if (!value) {
					yok();
				}
			}

			@Override
			public void err(int statusCode, String statusText, Throwable exception) {
				yok();
			}

			private void yok() {
				FilesDao.breed(folder, Data.WRITING_PUBLIC_MASK, null, null, getUserDir(), new ContainersResponse() {
					@Override
					public void ready(Containers value) {
					}
				});

			}

		});

	}

//	protected Widget getCizgiyPage(FlowPanel pnlDesign) {
//		HTML header = new HTML(
//				"<img src='/_public/images/cizgiy_logo.png' style='margin: 10px 0 0 -5px;float:left;cursor:pointer;'/>"
//						+ "<a href='http://www.cizgiy.com/kategoriler' class='selected' style='float:right;'>TÜM ÜRÜNLER</a>");
//		header.setSize("880px", "112px");
//		header.setStyleName("wheader_holder menu_hrzntl__fe_item");
//		header.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				Window.Location.replace("/");
//
//			}
//		});
//
//		VerticalPanel vp = new VerticalPanel();
//		vp.setStyleName("body-inner");
//		vp.add(header);
//		vp.add(pnlDesign);
//
//		return vp;
//	}

	public void switchCanvas(String canvasName) {
		final Stock parentStock = db.getStock(parentStockId);
		showCanvas(parentStock, canvasName);
	}

	protected void showCanvas(Stock pp, String canvasName) {

		JSONObject jo = ClientUtil.getObject(pp.getStock().options.getValue());
		// JSONObject jo = pp.effectiveOptions();
		JSONObject joCanvases = ClientUtil.getObject(jo.get("canvas"));
		JSONObject canvas = ClientUtil.getObject(joCanvases.get(canvasName));
		if (canvas == null) {
			canvas = ClientUtil.getObject(joCanvases.get("back"));
			canvasName = "back";
		}

		int w = ClientUtil.getNumber(canvas.get("w"));
		int h = ClientUtil.getNumber(canvas.get("h"));

		int x = ClientUtil.getNumber(canvas.get("x"));
		int y = ClientUtil.getNumber(canvas.get("y"));

		int backW = ClientUtil.getNumber(canvas.get("backw"));
		int backH = ClientUtil.getNumber(canvas.get("backh"));

		String backImg = ClientUtil.getString(canvas.get("backimg"));

		try {
			graphHolder.switchCanvas(canvasName, backImg, new Point(backW, backH), new Point(x, y), new Point(w, h));
		} catch (RunException e) {
			Window.alert(e.getMessage());
			e.printStackTrace();
		}

	}

	public String getUserStockDir() {
		return userDir + "/stocks";
	}

	public String getUserDir() {
		return userDir;
	}

	public Stocks getParentStock() {
		return db.getStock(parentStockId).getStock();
	}

	public Stocks getVrtualStock() {
		return db.getStock(virtualStockId).getStock();
	}

	public Map<String, JSONObject> getCanvases() {
		return graphHolder.getCanvases();
	}

	public String getImgUri(String stockId, String canvas) {
		String fileName = "i" + ClientUtil.getTitleFromUri(stockId) + "_" + canvas + ".jpg";
		String fileUri = getUserStockDir() + "/" + fileName;
		return fileUri;

	}

	private boolean isRequested = false;

	public String getVirtualStockId() {
		return virtualStockId;
	}

	public boolean getRequested() {
		return isRequested;
	}


	public void areaCoefChanged(int oldCoef, int nCoef) {

	}

	public void setVirtualStockId(String id) {
		virtualStockId = id;
		
	}

	public String getVirtualTitle() {		
		return virtualTitle;
	}

	public void disable() {	
		RootPanel.get().getElement().getStyle().setOpacity(.5);
		
	}

}
