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

package com.bilgidoku.rom.mail.maildo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import com.bilgidoku.rom.epostatemel.javam.mail.internet.MailAddress;
import com.bilgidoku.rom.ilk.util.Genel;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.mail.Email;
import com.bilgidoku.rom.mail.MailProcessContext;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.session.OturumGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;

public class RemoteDelivery extends AbstractMailDo {
	
	private static final MC mc=new MC(RemoteDelivery.class);

	/** Flag to define verbose logging messages. */
	private boolean isDebug = true;

	/** Default properties for the JavaMail Session */
	private Properties defprops = new Properties();



	private final String heloName = Genel.getHostName();

	private boolean usePriority=false;

	public RemoteDelivery(MailProcessContext context, MailDoConfig config) {
		super(context, config);
	}


	public String getMailetInfo() {
		return "RemoteDelivery Mailet";
	}

	private static final Astate service=mc.c("service");
	private static final Astate each=mc.c("each");
	
	
	/**
	 * For this message, we take the list of recipients, organize these into
	 * distinct servers, and duplicate the message for each of these servers,
	 * and then call the deliver (messagecontainer) method for each
	 * server-specific messagecontainer ... that will handle storing it in the
	 * outgoing queue if needed.
	 * 
	 * @param mail
	 *            org.apache.mailet.Mail
	 */
	public void service(Email mail) {
		service.more();

		if(mail.getHostId()==null || mail.getUserName()==null){
			OturumGorevlisi.tek().unauthSmtpSendReq(mail.getRemoteHost(), mail.getRemoteAddr());
			return;
		}
		
		// Do I want to give the internal key, or the message's Message ID
		if (isDebug) {
			Sistem.outln("Remotely delivering mail " + mail.getName());
		}
		
		Collection<MailAddress> recipients = mail.getRecipients();

//		if (usePriority) {
//			mail.setPriority(MailPrioritySupport.HIGH_PRIORITY);
//		}
		// Must first organize the recipients into distinct servers (name
		// made case insensitive)
		Hashtable<String, Collection<MailAddress>> targets = new Hashtable<String, Collection<MailAddress>>();
		for (Iterator<MailAddress> i = recipients.iterator(); i.hasNext();) {
			MailAddress target = i.next();
			String targetServer = target.getDomain().toLowerCase(Locale.US);
			Collection<MailAddress> temp = targets.get(targetServer);
			if (temp == null) {
				temp = new ArrayList<MailAddress>();
				targets.put(targetServer, temp);
			}
			temp.add(target);
		}

		// We have the recipients organized into distinct servers... put
		// them into the
		// delivery store organized like this... this is ultra inefficient I
		// think...

		// Store the new message containers, organized by server, in the
		// outgoing mail repository
		String name = mail.getName();
		for (Map.Entry<String, Collection<MailAddress>> entry : targets.entrySet()) {
			if (isDebug) {
				StringBuilder logMessageBuffer = new StringBuilder(128).append("Sending mail to ")
						.append(entry.getValue()).append(" on host ").append(entry.getKey());
				Sistem.outln(logMessageBuffer.toString());
			}
			mail.setRecipients(entry.getValue());
//			StringBuilder nameBuffer = new StringBuilder(128).append(name).append("-to-").append(entry.getKey());
//			mail.setName(nameBuffer.toString());
			try {
				each.more();
				context.remoteDeliver(mail);
			} catch (KnownError e) {
				each.failed();
				Sistem.printStackTrace(e, "Unexpected remote deliver failed:");
			}
		
		}
	}

	/**
	 * Stops all the worker threads that are waiting for messages. This method
	 * is called by the Mailet container before taking this Mailet out of
	 * service.
	 */
	public synchronized void destroy() {
		// Mark flag so threads from this Mailet stop themselves
//		destroyed = true;

		// Wake up all threads from waiting for an accept
		// for (Iterator<Thread> i = workersThreads.iterator(); i.hasNext();) {
		// Thread t = i.next();
		// t.interrupt();
		// }
		notifyAll();
	}


	protected String getHeloName() throws KnownError {
		return heloName;
	}




	@Override
	public String getMailetName() {
		// TODO Auto-generated method stub
		return null;
	}

}
