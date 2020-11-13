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

package com.bilgidoku.rom.smtp.core.fastfail;

import com.bilgidoku.rom.smtp.SMTPRetCode;
import com.bilgidoku.rom.smtp.SmtpMailAddress;
import com.bilgidoku.rom.smtp.dsn.DSNStatus;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.HookReturnCode;
import com.bilgidoku.rom.smtp.hook.RcptHook;
import com.bilgidoku.rom.smtp.SMTPSession;

/**
 * Handler which want todo an recipient check should extend this
 *
 */
public abstract class AbstractValidRcptHandler implements RcptHook {

	/**
	 * @see com.bilgidoku.rom.smtp.hook.RcptHook#doRcpt(com.bilgidoku.rom.smtp.SMTPSession, org.apache.SmtpMailAddress.MailAddress, org.apache.SmtpMailAddress.MailAddress)
	 */
	public HookResult doRcpt(SMTPSession session, SmtpMailAddress sender, SmtpMailAddress rcpt) {
		boolean reject = false;

		if (isLocalDomain(session, rcpt.getDomain()) == false) {
			//                session.getLogger().debug("Unknown domain " + rcpt.getDomain() + " so reject it");

		} else {
			if (isValidRecipient(session, rcpt) == false) {
				reject = true;
			}
		}

		if (reject) {
			//user not exist
			//            session.getLogger().info("Rejected message. Unknown user: " + rcpt.toString());
			return new HookResult(HookReturnCode.DENY, SMTPRetCode.MAILBOX_PERM_UNAVAILABLE, DSNStatus.getStatus(
					DSNStatus.PERMANENT, DSNStatus.ADDRESS_MAILBOX) + " Unknown user: " + rcpt.toString());
		} else {
			return HookResult.declined();
		}
	}

	/**
	 * Return true if email for the given recipient should get accepted
	 * 
	 * @param recipient
	 * @return isValid
	 */
	protected abstract boolean isValidRecipient(SMTPSession session, SmtpMailAddress recipient);

	/**
	 * Return true if the domain is local
	 * 
	 * @param session
	 * @param domain
	 * @return local
	 */
	protected abstract boolean isLocalDomain(SMTPSession session, String domain);
}
