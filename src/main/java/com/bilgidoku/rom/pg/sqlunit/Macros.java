package com.bilgidoku.rom.pg.sqlunit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.bilgidoku.rom.ilk.file.FromResource;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.min.Sistem;

/**
 * 
 * Todo: Macro'lari stringtemplate yapmali bir director'de kendi ile ayni isimde
 * dosyalarda durmalilar
 * 
 * Todo: Dispatchresource icin macro escaping gerekiyor Su an dispatch resource
 * ic ice macro kullanilamiyor
 * 
 * Todo: Macro'lar diger statement'lardan ayrilsa kod testi kolaylasir
 * 
 * @author avci
 *
 */
public class Macros {
	private final String MACROUSE_BEGIN = ">!\\{";
	private final String MACROUSE_END = "}!<";

	private Map<String, SuMacro> macros = new HashMap<String, SuMacro>();
	private Map<String, String> tableId = new HashMap<String, String>();

	public Macros() {

		try {
			tableId.put("files", "site.files");
//			tableId.put("writings", "site.writings");
//			JSONObject rp = FromResource.loadJsonObject("META-INF/rom-resource-prefix");
//			Iterator<String> its = rp.keys();
//			while (its.hasNext()) {
//				String k = its.next();
//				// container'lar bu listede yok
//				if (k.equals("/_/"))
//					continue;
//				if (k.startsWith("/_/")) {
//					tableId.put(k.substring(3), rp.getString(k));
//				}
//			}
		} catch (Exception e) {
			throw new RuntimeException("Couldnt load;/META-INF/rom-resource-prefix", e);
		}
	}

	public String replaceMacro(String s) {
		if (s == null)
			return null;

		for (int deep = 0; deep < 20; deep++) {
			String[] parts = s.split(MACROUSE_BEGIN);
			if (parts.length == 1) {
				break;
			}
			StringBuilder sb = new StringBuilder();
			sb.append(parts[0]);
			for (int i = 1; i < parts.length; i++) {
				String string = parts[i];
				int ind = string.indexOf(MACROUSE_END);
				if (ind < 0) {
					Sistem.errln("Macro definition failed, no macro end:" + s);
					sb.append(string);
					continue;
				}
				String macroUse = string.substring(0, ind).trim();
				String macroName = macroUse;
				String macroArgs = null;
				int endTitle = macroUse.indexOf(' ');
				if (endTitle > 0) {
					macroName = macroUse.substring(0, endTitle).trim();
					macroArgs = macroUse.substring(endTitle + 1);
				}

				if (macroName.equals("dispatchresource")) {
					String dispStr = dispatchResource(macroArgs);
					// com.bilgidoku.rom.gunluk.Sistem.outln("BEFORE");
					// com.bilgidoku.rom.gunluk.Sistem.outln(dispStr);
					// com.bilgidoku.rom.gunluk.Sistem.outln("AFTER");
					// dispStr=replaceMacro(dispStr);
					// com.bilgidoku.rom.gunluk.Sistem.outln(dispStr);
					sb.append(dispStr);
				} else {
					SuMacro macro = macros.get(macroName);
					if (macro == null) {
						Sistem.errln("Undefined macro:" + macroName);
						sb.append(string);
						continue;
					}
					String macroDef = macro.toText;
					if (endTitle > 0) {
						macroName = macroUse.substring(0, endTitle).trim();
						String[] rep = macroArgs.split("\t");
						for (int j = 0; j < rep.length; j = j + 2) {

							if (rep[j].equals("_addprefix")) {
								try {
									macroDef = rep[j + 1]  + macroDef;
									macroDef = macroDef.replaceAll(",", ","+rep[j + 1]);

								} catch (Exception e) {
									Sistem.printStackTrace(e);
								}
								continue;
							}

							try {
								macroDef = macroDef.replaceAll(rep[j], rep[j + 1]);
							} catch (Exception e) {
								Sistem.printStackTrace(e);
							}
						}
					}
					sb.append(macroDef);
				}
				if (string.length() > ind + MACROUSE_END.length()) {
					sb.append(string.substring(ind + MACROUSE_END.length()));
				}
			}
			s = sb.toString();
		}
		return s;
	}

	private String dispatchResource(String macroArgs) {
		StringBuilder sb = new StringBuilder();

		sb.append("		if a_iscont then\n");
		String kc = macroArgs.replaceAll("rom.resources", "rom.containers");
		sb.append(kc);
		sb.append("\n");
		sb.append("		else\n");
		sb.append("			select rom.tabledispatcher(a_self) into v_d;\n");
		sb.append("			case v_d\n");
		for (Entry<String, String> e : tableId.entrySet()) {
			String k = macroArgs.replaceAll("rom.resources", e.getValue());
			sb.append("		when '").append(e.getKey()).append("' then\n");
			sb.append("					").append(k).append("\n");
		}
		sb.append("				else\n");
		sb.append("					raise exception 'Unexpected table dispatcher:% in %',v_d,a_self;\n");
		sb.append("				end case;");
		sb.append("		end if;\n");
		return sb.toString();
	}

	public void addMacro(String macroTitle, SuMacro suMacro) {
		this.macros.put(macroTitle, suMacro);
	}

}
