package com.bilgidoku.rom.epostatemel.javam.rom;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.epostatemel.javam.activation.DataHandler;
import com.bilgidoku.rom.epostatemel.javam.activation.DataSource;
import com.bilgidoku.rom.epostatemel.javam.activation.FileDataSource;
import com.bilgidoku.rom.epostatemel.javam.mail.Address;
import com.bilgidoku.rom.epostatemel.javam.mail.BodyPart;
import com.bilgidoku.rom.epostatemel.javam.mail.Flags;
import com.bilgidoku.rom.epostatemel.javam.mail.MessagingException;
import com.bilgidoku.rom.epostatemel.javam.mail.Session;
import com.bilgidoku.rom.epostatemel.javam.mail.Message.RecipientType;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.AddressException;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.AttachDeal;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.InternetAddress;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.MimeBodyPart;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.MimeMessage;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.MimeMultipart;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.NewsAddress;
import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.min.Sistem;
/**
 * 
 * Bcc handling: json->mime ignores bcc field. That's all.
 * 
 * 
 * @author avci
 *
 */
public class JsonMime {
	private static PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS).and(Sanitizers.LINKS).and(Sanitizers.IMAGES)
			.and(Sanitizers.STYLES);

//	private final Integer hostId;
//	private final String user;
	private String contentType;
	private String disposition;
	private final int size;

	private final Integer flags;
	private String fileName;
	private String str;
	private Boolean auth;

	private List<JsonMime> parts;

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

	private String dbfs;

	public static MimeMessage toMime(AttachDeal deal, JSONObject jo) throws KnownError {
		try {
			JsonMime jm = new JsonMime(jo);
			return jm.toMime(deal);
		} catch (JSONException | MessagingException | UnsupportedEncodingException e) {
			throw new KnownError(e);
		}
	}

	public static JSONObject toJson(AttachDeal deal, MimeMessage mm) throws KnownError {
		try {
			JsonMime jm = new JsonMime(mm, deal);
			return jm.toJson();
		} catch (JSONException | MessagingException | IOException e) {
			throw new KnownError(e);
		}
	}

	private JsonMime(JSONObject jo) throws JSONException, AddressException, UnsupportedEncodingException {
//		this.hostId=jo.optInteger("hostid", null);
//		this.user=jo.optString("user", null);
		contentType = jo.getString("ctype");
		disposition = jo.getString("disp");
		size = jo.optInt("size");
		flags = optInt(jo, "flags");
		subject = jo.optString("subject", null);
		msgId = jo.optString("msgid", null);
		inReplyTo = jo.optString("inreplyto", null);
		references = jo.optString("references", null);

		sentDate = optLong(jo, "sentdate");
		from = jsonToIntAddr(jo, "from");
		to = jsonToIntAddr(jo, "to");
		cc = jsonToIntAddr(jo, "cc");
		bcc = jsonToIntAddr(jo, "bcc");
		news = jsonToNewsAddr(jo, "news");
		
		auth=jo.optBoolean("rom.auth");

		setStr(jo.optString("str", null));

		if (str == null) {
			fileName = jo.optString("file", null);
			if (fileName != null) {
				dbfs = jo.getString("dbfs");
			} else {
				JSONArray ja = jo.optJSONArray("parts");
				if (ja != null) {
					parts = new ArrayList<JsonMime>();
					for (int i = 0; i < ja.length(); i++) {
						JSONObject je = ja.getJSONObject(i);
						parts.add(new JsonMime(je));
					}
				}
			}
		}
	}

	private JSONObject toJson() throws JSONException, KnownError, IOException, MessagingException {
		JSONObject ret = new JSONObject();
		ret.put("ctype", contentType);
		ret.put("disp", disposition);
		ret.put("size", size);

//		if(hostId!=null)
//			ret.put("hostid", hostId);
//		if(user!=null)
//			ret.put("user", user);
		if (flags != null) {
			ret.put("flags", flags);
		}
		if (subject != null) {
			ret.put("subject", subject);
		}
		if (msgId != null) {
			ret.put("msgid", msgId);
		}
		if (inReplyTo != null) {
			ret.put("inreplyto", inReplyTo);
		}
		if (references != null) {
			ret.put("references", references);
		}

		if (sentDate != null) {
			ret.put("sentdate", sentDate);
		}
		if (from != null) {
			ret.put("from", internetAddrToJson(from));
		}
		if (to != null) {
			ret.put("to", internetAddrToJson(to));
		}
		if (cc != null) {
			ret.put("cc", internetAddrToJson(cc));
		}
		if (bcc != null) {
			ret.put("bcc", internetAddrToJson(bcc));
		}
		if (news != null) {
			ret.put("news", newsAddrToJson(news));
		}
		if (auth !=null){
			ret.put("rom.auth", auth);
		}

		if (fileName != null) {
			ret.put("file", fileName);
			ret.put("dbfs", dbfs);
		} else if (str != null) {
			ret.put("str", str);
		} else {
			JSONArray ja = new JSONArray();
			ret.put("parts", ja);
			for (JsonMime it : parts) {
				ja.put(it.toJson());
			}
		}
		return ret;
	}

	private boolean isTextContent() {
		return contentType != null && contentType.startsWith("text");
	}

	private Long optLong(JSONObject jo, String string) {
		long l = jo.optLong(string, -1);
		if (l == -1)
			return null;
		return l;
	}

	private Integer optInt(JSONObject jo, String string) {
		int l = jo.optInt(string, -1);
		if (l == -1)
			return null;
		return l;
	}

	
	private MimeBodyPart toMimeBodyPart(AttachDeal deal) throws MessagingException, KnownError {
		MimeBodyPart ret = new MimeBodyPart();

		ret.setDisposition(disposition);

		if (fileName != null) {
			File df = deal.get(dbfs);
			DataSource source = new FileDataSource(df);
			ret.setDataHandler(new DataHandler(source));
			ret.setFileName(fileName);
		} else if (str != null) {
			ret.setContent(str, contentType);
		} else {
			MimeMultipart mp = new MimeMultipart();
			for (JsonMime it : parts) {
				MimeBodyPart mbp = it.toMimeBodyPart(deal);
				mp.addBodyPart(mbp);
			}
			ret.setContent(mp);
		}
		return ret;
	}

	private MimeMessage toMime(AttachDeal deal) throws MessagingException, KnownError {
		MimeMessage ret = new MimeMessage(Session.getInstance());
		Flags f = new Flags();
		if(flags==null){
			f.setSystemFlags(0);
		}else{
			f.setSystemFlags(flags);
		}
		ret.setFlags(f, true);

		if (from != null) {
			ret.setFrom(from[0]);
		}
		if (to != null) {
			ret.setRecipients(RecipientType.TO, to);
		}
		if (cc != null) {
			ret.setRecipients(RecipientType.CC, cc);
		}
//		if (bcc != null) {
//			ret.setRecipients(RecipientType.BCC, bcc);
//		}
		if (news != null) {
			ret.setRecipients(RecipientType.NEWSGROUPS, news);
		}

		if (subject != null) {
			ret.setSubject(subject);
		}
		// if (msgId != null) {
		// ret.set("msgid", msgId);
		// }

		if (inReplyTo != null) {
			ret.setHeader("In-Reply-To", inReplyTo);
		}
		if (references != null) {
			ret.setHeader("References", references);
		}

		if (sentDate != null) {
			ret.setSentDate(new Date(sentDate));
		}
		
		if (auth!=null){
			ret.setHeader("rom.auth", auth.toString());
		}

		ret.setDisposition(disposition);

		if (fileName != null) {
			File df = deal.get(dbfs);
			DataSource source = new FileDataSource(df);
			ret.setDataHandler(new DataHandler(source));
			ret.setFileName(fileName);
		} else if (str != null) {
			ret.setContent(str, contentType);
		} else {
			MimeMultipart mp = new MimeMultipart();
			for (JsonMime it : parts) {
				MimeBodyPart mbp = it.toMimeBodyPart(deal);
				mp.addBodyPart(mbp);
			}
			ret.setContent(mp);
		}
		return ret;
	}
	
	public static List<InternetAddress> getRecipients(JSONObject jo) throws KnownError {
		try {
			InternetAddress[] ta = jsonToIntAddr(jo,"to");
			InternetAddress[] ca;
			InternetAddress[] ba;
			ca = jsonToIntAddr(jo,"cc");
			ba = jsonToIntAddr(jo,"bcc");
			List<InternetAddress> ret=new ArrayList<InternetAddress>();
			
			if(ta!=null){
				for (InternetAddress internetAddress : ta) {
					ret.add(internetAddress);
				}
			}
			if(ca!=null){
				for (InternetAddress internetAddress : ca) {
					ret.add(internetAddress);
				}
			}
			if(ba!=null){
				for (InternetAddress internetAddress : ba) {
					ret.add(internetAddress);
				}
			}
			return ret;
		} catch (AddressException | UnsupportedEncodingException | JSONException e) {
			throw new KnownError(e);
		}
	}

	public static InternetAddress[] jsonToIntAddr(JSONObject jo, String string) throws JSONException, AddressException,
			UnsupportedEncodingException {
		JSONArray s = jo.optJSONArray(string);
		if (s == null) {
			return null;
		}
		InternetAddress[] ret = new InternetAddress[s.length()];
		for (int i = 0; i < s.length(); i++) {
			JSONObject je = s.getJSONObject(i);
			ret[i] = new InternetAddress(je.getString("a"), je.optString("p", null));
		}
		return ret;
	}

	private JSONArray internetAddrToJson(InternetAddress[] from2) throws JSONException {
		JSONArray ja = new JSONArray();
		for (InternetAddress address : from2) {
			JSONObject jo = new JSONObject();
			jo.put("a", address.getAddress());
			String p = address.getPersonal();
			if (p != null) {
				jo.put("p", p);
			}
			ja.put(jo);
		}
		return ja;
	}

	private NewsAddress[] jsonToNewsAddr(JSONObject jo, String string) throws JSONException, AddressException {
		JSONArray s = jo.optJSONArray(string);
		if (s == null) {
			return null;
		}
		NewsAddress[] ret = new NewsAddress[s.length()];
		for (int i = 0; i < s.length(); i++) {
			JSONObject je = s.getJSONObject(i);
			ret[i] = new NewsAddress(je.getString("g"), je.optString("h", null));
		}
		return ret;
	}

	private JSONArray newsAddrToJson(NewsAddress[] from2) throws JSONException {
		JSONArray ja = new JSONArray();
		for (NewsAddress address : from2) {
			JSONObject jo = new JSONObject();
			jo.put("g", address.getNewsgroup());
			String h = address.getHost();
			if (h != null)
				jo.put("h", address.getHost());
			ja.put(jo);
		}
		return ja;
	}

	private JsonMime(BodyPart mm, AttachDeal deal) throws MessagingException, IOException, KnownError {
//		this.hostId=null;
//		this.user=null;
		this.size = mm.getSize();
		this.flags=null;
		this.contentType = mm.getContentType();
		this.disposition = mm.getDisposition();
		if (disposition != null && disposition.equals("attachment")) {
			fileName = mm.getFileName();
			dbfs = deal.make(mm.getInputStream(), fileName);
		} else {
			disposition = "inline";
			Object content = mm.getContent();
			if (content instanceof String) {
				setStr((String) content);
			} else if (content instanceof MimeMultipart) {
				this.parts = new ArrayList<JsonMime>();
				MimeMultipart multiPart = (MimeMultipart) content;
				for (int i = 0; i < multiPart.getCount(); i++) {
					BodyPart bp = multiPart.getBodyPart(i);
					parts.add(new JsonMime(bp, deal));
				}
			} else if (content instanceof InputStream) {
				this.str = "Unknown content type: please use original mail";
			} else {
				Sistem.errln("Unexpected content class:" + content);
			}
		}
	}

	private JsonMime(MimeMessage mm, AttachDeal deal) throws MessagingException, IOException, KnownError {
//		this.hostId=hostId;
//		this.user=user;
		this.flags = mm.getFlags().getSystemFlagsInt();
		this.subject = mm.getSubject();
		this.size = mm.getSize();
		Date dt = mm.getSentDate();
		if (dt != null) {
			this.sentDate = dt.getTime();
		}

		this.from = mm.getInternetFrom();
		this.to = mm.getInternetRecipients(RecipientType.TO);
		this.cc = mm.getInternetRecipients(RecipientType.CC);
		this.bcc = mm.getInternetRecipients(RecipientType.BCC);
		this.news = mm.getNewsRecipients();

		this.msgId = mm.getMessageID();
		this.references = mm.getHeader("References", " ");
		this.inReplyTo = mm.getHeader("In-Reply-To", " ");

		this.contentType = mm.getContentType();
		this.disposition = mm.getDisposition();
		if (disposition != null && disposition.equals("attachment")) {
			fileName = mm.getFileName();
			dbfs = deal.make(mm.getInputStream(), fileName);
		} else {
			disposition = "inline";
			Object content = mm.getContent();
			if (content instanceof String) {
				setStr((String) content);
			} else if (content instanceof MimeMultipart) {
				this.parts = new ArrayList<JsonMime>();
				MimeMultipart multiPart = (MimeMultipart) content;
				for (int i = 0; i < multiPart.getCount(); i++) {
					BodyPart bp = multiPart.getBodyPart(i);
					parts.add(new JsonMime(bp, deal));
				}
			} else if (content instanceof InputStream) {
				this.str = "Unknown content type: please use original mail";
			} else {
				Sistem.errln("Unexpected content class:" + content);
			}
		}
	}

	//
	// public String getContent() throws IOException{
	// if(str!=null){
	// return str;
	// }
	// if(stream!=null){
	// return new String(ASCIIUtility.getBytes(stream));
	// }
	// return "";
	// }
	//
	// public InputStream getContentInputStream() throws IOException{
	// if(stream!=null){
	// return stream;
	// }
	//
	// if(str!=null){
	// return new ByteArrayInputStream(str.getBytes());
	// }
	// return new ByteArrayInputStream("".getBytes());
	// }
	//
	//

	public static void summary(JSONObject mime, StringBuilder sb) throws KnownError {
		try {
			JsonMime jm = new JsonMime(mime);
			jm.summary(sb);
		} catch (AddressException | JSONException | UnsupportedEncodingException e) {
			throw new KnownError(e);
		}
	}

	private void summary(StringBuilder sb) {
		checkAdd(sb, subject, "Subject:");
		checkAddInternetAddr(sb, "To:", to);
		checkAddInternetAddr(sb, "Bcc:", bcc);
		checkAddInternetAddr(sb, "Cc:", cc);
		checkAddNewsAddr(sb, "Newsgroups:", news);

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

	private void checkAddNewsAddr(StringBuilder sb, String string, NewsAddress[] to2) {
		if (to2 == null)
			return;
		sb.append(string);
		for (NewsAddress address : to2) {
			sb.append(" ");
			sb.append(address.getNewsgroup());
		}
		sb.append("\n");
	}

	private void checkAddInternetAddr(StringBuilder sb, String string, InternetAddress[] to2) {
		if (to2 == null)
			return;
		sb.append(string);
		for (InternetAddress address : to2) {
			sb.append(" ");
			sb.append(address.toUnicodeString());
		}
		sb.append("\n");
	}

	private void checkAddAddr(StringBuilder sb, String string, Address[] to2) {
		if (to2 == null)
			return;
		sb.append(string);
		for (Address address : to2) {
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
	public final void setStr(String str) {
		if (isTextContent()) {
			this.str = sanitizer.sanitize(str);
		} else {
			this.str = str;
		}
	}
	
//	public static void blindCopy(JSONObject jo){
//		JSONArray s = jo.optJSONArray("bcc");
//		if(s==null)
//			return;
//		jo.remove("bcc");
//	}

	// @Override
	// public void summary(StringBuilder sb) throws KnownError {
	// try {
	// Enumeration<Header> all = headers.getAllHeaders();
	// while (all.hasMoreElements()) {
	// Header h = all.nextElement();
	// sb.append(h.getName());
	// sb.append(":");
	// sb.append(h.getValue());
	// sb.append("\n");
	// }
	//
	// String disp = getDisposition();
	// if (disp != null) {
	// if (disp.equals("attachment")) {
	// String fileName = getFileName();
	// if (fileName != null) {
	// sb.append("-- attachment:");
	// sb.append(fileName);
	// sb.append(" // not added for summarizing\n");
	// }
	// }
	// }
	//
	// Object o = getContent();
	// if (o instanceof MimeMultipart) {
	// ((MimeMultipart) o).summary(sb);
	// } else {
	// byte[] c=content;
	// if(c==null){
	// c=ASCIIUtility.getBytes(contentStream);
	// }
	//
	// if(c!=null){
	// sb.append(new String(c));
	// }
	// }
	//
	//
	// return;
	// } catch (IOException | MessagingException e) {
	// throw new KnownError(e);
	// }
	//
	// }

}
