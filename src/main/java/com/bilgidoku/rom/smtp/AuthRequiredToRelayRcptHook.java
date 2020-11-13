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

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.kurum.KurumGorevlisi;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.dict.CommonSession;
import com.bilgidoku.rom.session.OturumGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.smtp.dsn.DSNStatus;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.HookReturnCode;
import com.bilgidoku.rom.smtp.hook.RcptHook;

public class AuthRequiredToRelayRcptHook implements RcptHook {
	private static final MC mc = new MC(AuthRequiredToRelayRcptHook.class);


	private static final HookResult USER_NOT_LOCAL = new HookResult(HookReturnCode.DISCONNECT,
			SMTPRetCode.USER_NOT_LOCAL, DSNStatus.getStatus(DSNStatus.PERMANENT, DSNStatus.SECURITY_AUTH)
					+ " Not a local user");

	private static final HookResult TEMP_ERROR = new HookResult(HookReturnCode.DISCONNECT,
			SMTPRetCode.AUTH_TEMPORARY_ERROR, DSNStatus.getStatus(DSNStatus.PERMANENT, DSNStatus.SECURITY_AUTH)
					+ " TEMP ERROR");

	private static final HookResult AUTH_REQUIRED = new HookResult(HookReturnCode.DENY, SMTPRetCode.AUTH_REQUIRED,
			DSNStatus.getStatus(DSNStatus.PERMANENT, DSNStatus.SECURITY_AUTH) + " Authentication Required");
	private static final HookResult RELAYING_DENIED = new HookResult(HookReturnCode.DISCONNECT,
	// sendmail returns 554 (SMTPRetCode.TRANSACTION_FAILED).
	// it is not clear in RFC wether it is better to use 550 or 554.
			SMTPRetCode.MAILBOX_PERM_UNAVAILABLE, DSNStatus.getStatus(DSNStatus.PERMANENT, DSNStatus.SECURITY_AUTH)
					+ " Requested action not taken: relaying denied");

	private static final Astate hackSender = mc.c("hack-sender");
	private static final Astate toInvalidLocal = mc.c("toinvalidlocal");
	private static final Astate fromInvalidSender = mc.c("frominvalidsender");
	private static final Astate relayingDenied = mc.c("relayingdenied");

	/**Session
	 * @throws KnownError 
	 * @see com.bilgidoku.rom.smtp.hook.RcptHook#doRcpt(com.bilgidoku.rom.smtp.SMTPSession,
	 *      org.apache.SmtpMailAddress.MailAddress, org.apache.SmtpMailAddress.MailAddress)
	 */
	public HookResult doRcpt(SMTPSession ses, SmtpMailAddress sender, SmtpMailAddress rcpt) throws KnownError {
		String senderDomain = sender.getDomain();
		String senderUser = sender.getLocalPart();
		String toDomain = rcpt.getDomain();
		String toUser = rcpt.getLocalPart();

		Sistem.outln(senderUser + "@" + senderDomain + "->" + toUser + "@" + toDomain);

		boolean senderLocalDomain = isLocalDomain(senderDomain);
		boolean senderLocalUser = isLocalUser(ses, senderDomain, senderUser);
		boolean toLocalUser = toUser.equals("postmaster") || toUser.equals("abuse") || isLocalUser(ses, toDomain, toUser);
		boolean toLocalDomain = isLocalDomain(toDomain);

		Sistem.outln(senderLocalUser + "@" + senderLocalDomain + "->" + toLocalUser
				+ "@" + toLocalDomain);

		// auth user'lar rcpt tanimlayabilir
		if (!ses.isGuest()) {
			// Sender'in domain'i login oldugu ile ayni olmali
			if (!senderDomain.equals(ses.getDomain().getEmailDomain())) {
				hackSender.more(ses.getUserEmail() + " faked as " + senderUser + "@" + senderDomain);
				return abuse(USER_NOT_LOCAL, ses);
			}
			return HookResult.declined();
		}

		if (toLocalDomain && !toLocalUser) {
			toInvalidLocal.more();
			return abuse(USER_NOT_LOCAL, ses);
		}

		if (senderLocalDomain && !senderLocalUser) {
			fromInvalidSender.more();
			return abuse(USER_NOT_LOCAL, ses);
		}

		if (!toLocalUser) {
			if (senderLocalUser) {
				return AUTH_REQUIRED;
			}
			relayingDenied.more();
			return abuse(RELAYING_DENIED, ses);
		}

		return HookResult.declined();
	}

	private HookResult abuse(HookResult res, SMTPSession session2) {
		boolean ret = OturumGorevlisi.tek().unauthSmtpSendReq(null, session2.getIpAddress());
		if (ret) {
			return TEMP_ERROR;
		}
		return res;
	}

	/**
	 * @throws KnownError 
	 * @see com.bilgidoku.rom.smtp.core.AbstractAuthRequiredToRelayRcptHook#isLocalDomain(java.lang.String)
	 */
	protected boolean isLocalDomain(String domain) {
		return KurumGorevlisi.tek().optDomain(domain)!=null;
	}

	protected boolean isLocalUser(CommonSession session, String domain, String username) throws KnownError {
		return OturumGorevlisi.tek().optUser(session, username , domain)!=null;
	}

}
