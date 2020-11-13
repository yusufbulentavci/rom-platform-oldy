package com.bilgidoku.rom.shared.cmds;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.AttImpl;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.AjaxForm;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.xml.Elem;


public class FieldValidateCommand extends Command {

	public FieldValidateCommand() {
		super("c:fieldvalidate");
	}

	@Override
	public void execute(final Elem curElem, final RenderCallState rcs) throws RunException {
		AjaxForm af = rcs.form;
		if(af==null)
			return;
		Elem inElem=(Elem)curElem;
		if(!inElem.getTag().equals("input") && !inElem.getTag().equals("select")){
			throw new RunException("Parent of fieldvalidate should be input or select but it is "+inElem.getTag(), rcs.getStackTrace());
		}
		String id=ensureId(inElem);
//		JSONObject conf = ch.getParamObj();
		af.validations.put(id, new JSONObject());
	}
	
	@Override
	public Tag getDef() {
		Att attCode = new AttImpl("code", false, 1, 
				new String[] {"no","latinalphanumeric","latinalpha","alphanumeric","alpha","integer","decimal","email","phoneno"}, 
				1, "");
		Att attFormat = new AttImpl("format", false, 1, 
				new String[] {"no","lowercase","uppercase","titlecase","sentencecase"}, 
				1, "");
		
		Att attNotEmpty = new AttImpl("notempty", false, 3, truefalse, 1, "");		
		Att attAcceptWS = new AttImpl("acceptws", false, 3, truefalse, 1, "");		
		Att attTrimWS = new AttImpl("trimws", false, 3, truefalse, 1, "");		
		
		Att attExtraChars = new AttImpl("extrachars", false, 1, emptylist, 1, "");
		
		Att attMinSize = new AttImpl("minsize", false, 0, emptylist, 1, "");		
		Att attMaxSize = new AttImpl("maxsize", false, 0, emptylist, 1, "");	
		
		return new TagImpl("c:fieldvalidate", false, "cmd", new Att[] {attCode,attFormat,attNotEmpty,
				attAcceptWS, attTrimWS, attExtraChars, attMinSize, attMaxSize}, emptylist, null, "");
	}
}
