package com.bilgidoku.rom.shared;



public class RunException extends Exception{
	
//	Stack<String> reason=new Stack<String>();
	
	String attribute;
	String expr;
	String widget;
	String codeTrace;
	
	
	public RunException(String string, Exception e) {
		super(string,e);
	}
	
	public RunException(String string, Exception e, String stackTrace) {
		super(string,e);
		this.codeTrace=stackTrace;
	}

	public RunException(String string, String stackTrace) {
		super(string);
		this.codeTrace=stackTrace;
	}
	
	public RunException(String string) {
		super(string);
	}


//	public RunException(Exception e, String stackTrace) {
//		super(e);
//	}
	
//	public RunException reason(String r){
//		reason.add(r);
//		return this;
//	}
	
//	public RunException(String string, Exception e, String stackTrace) {
//		// TODO Auto-generated constructor stub
//	}

	public RunException attribute(String r){
		this.attribute=r;
		return this;
	}
	
	public RunException expr(String r){
		this.expr=r;
		return this;
	}

	public RunException widget(String ct) {
		this.widget=ct;
		return this;
	}
	
	public String toString(){
		StringBuilder sb=new StringBuilder();
		if(this.getMessage()!=null){
			sb.append("msg:");
			sb.append(this.getMessage());
			sb.append(" ");
		}
		if(widget!=null){
			sb.append("widget:");
			sb.append(widget);
			sb.append(" ");
		}
		if(attribute!=null){
			sb.append("attribute:");
			sb.append(attribute);
			sb.append(" ");
		}
		if(expr!=null){
			sb.append("expr:");
			sb.append(expr);
			sb.append(" ");
		}
		
		if(codeTrace!=null){
			sb.append("codetrace:");
			sb.append(codeTrace);
			sb.append(" ");
		}
		
		Throwable l = this.getCause();
		if(l!=null){
			if(l instanceof RunException){
				sb.append("CAUSE->");
				sb.append(((RunException) l).toString());
			}else{
				if(l.getMessage()!=null){
					sb.append("CAUSE->");
					sb.append(l.getMessage());
				}
			}	
		}
		
		
		return sb.toString();
	}
}
