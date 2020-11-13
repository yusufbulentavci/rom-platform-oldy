package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBoxRegistry;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

class PxBoxRegistry extends InputBoxRegistry {

	@Override
	public boolean iCanInput(Att att) {		
		return att.getType() == 0;
	}

	@Override
	public InputBox create(Att att, String value) {
		return new PxContextBox(att, value, 50);
	}

}

public class PxContextBox extends InputBox {

	private final ListBox listBox = new ListBox(false);

	protected PxContextBox(final Att att, String value, int rangeEnd) {
		this(att.getNamed(), value, rangeEnd);
	}
	
	public PxContextBox(final String name, String value, int rangeEnd) {
		listBox.setName(name);		
		listBox.setTitle(name);
		
		for (int i = 0; i < rangeEnd; i++) {
			listBox.addItem(i + "");
		}
		
		if (value != null && !value.isEmpty())
			ClientUtil.findAndSelect(listBox, value.replace("px", ""));
		
		listBox.addChangeHandler(new ChangeHandler() {			
			@Override
			public void onChange(ChangeEvent event) {
				fireEvent(new InputChanged(name, listBox.getValue(listBox.getSelectedIndex()) + "px"));
				
			}
		});
		
		HorizontalPanel fp = new HorizontalPanel();
		fp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		fp.add(listBox);
		fp.add(new Label("px"));
		
		
		initWidget(fp);
	}

	public static InputBoxRegistry getRegistry() {
		return new IntegerBoxRegistry();
	}

	public String getPxValue() {
		return listBox.getValue(listBox.getSelectedIndex()) + "px";
	}

	public int getIntValue() {
		int i = 0;
		try {
			String val =  listBox.getValue(listBox.getSelectedIndex());
			i = Integer.parseInt(val);
		} catch (Exception e) {
			// TODO: handle exception
		}				
		return i;
	}

	public void setPxValue(String pxValue) {
		if (pxValue != null && !pxValue.isEmpty())
			ClientUtil.findAndSelect(listBox, pxValue.replace("px", ""));
		
	}

}
