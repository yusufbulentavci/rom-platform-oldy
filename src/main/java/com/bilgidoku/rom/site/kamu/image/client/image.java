package com.bilgidoku.rom.site.kamu.image.client;

import java.util.List;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.browse.image.BrowseCallback;
import com.bilgidoku.rom.gwt.client.util.browse.image.Browser;
import com.bilgidoku.rom.gwt.client.util.browse.image.ServerBrowseCb;
import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompBase;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
import com.bilgidoku.rom.gwt.client.util.com.FrameCom;
import com.google.gwt.user.client.Window.Location;

/**
 * 
 * 
 * 
 * CKEditor=editor1&CKEditorFuncNum=1&langCode=en
 * 
 * 
 * @author avci
 * 
 */
public class image extends RomEntryPoint implements ServerBrowseCb, BrowseCallback {
	
	public static final CompInfo info = new CompInfo("+image", 100, new String[] {},
			new String[] {}, null);

	private CompFactory factory = new CompFactory() {

		@Override
		public CompInfo info() {
			return info;
		}

		@Override
		public Comp create() {
			return comp;
		}

	};
	
	

	private CompBase comp = new CompBase() {
		
		public void initial() {
			bi.setResource(FrameCom.getParentAttr("uri"));
//			RomEntryPoint.one.addToRootPanel(bi);	
		}

		@Override
		public CompInfo compInfo() {
			return info;
		}

	};

	
	private Browser bi = new Browser(true, this);
	


	String ckEditorFuncNum;
	String ckEditorLangCode;
	String ckEditorId;

	public image() {
		super("Rom Server Image Browser Application", false, null, true, true);
		ckEditorId = Location.getParameter("CKEditor");
		ckEditorFuncNum = Location.getParameter("CKEditorFuncNum");
		ckEditorLangCode = Location.getParameter("langCode");
	}



	@Override
	protected void main() {
		start();
	}

	protected void start() {
		
		RomEntryPoint.one.addToRootPanel(bi);

	}

	@Override
	public void cancel() {
	}

	@Override
	public void noImage() {
	}

	protected native void closeForCkEditor(String fileUrl, String ckEditorId, String ckEditorFuncNum)/*-{
		try {
		if(fileUrl!=null)
		$wnd.opener.CKEDITOR.tools.callFunction(ckEditorFuncNum, fileUrl);
		}finally
		$wnd.close();		
		}-*/;

	@Override
	public void selected(String link) {
		if (link == null || ckEditorId == null)
			return;

		closeForCkEditor(link.indexOf("?") > 0 ? link + "&romthumb=m" : link + "?romthumb=m", ckEditorId,
				ckEditorFuncNum);
	}

	@Override
	public String comGetStr() {
		return bi.getSelected();
	}

	@Override
	public List<CompFactory> factory() {
		List<CompFactory> f = super.factory();
		f.add(factory);
		return f;
	}
}
