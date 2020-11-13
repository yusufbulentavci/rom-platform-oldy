package com.bilgidoku.rom.site.yerel.boxing;

import com.bilgidoku.rom.gwt.client.util.com.RomFrameImpl;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.HtmlEditAreaWrapper;
import com.bilgidoku.rom.site.yerel.common.SvgEditWrapper;
import com.bilgidoku.rom.site.yerel.common.UmlEditWrapper;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public class DlgHtmlEdit extends ActionBarDlg {

	final Button btnOk = new SiteButton("/_local/images/common/check.png", Ctrl.trans.ok(), Ctrl.trans.ok(), "");
	final Button btnCancel = new Button("Cancel");
	final Button btnHelp = new SiteButton("/_local/images/common/question.png", Ctrl.trans.help(), Ctrl.trans.help(),
			"");
	private final RomFrameImpl rt;
	private CodeEditCb cb;
	private final char type;

	public DlgHtmlEdit(final char type, String html, int width, int height) {
		super(Ctrl.trans.edit(), null, Ctrl.trans.ok());
//		super(true, true, false, true, true);
		
		width=1000;
		height=700;
		
		if(type == 'u'){
			this.type='u';
			rt=UmlEditWrapper.create(html);
			rt.setWidth("860px");
			rt.setHeight("700px");
		}else if(html.indexOf("<svg")>=0){
			this.type='s';
//			html=RegExp.compile("width=\"[0-9]+px").replace("$1", "width=\""+width+"px");
			rt=SvgEditWrapper.create(html);
			rt.setWidth("1000px");
			rt.setHeight("700px");
		}else{
			this.type='h';
			rt = HtmlEditAreaWrapper.create(html);

			rt.setWidth("860px");
			rt.setHeight("500px");
		}
		run();

	}

	public void start(CodeEditCb cb) {
		this.cb = cb;
		btnOk.setVisible(true);
		show();
		center();
	}

	public String getHtml() {
		return rt.getText();
	}

	@Override
	public Widget ui() {
		return rt;
	}

	@Override
	public void cancel() {
		cb.codeState(false);		
	}

	@Override
	public void ok() {
		cb.codeState(true);
		
	}

	public String getFeature(String cls) {
		return rt.getAttr(cls);
	}

}
