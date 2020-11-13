package com.bilgidoku.rom.site.yerel.exam;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.gwt.araci.client.site.ContentsDao;
import com.bilgidoku.rom.gwt.araci.client.site.Questions;
import com.bilgidoku.rom.gwt.araci.client.site.QuestionsDao;
import com.bilgidoku.rom.gwt.araci.client.site.QuestionsResponse;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.util.common.SiteTagBox;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;

public class PnlQuestion extends Composite {
	
	//option count 5
	//option indexes 
	//A => 0
	//B => 1
	//C => 2
	//D => 3
	//E => 4
	//correct option => is one of option indexes like 1 for B
	
	
	SiteTagBox tbTags = new SiteTagBox();
	
	RadioButton rbA = new RadioButton("answer");
	RadioButton rbB = new RadioButton("answer");
	RadioButton rbC = new RadioButton("answer");
	RadioButton rbD = new RadioButton("answer");
	RadioButton rbE = new RadioButton("answer");
	
	final RadioButton[] rbs = { rbA, rbB, rbC, rbD, rbE };

	TextArea tbText = new TextArea();
	
	TextArea tbA = new TextArea();
	TextArea tbB = new TextArea();
	TextArea tbC = new TextArea();
	TextArea tbD = new TextArea();
	TextArea tbE = new TextArea();

	TextArea[] tbs = { tbA, tbB, tbC, tbD, tbE };
	ListBox lb = new ListBox();
	
	public PnlQuestion(final String qUri, final Runnable run) {
		for (int i = 0; i < tbs.length; i++) {
			tbs[i].setSize("150px", "40px");
		}


		for (int i = 1; i < 6; i++) {
			lb.addItem(i + "");
		}

		lb.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				rbsView();
			}
		});
		
		SiteButton save= new SiteButton("Save", "Save");

		FlexTable ft = new FlexTable();
		
		ft.setHTML(0, 1, "Question");
		ft.setWidget(0, 2, tbText);
		
		ft.setHTML(1, 1, "Option Count");
		ft.setWidget(1, 2, lb);
		
		ft.setHTML(2, 0, "A");
		ft.setWidget(2, 1, rbA);
		ft.setWidget(2, 2, tbA);

		ft.setHTML(3, 0, "B");
		ft.setWidget(3, 1, rbB);
		ft.setWidget(3, 2, tbB);

		ft.setHTML(4, 0, "C");
		ft.setWidget(4, 1, rbC);
		ft.setWidget(4, 2, tbC);

		ft.setHTML(5, 0, "D");
		ft.setWidget(5, 1, rbD);
		ft.setWidget(5, 2, tbD);

		ft.setHTML(6, 0, "E");
		ft.setWidget(6, 1, rbE);
		ft.setWidget(6, 2, tbE);

		ft.setHTML(7, 1, "Tags");
		ft.setWidget(7, 2, tbTags);

		ft.setWidget(8, 2, save);
	
		save.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				
				ContentsDao.title(Ctrl.infoLang(), tbText.getValue(), qUri, new StringResponse());				

				int answer = -1;
				for (int i = 0; i < rbs.length; i++) {
					if (rbs[i].getValue()) {
						answer = i;
						break;
					}
				}

				QuestionsDao.change(Ctrl.infoLang(), tbA.getValue(), tbB.getValue(), tbC.getValue(), tbD.getValue(), tbE.getValue(),
						Integer.parseInt(lb.getSelectedValue())-1, answer, qUri, new StringResponse() {
							@Override
							public void ready(String value) {
								run.run();								
							}
						});
				
				if (tbTags.getValue() != null)
					ResourcesDao.setrtag(tbTags.getValue(), qUri, new StringResponse());
						
				
			}
		});	
		
		initWidget(ft);
		
		//load
		QuestionsDao.get(Ctrl.infoLang(), qUri, new QuestionsResponse() {
			@Override
			public void ready(Questions value) {
				tbText.setValue(value.title[0]);
				tbA.setValue(value.option_a[0]);
				tbB.setValue(value.option_b[0]);
				tbC.setValue(value.option_c[0]);
				tbD.setValue(value.option_d[0]);
				tbE.setValue(value.option_e[0]);
				
				tbTags.setValue(value.rtags);
				
				int option_count = value.option_count;
				if (option_count > 0)
					lb.setSelectedIndex(option_count -1);
				
				rbsView();

				rbs[value.correct_option].setValue(true);
				
			}
		});

	}
	
	protected void rbsView() {
		// disable All
		for (int i = 0; i < tbs.length; i++) {
			tbs[i].addStyleName("grey");
			tbs[i].setEnabled(false);			
		}

		for (int i = 0; i < lb.getSelectedIndex() + 1; i++) {
			tbs[i].removeStyleName("grey");
			tbs[i].setEnabled(true);
		}

	}

	public void loadData() {
		
	}

}
