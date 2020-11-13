package com.bilgidoku.rom.site.yerel.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Mails;
import com.bilgidoku.rom.gwt.araci.client.rom.MailsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.MailsResponse;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.widgets.images.Images;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.site.yerel.ClientKnownError;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.bilgidoku.rom.site.yerel.mail.core.EmailMbMeta;
import com.bilgidoku.rom.site.yerel.mail.core.InternetAddress;
import com.bilgidoku.rom.site.yerel.mail.core.MailUtil;
import com.bilgidoku.rom.site.yerel.mail.core.MailWrap;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.RowStyles;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;

public class TabMailList extends Composite implements TabMailView {

	private final CellTable<MailWrap> table = new CellTable<MailWrap>();
	private List<MailWrap> pageMails;
	private static int PAGESIZE = 10;
	private final String mailboxUri;

	private SiteToolbarButton btnPrev = new SiteToolbarButton("/_local/images/common/page_previous.png", Ctrl.trans.previousPage(), "", "");
	private SiteToolbarButton btnNext = new SiteToolbarButton("/_local/images/common/page_next.png", Ctrl.trans.nextPage(), "", "");
	
	private HTML pageInfo = new HTML();

	private NavMailBox nav;
	private final TabMailToolbar toolbar;
	private int offset = 0;
	private int totalMailCount = 0;

	private final MultiSelectionModel<MailWrap> selectionModel = new MultiSelectionModel<MailWrap>(
			new ProvidesKey<MailWrap>() {
				@Override
				public Object getKey(MailWrap item) {
					return item.getUri();
				}
			});

	public TabMailList(final String mbUri, NavMailBox nav) {
		// offset is needed
		this.mailboxUri = mbUri;
		this.nav = nav;		
		this.toolbar = new TabMailToolbar(this, getType(), false);
		
		Label emptyBox = new Label(Ctrl.trans.emptyMailBox());
		emptyBox.setStyleName("site-tag");
		emptyBox.setWidth("2000px");

		table.setSelectionModel(selectionModel, DefaultSelectionEventManager.<MailWrap> createCheckboxManager());
		table.setEmptyTableWidget(new Label(Ctrl.trans.emptyMailBox()));
		
		addColumns();
		toolbar.hideButtons();

		forRowChecked();
		forReadMail();
		forNext();
		forPrevious();

		FlexTable top = new FlexTable();
		top.setWidth("100%");		
		top.setWidget(0, 0, toolbar);
		top.setWidget(0, 1, uiPager());
		top.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		top.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		// ui
		final DockLayoutPanel vp = new DockLayoutPanel(Unit.EM);
		vp.addNorth(top, 2.8);
		vp.add(new ScrollPanel(table));
		initWidget(vp);		
		
		ContainersDao.listing("rom", "mails", "/_/c", new ContainersResponse() {
			public void array(List<Containers> value) {
				for (Iterator<Containers> iterator = value.iterator(); iterator.hasNext();) {
					Containers containers = (Containers) iterator.next();
					if (containers.uri.equals(mbUri)) {
						EmailMbMeta mm = new EmailMbMeta(containers.nesting);
						totalMailCount = mm.getCount();
						loadPage();
						break;
					}
				}	
			}

		});

		
	}

	private void forPrevious() {
		btnPrev.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				loadPreviousPage();

			}
		});

	}

	protected void loadPreviousPage() {
		if (offset - PAGESIZE >= 0) {
			offset = offset - PAGESIZE;
		}
		loadPage();

	}

	private void forNext() {
		btnNext.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				loadNextPage();

			}
		});

	}

	protected void loadNextPage() {
		if (offset < totalMailCount) {
			offset = offset + PAGESIZE;
		}
		loadPage();
	}

	private Widget uiPager() {
		
		FlowPanel f = new FlowPanel();
		f.add(btnPrev);
		f.add(pageInfo);
		f.add(btnNext);
		f.addStyleName("gwt-RichTextToolbar");
		
		return f;
	}

	private void forReadMail() {

		table.addCellPreviewHandler(new Handler<MailWrap>() {

			@Override
			public void onCellPreview(CellPreviewEvent<MailWrap> event) {
				// read mail, open tabmail
				boolean isClick = BrowserEvents.CLICK.equals(event.getNativeEvent().getType());

				if (event.getColumn() > 0 && isClick) {

					readMail(event.getValue());

				}

			}

		});

	}

	private void loadPage() {

		MailsDao.list(offset, PAGESIZE, mailboxUri, new MailsResponse() {
			@Override
			public void array(List<Mails> myarr) {

				List<MailWrap> emails = new ArrayList<MailWrap>();
				pageMails = emails;
				for (int i = 0; i < myarr.size(); i++) {
					Mails emailData = myarr.get(i);
					try {
						emails.add(new MailWrap(emailData));
					} catch (ClientKnownError e) {
						// TODO Auto-generated catch block
						Sistem.printStackTrace(e);
					}
				}
				// Collections.reverse(emails);

				table.setRowCount(emails.size(), true);
				table.setRowData(emails);
				updateUI(offset, offset + PAGESIZE);

			}
		});
	}

	protected void updateUI(int from, int to) {
		pageInfo.setHTML(" " + from + " to " + to + "  ");

		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);

		if (from <= 1) {
			btnPrev.setEnabled(false);
		}

		if (to >= totalMailCount) {
			btnNext.setEnabled(false);
		}

	}

	private void forRowChecked() {
		table.getSelectionModel().addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				// get selected row count
				if (getSelectedItes().isEmpty()) {
					toolbar.hideButtons();
				} else {
					toolbar.showButtons();
				}

			}
		});
	}

	protected void readMail(final MailWrap email) {
		if (mailboxUri.indexOf("draft") > 0) {
			TabNewMail newMail = new TabNewMail(email.getSubject(), email.getTo(), email.getCc(), email);
			Ctrl.openTab(email.getUri(), Ctrl.trans.mail() + ":" + email.getSubject(), newMail, Data.MAIL_COLOR);

		} else {

//			TabReadMail mail = new TabReadMail(email, mailboxUri, nav, this);
//			Ctrl.openTab(email.getUri(), Ctrl.trans.mail() + ":" + email.getSubject(), mail, Data.MAIL_COLOR);

		}

	}

	private void addColumns() {

		CheckboxHeader selectAll = new CheckboxHeader();
		selectAll.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue())
					for (int i = 0; i < pageMails.size(); i++) {
						selectionModel.setSelected(pageMails.get(i), true);
					}
				else
					for (int i = 0; i < pageMails.size(); i++) {
						selectionModel.setSelected(pageMails.get(i), false);
					}

			}
		});

		Column<MailWrap, Boolean> checkColumn = new Column<MailWrap, Boolean>(new CheckboxCell(true, false)) {
			@Override
			public Boolean getValue(MailWrap object) {
				if (object == null || object.getUri() == null)
					return null;
				// Get the value from the selection model.
				return selectionModel.isSelected(object);
			}
		};
		table.setColumnWidth(checkColumn, 35.0, Unit.PX);
		table.addColumn(checkColumn, selectAll);

		final Images img = GWT.create(Images.class);

		Column<MailWrap, ImageResource> attachColumn = new Column<MailWrap, ImageResource>(new ImageResourceCell()) {
			@Override
			public ImageResource getValue(MailWrap object) {
				if (object.hasAttachment())
					return img.bullet_attach();
				else
					return null;

			}
		};
		table.setColumnWidth(attachColumn, 35.0, Unit.PX);
		table.addColumn(attachColumn, "");

		Column<MailWrap, ImageResource> importantColumn = new Column<MailWrap, ImageResource>(new ImageResourceCell()) {
			@Override
			public ImageResource getValue(MailWrap object) {
				if (object.isImportant())
					return img.bullet_star();
				else
					return null;

			}
		};
		table.setColumnWidth(importantColumn, 35.0, Unit.PX);
		table.addColumn(importantColumn, "");

		if (getType().equals("draft") || getType().equals("sent")) {
			TextColumn<MailWrap> toColumn = new TextColumn<MailWrap>() {
				@Override
				public String getValue(MailWrap email) {
					InternetAddress[] internetAddresses = email.getTo();
					if (internetAddresses == null || internetAddresses.length < 1) {
						return "";
					}

					String to = "";
					for (int i = 0; i < internetAddresses.length; i++) {
						InternetAddress addr = internetAddresses[i];
						to = ", " + MailUtil.mailFormat(addr.getPersonal(), addr.getAddress());
					}
					return to.substring(2);
				}
			};
			table.addColumn(toColumn, Ctrl.trans.to());

		} else {

			TextColumn<MailWrap> fromColumn = new TextColumn<MailWrap>() {
				@Override
				public String getValue(MailWrap email) {
					InternetAddress[] internetAddresses = email.getFrom();
					if (internetAddresses == null || internetAddresses.length < 1) {
						return "";
					}

					String from = "";
					for (int i = 0; i < internetAddresses.length; i++) {
						InternetAddress addr = internetAddresses[i];
						from = ", " + MailUtil.mailFormat(addr.getPersonal(), addr.getAddress());
					}
					return from.substring(2);
				}

			};
			table.addColumn(fromColumn, Ctrl.trans.from());

		}

		TextColumn<MailWrap> subjectColumn = new TextColumn<MailWrap>() {
			@Override
			public String getValue(MailWrap email) {
				return getShortComment(email.getSubject());
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
		table.addColumn(subjectColumn, Ctrl.trans.subject());

		DateCell dateCell = new DateCell(DateTimeFormat.getMediumDateTimeFormat());

		Column<MailWrap, Date> dateColumn = new Column<MailWrap, Date>(dateCell) {
			@Override
			public Date getValue(MailWrap object) {
				// return new Date(object.getDate());
				return object.getDate();
			}
		};
		table.addColumn(dateColumn, Ctrl.trans.date());

		table.setRowStyles(new RowStyles<MailWrap>() {
			@Override
			public String getStyleNames(MailWrap obj, int rowIndex) {
				if (!obj.isRead()) {
					return "site-bold";
				} else {
					return "site-normal";
				}
			}
		});
	}

	private Set<MailWrap> getSelectedItes() {
		@SuppressWarnings("unchecked")
		MultiSelectionModel<MailWrap> mu = ((MultiSelectionModel<MailWrap>) table.getSelectionModel());
		Set<MailWrap> set = mu.getSelectedSet();
		return set;
	}

	@SuppressWarnings("unchecked")
	private void clearSelection() {
		((MultiSelectionModel<MailWrap>) table.getSelectionModel()).clear();
	}

	@Override
	public Set<MailWrap> getMailItems() {
		return getSelectedItes();
	}

	@Override
	public void markAsReaded(MailWrap email) {
//		nav.mailRead(mailboxUri);
		reload();
	}

	@Override
	public void markAsUnReaded(MailWrap email) {
//		nav.mailUnRead(mailboxUri);
		reload();
	}

	@Override
	public void mailDeleted(MailWrap email) {
//		nav.mailDeleted(mailboxUri, email);
		reload();
	}

	@Override
	public void reload() {
		loadPage();
		toolbar.hideButtons();
		clearSelection();
	}

	@Override
	public void moved(MailWrap email, String target) {
//		nav.mailDeleted(mailboxUri, email);
//		nav.mailAdded(target, email);
		reload();
	}

	@Override
	public void markAsImportant(MailWrap email) {
		reload();

	}

	@Override
	public void markAsUnImportant(MailWrap email) {
		reload();

	}

	public void markAsRead(MailWrap email) {
		toolbar.markAsRead(email);
		//
		table.redraw();
	}

	@Override
	public String getType() {
		return ClientUtil.getTitleFromUri(mailboxUri);
	}

}
