package com.bilgidoku.rom.site.yerel.boxing;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.shared.code.Wgt;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.boxer;
import com.bilgidoku.rom.site.yerel.wgts.edit.code.NodePnlParam;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Widget;

public class DlgWidgetEdit extends ActionBarDlg {
	private CodeEditCb cb;

	final NodePnlParam paramPanel;

	private JSONObject translations;

	public DlgWidgetEdit(JSONObject translations1, String title) {
		super(title, null, Ctrl.trans.ok());
		this.translations = translations1;
		
		paramPanel = new NodePnlParam(translations);

		run();

	}

	public void update(Code code, boolean fromToolbox, CodeEditCb cb) throws RunException {

		this.cb = cb;

		boolean showParams = existsParams(code, code.tag);
		if (fromToolbox) {
			if (!showParams) {
				cb.codeState(true);
				DlgWidgetEdit.this.hide();
				return;
			}
		}

		if (showParams) {
			paramPanel.update(code.tag, code);
			paramPanel.showData();
		}

	}

	private boolean existsParams(Code code, String tag) throws RunException {
		if (code.getParamDefs() == null)
			return false;

		if (code.getParamDefs().size() <= 0)
			return false;

		Wgt widget2 = boxer.allCodeRepo.getWidget(tag);
		Map<String, Att> params = widget2.getParamDefs();

		boolean isAllParamsInnerUse = true;
		Set<String> keySet = params.keySet();
		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Att att = params.get(key);
			if (att.isDeclare())
				continue;
			else {
				isAllParamsInnerUse = false;
				break;
			}

		}

		return !isAllParamsInnerUse;

	}

	@Override
	public Widget ui() {
		return paramPanel;
	}

	@Override
	public void ok() {
		cb.codeState(true);

	}

	@Override
	public void cancel() {
//		widgetSelected(false);
		cb.codeState(false);
	}

}
