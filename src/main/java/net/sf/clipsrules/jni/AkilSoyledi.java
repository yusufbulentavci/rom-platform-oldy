package net.sf.clipsrules.jni;

import java.util.List;

import com.bilgidoku.rom.session.OturumGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.http.session.AppSessionExtension;
import com.bilgidoku.rom.web.sessionfuncs.rtcmds.PushCommand;



public class AkilSoyledi implements UserFunction {

	final AkilExecDbOp op;
	private AppSessionExtension sessionService;

	public AkilSoyledi() {
		this.op = new AkilExecDbOp();
	}
	
	public AppSessionExtension se() {
		if(sessionService==null)
			sessionService = (AppSessionExtension) OturumGorevlisi.tek().getExtension(AppSessionExtension.APP);
		return sessionService;
	}

	@Override
	public PrimitiveValue evaluate(List<PrimitiveValue> arguments) {
		try {
			if (arguments.size() < 3)
				return null;
			String eylem = arguments.get(0).getValue().toString();

			if (!arguments.get(1).isNumber()) {
				System.err.println("Hostid should be number");
				return null;
			}
			long hi = (long) arguments.get(1).getValue();
			
			if (!arguments.get(2).isNumber()) {
				System.err.println("Cint should be number");
				return null;
			}
			long cint = (long) arguments.get(2).getValue();


			PushCommand c = new PushCommand("*akil.soyledi");
//			String yazi = arguments.get(3).getValue().toString();
//			String soru = arguments.get(4).getValue().toString();
//			String tip = arguments.get(5).getValue().toString();
//			String regex = arguments.get(6).getValue().toString();
//			
			
			AkilJsonFactTransform.factToJson(c.getJo(), eylem, arguments);
			se().push((int)hi, "/_/co/" + cint, c);

		} catch (KnownError e) {
			e.printStackTrace();
		}
		return null;
	}
}
