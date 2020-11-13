package com.bilgidoku.rom.site.yerel.writings;

import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Containers;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersDao;
import com.bilgidoku.rom.gwt.araci.client.rom.ContainersResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.gwt.araci.client.site.Contents;
import com.bilgidoku.rom.gwt.araci.client.site.ContentsDao;
import com.bilgidoku.rom.gwt.araci.client.site.ContentsResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Writings;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsResponse;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.gwt.client.util.common.NewItemDlg;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItem;
import com.bilgidoku.rom.gwt.client.util.common.SiteTreeItemData;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.AccessDlg;
import com.bilgidoku.rom.site.yerel.common.NavToolbarBase;
import com.bilgidoku.rom.site.yerel.common.NavTreeItem;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.bilgidoku.rom.site.yerel.writings.wizard.ApplyTemplate;
import com.bilgidoku.rom.site.yerel.writings.wizard.PageReady;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

public class NavToolbar extends NavToolbarBase implements PageReady {

	private final NavWriting nav;
	public SiteToolbarButton btnOpen = new SiteToolbarButton("/_local/images/common/pencil.png", "",
			Ctrl.trans.openPage(), "sayfa_open.mp4");
	public SiteToolbarButton btnMakePrivate = new SiteToolbarButton("/_local/images/common/key.png", "",
			Ctrl.trans.isConfidential(), "sayfa-interneteac.mp4");

	public SiteToolbarButton btnReset = new SiteToolbarButton("/_local/images/common/reset.png", "", Ctrl.trans.reset(),
			"sayfa-yenidenolustur.mp4");

	public NavToolbar(NavWriting nav) {
		this.nav = nav;

		this.btnNew.setHelpy("sayfa-yenisayfa.mp4");
		this.btnRename.setHelpy("sayfa-yenidenisimlendir.mp4");
		this.btnDelete.setHelpy("sayfa-sil.mp4");

		forOpen();
		forPrivate();
		forReset();

		Widget[] btns = { btnNew, btnReload, btnRename, btnMakePrivate, ClientUtil.getSeperator(), btnOpen, btnDelete,
				btnReset, btnCopyLink };
		this.add(ClientUtil.getToolbar(btns, 9));
	}

	private void forReset() {
		btnReset.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				TreeItem selItem = nav.getTree().getSelectedItem();
				if (selItem == null) {
					Window.alert(Ctrl.trans.selectAnItem());
					return;
				}
				SiteTreeItemData selNode = (SiteTreeItemData) selItem.getUserObject();
				String uri = selNode.getUri();
				if (selNode.isContainer()) {
					uri = selNode.getUriPrefix();
					uri = uri.substring(0, uri.length() - 1);
					if (uri.isEmpty())
						uri = "/";
				}

				WritingsDao.get(Ctrl.infoLang(), uri, new WritingsResponse() {
					@Override
					public void ready(Writings value) {

						ResetDlg resetDlg = new ResetDlg();

						resetDlg.loadData(value.container, ClientUtil.getTitleFromUri(value.container), value.title[0],
								value.uri);

						resetDlg.show();

					}
				});

			}
		});

	}

	private void forPrivate() {
		btnMakePrivate.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
//				final SiteTreeItem selected = (SiteTreeItem) nav.getSelectedItem();
//				if (selected == null)
//					return;
//
//				final SiteTreeItemData data = (SiteTreeItemData) selected.getUserObject();
//
//				if (!data.isContainer()) {
//					return;
//				}
//				if (data.getMask().equals(Data.WRITING_PUBLIC_MASK)) {
//					if (Window.confirm(Ctrl.trans.makePrivate())) {
//						ResourcesDao.setmask(Data.WRITING_PRIVATE_MASK, selected.getDragData(), new StringResponse() {
//							@Override
//							public void ready(String value) {
//								reloadContainer(selected.getParentItem());
//							}
//						});
//					}
//				} else {
//					if (Window.confirm(Ctrl.trans.makePublic())) {
//						ResourcesDao.setmask(Data.WRITING_PUBLIC_MASK, selected.getDragData(), new StringResponse() {
//							@Override
//							public void ready(String value) {
//								reloadContainer(selected.getParentItem());
//							}
//						});
//					}
//
//				}

				TreeItem selItem = nav.getTree().getSelectedItem();
				if (selItem == null) {
					Window.alert(Ctrl.trans.selectAnItem());
					return;
				}
				SiteTreeItemData selNode = (SiteTreeItemData) selItem.getUserObject();
				String uri = selNode.getUri();
				if (selNode.isContainer()) {
					uri = selNode.getUriPrefix();
					uri = uri.substring(0, uri.length() - 1);
					if (uri.isEmpty())
						uri = "/";
				}
				new AccessDlg(uri);

			}
		});

	}

	private void forOpen() {
		btnOpen.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TreeItem selItem = nav.getTree().getSelectedItem();
				if (selItem == null) {
					Window.alert(Ctrl.trans.selectAnItem());
					return;
				}
				SiteTreeItemData selNode = (SiteTreeItemData) selItem.getUserObject();
				String uri = selNode.getUri();
				if (selNode.isContainer()) {
					uri = selNode.getUriPrefix();
					uri = uri.substring(0, uri.length() - 1);
					if (uri.isEmpty())
						uri = "/";
				}
				openItem(false, uri);
			}
		});

	}

	@Override
	public void deleteItem() {

		final TreeItem toDel = nav.getSelectedItem();
		if (toDel == null)
			return;

		final SiteTreeItemData data = (SiteTreeItemData) toDel.getUserObject();

		if (Ctrl.info().langcodes.length == 1) {
			// site için tek dil
			if (Window.confirm(Ctrl.trans.confirmDelete() + "\n" + ClientUtil.getTitleFromUri(data.getUri())))
				deletePage(data.isContainer(), data.getUri(), toDel);

			return;
		}

		/// _/writings

		final String delUri = data.getUri().equals("/_/writings") ? "/" : data.getUri();

		WritingsDao.get(Ctrl.info().langcodes[0], delUri, new WritingsResponse() {
			@Override
			public void ready(final Writings value) {
				if (value.langcodes.length == 1) {

					if (Window.confirm(Ctrl.trans.confirmDelete() + "\n" + ClientUtil.getTitleFromUri(data.getUri())))
						deletePage(data.isContainer(), data.getUri(), toDel);

					return;

				}

				TreeItem selItem = nav.getTree().getSelectedItem();
				int sag = selItem.getElement().getFirstChildElement().getAbsoluteLeft()
						+ selItem.getElement().getFirstChildElement().getClientWidth();
				int top = selItem.getElement().getFirstChildElement().getAbsoluteTop();

				// ask language
				final DlgDelete dlg = new DlgDelete(value.langcodes);
				dlg.show();
				dlg.setPopupPosition(sag + 10, top);
				dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						// check whether this content exists
						if (dlg.selected == null) {
							deletePage(data.isContainer(), data.getUri(), toDel);
							return;
						}

						WritingsDao.dellang(dlg.selected, delUri, new BooleanResponse() {
							@Override
							public void ready(Boolean value) {
								Ctrl.setStatus("Dil silindi");
							}
						});

					}
				});

			}
		});

	}

	private void deletePage(boolean container, final String uri, final TreeItem toDel) {
		if (nav.getSelectedItem().getParentItem() == null) {
			Window.alert("Ana sayfa silinemez");
			return;
		}

		if (!container)
			WritingsDao.destroy(uri, new StringResponse() {
				public void ready(String value) {
					toDel.remove();
					Ctrl.closeTab(uri);
				}
			});
		else
			WritingsDao.extinct(uri, new StringResponse() {
				@Override
				public void ready(String value) {
					toDel.remove();
					Ctrl.closeTab(uri);
				}
			});

	}

	@Override
	public void editSelectedItem() {
		TreeItem selItem = nav.getTree().getSelectedItem();
		if (selItem == null) {
			Window.alert(Ctrl.trans.selectAnItem());
			return;
		}
		SiteTreeItemData selNode = (SiteTreeItemData) selItem.getUserObject();
		if (selNode.isContainer()) {
			String uri = selNode.getUriPrefix();
			uri = uri.substring(0, uri.length() - 1);
			if (uri.isEmpty())
				uri = "/";
			editItem(uri);
		} else
			editItem(selNode.getUri());
	}

	@Override
	public void editItem(String uri) {
		openItem(true, uri);
	}

	private void openItem(final boolean inFrame, final String pageUri) {

		if (Ctrl.info().langcodes.length == 1) {
			openUriInLang(pageUri, Ctrl.info().langcodes[0], inFrame);
			return;
		}

		// there are more than one language
		WritingsDao.get(Ctrl.info().langcodes[0], pageUri, new WritingsResponse() {
			@Override
			public void ready(final Writings value) {

				TreeItem selItem = nav.getTree().getSelectedItem();
				int sag = selItem.getElement().getFirstChildElement().getAbsoluteLeft()
						+ selItem.getElement().getFirstChildElement().getClientWidth();
				int top = selItem.getElement().getFirstChildElement().getAbsoluteTop();

				// ask language
				final DlgChooseLang dlg = new DlgChooseLang(value.langcodes);
				dlg.setPopupPosition(sag + 10, top);
				dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						// check whether this content exists
						if (dlg.selectedLang == null || dlg.selectedLang.isEmpty())
							return;

						if (ClientUtil.existInArray(value.langcodes, dlg.selectedLang)) {
							openUriInLang(pageUri, dlg.selectedLang, inFrame);
							return;
						}
						// create writing in lang and open
						createLangAndOpen(pageUri, dlg.selectedLang, inFrame);

					}
				});
			}
		});

	}

	private void openUriInLang(String pageUri, String lang, boolean inFrame) {

		UrlBuilder builder = Location.createUrlBuilder().setPath(pageUri);
		ClientUtil.removeAllParameters(builder);
		builder.setParameter("pagelang", lang);
		// builder.setParameter("lng", lang);
		builder.setParameter("rom.render", "htmledit");
		builder.setParameter("locale", RomEntryPoint.one.currentLocale);

		// if (Location.getPath().contains("debug")) {
		// builder.setParameter("gwt.debug", "edit");
		// }

		String openUri = builder.buildString();

		if (inFrame) {
			Ctrl.setStatus(pageUri);

			// Frame frm = new Frame(openUri);
			// frm.setWidth("99%");
			// frm.setHeight("99%");

			WritingFrame frm = WritingFrame.create(pageUri, lang, inFrame);

			Ctrl.startWaiting();

			String tabTitle = ClientUtil.getTitleFromUri(pageUri);
			if (Ctrl.isMultiLang()) {
				tabTitle = tabTitle + " (" + lang + ") ";
			}

			Ctrl.openTab(openUri, tabTitle, frm, Data.CONTENT_COLOR);

		} else {
			Window.open(openUri, "_blank", "");
		}

	}

	private void createLangAndOpen(final String pageUri, final String lang, final boolean inFrame) {
		if (Window.confirm(Ctrl.trans.confirmNewLang(ClientUtil.findLangMatch(lang)))) {
			WritingsDao.newlang(lang, pageUri, new StringResponse() {
				@Override
				public void ready(String value) {

					WritingsDao.copylangcontent(lang, Ctrl.infoLang(), true, true, false, false, pageUri,
							new StringResponse() {
								@Override
								public void ready(String value) {
									openUriInLang(pageUri, lang, inFrame);
								}
							});

				}
			});
		}
	}

	@Override
	public void newItem() {

		if (nav.getSelectedItem() == null) {
			Window.alert(Ctrl.trans.selectAnItem());
			return;
		}

		SiteTreeItemData data = (SiteTreeItemData) nav.getSelectedItem().getUserObject();

		final NewPageDlg dlg = new NewPageDlg("", "", data.getUriPrefix(), Data.WRITING_ROOT, data.isContainer(),
				Ctrl.trans.newItem());
		dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				String pageLang = dlg.getPageLang();
				String title = dlg.getNamed();
				String fixedTitle = dlg.getFixedTitle();
				String summary = dlg.getSummary();
				String image = dlg.getImage();
				String template = dlg.getTemplate();

				if (title != null && !title.isEmpty()) {
					createWriting(pageLang, title, fixedTitle, summary, image, template);
				}
			}

		});
	}

	private void createWriting(String pageLang, String title, String fixedTitle, String summary, String image,
			String template) {

		final TreeItem parent = nav.getSelectedItem();
		final SiteTreeItemData data = nav.getSelectedData();

		if (data.isContainer()) {
			addWriting(parent, pageLang, title, fixedTitle, summary, image, template);
		} else {
			makeParentContainerAndAddWriting(parent, pageLang, title, fixedTitle, summary, image, template);
		}

	}

	private void makeParentContainerAndAddWriting(final TreeItem parent, final String pageLang, final String title,
			final String fixedTitle, final String summary, final String image, final String template) {

		final SiteTreeItemData data = (SiteTreeItemData) parent.getUserObject();

		final String uri = Data.WRITING_ROOT + data.getUri();
		final String uriPrefix = data.getUri() + "/";
		final String parentContainer = nav.getHolderUri();

		WritingsDao.breed(uri, pageLang, Data.WRITING_PUBLIC_MASK, uriPrefix, "w:standart", title, parentContainer,
				new ContainersResponse() {
					@Override
					public void ready(Containers value) {
						Ctrl.setStatus(Ctrl.trans.added(title));

						NavTreeItem prnt = (NavTreeItem) parent;
						prnt.changeIcon("/_local/images/common/folder.png");
						prnt.setUserObject(new SiteTreeItemData(data.getTitle(), uri, true, uriPrefix));

						addWriting(parent, pageLang, title, fixedTitle, summary, image, template);

					}
				});

	}

	private void addWriting(final TreeItem parent, final String pageLang, final String title, final String fixedTitle,
			final String summary, final String image, final String template) {

		SiteTreeItemData data = (SiteTreeItemData) parent.getUserObject();

		final String delegated = data.getUri();
		final String uriPrefix = data.getUriPrefix();

		final String uri = uriPrefix + fixedTitle;

		WritingsDao.neww(pageLang, title, uri, delegated, new StringResponse() {
			public void ready(final String writingUri) {
				if (template != null && !template.isEmpty()) {
					new ApplyTemplate(delegated, parent.getText(), title, uri, template, NavToolbar.this, pageLang);
				} else {
					new ApplyTemplate(delegated, parent.getText(), title, uri, "default", NavToolbar.this, pageLang);
					// Ctrl.setStatus(Ctrl.trans.added(title));
				}
				applyContent(uri, pageLang, summary, image);
				nav.addLeaf(parent, writingUri, title);
			}
		});

	}

	protected void applyContent(String uri, String pageLang, String summary, String image) {
		if (summary != null && !summary.isEmpty()) {
			ContentsDao.summary(pageLang, summary, uri, new StringResponse() {
				@Override
				public void ready(String value) {
				}
			});
		}

		if (image != null && !image.isEmpty()) {
			ContentsDao.mediumicon(image, uri, new StringResponse() {
				@Override
				public void ready(String value) {
				}
			});

			ContentsDao.largeicon(image, uri, new StringResponse() {
				@Override
				public void ready(String value) {
				}
			});

		}

	}

	private List<Containers> cons = null;
	private List<Contents> pages = null;

	@Override
	public void reloadContainer(TreeItem item) {
		// dragdata contains pageuri
		// data.getUri => container uri

		boolean reload = false;
		if (item == null) {
			reload = true;
			item = nav.getHolderContainer();
		}
		// clear selection and set selected item as the "item"
		nav.getTree().setSelectedItem(null, false);
		nav.getTree().setSelectedItem(item);
		// item.setState(false, false);

		// if (!item.getState()) { item.setState(false, false); } else {
		if ((item.getChildCount() == 1 && item.getChild(0).getText().equals("!")) || reload) {
			// do not reload every time it expands, reload every time when
			// "reload"
			item.removeItems();
			getData(item);
		} else {
			item.setState(true, false);
		}

	}

	private void getData(TreeItem item) {
		cons = null;
		pages = null;
		final TreeItem parent = item;
		final SiteTreeItemData pd = (SiteTreeItemData) parent.getUserObject();
		// parent.removeItems();

		ContainersDao.listsub(pd.getUri(), "/_/c", new ContainersResponse() {
			@Override
			public void array(List<Containers> value) {
				cons = value;
				dataReady(parent);
			}
		});
		WritingsDao.list(Ctrl.infoLang(), "", pd.getUri(), new ContentsResponse() {
			public void array(List<Contents> value) {
				pages = value;
				dataReady(parent);
			}
		});
	}

	protected void dataReady(TreeItem parent) {
		if (cons == null || pages == null)
			return;

		for (int i = 0; i < pages.size(); i++) {
			Contents s = pages.get(i);

			if (s.uri.equals("/"))
				continue;

			Containers con = isContainer(s.uri);
			if (con == null) {
				if (nav.listItems)
					nav.addLeaf(parent, s.uri, s.title[0], s.title[0], null, nav.isEditable);
			} else {
				addContainer(parent, con.mask, con.uri, con.uri_prefix, s.title[0], s.uri);
			}
		}

		pages = null;
		cons = null;

		parent.setState(true, false);

	}

	private void addContainer(TreeItem parent, Long mask, String uri, String uri_prefix, String text, String pageuri) {
		String conImg = "/_local/images/common/folder_page.png";

		// if (!CRoleMask.maskIsPublic(mask))
		// conImg = containerPrivateImage;

		if (mask.equals(Data.WRITING_PRIVATE_MASK))
			conImg = "/_local/images/common/folder_page_locked.png";

		SiteTreeItemData data = new SiteTreeItemData(text, uri, true, uri_prefix);
		data.setMask(mask);

		NavTreeItem node = new NavTreeItem(text, conImg, null, pageuri, false, nav.isEditable, nav.isEditable);
		node.setUserObject(data);

		node.addTextItem("!");
		node.setState(false);

		if (parent == null)
			nav.getTree().addItem(node);
		else
			parent.addItem(node);

		nav.deletable(node);
		nav.editable(node);

	}

	private Containers isContainer(String uri) {
		if (cons.size() == 0)
			return null;

		for (int i = 0; i < cons.size(); i++) {
			Containers con = cons.get(i);
			if (con.uri.equals(Data.WRITING_ROOT + uri)) {
				return con;
			}

		}
		return null;
	}

	@Override
	public void containerSelected() {
		buttonsStates(false);
	}

	@Override
	public void rootLevelSelected() {
		// btnDelete.setEnabled(false);
		// btnEdit.setEnabled(true);
	}

	@Override
	public void pageReady(String title, String uri, String lang) {
		openUriInLang(uri, lang, true);
	}

	// ----------------------NEW CONTAINER--------------------------------------
	@Override
	public void newContainer() {
	}

	@Override
	public void renameItem() {

		final TreeItem selected = nav.getSelectedItem();
		if (selected == null)
			return;

		final SiteTreeItemData data = (SiteTreeItemData) selected.getUserObject();
		final String uri = data.getUri();
		final String path = uri.substring(0, uri.lastIndexOf("/"));

		if (uri.equals("/_/writings"))
			return;

		final NewItemDlg dlg = new NewItemDlg(path, Ctrl.trans.rename(), selected.getText().trim(), "");
		dlg.addCloseHandler(new CloseHandler<PopupPanel>() {

			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				// clicked ok
				if (!dlg.isOk)
					return;

				final String title = dlg.getNamed();
				String fixedTitle = dlg.getFixedTitle();

				String newUri = null;

				if (title != null && !title.isEmpty() && fixedTitle != null && !fixedTitle.isEmpty()) {
					newUri = path + "/" + fixedTitle;
				}

				if (data.isContainer()) {
					String uriPrefix = (newUri + "/").replaceFirst(Data.WRITING_ROOT, "");

					String changeFrom = uri.replaceFirst(Data.WRITING_ROOT, "");
					final String changeTo = newUri.replaceFirst(Data.WRITING_ROOT, "");

					WritingsDao.containerreuri(changeTo, uriPrefix, changeFrom, new StringResponse() {
						@Override
						public void ready(String value) {
							if (value == null) {
								Window.alert("Unexpected situation");
								return;
							}

							ContentsDao.title(Ctrl.infoLang(), title, changeTo, new StringResponse() {
								public void ready(String value) {
									reloadContainer(selected.getParentItem());
								};
							});

						}

					});

				} else {

					final String lastUri = newUri;

					ResourcesDao.reuri(lastUri, uri, new BooleanResponse() {
						@Override
						public void ready(Boolean value) {

							ContentsDao.title(Ctrl.infoLang(), title, lastUri, new StringResponse() {
								public void ready(String value) {
									reloadContainer(selected.getParentItem());
								};
							});

						}
					});

				}

			}

		});

	}

	@Override
	public TreeItem getSelectedItem() {
		return nav.getSelectedItem();
	}

}
