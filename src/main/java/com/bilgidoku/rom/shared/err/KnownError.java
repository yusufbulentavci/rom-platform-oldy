package com.bilgidoku.rom.shared.err;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;



@SuppressWarnings("serial")
public final class KnownError extends Exception implements Serializable{
	private static AtomicInteger idgen=new AtomicInteger();
	
//	private final int id=idgen.incrementAndGet();
//	private boolean reported=false;
	private String subject;
	private boolean fatal=false;
	private boolean silent=false;
	
	private int code=500;
	
	public KnownError() {
		super();
	}

	public KnownError(String string) {
		super(string);
	}

	public KnownError(Exception e1) {
		super(e1);
	}

	public KnownError(String msg, Exception cause) {
		super(msg, cause);
	}
	
	public String getExtra(){
		return "code:"+code+(subject!=null?("subject:"+subject):"");
	}

	public int httpCode(){
		return code;
	}
	
	public KnownError fatal(){
		this.fatal=true;
		return this;
	}
	

	public KnownError internalError() {
		code=500;
		return this;
	}

	public KnownError badRequest() {
		code=400;
		return this;
	}

	public KnownError forbidden() {
		code=403;
		return this;
	}

	public KnownError notFound(String subject) {
		silent=true;
		code=404;
		this.subject=subject;
		return this;
	}
	
	public KnownError setSilent(){
		this.silent=true;
		return this;
	}
	
	public boolean isSilent(){
		return this.silent;
	}

	public KnownError temporary() {
		code=503; // service unavailable
		return this;
	}

	public KnownError security() {
		code=403;
		return this;
	}

	public KnownError notImplemented() {
		code=501;
		return this;
	}

	public boolean isBadRequest() {
		return code==400;
	}
	
	public boolean isTemporary(){
		return code==503;
	}
	
	public boolean isNotFound(){
		return code==404;
	}

	public String getSummary() {
		StringBuilder sb=new StringBuilder();
		sb.append(this.getClass().getSimpleName());
		sb.append(" ");
		if(getMessage()!=null){
			sb.append(" msg:");
			sb.append(getMessage());
		}
		if(getCause()!=null){
			sb.append(" cause:");
			sb.append(getCause().getClass().getSimpleName());
			if(getCause().getMessage()!=null){
				sb.append(" msg:");
				sb.append(getCause().getMessage());
			}
		}
		sb.append(" code:");
		sb.append(code);
		if(subject!=null){
			sb.append(" subj:");
			sb.append(subject);
		}
		if(fatal){
			sb.append(" FATAL ERROR");
		}
		return sb.toString();
	}
	
	public String getSubject(){
		return subject;
	}
	
	public int getCode(){
		return code;
	}
	
	public String toString() {
		return getSummary();
	}
	

//	public void report(String string) {
//		System.err.println("KE("+id+"):"+string);
//		if(reported)
//			return;
//		if(code==404){
//			System.err.println("Subject:"+subject);
//		}else{
//			this.printStackTrace();
//			if(this.getCause()!=null){
//				reportCause(this.getCause());
//			}
//		}
//		reported=true;
//	}

//	private void reportCause(Throwable cause) {
//		if(cause instanceof KnownError){
//			KnownError c = (KnownError)cause;
//			c.report("CAUSED BY:");
//			return;
//		}
//		
//		System.err.println("CAUSE:");
////		com.bilgidoku.rom.gunluk.Sistem.errln(cause.getMessage());
//		cause.printStackTrace();
//		
//		if(cause.getCause()!=null){
//			reportCause(cause.getCause());
//		}
//	}
}
