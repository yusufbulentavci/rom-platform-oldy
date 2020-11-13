package com.bilgidoku.rom.site.yerel.mail;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.yerel.mail.core.InternetAddress;
import com.bilgidoku.rom.site.yerel.widget.BulletList;
import com.bilgidoku.rom.site.yerel.widget.ContactMultiWordSuggestion;
import com.bilgidoku.rom.site.yerel.widget.ContactSuggestOracle;
import com.bilgidoku.rom.site.yerel.widget.ListItem;
import com.bilgidoku.rom.site.yerel.widget.Paragraph;
import com.bilgidoku.rom.site.yerel.widget.Span;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextBox;

public class TextBoxMailAddr extends Composite {
	private final List<String> itemsSelected = new ArrayList<String>();
	private final BulletList ul = new BulletList();
	private final List<Contacts> contacts;
	private final SiteButton btnSelect = new SiteButton("/_local/images/common/up.png", "", "", "");

	public TextBoxMailAddr(List<Contacts> con) {
		this.contacts = con;
		ul.setStyleName("token-input-list-facebook");
		ul.addStyleName("gwt-TextBox");
		ul.setWidth("100%");

		final ListItem item = new ListItem();
		item.setStyleName("token-input-input-token-facebook");

		final TextBox itemBox = new TextBox();
		itemBox.getElement().setAttribute("style", "outline-color: -moz-use-text-color; outline-style: none; outline-width: medium;");
		itemBox.setWidth("100%");

		final SuggestBox box = new SuggestBox(getSuggestions(), itemBox);
		box.getElement().setId("suggestion_box");

		item.add(box);
		ul.add(item);

		forKeyUp(itemBox);
		forKeyDown(itemBox);
		forFocusOut(itemBox);
		forBoxSelect(itemBox, box);
		forSelect();

		HorizontalPanel panel = new HorizontalPanel();
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		panel.setWidth("100%");
		panel.setSpacing(0);
		
		panel.add(ul);
		panel.add(btnSelect);
		btnSelect.setHeight("26px");
		panel.setCellWidth(btnSelect, "20px");

		initWidget(panel);
		box.setFocus(true);
	}

	private void forFocusOut(final TextBox itemBox) {
		itemBox.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				if (validMailAddress(itemBox.getValue())) {
					deselectItem(itemBox, ul);
				}
				
			}
		});
		
	}

	private void forBoxSelect(final TextBox itemBox, SuggestBox box) {
		box.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
			public void onSelection(SelectionEvent selectionEvent) {
				deselectItem(itemBox, ul);
			}
		});
	}

	private void forSelect() {
		btnSelect.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ContactSelectWindow csw = new ContactSelectWindow(contacts, TextBoxMailAddr.this);
				csw.show();
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

	private void forKeyDown(final TextBox itemBox) {
		itemBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				// || event.getNativeKeyCode() == 59 ; || event.getNativeKeyCode() == 188 , 32 => space
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER || event.getNativeKeyCode() == KeyCodes.KEY_TAB || event.getNativeKeyCode() == 32 || event.getNativeKeyCode() == 188)   {
					if (validMailAddress(itemBox.getValue())) {
						deselectItem(itemBox, ul);
					}					
				}
				// handle backspace
				if (event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE) {
					if ("".equals(itemBox.getValue().trim())) {
						ListItem li = (ListItem) ul.getWidget(ul.getWidgetCount() - 2);
						Paragraph p = (Paragraph) li.getWidget(0);
						if (itemsSelected.contains(p.getText())) {
							itemsSelected.remove(p.getText());
							//Sistem.outln("Removing selected item '" + p.getText() + "'", null);
							//Sistem.outln("Remaining: " + itemsSelected, null);
						}
						ul.remove(li);
						itemBox.setFocus(true);
					}
				}
			}
		});
		
	}

	protected boolean validMailAddress(String value) {
		if (value.contains("@"))
			return true;
		return false;
	}

	private void deselectItem(final TextBox itemBox, final BulletList ul) {
		if (itemBox.getValue() != null && !itemBox.getValue().isEmpty()) {
			final ListItem li = new ListItem();
			li.setStyleName("token-input-token-facebook");
			Paragraph p = new Paragraph(itemBox.getValue());

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
	
			itemsSelected.add(itemBox.getValue());

			ul.insert(li, ul.getWidgetCount() - 1);
			itemBox.setValue("");
			itemBox.setFocus(true);
		}
	}

	private void removeListItem(ListItem displayItem, BulletList list) {
		itemsSelected.remove(displayItem.getWidget(0).getElement().getInnerHTML());
		list.remove(displayItem);
	}

	public void addItem(String text) {
		// incoming format bilo <admin@coreks.com>
		final TextBox itemBox = new TextBox();
		itemBox.getElement().setAttribute("style", "outline-color: -moz-use-text-color; outline-style: none; outline-width: medium;");
		itemBox.setValue(text);
		deselectItem(itemBox, ul);
	}

	private ContactSuggestOracle getSuggestions() {
		ContactSuggestOracle moracle = new ContactSuggestOracle();
		for (int i = 0; i < this.contacts.size(); i++) {
			moracle.add(new ContactMultiWordSuggestion(this.contacts.get(i)));
		}
    	return moracle;
    }
    
    public InternetAddress[] getEMails() {
    	if (ul.getWidgetCount() <= 0)
    		return null;    				
    	List<InternetAddress> ret = new ArrayList<InternetAddress>();
    	for (int j = 0; j < ul.getWidgetCount(); j++) {
    		ListItem li = (ListItem) ul.getWidget(j);
    		String addr = li.getWidget(0).getElement().getInnerText();
    		if (addr != null && addr.length()>0) {
    			ret.add(new InternetAddress(addr,null));
    		}
		}
    	if(ret.size()==0)
    		return null;
		return ret.toArray(new InternetAddress[ret.size()]);
	}

}
