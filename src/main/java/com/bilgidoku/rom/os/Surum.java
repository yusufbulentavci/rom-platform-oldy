package com.bilgidoku.rom.os;

public class Surum {
	
	Integer ana;
	Integer yardimci;
	Integer fix;
	
	
	public Surum(String str) {
		String[] ss = str.split("\\.");
		ana=Integer.parseInt(ss[0]);
		if(ss.length>1)
			yardimci=Integer.parseInt(ss[1]);
		if(ss.length>2)
			yardimci=Integer.parseInt(ss[2]);
	}

	public static void main(String[] args) {
		System.out.println(new Surum("11.0"));
	}

	public String verString() {
		StringBuilder sb=new StringBuilder();
		sb.append(ana);
		if(yardimci!=null) {
			sb.append(".");
			sb.append(yardimci);
		}
		if(fix!=null) {
			sb.append(".");
			sb.append(fix);
		}
		return sb.toString();
	}
	

}

