package com.bilgidoku.rom.site.yerel.boxing;

import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.wgts.edit.code.NodePnlAnim;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Widget;

public class AnimationDlg extends ActionBarDlg {
	private BoxHolder boxHolder;
	// private String boxId;
	private NodePnlAnim pnl = new NodePnlAnim();

	private JSONObject animations = null;

	public AnimationDlg(BoxHolder hld) {
		super(Ctrl.trans.animation(), "/_local/images/common/check.png", Ctrl.trans.ok());
		boxHolder = hld;
		run();
	}

	public void update(String id, BoxHolder hld, Code code) {
		boxHolder = hld;
		pnl.setCode(code);

		if (animations != null) {
			pnl.show();
			return;
		}

		new GetReadyAnimationList(this);
	}

	public void animationsReady(JSONObject obj) {
		animations = obj;
		pnl.setAnimations(animations);
		pnl.show();
	}

	@Override
	public Widget ui() {
		return pnl;
	}

	@Override
	public void cancel() {
	}

	@Override
	public void ok() {
		if (boxHolder == null)
			return;

		if (pnl.changed) {
			boxHolder.setChanged(true);
		}
		
	}

}
