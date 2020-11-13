package com.bilgidoku.rom.site.yerel.boxing;

import java.util.List;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.RunZone;
import com.bilgidoku.rom.shared.code.Code;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class BoxHolder // implements BoxerCanvasFeedback
{

	public String nid;
	public String lngcode;
	public String uri;
	public String tbl;
	public String symbl;
	public String clmn;

	private BoxerCanvas canvas;

	private AbsolutePanel boxingPanel;
	private RunZone runZone;
	private final Code code;

	private boolean changed = false;
	private final AnimationDlg dlgAnim = new AnimationDlg(this);
//	private final TimeLineDlg timeLine = new TimeLineDlg();
	private boolean busy;
	private BoxHolderCb myBoxer;

	public BoxHolder(BoxHolderCb feedback, RunZone rz, AbsolutePanel boxingPanel, String nid, String lngcode, String uri,
			String tbl, String symbl, String clmn, Code code) {
		this.myBoxer = feedback;
		this.runZone = rz;
		this.boxingPanel = boxingPanel;
		this.nid = nid;
		this.lngcode = lngcode;
		this.uri = uri;
		this.tbl = tbl;
		this.symbl = symbl;
		this.clmn = clmn;
		this.code = code;
//		showTimeLine();
	}

	public void removed() {
		com.google.gwt.dom.client.Element el = Document.get().getElementById("rsz" + nid);
		if (el != null)
			el.removeFromParent();
	}

	public void htmlReady() {
		if (canvas == null) {
			try {
				canvas = new BoxerCanvas(this, boxingPanel, nid, code);
				return;
			} catch (RunException e) {
				e.printStackTrace();
			}
		}
		canvas.redraw();
	}

	public com.google.gwt.json.client.JSONObject codes() throws RunException {
		return canvas.codes();
	}

	public void drawStart() {
	}

	public Tcs popTransfer() throws RunException {
		Code c = myBoxer.popTransfer();
		if (c == null)
			return null;

		String s = HtmlPreview.doit(c, runZone);
		return new Tcs(c, s);
	}

	public void pushTransfer(Code code) {
		myBoxer.pushTransfer(code);
	}

	public void canvasResize() {
		setChanged(true);
		myBoxer.canvasResize();
	}

	public void relocate() {
		this.setChanged(true);
		canvas.relocate();
	}

	public String name() {
		return clmn;
	}

	public void busy() {
		this.busy = true;
	}

	protected void checkIdle() {
		if (!busy) {
			canvas.idle();
		}
		this.busy = false;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public void showMenu(int left, int top, String id, Code code) {
		DlgBoxOptions menu = new DlgBoxOptions(this, id);
		menu.setPopupPosition(left, top);
		menu.show();
	}
	

	public void deleteBox(String id) {
		this.canvas.removeBox(id);
		setChanged(true);

	}
	
	public void editBox(String boxId) {
		this.canvas.editBox(boxId);

	}

	public void editWidget(final String id, final Code code) {
		try {

			String title = ClientUtil.getTranslation(code.tag.replace("w:", "_"), myBoxer.getSelectedTrans());
			
			final DlgWidgetEdit dlgEditWidget = new DlgWidgetEdit(myBoxer.getSelectedTrans(), title);
			dlgEditWidget.center();
			dlgEditWidget.update(code, false, new CodeEditCb() {
				@Override
				public void codeState(boolean isChanged) {
					widgetEditted(isChanged, id, code);
				}
			});
			
		} catch (RunException e) {
			e.printStackTrace();
		}
	}

	public void editHtml(final char type, final Code code, final String id, final String html) {

		final DlgHtmlEdit dlgHtmlWidget = new DlgHtmlEdit(type, html, 0, 0);

		
		dlgHtmlWidget.start(new CodeEditCb() {
			@Override
			public void codeState(boolean isChanged) {
				if (!isChanged) {
					widgetNotChanged(id);
					return;
				}
				
				String html = dlgHtmlWidget.getHtml();
				
				if(type=='u'){
					String img=dlgHtmlWidget.getFeature("img");
					
					code.setParam("_uml", html);
					code.setParam("_img", img);
					widgetEditted(isChanged, id, code);
					return;
				}

				htmlEditted(id, html);

			}
		});

	}

	protected void htmlEditted(String id, String html) {

		List<Code> newCode = myBoxer.getBoxHtmlCode(html);
		this.canvas.htmlChanged(id, newCode, html);
		setChanged(true);

	}



	public void showStyleDlg(final String boxId) {
		Code boxCode = canvas.getCodeOfBox(boxId);
		if (boxCode == null)
			return;

		final StyleDlg dlgStyle = new StyleDlg(boxId, this, boxCode);
		dlgStyle.update(new CodeEditCb() {			
			@Override
			public void codeState(boolean isChanged) {
				if (!isChanged) 
					return;					
				
				styleChanged(boxId, dlgStyle.getStyleCode());
				
			}
		});
		
		dlgStyle.show();
		dlgStyle.center();
	}

	// returnings
	protected void widgetEditted(boolean isChanged, String id, Code code2) {
		if (!isChanged) {
			widgetNotChanged(id);
			return;
		}

		String html;
		try {
			html = HtmlPreview.doit(code2, runZone);
			widgetChanged(id, html);

		} catch (RunException e) {
			e.printStackTrace();
		}

	}

	public void widgetNotChanged(String id) {
		canvas.widgetNotChanged(id);
	}

	public void widgetChanged(String id, String html) {
		canvas.widgetChanged(id, html);
		setChanged(true);
	}

	protected void styleChanged(String boxId, Code code2) {
		setChanged(true);
		canvas.styleBox(boxId, code2);
	}

	public void showAnimationDlg(String boxId) {
		Code boxCode = canvas.getCodeOfBox(boxId);
		if (boxCode == null)
			return;

		dlgAnim.update(boxId, this, boxCode);
		dlgAnim.show();
		dlgAnim.center();
	}

	public void idle() {
		canvas.idle();

	}

	public void detach() {
		canvas.detach();
	}

	public void selectAll() {
		canvas.selectAll();
	}

}
