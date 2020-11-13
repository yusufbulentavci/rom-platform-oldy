package com.bilgidoku.rom.shared.cmds.htmlevents;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.RunZone;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.TemplatingUtil;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.expr.MyLiteral;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONNull;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.xml.Elem;

public class HtmlEventCommand extends Command {

	private final String eventName;
	// mk=>mouse key
	private boolean mk;
	protected boolean hasValueAtt = false;

	public HtmlEventCommand(String eventName) {
		this(eventName, false);
	}

	public HtmlEventCommand(String eventName, boolean mk) {
		super("c:" + eventName);
		this.eventName = eventName;
		this.mk = mk;
	}

	@Override
	public void execute(final Elem curElem, RenderCallState rcs) throws RunException {
		if (Portable.one.codeEditor()!=null)
			return;

		// if(routine!=null){
		// String tag = curElem.getTag();
		// TemplatingUtil.assertThat(tag.indexOf(':') < 0, tag);
		// curElem.setAttribute(eventName, "rom.rr(\"" + rcs.getId() +
		// "\",event,\""+routine+");return false;");
		// return;
		// }

		this.registerEvent(rcs, curElem, eventName, mk);
	}

	@Override
	public Tag getDef() {
		Att rtn = new AttImpl("then", true, Att.STRING, emptylist, 1, "");
		Att name = new AttImpl("name", false, Att.STRING, emptylist, 1, "");
		Att goal = new AttImpl("goal", false, Att.STRING, emptylist, 1, "");
		Att var1 = new AttImpl("var1", false, Att.STRING, emptylist, 1, "");
		Att val1 = new AttImpl("val1", false, Att.STRING, emptylist, 1, "");
		Att var2 = new AttImpl("var2", false, Att.STRING, emptylist, 1, "");
		Att val2 = new AttImpl("val2", false, Att.STRING, emptylist, 1, "");

		return new TagImpl(this.prefix, false, "cmd", new Att[] { rtn, name, goal, var1, val1, var2, val2 }, emptylist,
				null, "");
	}

	protected void registerEvent(final RenderCallState rcs, final Elem curElem, final String eventName, final boolean mk)
			throws RunException {
		String name = rcs.getParamStr("name", true, null);

		// if(name==null){
		// Elem gn = Doc.appendGroupingNode(curElem);
		// String htmlId = ensureId(gn);
		// name = rcs.addChangeable(name, htmlId, false, "${true}", null);
		// return;
		// }

		JSONArray ja = new JSONArray();

		
		String routine = rcs.getParamStr("then", true, null);

		String goal = rcs.evaluateParam("goal", true, null);
		ja.set(0, goal == null ? JSONNull.getInstance() : new JSONString(goal));

		String var1 = rcs.getParamStr("var1", true, null);
		if (var1 == null) {
			ja.set(1, JSONNull.getInstance());
			ja.set(2, JSONNull.getInstance());
		} else {
			ja.set(1, new JSONString(var1));
			MyLiteral val1 = rcs.evaluateLiteral("val1", true, null);
			ja.set(2, val1.store());
		}

		String var2 = rcs.getParamStr("var2", true, null);
		if (var2 == null) {
			ja.set(3, JSONNull.getInstance());
			ja.set(4, JSONNull.getInstance());
		} else {
			ja.set(3, new JSONString(var2));
			MyLiteral val2 = rcs.evaluateLiteral("val2", true, null);
			ja.set(4, val2.store());
		}

		String tag = curElem.getTag();
		
		TemplatingUtil.assertThat(tag.indexOf(':') < 0, tag);
		if (mk) {
			curElem.setAttribute(eventName, "rom.mke(\"" + rcs.getId() + "\"," + addStrParam(name) + ","
					+ addStrParam(routine) + ",event," + ja.toString() + ");return false;");
		} else {
			curElem.setAttribute(eventName, "rom.je(\"" + rcs.getId() + "\"," + addStrParam(name) + ","
					+ addStrParam(routine) + ",event," + ja.toString() + "," + hasValue() + ");return false;");
		}

	}

	private String addStrParam(String name) {
		return (name == null) ? null : "\"" + name + "\"";
	}

	private String hasValue() {
		if (!hasValueAtt) {
			return "null";
		}
		return "this.value";
	}
}
