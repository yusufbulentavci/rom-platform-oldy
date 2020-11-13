package com.bilgidoku.rom.site.yerel.wgts.edit.input.types;

import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBox;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.InputBoxRegistry;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;

class TickBoxRegistry extends InputBoxRegistry {

	@Override
	public boolean iCanInput(Att att) {
		return att.getContext()==Att.CONTEXT_TICK;
	}

	@Override
	public InputBox create(Att att, String value) {
		return new TickBox(att, value);
	}

}

public class TickBox extends InputBox {
	private final ListBox listBox;
	
	

	public static InputBoxRegistry getRegistry() {
		return new TickBoxRegistry();
	}

	public TickBox(Att att, String value) {
		
		
		listBox = getAttEnumBox(att, value);
		initWidget(listBox);
	}

	private ListBox getAttEnumBox(Att att, String value) {
		int[] ticks=new int[]{1,2,3,4,5,8,10,30,60};
		final ListBox tf = new ListBox();
		tf.setTitle(att.getNamed());
		
		tf.addItem("");
		
		int ind = -1;
		for (int secs : ticks) {
			String val=(secs*4)+"";
			tf.addItem(secs+"secs",val);
			if (value!=null && !value.isEmpty() && ind < 0) {
				if (value.equals(val))
					ind = (-ind);
				else
					ind--;
			}
		}
		if (ind > 0)
			tf.setSelectedIndex(ind);
		else
			tf.setSelectedIndex(0);

		tf.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				ListBox w = (ListBox) event.getSource();
				String name = w.getTitle();
				String value = w.getValue(w.getSelectedIndex());

				fireEvent(new InputChanged(name, value));
			}
		});
		return tf;
	}


}
