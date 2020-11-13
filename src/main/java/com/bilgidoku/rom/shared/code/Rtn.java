package com.bilgidoku.rom.shared.code;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JsonTransfer;

public class Rtn implements JsonTransfer {

	public List<Code> codes = new ArrayList<Code>();

	public Rtn() {
	}

	public Rtn(Code cin) {
		codes.add(cin);
	}

	public Rtn(List<Code> cin) {
		codes = cin;
	}

	public void init(JSONArray ja, CodeRepo wr) throws RunException {
		if (ja != null)
			for (int i = 0; i < ja.size(); i++) {
				codes.add(new Code(null, ja.get(i).isObject(), wr));
			}

	}

	public JSONArray store() throws RunException {
		JSONArray ret = new JSONArray();
		for (Code it : codes) {
			ret.add(it.store());
		}
		return ret;
	}

	public boolean isEmpty() {
		return codes==null || codes.size()==0;
	}
}
