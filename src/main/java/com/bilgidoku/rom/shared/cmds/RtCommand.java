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
import com.bilgidoku.rom.shared.xml.Elem;


public class RtCommand extends Command {

	public RtCommand() {
		super("c:rt");
	}

	@Override
	public void execute(final Elem curElem, final RenderCallState rcs) throws RunException {
		
		
		MyLiteral cmds=rcs.evaluateLiteral("cmds", true, null);
		if(cmds==null)
			return;
		
		MyLiteral cid=rcs.evaluateLiteral("cid", true, null);
		if(cid==null)
			cid=new MyLiteral("no");
		
		
		JSONArray ja=cmds.getArray();
		if(ja==null || ja.size()==0)
			return;
		
		ja.add(curElem.ensureId());
		ja.add(cid.getString());
		
		rcs.processRt(ja);
		
	
	}
	
	@Override
	public Tag getDef() {
		Att cmds = new AttImpl("cmds", false, Att.ARRAY, emptylist, 1, "Run array of rt comments");

		return new TagImpl("c:rt", false, "cmd", new Att[] { cmds}, emptylist, null, "");
	}
}
