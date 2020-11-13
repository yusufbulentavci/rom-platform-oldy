package com.bilgidoku.rom.shared.code;

import java.util.Map;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.RunZone;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.xml.Elem;

public abstract class Command{
	private static int idSeq = 0;
	protected static final String[] truefalse = new String[] { "true", "false" };
	protected static final String[] emptylist = new String[] {};

	public void setup() {
	}

	protected final String prefix;

	public String getCmdStr() {
		return prefix;
	}

	public Command(String prefix) {
		this.prefix = prefix;
	}

	public boolean isOk(final String waitTag) {
		return waitTag.startsWith(this.prefix);
	}

	public void endCommand(RunZone rz) {

	}
	
	public void exec(Elem curElem, RenderCallState rz) throws RunException {
//		Sistem.outln(rz.getCurTag());
		execute(curElem, rz);
	}
	protected abstract void execute(final Elem curElem, final RenderCallState rz) throws RunException; 

	
	public static String ensureId(Elem newNode) {
		String c = newNode.getAttribute("id");
		if (c == null) {
			String id = "ei" + System.currentTimeMillis()+"-"+(idSeq++);
			newNode.setAttribute("id", id);
			return id;
		}
		return c;
	}

//	public String assertHasId(Elem newNode, CommandCap rz) throws RunException {
//		String c = newNode.getAttribute("id");
//		if (c == null) {
//			throw new RunException("Element should have id but do not", rz.getStackTrace());
//		}
//		return c;
//	}

	public Map<String, Att> getParamDefs() {
		return this.getDef() != null ? this.getDef().getParamDefs() : null;
	}

	public Map<String, Att> getAtDefs() {
		return this.getDef().getAtDefs();
	}
	
	abstract public Tag getDef();

	
}
