package net.sf.clipsrules.jni;

import java.util.List;

import com.bilgidoku.rom.pg.sembol.SembolGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;



public class AkilSembolNo implements UserFunction {



	@Override
	public PrimitiveValue evaluate(List<PrimitiveValue> arguments) {
		try {
			String symbol = arguments.get(0).getValue().toString();
			
			
			Integer ret=SembolGorevlisi.tek().check(symbol);
			return new IntegerValue(ret);
			
		} catch (KnownError e) {
			e.printStackTrace();
		}
		return null;
	}
}
