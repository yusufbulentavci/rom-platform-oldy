package com.bilgidoku.rom.site.kamu.tshirt.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.gwt.araci.client.rom.Stocks;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.RunException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class Stock {
	private Stocks stock;
	private Stock firstStock;
	private Map<String, Stock> children;
	private Map<String, Stock> alternatives;

	private Stock virtualsParent;
	private Constraint constraint;

	private JSONObject effectiveOptions = null;

	Stock(Stocks stock) {
		this.stock = stock;
	}

	public void addSibling(Stock pr) {
		if (virtualsParent != null) {
			virtualsParent.addSibling(pr);
			return;
		}
		if (firstStock != null) {
			firstStock.addSibling(pr);
			return;
		}
		if (children == null)
			children = new HashMap<>();

		children.put(pr.getUri(), pr);
		Sistem.outln("ADD SIBLING:"+stock.uri+" <"+pr.getUri());
	}

	public Map<String, Stock> getAlternatives() {
		if (firstStock != null) {
			return firstStock.alternatives;
		}

		return alternatives;
	}

	public void addAlternative(Stock pr) {
		if (firstStock != null) {
			firstStock.addAlternative(pr);
			return;
		}
		if (alternatives == null)
			alternatives = new HashMap<>();

		alternatives.put(pr.getUri(), pr);
	}

	private String getUri() {
		return stock.uri;
	}

	public void setFirstStock(Stock p) {
		this.firstStock = p;
	}

	public Stock getFirstStock() {
		if (this.firstStock != null)
			return this.firstStock;
		return this;
	}

	public void setVirtualsParent(Stock p) {
		this.virtualsParent = p;
	}

	public Stocks getStock() {
		return stock;
	}

	public String getColor() {
		return inheritedOptStr("color");
	}

	public String getSize() {
		return inheritedOptStr("size");
	}

	private String inheritedOptStr(String opt) {
		final JSONObject options = effectiveOptions();
		if(options==null)
			return null;
		
		try {
			String val = ClientUtil.optString(options, opt);
			return val;
		} catch (RunException e) {
			Sistem.printStackTrace(e, options!=null?options.toString():"Empty");
			return null;
		} 
	}
	
	
	private static JSONObject merge(Stock parent, JSONObject ch){
		JSONObject tmp=null;
//		if(effectiveOptions!=null)
//			return effectiveOptions;
		
		JSONObject ef = parent.stock.options.getValue().isObject();
		
		if (parent.firstStock != null) {
			tmp=merge(parent.firstStock, ef);
		}
		
		if (parent.virtualsParent != null) {
			tmp=merge(parent.virtualsParent, ef);
		}
		
		

		tmp = deepMerge(tmp, ch);
		
		return tmp;
		
		
	}
	

	public JSONObject effectiveOptions() {		
		if (this.virtualsParent == null)
			return this.stock.options.getValue().isObject();
		
		if (effectiveOptions != null)
			return effectiveOptions;
		
		
		effectiveOptions=merge(this, null);
		if(effectiveOptions!=null)
			Sistem.outln(stock.uri+"-"+effectiveOptions.toString());
		
		return effectiveOptions;

	}

	public static JSONObject deepMerge(JSONObject paren, JSONObject chil) {
		if (paren == null && chil == null)
			return null;

		if (chil == null)
			return paren;
		if (paren == null)
			return chil;
		
		JSONObject ret = JSONParser.parseLenient(paren.toString()).isObject();

		for (Iterator<String> iterator = chil.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			JSONValue value = chil.get(key);
			if (paren.containsKey(key)) {
				// existing value for "key" - recursively deep merge:
				JSONObject ch = value.isObject();
				JSONValue t = paren.get(key);
				if (ch != null) {
					JSONObject pr = t.isObject();
					if (pr == null) {
						Sistem.errln("JSON Merging error ");
					} else
						ret.put(key, deepMerge(pr, ch));
				} else {
					ret.put(key, value);
				}
			}else{
				ret.put(key, value);
			}
		}
		
//		for (Iterator<String> iterator = paren.keySet().iterator(); iterator.hasNext();) {
//			String key = (String) iterator.next();
//			if(chil.containsKey(key))
//				continue;
//			ret.put(key, paren.get(key));			
//		}
		
		return ret;
	}

	public Map<String, List<Stock>> groupByOption(String opt, Constraint... cons) {
		// First stock only groups
		// Ignore others
		if (virtualsParent != null) {
			return virtualsParent.groupByOption(opt, cons);
		}

		if (firstStock != null) {
			return firstStock.groupByOption(opt, cons);
		}

		Map<String, List<Stock>> ret = new HashMap<>();
		if (children == null)
			return ret;
		
		Sistem.outln("GROUPBy"+children.size());
		
		for (Stock it : children.values()) {
			if (cons != null) {
				boolean pass = false;
				for (int i = 0; i < cons.length; i++) {
					constraint = cons[i];
					String val = it.inheritedOptStr(constraint.name);
					if (val != null && !val.equals(constraint.value)) {
						pass = true;
						break;
					}
				}
				if (pass)
					continue;
			}
			String val = it.inheritedOptStr(opt);
			if (val == null)
				continue;
			List<Stock> lst = ret.get(val);
			if (lst == null) {
				lst = new ArrayList<Stock>();
				ret.put(val, lst);
			}
			lst.add(it);
		}
		return ret;
	}

	public Map<String, StockCanvas> getCanvasList() {

		// if (virtualsParent != null) {
		// return extend(virtualsParent.getCanvasList());
		// }
		//
		// if (firstStock != null) {
		// return firstStock.getCanvasList();
		// }

		Map<String, StockCanvas> canvases = new HashMap<>();

		JSONObject options = effectiveOptions();
		try {
			if (options != null) {
				JSONObject jo = ClientUtil.optObject(options, "canvas");
				for (String key : jo.keySet()) {
					JSONObject jj = ClientUtil.getObject(jo, key);
					int width = ClientUtil.optInteger(jj, "w");
					int height = ClientUtil.optInteger(jj, "h");
					int x = ClientUtil.optInteger(jj, "x");
					int y = ClientUtil.optInteger(jj, "y");

					int backW = ClientUtil.optInteger(jj, "backw");
					int backH = ClientUtil.optInteger(jj, "backh");

					String rendered = ClientUtil.optString(jj, "rendered");
					String backImg = ClientUtil.optString(jj, "backimg");
					JSONObject elms=ClientUtil.optObject(jj, "rgf");
					if(elms==null)
						elms=new JSONObject();

					StockCanvas pc = new StockCanvas(key, width, height, x, y, backW, backH, backImg, rendered, elms);
					canvases.put(key, pc);
				}
			}
		} catch (RunException e) {
			Sistem.printStackTrace(e, options!=null?options.toString():"Empty");
		}
		return canvases;
	}

	// private Map<String, StockCanvas> extend(Map<String, StockCanvas>
	// canvasList) {
	//
	// JSONObject options = (JSONObject) stock.options.getValue();
	// try {
	// if (options != null) {
	// JSONObject jo = ClientUtil.optObject(options, "canvas");
	// for (String key : jo.keySet()) {
	// JSONObject jj = ClientUtil.getObject(jo, key);
	// StockCanvas sc = canvasList.get(key);
	//
	// String image = ClientUtil.optString(jj, "image");
	// if (image != null) {
	// sc.rendered = image;
	// }
	// }
	// }
	// } catch (RunException e) {
	// Sistem.printStackTrace(e, options.toString());
	// }
	//
	// return null;
	// }

}
