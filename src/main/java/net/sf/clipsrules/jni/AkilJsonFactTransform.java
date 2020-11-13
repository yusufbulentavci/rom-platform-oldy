package net.sf.clipsrules.jni;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.shared.err.KnownError;


public class AkilJsonFactTransform {
	
	public static String consoleJsonToFact(String template, JSONObject jo) throws KnownError {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(template);
		sb.append(" ");
		Iterator<String> it = jo.keys();
		try {
			while (it.hasNext()) {
				String key = it.next();
				String val = jo.getString(key);
				sb.append("(");
				sb.append(key);
				sb.append(" ");
				sb.append(val);
				sb.append(") ");
			}
		} catch (JSONException e) {
			throw new KnownError("consoleJsonToFact:", e);
		}

		sb.append(")");
		return sb.toString();
	}

	public static String jsonToFact(int hostId, Integer cint, String eylem, JSONObject jo) throws KnownError {
		StringBuilder sb = new StringBuilder();
		sb.append("(soyledi (host ");
		sb.append(hostId);
		sb.append(") (cint ");
		sb.append(cint);
		sb.append(") (eylem ");
		sb.append(eylem);
		sb.append(") ");
		Iterator<String> it = jo.keys();
		try {
			while (it.hasNext()) {
				String key = it.next();
				if (key.equals("host") || key.equals("cint")) {
					throw new KnownError("key try:" + key + " " + sb.toString()).security();
				}

				String val = jo.getString(key);
				sb.append("(");
				sb.append(key);
				sb.append(" ");
				sb.append(val);
				sb.append(") ");
			}
		} catch (JSONException e) {
			throw new KnownError("jsontofact:", e);
		}

		sb.append(")");
		return sb.toString();
	}

	public static void factToJson(JSONObject jsonObject, String eylem, List<PrimitiveValue> arguments) {
		jsonObject.safePut("eylem", eylem);
		for(int i=3; i<arguments.size(); i=i+2) {
			String key=arguments.get(i).getValue().toString();
			PrimitiveValue val = arguments.get(i+1);
			if(val.isString() || val.isSymbol()) {
				jsonObject.safePut(key, val.getValue().toString());
			}else if(val.isNumber()) {
				if(val.isInteger()) {
					jsonObject.safePut(key, ((NumberValue)val.getValue()).intValue());
				}else {
					jsonObject.safePut(key, ((NumberValue)val.getValue()).doubleValue());
				}
			}else if(val.isMultifield()) {
				List<String> ls=new ArrayList<String>();
				List<PrimitiveValue> pm = (List<PrimitiveValue>) val.getValue();
				for (PrimitiveValue p : pm) {
					ls.add(p.toString());
				}
				try {
					jsonObject.put(key, ls);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(jsonObject.toString());
				
			}else {
				System.err.println("Val type not implemented:"+val.getValue().toString());
			}
		}
	}

}
