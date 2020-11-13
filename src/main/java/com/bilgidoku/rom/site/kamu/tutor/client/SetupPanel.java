package com.bilgidoku.rom.site.kamu.tutor.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;

public class SetupPanel extends FlexTable{
	
	public SetupPanel(final Setup setup, final Leveler leveler){
		
		
	
		Button reset=new Button("Factory settings");
		reset.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				setup.factorySettings();
			}
		});
		
		this.setWidget(0, 0, reset);
		this.getFlexCellFormatter().setColSpan(0, 0,  2);
		
		setHTML(1, 0, "Level:");

		final ListBox lb = new ListBox();
		lb.setSelectedIndex(setup.getLevel());
		
		for(int i=0; i<leveler.levelLength(); i++){
			lb.addItem(leveler.getName(i));
		}

		setWidget(1, 1, lb);

		lb.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				setup.setLevel(lb.getSelectedIndex());
				setup.save();
			}
		});
		
		
	}

}
