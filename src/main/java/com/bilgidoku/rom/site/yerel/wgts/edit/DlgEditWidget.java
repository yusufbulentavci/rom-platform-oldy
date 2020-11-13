package com.bilgidoku.rom.site.yerel.wgts.edit;

import java.util.HashMap;
import java.util.Map;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.shared.code.Wgt;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

public class DlgEditWidget extends DialogBox {
	private final NavWidgets tab;

	final TextBox txtParamName = new TextBox();
	final ListBox listParamType = new ListBox();
	final TextBox txtParamEnum = new TextBox();
	final CheckBox chkAttRequired = new CheckBox();
	final CheckBox chkRunzone = new CheckBox();
	final CheckBox chkAttDeclare = new CheckBox();

	final Button btnSave = new Button("Save");
	final Button btnCancel = new Button("Cancel");
	final Button btnAdd = new Button("Add/Change");
	final Button btnDelete = new Button(Ctrl.trans.delete());
	final TextBox txtName = new TextBox();
	final TextBox txtParamDefault = new TextBox();
	final TextBox txtDefaultWidth = new TextBox();
	final TextBox txtDefaultHeight = new TextBox();
	

	final ListBox listParams = new ListBox();
	final ListBox listContext = new ListBox();
	// final ListBox widGrp = new ListBox(false);
	final TextBox widGrp = new TextBox();
	final FlexTable f = new FlexTable();
	private String orgName;
	// private String orgGrp;
	private Map<String, Att> paramDefs;

	public DlgEditWidget(NavWidgets tab, Wgt wdgt, Code code) {

		this.tab = tab;

		if (wdgt != null) {
			txtName.setValue(wdgt.getNamed());
			// loadWidgetGrps(wdgt.getGroup());
			chkRunzone.setValue(wdgt.ownZone);
			widGrp.setValue(wdgt.getGroup());
			paramDefs = wdgt.getParamDefs();
			loadParams(wdgt.getParams());
			orgName = wdgt.getNamed();
			txtDefaultHeight.setValue(wdgt.getDefHeight() == null ? null : wdgt.getDefHeight() + "");
			txtDefaultWidth.setValue(wdgt.getDefWidth() == null ? null : wdgt.getDefWidth() + "");
			// orgGrp = wdgt.getGroup();
		} else {
			paramDefs = new HashMap<String, Att>();
			// loadWidgetGrps(null);
			widGrp.setValue("");
		}

		loadTypes();
		loadContext();

		forSelectAtt();
		forSave(wdgt, code);
		forCancel();
		forDeleteParam();
		forAddParam();

		ui();

		btnCancel.setStyleName("site-closebutton");
		this.setWidget(f);
		this.setGlassEnabled(true);
		this.setAutoHideEnabled(true);
		this.setText("Save As Widget");
		this.center();

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				if (listParams.getItemCount() > 0)
					loadAttribute(listParams.getValue(0));

			}
		});

	}

	private void forSelectAtt() {
		listParams.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				ListBox tb = (ListBox) event.getSource();
				int ind = tb.getSelectedIndex();
				if (ind < 0)
					return;
				loadAttribute(tb.getItemText(ind));

			}
		});

	}

	protected void loadAttribute(String def) {

		Att attr = paramDefs.get(def);
		txtParamName.setValue(attr.getNamed());
		txtParamEnum.setValue(getArrToStr(attr.getEnumeration()));
		txtParamDefault.setValue(attr.getDefvalue());
		setParamType(attr.getType());
		setContext(attr.getContext());
		chkAttDeclare.setValue(attr.isDeclare());
		chkAttRequired.setValue(attr.isReq());

	}

	private String getArrToStr(String[] enumeration) {
		if (enumeration == null || enumeration.length <= 0)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < enumeration.length; i++) {
			sb.append("," + enumeration[i]);
		}
		return sb.toString().substring(1);
	}

	private void forAddParam() {
		btnAdd.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// add to list
				String name = txtParamName.getValue();
				String attEnum = txtParamEnum.getValue();
				String def = txtParamDefault.getValue();
				if(def==null || def.isEmpty())
					def=null;
				int type = getTypeInt();
				int context = getContextInt();
				boolean req = chkAttRequired.getValue();
				boolean dec = chkAttDeclare.getValue();
				String[] arrEnum = (attEnum != null && !attEnum.isEmpty() ? attEnum.split(",") : null);

				paramDefs.put(name, new AttImpl(name, type, arrEnum, req, dec, def, context));

				if (ClientUtil.findIndex(listParams, name) < 0) {
					listParams.addItem(name);
				}

			}

		});

	}

	protected int getTypeInt() {
		String type = listParamType.getValue(listParamType.getSelectedIndex());
		int i = Integer.parseInt(type);
		return i;
	}

	protected int getContextInt() {
		String con = listContext.getValue(listContext.getSelectedIndex());
		int i = Integer.parseInt(con);
		return i;
	}

	private void loadTypes() {
		listParamType.addItem("int", Att.INT + "");
		listParamType.addItem("string", Att.STRING + "");
		listParamType.addItem("double", Att.DOUBLE + "");
		listParamType.addItem("boolean", Att.BOOL + "");
		listParamType.addItem("array", Att.ARRAY + "");
		listParamType.addItem("object", Att.OBJECT + "");
	}

	private void loadContext() {
		listContext.addItem("no context", Att.CONTEXT_NULL + "");
		listContext.addItem("container", Att.CONTEXT_CONTAINER + "");
//		listContext.addItem("page", Att.CONTEXT_PAGE + "");
		listContext.addItem("list", Att.CONTEXT_LIST + "");
		listContext.addItem("array", Att.CONTEXT_ARRAY + "");
		listContext.addItem("image", Att.CONTEXT_IMG + "");
		listContext.addItem("file", Att.CONTEXT_FILE + "");
		listContext.addItem("link", Att.CONTEXT_LINK + "");
		listContext.addItem("tag", Att.CONTEXT_TAG + "");
		listContext.addItem("---------", "");
		listContext.addItem("int", Att.CONTEXT_INT + "");
		listContext.addItem("boolean", Att.CONTEXT_BOOLEAN + "");
		listContext.addItem("rect", Att.CONTEXT_RECT + "");
		listContext.addItem("width", Att.CONTEXT_WIDTH + "");
//		listContext.addItem("height", Att.CONTEXT_HEIGHT + "");
		listContext.addItem("height", 17 + "");
		listContext.addItem("time", Att.CONTEXT_TICK + "");
		listContext.addItem("---------", "");
		listContext.addItem("font", Att.CONTEXT_FONT + "");
		listContext.addItem("htmltext", Att.CONTEXT_HTMLTEXT + "");
		listContext.addItem("textarea", Att.CONTEXT_TEXTAREA + "");
		listContext.addItem("youtube", Att.CONTEXT_YOUTUBE + "");
		// listContext.addItem("translation", Att.CONTEXT_TRANS + "");
	}

	private void setParamType(int type) {

		for (int i = 0; i < listParamType.getItemCount(); i++) {
			if ((type + "").equals(listParamType.getValue(i))) {
				listParamType.setSelectedIndex(i);
				break;
			}
		}
	}

	private void setContext(int con) {

		for (int i = 0; i < listContext.getItemCount(); i++) {

			if ((con + "").equals(listContext.getValue(i))) {
				listContext.setSelectedIndex(i);
				break;
			}
		}
	}

	private void ui() {
		listParams.setVisibleItemCount(12);
		listParams.setWidth("140px");
		FlexTable fatt = new FlexTable();
		fatt.setWidget(0, 0, listParams);
		fatt.setHTML(1, 1, "Name");
		fatt.setWidget(1, 2, txtParamName);
		fatt.setHTML(2, 1, "Type:");
		fatt.setWidget(2, 2, listParamType);
		fatt.setHTML(3, 1, "Enum:");
		fatt.setWidget(3, 2, txtParamEnum);
		
		fatt.setHTML(4, 1, "Required:");
		fatt.setWidget(4, 2, chkAttRequired);
		
		fatt.setHTML(5, 1, "Inner Use:");
		fatt.setWidget(5, 2, chkAttDeclare);

		fatt.setHTML(6, 1, "Default:");
		fatt.setWidget(6, 2, txtParamDefault);

		fatt.setHTML(7, 1, "Context:");
		fatt.setWidget(7, 2, listContext);

		fatt.setWidget(8, 1, btnAdd);
		fatt.setWidget(8, 2, btnDelete);

		fatt.getFlexCellFormatter().setRowSpan(0, 0, 9);

		CaptionPanel catt = new CaptionPanel("Parameters");
		catt.add(fatt);

		FlexCellFormatter ff = f.getFlexCellFormatter();
		f.setHTML(0, 0, "Widget Group:");
		f.setWidget(0, 1, widGrp);
		f.setHTML(1, 0, "Widget Name:");
		f.setWidget(1, 1, txtName);
		f.setHTML(2, 0, "Runzone:");
		f.setWidget(2, 1, chkRunzone);
		
		f.setHTML(3, 0, "Default width:");
		f.setWidget(3, 1, this.txtDefaultWidth);
		
		f.setHTML(4, 0, "Default height:");
		f.setWidget(4, 1, this.txtDefaultHeight);
		
		f.setWidget(5, 0, catt);
		f.setWidget(6, 0, btnSave);
		f.setWidget(6, 1, btnCancel);
		ff.setColSpan(5, 0, 2);
	}

	private void loadParams(Att[] atts) {
		for (int i = 0; i < atts.length; i++) {
			listParams.addItem(atts[i].getNamed());
		}
	}

	// private void loadWidgetGrps(String widgetGroup) {
	// String[] listTypes = Data.WIDGET_GROUPS;
	// for (int i = 0; i < listTypes.length; i++) {
	// widGrp.addItem(listTypes[i]);
	// if (widgetGroup != null && widgetGroup.equals(listTypes[i])) {
	// widGrp.setSelectedIndex(i);
	// }
	// }
	// }

	private void forDeleteParam() {
		btnDelete.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int i = listParams.getSelectedIndex();
				if (i < 0) {
					Window.alert("Select parameter definition to delete");
					return;
				}
				listParams.removeItem(i);
				String named = txtParamName.getValue();
				paramDefs.remove(named);

				emptyForm();
			}

			private void emptyForm() {
				txtParamEnum.setValue(null);
				txtParamName.setValue(null);
				txtParamDefault.setValue(null);
				txtDefaultWidth.setValue(null);
				txtDefaultHeight.setValue(null);
				chkRunzone.setValue(false);
				listParamType.setSelectedIndex(0);
				listContext.setSelectedIndex(0);
				chkAttRequired.setValue(false);
				chkAttDeclare.setValue(false);
			}
		});
	}

	private void forCancel() {
		btnCancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				DlgEditWidget.this.hide(true);
			}
		});
	}

	private void forSave(final Wgt widget, final Code code) {
		btnSave.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (txtName.getValue().isEmpty()) {
					Window.alert(Ctrl.trans.someValuesEmpty());
					return;
				}
				try {

					String wName = txtName.getValue().startsWith("w:") ? txtName.getValue() : "w:" + txtName.getValue();
					String wGrp = widGrp.getValue();

					boolean runzone = chkRunzone.getValue();
					String dw = txtDefaultWidth.getValue();
					Integer defWidth=(dw==null || dw.length()==0)?null:Integer.parseInt(dw);

					String dh = txtDefaultHeight.getValue();
					Integer defHeight=(dh==null || dh.length()==0)?null:Integer.parseInt(dh);
					
					if (code != null) {// new widget
						tab.newWidget(wName, wGrp, code, runzone, paramDefs, defWidth, defHeight);

					} else { // edit widget

						if (!orgName.equals(wName)) {
							Window.alert("Widget name can not be changed");
							return;
						}

						tab.changeProperties(wName, wGrp, paramDefs, runzone, defWidth, defHeight);

					}
					hide();

				} catch (RunException e) {
					Window.alert(e.getMessage());
					Sistem.printStackTrace(e);
				}
			}
		});
	}

}