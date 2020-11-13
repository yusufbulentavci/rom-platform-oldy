package com.bilgidoku.rom.shared.expr;

import java.util.List;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.state.Scope;
import com.bilgidoku.rom.shared.expr.ExprRunner;

public class Evaluator {

	public static Integer evaluateInt(String value, Scope state) throws  RunException {
		if (value == null)
			return null;
		List<MyLiteral> ret = ExprRunner.build(value, state);
		if (ret.size() == 0 || (ret.size() == 1 && ret.get(0) == null))
			return null;
		MyLiteral l = ret.get(0);
		return l.getInt();
	}

	public static String evaluateText(String value, Scope state) throws RunException {
		if (value == null)
			return null;
		
		List<MyLiteral> ret = ExprRunner.build(value, state);
		if (ret.size() == 0 || (ret.size() == 1 && ret.get(0) == null))
			return "";
		StringBuilder sb = new StringBuilder();
		for (MyLiteral myLiteral : ret) {
			sb.append(myLiteral.toString());
		}
		return sb.toString();

	}

	public static MyLiteral evaluate(String value, Scope state) throws  RunException {
		if (value == null)
			return null;
		
		List<MyLiteral> ret = ExprRunner.build(value, state);
		if (ret == null)
			return null;

		if (ret.size() == 1) {
			return ret.get(0);
		}
		StringBuilder sb = new StringBuilder();
		for (MyLiteral myLiteral : ret) {
			sb.append(myLiteral.toString());
		}
		return new MyLiteral(sb.toString());

	}
	
	public static MyLiteral deferred(String value, Scope state) throws  RunException {
		if (value == null)
			return null;
		
		List<MyLiteral> ret = ExprRunner.build(value, state);
		if (ret == null)
			return null;

		if (ret.size() == 1) {
			return ret.get(0);
		}
		StringBuilder sb = new StringBuilder();
		for (MyLiteral myLiteral : ret) {
			sb.append(myLiteral.toString());
		}
		return new MyLiteral(sb.toString());

	}

	

	public static boolean evaluateCondition(String value, Scope state) throws RunException {
		if (value == null)
			return false;
		MyLiteral ret = evaluate(value, state);
		if(ret==null)
			return false;
		if(ret.isBool()){
			return ret.getBoolean();
		}
		if(ret.getNull()){
			return false;
		}
		return true;
	}
}
