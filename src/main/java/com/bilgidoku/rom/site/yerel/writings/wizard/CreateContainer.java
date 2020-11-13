package com.bilgidoku.rom.site.yerel.writings.wizard;

import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.google.gwt.user.client.ui.Composite;

public class CreateContainer extends Composite implements PageReady{

	final protected PageWizard parent;

	public CreateContainer(String pageName, String template, String[] alternativeNames, PageWizard caller) {
		this.parent = caller;		
		initWidget(new PnlCreateContainer(pageName, template, alternativeNames, parent, Data.WRITING_ROOT));
	}


	@Override
	public void pageReady(String title, String uri, String lang) {
		parent.pageAdded(title, uri);
	}


}
