package com.bilgidoku.rom.site.kamu.tutor.client;

import java.util.List;

import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ContactsDao;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Utils;
import com.bilgidoku.rom.site.kamu.tutor.client.constants.tutortrans;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class InstrumentTutor implements Selected {
	private static final int HEIGHT = 500;

	private static final int WIDTH = 500;

	public static tutortrans trans = (tutortrans) GWT.create(tutortrans.class);

	protected final String tutorName;

	public final Learning learn;

	final Label stat = new Label("");

	protected short which = 0;
	int count = 0;

	private boolean ignoreCount;

	protected final Option[] options;

	private final Instrument instrument;

	private final Img[] imgs;
	// private final Vector2d loc;
	private final Button[] sels;
	
	private final PtrStyle ptrStyle;
	private final LineStyle lineStyle;
	private final String lang;

	private final Leveler leveler;

	protected boolean needToSave = false;
	private JSONObject oldSaved;

	private String btnsParams;
	private final String codeParams;

	private String summaryParams;

	public InstrumentTutor(String lang, String name, Img[] imgs, Option[] opts, Button[] sels, Level[] levels,
			PtrStyle ptrStyle, LineStyle lineStyle, String btnsParams, String code2, String summaryParams) {
		this.options = opts;
		this.tutorName = "tutor" + name;
		this.leveler = new Leveler(opts.length, levels);
		this.learn = new Learning(options.length, leveler);
		this.btnsParams = btnsParams;
		this.summaryParams=summaryParams;

		// Sistem.outln("Scores:"+scores.length);

		Sistem.outln("optionCount" + opts.length);
		this.lang = lang;
		this.imgs = imgs;
		this.sels = sels;

		this.ptrStyle = ptrStyle;
		this.lineStyle = lineStyle;
		codeParams = code2;
		this.instrument = createInstrument();
		defaultValues();
	}

	public Instrument createInstrument() {
		return new Instrument(this, ptrStyle, lineStyle, tutorName, new Vector2d(WIDTH, HEIGHT), imgs, options, sels,
				btnsParams, codeParams, summaryParams);
	}

	protected void defaultValues() {
		setConfLevel((short) 0);
	}

	protected Widget getShowGui() {
		return instrument;
	}

	protected void renderQuestion(int which) {
		instrument.ask(which);
	}

	protected VerticalPanel renderWeKnow() {
		VerticalPanel weKnow = new VerticalPanel();
		for (Short integer : learn.getKnown()) {
			weKnow.add(new Label(options[integer].name));
		}
		return weKnow;
	}

	protected Panel renderWeAreWorking() {
		Sistem.outln("Render we are working");
		VerticalPanel workingOn = new VerticalPanel();
		for (Short integer : learn.getWorking()) {
			workingOn.add(new Label(options[integer].name));
		}
		return workingOn;
	}

	protected SetupPanel createSetupPanel() {
		return new SetupPanel(new SetupFeedback(), leveler);
	}

	protected void questionSelected() {
		// TODO Auto-generated method stub

	}

	protected Option[] selectOptions() {
		return instrument.getOptions();
	}

	protected String desc(Option options2) {
		return options2.name;
	}

	protected void renderOption(Option option) {
		instrument.render(option.index);
	}

	public void setConfLevel(int i) {
		leveler.setLevel(i);
	}

	public int getConfLevel() {
		return leveler.getLevel();
	}

	public void start() {
		String cid = RomEntryPoint.com().get("cid");
		
		ContactsDao.getnestingvalue(tutorName, cid, new StringResponse() {

			@Override
			public void ready(String value) {
				if (value == null)
					Sistem.outln("Nothing to load");
				else
					loadFromObj(ClientUtil.parseEncodedObject(value));
				learn.loadCompleted();

				ui();
			}

		});
	}

	protected void loadFromObj(JSONObject jo) {
		Sistem.outln("Loading");
		try {
			JSONArray arr = ClientUtil.optArray(jo, "score");
			if (arr == null)
				return;

			learn.loadScores(arr);

			Integer lvl = ClientUtil.optInteger(jo, "level");
			if (lvl != null) {
				setConfLevel((short)(int)lvl);
			}

			arr = ClientUtil.optArray(jo, "con");
			if (arr == null)
				return;
			learn.loadConsan(arr);

			// Sistem.outln("Object loaded!" + this.consantrateOn.toString());

		} catch (RunException e) {
			Sistem.printStackTrace(e);
		}
	}

	protected void save() {
		if (!needToSave) {
			Sistem.outln("No need to save");
			return;
		}
		needToSave = false;
		JSONObject jo = learn.saveObj(getConfLevel());
		if (this.oldSaved != null && jo.equals(oldSaved)) {
			Sistem.outln("Same no need to save");
			return;
		}
		String s = jo.toString();
		s = Utils.switchQuote(s);
		String cid = RomEntryPoint.com().get("cid");
		ContactsDao.setnestingvalue(tutorName, s, cid, new BooleanResponse() {
		});
	}

	private void showDlg(final Widget setupPanel) {
		final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
		simplePopup.setAnimationEnabled(true);
		simplePopup.setWidget(setupPanel);
		simplePopup.show();
	}

	private void ui() {

		
		Button setupBtn=new Button(trans.setup());
		setupBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				final SetupPanel setupPanel = createSetupPanel();
				showDlg(setupPanel);
			}
		});
		Button weKnowBtn=new Button(trans.weknow());
		weKnowBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				showDlg(renderWeKnow());
			}
		});
		Button weWorkingBtn=new Button(trans.weareworkingon());
		weWorkingBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				teachLearning();
			}
		});
		

		HorizontalPanel topPanel = new HorizontalPanel();
		topPanel.add(setupBtn);
		topPanel.add(weKnowBtn);
		topPanel.add(weWorkingBtn);
		topPanel.add(stat);

		VerticalPanel vp = new VerticalPanel();
		vp.add(topPanel);
		vp.add(getShowGui());

		RomEntryPoint.one.addToRootPanel(vp);

		// showTune.render("X:1\nT:\nM:\nD2");

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
//				ask();
				teachLearning();			}
		});
	}

	// {
	// return new Setup(){
	//
	// @Override
	// public int getOctaveCount() {
	// return octaveCount;
	// }
	//
	// @Overrideint
	// public void setOctaveCount(int i) {
	// octaveCount=i;
	// octaveCountToLimits();
	// needToSave=true;
	// save();
	// }
	//
	//
	//
	// }
	// return null;
	// setOctaveCount(2);
	// }

	protected void setStat(String text) {
		stat.setText(text);
	}

	protected void clearStat() {
		stat.setText("");
	}

	protected class SetupFeedback implements Setup {
		@Override
		public void factorySettings() {
			resetExtension();
			learn.factorySettings();
			shouldSave();
			setStat("Back to factory settings");
			ask();
		}

		@Override
		public void resetExtension() {
			// TODO Auto-generated method stub

		}

		@Override
		public int getLevel() {
			return getConfLevel();
		}

		@Override
		public void setLevel(int i) {
			setConfLevel(i);
		}

		@Override
		public void save() {
			shouldSave();
		}
	};

	private void teachLearning() {
		final List<Short> learnList = this.learn.circle.toList();
		if (learnList == null || learnList.size() == 0) {
			Sistem.outln("nothing to teach");
			ask();
			return;
		}

		final int i = 0;

		teachLearningIn(learnList, i);

	}

	private void teachLearningIn(final List<Short> learnList, final int i) {
		instrument.clean();
		renderOption(options[learnList.get(i)]);
		Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {

			@Override
			public boolean execute() {
				if ((i+1) < learnList.size()) {
					teachLearningIn(learnList, i+1);
				}else{
					ask();
				}
				return false;
			}
		}, 2000);
	}

	private void ask() {
		tick();
		this.ignoreCount = false;
		which = learn.getToAsk();
		questionSelected();

		// Window.alert(tr);

		renderQuestion(which);

		if (learn.weKnowAll()) {
			setStat("You know all of them. Making exercise...");
		} else {
			clearStat();
		}

	}

	public void shouldSave() {
		needToSave = true;
		save();
	}

	private void processFailed() {
		this.ignoreCount = true;
		learn.failed(which);
		setStat(desc(options[which]));
		renderOption(options[which]);
		Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
			@Override
			public boolean execute() {
				ask();

				return false;
			}
		}, 3000);
	}

	private void tick() {
		count++;
		Sistem.outln("tick");

		if (count % 5 == 0) {
			Sistem.outln("save");
			save();
		}

		if (ignoreCount)
			return;

		// if(count-askedOn>5){
		// processFailed();
		// }
	}

	@Override
	public void noteSelected(short i) {
		if (options[which].selectIndex == i) {
			final short optionIndex = which;
			setStat("YES! " + desc(options[which]));

			renderOption(options[which]);
			if (learn.aResponse(optionIndex))
				needToSave = true;

			Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
				@Override
				public boolean execute() {
					ask();
					return false;
				}
			}, 1000);
		} else {
			processFailed();
		}
	}

	@Override
	public void dontKnow() {
		processFailed();

	}
}
