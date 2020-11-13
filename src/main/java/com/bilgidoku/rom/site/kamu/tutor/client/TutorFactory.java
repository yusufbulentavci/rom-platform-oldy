package com.bilgidoku.rom.site.kamu.tutor.client;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.google.gwt.user.client.ui.Button;

public class TutorFactory {

	public static Object create(boolean edit, String lang, JSONObject jo, String btnsParams, String code2, String summaryParams) {

		InstrumentTutor ret = null;
		// JSONObject
		// jo=com.google.gwt.json.client.JSONParser.parseStrict(dene).isObject();
		try {
			String[] langs = jo.getStringArray("langs");
			JSONArray names = jo.getArray("name");
			JSONArray sels = jo.getArray("sels");
			JSONArray opts = jo.getArray("opts");
			JSONArray imgs = jo.getArray("imgs");
//			JSONArray line = jo.getArray("line");
//			JSONArray ptr = jo.getArray("ptr");
//			JSONArray lvls = jo.getObject("conf").isObject().getArray("levels").isArray();
//			JSONArray dontknow = jo.getObject("conf").isObject().getArray("dontknow").isArray();
			
			
//			Vector2d confDontKnow=new Vector2d(dontknow);
			
			int sl = 0;
			for (int i = 0; i < langs.length; i++) {
				if (langs[i].equals(lang)) {
					sl = i;
					break;
				}
			}
			
			String tutorName=getLanged(names, sl);

			Option[] options=new Option[opts.size()];
			for (int i = 0; i < opts.size(); i++) {
				JSONArray ja = opts.get(i).isArray();
				
				JSONArray jn=ja.get(0).isArray();
				String name=getLanged(jn, sl);
				
								
				String code=ja.get(1).isString().stringValue();
				
				Vector2d v=new Vector2d(ja.get(2).isArray());
				
				int selInd=ja.get(3).isNumber().intValue();
				int imgInd=ja.get(4).isNumber().intValue();
				
				String summary=null;
				if(jn.size()>5){
					jn=ja.get(5).isArray();
					summary=getLanged(jn, sl);
				}
				
				
				Option o=new Option(i, name, code, imgInd, v, selInd, summary);
				options[i]=o;
			}
			
			Img[] images=new Img[imgs.size()];
			for (int i = 0; i < imgs.size(); i++) {
				JSONArray ja = imgs.get(i).isArray();
				
				JSONArray jn=ja.get(0).isArray();
				String name=getLanged(jn, sl);
				
				String uri=ja.get(1).isString().stringValue();
				
				Vector2d pos=new Vector2d(ja.get(2).isArray());
				Vector2d size=new Vector2d(ja.get(3).isArray());
				
				
				Img img=new Img(name,uri, pos, size);
				images[i]=img;
			}
			
			Button[] selects=new Button[sels.size()];
			for(int i=0;i<selects.length; i++){
				JSONArray ja = sels.get(i).isArray();
				
				JSONArray jn=ja.get(0).isArray();
				String name=getLanged(jn, sl);
				
//				Vector2d pos=new Vector2d(ja.get(1).isArray());
				
				Button sel=new Button(name);
				selects[i] = sel;
			}
			
			
//			LineStyle lineStyle=new LineStyle(line.get(0).isNumber().intValue(), new Vector3d(line.get(1).isArray()));
//			
//			PtrStyle ptrStyle=new PtrStyle(ptr.get(0).isNumber().intValue(), ptr.get(1).isNumber().intValue(), new Vector3d(ptr.get(2).isArray()));
//
//			Level[] levels=new Level[lvls.size()];
//			for(int i=0;i<levels.length; i++){
//				JSONArray ja = lvls.get(i).isArray();
//				
//				JSONArray jn=ja.get(0).isArray();
//				String name=getLanged(jn, sl);
//				
//				int lower=ja.get(1).isNumber().intValue();
//				int count=ja.get(2).isNumber().intValue();
//				
//				Level l=new Level(name, lower, count);
//				levels[i] = l;
//				
//			}
			Level[] levels = new Level[] { new Level("all", (short)0, 0) };
			PtrStyle ptrStyle = new PtrStyle(2, 5, new Vector3d(255, 0, 0));
			LineStyle lineStyle = new LineStyle(2, new Vector3d(255, 0, 0));
			return new InstrumentTutor(lang, tutorName, images, options, selects, levels, ptrStyle, lineStyle, btnsParams, code2, summaryParams);

		} catch (RunException e) {
			Sistem.printStackTrace(e, "Cant create Instrument tutor...");
		}

		return ret;
	}

	static String getLanged(JSONArray ja, int index) throws RunException {
		if (index >= ja.size()) {
			index = 0;
		}
		return ja.get(index).isString().stringValue();

	}

}
