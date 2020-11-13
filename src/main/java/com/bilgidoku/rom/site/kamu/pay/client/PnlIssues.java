package com.bilgidoku.rom.site.kamu.pay.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Issues;
import com.bilgidoku.rom.gwt.araci.client.rom.IssuesDao;
import com.bilgidoku.rom.gwt.araci.client.rom.IssuesResponse;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

public class PnlIssues extends Composite {

	private final CellTable<Issues> table = new CellTable<Issues>();
	private final NoSelectionModel<Issues> selectionModel = new NoSelectionModel<Issues>();

	private final HorizontalPanel tb = new HorizontalPanel();
	SiteButton reload = new SiteButton(pay.trans.reload(), "");
	SiteButton newIssue = new SiteButton(pay.trans.newIssue(), "");
	private VerticalPanel holder = new VerticalPanel();

	public PnlIssues() {
		table.setSelectionModel(selectionModel);

		addColumns();
		forSelect();

		forReload();
		forNew();

		tb.setStyleName("gwt-RichTextToolbar");
		// holder.addStyleName("site-padding");
		holder.setSpacing(5);

		initWidget(holder);
		getData();
	}

	private void forNew() {
		newIssue.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				newIssue();
			}
		});

	}

	private void forReload() {
		reload.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getData();

			}
		});

	}

	protected void newIssue() {
		final NewIssueDlg dlg = new NewIssueDlg();
		dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				if (!dlg.id.equals("0")) {
					getData();
				}
			}
		});

	}

	private void ui(boolean isEmpty) {

		tb.clear();
		holder.clear();

		if (isEmpty) {

			holder.add(new HTML(pay.trans.noIssuesInfo()));
			holder.add(newIssue);

		} else {
			tb.add(newIssue);
			// tb.add(listOpenOnes);
			tb.add(reload);

			holder.add(tb);
			holder.add(table);

		}

	}

	private void getData() {
		IssuesDao.listmine(true, true, null, null, "/_/issues", new IssuesResponse() {
			@Override
			public void array(List<Issues> myarr) {
				if (myarr.size() <= 0) {
					ui(true);
				} else {
					ui(false);
					populateTable(myarr);
				}

			}

		});
	}

	private void populateTable(List<Issues> myarr) {
		table.setRowCount(myarr.size(), true);
		table.setRowData(0, myarr);
	}

	private void forSelect() {
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				new DlgIss(selectionModel.getLastSelectedObject());
			}
		});

	}

//	public void clearSelection() {
//		selectionModel.
//	}

	private void addColumns() {

		TextColumn<Issues> txtId = new TextColumn<Issues>() {
			@Override
			public String getValue(Issues object) {
				return object.uri.substring(object.uri.lastIndexOf("/") + 1);

			}

		};

		TextColumn<Issues> txtTitle = new TextColumn<Issues>() {
			@Override
			public String getValue(Issues object) {
				return object.title;
			}
		};

		TextColumn<Issues> txtDesc = new TextColumn<Issues>() {
			@Override
			public String getValue(Issues object) {
				return getShort(object.description);
			}

			private String getShort(String comment) {
				if (comment == null || comment.isEmpty())
					return "";
				else if (comment.length() > 50)
					return comment.substring(0, 47) + "...";
				else
					return comment;
			}

		};

		TextColumn<Issues> txtState = new TextColumn<Issues>() {
			@Override
			public String getValue(Issues object) {
				return (object.close_date == null) ? pay.trans.opened() : pay.trans.closed();
			}
		};

		DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);
		DateCell dateCell = new DateCell(dateFormat);
		Column<Issues, Date> clmnCreateDate = new Column<Issues, Date>(dateCell) {
			@Override
			public Date getValue(Issues object) {
				DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
				Date date1 = dtf.parseStrict(object.creation_date);
				return date1;
			}
		};

		table.addColumn(txtId, "Id");

		table.addColumn(txtTitle, pay.trans.title());

		table.addColumn(txtDesc, pay.trans.description());

		table.addColumn(clmnCreateDate, pay.trans.createDate());

		table.addColumn(txtState, pay.trans.isOpen());
	}

	private class DlgIss extends ActionBarDlg {
		private Issues selected;
		private List<Contacts> cons;
		
		public DlgIss(final Issues selected) {
			super(ClientUtil.getTitleFromUri(selected.uri) + " nolu konu detayı", null, null);
			this.selected = selected;
			ContactsDao.list("", "/_/co", new ContactsResponse() {
				@Override
				public void array(List<Contacts> myarr) {
					cons = myarr;					
					run();
					show();
					center();
				}
			});
		}

		@Override
		public Widget ui() {			
			HashMap<String, Contacts> o = getContactHash(cons);
			PnlIssue iss = new PnlIssue(selected, o);
			return iss;
		}
		
		private HashMap<String, Contacts> getContactHash(List<Contacts> listContacts2) {
			HashMap<String, Contacts> conmap = new HashMap<String, Contacts>();
			for (int i = 0; i < listContacts2.size(); i++) {
				Contacts contacts = listContacts2.get(i);
				if (!conmap.containsKey(contacts.uri)) {
					conmap.put(contacts.uri, contacts);
				}
			}
			return conmap;
		}


		@Override
		public void cancel() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void ok() {
			// TODO Auto-generated method stub
			
		}
	}


}
