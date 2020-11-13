package com.bilgidoku.rom.site.kamu.cafe.client;

import java.util.List;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompBase;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class cafe extends RomEntryPoint{
	public cafe() {
		super("Rom Server Exam Application", false, null, true, true);
	}



	public static final CompInfo info = new CompInfo("+cafe", 100, new String[] {},
			new String[] {"isauth"}, null);

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
			
			HorizontalPanel holder = new HorizontalPanel();
			holder.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
			holder.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
//			holder.add(new PnlTakeExam());
			holder.addStyleName("site-holder");
			holder.setWidth("800px");
			RomEntryPoint.one.addToRealRootPanel(holder);
		}

		@Override
		public CompInfo compInfo() {
			return info;
		}

	};

	@Override
	public List<CompFactory> factory() {
		List<CompFactory> f = super.factory();
		f.add(factory);
		return f;
	}



}
