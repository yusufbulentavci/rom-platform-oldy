package com.bilgidoku.rom.site.yerel.mail.core;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bilgidoku.rom.site.yerel.ClientKnownError;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class Mime {
	private final String contentType;
	private final String disposition;
	private Integer size = 0;

	private Integer flags;
	private String dbfs;
	private String fileName;
	private String str;
	private List<Mime> parts;

	private InternetAddress[] from;
	private InternetAddress[] to;
	private InternetAddress[] bcc;
	private InternetAddress[] cc;
	private NewsAddress[] news;

	private String subject;
	private Long sentDate;
	private String msgId;
	private String inReplyTo;
	private String references;

	Mime(JSONObject jo) throws ClientKnownError {
		try {
			contentType = getString(jo, "ctype");
			disposition = getString(jo, "disp");
			size = optInt(jo, "size");
			flags = optInt(jo, "flags");
			subject = optString(jo, "subject");
			msgId = optString(jo, "msgid");
			inReplyTo = optString(jo, "inreplyto");
			references = optString(jo, "references");

			sentDate = optLong(jo, "sentdate");
			from = jsonToIntAddr(jo, "from");
			to = jsonToIntAddr(jo, "to");
			cc = jsonToIntAddr(jo, "cc");
			bcc = jsonToIntAddr(jo, "bcc");
			news = jsonToNewsAddr(jo, "news");

			str = optString(jo, "str");
			if (str == null) {
				fileName = optString(jo, "file");
				if (fileName != null) {
					dbfs = getString(jo, "dbfs");
				} else {
					JSONArray ja = optJSONArray(jo, "parts");
					if (ja != null) {
						parts = new ArrayList<Mime>();
						for (int i = 0; i < ja.size(); i++) {
							JSONObject je = ja.get(i).isObject();
							parts.add(new Mime(je));
						}
					}
				}
			}
		} catch (JSONException e) {
			throw new ClientKnownError(e);
		}
	}

	public Mime(String contentType, String disposition) {
		this.contentType = contentType;
		this.disposition = disposition;
	}

	private int getInt(JSONObject jo, String string) throws ClientKnownError {
		JSONValue jv = jo.get(string);
		if (jv == null || jv.isNull() != null)
			throw new ClientKnownError("Json int not found/ key:" + string + " in " + jo.toString());
		return (int) jv.isNumber().doubleValue();
	}

	private Integer optInt(JSONObject jo, String string) throws ClientKnownError {
		JSONValue jv = jo.get(string);
		if (jv == null || jv.isNull() != null)
			return null;
		return (int) jv.isNumber().doubleValue();
	}

	private Long optLong(JSONObject jo, String string) throws ClientKnownError {
		JSONValue jv = jo.get(string);
		if (jv == null || jv.isNull() != null)
			return null;
		return (long) jv.isNumber().doubleValue();
	}

	private String optString(JSONObject jo, String string) {
		JSONValue jv = jo.get(string);
		if (jv == null || jv.isNull() != null)
			return null;
		return jv.isString().stringValue();
	}

	private String getString(JSONObject jo, String string) throws ClientKnownError {
		JSONValue jv = jo.get(string);
		if (jv == null || jv.isNull() != null)
			throw new ClientKnownError("Json string not found/ key:" + string + " in " + jo.toString());
		return jv.isString().stringValue();
	}

	private JSONArray optJSONArray(JSONObject jo, String string) {
		JSONValue jv = jo.get(string);
		if (jv == null || jv.isNull() != null)
			return null;
		return jv.isArray();
	}

	public JSONObject toJson() {
		JSONObject ret = new JSONObject();
		ret.put("ctype", new JSONString(contentType));

		ret.put("disp", new JSONString(disposition));

		if (flags != null) {
			ret.put("flags", new JSONNumber(flags));
		}

		if (subject != null) {
			ret.put("subject", new JSONString(subject));
		}
		if (msgId != null) {
			ret.put("msgid", new JSONString(msgId));
		}
		if (inReplyTo != null) {
			ret.put("inreplyto", new JSONString(inReplyTo));
		}
		if (references != null) {
			ret.put("references", new JSONString(references));
		}

		if (sentDate != null) {
			ret.put("sentdate", new JSONNumber(sentDate));
		}
		if (to != null) {
			ret.put("to", internetAddrToJson(to));
		}
		if (cc != null) {
			ret.put("cc", internetAddrToJson(cc));
		}
		if (from != null) {
			ret.put("from", internetAddrToJson(from));
		}
		if (bcc != null) {
			ret.put("bcc", internetAddrToJson(bcc));
		}
		if (news != null) {
			ret.put("news", newsAddrToJson(news));
		}

		if (fileName != null) {
			ret.put("file", new JSONString(fileName));
			ret.put("dbfs", new JSONString(dbfs));
		} else if (str != null) {
			ret.put("str", new JSONString(str));
		} else {
			JSONArray ja = new JSONArray();
			ret.put("parts", ja);
			for (int i = 0; i < parts.size(); i++) {
				ja.set(i, parts.get(i).toJson());
			}
		}
		return ret;
	}

	private InternetAddress[] jsonToIntAddr(JSONObject jo, String string) throws JSONException, ClientKnownError {
		JSONArray s = optJSONArray(jo, string);
		if (s == null) {
			return null;
		}
		InternetAddress[] ret = new InternetAddress[s.size()];
		for (int i = 0; i < ret.length; i++) {
			JSONObject je = s.get(i).isObject();
			ret[i] = new InternetAddress(getString(je, "a"), optString(je, "p"));
		}
		return ret;
	}

	private JSONArray internetAddrToJson(InternetAddress[] from2) throws JSONException {
		JSONArray ja = new JSONArray();
		int i = 0;
		for (InternetAddress address : from2) {
			JSONObject jo = new JSONObject();
			jo.put("a", new JSONString(address.getAddress()));
			String p = address.getPersonal();
			if (p != null) {
				jo.put("p", new JSONString(p));
			}
			ja.set(i++, jo);
		}
		return ja;
	}

	private NewsAddress[] jsonToNewsAddr(JSONObject jo, String string) throws JSONException, ClientKnownError {
		JSONArray s = optJSONArray(jo, string);
		if (s == null) {
			return null;
		}
		NewsAddress[] ret = new NewsAddress[s.size()];
		for (int i = 0; i < s.size(); i++) {
			JSONObject je = s.get(i).isObject();
			ret[i] = new NewsAddress(getString(je, "g"), optString(je, "h"));
		}
		return ret;
	}

	private JSONArray newsAddrToJson(NewsAddress[] from2) throws JSONException {
		JSONArray ja = new JSONArray();
		int i = 0;
		for (NewsAddress address : from2) {
			JSONObject jo = new JSONObject();
			jo.put("g", new JSONString(address.getNewsgroup()));
			String h = address.getHost();
			if (h != null)
				jo.put("h", new JSONString(address.getHost()));
			ja.set(i++, jo);
		}
		return ja;
	}

	public static void summary(JSONObject mime, StringBuilder sb) throws ClientKnownError {
		try {
			Mime jm = new Mime(mime);
			jm.summary(sb);
		} catch (JSONException e) {
			throw new ClientKnownError(e);
		}
	}

	private void summary(StringBuilder sb) {
		checkAdd(sb, subject, "Subject:");
		checkAddAddr(sb, "To:", to);
		checkAddAddr(sb, "Bcc:", bcc);
		checkAddAddr(sb, "Cc:", cc);
		checkAddAddr(sb, "Newsgroups:", news);

		if (sentDate != null) {
			sb.append("Sent-Date:");
			sb.append(new Date(sentDate));
			sb.append("\n");
		}
		checkAdd(sb, contentType, "Content-Type:");
		checkAdd(sb, msgId, "Msg-Id:");
		checkAdd(sb, inReplyTo, "In-Reply-To:");
		checkAdd(sb, references, "References:");

		checkAdd(sb, fileName, "Attachment fileName:");
		checkAdd(sb, str, "Content:");
		if (parts != null) {
			for (int i = 0; i < parts.size(); i++) {
				sb.append("-----Part ");
				sb.append(i);
				sb.append(" begins-----\n");
				parts.get(i).summary(sb);
				sb.append("-----Part ");
				sb.append(i);
				sb.append(" ends-----\n");
			}
		}
	}

	private void checkAddAddr(StringBuilder sb, String string, Object[] to2) {
		if (to2 == null)
			return;
		sb.append(string);
		for (Object address : to2) {
			sb.append(" ");
			sb.append(address.toString());
		}
		sb.append("\n");
	}

	private void checkAdd(StringBuilder sb, String subject2, String string) {
		if (subject2 != null) {
			sb.append(string);
			sb.append(subject2);
			sb.append("\n");
		}
	}

	public InternetAddress[] getFrom() {
		return from;
	}

	public String getSubject() {
		return subject;
	}

	public InternetAddress[] getTo() {
		return to;
	}

	public InternetAddress[] getBcc() {
		return bcc;
	}

	public InternetAddress[] getCc() {
		return cc;
	}

	public NewsAddress[] getNews() {
		return news;
	}

	public String showHtml(String uri) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		if (parts != null) {
			for (Mime mime : parts) {
				sb.append(mime.showHtml(uri));
			}
		} else if (dbfs != null) {
			sb.append("<a target=\"_blank\" href=\"" + uri + "/getfile.rom?fn=" + URL.encode(dbfs) + "\">Attachment:");
			sb.append(fileName);
			if (size != null && size > 0) {
				sb.append(" Size:");
				sb.append(size);
			}
			sb.append("</a>&nbsp;&nbsp;");
		} else if (str != null) {
			if (isTextContent() ) {
				String strA = str;
				if(!isHtml())
					strA=str.replaceAll("\n", "<br>");
				sb.append(strA);
				
			}
		}

		return sb.toString();
	}

	public int getFlags() {
		if (flags == null)
			return 0;
		return flags;
	}

	public Date getDate() {
		if (sentDate == null)
			return null;
		return new Date(sentDate);
	}

	private boolean isTextContent() {
		return contentType != null && contentType.startsWith("text");
	}
	
	private boolean isHtml() {
		return contentType != null && contentType.startsWith("text/html");
	}

	public void setSubject(String subject2) {
		this.subject = subject2;
	}

	public void setStr(String body) {
		this.str = body;
	}

	public void setTos(InternetAddress[] tos) {
		this.to = tos;
	}

	public void setCcs(InternetAddress[] ccs) {
		this.cc = ccs;
	}

	public void setBccs(InternetAddress[] bccs) {
		this.bcc = bccs;
	}

	public static Mime createMulti(String type) {
		Mime mime = new Mime("multipart/" + type, "inline");
		mime.parts = new ArrayList<Mime>();
		return mime;
	}

	public void addPart(Mime part) {
		this.parts.add(part);
	}

	public static Mime createAttach(String fileName, String dbfs, String ct) {
		Mime mime = new Mime(ct, "attachment");
		mime.fileName = fileName;
		mime.dbfs = dbfs;
		return mime;
	}

	public void setFrom(InternetAddress internetAddress) {
		this.from = new InternetAddress[] { internetAddress };
	}

	public boolean isMulti() {
		return parts != null && this.contentType.startsWith("multipart/");
	}

	public boolean isMultiAlternative() {
		return this.contentType.startsWith("multipart/alternative");
	}

	public Set<String> getAlternatives() {
		if (parts == null)
			return null;
		
		Set<String> ret = new HashSet<String>();
		for (Mime m : parts) {
			ret.add(m.contentTypeStripped());
		}
		return ret;
	}

	private String contentTypeStripped() {
		int ind = contentType.indexOf(';');
		if (ind > 0)
			return contentType.substring(0, ind).trim();

		return contentType.trim();
	}

	public Mime getAlternative(String string) {
		for (Mime m : parts) {
			if (m.contentType.trim().startsWith(string))
				return m;
		}
		return null;
	}

	public boolean hasAttachment() {
//		if(contentTypeStripped().equals("multipart/mixed"))
//			return false;
		if (parts == null)
			return false;
		
		for (Mime m : parts) {
			if(m.dbfs!=null)
				return true;
		}
		return false;
	}

}
