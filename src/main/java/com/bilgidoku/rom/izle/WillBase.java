package com.bilgidoku.rom.izle;



/**
 * Server data ve thread'ler olarak ayrilmis durumda.
 * Kimin eli kimin cebinde belli degil.
 * Buna bir son vermek gerek.
 * 
 * Thread bir ise baslamadan once Will tipinde bir obje yaratacak
 * Will temel bir will'e bagli olacak
 * Will bir session'a bagli olacak
 * 
 * 
 * @author avci
 *
 */
public final class WillBase{
	
//	final static private MonitorService monitorService=ServiceDiscovery.getService(MonitorService.class);
//	final static private MonitorFilter filter=monitorService.getFilter();
	
	private WillListener listener=null;
	
//	private final String service;
	
	Integer hostId;
	String email;
	String ip;
	
//	
//	public WillBase(String service){
//		this.service=service;
//	}
//	
//	public String toString(){
//		return service+":"+action+" "+param;
//	}
//	
//	public void set(String action){
//		this.action=action;
//		startTime=System.currentTimeMillis();
//		monitorService.setWill(this);
//	}
//	
//	public void param(String param){
//		this.param=param;
//	}
//	
//	public void end() {
//		endTime=System.currentTimeMillis();
//		if(listener!=null)
//			listener.endWill(this);
//		monitorService.noWill(this);
//	}
//	
//
//	public void notSelected() {
//		selected=false;
//	}
//	
//	public void selected() {
//		selected=true;
//	}
//
////	public void setAction(String uriPath) {
////		Boolean td=filter.actionChanged(this);
////	}
//
//
//	public void setListener(WillListener session) {
//		this.listener=session;
//	}
//
//	class Will implements AutoCloseable{
//		String action;
//		
//		String param;
//		
//		long startTime;
//		long endTime;
//		
//		boolean selected=false;
//
//		public long cpuTime() {
//			long k = endTime-startTime;
//			return k==0?1:k;
//		}
//
//		@Override
//		public void close() throws Exception {
//			this.action=null;
//			hostId=null;
//			email=null;
//			ip=null;
//			param=null;
//		}
//	}
	
	
}
