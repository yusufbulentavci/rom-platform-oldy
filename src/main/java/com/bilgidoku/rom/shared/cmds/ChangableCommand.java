package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.xml.Doc;
import com.bilgidoku.rom.shared.xml.Elem;

public class ChangableCommand extends Command {

	public ChangableCommand() {
		super("c:changeable");
	}

	@Override
	public void execute(Elem curElem, RenderCallState rcs) throws RunException {
		// if(change!=null){
		// throw new RunException("Nested changeable");
		// }
		Elem gn = Doc.appendGroupingNode(curElem);
		String htmlId = ensureId(gn);
		String routine = rcs.getParamStr("then", false, null);
		String name = rcs.getParamStr("name", true, null);
		String when = rcs.getParamStr("when", true, null);
		String goal = rcs.getParamStr("goal", true, null);
		String guardwhen = rcs.getParamStr("guardwhen", true, null);
		String guardgoal = rcs.getParamStr("guardgoal", true, null);

		Boolean appendHtml = rcs.getParamBool("append", true, false);
		Boolean scrollDown = rcs.getParamBool("scrolldown", true, null);

		
		
		String changeId = rcs.rz().addChangeable(routine, name, htmlId, appendHtml, when, goal, guardwhen, guardgoal, scrollDown);

		String likes = rcs.getParamStr("likes", true, null);
		if (likes != null || goal!=null || guardgoal!=null) {
			rcs.rz().addStateTrigger(changeId, likes, goal!=null||guardgoal!=null);
		}

//		if(name!=null && name.equals("arttir")){
//			name="arttir";
//		}
		
		Integer tickPeriod = rcs.evaluateInt("tickperiod", true, null);
//		Integer tickPeriod = rcs.getParamInt("tickperiod", true, null);
		if (tickPeriod != null && tickPeriod!= 0) {
			Integer tickDelay = rcs.getParamInt("tickdelay", true, 0);
			Integer tickTimes = rcs.getParamInt("ticktimes", true, tickTimes=Integer.MAX_VALUE);
			rcs.rz().addTickTrigger(changeId, tickPeriod, tickDelay, tickTimes);
		}

		Boolean appLoaded = rcs.getParamBool("pageloaded", true, Boolean.FALSE);
		if (appLoaded) {
			rcs.rz().addPageLoadedTrigger(changeId);
		}

		String rtdlgvar = rcs.getParamStr("rtdlgvar", true, null);
		if (rtdlgvar!=null) {
			rcs.rz().addRtTrigger(changeId, rtdlgvar);
		}
		
		if (rcs.getParamBool("immediate", true, Boolean.TRUE)) {
			Boolean b=rcs.isToRunChange(null, changeId);
			if(b!=null && b){
//				rcs.walkChildren(gn);
//				Nuote.one().debug("changable:"+htmlId, routine);
				rcs.runRoutine(routine, gn);
			}
		}
	}

	@Override
	public Tag getDef() {
		Att name = new AttImpl("name", false, Att.STRING, emptylist, 1, "Name of changeable");
		Att append = new AttImpl("append", false, Att.BOOL, emptylist, 1, "True for append html else replace html");
		Att scrolldown = new AttImpl("scrolldown", false, Att.BOOL, emptylist, 1, "");
		
		
		Att guardwhen = new AttImpl("guardwhen", false, Att.STRING, emptylist, 1, "");
		Att guardgoal = new AttImpl("guardgoal", false, Att.STRING, emptylist, 1, "");
		
		Att dlgvar = new AttImpl("rtdlgvar", false, Att.STRING, emptylist, 1, "");
		
		Att when = new AttImpl("when", false, Att.STRING, emptylist, 1, "Change if this evaluates to true");
		Att goal = new AttImpl("goal", false, Att.STRING, emptylist, 1, "_goal variable should be equal to");
		Att likes = new AttImpl("likes", false, Att.STRING, emptylist, 1, "");

		Att tickPeriod = new AttImpl("tickperiod", false, Att.INT, emptylist, 1, "");
		Att tickDelay = new AttImpl("tickdelay", false, Att.INT, emptylist, 1, "");
		Att tickTimes = new AttImpl("ticktimes", false, Att.INT, emptylist, 1, "");

		Att pageloaded = new AttImpl("pageloaded", false, Att.BOOL, emptylist, 1, "");
		Att immediate = new AttImpl("immediate", false, Att.BOOL, emptylist, 1, "");
		
		Att rtn = new AttImpl("then", true, Att.STRING, emptylist, 1, "");

		return new TagImpl("c:changeable", false, "cmd", new Att[] { rtn, name, guardwhen, guardgoal, append, scrolldown, when, goal, likes, tickPeriod, tickDelay, tickTimes,
				pageloaded, dlgvar, immediate }, emptylist, null, "");
	}

}
