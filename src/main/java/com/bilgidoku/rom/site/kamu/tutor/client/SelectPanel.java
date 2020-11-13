package com.bilgidoku.rom.site.kamu.tutor.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SelectPanel extends VerticalPanel {

	private final Option[] names;
	
	private Selected noteSelected;

	public SelectPanel(Option[] names, Selected ns) {
		this.names=names;
		this.noteSelected=ns;
		HorizontalPanel hp=new HorizontalPanel();
		
		for (final Option o : names) {
			Button nb = new Button(o.name);
			hp.add(nb);
			nb.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
//					noteSelected.noteSelected(o.selectName);
				}
			});
		}
		
//		for (char i = 'a'; i < 'h'; i++) {
//			final String note=i + "";
//			Button nb = new Button(note+" "+names[i-'a']);
//			hp.add(nb);
//			nb.addClickHandler(new ClickHandler() {
//				
//				@Override
//				public void onClick(ClickEvent event) {
//					noteSelected.noteSelected(note);
//				}
//			});
//		}
		
		
		this.add(hp);
		Button nb = new Button("Dont know");
		nb.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				noteSelected.dontKnow();
			}
		});
		this.add(nb);

//		this.setSize("100%", "100px");

	}

}
