package com.bilgidoku.rom.site.yerel.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Widgets;
import com.bilgidoku.rom.gwt.araci.client.rom.WidgetsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.WidgetsResponse;
import com.bilgidoku.rom.site.yerel.mail.core.InternetAddress;
import com.bilgidoku.rom.site.yerel.widget.BulletList;
import com.bilgidoku.rom.site.yerel.widget.ListItem;
import com.bilgidoku.rom.site.yerel.widget.Paragraph;
import com.bilgidoku.rom.site.yerel.widget.Span;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;

public class WikiTextArea extends Composite{
	
	private final List<String> itemsSelected = new ArrayList<String>();
	private final BulletList ul = new BulletList();

	public WikiTextArea() {
		
		ul.setStyleName("token-input-list-facebook");
		ul.addStyleName("gwt-TextBox");
		ul.setWidth("100%");
		ul.setHeight("100px");

		final ListItem item = new ListItem();
		item.setStyleName("token-input-input-token-facebook");

		final TextBox itemBox = new TextBox();
		itemBox.getElement().setAttribute("style", "outline-color: -moz-use-text-color; outline-style: none; outline-width: medium;");
		itemBox.setWidth("100%");

		item.add(itemBox);
		ul.add(item);

		forKeyUp(itemBox);
//		forKeyDown(itemBox);
		forFocusOut(itemBox);
		

		HorizontalPanel panel = new HorizontalPanel();
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		panel.setWidth("100%");
		panel.setSpacing(0);
		
		panel.add(ul);

		initWidget(panel);
		itemBox.setFocus(true);
		
		String text = "0123{{ali/}}sto{{veli/}}";
//		WikiEval ev=new WikiEval(text);
//		ev.doit();
//		int textPointer = 0;
//		for (Ws ws : ev.ws) {
//			
//			if (ws.startStart.iStart > textPointer) {				
//				ul.add(new HTML(text.substring(textPointer, ws.startStart.iStart)));
//				textPointer = ws.startStart.iEnd;
//			}
//			
//			deselectItem(ws.name, ul);
//		}
//		if (text.length() > textPointer) {
//			ul.add(new HTML(text.substring(textPointer)));
//		}
		
	}
	
	private void getWidgets() {

		WidgetsDao.get("/_/widgets", new WidgetsResponse() {
			@Override
			public void ready(Widgets value) {
				HashMap<String, JSONObject> widgets = new HashMap<String, JSONObject>();
				JSONObject jo = value.codes.getValue().isObject();
				List<String> keys = new ArrayList<String>(jo.keySet());
				for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {

					String key = (String) iterator.next();
					JSONObject widget = (JSONObject) jo.get(key);
					widgets.put(key, widget);
				}

			}
		});

	}


	private void forFocusOut(final TextBox itemBox) {
		itemBox.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				if (validMailAddress(itemBox.getValue())) {
					
					deselectItem(itemBox.getValue(), ul);
					itemBox.setValue("");
					itemBox.setFocus(true);

					
				}
				
			}
		});
		
	}

	private void forKeyUp(final TextBox itemBox) {
		itemBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				//backspace
				if (event.getNativeKeyCode() == 188) {
					itemBox.setValue("");
				}
			}
		});
	}

//	private void forKeyDown(final TextBox itemBox) {
//		itemBox.addKeyDownHandler(new KeyDownHandler() {
//			public void onKeyDown(KeyDownEvent event) {
//				// || event.getNativeKeyCode() == 59 ; || event.getNativeKeyCode() == 188 , 32 => space
//				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER || event.getNativeKeyCode() == KeyCodes.KEY_TAB || event.getNativeKeyCode() == 32 || event.getNativeKeyCode() == 188)   {
//					if (validMailAddress(itemBox.getValue())) {
//						deselectItem(itemBox, ul);
//					}					
//				}
//				// handle backspace
//				if (event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE) {
//					if ("".equals(itemBox.getValue().trim())) {
//						ListItem li = (ListItem) ul.getWidget(ul.getWidgetCount() - 2);
//						Paragraph p = (Paragraph) li.getWidget(0);
//						if (itemsSelected.contains(p.getText())) {
//							itemsSelected.remove(p.getText());
//							//Sistem.outln("Removing selected item '" + p.getText() + "'", null);
//							//Sistem.outln("Remaining: " + itemsSelected, null);
//						}
//						ul.remove(li);
//						itemBox.setFocus(true);
//					}
//				}
//			}
//		});
//		
//	}

	protected boolean validMailAddress(String value) {
		if (value.contains("@"))
			return true;
		return false;
	}

	
	private void deselectItem(String text, final BulletList ul) {
		if (text != null && !text.isEmpty()) {
			final ListItem li = new ListItem();
			li.setStyleName("token-input-token-facebook");
			Paragraph p = new Paragraph(text);

			li.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent clickEvent) {
					li.addStyleName("token-input-selected-token-facebook");
				}
			});

			Span span = new Span("x");
			span.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent clickEvent) {
					removeListItem(li, ul);
				}
			});

			li.add(p);
			li.add(span);

			itemsSelected.add(text);
			ul.insert(li, ul.getWidgetCount() - 1);
		}
	}

	private void removeListItem(ListItem displayItem, BulletList list) {
		// Sistem.outln("Removing: " +
		// displayItem.getWidget(0).getElement().getInnerHTML(), null);
		itemsSelected.remove(displayItem.getWidget(0).getElement().getInnerHTML());
		list.remove(displayItem);
	}

	public void addItem(String text) {
		// incoming format bilo <admin@coreks.com>
		final TextBox itemBox = new TextBox();
		itemBox.getElement().setAttribute("style", "outline-color: -moz-use-text-color; outline-style: none; outline-width: medium;");
		itemBox.setValue(text);
		
		deselectItem(itemBox.getValue(), ul);
		itemBox.setValue("");
		itemBox.setFocus(true);

	}

    
    public InternetAddress[] getEMails() {
    	if (ul.getWidgetCount() <= 0)
    		return null;    				
    	List<InternetAddress> ret = new ArrayList<InternetAddress>();
    	for (int j = 0; j < ul.getWidgetCount(); j++) {
    		ListItem li = (ListItem) ul.getWidget(j);
    		String addr = li.getWidget(0).getElement().getInnerHTML();
    		if (addr != null && addr.length()>0) {
    			ret.add(new InternetAddress(addr,null));
    		}
		}
    	if(ret.size()==0)
    		return null;
		return ret.toArray(new InternetAddress[ret.size()]);
	}

	public void setValue(String string) {
		// TODO Auto-generated method stub
		
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCursorPos(int i) {
		// TODO Auto-generated method stub
		
	}

	public void setFocus(boolean b) {
		// TODO Auto-generated method stub
		
	}


}
