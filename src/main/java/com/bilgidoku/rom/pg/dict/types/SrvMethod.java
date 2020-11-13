package com.bilgidoku.rom.pg.dict.types;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.ilk.util.SiteUtil;
import com.bilgidoku.rom.pg.dict.CGAtt;
import com.bilgidoku.rom.pg.dict.CGMethod;
import com.bilgidoku.rom.pg.dict.Net;

public class SrvMethod implements CGMethod{
	public String servuri;
	public String name;
	public String http;
	public List<SrvAtt> args=new ArrayList<SrvAtt>();
	public TypeHolder retTypeHolder;
	private final Method javaMethod;
	public Net net;
	public boolean hasFileArg=false;
	private boolean returnsStream;
	public String contenttype=null;
	private final String className;
	private final String methodName;
	public final String serverName;
	public String[] roles;
	private String[] auditParams=null;
	public int cpu=100;
	
	private boolean retFile=false;

	public SrvMethod(String className, String methodName, Method method, String serverName) {
		this.javaMethod=method;
		this.className=className;
		this.methodName=methodName;
		this.serverName=serverName;
	}

	public String getHttpUp(){
		return http.toUpperCase();
	}

	public boolean getFormPosting() {
		return this.http.equalsIgnoreCase("post");
	}

	public int getParamCount(){
		return getParams().size();
	}
	public boolean getHasParams(){
		return getParams().size()>0;
	}
	
	public List<CGAtt> getParams() {
		List<CGAtt> params = new ArrayList<CGAtt>();
		for (SrvAtt arg : args) {
			if (!arg.name.startsWith("p_")) {
				continue;
			}
			String cut = arg.normalName();
			SrvAtt np=arg.clone();
			np.name=cut;
			params.add(np);
		}

		return params;
	}
	
	public List<CGAtt> getServerArgs() {
		List<CGAtt> params = new ArrayList<CGAtt>();
		for (SrvAtt arg : args) {
			params.add(arg);
		}

		return params;
	}
	
	public String getServuri() {
		return servuri;
	}

	public String getName() {
		return name;
	}

	public String getHttp() {
		return http;
	}

	public List<SrvAtt> getArgs() {
		return args;
	}

	public boolean isRetset() {
		return false;
	}

	public String getRetJavaType() {
		return retTypeHolder.getJavaType();
	}
	
	public String getRetJavaResponseType() {
		return retTypeHolder.getJavaResponseType();
	}

	public void addArg(SrvAtt att) {
		this.args.add(att);
	}

	public void setRetJavaType(Class returnType) {
		
	}

	public Method getJavaMethod() {
		return javaMethod;
	}

	@Override
	public String getNameJavaForm() {
		return name;
	}

	@Override
	public boolean getHasArgs() {
		return this.getArgs().size() != 0;
	}

	@Override
	public TypeHolder getRetType() {
//		if(this.retTypeHolder==null){
////			syso("");
//		}
		return this.retTypeHolder;
	}

	@Override
	public boolean isBreed() {
		return false;
	}

	@Override
	public String getSchemaName() {
		return "service";
	}

	@Override
	public String getTableName() {
		return className.substring(className.lastIndexOf('.')+1);
	}

	@Override
	public boolean getFormDeleting() {
		return false;
	}

	@Override
	public String getUriPostfix() {
		return null;
	}

	public boolean isReturnsStream() {
		return returnsStream;
	}

	public void enableReturnsStream() {
		this.hasFileArg=true;
		this.returnsStream = true;
	}

	@Override
	public String getBeforeStr() {
		return null;
	}

	@Override
	public String getAfterStr() {
		return null;
	}

	@Override
	public boolean isHook() {
		return false;
	}

	@Override
	public int getArgLen() {
		return args.size();
	}

	@Override
	public String getClassName() {
		return className;
	}

	@Override
	public String getJavaMethodName() {
		return methodName;
	}

	@Override
	public boolean isReturnsVoid() {
		return false;
	}

	@Override
	public boolean isReturnPrimitive() {
		return this.retTypeHolder.isPrimitive();
	}

	@Override
	public String getUri() {
		return servuri;
	}

	@Override
	public String getCapTableName() {
		return SiteUtil.capitalize(serverName);
	}

	@Override
	public String getNet() {
		return net.name();
	}

	@Override
	public String getOne() {
		return null;
	}

	@Override
	public String getHsc() {
		return null;
	}

	@Override
	public String getPrefix() {
		return null;
	}

	@Override
	public String getRoleStr() {
		return "";
	}

	@Override
	public boolean isService() {
		return true;
	}
	
	public void setAudits(String[] as) {
		this.auditParams=as;
	}
	
	public boolean getAudit(){
		return auditParams!=null;
	}
	
	public String[] getAuditparams(){
		return auditParams;
	}
	
	public String getAuditname(){
		return this.servuri+"."+this.name;
	}
	
	public String getAuditparamnames(){
		StringBuilder sb=new StringBuilder();
		for(int i=0; i<auditParams.length; i++){
			if(i!=0)
				sb.append(",");
			sb.append('"');
			sb.append(auditParams[i].substring(3));
			sb.append('"');
		}
		return sb.toString();
	}

	@Override
	public String getAccesslevel() {
		throw new RuntimeException("Access level not to be called from service");
	}

	@Override
	public int getCpu() {
		return cpu;
	}

	@Override
	public boolean getHasAuditParam() {
		return this.auditParams!=null && this.auditParams.length>0;
	}

	@Override
	public String getReturnMime() {
		return contenttype;
	}

	@Override
	public boolean isReturnJust() {
		return contenttype != null && !contenttype.equals("text/html; charset=UTF-8");
	}

	public boolean isRetFile() {
		return retFile;
	}

	public void setRetFile(boolean retFile) {
		this.retFile = retFile;
	}
}
