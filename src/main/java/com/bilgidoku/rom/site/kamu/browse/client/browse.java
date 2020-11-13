package com.bilgidoku.rom.site.kamu.browse.client;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.browse.image.BrowseCallback;
import com.bilgidoku.rom.gwt.client.util.browse.image.ServerBrowseCb;
import com.bilgidoku.rom.gwt.client.util.browse.items.BrowseItems;
import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompBase;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
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
public class browse extends RomEntryPoint implements ServerBrowseCb, BrowseCallback {
	public static final CompInfo info = new CompInfo("+browse", 100, new String[] {},
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
			RomEntryPoint.one.addToRootPanel(bi);	
		}

		@Override
		public CompInfo compInfo() {
			return info;
		}

	};

	BrowseItems bi=new BrowseItems(this);
	
	String ckEditorFuncNum;
	String ckEditorLangCode;
	String ckEditorId;

	public browse() {
		super("Rom Server Browse App", false, null, false, false);
		ckEditorId = Location.getParameter("CKEditor");
		ckEditorFuncNum = Location.getParameter("CKEditorFuncNum");
		ckEditorLangCode = Location.getParameter("langCode");
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

		closeForCkEditor(link, ckEditorId, ckEditorFuncNum);
	}

	@Override
	public void cancel() {
	}

	@Override
	public void noImage() {
	}

	@Override
	public String comGetStr() {
		return bi.getSelected();
	}

	
}
