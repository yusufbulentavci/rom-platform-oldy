package com.bilgidoku.rom.site.yerel.mail.core;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.bilgidoku.rom.gwt.araci.client.rom.Mails;
import com.bilgidoku.rom.site.yerel.ClientKnownError;

public class MailWrap {
	// TODO update flags
	final Mails mails;

	private final Mime part;

	public MailWrap(Mails mails) throws ClientKnownError {
		this.mails = mails;
		part = new Mime(mails.mime.getValue().isObject());
	}

	public String getUri() {
		return mails.uri;
	}

	public boolean isMultiPart() {
		return part.isMulti();
	}
	
	public Mime getMime(){
		return part;
	}

	public InternetAddress[] getFrom() {
		return part.getFrom();
	}

	public String getSubject() {
		return part.getSubject();
	}

	public Date getDate() {
		// new Date(object.internalDate)
		// return mails.creation_date;
		Date d = part.getDate();
		if (d != null)
			return d;
		return new Date();
	}

	public InternetAddress[] getTo() {
		return part.getTo();
	}

	public InternetAddress[] getCc() {
		return part.getCc();
	}

	public InternetAddress[] getBcc() {
		return part.getBcc();
	}

	public String getBody() {
		// TODO Auto-generated method stub
		return null;
	}

	public String showAlternative(String string) {
		Mime m=part.getAlternative(string);
		if(m==null){
			return "No alternative for "+string;
		}
		try {
			return m.showHtml(mails.uri);
		} catch (UnsupportedEncodingException e) {
			return "Unsupported encoding";
		}
	}
	
	public String showHtml() {
		try {
			String html = part.showHtml(mails.uri);
			return html;
		} catch (UnsupportedEncodingException e) {
			return "Unsupported encoding";
		}
	}

	public String getFromName() {
		InternetAddress[] a = part.getFrom();
		if (a == null || a.length == 0)
			return "";
		return a[0].personalPreferred();
	}
	
	public void unRead() {
		setState(0);
	}

	
	public void read() {
		setState(32);
	}


//	public boolean isUnRead() {
//		if ((mails.state & 0) == 0)
//			return true;
//		return false;
//	}

	public boolean isRead() {
		if ((mails.state & 32) != 0)
			return true;
		return false;

	}

	public boolean isAnswered() {
		if ((mails.state & 2) != 0)
			return true;
		return false;
	}

	public boolean isImportant() {
		if ((mails.state & 256) != 0)
			return true;
		return false;
	}

	public boolean isSent() {
		if ((mails.state & 16) != 0)
			return true;
		return false;
	}

	private void setState(int i) {
		//seen 32
		//answered 2
		//important 256
		//16 sent

		mails.state = mails.state | i;
		
	}

	public boolean hasAttachment() {
		return part.hasAttachment();
	}

	
}
