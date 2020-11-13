package com.bilgidoku.rom.site.kamu.tutor.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.json.client.JSONObject;

public class Tutor {

	Name[] names;
	
	protected String tutorName;
	protected int[] scores;
	protected final Set<Integer> consantrateOn = new HashSet<Integer>();
	protected final List<Integer> known = new ArrayList<Integer>();

	protected final List<Integer> last = new ArrayList<Integer>();

	protected boolean needToSave = false;

	private JSONObject oldSaved;
	
	
	

	

}
