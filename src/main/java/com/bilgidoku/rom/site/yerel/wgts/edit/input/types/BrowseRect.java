package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.Widget;

public class BrowseRect extends ActionBarDlg {

	private final TextBox tw = new TextBox();
	private final TextBox th = new TextBox();
	private final SimplePanel rect = new SimplePanel();
	private final HorizontalPanel pnlShowSelected = new HorizontalPanel();
	private HTML lbl1 = new HTML();
	private HTML lbl2 = new HTML();

	public String selectedValue = null;
	public String selectedObject = null;

	private static int[] sugs = new int[] { 300, extracted(300, 0.66f), 350, extracted(350, 0.66f), 400,
			extracted(400, 0.66f), 450, extracted(450, 0.66f), 500, extracted(500, 0.60f), 550, extracted(550, 0.60f),
			600, extracted(600, 0.60f), 650, extracted(650, 0.60f), 700, extracted(700, 0.55f), 750,
			extracted(750, 0.55f), 800, extracted(800, 0.55f) };
	private String[] vals;

	private static int extracted(int val, float ratio) {
		return (int) Math.round(val * ratio);
	}

	public BrowseRect(String[] vals) {
		super(Ctrl.trans.size(), null, Ctrl.trans.ok());
		this.vals = vals;	
		run();		
		this.show();
		this.center();
	}

	private String getPx() {
		return "px&nbsp;&nbsp;&nbsp;";
	}

	private String getPercent() {
		return "&nbsp;&nbsp;&nbsp;";
	}

	private void forSuggest(final ListBox suggests) {
		suggests.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String val = suggests.getValue(suggests.getSelectedIndex());
				String[] vals = val.split("-");
				String width = vals[0].trim();
				String height = vals[1].trim();

				tw.setValue(width);
				th.setValue(height);

				if (vals[0].trim().indexOf("%") > 0) {
					lbl1.setHTML(getPercent());
				} else {
					lbl1.setHTML(getPx());
					width = width + "px";
				}

				if (vals[1].trim().indexOf("%") > 0) {
					lbl2.setHTML(getPercent());

				} else {
					lbl2.setHTML(getPx());
					height = height + "px";
				}
				rect.setSize(width, height);

			}
		});

	}

	private void forChangeW() {
		tw.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				rect.setWidth(tw.getValue().trim());
				if (tw.getValue().isEmpty()) {
					rect.setHeight("1px");
				}
			}
		});

	}

	private void forChangeH() {
		th.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				rect.setHeight(th.getValue().trim());
				if (tw.getValue().isEmpty()) {
					rect.setWidth("1px");
				}

			}
		});

	}


	@Override
	public Widget ui() {
		th.setAlignment(TextAlignment.RIGHT);
		tw.setAlignment(TextAlignment.RIGHT);
		tw.setWidth("40px");
		th.setWidth("40px");
		rect.setStyleName("site-rect");
		if (vals != null) {			
			
			if (vals[0].indexOf("%") < 0) { 
				lbl1.setHTML(getPx());
				tw.setValue(vals[0].trim().replace("px", ""));
			} else 
				tw.setValue(vals[0].trim());

			if (vals[1].indexOf("%") < 0) { 
				lbl2.setHTML(getPx());
				th.setValue(vals[1].trim().replace("px", ""));
			} else
				th.setValue(vals[1].trim());

			rect.setSize(vals[0].trim(), vals[1].trim());
		} else {
			rect.setSize("1px", "1px");
		}

		final ListBox suggests = new ListBox();
		suggests.setVisibleItemCount(8);
		for (int i = 0; i < sugs.length; i += 2) {
			suggests.addItem(sugs[i] + " - " + sugs[i + 1] + "");
		}
		suggests.addItem("100% - 450");

		forChangeW();
		forChangeH();
		forSuggest(suggests);

		pnlShowSelected.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		pnlShowSelected.add(new Label(Ctrl.trans.width() + ":"));
		pnlShowSelected.add(tw);
		pnlShowSelected.add(lbl1);
		pnlShowSelected.add(new Label(Ctrl.trans.height() + ":"));
		pnlShowSelected.add(th);
		pnlShowSelected.add(lbl2);

		HorizontalPanel l1 = new HorizontalPanel();
		l1.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
		l1.add(new Label(Ctrl.trans.size() + ":"));
		l1.add(suggests);

		FlexTable vp = new FlexTable();
		vp.setCellPadding(2);
		vp.setCellSpacing(3);
		vp.setHTML(0, 0, Ctrl.trans.suggestions());
		vp.setWidget(0, 1, suggests);
		// vp.setWidget(0, 2, hp);
		vp.setWidget(1, 1, pnlShowSelected);
		vp.setWidget(2, 0, rect);
		vp.getFlexCellFormatter().setWidth(0, 0, "70px");
		vp.getFlexCellFormatter().setColSpan(2, 0, 4);
		vp.getFlexCellFormatter().setColSpan(1, 1, 3);

		return vp;
	}

	@Override
	public void cancel() {
		
	}

	@Override
	public void ok() {

		 String w = tw.getValue();
		 String h = th.getValue();

		if (w == null || w.isEmpty()) {
			Window.alert(Ctrl.trans.control() + Ctrl.trans.width());
			return;
		}

		if (h == null || h.isEmpty()) {
			Window.alert(Ctrl.trans.control() + Ctrl.trans.height());
			return;
		}

		if (w.indexOf("%") <= 0) {
			w = w.trim() + "px";
		}

		if (h.indexOf("%") <= 0) {
			h = h.trim() + "px";

		} 

		
		selectedValue = w + ", " + h;
		selectedObject = "{\"width\":\"" + w + "\",\"height\":\"" + h + "\"}";

		
	}
}
