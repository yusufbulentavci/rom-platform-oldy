package com.bilgidoku.rom.shared.xml;



public class Doc extends Elem{
	
	public Doc(){
		super("");
	}
	
//	public Doc(JSONObject jo) throws RunException {
//		super(jo);
//		// TODO Auto-generated constructor stub
//	}

//	public Doc(JSONArray ja) throws RunException {
//		super("");
//		if(ja==null || ja.isNull()!=null)
//			return;
//		List<Elem> elems=new ArrayList<Elem>();
//		for(int i=0; i< ja.size(); i++){
//			JSONObject jo=ja.get(i).isObject();
//			elems.add(new Elem(jo));
//		}
//	}
	
	
	
	public String toString(){
		StringBuilder sb=new StringBuilder();
		smakeStr(sb);
		return sb.toString();
	}

	
	public static Elem createSpanNode() {
		Elem el = new Elem("span");
		return el;
	}
	public static Elem createGroupingNode() {
		Elem el = new Elem("div");
		el.setAttribute("style", "display:inline");
		el.setAttribute("class","grouping");
		return el;
	}

	public static Elem createInvisibleGroupingNode() {
		Elem el = new Elem("span");
		el.setAttribute("style", "display:none");
		return el;
	}

	public static Elem appendGroupingNode(Elem curElem) {
		Elem newElem = createGroupingNode();
		curElem.appendChild(newElem);
		return newElem;
	}
	
	public static Elem appendTag(Elem curElem, String name) {
		Elem newElem = new Elem(name);
		curElem.appendChild(newElem);
		return newElem;
	}

	@Override
	public String getTag() {
		return "NotUsed";
	}
	
	public Elem getOnlyChild(){
		return this.childs.get(0);
	}

	
	
}
