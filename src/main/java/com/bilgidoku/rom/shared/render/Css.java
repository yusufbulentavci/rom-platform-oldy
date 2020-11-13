package com.bilgidoku.rom.shared.render;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Runner;
import com.bilgidoku.rom.shared.code.Animation;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.json.JsonTransfer;
import com.bilgidoku.rom.shared.json.JsonUtil;

public class Css implements JsonTransfer {
	public static final String ROM_TAG_STYLE = "romts";
	private static int designId = 0;

	private final Set<String> tagStyles = new HashSet<String>();

	private final StringBuilder cssBuilder = new StringBuilder();
	private StringBuilder baseCss = new StringBuilder();
	private final Set<String> googleFonts = new HashSet<String>();

	public Css(JSONArray cssArrayCommon, JSONArray cssArray, Runner templating) throws RunException {
		addBaseCss(new JSONArray[] { cssArrayCommon, cssArray }, templating);
	}

	public Css(JSONValue v) throws RunException {
		JSONObject stored = v.isObject();
		JSONArray a = stored.getArray("s");
		for (int i = 0; i < a.size(); i++) {
			tagStyles.add(a.get(i).isString().stringValue());
		}
		
		JSONArray f = stored.getArray("f");
		for (int i = 0; i < f.size(); i++) {
			googleFonts.add(f.get(i).isString().stringValue());
		}
	}

	@Override
	public JSONValue store() throws RunException {
		JSONObject ret = new JSONObject();
		ret.put("s", JsonUtil.storeSet(tagStyles));
		ret.put("f", JsonUtil.storeSet(googleFonts));

		return ret;
	}

	public static final String[] SAFE_FONTS = { "Arial,Helvetica,sans-serif", "Arial Narrow,Nimbus Sans L,sans-serif",
			"Arial Black,Gadget,sans-serif", "Bookman Old Style,serif",
			"Cambria,Palatino Linotype,Book Antiqua,URW Palladio L,serif", "Century Gothic,sans-serif",
			"Comic Sans MS,cursive,sans-serif", "Constantina,Georgia,Nimbus Roman No9 L,serif", "Courier,monospace",
			"Courier New,Courier,monospace", "Garamond,serif", "Georgia,serif",
			"Helvetica,Arial,DejaVu Sans,Liberation Sans,Freesans,sans-serif", "Impact,Charcoal,sans-serif",
			"Lucida Console,Monaco,monospace", "Lucida Sans Unicode,Lucida Grande,sans-serif",
			"MS Sans Serif,Geneva,sans-serif", "Palatino Linotype,Book Antiqua,Palatino,serif", "Symbol,sans-serif",
			"Tahoma,Geneva,sans-serif", "Times New Roman,Times,serif", "Trebuchet MS,Helvetica,sans-serif",
			"Verdana,Geneva,sans-serif", "Webdings,sans-serif", "Wingdings,Zapf Dingbats,sans-serif" };

	public void useFont(JSONValue jsonValue) {
		if (jsonValue == null)
			return;
		if (jsonValue.isString() == null) {
			return;
		}
		useFont(jsonValue.isString().stringValue());
	}

	public void useFont(String fontFamily) {
		if (fontFamily.indexOf(",") <= 0)
			googleFonts.add(fontFamily);
	}

	private void addBaseCss(JSONArray[] twoCss, Runner templating) throws RunException {
		Map<String, List<String>> props = toMap(twoCss, templating);

		for (Entry<String, List<String>> se : props.entrySet()) {
			baseCss.append(se.getKey());
			baseCss.append("{");
			baseCss.append("\n");
			for (String il : se.getValue()) {
				baseCss.append(il);
				baseCss.append("\n");
			}
			baseCss.append("}");
			baseCss.append("\n");
		}
	}

	public static Map<String, List<String>> toMap(JSONArray[] twoCss, Runner templating) throws RunException {
		Map<String, List<String>> props = new HashMap<String, List<String>>();
		for (JSONArray cssArray : twoCss) {
			for (int i = 0; i < cssArray.size(); i++) {
				JSONObject item = cssArray.get(i).isObject();

				StringBuilder nameBuilder = new StringBuilder();
				JSONValue n = item.opt("widget");
				if (n != null) {
					if (!n.isString().stringValue().equals("defaults")) {
						nameBuilder.append(".");
						nameBuilder.append(n.isString().stringValue());
						nameBuilder.append(" ");
					}
				}

				n = item.opt("tag");
				if (n != null) {
					nameBuilder.append(n.isString().stringValue());
				}
				n = item.opt("pseclass");
				if (n != null) {
					String pse = n.isString().stringValue();
					if (!pse.equals("defaultstyle")) {
						nameBuilder.append(":");
						nameBuilder.append(n.isString().stringValue());
					}
				}

				String name = nameBuilder.toString();
				List<String> ps = props.get(name);
				if (ps == null) {
					ps = new LinkedList<String>();
				}
				n = item.opt("property");
				JSONValue r = item.opt("value");
				if (n == null || r == null)
					continue;

				String value = r.isString().stringValue();
				try {
					value = templating.evaluateText(value);
				} catch (ClassCastException cce) {
					throw new RuntimeException("Class cast for exp:" + value);
				}

				ps.add(n.isString().stringValue() + ":" + value + ";");

				props.put(nameBuilder.toString(), ps);
			}
		}
		return props;
	}

	public synchronized String addStyleClass(String mytag, Map<String, Map<String, String>> style, Animation animation)
			throws RunException {

		if (Portable.one.codeEditor() != null && Portable.one.codeEditor().inPreviewMode()) {
			return defaultStyleToStr(style);
		}

		if (animation == null && style.size() == 1 && style.get("defaultstyle") != null) {
			return defaultStyleToStr(style);
		}

		String cls = ROM_TAG_STYLE + (designId++);
		// if (!tagStyles.contains(cls)) {
		addStyleToTagStyle(cls, mytag, style, animation);
		tagStyles.add(cls);
		// }
		return cls;
	}

	public static String defaultStyleToStr(Map<String, Map<String, String>> style) throws RunException {
		StringBuilder sb = new StringBuilder();
		addStyleText(sb, style.get("defaultstyle"));
		return sb.toString();
	}

	private static void addStyleText(StringBuilder sb, Map<String, String> item) throws RunException {
		if (item == null)
			return;
		for (String innerKey : item.keySet()) {
			String innerValue = item.get(innerKey);
			sb.append(innerKey);
			sb.append(':');
			sb.append(innerValue);
			sb.append(";");
		}
	}

	private void addStyleToTagStyle(String cls, String mytag, Map<String, Map<String, String>> style,
			Animation animation) throws RunException {
		if (animation != null) {
			addAnimation(cssBuilder, cls, animation.keyFrames);
		}
		for (String key : style.keySet()) {
			Map<String, String> value = style.get(key);
			cssBuilder.append(mytag);
			cssBuilder.append('.');
			cssBuilder.append(cls);
			boolean defaultstyle = true;
			if (!key.equals("defaultstyle")) {
				cssBuilder.append(':');
				cssBuilder.append(key);
				defaultstyle = false;
			}
			cssBuilder.append(" {\n");
			addStyleText(cssBuilder, value);
			if (defaultstyle && animation != null) {
				cssBuilder.append("-webkit-animation-name:").append(cls).append(";\n");
				cssBuilder.append("animation-name:").append(cls).append(";\n");

				cssBuilder.append("-webkit-animation-duration:").append(animation.duration).append("ms;\n");
				cssBuilder.append("animation-duration:").append(animation.duration).append("ms;\n");

				if (animation.delay != null) {
					cssBuilder.append("-webkit-animation-delay:").append(animation.delay).append("ms;\n");
					cssBuilder.append("animation-delay:").append(animation.delay).append("ms;\n");
				}

				if (animation.direction != null) {
					cssBuilder.append("-webkit-animation-direction:").append(animation.direction).append(";\n");
					cssBuilder.append("animation-direction:").append(animation.direction).append(";\n");
				}

				if (animation.iterationCount != null) {
					cssBuilder.append("-webkit-animation-iteration-count:").append(animation.iterationCount)
							.append(";\n");
					cssBuilder.append("animation-iteration-count:").append(animation.iterationCount).append(";\n");
				}

				if (animation.fillMode != null) {
					cssBuilder.append("-webkit-animation-fill-mode:").append(animation.fillMode).append(";\n");
					cssBuilder.append("animation-fill-mode:").append(animation.fillMode).append(";\n");
				}

			}
			cssBuilder.append("\n }\n");
		}

	}

	private void addAnimation(StringBuilder sbm, String cls, Map<String, Map<String, String>> tmap) throws RunException {

		StringBuilder sb = new StringBuilder();
		for (Entry<String, Map<String, String>> it : tmap.entrySet()) {
			sb.append(it.getKey()).append(" {");
			addStyleText(sb, it.getValue());
			sb.append("}\n");
		}

		sbm.append("\n@-webkit-keyframes ").append(cls).append(" {\n").append(sb.toString()).append("}\n");
		sbm.append("\n@keyframes ").append(cls).append(" {\n").append(sb.toString()).append("}\n");
	}

	public String cssText() {
		return baseCss.toString() + "\n" + cssBuilder.toString();
	}

}
