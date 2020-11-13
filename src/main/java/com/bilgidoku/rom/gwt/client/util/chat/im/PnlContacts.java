package com.bilgidoku.rom.gwt.client.util.chat.im;

import com.bilgidoku.rom.gwt.client.util.chat.im.repo.CbContacts;
import com.bilgidoku.rom.gwt.client.util.chat.im.repo.ContactBla;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class PnlContacts extends Composite {

	private final ContactCell conCell = new ContactCell();
	private final CellList<ContactBla> conList = new CellList<ContactBla>(conCell);

	final ListDataProvider<ContactBla> dataProvider = new ListDataProvider<ContactBla>();

	private final SingleSelectionModel<ContactBla> contListSelModel = new SingleSelectionModel<ContactBla>();
	private final XmppGui gui;
	
	public final CbContacts cb=new CbContacts() {
		
		
		

		@Override
		public void contactAdded(ContactBla contact) {
			dataProvider.getList().add(contact);
			conList.redraw();
		}
		
		@Override
		public void contactRemove(ContactBla vla) {
			dataProvider.getList().remove(vla);
			conList.redraw();
		}

		@Override
		public void resetList() {
			
		}

		@Override
		public void presenceChanged(ContactBla cbla) {
			conList.redraw();
		}

		@Override
		public void dlgSay(ContactBla param, boolean mine, String text) {

		}

		@Override
		public void dlgJoin(ContactBla param, boolean mine) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dlgContactPhotoChanged(ContactBla param, boolean mine) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dlgVideo(ContactBla param, boolean mine, String src, String docmd) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dlgTvImg(ContactBla param, boolean mine, String src) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dlgTvVideo(ContactBla param, boolean mine, String src) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dlgTvMark(ContactBla param, boolean mine, int markx, int marky) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dlgTvVideoCtrl(ContactBla param, boolean mine, Integer secs, String ctrl) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dlgTvText(ContactBla param, boolean mine, String str) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dlgTvShow(ContactBla param, boolean mine, char str) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dlgTvHeader(ContactBla param, boolean mine, String str) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dlgTvPresence(ContactBla param, boolean mine, String text) {
			// TODO Auto-generated method stub
			
		}

	};

	public PnlContacts(XmppGui gui) {
		this.gui=gui;
		conList.setSize("180px", "200px");
		conList.setSelectionModel(contListSelModel);
		dataProvider.addDataDisplay(conList);

		forSelect();
		forClickRow();

		ScrollPanel sp = new ScrollPanel(conList);
		sp.setStyleName("site-chatdlgin");
		initWidget(sp);
	}

//	public void contactsReady(Map<String, ContactBla> cs) {
//		List<ContactBla> blas = new ArrayList<ContactBla>();
//		blas.addAll(cs.values());
//
//		conList.setRowCount(blas.size(), true);
//		conList.setRowData(0, blas);
//	}

	private void forClickRow() {
		conList.addCellPreviewHandler(new com.google.gwt.view.client.CellPreviewEvent.Handler<ContactBla>() {
			@Override
			public void onCellPreview(CellPreviewEvent<ContactBla> event) {
				boolean isClick = BrowserEvents.CLICK.equals(event.getNativeEvent().getType());
				if (event.getColumn() > 0 && isClick) {
					gui.showChatDlg(contListSelModel.getSelectedObject());
				}
			}
		});
	}

	private void forSelect() {
		contListSelModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				gui.showChatDlg(contListSelModel.getSelectedObject());
			}
		});
	}

	private class ContactCell extends AbstractCell<ContactBla> {
		@Override
		public void render(Context context, ContactBla row, SafeHtmlBuilder sb) {
			if (row == null) {
				return;
			}

			String status = row.getPresenceImg();
			sb.appendHtmlConstant("<div style='width:180px;line-height:34px;display:inline-block;'>"
					+ "<img style='width:32px;height:32px;border:1px solid #dedede;float:left;margin: 1px 5px 1px 0;' src='/_public/images/contact.gif'></img>"
					+ row.getContactName() + "<img style='float:right;padding-top:10px;' " + "src='" + status
					+ "'></img>" + " </div>");

		}
	}



//	private int findCidIndex(String cid) {
//		int i = 0;
//		List<ContactBla> lst = dataProvider.getList();
//
//		for (; i < lst.size(); i++) {
//			ContactBla it = lst.get(i);
//			if (it.contactUri().equals(cid)) {
//				break;
//			}
//		}
//
//		if (i < lst.size())
//			return i;
//		return -1;
//	}
	
//	private ContactBla findCidBla(String cid) {
//		int ind = findCidIndex(cid);
//		if (ind < 0)
//			return null;
//		return dataProvider.getList().get(ind);
//	}



	// public void appendToChatHistory(String uri, String html) {
	// ContactBla bla = contacts.getBla(uri);
	// if (bla == null)
	// return;
	// bla.addMsg(html);
	//
	// }

}