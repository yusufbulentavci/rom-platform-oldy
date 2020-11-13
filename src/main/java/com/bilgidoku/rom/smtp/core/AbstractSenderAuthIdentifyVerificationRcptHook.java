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
package com.bilgidoku.rom.smtp.core;

import java.util.Locale;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.smtp.SMTPRetCode;
import com.bilgidoku.rom.smtp.SmtpMailAddress;
import com.bilgidoku.rom.smtp.dsn.DSNStatus;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.HookReturnCode;
import com.bilgidoku.rom.smtp.hook.RcptHook;
import com.bilgidoku.rom.protokol.protocols.ProtocolSession;
import com.bilgidoku.rom.smtp.SMTPSession;

/**
 * Handler which check if the authenticated user is the same as the one used as
 * MAIL FROM
 */
public abstract class AbstractSenderAuthIdentifyVerificationRcptHook implements
		RcptHook {
	private static final HookResult INVALID_AUTH = new HookResult(
			HookReturnCode.DENY, SMTPRetCode.BAD_SEQUENCE, DSNStatus.getStatus(
					DSNStatus.PERMANENT, DSNStatus.SECURITY_AUTH)
					+ " Incorrect Authentication for Specified Email Address");

	/**
	 * @throws KnownError 
	 * @see com.bilgidoku.rom.smtp.hook.RcptHook#doRcpt(com.bilgidoku.rom.smtp.SMTPSession,
	 *      org.apache.SmtpMailAddress.MailAddress,
	 *      org.apache.SmtpMailAddress.MailAddress)
	 */
	public HookResult doRcpt(SMTPSession session, SmtpMailAddress sender,
			SmtpMailAddress rcpt) throws KnownError {
		if (session.getProtocolUser() != null) {
			String authUser = (session.getProtocolUser()).toLowerCase(Locale.US);
			SmtpMailAddress senderAddress = session.getSender();
			String username = null;

			if (senderAddress != null) {
				username = senderAddress.toString();
			}

			// Check if the sender address is the same as the user which was
			// used to authenticate.
			// Its important to ignore case here to fix JAMES-837. This is save
			// todo because if the handler is called
			// the user was already authenticated
			if ((senderAddress == null)
					|| (!authUser.equalsIgnoreCase(username))
					|| (!isLocalDomain(senderAddress.getDomain()))) {
				return INVALID_AUTH;
			}
		}
		return HookResult.declined();
	}

	/**
	 * Return true if the given domain is a local domain for this server
	 * 
	 * @param domain
	 * @return isLocal
	 * @throws KnownError 
	 */
	protected abstract boolean isLocalDomain(String domain) throws KnownError;
}
