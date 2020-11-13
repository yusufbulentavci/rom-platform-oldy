package com.bilgidoku.rom.site.yerel.issues;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.ArrayResponse;
import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsDao;
import com.bilgidoku.rom.gwt.client.util.common.AskForValueDlg;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.HasContainer;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NavTasks extends Composite implements HasContainer {

	String uri = Cookies.getCookie("cid");
	VerticalPanel lines = new VerticalPanel();
	List<Task> tasks;
	public SiteToolbarButton btnNew = new SiteToolbarButton("/_local/images/common/add.png", "", Ctrl.trans.newItem(),
			"");
	private final Image checkImg = new Image("/_public/images/bar/check.png");
	final ToggleButton btnPostponed = new ToggleButton(new Image("/_public/images/bar/go_right.png"), new Image("/_public/images/bar/go_right.png"));
	final ToggleButton btnCompleted = new ToggleButton(checkImg, checkImg);

	public NavTasks() {

		initWidget(lines);
		btnPostponed.setTitle("Show/hide postponed");
		btnCompleted.setTitle("Show/hide completed");
		fornew();
	}

	private void fornew() {
		btnNew.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final AskForValueDlg dlg = new AskForValueDlg("New task", "", 3, 3, "OK");
				dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						String title = dlg.getValue();
						if (title != null && !title.isEmpty()) {
							if (tasks == null)
								tasks = new ArrayList<Task>();

							Task t = new Task(title);
							tasks.add(t);
							saveAllTasks();
							ui();
						}

					}
				});

			}
		});

		btnPostponed.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ui();
			}
		});
		btnCompleted.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ui();
			}
		});
	}

	private boolean busy = false;

	@Override
	public void addContainers() {
		if (busy)
			return;

		busy = true;
		tasks = null;
		ContactsDao.getworks(uri, new ArrayResponse<Json>() {
			@Override
			public void ready(Json[] myarr) {
				if (myarr != null) {
					for (int i = 0; i < myarr.length; i++) {
						Task t = new Task(myarr[i]);
						if (t.end == null) {
							if (tasks == null)
								tasks = new ArrayList<Task>();
							tasks.add(t);
						}
					}
				}
				ui();
				busy = false;
			}

		});
	}

	void ui() {

		lines.clear();
		lines.setSpacing(3);
		lines.setWidth("100%");
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(btnNew);
		hp.add(btnPostponed);
		hp.add(btnCompleted);
		lines.add(hp);

		if (tasks == null)
			return;

		// First of all add importants
		addRows(true);
		addRows(false);
	}

	private void addRows(boolean isImportant) {
		Boolean showPostponed = btnPostponed.getValue();
		Boolean showCompleted = btnCompleted.getValue();

		int added = 0;
		for (int i = 0; i < tasks.size(); i++) {
			final Task task = tasks.get(i);

			if (isImportant && !task.isImportant) {
				// Show only importants and if task is not important
				continue;
			} else if (!isImportant && task.isImportant) {
				// Show only unimportants and if task is important
				continue;
			}

			if (!showCompleted && task.end != null) {
				// Dont show completed and task is completed
				continue;
			}

			if (!showPostponed && task.isPostponed) {
				// Dont show postponed and task is postponed
				continue;
			}

			addTask(task, added);

			added++;

		}
	}

	private void addTask(final Task task, int added) {
		final SimplePanel line = new SimplePanel();
		line.setStyleName((added % 2 == 0) ? "row-odd" : "row-even");
		line.setWidth("100%");

		final HTML htmlTitle = new HTML(task.title);

		CheckBox cb = new CheckBox();
		cb.setTitle(Ctrl.trans.finish());
		cb.setValue(task.isCompleted());
		cb.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (task.end == null) {
					task.end = new Date();
					saveAllTasks();
					// line.removeFromParent();
					ui();
				} else {
					task.end = null;
					saveAllTasks();
					ui();
				}
			}
		});

		SiteToolbarButton btnDelete = new SiteToolbarButton("/_local/images/common/bin.png", "", "", "");
		btnDelete.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				tasks.remove(task);
				saveAllTasks();
				ui();
			}
		});

		final ToggleButton pp = new ToggleButton(new Image("/_public/images/bar/go_right.png"), new Image("/_public/images/bar/go_right.png"));
		pp.setValue(task.isPostponed);
		pp.setTitle("Postpone task");
		pp.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				task.setPostponed(pp.getValue());
				saveAllTasks();
				ui();
			}
		});

		final ToggleButton imp = new ToggleButton(new Image("/_public/images/bar/exclamation.png"), new Image("/_public/images/bar/exclamation.png"));
		imp.setValue(task.isImportant);
		imp.setTitle("Important");
		imp.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				task.setImportant(imp.getValue());
				saveAllTasks();
				ui();
			}
		});

		SiteToolbarButton btnEdit = new SiteToolbarButton("/_local/images/common/pencil.png", "", "", "");
		btnEdit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final AskForValueDlg dlg = new AskForValueDlg("Edit task", task.title, 3, 3, "OK");
				dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						String title = dlg.getValue();
						if (title != null && !title.isEmpty()) {
							task.title = title;
							htmlTitle.setHTML(title);
							saveAllTasks();
						}

					}
				});

			}
		});

		VerticalPanel vp = new VerticalPanel();
		vp.add(cb);
		vp.add(imp);
		vp.add(pp);
		vp.add(btnEdit);
		vp.add(btnDelete);

		HorizontalPanel hp = new HorizontalPanel();
		// hp.setSpacing(3);
		// hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		hp.add(vp);
		hp.add(htmlTitle);

		line.add(hp);
		lines.add(line);
	}

	protected void saveAllTasks() {
		if (tasks == null)
			return;

		ContactsDao.setworks(getTasks(), uri, new BooleanResponse() {
			public void ready(Boolean value) {
				Ctrl.setStatus("kaydedildi");
			};
		});

	}

	private Json[] getTasks() {
		Json[] arr = new Json[tasks.size()];
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			arr[i] = task.getJson();
		}
		return arr;
	}
}
