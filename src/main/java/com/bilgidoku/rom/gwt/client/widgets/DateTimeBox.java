package com.bilgidoku.rom.gwt.client.widgets;

import java.util.Date;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.widgets.images.Images;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

public class DateTimeBox extends Composite {
	private final Images images = GWT.create(Images.class);

	SiteButton btnOpenPopup = new SiteButton(images.pencil(), "", "", "sml");
	private final TextBox tb = new TextBox();
	// private Image btnOpenPopup = new Image(images.bullet_star());

	final DatePicker dp = new DatePicker();
	final DlgDatePick dlg = new DlgDatePick();
	final ListBox lbHours = new ListBox();
	final ListBox lbMinutes = new ListBox();

	public DateTimeBox() {
		
		loadTime();

//		dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
//			@Override
//			public void onClose(CloseEvent<PopupPanel> event) {
//				tb.setValue(dlg.getValueStr());
//			}
//		});

		dp.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				tb.setValue(dlg.getValueStr());
				dlg.hide();
				fireDate();
				
			}
		});
		
		lbHours.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				fireDate();
			}
		});

		lbMinutes.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				fireDate();
			}
		});

		btnOpenPopup.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dlg.show();
				dlg.center();

			}
		});

		tb.setWidth("120px");
		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(tb);
		hp.add(btnOpenPopup);
		hp.add(new HTML("  "));
		hp.add(lbHours);
		hp.add(new HTML(":"));
		hp.add(lbMinutes);
		initWidget(hp);

	}

	private void loadTime() {

		for (int i = 0; i < 24; i++) {
			String str = ("0" + i);
			str = str.length() > 2 ? str.substring(str.length() - 2) : str;
			lbHours.addItem(str, str);
		}

		for (int i = 0; i < 59; i++) {
			String str = ("0" + i);
			str = str.length() > 2 ? str.substring(str.length() - 2) : str;
			lbMinutes.addItem(str, str);
		}

	}

	protected String getTime() {
		return lbHours.getSelectedValue() + ":" + lbMinutes.getSelectedValue();
	}

	public String getValue() {
		return tb.getValue() + " " + getTime();
	}

	public void setValue(String formatted) {
		String[] h = formatted.split(" ");
		tb.setValue(h[0]);
		
		String[] ts = h[1].split(":");
		String hour = ts[0];
		String minute = ts[1];
		
		int hi = Integer.parseInt(hour);
		int mi = Integer.parseInt(minute);
		
		lbHours.setSelectedIndex(hi);
		lbMinutes.setSelectedIndex(mi);
	}

	
	private class DlgDatePick extends ActionBarDlg {

		public DlgDatePick() {
			super("Pick a date", null, null);
			run();
		}

		public String getValueStr() {
			Date d = dp.getValue();
			if (d == null)
				return "";

			DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd");
			return format.format(d);
		}

		@Override
		public Widget ui() {
			return dp;
		}

		@Override
		public void cancel() {
		}

		@Override
		public void ok() {
		}

	}

	public HandlerRegistration addChangedHandler(InputChangedHandler handler) {
		return this.addHandler(handler, InputChanged.TYPE);
	}

	protected void fireDate() {
		DateTimeBox.this.fireEvent(new InputChanged("date", getValue()));
	}
	

}
