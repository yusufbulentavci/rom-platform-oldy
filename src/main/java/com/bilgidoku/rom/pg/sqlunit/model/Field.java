package com.bilgidoku.rom.pg.sqlunit.model;

import com.bilgidoku.rom.pg.dict.CGAtt;
import com.bilgidoku.rom.pg.dict.types.TypeAdapter;
import com.bilgidoku.rom.pg.dict.types.TypeHolder;

public class Field implements CGAtt{
	public final String name;
	private final TypeHolder sqlTypeHolder;
	public boolean pk=false;
	public boolean setMethod=true;
	public boolean getMethod=true;
	

	@Override
	public String toString() {
		return "Field [name=" + name + ", sqlTypeHolder=" + sqlTypeHolder + ", pk=" + pk + ", setMethod=" + setMethod
				+ ", getMethod=" + getMethod + "]";
	}


	public Field(String named, TypeAdapter type, boolean isMap, int dims, boolean pk, String[] anns) {
		this.name=named.trim();
		if(isMap){
			this.sqlTypeHolder=new TypeHolder(dims, new TypeHolder(type, 0));
		}else{
			this.sqlTypeHolder=new TypeHolder(type,dims);
		}
		
		if(anns!=null) {
			for (String string : anns) {
				string = string.trim();
				if(string.equals("noset")) {
					setMethod=false;
				}else if(string.equals("noget")) {
					getMethod=false;
				}
			}
		}
	}

	
	public Field(String cut, TypeHolder typeHolder) {
		this.name=cut;
		this.sqlTypeHolder=typeHolder;
	}

	public String normalName(){
		if(name.length()<3){
			throw new RuntimeException("Name can not be shorter than 3:"+name);
		}
		return name.substring(2);
	}


	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Field))
			return false;
		Field f = (Field)obj;
		return f.name.equals(name) && f.sqlTypeHolder.equals(sqlTypeHolder) && f.pk==this.pk && f.getMethod==this.getMethod && f.setMethod==this.setMethod;
	}

	public static String capitalize(String s) {
		if (s.length() == 0)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}


	public TypeHolder getTypeHolder() {
		return sqlTypeHolder;
	}
	
	@Override
	public Object clone(){
		Field f=new Field(this.name, (TypeHolder) this.sqlTypeHolder.clone());
		f.pk=this.pk;
		return f;
	}
	
//	////////////////////////////////////
//	public String getAttNameJavaFormFirstUpper() {
//		return capitalize(getAttNameJavaForm());
//	}


	@Override
	public String getName() {
		return name;
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
		return name.equals("a_iscont");
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
