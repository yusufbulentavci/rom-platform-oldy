package com.bilgidoku.rom.web.html;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONArray;

public class PortableServer extends PortabilityServerImpl {



	

	// @Override
	// public void work(Object session, RunZone rz, Elem container) {
	// rz.render(container);
	// }

	@Override
	public void domShow(String item, Boolean inverse) throws RunException {
		throw new RunException("Server side dom show not accepted");
	}

	@Override
	public void domAppend(String htmlId, String htmlStr) throws RunException {
		throw new RunException("Server side dom append not accepted");
	}

	@Override
	public void domSet(String htmlId, String htmlStr) throws RunException {
		throw new RunException("Server side dom set not accepted");
	}

	@Override
	public void error(Exception e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(String string, String string2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatal(Exception e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatal(String string, String string2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(String string) {
		// TODO Auto-generated method stub

	}


//	@Override
//	public String getCookie(Object session, String name) throws RunException {
//		Cookie as = (Cookie) session;
//		if (name.equals("cid")) {
//			return as.getCid();
//		} else if (name.equals("user")) {
//			return as.getUserName();
//		} else if (name.equals("cname")) {
//			return as.getCname();
//		} else if (name.equals("roles")) {
//			return as.getRoles();
//		}
//		throw new RunException("Cookie param not known:" + name);
//	}

	@Override
	public JSONArray jsonArrayConstuctFromJS(Object params) {
		throw new RuntimeException(
				"Shouldnt be called(server side)-jsonArrayConstuctFromJS");
	}


//	@Override
//	public void rtConnect(String name, String rzid, String app, String subject) {
//		throw new RuntimeException("Shouldnt be called(server side)-rtConnect");
//	}

	@Override
	public void domScroll(String changeHtmlId, String refchange,
			boolean tobottom) {
		throw new RuntimeException("Shouldnt be called(server side)-domScroll");
	}

	@Override
	public void outln(String s) {
		Sistem.outln(s);
	}

	@Override
	public void errln(String s) {
		Sistem.errln(s);
	}

	@Override
	public void printStackTrace(Throwable x, String extra) {
		Sistem.printStackTrace(x, extra);
	}

	@Override
	public JSONArray select(String query) {
		throw new RuntimeException("Shouldnt be called(server side)-select");
	}

	

}
