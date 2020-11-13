package com.bilgidoku.rom.site.yerel.wgts.edit.code;

import java.util.Map;

import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.ImageAnchor;
import com.bilgidoku.rom.site.yerel.wgts.edit.Seen;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class NodePnlStyle extends Composite implements Seen {

	private PnlAdvancedStyle advancedForm = new PnlAdvancedStyle(this);
	private PnlSimpleStyle simpleForm = new PnlSimpleStyle(this);

	private Code code;

	public boolean changed = false;

	public NodePnlStyle() {

		switchToSimple(true);

		initWidget(getForm());
	}

	private Widget getForm() {
		ImageAnchor chkSimpleMode = new ImageAnchor();
		chkSimpleMode.changeResource("/_local/images/common/html.png");
		chkSimpleMode.setTitle(Ctrl.trans.options());

		chkSimpleMode.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (advancedForm.isVisible()) {
					switchToSimple(true);					
					simpleForm.showSimpleData();
				} else {
					switchToSimple(false);
					advancedForm.showAdvancedData();
				}

			}
		});

		VerticalPanel vp = new VerticalPanel();
		vp.add(simpleForm);
		vp.add(chkSimpleMode);
		vp.add(advancedForm);
		return vp;

	}

	public void switchToSimple(boolean b) {
		
		simpleForm.setVisible(b);
		advancedForm.setVisible(!b);

	}

	public void setCode(Code code) {
		this.code = code;
	}

	public void showData() {
		if (code == null)
			return;

		if (code.tag.startsWith("c:"))
			return;

		
		simpleForm.resetForm();
		advancedForm.resetForm();

		switchToSimple(true);
		simpleForm.showSimpleData();

	}

	@Override
	public void dataChanged(String name, String value) {
		dataChanged("defaultstyle", name, value);		
	}

	public Code getCode() {
		return this.code;
	}


	@Override
	public void dataChanged(String type, String name, String value) {
		changed = true;
		if (value == null || value.isEmpty()) {
			code.delStyleByType(type, name);
			return;
		}
		code.setStyleByType(type, name, value);	
	}

	public Map<String, String> getStyleByType(String type) {
		return code.getStyleByType(type);
	}

	
	
}
