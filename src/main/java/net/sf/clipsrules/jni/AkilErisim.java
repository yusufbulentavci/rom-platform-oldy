package net.sf.clipsrules.jni;

import java.util.List;

public interface AkilErisim {

	void addRouter(Router clipsRouter);

	public void addUserFunction(String functionName, String returnTypes, int minArgs, int maxArgs, String restrictions,
			UserFunction callback);

	FactAddressValue assertString(String string) throws CLIPSException;

	void loadFromString(String cmd) throws CLIPSLoadException;

	PrimitiveValue eval(String evalString) throws CLIPSException;

	void reset() throws CLIPSException;

	long run() throws CLIPSException;

	FactAddressValue findFact(String variable, String deftemplate, String condition) throws CLIPSException;

	void retainFact(FactAddressValue theFact);

	List<FactAddressValue> findAllFacts(String deftemplate) throws CLIPSException;

	List<FactInstance> getFactList();


}
