package com.bilgidoku.rom.site.yerel.wgts.edit.code;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SelectWidget extends DialogBox{
	
	public String selected;
	final Button btnSave = new Button("Ok");
	final Button btnCancel = new Button("Cancel");
	
	
	public void start(List<String> widgetNames, final Runnable cb){
		VerticalPanel vert = new VerticalPanel();
		final ListBox listBox=new ListBox();
		listBox.setVisibleItemCount(10);
		
//		List<String> listNames = new ArrayList<String>();
//		listNames.addAll(widgetNames);		
//		Collections.sort(listNames);
		
		for (Iterator<String> iterator = widgetNames.iterator(); iterator.hasNext();) {
			String widget = (String) iterator.next();
			listBox.addItem(widget.substring(2), widget);
		}
		
		vert.add(listBox);
		
		FlowPanel flow=new FlowPanel();
		flow.add(btnSave);
		flow.add(btnCancel);
		
		vert.add(flow);
		
		this.setWidget(vert);		
		this.setText("Select widget");
		this.center();
		
		btnSave.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				int ind = listBox.getSelectedIndex();
				if(ind>=0)
					selected=listBox.getValue(ind);
				hide(true);
				cb.run();
			}
		});
		btnCancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selected=null;
				hide(true);
				cb.run();
			}
		});
		this.show();
		
	}
	

}
