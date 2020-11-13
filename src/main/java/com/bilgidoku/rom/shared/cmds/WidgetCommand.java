package com.bilgidoku.rom.shared.cmds;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.code.Wgt;
import com.bilgidoku.rom.shared.expr.MyLiteral;
import com.bilgidoku.rom.shared.xml.Doc;
import com.bilgidoku.rom.shared.xml.Elem;

public class WidgetCommand extends Command {
	public WidgetCommand() {
		super("w:");
	}

	@Override
	public void execute(Elem curElem, final RenderCallState rcs) throws RunException {
		preview(curElem, rcs);
	}

	public static void preview(Elem curElem, final RenderCallState rcs) throws RunException {
		Wgt wg = rcs.getWidget();
		if(wg==null){
			Sistem.errln("Widget not found:"+rcs.getCurTag());
			return;
		}
			
		String view = "";
		String cssClass = "";
		// String ct = rcs.getCurTag();
		try {

			/*
			 * Preview mode'ta değilse ve ownZone değilse
			 */
			if (!(Portable.one.codeEditor() != null && Portable.one.codeEditor().inPreviewMode()) && wg.ownZone
					&& !rcs.amitop()) {
				Elem runzonenode = Doc.appendGroupingNode(curElem);
				String htmlId = ensureId(runzonenode);
				rcs.newRunZone(htmlId, runzonenode);
				return;
			}

			Att[] paramdefs = wg.getParams();

			for (Att att : paramdefs) {
				// JSONObject attdef=attdefs.get(key).isObject();

				if (att.isDeclare()) {
					MyLiteral value = rcs.evaluate(att.getDefvalue(), att.getType());
					rcs.setVariable(att.getNamed(), value);
				} else {
					MyLiteral val = rcs.evaluateLiteral(att.getNamed(), true, null);
					if (val == null || val.empty()) {
						String s = att.getDefvalue();
						if (s != null) {
							val = rcs.evaluate(s, att.getType());
						}
						if (val == null || val.empty()) {
							if (att.isReq())
								throw new RunException("Required parameter not set for key:" + att.getNamed());
						}
					}
					if (val != null && val.type != att.getType()) {
						val = val.cast(att.getType());
					}
					if (att.getNamed().equals("_view")) {
						view = val.getString();
					}
					try {
						rcs.setVariable(att.getNamed(), val);
					} catch (Exception e) {
						throw new RunException("Failed to add param to scope/ value:" + val, rcs.getStackTrace())
								.attribute(att.getNamed());
					}
				}
			}

			Elem gn = Doc.createGroupingNode();

			TagCommand.widgetCommon(gn, rcs);

			cssClass = "w" + rcs.getCurTag().substring(2) + view;
			gn.appendForAttribute("class", cssClass);
			curElem.appendChild(gn);

			rcs.walk(wg.getCodes(), gn);

		} catch (Exception e) {

			Sistem.printStackTrace(e, "Preview widget");

			Elem gn = Doc.createGroupingNode();
			gn.appendForAttribute("class", cssClass);
			if (!(Portable.one.codeEditor() != null && Portable.one.codeEditor().inPreviewMode()))
				gn.appendForAttributeStyle("display:none");
			gn.setNodeValue(e.getMessage());

			curElem.appendChild(gn);

			// throw new
			// RunException("Widget:"+ct,e,rcs.getStackTrace()).widget(ct);
			// throw new RunException("Widget:",e);
		}

	}

	@Override
	public Tag getDef() {
		return null;
	}

}
