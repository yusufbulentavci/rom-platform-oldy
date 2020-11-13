package com.bilgidoku.rom.site.yerel.writings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Runner;
import com.bilgidoku.rom.shared.WidgetInstance;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.shared.code.CodeRepo;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Text;

public class ElementToCode {

	public List<Code> ret = new ArrayList<Code>();
	final CodeRepo repo;
	final Runner runner;

	public ElementToCode(CodeRepo repo, Runner runner) {
		this.repo = repo;
		this.runner = runner;
	}

	public void doit(Node e2, Code parent) throws RunException {
		for (int index = 0; index < e2.getChildCount(); index++) {
			Node e = e2.getChild(index);
			Code c = dealNode(e, parent);
			if (c == null)
				continue;
			doit(e, c);
			if (parent == null)
				ret.add(c);
			else
				parent.addChild(c);
		}
	}
	//
	// private void doit(Node e2, Code parent) throws RunException {
	// for (int index = 0; index < e2.getChildCount(); index++) {
	// Node e = e2.getChild(index);
	// Code c = dealNode(e, parent);
	// if (c == null)
	// continue;
	// doit(e, c);
	// if (parent == null)
	// ret.add(c);
	// else
	// parent.addChild(c);
	// }
	// }

	private Code dealNode(Node e, Code parent) throws RunException {
		if (e instanceof Text) {
			Text t = (Text) e;
			String nn = t.getNodeName();
			if (nn.equals("#text")) {
				Code c = new Code(parent, repo, "c:text");
				// if(t.getChildCount()==0)
				c.text = t.getData();
				return c;
			} else {
				Map<String, String> ats = getAts(t);
				String wins = (ats==null)?null:ats.get("rom_wgt");
				if (wins == null) {
					Code c = new Code(parent, repo, nn.toLowerCase());
					if(ats!=null)
						setAttr(c, ats);
					return c;
				} else {
					WidgetInstance ins = runner.getWidgetInstance(wins);
					return ins.getCodeCur();
				}

				// c.text=ce.getNodeValue();
			}
			// Window.alert("at:"+t.getNodeName());

		} else if (e instanceof Element) {
			Element ce = (Element) e;

			Map<String, String> ats = getAts(ce);
			String wins = (ats==null)?null:ats.get("rom_wgt");
			if (wins == null) {
				// Code c=new Code(parent, repo, nn.toLowerCase());
				// addAttr(c,ats);
				// return c;
				Code c = new Code(parent, repo, ce.getTagName());
				if(ats!=null)
					setAttr(c, ats);
				c.text = ce.getNodeValue();
				return c;
			} else {
				WidgetInstance ins = runner.getWidgetInstance(wins);
				return ins.getCodeCur();
			}

		}
		return null;
	}

	private void setAttr(Code c, Map<String, String> ats) {
		c.setAts(ats);
	}

	// private void addAttr(Code c, Node ce) {
	// final JsArray<Node> attributes = getAttributes(ce);
	// for (int i = 0; i < attributes.length(); i ++) {
	// final Node node = attributes.get(i);
	// String attributeName = node.getNodeName();
	// // if(attributeName.startsWith("rom_")){
	// String attributeValue = node.getNodeValue();
	// c.addAtt(attributeName, attributeValue);
	// // }
	// }
	// }

	public static Map<String, String> getAts(Node elem) {
		JsArray<Node> nats = getAttributes(elem);
		if (nats == null)
			return null;
		Map<String, String> r = new HashMap<String, String>();
		for (int i = 0; i < nats.length(); i++) {
			final Node node = nats.get(i);
			String attributeName = node.getNodeName();
			// if(attributeName.startsWith("rom_")){
			String attributeValue = node.getNodeValue();
			r.put(attributeName, attributeValue);
			// }
		}
		return r;
	}

	public static native JsArray<Node> getAttributes(Node elem) /*-{
		return elem.attributes;
	}-*/;

	public String firstImg() {
		if (ret == null)
			return null;
		for (Code r : ret) {
			Code found = r.findTag("img");
			if (found != null) {
				String src = found.getAtt("src");
				if (src != null) {
					return src;
				}
			}
		}
		return null;
	}

	public static List<Code> now(Runner runner, final CodeRepo codeRepo, final String html) {
		DivElement span = Document.get().createDivElement();
		span.setInnerHTML(html);

		ElementToCode etc = new ElementToCode(codeRepo, runner);
		try {

			etc.doit(span, null);
			return etc.ret;

		} catch (RunException e) {
			Sistem.printStackTrace(e, "While Element To Code: Html:"+html);
			throw new RuntimeException(e);
		}
	};

//	public static native String getHtml(String id)/*-{
//		var editor = $wnd.CKEDITOR.instances[id];
//		return editor.getData();
//	}-*/;

}
