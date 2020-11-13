package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.expr.MyLiteral;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONBoolean;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.request.JsonResponse;
import com.bilgidoku.rom.shared.xml.Elem;

public class LoadCommand extends Command {

	public LoadCommand() {
		super("c:load");
	}

	@Override
	public Tag getDef() {
		Att var = new AttImpl("var", false, Att.STRING, emptylist, 1, "");
		Att uri = new AttImpl("uri", false, Att.STRING, emptylist, 1, "");
		Att arr = new AttImpl("arr", false, Att.STRING, emptylist, 1, "");

		return new TagImpl("c:load", false, "cmd", new Att[] { var, uri, arr }, emptylist, null, "");
	}

	@Override
	public void execute(final Elem curElem, final RenderCallState rcs) throws RunException {
		String var = rcs.getParamStr("var", true, "_load");
		String href = rcs.evaluateParam("uri", true, null);

		if (href == null || href.isEmpty()) {
			MyLiteral arr = rcs.evaluateLiteral("arr", false, new MyLiteral());
			JSONArray set=new JSONArray();
			JSONArray a=arr.getArray();
			if(a!=null){
				for(int i=0; i<a.size(); i++){
					String iuri=a.get(i).isString().stringValue();
					final JsonResponse obj = loadItem(rcs, iuri);
					if(obj.obj!=null)
						set.add(obj.obj);					
				}
			}
			rcs.setVariable(var, set);
			rcs.setVariable("_loadok", JSONBoolean.getInstance(true));
		} else {
			// if it is a writing container, get writing uri
			final JsonResponse obj = loadItem(rcs, href);

			if (!obj.succ) {
				rcs.setVariable("_loadok", JSONBoolean.getInstance(false));
				rcs.setVariable("_loaderr", new JSONString(obj.errText));
				return;
			}
			rcs.setVariable(var, obj.obj);
			rcs.setVariable("_loadok", JSONBoolean.getInstance(true));
			return;
		}

	}

	protected JsonResponse loadItem(final RenderCallState rcs, String href) throws RunException {
		if (href.startsWith("/_/writings")) {
			href = href.replace("/_/writings", "");
			if (href.isEmpty())
				href = "/";
		}

		final JsonResponse obj = new JsonResponse(rcs.getRequest().getReqLang(), rcs.getRequest().getPostman());
		obj.load(href);
		return obj;
	}
}