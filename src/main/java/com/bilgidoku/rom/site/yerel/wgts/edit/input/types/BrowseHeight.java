package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.SiteIntegerBox;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class BrowseHeight extends ActionBarDlg {

	private final SiteIntegerBox th = new SiteIntegerBox();
	private final SimplePanel rect = new SimplePanel();
	private HTML lbl1 = new HTML();

	public String selectedValue = null;
	private final String prWidth = "75px";
	private final ListBox suggests = new ListBox();
	
	private static int[] sugs = new int[] { 50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850 };

	public BrowseHeight(String val) {
		super(Ctrl.trans.height(), null, Ctrl.trans.ok());
		val = val.trim();
		th.setWidth("75px");
		th.setValue("75");

		rect.setStyleName("site-rect");
		setValues(val);
	
		forChangeW();
		forSuggest(suggests);
	
		run();
		this.show();
		this.center();

	}

	private void setValues(String val) {
		if (val != null && !val.isEmpty()) {			
			if (val.indexOf("%") < 0) { 
				lbl1.setHTML(getPx());
				rect.setSize(prWidth, val);
				th.setValue(val.replace("px", ""));
			} else { 
				lbl1.setHTML(getPercent());
				rect.setSize(prWidth, val);
				th.setValue(val);
			}
			
		} else {
			rect.setSize(prWidth, prWidth);
		}		
	}

	private void forSuggest(final ListBox suggests) {
		suggests.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String val = suggests.getValue(suggests.getSelectedIndex());
				setValues(val);

			}
		});

	}
	
	private String getPx() {
		return "px&nbsp;&nbsp;&nbsp;";
	}

	private String getPercent() {
		return "&nbsp;&nbsp;&nbsp;";
	}

	private void forChangeW() {
		th.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				setValues(th.getValue() + "px");
			}
		});

	}

	@Override
	public Widget ui() {
		
		suggests.setVisibleItemCount(8);
		for (int i = 0; i < sugs.length; i += 1) {
			suggests.addItem(sugs[i] + "px");
		}

		HorizontalPanel l2 = new HorizontalPanel();
		l2.setSpacing(3);
		l2.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);				
		l2.add(new Label(Ctrl.trans.suggestions()));
		l2.add(suggests);	
		l2.add(th);
		l2.add(lbl1);
		l2.add(rect);

		return l2;
	}

	@Override
	public void cancel() {
	}

	@Override
	public void ok() {

		String val = th.getValue();
		
		if (val == null || val.isEmpty()) {
			Window.alert(Ctrl.trans.control() + Ctrl.trans.width());
			return;
		}

		if (val.indexOf("%") >= 0)
			selectedValue = val;
		else
			selectedValue = val + "px";
	
	}
}
