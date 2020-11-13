package com.bilgidoku.rom.shared;

public interface Postman {

	String postManGetJson(String href) throws RunException;
	
	String postManSynch(Object session, String href, String data) throws RunException;

	String postManSubmit(String formId,String resetFields) throws RunException;
}
