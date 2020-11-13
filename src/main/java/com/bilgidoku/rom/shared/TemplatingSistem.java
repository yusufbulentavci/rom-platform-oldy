package com.bilgidoku.rom.shared;



public class TemplatingSistem {
	public static void outln(String s){
		if(Portable.one==null){
			System.out.println(s);
		}else{
			Portable.one.outln(s);
		}
	}
	
	public static void outln(Object s){
		outln(""+s);
	}
	
	public static void errln(String s){
		if(Portable.one==null){
			System.err.println(s);
		}else{
			Portable.one.errln(s);
		}
	}
	
	public static void errln(Object s){
		errln(""+s);
	}

	public static void printStackTrace(Throwable x, String extra) {
		if(Portable.one==null){
			if(extra!=null){
				System.err.println("Extra:"+extra);
			}
			x.printStackTrace();
		}else{
			Portable.one.printStackTrace(x, extra);
		}
	}
	
	public static void printStackTrace(Throwable x) {
		printStackTrace(x,null);
	}
	

}
