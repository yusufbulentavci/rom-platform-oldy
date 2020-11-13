package com.bilgidoku.rom.site.yerel.exam;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Contents;
import com.bilgidoku.rom.gwt.araci.client.site.ContentsResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Exams;
import com.bilgidoku.rom.gwt.araci.client.site.ExamsDao;
import com.bilgidoku.rom.gwt.araci.client.site.ExamsResponse;
import com.bilgidoku.rom.gwt.araci.client.site.QuestionsDao;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.SiteTagBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PnlQuestions extends Composite {

//	List<String> selected = new ArrayList<String>();
	SiteTagBox tb = new SiteTagBox();
	VerticalPanel holder = new VerticalPanel();
	VerticalPanel result = new VerticalPanel();
	DlgNewQuestion dlg = new DlgNewQuestion();

	private Exams exam = null;
	
	public PnlQuestions() {
		
		dlg.addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				if (dlg.question == null)
					return;

				QuestionsDao.neww("en", dlg.question, "/_/questions", new StringResponse() {
					@Override
					public void ready(String value) {
						populate(null);
					}
				});

			}
		});

		Button btnList = new Button("List");
		btnList.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				populate(tb.getValue());
			}
		});

		Button btnNewQuestion = new Button("New Question");
		btnNewQuestion.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dlg.show();
				dlg.center();
			}
		});

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(btnNewQuestion);
		hp.add(tb);
		hp.add(btnList);

		holder.add(hp);
		holder.add(result);

		initWidget(holder);

		
	}
	
	public void loadAndMarkQuestions(Exams e) {		
		this.exam = e;
		
		populate(null);
		
		
	}


	private void markQuestions(Exams exam2) {
		if (exam2 == null || exam2.questions == null)
			return;
		
		String[] questions = exam2.questions;
		for (int i = 0; i < result.getWidgetCount(); i++) {
			if (result.getWidget(i) instanceof QuestionLine) {
				((QuestionLine) result.getWidget(i)).checkIfOneOf(questions);						
			}
		}

		
	}

	protected void populate(String[] tags) {
		result.clear();
		QuestionsDao.list("en", tags, "/_/questions", new ContentsResponse() {
			@Override
			public void array(List<Contents> myarr) {
				for (int i = 0; i < myarr.size(); i++) {
					Contents q = myarr.get(i);
					result.add(new QuestionLine(q, i));
				}
				
				markQuestions(exam);

			}
		});
	}


	public String[] getSelectedQuestions() {
		List<String> selected = new ArrayList<String>();
		for (int i = 0; i < result.getWidgetCount(); i++) {
			if (result.getWidget(i) instanceof QuestionLine) {
				QuestionLine q = (QuestionLine) result.getWidget(i);						
				if (q.isChecked())
					selected.add(q.getUri());
			}
		}

		
		return selected.toArray(new String[0]);
	}

	private class DlgQuestion extends ActionBarDlg {
		private final String qUri;

		public DlgQuestion(String uri) {
			super("Edit Question", null, null);
			this.qUri = uri;
			run();
			this.show();
			this.center();

		}

		@Override
		public Widget ui() {
			return new PnlQuestion(qUri, new Runnable() {				
				@Override
				public void run() {					
					DlgQuestion.this.hide();					
				}
			});
		}

		@Override
		public void cancel() {

		}

		@Override
		public void ok() {

		}

	}

	private class QuestionLine extends Composite {
		
		private final CheckBox rb = new CheckBox();
		private final String myUri;
		public QuestionLine(final Contents q, int i) {
			myUri = q.uri;
			
			HorizontalPanel hp = new HorizontalPanel();			
			hp.add(rb);
			Anchor a = new Anchor(q.title[0]);
			a.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					new DlgQuestion(q.uri);
				}
			});
			hp.add(a);
			if (i % 2 == 0)
				hp.addStyleName("row-odd");
			else
				hp.addStyleName("row-even");
			
			initWidget(hp);
		}
		
		public String getUri() {
			return myUri;
		}

		public void checkIfOneOf(String[] questions) {
			if (questions == null)
				return;
			for (int i = 0; i < questions.length; i++) {
				String uri = questions[i];
				if (myUri.equals(uri)) {
					rb.setValue(true);
					break;
				}
			}
			
		}

		public boolean isChecked() {
			return rb.getValue();
		}
	
		
	}

}
