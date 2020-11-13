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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class BrowseWidth extends ActionBarDlg {

	private final SiteIntegerBox tw = new SiteIntegerBox();
	private final SimplePanel rect = new SimplePanel();
	private HTML lbl1 = new HTML();

	public String selectedValue = null;
	private final String prHeight = "100px";

	private static int[] sugs = new int[] { 50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 600, 650, 700, 750, 800, 850 };
	private String val;


	public BrowseWidth(String val) {
		super(Ctrl.trans.width(), null, Ctrl.trans.ok());
		this.val = val.trim();
		run();
		
		this.show();
		this.center();
	}

	private void setValues(String val) {
		if (val != null && !val.isEmpty()) {			
			if (val.indexOf("%") < 0) { 
				lbl1.setHTML(getPx());
				rect.setSize(val, prHeight);
				tw.setValue(val.replace("px", ""));
			} else { 
				lbl1.setHTML(getPercent());
				rect.setSize(val, prHeight);
				tw.setValue(val);
			}
			
		} else {
			rect.setSize("10px", prHeight);
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
		tw.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				setValues(tw.getValue() + "px");
			}
		});

	}

	@Override
	public Widget ui() {
		tw.setWidth("40px");
		tw.setValue("50");


		rect.setStyleName("site-rect");
		setValues(val);

		final ListBox suggests = new ListBox();
		suggests.setVisibleItemCount(8);
		for (int i = 0; i < sugs.length; i += 1) {
			suggests.addItem(sugs[i] + "px");
		}
		suggests.addItem("25%");
		suggests.addItem("50%");
		suggests.addItem("75%");
		suggests.addItem("100%");

		forChangeW();
		forSuggest(suggests);

		HorizontalPanel l2 = new HorizontalPanel();
		l2.setSpacing(3);
		l2.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		l2.add(new Label(Ctrl.trans.suggestions()));
		l2.add(suggests);
		l2.add(tw);
		l2.add(lbl1);		
		
		VerticalPanel vp = new VerticalPanel();
		vp.add(l2);
		l2.add(rect);

		return vp;
	}

	@Override
	public void cancel() {
	}

	@Override
	public void ok() {
		String val = tw.getValue();
		
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
