package com.bilgidoku.rom.site.yerel.wgts.edit.code;

import java.util.Map;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.shared.code.NullableMap;
import com.bilgidoku.rom.site.yerel.wgts.edit.FlxGrid;
import com.bilgidoku.rom.site.yerel.wgts.edit.Seen;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Composite;

public class NodePnlParam extends Composite implements Seen {
	private Code code;
	private final FlxGrid flx = new FlxGrid(this, false);
	public boolean changed = false;
	private String tag;
	private JSONObject translations;

	public NodePnlParam(JSONObject translations) {
		this.translations = translations;
		initWidget(flx);
	}

	public void update(String tag, Code code) {
		this.tag = tag;
		this.code = code;
	}

	@Override
	public void showData() throws RunException {
		// command ve widget için çalışır
		if (!(tag.startsWith("c:") || tag.startsWith("w:")))
			return;

		if (code == null)
			return;

		flx.resetTable();
		
		NullableMap<String, String> tagPrms = code.getParams();
		Map<String, Att> allParams = code.getParamDefs();

		
		if (allParams != null && allParams.size() > 0) {
			for (String key : allParams.keySet()) {
				Att param = allParams.get(key);
				if (!param.isMethod() && !param.isDeclare()) {
					String value = null;
					if (tagPrms.get(key) != null)
						value = tagPrms.get(key);
					
					if (value == null) {
						value = param.getDefvalue();						
					}
					
					flx.addOneParam(param, value, translations);
				}
			}

		} else {
			if (tagPrms != null && tagPrms.keySet() != null)
				for (String key : tagPrms.keySet()) {
					MyAtt myatt = new MyAtt(key);
					flx.addOneRow(myatt, tagPrms.get(key));
				}
		}

	}

	@Override
	public void dataChanged(String name, String value) {
		if(value==null || value.isEmpty()){
			this.code.removeParam(name);
			return;
		}
		this.code.setParam(name, value);
		this.changed = true;
	}

	@Override
	public void dataChanged(String type, String name, String value) {
		// TODO Auto-generated method stub
		
	}

}
