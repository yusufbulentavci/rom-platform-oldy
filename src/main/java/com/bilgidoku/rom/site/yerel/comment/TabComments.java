package com.bilgidoku.rom.site.yerel.comment;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Comments;
import com.bilgidoku.rom.gwt.araci.client.rom.CommentsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.CommentsResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Contacts;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.DialogsDao;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.contacts.TabContact;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class TabComments extends Composite {

	private final CellTable<CommentsExtended> table = new CellTable<CommentsExtended>();
	// private final TextArea body = new TextArea();
	private final SimplePanel body = new SimplePanel();
	private final SingleSelectionModel<CommentsExtended> selectionModel = new SingleSelectionModel<CommentsExtended>();
	private final String dialogUri;

	// private final String panelMod;

	public TabComments(String dialogUri, String topic, final String mod) {
		this.dialogUri = dialogUri;

		getData(mod);
		forSelect();

		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
		table.setSelectionModel(selectionModel);
		addColumns(dialogUri);
		populateTable();

		body.setSize("100%", "100%");
		body.addStyleName("site-padding");
		body.addStyleName("site-holder");
		// body.setVisibleLines(7);

		SiteButton reload = new SiteButton("/_local/images/common/refresh.png", Ctrl.trans.reload(), Ctrl.trans.reload(), "");
		
		reload.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				listComments = null;
				hashContacts = null;
				data = null;

				getData(mod);

			}
		});
		Widget[] btns = { reload };
		VerticalPanel hp = new VerticalPanel();
		hp.addStyleName("site-padding");
		hp.add(ClientUtil.getToolbar(btns, 1));
		hp.add(table);

		SplitLayoutPanel sp = new SplitLayoutPanel();
		sp.addSouth(body, 200);
		sp.add(new ScrollPanel(hp));

		initWidget(sp);
	}

	private void getData(String mod) {
		if (mod.equals("waitingapproval"))
			getWaitingComments();
		else if (mod.equals("all"))
			getAllComments();
		else if (mod.equals("allwaiting"))
			getAllWating();
		
		getContacts();
		populateTable();
	}

	private void getAllWating() {
		CommentsDao.listwaitingapproval(null, "/_/_comments", new CommentsResponse() {
			@Override
			public void array(List<Comments> value) {
				listComments = value;
				populateTable();
			}
		});
		
	}

	// --get data
	private List<Comments> listComments = null;
	private HashMap<String, Contacts> hashContacts = null;
	private LinkedList<CommentsExtended> data = null;

	private void getAllComments() {
		DialogsDao.comments(dialogUri, new CommentsResponse() {
			@Override
			public void array(List<Comments> value) {
				listComments = value;
				populateTable();
			}
		});

	}

	private void getContacts() {
		ContactsDao.list("", "/_/co", new ContactsResponse() {
			public void array(List<Contacts> value) {
				hashContacts = getContactHash(value);
				populateTable();
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

		});
	}

	private void getWaitingComments() {
		CommentsDao.listwaitingapproval(dialogUri, "/_/_comments", new CommentsResponse() {
			@Override
			public void array(List<Comments> value) {
				listComments = value;
				populateTable();
			}
		});
	}

	private void populateTable() {
		if (listComments == null || this.hashContacts == null)
			return;
		data = makeReady();
		refreshTable();

	}
	
	private void refreshTable() {		
		table.setRowCount(data.size(), true);
		table.setRowData(0, data);
		if (data.size() > 0) {
			CommentsExtended first = data.get(0);
			selectionModel.setSelected(first, true);
		}

	}

	private LinkedList<CommentsExtended> makeReady() {
		LinkedList<CommentsExtended> lex = new LinkedList<CommentsExtended>();
		for (int i = 0; i < listComments.size(); i++) {
			Comments comments = listComments.get(i);
			
			if (comments.delegated.startsWith(Data.ISSUE_ROOT))
				continue;
			
			CommentsExtended ce = new CommentsExtended(comments);
			ce.setCon(hashContacts.get(comments.contact));
			lex.add(ce);
		}
		return lex;
	}

	private void forSelect() {
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				CommentsExtended selected = selectionModel.getSelectedObject();
				if (selected != null) {
					body.setWidget(new HTML(selected.getCom().comment));
				}
			}
		});
	}

	private void addColumns(String dialogUri) {

		ClickableTextCell clickPage = new ClickableTextCell();
		
		Column<CommentsExtended, String> clickPg = new Column<CommentsExtended, String>(clickPage) {
			public String getValue(CommentsExtended object) {
				return ClientUtil.getTitleFromUri(object.delegated());
			}
		};
		
		clickPg.setFieldUpdater(new FieldUpdater<CommentsExtended, String>() {
			@Override
			public void update(int index, CommentsExtended object, String value) {
				gotoPage(object.delegated());
			}

			private void gotoPage(final String uri) {
				Ctrl.editPage(uri);
			}
		});

		table.addColumn(clickPg, Ctrl.trans.openPage());

		
		ClickableTextCell clickContact = new ClickableTextCell();
		Column<CommentsExtended, String> click = new Column<CommentsExtended, String>(clickContact) {
			public String getValue(CommentsExtended object) {
				return object.getCon().first_name + " " + object.getCon().last_name;
			}
		};
		click.setFieldUpdater(new FieldUpdater<CommentsExtended, String>() {
			@Override
			public void update(int index, CommentsExtended object, String value) {
				gotoContact(object.getCon().uri, object.getCon().first_name, object.getCon().last_name);
			}

			private void gotoContact(final String uri, final String first_name, final String last_name) {
				TabContact tw = new TabContact(uri, false);
				Ctrl.openTab(uri, first_name + " " + last_name, tw, Data.MAIL_COLOR);
			}
		});

		table.addColumn(click, Ctrl.trans.openContact());

		TextColumn<CommentsExtended> textColumn = new TextColumn<CommentsExtended>() {
			@Override
			public String getValue(CommentsExtended object) {
				return getShortComment(object.getCom().comment);
			}

			private String getShortComment(String comment) {
				if (comment == null || comment.isEmpty())
					return "";
				else if (comment.length() > 50)
					return comment.substring(0, 47) + "...";
				else
					return comment;
			}
		};
		table.addColumn(textColumn, Ctrl.trans.comment());

		DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);
		DateCell dateCell = new DateCell(dateFormat);
		Column<CommentsExtended, Date> dateColumn = new Column<CommentsExtended, Date>(dateCell) {
			@Override
			public Date getValue(CommentsExtended object) {
				DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
				Date date1 = dtf.parseStrict(object.getCom().creation_date);
				return date1;
			}
		};
		table.addColumn(dateColumn, Ctrl.trans.commentDate());

		// buttonCell.
		ButtonCell delButton = new ButtonCell();
		Column<CommentsExtended, String> del = new Column<CommentsExtended, String>(delButton) {
			public String getValue(CommentsExtended object) {
				return Ctrl.trans.delete();
			}
		};
		del.setFieldUpdater(new FieldUpdater<CommentsExtended, String>() {
			@Override
			public void update(int index, CommentsExtended object, String value) {
				if (Window.confirm(Ctrl.trans.confirmDelete())) {
					CommentsExtended selected = selectionModel.getSelectedObject();
					if (selected != null) {
						deleteComment(selected.getCom().uri);
					}
				}
			}

			private void deleteComment(String toDel) {
				CommentsDao.destroy(toDel, new StringResponse() {
					@Override
					public void ready(String value) {
						data.remove(selectionModel.getSelectedObject());
						refreshTable();
					}
				});
			}
		});
		table.addColumn(del, Ctrl.trans.delete());

		Column<CommentsExtended, String> approve = getApproveButton();
		table.addColumn(approve, Ctrl.trans.approve());
	}

	private Column<CommentsExtended, String> getApproveButton() {
		ButtonCell approveButton = new ButtonCell();
		Column<CommentsExtended, String> approve = new Column<CommentsExtended, String>(approveButton) {
			public String getValue(CommentsExtended object) {
				if (object.getCom().approved)
					return Ctrl.trans.notApproved();
				else
					return Ctrl.trans.ok();
			}
		};
		approve.setFieldUpdater(new FieldUpdater<CommentsExtended, String>() {
			@Override
			public void update(int index, CommentsExtended object, String value) {
				CommentsExtended selected = selectionModel.getSelectedObject();
				if (selected != null) {
					approveComment(selected.getCom().uri);
				}
			}

			private void approveComment(String toDel) {
				CommentsDao.approve(toDel, new StringResponse() {
					@Override
					public void ready(String value) {
						data.remove(selectionModel.getSelectedObject());
						refreshTable();
					}
				});
			}
		});
		return approve;
	}
}
