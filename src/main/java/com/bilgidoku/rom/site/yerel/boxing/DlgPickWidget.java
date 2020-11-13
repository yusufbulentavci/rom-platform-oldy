package com.bilgidoku.rom.site.yerel.boxing;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.bilgidoku.rom.gwt.client.util.Layer;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.code.Wgt;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.boxer;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class DlgPickWidget extends BoxerMenu {

	private final WidgetCell wdtCell = new WidgetCell();
	private final CellList<MyWidget> wdtList = new CellList<MyWidget>(wdtCell);
	private final SingleSelectionModel<MyWidget> wdtListSelModel = new SingleSelectionModel<MyWidget>();
	public MyWidget selected;
	private BoxerGui boxerGui;

	public DlgPickWidget(BoxerGui boxerGui) {

		super(Ctrl.trans.addItem(), Layer.layer1);
		this.boxerGui = boxerGui;

		wdtList.setSelectionModel(wdtListSelModel);

		ScrollPanel sp = new ScrollPanel(wdtList);
		sp.setWidth("200px");
		sp.setHeight("400px");

		this.add(sp);

		loadData();

		forSelect();

	}

	private void forSelect() {
		wdtListSelModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				selected = wdtListSelModel.getSelectedObject();
				if (selected != null) {
					boxerGui.widgetSelected();
					DlgPickWidget.this.setVisible(false);
					wdtListSelModel.clear();
				}
			}
		});
	}
	
	public void loadData() {
		try {
			reloadData();
		} catch (RunException e) {
			Window.alert(e.getMessage());
			e.printStackTrace();
		}
	}

	private void reloadData() throws RunException {
		Set<String> setKeys = boxer.allCodeRepo.widgets.keySet();

		int i = 2;
		for (String key : setKeys) {
			Wgt widget = boxer.allCodeRepo.getWidget(key);
			String grp = widget.getGroup();
			if (grp == null)
				continue;

			if (!grp.equals("ecommerce") && !grp.startsWith(".") || (boxer.getECommerce() && grp.equals("ecommerce"))) {
				i++;
			}
		}

		MyWidget[] rows = new MyWidget[i];

		rows[0] = new MyWidget("atext", "html", Ctrl.trans.text());
		rows[1] = new MyWidget("atext", "svg", Ctrl.trans.svg());

		int j = 2;
		
		for (Iterator<String> iterator = setKeys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Wgt widget = boxer.allCodeRepo.getWidget(key);
			String grp = widget.getGroup();

			if (grp == null) {
				Window.alert("group null");
				continue;
			}

			if (!grp.equals("ecommerce") && !grp.startsWith(".") || (boxer.getECommerce() && grp.equals("ecommerce"))) {
				String lbl = boxer.getTranslation(key.replace("w:", "_"));

				MyWidget arr = new MyWidget(grp, key, lbl);
				rows[j] = arr;
				j++;

			}
		}

		// order list to grp
		Arrays.sort(rows);		

		List<MyWidget> meys = Arrays.asList(rows);

		wdtList.setVisibleRange(0, meys.size());
		wdtList.setRowCount(meys.size(), true);
		wdtList.setRowData(0, meys);


	}

	private class WidgetCell extends AbstractCell<MyWidget> {
		@Override
		public void render(Context context, MyWidget row, SafeHtmlBuilder sb) {
			if (row == null) {
				return;
			}
			String img = "";

			if (row.grp == null || row.grp.isEmpty() || row.grp.equals("other")) {
				img = "/_local/images/widgets/other.png";
			} else
				img = "/_local/images/widgets/" + row.grp + ".png";

			sb.appendHtmlConstant("<div style='width: 151px;position:relative;' class='gwt-RichTextToolbar'>"
					+ "<div style='float:left;background-repeat:no-repeat;width:40px; height:40px;background-image:url("
					+ img + ");background-position:center;'></div>"
					+ "<div style='height:40px;line-height:40px;text-align:left;'>" + row.lbl + "</div></div>");

		}
	}

	class MyWidget implements Comparable<MyWidget> {
		private String grp;
		private String key;
		private String lbl;
		private String img;

		public MyWidget(String grp1, String key1, String lbl1) {
			this.grp = grp1;
			this.key = key1;
			this.lbl = lbl1;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getLbl() {
			return lbl;
		}

		public void setLbl(String lbl) {
			this.lbl = lbl;
		}

		public String getImg() {
			return img;
		}

		public void setImg(String img) {
			this.img = img;
		}

		public String getGrp() {
			if (grp == null)
				return "";
			return grp;
		}

		public void setGrp(String grp) {
			this.grp = grp;
		}

		@Override
		public int compareTo(MyWidget o) {
			String grp = ((MyWidget) o).getGrp();
			return this.getGrp().compareTo(grp);
		}
	}

	public void infoChanged() {
		loadData();
	}

}
