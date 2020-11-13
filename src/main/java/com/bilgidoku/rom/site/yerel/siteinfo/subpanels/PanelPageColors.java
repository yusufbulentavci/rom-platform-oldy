package com.bilgidoku.rom.site.yerel.siteinfo.subpanels;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.widgets.PasteEvent;
import com.bilgidoku.rom.gwt.client.widgets.PasteHandler;
import com.bilgidoku.rom.gwt.client.widgets.colorpicker.ColorTextBox;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.pagedlgs.SiteDlg;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class PanelPageColors extends Composite {
	private ColorTextBox foremost;
	private ColorTextBox fore;
	private ColorTextBox backmost;
	private ColorTextBox back;
	private ColorTextBox color3;
	private ColorTextBox textColor;
	// private ColorTextBox textHiliColor;
	// private ColorTextBox textWeakColor;
	private ColorTextBox textColorOnFore;
	private ColorTextBox textColorOnForeMost;
	private ColorTextBox textColorOnColor3;

	private ColorCell colorCell;
	private CellList<String[]> pageColorList;
	private SingleSelectionModel<String[]> pageColorListSelModel;

	private final SiteDlg view;
	public boolean initted = false;

	public PanelPageColors(SiteDlg viewDlg) {
		this.view = viewDlg;
		init();
	}

	public void init() {
		initted = true;
		fore = new ColorTextBox();
		foremost = new ColorTextBox();
		backmost = new ColorTextBox();
		back = new ColorTextBox();
		color3 = new ColorTextBox();
		textColor = new ColorTextBox();
		// textHiliColor = new ColorTextBox();
		// textWeakColor = new ColorTextBox();
		textColorOnFore = new ColorTextBox();
		textColorOnForeMost = new ColorTextBox("#FFFFFF");
		textColorOnColor3 = new ColorTextBox();

		foremost.setTitle(Ctrl.trans.foremost());
		fore.setTitle(Ctrl.trans.fore());
		backmost.setTitle(Ctrl.trans.backmost());
		back.setTitle(Ctrl.trans.back());
		color3.setTitle(Ctrl.trans.color3());
		textColor.setTitle(Ctrl.trans.textColor());
		// textHiliColor.setTitle(Ctrl.trans.hili());
		// textWeakColor.setTitle(Ctrl.trans.weak());
		textColorOnFore.setTitle(Ctrl.trans.colorOnFore());
		textColorOnForeMost.setTitle(Ctrl.trans.colorOnForemost());
		textColorOnColor3.setTitle(Ctrl.trans.colorOnColor3());

		colorCell = new ColorCell();
		pageColorList = new CellList<String[]>(colorCell);
		pageColorListSelModel = new SingleSelectionModel<String[]>();

		setNames();
		forSelectColor();

		forForemostChange();
		forForeChange();
		forBackChange();
		forBackmostChange();
		forColor3Change();

		forTextColorChange();
		// forTextHiliChange();
		// forTextWeakChange();
		forColorOnForeChange();
		forColorOnForemostChange();
		forColorOnColor3Change();

		// ui
		pageColorList.setSelectionModel(pageColorListSelModel);
		List<String[]> colorData = new ArrayList<String[]>();
		for (String key : Data.PAGECOLORS.keySet()) {
			String value = Data.PAGECOLORS.get(key);
			value = value + "," + key;
			String[] colors = value.split(",");
			colorData.add(colors);
		}
		pageColorList.setVisibleRange(0, colorData.size());
		pageColorList.setRowCount(colorData.size(), true);
		pageColorList.setRowData(0, colorData);

		ScrollPanel pageColorScroll = new ScrollPanel(pageColorList);
		pageColorScroll.setSize("170px", "160px");

		FlexTable clrs = new FlexTable();

		clrs.setHTML(0, 1, Ctrl.trans.textColors());

		clrs.setWidget(1, 0, back);
		clrs.setWidget(1, 1, textColor);

		// clrs.setWidget(2, 1, new SiteLabel(Ctrl.trans.weak(),
		// Ctrl.trans.weakDesc()));
		// clrs.setWidget(2, 2, textWeakColor);
		//
		// clrs.setWidget(3, 1, new SiteLabel(Ctrl.trans.hili(),
		// Ctrl.trans.hiliDesc()));
		// clrs.setWidget(3, 2, textHiliColor);

		clrs.setWidget(4, 0, backmost);

		clrs.setWidget(5, 0, fore);
		clrs.setWidget(5, 1, textColorOnFore);

		clrs.setWidget(6, 0, foremost);
		clrs.setWidget(6, 1, textColorOnForeMost);

		clrs.setWidget(7, 0, color3);
		clrs.setWidget(7, 1, textColorOnColor3);

		FlexTable holder = new FlexTable();
		holder.setWidget(0, 0, pageColorScroll);
		holder.setWidget(1, 0, clrs);
//		holder.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

		initWidget(holder);
	}

	private void forSelectColor() {
		pageColorListSelModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				String[] arr = pageColorListSelModel.getSelectedObject();
				/*
				 * back, backmost, fore, foremost, color3, text_color,
				 * text_hili_color, text_weak_color, color_on_fore,
				 * color_on_foremost, color_on_color3
				 */
				String sBack = arr[0];
				String sBackmost = arr[1];
				String sFore = arr[2];
				String sForemost = arr[3];
				String sColor3 = arr[4];

				back.setHexValue(sBack);
				backmost.setHexValue(sBackmost);
				fore.setHexValue(sFore);
				foremost.setHexValue(sForemost);
				color3.setHexValue(sColor3);

				String sColor = getFontColor(sBack);
				String sHiliColor = ClientUtil.getDarkerFontColor(sBack);
				String sWeakColor = ClientUtil.getWeakFontColor(sBack);
				textColor.setHexValue(sColor);
				// textHiliColor.setHexValue(sHiliColor);
				// textWeakColor.setHexValue(sWeakColor);

				String sColorOnFore = getFontColor(sFore);
				String sColorOnForeMost = getFontColor(sForemost);
				String sColorOnColor3 = getFontColor(sColor3);

				textColorOnFore.setHexValue(sColorOnFore);
				textColorOnForeMost.setHexValue(sColorOnForeMost);
				textColorOnColor3.setHexValue(sColorOnColor3);

				view.paletteChanged();

			}
		});
	}

	private void setNames() {
		backmost.setBoxHeight("12px");
		back.setBoxHeight("12px");
		fore.setBoxHeight("12px");
		foremost.setBoxHeight("12px");
		color3.setBoxHeight("12px");

		// color5.setBoxHeight("12px");
		// -----
		textColor.setBoxHeight("12px");
		textColorOnColor3.setBoxHeight("12px");
		textColorOnFore.setBoxHeight("12px");
		textColorOnForeMost.setBoxHeight("12px");
		// textHiliColor.setBoxHeight("12px");
		// textWeakColor.setBoxHeight("12px");

	}

	private void textColorOnColor3Changed() {
		textColorOnColor3.setHexValue(textColorOnColor3.getValue());
		view.paletteChanged();
	}

	private void forColorOnColor3Change() {
		textColorOnColor3.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				textColorOnColor3Changed();
			}

		});
		textColorOnColor3.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				textColorOnColor3Changed();
			}
		}, PasteEvent.TYPE);
	}

	private void textColorOnForeMostChanged() {
		textColorOnForeMost.setHexValue(textColorOnForeMost.getValue());
		view.paletteChanged();
	}

	private void forColorOnForemostChange() {
		textColorOnForeMost.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				textColorOnForeMostChanged();
			}

		});
		textColorOnForeMost.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				textColorOnForeMostChanged();
			}
		}, PasteEvent.TYPE);
	}

	private void textColorOnForeChanged() {
		textColorOnFore.setHexValue(textColorOnFore.getValue());
		view.paletteChanged();
	}

	private void forColorOnForeChange() {
		textColorOnFore.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				textColorOnForeChanged();
			}

		});
		textColorOnFore.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				textColorOnForeChanged();
			}
		}, PasteEvent.TYPE);
	}

	// private void textWeakColorChanged() {
	// textWeakColor.setHexValue(textWeakColor.getValue());
	// view.paletteChanged();
	// }
	//
	// private void forTextWeakChange() {
	// textWeakColor.addChangeHandler(new ChangeHandler() {
	// @Override
	// public void onChange(ChangeEvent event) {
	// textWeakColorChanged();
	// }
	//
	// });
	// textWeakColor.addHandler(new PasteHandler() {
	// @Override
	// public void paste(PasteEvent event) {
	// textWeakColorChanged();
	// }
	// }, PasteEvent.TYPE);
	//
	// }
	//
	// private void textHiliColorChanged() {
	// textHiliColor.setHexValue(textHiliColor.getValue());
	// view.paletteChanged();
	// }
	//
	// private void forTextHiliChange() {
	// textHiliColor.addChangeHandler(new ChangeHandler() {
	// @Override
	// public void onChange(ChangeEvent event) {
	// textHiliColorChanged();
	// }
	// });
	// textHiliColor.addHandler(new PasteHandler() {
	// @Override
	// public void paste(PasteEvent event) {
	// textHiliColorChanged();
	// }
	// }, PasteEvent.TYPE);
	// }

	private void textColorChanged() {
		textColor.setHexValue(textColor.getValue());
		view.paletteChanged();
	}

	private void forTextColorChange() {
		textColor.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				textColorChanged();
			}
		});
		textColor.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				textColorChanged();
			}
		}, PasteEvent.TYPE);
	}

	private void forColor3Change() {
		color3.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				color3Changed();
			}
		});

		color3.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				color3Changed();
			}
		}, PasteEvent.TYPE);

	}

	private void color3Changed() {
		color3.setHexValue(color3.getValue());
		textColorOnColor3.setHexValue(getFontColor(color3.getValue()));
		view.paletteChanged();
	}

	private void forBackmostChange() {
		backmost.addDomHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				backmostChanged();
			}
		}, ChangeEvent.getType());
		backmost.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				backmostChanged();
			}
		}, PasteEvent.TYPE);
	}

	private void backmostChanged() {
		backmost.setHexValue(backmost.getValue());
		view.paletteChanged();
	}

	private void forBackChange() {
		back.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				backChanged();
			}
		});
		back.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				backChanged();
			}
		}, PasteEvent.TYPE);
	}

	private void backChanged() {
		back.setHexValue(back.getValue());
		view.paletteChanged();

		String sColor = getFontColor(back.getValue());
		String sHiliColor = ClientUtil.getDarkerFontColor(back.getValue());
		String sWeakColor = ClientUtil.getWeakFontColor(back.getValue());
		textColor.setHexValue(sColor);
		// textHiliColor.setHexValue(sHiliColor);
		// textWeakColor.setHexValue(sWeakColor);

	}

	private void forForeChange() {
		fore.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				foreChanged();
			}
		});
		fore.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				foreChanged();
			}
		}, PasteEvent.TYPE);
	}

	private void foreChanged() {
		fore.setHexValue(fore.getValue());
		textColorOnFore.setHexValue(getFontColor(fore.getValue()));
		view.paletteChanged();
	}

	private void forForemostChange() {
		foremost.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				foremostChanged();
			}
		});
		foremost.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				foremostChanged();
			}
		}, PasteEvent.TYPE);
	}

	private void foremostChanged() {
		foremost.setHexValue(foremost.getValue());
		textColorOnForeMost.setHexValue(getFontColor(foremost.getValue()));
		view.paletteChanged();
	}

	public void loadData(JSONObject jo) {
		back.setHexValue(ClientUtil.getString(jo.get("back")));
		backmost.setHexValue(ClientUtil.getString(jo.get("backmost")));
		foremost.setHexValue(ClientUtil.getString(jo.get("foremost")));
		fore.setHexValue(ClientUtil.getString(jo.get("fore")));
		color3.setHexValue(ClientUtil.getString(jo.get("color3")));

		textColor.setHexValue(ClientUtil.getString(jo.get("text_color")));
		// textHiliColor.setHexValue(ClientUtil.getString(jo.get("text_strong")));
		// textWeakColor.setHexValue(ClientUtil.getString(jo.get("text_weak")));
		textColorOnFore.setHexValue(ClientUtil.getString(jo.get("fore_text_color")));
		textColorOnColor3.setHexValue(ClientUtil.getString(jo.get("color3_text_color")));
		textColorOnForeMost.setHexValue(ClientUtil.getString(jo.get("foremost_text_color")));
	}

	public void putColors(JSONObject stored) {
		stored.put("back", new JSONString(back.getValue()));
		stored.put("backmost", new JSONString(backmost.getValue()));
		stored.put("foremost", new JSONString(foremost.getValue()));
		stored.put("fore", new JSONString(fore.getValue()));
		stored.put("color3", new JSONString(color3.getValue()));

		stored.put("text_color", new JSONString(textColor.getValue()));
		// stored.put("text_strong", new JSONString(textHiliColor.getValue()));
		// stored.put("text_weak", new JSONString(textWeakColor.getValue()));
		stored.put("fore_text_color", new JSONString(textColorOnFore.getValue()));
		stored.put("color3_text_color", new JSONString(textColorOnColor3.getValue()));
		stored.put("foremost_text_color", new JSONString(textColorOnForeMost.getValue()));
	}
	
	private class ColorCell extends AbstractCell<String[]> {
		
		@Override
		public void render(Context context, String[] row, SafeHtmlBuilder sb) {
			if (row == null) {
				return;
			}
			sb.appendHtmlConstant("<table>");
			sb.appendHtmlConstant("<tr>");
			for (int i = 0; i < (row.length -1); i++) {
				sb.appendHtmlConstant("<td width='40px' height='15px' style='border: 1px solid #99BBE8; background-color: "+ row[i] +";'></td>");
			}
			sb.appendHtmlConstant("</tr></table>");

		}
	}

	
	public static String getFontColor(String hex) {
		hex = hex.replace("#", "");
		if (hex.isEmpty())
			return "#FFFFFF";

		String rr = hex.substring(0, 2);
		String gg = hex.substring(2, 4);
		String bb = hex.substring(4);

		int r = ClientUtil.hexToInt(rr);
		int g = ClientUtil.hexToInt(gg);
		int b = ClientUtil.hexToInt(bb);

		int t = 0;

		if (r <= 128)
			t = t + 1;

		if (g <= 128)
			t = t + 1;

		if (b <= 128)
			t = t + 1;

		if (t > 1) {
			return "#FFFFFF"; // black
		} else {
			return "#3F4041"; // white
		}
	}


}
