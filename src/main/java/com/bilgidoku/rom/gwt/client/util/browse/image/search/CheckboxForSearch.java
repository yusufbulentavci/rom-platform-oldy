package com.bilgidoku.rom.gwt.client.util.browse.image.search;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;

public class CheckboxForSearch extends CheckBox {
	
	public CheckboxForSearch(String name, CheckBox[] mutuals) {
		super(name);
		setMutuals(mutuals);
	}

	public CheckboxForSearch(String name) {
		super(name);
	}


	public void setMutuals(final CheckBox[] mutuals) {
		this.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				if (mutuals == null)
					return;
				
				for (int i = 0; i < mutuals.length; i++) {
					mutuals[i].setValue(!CheckboxForSearch.this.getValue());
				}
				
			}
		});
	}
	

}
