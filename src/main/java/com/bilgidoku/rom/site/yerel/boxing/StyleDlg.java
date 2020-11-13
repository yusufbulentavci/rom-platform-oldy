package com.bilgidoku.rom.site.yerel.boxing;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.wgts.edit.code.NodePnlStyle;
import com.google.gwt.user.client.ui.Widget;

public class StyleDlg extends ActionBarDlg {
	private BoxHolder boxHolder;
	// private String boxId;
	private NodePnlStyle pnl = new NodePnlStyle();
	private Code styleCode;
	private CodeEditCb codeEditCb;

	public StyleDlg(String id, BoxHolder hld, Code code) {
		super(Ctrl.trans.style(), null, "OK");
		boxHolder = hld;
		
		setStyleCode(code);
		
		pnl.setCode(getStyleCode());
		pnl.showData();
		
		this.hide();
		run();

	}

	public Code getStyleCode() {
		return styleCode;
	}

	public void setStyleCode(Code styleCode) {
		this.styleCode = styleCode;
	}

	public void update(CodeEditCb codeEditCb) {
		this.codeEditCb = codeEditCb;
		
	}

	@Override
	public Widget ui() {
		return pnl;
	}

	@Override
	public void cancel() {
		codeEditCb.codeState(false);
	}

	@Override
	public void ok() {
		setStyleCode(pnl.getCode());

		if (boxHolder == null) {
			codeEditCb.codeState(false);
			return;
		}

		if (pnl.changed) {
			codeEditCb.codeState(true);
		}


		
	}

}
