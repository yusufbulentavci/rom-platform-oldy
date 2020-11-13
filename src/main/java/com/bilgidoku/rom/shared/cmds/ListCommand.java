package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.json.JSONBoolean;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.request.JsonResponse;
import com.bilgidoku.rom.shared.xml.Elem;

public class ListCommand extends Command {

	public ListCommand() {
		super("c:list");
	}

	@Override
	public Tag getDef() {
		Att var = new AttImpl("var", false, Att.STRING, emptylist, 1, "");
		Att list = new AttImpl("list", false, Att.STRING, emptylist, 1, "");

		return new TagImpl("c:list", false, "cmd", new Att[] { var, list}, emptylist, null, "");
	}
	
	@Override
	public void execute(final Elem curElem, final RenderCallState rcs) throws RunException {
		String var = rcs.getParamStr("var", true, "_load");
		String href = rcs.evaluateParam("list", false, null);
		
		if(href.startsWith("/_/lists") || href.startsWith("/_/links")){
			href+="/content_list.rom";
		}else{
//			href = rcs.evaluateParam("container", false, null);
//			if(href==null)
//				return;
			if(!href.startsWith("/_") && !href.startsWith("/f")){
				//get writing container
				if(href.length()==0 || href.equals("/") || href.equals("/home")){
					href="/_/writings/home";
				}else{
					href="/_/writings"+href;
				}
			}
		}
		
		final JsonResponse obj=new JsonResponse(rcs.getRequest().getReqLang(), rcs.getRequest().getPostman());
		obj.load(href);
		if (!obj.succ) {
			rcs.setVariable("_loadok", JSONBoolean.getInstance(false));
			rcs.setVariable("_loaderr", new JSONString(obj.errText));
			return;
		}

		rcs.setVariable(var, obj.obj);
		rcs.setVariable("_loadok", JSONBoolean.getInstance(true));
	}
}