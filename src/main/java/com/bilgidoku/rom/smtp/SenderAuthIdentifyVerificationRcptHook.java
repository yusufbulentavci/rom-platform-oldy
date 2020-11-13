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

import com.bilgidoku.rom.kurum.KurumGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.smtp.core.AbstractSenderAuthIdentifyVerificationRcptHook;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.HookReturnCode;

/**
 * Handler which check if the authenticated user is incorrect
 */
public class SenderAuthIdentifyVerificationRcptHook extends AbstractSenderAuthIdentifyVerificationRcptHook {


//	private static final HostService domains = ServiceDiscovery.getService(HostService.class);

	@Override
	public HookResult doRcpt(SMTPSession session, SmtpMailAddress sender, SmtpMailAddress rcpt) throws KnownError {
		if (session.verifyIdentity()) {
			return super.doRcpt(session, sender, rcpt);
		} else {
			return new HookResult(HookReturnCode.DECLINED);
		}
	}

	/**
	 * @throws KnownError 
	 * @see com.bilgidoku.rom.smtp.core.AbstractSenderAuthIdentifyVerificationRcptHook#isLocalDomain(java.lang.String)
	 */
	protected boolean isLocalDomain(String domain) throws KnownError {
		return KurumGorevlisi.tek().containsDomain(domain);
	}
}
