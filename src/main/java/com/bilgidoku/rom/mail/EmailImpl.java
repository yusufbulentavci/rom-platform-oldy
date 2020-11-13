/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/

package com.bilgidoku.rom.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.bilgidoku.rom.epostatemel.javam.mail.MessagingException;
import com.bilgidoku.rom.epostatemel.javam.mail.Session;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.AddressException;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.InternetAddress;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.MailAddress;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.MimeMessage;
import com.bilgidoku.rom.epostatemel.javam.rom.JsonMime;
import com.bilgidoku.rom.epostatemel.mail.smtp.SMTPMessage;
import com.bilgidoku.rom.gunluk.GunlukGorevlisi;
import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.pg.dbfs.DbfsGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;

/**
 * <p>
 * Wraps a MimeMessage adding routing information (from SMTP) and some simple
 * API enhancements.
 * </p>
 * <p>
 * From James version > 2.2.0a8 "mail attributes" have been added. Backward and
 * forward compatibility is supported:
 * <ul>
 * <li>messages stored in file repositories <i>without</i> attributes by James
 * version <= 2.2.0a8 will be processed by later versions as having an empty
 * attributes hashmap;</li>
 * <li>messages stored in file repositories <i>with</i> attributes by James
 * version > 2.2.0a8 will be processed by previous versions, ignoring the
 * attributes.</li>
 * </ul>
 * </p>
 */
public class EmailImpl implements Email {


	private final JSONArray dbfs;
	private final JSONObject mime;

	/**
	 * The sender of this mail.
	 */
	private MailAddress sender;
	/**
	 * The collection of recipients to whom this mail was sent.
	 */
	private Collection<MailAddress> recipients;

	// /**
	// * The identifier for this mail message
	// */
	// private String name;
	//

	/**
	 * Attributes added to this MailImpl instance
	 */
	private int priority;
	private String deliveryError;
	private final Integer hostId;
	private final String userName;
	private final String remoteHost;
	private final String remoteAddr;
	private final int spam;

	private final String requestId;

	public EmailImpl(JSONObject jo) throws KnownError {
		try {
			this.hostId = jo.optInteger("hostid", null);
			this.userName = jo.optString("user", null);
			this.remoteHost = jo.getString("rhost");
			this.remoteAddr = jo.getString("raddr");
			this.dbfs = jo.getJSONArray("dbfs");
			this.mime = jo.getJSONObject("mime");
			this.spam = jo.optInt("spam", 0);
			this.requestId = jo.getString("requestid");

			this.sender = new MailAddress(jo.getString("sender"));
			JSONArray ja = jo.optJSONArray("recipients");
			recipients = new ArrayList<MailAddress>();
			if (ja == null || ja.length() == 0) {
				List<InternetAddress> rec = JsonMime.getRecipients(mime);
				for (InternetAddress internetAddress : rec) {
					recipients.add(new MailAddress(internetAddress));
				}
			} else {
				for (int i = 0; i < ja.length(); i++) {
					recipients.add(new MailAddress(ja.getString(i)));
				}
			}
		} catch (AddressException | JSONException e) {
			throw new KnownError(e);
		}
	}

	/**
	 * A constructor that creates a MailImpl with the specified name, sender,
	 * and recipients.
	 * 
	 * @param name
	 *            the name of the MailImpl
	 * @param sender
	 *            the sender for this MailImpl
	 * @param recipients
	 *            the collection of recipients of this MailImpl
	 * @throws KnownError
	 */
	private EmailImpl(String remoteHost, String remoteIp, Integer hostId, String userName, String mimeDbfs,
			JSONArray attachs, JSONObject mime, int spam, MailAddress sender, Collection<MailAddress> recipients)
			throws KnownError {
		this.dbfs = new JSONArray();
		this.dbfs.put(mimeDbfs);
		try {
			if (attachs != null) {
				for (int i = 0; i < attachs.length(); i++) {
					this.dbfs.put(attachs.getString(i));
				}
			}
		} catch (JSONException e) {
			throw new KnownError(e);
		}
		this.remoteHost = remoteHost;
		this.remoteAddr = remoteIp;
		this.mime = mime;
		this.spam = spam;
		// this.name = name;
		this.sender = sender;
		this.recipients = null;
		this.hostId = hostId;
		this.userName = userName;
		this.requestId = GunlukGorevlisi.tek().request(remoteAddr,hostId,userName,"mail", hostId + "-" + dbfs);

		// Copy the recipient list
		if (recipients != null) {
			Iterator<MailAddress> theIterator = recipients.iterator();
			this.recipients = new ArrayList<MailAddress>();
			while (theIterator.hasNext()) {
				this.recipients.add(theIterator.next());
			}
		}
	}

	public static JSONObject createJson(Integer hostId, String userName, String sender, String[] dbfs, JSONObject mime,
			String remoteHost, String remoteAddr, String rid) throws KnownError {
		try {
			JSONObject ret = new JSONObject();
			JSONArray a = new JSONArray();
			if (dbfs != null) {
				for (String string : dbfs) {
					a.put(string);
				}
			}
			ret.put("dbfs", a);
			ret.put("sender", sender);
			ret.put("mime", mime);
			ret.put("rhost", remoteHost);
			ret.put("raddr", remoteAddr);
			if (hostId != null) {
				ret.put("hostid", hostId);
			}
			if (userName != null) {
				ret.put("user", userName);
			}
			ret.put("requestid", rid);
			return ret;
		} catch (JSONException e) {
			throw new KnownError(e);
		}
	}

	@Override
	public JSONObject getJson() throws KnownError {
		JSONObject jo = new JSONObject();
		try {
			jo.put("dbfs", dbfs);
			jo.put("mime", mime);
			jo.put("sender", sender.toString());
			JSONArray ja = new JSONArray();
			for (MailAddress it : recipients) {
				ja.put(it.toString());
			}
			jo.put("recipients", ja);
			jo.put("rhost", remoteHost);
			jo.put("raddr", remoteAddr);
			if (hostId != null) {
				jo.put("hostid", hostId);
			}
			if (userName != null) {
				jo.put("user", userName);
			}
			if (spam != 0) {
				jo.put("spam", spam);
			}
			if (requestId != null) {
				jo.put("requestid", requestId);
			}

			return jo;
		} catch (JSONException e) {
			throw new KnownError(e);
		}
	}

	public static EmailImpl create(String remoteHost, String remoteIp, Integer hostId, String userName, int spam,
			MailAddress mailAddress, List<MailAddress> rcpts, File f) throws MessagingException, FileNotFoundException,
			KnownError, AddressException {
		FileInputStream is = new FileInputStream(f);
		MimeMessage mm = new MimeMessage(Session.getInstance(), is);
		AttachAdderDbfs aad = new AttachAdderDbfs();

		JSONObject jo = JsonMime.toJson(aad, mm);
		// JSONObject jo = mm.getJson(aad);
		String dbfs = DbfsGorevlisi.tek().make(0, f, true);

		// com.bilgidoku.rom.gunluk.Sistem.errln("Created dbfs:"+dbfs);

		EmailImpl mail = new EmailImpl(remoteHost, remoteIp, hostId, userName, dbfs, aad.ja, jo, spam, mailAddress,
				rcpts);
		return mail;
	}

	/**
	 * Get the recipients of this MailImpl.
	 * 
	 * @return the recipients of this MailImpl
	 */
	public Collection<MailAddress> getRecipients() {
		return recipients;
	}

	/**
	 * Get the sender of this MailImpl.
	 * 
	 * @return the sender of this MailImpl
	 */
	public MailAddress getSender() {
		return sender;
	}

	/**
	 * Get the remote host associated with this MailImpl.
	 * 
	 * @return the remote host associated with this MailImpl
	 */
	public String getRemoteHost() {
		return remoteHost;
	}

	/**
	 * Get the remote address associated with this MailImpl.
	 * 
	 * @return the remote address associated with this MailImpl
	 */
	public String getRemoteAddr() {
		return remoteAddr;
	}

	/**
	 * Set the sender of this MailImpl.
	 * 
	 * @param sender
	 *            the sender of this MailImpl
	 */
	public void setSender(MailAddress sender) {
		this.sender = sender;
	}

	@Override
	public String[] dbfs() throws KnownError {
		try {
			String[] ret = new String[dbfs.length()];
			for (int i = 0; i < ret.length; i++) {
				ret[i] = dbfs.getString(i);
			}
			return ret;
		} catch (JSONException e) {
			throw new KnownError(e);
		}
	}

	@Override
	public InputStream getInputStream() throws KnownError {
		try {
			return DbfsGorevlisi.tek().getInputStream(0, dbfs.getString(0));
		} catch (FileNotFoundException | JSONException e) {
			throw new KnownError(e);
		}
	}

	@Override
	public long getMailSize() throws KnownError {
		try {
			return DbfsGorevlisi.tek().get(0, dbfs.getString(0)).length();
		} catch (JSONException e) {
			throw new KnownError(e);
		}
	}

	@Override
	public String getName() {
		if (dbfs == null || dbfs.length() == 0)
			return "noname";
		try {
			return dbfs.getString(0);
		} catch (JSONException e) {
			return "errorname";
		}
	}

	@Override
	public void setRecipients(Collection<MailAddress> recipients) {
		this.recipients = recipients;
	}

	@Override
	public void setFromRepository(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public String getDeliveryError() {
		return this.deliveryError;
	}

	@Override
	public void deliveryError(String cause) {
		this.deliveryError = cause;
	}

	@Override
	public SMTPMessage getInputMessage() throws KnownError {
		InputStream is;
		try {
			is = DbfsGorevlisi.tek().getInputStream(0, dbfs.getString(0));
			SMTPMessage sm = new SMTPMessage(Session.getInstance(), is);
			return sm;
		} catch (FileNotFoundException | MessagingException | JSONException e) {
			throw new KnownError(e);
		}
	}

	@Override
	public void symLink(int hostIdTo) throws KnownError {
		try {
			for (int i = 0; i < dbfs.length(); i++) {
				String itm;
				itm = dbfs.getString(i);
				DbfsGorevlisi.tek().symlink(itm, hostIdTo);
			}
		} catch (JSONException e) {
			throw new KnownError(e);
		}
	}

	@Override
	public JSONObject getMimeJson() {
		return mime;
	}

	@Override
	public void summary(StringBuilder sb) throws KnownError {
		JsonMime.summary(mime, sb);
	}

	@Override
	public Integer getHostId() {
		return this.hostId;
	}

	@Override
	public String getUserName() {
		return this.userName;
	}

	@Override
	public int spam() {
		return spam;
	}

	@Override
	public String getRequestId() {
		return this.requestId;
	}
}
