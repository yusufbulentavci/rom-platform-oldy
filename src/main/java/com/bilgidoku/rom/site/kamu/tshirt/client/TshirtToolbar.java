package com.bilgidoku.rom.site.kamu.tshirt.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.gwt.araci.client.rom.Stocks;
import com.bilgidoku.rom.gwt.araci.client.rom.StocksDao;
import com.bilgidoku.rom.gwt.araci.client.site.CartDao;
import com.bilgidoku.rom.gwt.araci.client.site.FilesDao;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Role;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.widgets.ColorList;
import com.bilgidoku.rom.gwt.client.widgets.PasteEvent;
import com.bilgidoku.rom.gwt.client.widgets.PasteHandler;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.site.kamu.graph.client.GraphicEditor;
import com.bilgidoku.rom.site.kamu.tshirt.client.constants.tshirtrans;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TshirtToolbar extends Composite {

	private final tshirtrans trans = GWT.create(tshirtrans.class);
	
	public interface MyTemplate extends SafeHtmlTemplates {
		@Template("<div style='font-size:22px;padding: 5px 0 0;'>{0}</div>{1}")
		SafeHtml price(String pricae, String unit);
	}

	HTML lblPrice = new HTML();

	private static final MyTemplate TEMPLATE = GWT.create(MyTemplate.class);

	VerticalPanel pnlSizes = new VerticalPanel();
	VerticalPanel pnlCanvases = new VerticalPanel();
	VerticalPanel pnlColors = new VerticalPanel();
	VerticalPanel pnlBuy = new VerticalPanel();
	VerticalPanel pnlAlts = new VerticalPanel();
	VerticalPanel listAlts = new VerticalPanel();
	VerticalPanel pnlPreview = new VerticalPanel();
	SiteButton btnSave = new SiteButton(trans.saveStock(), trans.saveStock());

	Widget[] pnls = { pnlCanvases, pnlColors, pnlSizes, pnlBuy, pnlAlts };

	ListBox lbSizes = new ListBox();
	ColorList clColors = new ColorList(false);
	private TShirtFeedback tShirtFeedback;

	private tshirt tshirtPnl;
	boolean titleSaved = false;
	boolean imagesSaved = false;
	boolean optionsSaved = false;
	boolean firstStockSaved = false;
	boolean tarifSaved = false;
	boolean isBuy = false;

	public TshirtToolbar(tshirt tshirt, TShirtFeedback tShirtFeedback) {
		this.tshirtPnl = tshirt;
		this.tShirtFeedback = tShirtFeedback;
		// forChangeSize();
		forChangeColor();
		forSaveStock();

		lbSizes.setStyleName("site-label");

		Label lbl2 = new Label(trans.sizes() + ":");
		lbl2.setStyleName("site-label");

		SimplePanel combolHolder = new SimplePanel();
		combolHolder.setStyleName("site-combo");
		combolHolder.add(lbSizes);

		pnlSizes.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		pnlSizes.add(lbl2);
		pnlSizes.add(combolHolder);

		Label lbl = new Label(trans.colors() + ":");
		lbl.setStyleName("site-label");
		pnlColors.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		pnlColors.add(lbl);
		pnlColors.add(clColors);

		SiteButton btnBuy = new SiteButton("/_public/images/sepet.png", trans.addToBasket(), trans.addToBasket());
		btnBuy.setStyleName("site-btn btn-fancy");
		forBuy(btnBuy);

		lblPrice.setStyleName("site-label");

		pnlBuy.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		pnlBuy.add(lblPrice);
		pnlBuy.add(btnBuy);

		ScrollPanel spAlts = new ScrollPanel(listAlts);
		// spAlts.setSize("80px", "240px");
		spAlts.setWidth("80px");
		Label lbl3 = new Label(trans.suggestions());
		lbl3.setStyleName("site-label");
		pnlAlts.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		pnlAlts.add(lbl3);
		pnlAlts.add(spAlts);

		VerticalPanel top = new VerticalPanel();
		top.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);

		for (int i = 0; i < pnls.length; i++) {
			pnls[i].setStyleName("site-roundedpnl");
			pnls[i].setWidth("92px");
			top.add(pnls[i]);
		}

		if (Role.isAdmin()) {
			btnSave.setWidth("92px");
			top.add(btnSave);
		}
		initWidget(top);
	}

	protected void changeOptions(final String vStockId, String title) {

		Map<String, JSONObject> canvases = tshirtPnl.getCanvases();
		Set<String> keySet = canvases.keySet();

		final JSONObject joForVirtual = new JSONObject();

		for (Iterator<String> iter = keySet.iterator(); iter.hasNext();) {
			String key = (String) iter.next();

			String fileUri = tshirtPnl.getImgUri(vStockId, key);

			JSONObject jo = new JSONObject();
			jo.put("rendered", new JSONString(fileUri));
			jo.put("rgf", canvases.get(key));

			joForVirtual.put(key, jo);
		}

		JSONObject otSave = new JSONObject();
		otSave.put("canvas", joForVirtual);
		otSave.put("coef", new com.google.gwt.json.client.JSONNumber(4.0));
		otSave.put("title", new com.google.gwt.json.client.JSONString(title));

		StocksDao.setoptions(new Json(otSave), vStockId, new BooleanResponse() {
			@Override
			public void ready(Boolean value) {
				optionsSaved = true;
				checkFinished(vStockId);
			}
		});

	}

	private void forBuy(final SiteButton btn) {
		btn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				btn.setEnabled(false);
				tshirtPnl.disable();
				ClientUtil.startWaiting();
//				tshirtPnl.showLoading();

				getNewVirtualStockId(new Runnable() {

					@Override
					public void run() {
						if (tshirtPnl.getVirtualStockId() == null)
							return;

						final String vStockId = tshirtPnl.getVirtualStockId();
						final String title = tshirtPnl.getVirtualTitle();

						// şimdilik first stock'un tarife bilgisi alınır ama
						// normalde hesaplanması gerekir.
						// Json tariff =
						// tshirtPnl.db.getStock(tshirtPnl.parentStockId).getStock().tariff;
						// tarifSaved = false;

						titleSaved = false;
						imagesSaved = false;
						optionsSaved = false;
						firstStockSaved = false;
						isBuy = true;

						setTitle(title, vStockId);
						// setTarif(tariff, vStockId);
						saveCanvasImg(vStockId);
						changeOptions(vStockId, title);
						saveFirstStock(vStockId, tshirtPnl.getParentStock());

						checkFinished(vStockId);

					}
				});

			}
		});
	}

	// protected void setTarif(Json tariff, final String vStockId) {
	// StocksDao.settariff(tariff, vStockId, new BooleanResponse() {
	// @Override
	// public void ready(Boolean value) {
	// tarifSaved = true;
	// checkFinished(vStockId);
	// }
	// });
	//
	// }

	private void forSaveStock() {
		btnSave.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				btnSave.setEnabled(false);
				// tshirtPnl.startWaiting("İşleminiz yapılıyor, lüften
				// bekleyiniz.");

				if (tshirtPnl.virtualStockId != null) {
					// if (Window.confirm("Başlığı değiştirmek istiyor
					// musunuz?")) {
					// String title = Window.prompt("Tasarım için başlık
					// giriniz", "");
					// titleSaved = false;
					// setTitle(title, tshirtPnl.virtualStockId);
					// } else {

					// }

					titleSaved = true;
					changeStock(tshirtPnl.virtualStockId, tshirtPnl.getVrtualStock().title);
				} else {
					getNewVirtualStockId(new Runnable() {
						@Override
						public void run() {

							String vStockId = tshirtPnl.getVirtualStockId();

							if (vStockId == null)
								return;
							// change title
							String title = Window.prompt("Tasarım için başlık giriniz", "");
							titleSaved = false;
							setTitle(title, vStockId);

							changeStock(vStockId, title);

						}
					});
				}

			}
		});
	}

	private void changeStock(String vStockId, String title) {
		isBuy = false;
		imagesSaved = false;
		optionsSaved = false;
		firstStockSaved = false;

		// şimdilik first stock'un tarife bilgisi alınır ama normalde
		// hesaplanması gerekir.
		// Json tariff =
		// tshirtPnl.db.getStock(tshirtPnl.parentStockId).getStock().tariff;
		// tarifSaved = false;
		// setTarif(tariff, vStockId);

		saveCanvasImg(vStockId);
		changeOptions(vStockId, title);
		saveFirstStock(vStockId, tshirtPnl.getParentStock());

		checkFinished(null);

	}

	private void getNewVirtualStockId(final Runnable run) {
		StocksDao.virtualstock(tshirtPnl.parentStockId, "/_/_stocks", new StringResponse() {
			@Override
			public void ready(String value) {
				tshirtPnl.setVirtualStockId(value);
				run.run();
			}

			@Override
			public void err(int statusCode, String statusText, Throwable exception) {
				Window.alert(statusCode + statusText);
				run.run();
			}
		});

	}

	private void saveFirstStock(final String vStockId, Stocks virtualsParent) {
		String firstStockUri = virtualsParent.uri;
		if (virtualsParent.firststock != null)
			firstStockUri = virtualsParent.firststock;

		StocksDao.setfirststock(firstStockUri, vStockId, new BooleanResponse() {
			@Override
			public void ready(Boolean value) {
				firstStockSaved = true;
				checkFinished(vStockId);
			}
		});
	}

	protected void setTitle(final String title, final String vStockId) {
		StocksDao.settitle(title, vStockId, new BooleanResponse() {
			@Override
			public void ready(Boolean value) {
				titleSaved = true;
				checkFinished(vStockId);
			}
		});

	}

	protected void checkFinished(final String vStockId) {

		if (!titleSaved || !imagesSaved || !optionsSaved || !firstStockSaved) {
			return;
		}
		ClientUtil.stopWaiting();
		if (isBuy) {
			CartDao.add(vStockId, 1, null, "/_/_cart", new BooleanResponse() {
				@Override
				public void ready(Boolean value) {
					// Window.alert("added redirect ====> ");
					RomEntryPoint.com().post("*wndredirect", "uri","/_public/contact.html?tab=flow");
//					RomWebCom.redirectTop();a
					
					// Window.Location.replace("/_public/contact.html?tab=flow");
				}
			});
		} else
			Window.alert("Tasarımınız kaydedildi StockId:" + tshirtPnl.getVirtualStockId());

	}

	public void dirReady(final boolean buy) {

	}

	private void forChangeColor() {
		clColors.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				tShirtFeedback.stockChanged(clColors.getValue());

			}
		}, PasteEvent.TYPE);

	}

	// private void forChangeSize() {
	// lbSizes.addChangeHandler(new ChangeHandler() {
	// @Override
	// public void onChange(ChangeEvent event) {
	// tShirtFeedback.stockChanged(lbSizes.getSelectedValue());
	// }
	// });
	// }

	public void loadData(Stock st, String color, String size, JSONValue value) {
		// show price
		try {
			JSONObject tariff = ClientUtil.getObject(value);
			String[] ret = ClientUtil.getPriceAndUnit(tariff.get("price"));
			lblPrice.setHTML(TEMPLATE.price(ret[0], ret[1]));
		} catch (Exception e) {
			Sistem.outln(e.getMessage());
		}

		listAlts.clear();
		lbSizes.clear();
		lbSizes.addItem(size, st.getStock().uri);

		Map<String, List<Stock>> otherColors = st.groupByOption("color", (Constraint[]) null);
		ArrayList<String[]> colors = new ArrayList<>();
		Set<String> keysOthers = otherColors.keySet();
		for (Iterator<String> iterator = keysOthers.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();

			List<Stock> st1 = (List<Stock>) otherColors.get(key);
			String uri = null;
			String title = null;
			if (st1.size() > 0) {
				// get first product with that color
				Stock st2 = st1.iterator().next();
				uri = st2.getStock().uri;
				title = st2.getStock().title;
			}
			colors.add(new String[] { key, uri, title });
		}

		clColors.populate(colors);
		clColors.setColor(color);

		Constraint con = new Constraint("color", color);

		Map<String, List<Stock>> otherSizes = st.groupByOption("size", con);
		Set<String> keysSizes = otherSizes.keySet();
		for (Iterator<String> iterator = keysSizes.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			List<Stock> st1 = (List<Stock>) otherSizes.get(key);
			String uri = null;
			if (st1.size() > 0) {
				Stock st2 = st1.iterator().next();
				uri = st2.getStock().uri;
			}
			if (key.equals(size)) {
				// change value for the first entry
				lbSizes.setValue(0, uri);
			} else {
				lbSizes.addItem(key, uri);
			}
		}
		ClientUtil.findAndSelect(lbSizes, st.getStock().uri);

		// show canvases
		pnlCanvases.clear();
		pnlCanvases.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		pnlCanvases.setSpacing(4);
		Map<String, StockCanvas> canvases = st.getCanvasList();
		Set<String> keysCanvases = canvases.keySet();
		for (Iterator<String> iterator = keysCanvases.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			StockCanvas stockCanvas = canvases.get(key);
			pnlCanvases.add(getCanvasBtn(stockCanvas));
		}

	}

	public void loadAlternatives(Stock st) {
		pnlAlts.setVisible(false);
		listAlts.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		listAlts.setSpacing(4);
		Map<String, Stock> alts = st.getAlternatives();
		if (alts != null && alts.size() > 0)
			pnlAlts.setVisible(true);
		else
			return;

		Set<String> keysAlts = alts.keySet();
		for (Iterator<String> iterator = keysAlts.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Stock alt = alts.get(key);
			listAlts.add(getAlternativeBtn(alt));
		}

	}

	private Widget getAlternativeBtn(final Stock alt) {
		JSONObject jo = ClientUtil.getObject(alt.getStock().options.getValue());
		JSONObject joCanvases = ClientUtil.getObject(jo.get("canvas"));
		String canvasName = "front";
		JSONObject canvas = ClientUtil.getObject(joCanvases.get(canvasName));
		if (canvas == null) {
			canvasName = "back";
			canvas = ClientUtil.getObject(joCanvases.get(canvasName));
		}

		String backImg = ClientUtil.getString(canvas.get("backimg"));
		if (backImg == null) {
			return new SimplePanel();
		}

		Image img = new Image(backImg + "?romthumb=t");
		img.setSize("70px", "70px");
		img.setStyleName("site-smlimg");
		img.setTitle(alt.getStock().title);
		img.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				tShirtFeedback.stockChanged(alt.getStock().uri);
			}
		});
		return img;
	}

	private Widget getCanvasBtn(final StockCanvas stockCanvas) {
		final Image img = new Image(stockCanvas.backImg + "?romthumb=t");
		img.setWidth("70px");
		img.setStyleName("site-smlimg");
		img.setTitle(stockCanvas.name);
		img.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				removeAllSelected();
				img.addStyleName("selected-btn");
				tShirtFeedback.canvasChanged(stockCanvas);

			}
		});
		return img;
	}

	protected void removeAllSelected() {
		for (int i = 0; i < pnlCanvases.getWidgetCount(); i++) {
			Widget w = pnlCanvases.getWidget(i);
			w.removeStyleName("selected-btn");
		}
	}

	public void saveCanvasImg(final String virtualStockId) {
		Map<String, JSONObject> canvases = tshirtPnl.graphHolder.getCanvases();
		Set<String> keySet = canvases.keySet();
		final int saveCount = keySet.size();

		// save showing canvas
		final String furi = tshirtPnl.getImgUri(virtualStockId, tshirtPnl.graphHolder.curCanvasName);
		final String sturi = tshirtPnl.getUserStockDir();

		GraphicEditor.editState.unselect();

		saveImage(GraphicEditor.canvasHolder.getCanvas(), furi, sturi, new Runnable() {
			@Override
			public void run() {
				// saved set stock icon
				StocksDao.seticon(furi, virtualStockId, new BooleanResponse() {
					@Override
					public void ready(Boolean value) {
						if (saveCount == 1) {
							imagesSaved = true;
							checkFinished(virtualStockId);
						}
					}
				});

			}
		});

		if (saveCount == 1) {
			return;
		}

		// save other canvas
		String otherKey = null;
		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if (!key.equals(tshirtPnl.graphHolder.curCanvasName)) {
				otherKey = key;
			}
		}

		if (otherKey != null) {
			// the code supports max two canvases!
			final String secondKey = otherKey;
			tshirtPnl.switchCanvas(otherKey);
			com.google.gwt.core.client.Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
				@Override
				public boolean execute() {
					String furi = tshirtPnl.getImgUri(virtualStockId, secondKey);
					String sturi = tshirtPnl.getUserStockDir();
					saveImage(GraphicEditor.canvasHolder.getCanvas(), furi, sturi, new Runnable() {
						public void run() {
							imagesSaved = true;
							checkFinished(virtualStockId);
						}
					});
					return false;
				}
			}, 2000);
		}
	}

	public void saveImage(Canvas canvas, final String fileUri, final String container, final Runnable run) {
		if (canvas == null)
			return;

		final String imgText = canvas.toDataUrl("image/png");

		ResourcesDao.exists(fileUri, new BooleanResponse() {
			@Override
			public void ready(Boolean value) {
				if (!value) {
					yok();
				} else {
					changeFile(imgText, fileUri);
				}
			}

			@Override
			public void err(int statusCode, String statusText, Throwable exception) {
				yok();
			}

			private void yok() {
				String fileName = fileUri.substring(fileUri.lastIndexOf("/") + 1);
				FilesDao.neww("tr", fileUri, null, null, null, fileName, imgText.substring(imgText.indexOf(",") + 1),
						container, new StringResponse() {
							@Override
							public void ready(String value) {
								changeFile(imgText, fileUri);
							}
						});

			}

			private void changeFile(String imgText, String fileUri) {
				String text = imgText.substring(imgText.indexOf(",") + 1);
				FilesDao.pngtojpeg(text, fileUri, new BooleanResponse() {
					@Override
					public void ready(Boolean value) {
						run.run();
					}
				});

				// FilesDao.changecoded(text, fileUri, new BooleanResponse() {
				// @Override
				// public void ready(Boolean value) {
				// run.run();
				// }
				// });

			}

		});
	}

	// private void forPreview(SiteButton btnPreview) {
	// btnPreview.addClickHandler(new ClickHandler() {
	// @Override
	// public void onClick(ClickEvent event) {
	// GraphicEditor.drawer.drawImg(new AsyncCallback<String>() {
	// @Override
	// public void onSuccess(String result) {
	// new DlgImage(result);
	// }
	//
	// @Override
	// public void onFailure(Throwable caught) {
	// Sistem.printStackTrace(caught, "Failed to prewiew");
	// }
	// });
	//
	// }
	// });
	//
	// }

	// protected void setTarif(Json tariff, String vStockId) {
	// StocksDao.settariff(tariff, vStockId, new BooleanResponse() {
	// @Override
	// public void ready(Boolean value) {
	// tarifSaved = true;
	// checkFinished(null);
	// }
	// });
	//
	// }

}
