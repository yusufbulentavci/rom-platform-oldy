package com.bilgidoku.rom.js;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.shared.err.KnownError;

public class JsGorevlisi extends GorevliDir{
	public static final int NO=51;

	public static JsGorevlisi tek(){
		if(tek==null) {
			synchronized (JsGorevlisi.class) {
				if(tek==null) {
					tek=new JsGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static JsGorevlisi tek;
	
	ScriptEngine engine;
	private JsGorevlisi() {
		super("Js", NO);
		  engine = new ScriptEngineManager().getEngineByName("javascript");
		    Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
		    bindings.put("stdout", System.out);
	}
	
	public void eval(String code) throws KnownError {
		try {
			engine.eval(code);
		} catch (ScriptException e) {
			throw new KnownError("Js error", e);
		}
	}
	
//	public void dene() {
//		System.out.println("HEYYY");
//	}
//	
//	public static void main(String[] args) throws KnownError {
//		JsGorevlisi.tek().eval("com.bilgidoku.rom.js.JsGorevlisi.tek().dene();");
//	}
}
