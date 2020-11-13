package com.bilgidoku.rom.pg.dict;

import java.util.List;

import com.bilgidoku.rom.pg.dict.types.TypeHolder;

public interface CGMethod {

	public String getNameJavaForm();
	public boolean getHasArgs();
	
	public TypeHolder getRetType();
	
	public boolean isBreed() ;
	
	public String getSchemaName();

	public String getTableName();
	
	public boolean getHasParams();
	

	public int getParamCount();

	public List<CGAtt> getParams();

	public boolean getFormPosting();
	public boolean getFormDeleting();
	
	public String getUriPostfix();

	public boolean isRetset();
	
	public String getBeforeStr();
	public String getAfterStr();
	public boolean isHook();
	
	public int getArgLen();
	
	public String getClassName();
	public String getJavaMethodName();
	public boolean isReturnsStream();
	public boolean isReturnsVoid();
	public List<CGAtt> getServerArgs();	
	
	public boolean isReturnPrimitive();
	public String getReturnMime();
	public boolean isReturnJust();	
	
	public String getPrefix();
	public String getUri();
	public String getCapTableName();
	
	public String getNet();
	public String getOne();
	public String getHsc();
	
	public String getRoleStr();
	
	public boolean isService();
	public String getAccesslevel();
	public int getCpu();
	
	public boolean getHasAuditParam();
	public boolean isRetFile();
}
