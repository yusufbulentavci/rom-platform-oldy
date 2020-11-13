package com.bilgidoku.rom.gwt.client.widgets;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.widgets.images.Images;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

public class DateBox extends Composite {
	
	private Images images = GWT.create(Images.class);
	
	private final TextBox tb = new TextBox();
	private Image btnOpenPopup = new Image(images.bullet_star());
	
	final DatePicker dp = new DatePicker();

	public DateBox() {
		
		dp.addValueChangeHandler(new ValueChangeHandler<Date>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date d = event.getValue();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				DateBox.this.fireEvent(new InputChanged("date", sdf.format(d)));
			}
		});
		initWidget(dp);
	}

	public HandlerRegistration addChangedHandler(InputChangedHandler handler) {
		return this.addHandler(handler, InputChanged.TYPE);
	}
	
	private class DlgDatePick extends ActionBarDlg {

		public DlgDatePick() {
			super("Pick a date", null, "OK");
			// TODO Auto-generated constructor stub
		}

		@Override
		public Widget ui() {
			return dp;
		}

		@Override
		public void cancel() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void ok() {
			// TODO Auto-generated method stub
			
		}
		
	}

}
