package com.bilgidoku.rom.site.yerel.wgts.edit.code;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.wgts.edit.Seen;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;

public class PnlNodeTabs extends Composite {

	enum TagType {
		WIDGET, TAG, CTRL
	};

	private final NodePnlAtt attPanel;
	private final NodePnlStyle stylePanel;
	private final NodePnlText textPanel;
	private final NodePnlParam paramPanel;
	private final TabPanel holder = new TabPanel();

	public PnlNodeTabs(JSONObject translations) {

		attPanel = new NodePnlAtt();
		textPanel = new NodePnlText();
		paramPanel = new NodePnlParam(translations);
		stylePanel = new NodePnlStyle();

		holder.setStyleName("site-innerform");
		holder.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				try {
					Seen wdt = (Seen) holder.getWidget(event.getSelectedItem());
					wdt.showData();
				} catch (RunException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		initWidget(holder);
	}

	public void noParam() {
		//holder.clear();
		holder.remove(0);
		holder.selectTab(0);
//		paramPanel.setVisible(false);
		//holder.setVisible(false);
	}

	public void buildNodeTabs(String tag, Code code) throws RunException {
		attPanel.update(code);
		textPanel.update(code);
		stylePanel.setCode(code);
		paramPanel.update(tag, code);

		holder.clear();
		//holder.setVisible(true);

		if (tag.startsWith("w:")) {

			holder.add(paramPanel, Ctrl.trans.parameters());
			holder.add(stylePanel, Ctrl.trans.styles());
			holder.add(attPanel, Ctrl.trans.attributes());
			
			holder.selectTab(0);

		} else if (tag.startsWith("c:")) {

			holder.add(paramPanel, Ctrl.trans.parameters());
			holder.add(textPanel, Ctrl.trans.text());
			
			holder.selectTab(0);

		} else if (tag.startsWith("p:")) {

		} else {

			holder.add(attPanel, Ctrl.trans.attributes());
			holder.add(stylePanel, Ctrl.trans.styles());
			holder.add(textPanel, Ctrl.trans.text());
			
			holder.selectTab(0);

		}

	}

//	public boolean isChanged() {
//		return (attPanel != null && attPanel.changed) || (textPanel != null && textPanel.changed)
//				|| (stylePanel != null && stylePanel.changed) || (paramPanel != null && paramPanel.changed);
//	}

}
