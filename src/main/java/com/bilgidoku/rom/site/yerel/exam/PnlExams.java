package com.bilgidoku.rom.site.yerel.exam;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Contents;
import com.bilgidoku.rom.gwt.araci.client.site.ContentsDao;
import com.bilgidoku.rom.gwt.araci.client.site.ContentsResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Exams;
import com.bilgidoku.rom.gwt.araci.client.site.ExamsDao;
import com.bilgidoku.rom.gwt.araci.client.site.ExamsResponse;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.AskForValueDlg;
import com.bilgidoku.rom.gwt.client.util.common.SiteArrayBox;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PnlExams extends Composite {

	VerticalPanel results = new VerticalPanel();
	private List<Contents> exams;

	public PnlExams() {
		HorizontalPanel toolbar = new HorizontalPanel();
		SiteButton btnNew = new SiteButton("New Exam", "New Exam");
		btnNew.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final AskForValueDlg dlg = new AskForValueDlg("Exam Name", "OK");
				dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						if (dlg.getValue().isEmpty())
							return;
						ExamsDao.neww(Ctrl.infoLang(), dlg.getValue(), "/_/exams", new StringResponse() {
							@Override
							public void ready(String value) {
								populate();
							}
						});
					}
				});
			}
		});

		toolbar.add(btnNew);

		VerticalPanel vp = new VerticalPanel();
		vp.add(toolbar);
		vp.add(results);
		initWidget(vp);

		populate();

	}

	private void populate() {
		results.clear();
		addHeaderLine();
		ExamsDao.list(Ctrl.infoLang(), "/_/exams", new ContentsResponse() {
			@Override
			public void array(List<Contents> myarr) {
				exams = myarr;
				for (int i = 0; i < myarr.size(); i++) {
					Contents contents = myarr.get(i);
					results.add(new ExamLine(contents.uri, i));
				}
			}
		});

	}

	private void addHeaderLine() {
		Label title = new Label("Title");
		title.setWidth("197px");

		Label duartion = new Label("Secs");
		duartion.setWidth("26px");

		Label elimination = new Label("Elim.");
		elimination.setWidth("223px");

		Label count = new Label("Q Count");
		count.setWidth("42px");

		Label pre = new Label("Prereq.");
		pre.setWidth("42px");

		Label que = new Label("");

		HorizontalPanel hp = new HorizontalPanel();
		hp.setStyleName("header cell-header");
		hp.add(title);
		hp.add(duartion);
		hp.add(elimination);
		hp.add(pre);
		hp.add(que);
		hp.add(count);
		results.add(hp);

	}

	private class DlgQuestions extends ActionBarDlg {
		protected String[] questions = null;
		private PnlQuestions pnl = new PnlQuestions();

		public DlgQuestions() {
			super("Questions", null, "OK");
			run();
		}

		public void load(String examUri) {
			ExamsDao.get(Ctrl.infoLang(), examUri, new ExamsResponse() {
				public void ready(Exams value) {
					pnl.loadAndMarkQuestions(value);
				};
			});

		}

		@Override
		public Widget ui() {
			return pnl;
		}

		@Override
		public void cancel() {
			questions = null;
		}

		@Override
		public void ok() {
			questions = pnl.getSelectedQuestions();

		}
	}

	private class ExamLine extends Composite {

		SiteButton setQuestions = new SiteButton("Question List", "");
		SiteButton del = new SiteButton("del", "del");
		SiteButton save = new SiteButton("save", "change");
		SiteArrayBox reqs = new SiteArrayBox();
		final HTML count = new HTML();
		final TextBox tbDuration = new TextBox();
		final TextBox tbElimination = new TextBox();
		final TextBox tbTitle = new TextBox();
		private String examUri;

		public ExamLine(final String eUri, int i) {
			this.examUri = eUri;
			forSave();
			forDel();
			forSetQuestions();

			FocusHandler focusHandler = new FocusHandler() {
				@Override
				public void onFocus(FocusEvent event) {
					selectThisLine(ExamLine.this);
				}
			};
			tbTitle.addFocusHandler(focusHandler);
			tbDuration.addFocusHandler(focusHandler);
			tbElimination.addFocusHandler(focusHandler);

			tbTitle.setWidth("200px");
			count.addStyleName("cell-number");
			count.setWidth("30px");

			tbDuration.addStyleName("cell-number");
			tbDuration.setWidth("30px");

			tbElimination.addStyleName("cell-number");
			tbElimination.setWidth("30px");

			setQuestions.setWidth("110px");

			HorizontalPanel hp = new HorizontalPanel();
			hp.setSpacing(2);
			hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			hp.add(new HTML("*"));
			hp.add(tbTitle);
			hp.add(new HTML("*"));
			hp.add(tbDuration);
			hp.add(tbElimination);
			hp.add(save);
			hp.add(del);
			hp.add(setQuestions);
			hp.add(count);

			if (i % 2 == 0)
				hp.addStyleName("row-odd");
			else
				hp.addStyleName("row-even");

			initWidget(hp);

			load(eUri);

		}

		private void load(String examUri) {
			ExamsDao.get(Ctrl.infoLang(), examUri, new ExamsResponse() {
				public void ready(Exams value) {
					tbTitle.setValue(value.title[0]);
					count.setHTML(value.questions == null ? "0" : value.questions.length + "");
					if(value.elimination!=null)
						tbElimination.setValue(value.elimination + "");
					if(value.duration!=null)
						tbDuration.setValue(value.duration + "");
					List<String[]> arr = new ArrayList<String[]>();
					for (int i = 0; i < exams.size(); i++) {
						arr.add(new String[] { exams.get(i).uri, exams.get(i).title[0] });
					}
					reqs.load(value.requirements, arr);
				};
			});
		}

		private void forSetQuestions() {
			setQuestions.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					final DlgQuestions dlg = new DlgQuestions();
					dlg.load(examUri);
					dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
						@Override
						public void onClose(CloseEvent<PopupPanel> event) {
							if (dlg.questions == null)
								return;

							ExamsDao.setquestions(dlg.questions, examUri, new BooleanResponse() {
								public void ready(Boolean value) {
									count.setHTML(dlg.questions.length + " ");
								};
							});
						}
					});
					dlg.show();
					dlg.center();

				}
			});

		}

		private void forDel() {
			del.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (!Window.confirm("Are you sure")) {
						return;
					}
					ExamsDao.destroy(examUri, new StringResponse() {
						@Override
						public void ready(String value) {
							populate();
						}
					});
				}
			});

		}

		public void forSave() {
			save.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					ContentsDao.title(Ctrl.infoLang(), tbTitle.getValue(), examUri, new StringResponse());
					ExamsDao.setrequirements(reqs.getValueArr(), examUri, new BooleanResponse());

					Integer duration = tbDuration.getValue() == null ? null : Integer.parseInt(tbDuration.getValue());
					ExamsDao.setduration(duration, examUri, new BooleanResponse());

//					Integer elimination = tbElimination.getValue() == null ? null : Integer.parseInt(tbElimination.getValue());
//					ExamsDao.se


				}
			});

		}

	}

	protected void selectThisLine(ExamLine examLine) {
		for (int i = 0; i < results.getWidgetCount(); i++) {
			Widget widget2 = results.getWidget(i);
			widget2.removeStyleName("selectedRow");
		}
		examLine.addStyleName("selectedRow");

	}

}
