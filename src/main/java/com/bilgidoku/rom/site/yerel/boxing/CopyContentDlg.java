package com.bilgidoku.rom.site.yerel.boxing;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.gwt.client.util.Layer;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.boxer;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CopyContentDlg extends BoxerMenu {
	private final ListBox fromLangs = new ListBox();
	private final Button copy = new Button(Ctrl.trans.overwrite());
	private final CheckBox writing=new CheckBox("Body");
	private final CheckBox headerFooter=new CheckBox("Header-footer");
	
	
	
	public CopyContentDlg() {
		super(Ctrl.trans.overWriteThisPage(), Layer.layer1);
		writing.setValue(true);
		headerFooter.setValue(false);
		
		forCopy();
		
		VerticalPanel parts=new VerticalPanel();
		parts.add(writing);
		parts.add(headerFooter);
		
		HorizontalPanel vp = new HorizontalPanel();
		vp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		vp.setSpacing(3);
		vp.add(new Label(Ctrl.trans.fromLang()));
		vp.add(fromLangs);
		vp.add(parts);
		vp.add(copy);
		this.add(vp);
	}

	private void forCopy() {
		copy.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				
				boolean writingVal = writing.getValue();
				boolean hval=headerFooter.getValue();
				
				if(!writingVal && !hval){
					Window.alert("One of content should be selected!");
					return;
				}
				
				final String fromLang = fromLangs.getSelectedValue();
				if (Window.confirm(Ctrl.trans.confirmCopyLang(ClientUtil.findLangMatch(fromLang)))) {
					WritingsDao.copylangcontent(boxer.pageLang, fromLang, writingVal, writingVal, hval, hval, boxer.writing.uri, new StringResponse() {
						@Override
						public void ready(String value) {
							
							Window.Location.reload();
							
							
						}
					});
				}				
			}
		});		
	}

	
	public void loadLangs(String[] langcodes) {
		for (int i = 0; i < langcodes.length; i++) {
			
			if (langcodes[i].equals(boxer.pageLang))
				continue;
			
			fromLangs.addItem(ClientUtil.findLangMatch(langcodes[i]), langcodes[i]);
		}
	}

}
