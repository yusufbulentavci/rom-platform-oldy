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

package com.bilgidoku.rom.smtp;

import com.bilgidoku.rom.haber.HaberGorevlisi;
import com.bilgidoku.rom.haber.TalkUtil;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.mail.Email;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.smtp.dsn.DSNStatus;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.HookReturnCode;

/**
 * Queue the message
 */
public class ToProcessMailHook implements JamesMessageHook {

	
	/**
	 * Adds header to the message
	 * 
	 * @see org.apache.james.smtpserver#onMessage(SMTPSession)
	 */
	public HookResult onMessage(SMTPSession session, Email mail) {

		// session.getLogger().debug("sending mail");

		try {
			JSONObject jo = TalkUtil.m(0, "mail.process", mail.getJson());

			HaberGorevlisi.tek().send(jo, mail.getRequestId());

			// Collection<MailAddress> theRecipients = mail.getRecipients();
			// String recipientString = "";
			// if (theRecipients != null) {
			// recipientString = theRecipients.toString();
			// }
			// if (session.getLogger().isInfoEnabled()) {
			// StringBuilder infoBuffer = new
			// StringBuilder(256).append("Successfully spooled mail ").append(mail.getName()).append(" from ").append(mail.getSender()).append(" on ").append(session.getRemoteAddress().getAddress().toString()).append(" for ").append(recipientString);
			// session.getLogger().info(infoBuffer.toString());
			// }
		} catch (JSONException e) {
			Sistem.printStackTrace(e, "SendMailHandler:(" + mail.getName() + ")" + e.getMessage());
			return new HookResult(HookReturnCode.DENYSOFT, DSNStatus.getStatus(DSNStatus.TRANSIENT,
					DSNStatus.UNDEFINED_STATUS) + " Error processing message.");
		} catch (KnownError e) {
			Sistem.printStackTrace(e, "SendMailHandler:(" + mail.getName() + ")");
			return new HookResult(HookReturnCode.DENYSOFT, DSNStatus.getStatus(DSNStatus.TRANSIENT,
					DSNStatus.UNDEFINED_STATUS) + " Error processing message.");
		}
		return new HookResult(HookReturnCode.OK, DSNStatus.getStatus(DSNStatus.SUCCESS, DSNStatus.CONTENT_OTHER)
				+ " Message received");
	}

}
