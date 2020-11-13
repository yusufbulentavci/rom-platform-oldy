package net.sf.clipsrules.jni;

import java.util.List;

import com.bilgidoku.rom.shared.err.KnownError;


public class AkilDeleteFunction implements UserFunction {

	final AkilErisim env;
	final AkilExecDbOp op;

	public AkilDeleteFunction(AkilErisim env) {
		this.env = env;
		this.op = new AkilExecDbOp();
	}

	@Override
	public PrimitiveValue evaluate(List<PrimitiveValue> arguments) {
		try {
			System.out.println("-------------------------");
			delete(arguments);
//			StringBuilder sb = getFact(id,arguments);
//			System.out.println(sb.toString());
//			env.assertString(sb.toString());
//			System.out.println("-------------------------");

		} catch ( KnownError e) {
			e.printStackTrace();
		}
		return null;
	}

	private void delete(List<PrimitiveValue> arguments) throws KnownError {
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ");
		sb.append(arguments.get(0).getValue().toString());
		sb.append(" where id='");
		sb.append(arguments.get(1).getValue().toString());
		sb.append("'");
		op.doit(sb.toString());
	}

//	private StringBuilder getFact(Long id, List<PrimitiveValue> arguments) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("(");
//		sb.append(arguments.get(0).getValue().toString());
//		sb.append(" (id ");
//		sb.append(id);
//		sb.append(") ");
//		for (int i = 1; i < arguments.size(); i = i + 2) {
//			sb.append("(");
//			sb.append(arguments.get(i).getValue().toString());
//			sb.append(" ");
//			sb.append(arguments.get(i + 1).getValue());
//			sb.append(")");
//			sb.append(" ");
//		}
//		sb.append(")");
//		return sb;
//	}

}
