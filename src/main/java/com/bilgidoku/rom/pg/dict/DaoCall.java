package com.bilgidoku.rom.pg.dict;

import java.lang.reflect.Method;

import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.shared.err.SecurityError;
import com.bilgidoku.rom.shared.err.KnownError;

public abstract class DaoCall {

	public abstract Object call(CallInteraction request) throws KnownError, NotInlineMethodException, SecurityError, ParameterError;
	
	protected Method getMethod(Object ins, String string){
		try {
			Method[] m;
			Class c;
			if(ins instanceof Class) {
				c=(Class) ins;
			}else {
				c=ins.getClass();
			}
			m = c.getMethods();
			for (Method method : m) {
				if(method.getName().equals(string)){
					return method;
				}
			}
			throw new RuntimeException("Method not found:"+string);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected BeforeHook getBeforeHook(String string) {
		try {
			BeforeHook bh=(BeforeHook) this.getClass().getClassLoader().loadClass(string).newInstance();
			if(!bh.getStarted())
				bh.start();
			return bh;
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	protected AfterHook getAfterHook(String string){
		try {
			AfterHook af=(AfterHook) this.getClass().getClassLoader().loadClass(string).newInstance();
			if(!af.getStarted())
				af.start();
			return af;
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	public abstract String getDbfsOwner(CallInteraction request);

	public Long getCachePeriod(){
		return null;
	}
}
