package com.bilgidoku.rom.site.yerel.stock;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Stocks;
import com.bilgidoku.rom.gwt.araci.client.rom.StocksDao;
import com.bilgidoku.rom.gwt.araci.client.rom.StocksResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Tariffmodel;
import com.bilgidoku.rom.gwt.araci.client.rom.TariffmodelDao;
import com.bilgidoku.rom.gwt.araci.client.rom.TariffmodelResponse;
import com.bilgidoku.rom.gwt.araci.client.site.FilesDao;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.widgets.ImageBox;
import com.bilgidoku.rom.gwt.client.widgets.colorpicker.ColorTextBox;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.one;
import com.bilgidoku.rom.site.yerel.common.SiteIntegerBox;
import com.bilgidoku.rom.site.yerel.common.SitePriceBox;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TabStock extends Composite {
	
	static int[] cupFront = {700, 800, 377, 435,  70, 188};
	static int[] cupBack = {700, 800, 377, 435,  252, 188};
	static int[] tshirtFront = {3500, 4000, 1754, 2480,  870, 460};
	static int[] tshirtVFront = {3500, 4000, 1754, 2480,  870, 900};
	static int[] tshirtBack = {3500, 4000, 1754, 2480,  870, 460};

	private final static Map<String, int[]> CANVASES = new HashMap<String, int[]>() {
		private static final long serialVersionUID = 1L;

		{
			put("cupFront", cupFront);
			put("cupBack", cupBack);
			put("tshirtFront", tshirtFront);
			put("tshirtVFront", tshirtVFront);
			put("tshirtBack", tshirtBack);
		}	
	};
	
	private final TextBox txtTitle = new TextBox();
	private final TextArea txtSummary = new TextArea();
	private final ImageBox imgIcon = new ImageBox();
	private final Label lblFirstStock = new Label();
	private final CheckBox cbChangable = new CheckBox(Ctrl.trans.designable());
	private final PnlOptions pnlOpts = new PnlOptions();
	private final VerticalPanel pnlVirtuals = new VerticalPanel();

	private PnlAlternatives pnlAlts;
	private final SitePriceBox txtPr = new SitePriceBox();
	private final SiteIntegerBox txtAmount = new SiteIntegerBox();
	private final Button btnTitle = new Button(Ctrl.trans.save());
	private final Button btnSummary = new Button(Ctrl.trans.save());
	private final Button btnImg = new Button(Ctrl.trans.save());
	private final Button btnNewDesign = new Button(Ctrl.trans.newDesign());
	private final FlexTable holder = new FlexTable();
	private PnlTarife pnlTarife;
	private final String uri;

	final FlexTable ftFirst = new FlexTable();
	final FlexTable ftVirt = new FlexTable();

	public TabStock(final String uri) {

		this.uri = uri;
		loadStock();

		pnlVirtuals.setStyleName("site-panel");
		ftVirt.setWidget(0, 1, btnNewDesign);
		ftVirt.setHTML(1, 0, "Options");
		ftVirt.setWidget(1, 1, pnlOpts);
		ftVirt.setHTML(2, 0, "Virtuals");
		ftVirt.setWidget(2, 1, pnlVirtuals);
		ftVirt.setVisible(false);
		ftFirst.setVisible(false);

		HorizontalPanel fpTitle = new HorizontalPanel();
		fpTitle.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		fpTitle.add(txtTitle);
		fpTitle.add(btnTitle);

		HorizontalPanel fpSummary = new HorizontalPanel();
		fpSummary.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		fpSummary.add(txtSummary);
		fpSummary.add(btnSummary);

		HorizontalPanel fpImage = new HorizontalPanel();
		fpImage.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		fpImage.add(imgIcon);
		fpImage.add(btnImg);

		cbChangable.setValue(false);
		
		holder.setHTML(0, 0, "Stock:");
		holder.setWidget(0, 1, new Label(uri));

		holder.setHTML(1, 0, Ctrl.trans.firstStock());
		holder.setWidget(1, 1, lblFirstStock);

		holder.setHTML(2, 0, Ctrl.trans.title());
		holder.setWidget(2, 1, fpTitle);

		holder.setHTML(3, 0, Ctrl.trans.summary());
		holder.setWidget(3, 1, fpSummary);

		holder.setHTML(4, 0, Ctrl.trans.image());
		holder.setWidget(4, 1, fpImage);

		holder.setWidget(5, 1, ftFirst);

		holder.setWidget(6, 1, cbChangable);
		holder.setWidget(7, 1, ftVirt);

		initWidget(new ScrollPanel(holder));

		cbChangable.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ftVirt.setVisible(cbChangable.getValue());
			}
		});

		btnTitle.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				StocksDao.settitle(txtTitle.getValue(), uri, new BooleanResponse() {
					@Override
					public void ready(Boolean value) {
						Ctrl.setStatus("title changed");
					}
				});
			}
		});

		btnSummary.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				StocksDao.setsummary(txtSummary.getValue(), uri, new BooleanResponse() {
					@Override
					public void ready(Boolean value) {
						Ctrl.setStatus("summary changed");
					}
				});
			}
		});

		btnImg.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				StocksDao.seticon(imgIcon.getImgPath(), uri, new BooleanResponse() {
					@Override
					public void ready(Boolean value) {
						Ctrl.setStatus("icon changed");
					}
				});

			}
		});

		btnNewDesign.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String id = uri.substring(uri.lastIndexOf("/") + 1);
				Window.open("/_public/tshirt.html?stock=" + id, "_blank", "");
			}
		});

	}

	private void loadStock() {
		StocksDao.get(this.uri, new StocksResponse() {
			@Override
			public void ready(final Stocks value) {
				if (value.firststock == null || value.firststock.isEmpty()) {
					ftFirst.setVisible(true);
					// tarife
					pnlTarife = new PnlTarife();
					ftFirst.setHTML(0, 0, "Tarife");
					ftFirst.setWidget(0, 1, pnlTarife);

					Json tarife = value.tariff;
					JSONObject jo = tarife.getValue().isObject();
					txtAmount.setValue(value.amount.toString());
					cbChangable.setValue(value.onsale);
					txtPr.setPrice(jo.get("price"));

					// alternatifler
					StocksDao.list("/_/_stocks", new StocksResponse() {
						@Override
						public void array(List<Stocks> stocks) {
							pnlAlts = new PnlAlternatives(stocks, value.alternatives, uri);
							ftFirst.setHTML(1, 0, Ctrl.trans.alternatives());
							ftFirst.setWidget(1, 1, pnlAlts);
						}
					});

				}

				lblFirstStock.setText(value.firststock);
				txtTitle.setValue(value.title);
				txtSummary.setValue(value.summary);
				imgIcon.setImage(value.icon);

				if (value.options.getValue() != null && value.options.getValue().isObject() != null) {
					cbChangable.setValue(true);
					ftVirt.setVisible(true);

					pnlOpts.loadOptions(value.options.getValue());
					StocksDao.virtualslist(uri, new StocksResponse() {
						@Override
						public void array(List<Stocks> myarr) {

							for (int i = 0; i < myarr.size(); i++) {
								final HorizontalPanel row = new HorizontalPanel();
								final Stocks stock = myarr.get(i);
								final String owner = stock.ownercid;

								if (!ClientUtil.isUser(stock.ownercid, one.users))
									continue;

								final String id = stock.uri.substring(stock.uri.lastIndexOf("/") + 1);

								final HTML divTitle = new HTML("<img src='" + stock.icon
										+ "' width='120px' style='vertical-align: middle;'>" + id + " " + stock.title);

								divTitle.setTitle(stock.uri);
								divTitle.addClickHandler(new ClickHandler() {
									@Override
									public void onClick(ClickEvent event) {
										Window.open("/_public/tshirt.html?stock=" + id, "_blank", "");
									}
								});

								Button del = new Button("Sil");
								del.addClickHandler(new ClickHandler() {
									@Override
									public void onClick(ClickEvent event) {
										if (Window.confirm(Ctrl.trans.confirmDelete())) {
											StocksDao.destroy("/_/_stocks/" + id, new StringResponse() {
												public void ready(String value) {
													row.removeFromParent();
													// remove files

													JSONObject jo = stock.options.getValue().isObject();
													if (jo == null)
														return;
													JSONObject jocanvas = ClientUtil.getObject(jo.get("canvas"));
													if (jocanvas == null)
														return;

													for (Iterator<String> iterator = jocanvas.keySet()
															.iterator(); iterator.hasNext();) {

														String key = (String) iterator.next();

														final String fileUri = "/f/users/" + ClientUtil.getId(owner)
																+ "/stocks/i" + ClientUtil.getId(stock.uri) + "_" + key
																+ ".png";

														// "/f/users/1/stocks/i518_front.png"

														FilesDao.destroy(fileUri, new StringResponse() {
															@Override
															public void ready(String value) {
																Ctrl.setStatus(fileUri + " silindi");
															}
														});

													}

												}
											});
										}
									}
								});

								Button changeTitle = new Button(Ctrl.trans.changeTitle());
								changeTitle.addClickHandler(new ClickHandler() {
									@Override
									public void onClick(ClickEvent event) {
										final String title = Window.prompt(Ctrl.trans.title(), "");
										if (title == null)
											return;
										StocksDao.settitle(title, "/_/_stocks/" + id, new BooleanResponse() {
											public void ready(final Boolean value) {
												Ctrl.setStatus("Başlık değiştirildi.");
												divTitle.setHTML("<img src='" + stock.icon
														+ "' width='120px' style='vertical-align: middle;'>" + id + " "
														+ title);
											};
										});
									}
								});

								row.add(divTitle);
								row.add(changeTitle);
								row.add(del);

								pnlVirtuals.add(row);
							}
						}

					});

				}

			}
		});

	}

	private class PnlOptions extends Composite {
		String[] sizes = { "Standard", "XS", "S", "M", "L", "XL" };

		ListBox lbSizes = new ListBox();
		ColorTextBox clr = new ColorTextBox();
		VerticalPanel vpCanvases = new VerticalPanel();

		public PnlOptions() {
			for (int i = 0; i < sizes.length; i++) {
				lbSizes.addItem(sizes[i]);
			}

			Button add = new Button(Ctrl.trans.addCanvas());
			add.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					AddCanvasDlg dlg = new AddCanvasDlg();
					dlg.show();
					dlg.center();

				}
			});

			Button save = new Button(Ctrl.trans.saveOptions() );
			save.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					StocksDao.setoptions(getJson(), uri, new BooleanResponse() {
						public void ready(Boolean value) {
							Ctrl.setStatus("options saved");
						};
					});
				}

			});

			FlexTable ft = new FlexTable();
			ft.setStyleName("site-panel");

			ft.setHTML(0, 0, Ctrl.trans.size());
			ft.setWidget(0, 1, lbSizes);

			ft.setHTML(1, 0, Ctrl.trans.color());
			ft.setWidget(1, 1, clr);

			ft.setHTML(3, 0, Ctrl.trans.tariff());
			ft.setWidget(3, 1, add);

			ft.setHTML(3, 0, Ctrl.trans.canvases());
			ft.setWidget(3, 1, add);

			ft.setWidget(4, 1, vpCanvases);
			ft.setWidget(5, 0, save);

			initWidget(new ScrollPanel(ft));

		}
		// private String getTitle(String uri) {
		// String found = "";
		// for (int i = 0; i < lastStock.size(); i++) {
		// Stocks sts = lastStock.get(i);
		// if (sts.uri.equals(uri)) {
		// if (sts.firststock == null || sts.firststock.isEmpty())
		// found = sts.title;
		// else
		// found = "-----" + sts.title;
		// break;
		// }
		// }
		// return found;
		//
		// }

		public void loadOptions(JSONValue value) {

			JSONObject jo = value.isObject();
			String size = ClientUtil.getString(jo.get("size"));
			String collor = ClientUtil.getString(jo.get("color"));

			ClientUtil.findAndSelect(lbSizes, size);
			clr.setHexValue(collor);

			JSONObject canvases = ClientUtil.getObject(jo.get("canvas"));
			Set<String> keySet = canvases.keySet();

			for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				JSONValue jsonValue = canvases.get(key);
				addCanvas(key, jsonValue.toString());
			}

		}

		protected Json getJson() {
			JSONObject joCanvas = new JSONObject();
			for (int i = 0; i < vpCanvases.getWidgetCount(); i++) {
				HorizontalPanel hp = (HorizontalPanel) vpCanvases.getWidget(i);

				Label lbl = (Label) hp.getWidget(0);
				TextBox tb = (TextBox) hp.getWidget(1);
				if (tb.getValue() != null && !tb.getValue().isEmpty())
					joCanvas.put(lbl.getText(), JSONParser.parseStrict(tb.getValue()));
			}

			JSONObject jo = new JSONObject();
			jo.put("size", new JSONString(lbSizes.getSelectedValue()));
			jo.put("color", new JSONString(clr.getValue()));
			jo.put("canvas", joCanvas);

			Json j = new Json(jo);

			return j;
		}

		public void addCanvas(final String title, final String canvasjson) {
			Button btn = new Button(Ctrl.trans.changeCanvas());
			final TextBox tbJson = new TextBox();
			tbJson.setWidth("150px");
			if (canvasjson != null)
				tbJson.setValue(canvasjson);

			btn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					CanvasChangeDlg dlg = new CanvasChangeDlg(title, tbJson);
					if (tbJson.getValue() != null && !tbJson.getValue().isEmpty())
						dlg.loadData(JSONParser.parseStrict(tbJson.getValue()));
					dlg.show();
					dlg.center();
				}
			});

			HorizontalPanel hp = new HorizontalPanel();
			hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			hp.add(new Label(title));
			hp.add(tbJson);
			hp.add(btn);
			vpCanvases.add(hp);

		}
	}

	private class CanvasChangeDlg extends ActionBarDlg {
		TextBox tbw = new TextBox();
		TextBox tbh = new TextBox();
		TextBox tbx = new TextBox();
		TextBox tby = new TextBox();

		ImageBox renderedImage = new ImageBox();
		ImageBox backImage = new ImageBox();

		TextBox backW = new TextBox();
		TextBox backH = new TextBox();

		private TextBox tbJson;

		public CanvasChangeDlg(String canvas, TextBox tbJson) {
			super(Ctrl.trans.changeCanvas() + ":" + canvas, null, Ctrl.trans.ok());
			this.tbJson = tbJson;
			run();
		}

		public void loadData(JSONValue jsonValue) {
			if (jsonValue == null)
				return;

			final JSONObject jo = jsonValue.isObject();

			try {
				tbw.setValue(ClientUtil.getNumber(jo.get("w")) + "");
				tbh.setValue(ClientUtil.getNumber(jo.get("h")) + "");

				tbx.setValue(ClientUtil.getNumber(jo.get("x")) + "");
				tby.setValue(ClientUtil.getNumber(jo.get("y")) + "");

				backW.setValue(ClientUtil.getNumber(jo.get("backw")) + "");
				backH.setValue(ClientUtil.getNumber(jo.get("backh")) + "");

				String img = ClientUtil.getString(jo.get("rendered"));
				String backImg = ClientUtil.getString(jo.get("backimg"));

				renderedImage.setImage(img);
				backImage.setImage(backImg);

			} catch (Exception e) {
				Sistem.outln("load data" + e.getMessage());
			}

		}

		@Override
		public Widget ui() {
			final ListBox lb = new ListBox();
			lb.addItem(Ctrl.trans.selectAnItem());
			for (Iterator<String> iterator = CANVASES.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				lb.addItem(key, key);
			}
			
			lb.addChangeHandler(new ChangeHandler() {				
				@Override
				public void onChange(ChangeEvent event) {
					if (lb.getSelectedIndex() == 0)
						return;
					
					int[] is = CANVASES.get(lb.getSelectedValue());
					
					backW.setValue(is[0] + "");
					backH.setValue(is[1] + "");
					tbw.setValue(is[2] + "");
					tbh.setValue(is[3] + "");
					tbx.setValue(is[4] + "");
					tby.setValue(is[5] + "");
					
				}
			});
			
			tbw.setWidth("35px");
			tbh.setWidth("35px");
			tbx.setWidth("35px");
			tby.setWidth("35px");
			backW.setWidth("35px");
			backH.setWidth("35px");

//			backW.setValue("3500");
//			backH.setValue("4000");
//			tbw.setValue("1754");
//			tbh.setValue("2480");
//			tbx.setValue("870");
//			tby.setValue("460");

			FlowPanel fp = new FlowPanel();
			fp.add(tbw);
			fp.add(new Label(" x "));
			fp.add(tbh);

			FlowPanel fp1 = new FlowPanel();
			fp1.add(tbx);
			fp1.add(new Label(" x "));
			fp1.add(tby);

			FlowPanel fp2 = new FlowPanel();
			fp2.add(backW);
			fp2.add(new Label(" x "));
			fp2.add(backH);

			FlexTable ft = new FlexTable();

			ft.setHTML(0, 0, Ctrl.trans.background());
			ft.setWidget(0, 1, backImage);

			ft.setHTML(1, 0, Ctrl.trans.predefinedCanvases());
			ft.setWidget(1, 1, lb);
			
			ft.setHTML(2, 0, Ctrl.trans.backgroundSize());
			ft.setWidget(2, 1, fp2);

			ft.setHTML(3, 0, Ctrl.trans.canvasSize());
			ft.setWidget(3, 1, fp);

			ft.setHTML(4, 0, Ctrl.trans.canvasStartPoint());
			ft.setWidget(4, 1, fp1);

			ft.setHTML(5, 0, "Rendered Image");
			ft.setWidget(5, 1, renderedImage);

			return ft;
		}

		@Override
		public void cancel() {
		}

		@Override
		public void ok() {
			JSONObject jo = new JSONObject();
			jo.put("backimg", new JSONString(backImage.getImgPath()));

			jo.put("backw", new JSONNumber(Integer.parseInt(backW.getValue())));
			jo.put("backh", new JSONNumber(Integer.parseInt(backH.getValue())));

			jo.put("w", new JSONNumber(Integer.parseInt(tbw.getValue())));
			jo.put("h", new JSONNumber(Integer.parseInt(tbh.getValue())));

			jo.put("x", new JSONNumber(Integer.parseInt(tbx.getValue())));
			jo.put("y", new JSONNumber(Integer.parseInt(tby.getValue())));

			if (renderedImage.getImgPath() != null)
				jo.put("rendered", new JSONString(renderedImage.getImgPath()));

			tbJson.setValue(jo.toString());

		}
	}

	private class AddCanvasDlg extends ActionBarDlg {
		String[] canvases = { "front", "back" };
		ListBox lbCanvases = new ListBox();

		public AddCanvasDlg() {
			super("Add Canvas", null, Ctrl.trans.ok());
			run();
		}

		@Override
		public Widget ui() {
			for (int i = 0; i < canvases.length; i++) {
				lbCanvases.addItem(canvases[i]);
			}

			return lbCanvases;
		}

		@Override
		public void cancel() {
		}

		@Override
		public void ok() {
			addCanvas(lbCanvases.getSelectedValue());
		}
	}

	public void addCanvas(String title) {
		pnlOpts.addCanvas(title, null);

	}

	private class PnlTarife extends Composite {

		private final FlexTable form = new FlexTable();
		private final Button btnSave = new Button(Ctrl.trans.save());
		private final ListBox lbTarifs = new ListBox();

		public PnlTarife() {
			loadTarifs();
			forSave();

			form.setStyleName("site-panel");

			form.setHTML(0, 0, Ctrl.trans.amount());
			form.setWidget(0, 1, txtAmount);

			form.setHTML(1, 0, Ctrl.trans.select(Ctrl.trans.tariffModel()));
			form.setWidget(1, 1, lbTarifs);

			form.setHTML(2, 0, "----"+Ctrl.trans.or() + " " + Ctrl.trans.price() +"----");

			form.setHTML(3, 0, Ctrl.trans.price());
			form.setWidget(3, 1, txtPr);

			form.setWidget(5, 1, btnSave);

			form.getFlexCellFormatter().setColSpan(2, 0, 2);

			initWidget(form);
		}

		private void loadTarifs() {
			lbTarifs.clear();
			lbTarifs.addItem(Ctrl.trans.noTariffYet(), "");
			TariffmodelDao.list(Data.TARIFFMODEL_ROOT, new TariffmodelResponse() {
				@Override
				public void array(List<Tariffmodel> myarr) {
					if (myarr == null || myarr.size() <= 0)
						return;

					for (Iterator<Tariffmodel> iterator = myarr.iterator(); iterator.hasNext();) {
						Tariffmodel tf = (Tariffmodel) iterator.next();
						lbTarifs.addItem(tf.title, tf.uri);
					}
				}
			});

		}

		private void forSave() {
			btnSave.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					StocksDao.setamount(txtAmount.getIntVal(), uri, new BooleanResponse() {
						@Override
						public void ready(Boolean value) {
							Ctrl.setStatus("amount saved");
						}
					});

					if (!lbTarifs.getSelectedValue().isEmpty()) {
						StocksDao.settm(lbTarifs.getSelectedValue(), uri, new BooleanResponse() {
							@Override
							public void ready(Boolean value) {
								Ctrl.setStatus("tarife saved");
							}
						});
					} else {
						JSONObject jp = new JSONObject();
						jp.put("price", txtPr.getPrice());
						Json jo = new Json(jp);
						StocksDao.settariff(jo, uri, new BooleanResponse() {
							@Override
							public void ready(Boolean value) {
								Ctrl.setStatus("tarife saved");
							}
						});
					}
				}
			});
		}
	}

}
