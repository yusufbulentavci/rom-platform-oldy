package com.bilgidoku.rom.site.kamu.tutor.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.min.Sistem;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;

public class Learning {

	protected final int[] scores;
	protected final List<Short> known = new ArrayList<Short>();
//	protected final Set<Integer> consantrateOn = new HashSet<Integer>();
	protected final Circle circle=new Circle();

	protected int last=-1;
	private int tick = 0;
	private Leveler leveler;


	public Learning(int length, Leveler leveler) {
		scores = new int[length];
		this.leveler=leveler;
	}

	List<Short> getWorking() {
		List<Short> consantrateOn = circle.toList();
		List<Short> ret = new ArrayList<Short>();
		for (Short integer : consantrateOn) {
			ret.add(integer);
		}
		return ret;
	}

	List<Short> getKnown() {
		List<Short> ret = new ArrayList<Short>();
		for (short i = 0; i < scores.length; i++) {
			if (scores[i] >= 2) {
				Sistem.outln("We know:" + i);
				ret.add(i);
			}
		}
		return ret;
	}

	protected void fillWithValues(int val) {
		Arrays.fill(scores, val);
	}

	protected void loadScores(JSONArray arr) {

		for (short i = 0; i < arr.size() && i < scores.length; i++) {
			int val = ClientUtil.arrIntOnIndex(arr, i);
			if (val >= 2)
				known.add(i);
			scores[i] = val;
		}

	}
	
	protected void loadConsan(JSONArray arr) {
		for (int i = 0; i < arr.size(); i++) {
			short val = (short) ClientUtil.arrIntOnIndex(arr, i);
			this.circle.insertFirst(val);
		}
	}
	
	protected JSONObject saveObj(int confLevel) {
		JSONObject jo = new JSONObject();
		JSONArray arr = new JSONArray();
		for (int i = 0; i < scores.length; i++) {
			arr.set(i, new JSONNumber(scores[i]));
		}
		jo.put("score", arr);
		jo.put("level", new JSONNumber(confLevel));
		List<Short> consantrateOn=this.circle.toList();
		if (consantrateOn.size() > 0) {
			JSONArray carr = new JSONArray();
			int ind = 0;
			for (Short v : consantrateOn) {
				carr.set(ind++, new JSONNumber(v));
			}
			jo.put("con", carr);
		}

		return jo;
	}

	public void factorySettings() {
		for (int i = 0; i < scores.length; i++) {
			scores[i] = 1;
		}
		this.circle.clear();
		known.clear();
	}
	
	protected boolean weKnowAll(){
		return this.scores.length==this.known.size();
	}
	
	public short getToAsk(){
		short which=getToAsk2();
		last=which;
//		Sistem.outln("circle"+circle.toString());
		return which;
	}
	
	private short getToAsk2() {
		leveler.fill(circle, scores);
		tick++;
//		if (tick % 7 == 0) {
//			Sistem.outln("Remember known");
//			if (known.size() > 0) {
//				short l = (short) (Math.random() * known.size());
//
//				short x = known.get(l);
//				if (last!=x) {
//					Sistem.outln("Known:" + x);
//					return x;
//				}
//			}
//		}
		
		if(!this.circle.goToNextElement()){
			throw new RuntimeException("circle is empty");
		}
		return this.circle.getActualElementData();
	}
	
	protected void failed(short which) {
		this.scores[which] = 0;
		this.circle.insertBeforeActual(which);
//		leveler.fill(circle, scores);
		this.known.remove(Integer.valueOf(which));
		leveler.fill(circle, scores);
//		Sistem.outln("Consantrate:" + consantrateOn.toString());

	}
	
	public boolean aResponse(short optionIndex){
		boolean ret=false;
		if (scores[optionIndex] < 2)
			ret = true;
		
		if(scores[optionIndex]<2)
			scores[optionIndex]=2;
		else
			scores[optionIndex]++;
		
//		Sistem.outln(optionIndex+" : "+ scores[optionIndex]);
		known.add(optionIndex);
		circle.deleteActualElement();
		leveler.fill(circle, scores);
		return ret;
	}

	public void loadCompleted() {
	}

}
