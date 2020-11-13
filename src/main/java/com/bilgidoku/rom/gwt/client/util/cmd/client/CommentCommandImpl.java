package com.bilgidoku.rom.gwt.client.util.cmd.client;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.DialogsDao;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.cmds.client.CommentCommand;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.xml.Elem;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

class CommentDlg extends ActionBarDlg {

	public String comm;
	private final TextArea txt = new TextArea();
	private String dlgUri;

	public CommentDlg(String dlgUri) {
		super("Comment", null, "Send");
		this.dlgUri = dlgUri;
		run();
	}

	@Override
	public Widget ui() {
		txt.setSize("300px", "120px");
		return txt;
	}

	@Override
	public void cancel() {
		comm = null;
	}

	@Override
	public void ok() {
		comm = txt.getValue();
		if (comm == null || comm.length() == 0)
			return;

		DialogsDao.comment(null, comm, null, null, null, true, dlgUri, new StringResponse() {
		});

		Window.alert("Your comment taken for approvel, thank you.");

		this.hide();

	}

}

public class CommentCommandImpl extends CommentCommand {

	@Override
	protected void execute(Elem curElem, RenderCallState rz) throws RunException {
		String contact = RomEntryPoint.com().get("user.contact");
		if (contact == null) {
			RomEntryPoint.com().post("*userneed", "mode", "contact");
			return;
		}
		String dlgUri = rz.evaluateText("${item.dialog_uri}");

		final CommentDlg dlg = new CommentDlg(dlgUri);
		dlg.show();
		dlg.center();

	}

}
