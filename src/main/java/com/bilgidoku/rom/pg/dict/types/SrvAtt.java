package com.bilgidoku.rom.pg.dict.types;

import java.lang.reflect.Field;

import com.bilgidoku.rom.pg.dict.CGAtt;


public class SrvAtt implements CGAtt{
	String name;
	TypeHolder type;
	Field field;
	boolean nullable;
	
	public SrvAtt(String name, TypeHolder type, boolean nullable) {
		this.name=name;
		this.type=type;
		this.nullable=nullable;
	}

	
	public SrvAtt(String name2, TypeHolder th, Field fi) {
		this(name2,th, true);
		this.field=fi;
	}


	public String normalName(){
		if(name.length()<3){
			throw new RuntimeException("Name can not be shorter than 3:"+name);
		}
		return name.substring(2);
	}
	
	public SrvAtt clone(){
		SrvAtt s=new SrvAtt(name,type,nullable);
		return s;
	}

	public String getName(){
		return name;
	}
	public TypeHolder getTypeHolder() {
		return type;
	}


	@Override
	public String getPrefix() {
		return name.substring(0,2);
	}



	@Override
	public String getSuffix() {
		if(name.startsWith("p_") || name.startsWith("a_") || name.startsWith("c_") || name.startsWith("f_"))
			return normalName();
		return name;
	}


	@Override
	public boolean isParam() {
		return name.startsWith("p_");
	}


	@Override
	public boolean isCookie() {
		return name.startsWith("c_");
	}


	@Override
	public boolean isLang() {
		return name.equals("a_lang");
	}


	@Override
	public boolean isSelf() {
		return name.equals("a_self");
	}


	@Override
	public boolean isContact() {
		return name.equals("a_contact");
	}
	
	@Override
	public boolean isContactno() {
		return name.equals("a_contactno");
	}


	@Override
	public boolean isRoles() {
		return name.equals("a_roles");
	}


	@Override
	public boolean isIp() {
		return name.equals("a_remote_addr");
	}


	@Override
	public boolean isSession() {
		return name.equals("a_session");
	}
	
	@Override
	public boolean isDomain() {
		return name.equals("a_domain");
	}

	
	@Override
	public boolean isUser() {
		return name.equals("a_user");
	}
	
	@Override
	public boolean isHost() {
		return name.equals("a_host");
	}
	
	@Override
	public boolean isFile() {
		return name.startsWith("f_");
	}
	
	@Override
	public boolean isMask() {
		return name.equals("a_mask");
	}


	@Override
	public boolean isCont() {
		return false;
	}


	@Override
	public boolean isSid() {
		return name.equals("a_sid");
	}
	
	@Override
	public boolean isSint() {
		return name.equals("a_sint");
	}


	@Override
	public boolean isRid() {
		return name.equals("a_rid");
	}
}
