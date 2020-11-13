package com.bilgidoku.rom.gwt.client.util;

import com.bilgidoku.rom.shared.Postman;
import com.bilgidoku.rom.shared.RunException;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.FormElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.http.client.URL;

public class PostmanClient implements Postman {
	
	private String lang;

	public PostmanClient(String lang) {
		this.lang=lang;
	}

	@Override
	public String postManGetJson(String href) throws RunException {
		return getSynch(href);
	}

	@Override
	public String postManSynch(Object session, String href, String data) throws RunException {
		return postSynch(href, data);
	}

	@Override
	public String postManSubmit(String formId, String resetFields) throws RunException {
		FormElement form = (FormElement) Document.get().getElementById(formId);
		if (form == null)
			throw new RunException("Form not found for id:" + formId);

		String action = form.getAction();
		String method = form.getMethod();
		StringBuilder sb = new StringBuilder();
		sb.append("outform=json");

		NodeList<Element> inputs = form.getElementsByTagName("input");
		if (inputs != null) {
			for (int i = 0; i < inputs.getLength(); i++) {
				InputElement inp = (InputElement) inputs.getItem(i);
				String valToGo = inp.getValue();
				String inpname = inp.getName();
				append(sb, valToGo, inpname);
				if (inpname != null && inpname.length() > 0 && resetFields.indexOf(inpname) >= 0)
					inp.setValue(null);
			}
		}

		inputs = form.getElementsByTagName("textarea");
		if (inputs != null) {
			for (int i = 0; i < inputs.getLength(); i++) {
				TextAreaElement inp = (TextAreaElement) inputs.getItem(i);
				String valToGo = inp.getValue();
				String inpname = inp.getName();
				append(sb, valToGo, inpname);
				if (resetFields.indexOf(inpname) >= 0)
					inp.setValue(null);
			}
		}

		inputs = form.getElementsByTagName("select");
		if (inputs != null) {
			for (int i = 0; i < inputs.getLength(); i++) {
				SelectElement inp = (SelectElement) inputs.getItem(i);
				String valToGo = inp.getValue();
				String inpname = inp.getName();
				append(sb, valToGo, inpname);
				if (resetFields.indexOf(inpname) >= 0)
					inp.setValue(null);
			}
		}

		if (method.equalsIgnoreCase("get")) {
			return getSynch(action + "?" + sb.toString());
		}
		return postSynch(action, sb.toString());
	}
	

	private static native String getSynch(String href)
	/*-{
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.open("GET", href, false);
		xmlhttp.send();
		return xmlhttp.responseText;
	}-*/;

	private static native String postSynch(String href, String data)
	/*-{
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.open("POST", href, false);
		xmlhttp.send(data);
		return xmlhttp.responseText;
	}-*/;

	
	

	private void append(StringBuilder sb, String valToGo, String inpname) {
		sb.append('&');
		sb.append(inpname);
		sb.append('=');
		sb.append(URL.encodeQueryString(valToGo));
	}
}
