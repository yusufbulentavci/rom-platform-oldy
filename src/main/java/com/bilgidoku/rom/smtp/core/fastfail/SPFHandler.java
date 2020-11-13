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

import com.bilgidoku.rom.dns.exceptions.SPFErrorConstants;
import com.bilgidoku.rom.mail.Email;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.protokol.protocols.handler.LifecycleAwareProtocolHandler;
import com.bilgidoku.rom.smtp.JamesMessageHook;
import com.bilgidoku.rom.smtp.SMTPRetCode;
import com.bilgidoku.rom.smtp.SMTPSession;
import com.bilgidoku.rom.smtp.SmtpMailAddress;
import com.bilgidoku.rom.smtp.dsn.DSNStatus;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.HookReturnCode;
import com.bilgidoku.rom.smtp.hook.MailHook;
import com.bilgidoku.rom.smtp.hook.RcptHook;
import com.bilgidoku.rom.spf.SPF;
import com.bilgidoku.rom.spf.executor.SPFResult;

/**
 * This command handler can be used to reject emails with not match the SPF record of the sender domain -->
 * If checkAuthNetworks is set to true sender domain will be checked also for clients that -->
 * are allowed to relay. Default is false. -->
 * 
 * @author bulent.avci
 *
 */
public class SPFHandler implements JamesMessageHook, MailHook, RcptHook, LifecycleAwareProtocolHandler {
	private static final MC mc = new MC(SPFHandler.class);

	//    public static final String SPF_BLOCKLISTED = "SPF_BLOCKLISTED";
	//
	//    public static final String SPF_DETAIL = "SPF_DETAIL";
	//
	//    public static final String SPF_TEMPBLOCKLISTED = "SPF_TEMPBLOCKLISTED";
	//
	//    public final static String SPF_HEADER = "SPF_HEADER";

	public final static String SPF_HEADER_MAIL_ATTRIBUTE_NAME = "org.apache.james.spf.header";

	/** If set to true the mail will also be rejected on a softfail */
	private final boolean blockSoftFail;// = false;

	private final boolean blockPermError;// = true;

	private SPF spf = new SPF();

	public SPFHandler(boolean blockSoftFail, boolean blockPermError) {
		this.blockSoftFail = blockSoftFail;
		this.blockPermError = blockPermError;
	}

	private static final Astate x1 = mc.c("no-sender-or-helo-ehlo");

	/**
	 * Calls a SPF check
	 * 
	 * @param session
	 *            SMTP session object
	 */
	private void doSPFCheck(SMTPSession session, SmtpMailAddress sender) {
		String heloEhlo = session.getCurHeloName();

		// We have no Sender or HELO/EHLO yet return false
		if (sender == null || heloEhlo == null) {
			x1.more();
		} else {

			String ip = session.getRemoteAddress().getAddress().getHostAddress();

			SPFResult result = spf.checkSPF(ip, sender.toString(), heloEhlo);

			String spfResult = result.getResult();

			String explanation = "Blocked - see: " + result.getExplanation();

			// Store the header
			session.setSpfHeader(result.getHeaderText());

			//            session.getLogger().info("Result for " + ip + " - " + sender + " - " + heloEhlo + " = " + spfResult);

			// Check if we should block!
			if ((spfResult.equals(SPFErrorConstants.FAIL_CONV))
					|| (spfResult.equals(SPFErrorConstants.SOFTFAIL_CONV) && blockSoftFail)
					|| (spfResult.equals(SPFErrorConstants.PERM_ERROR_CONV) && blockPermError)) {

				if (spfResult.equals(SPFErrorConstants.PERM_ERROR_CONV)) {
					explanation = "Block caused by an invalid SPF record";
				}
				session.setSpfDetail(explanation);
				session.setSpfBlockListed(Boolean.TRUE);

			} else if (spfResult.equals(SPFErrorConstants.TEMP_ERROR_CONV)) {
				session.setSpfTempBlockListed(Boolean.TRUE);
			}

		}

	}

	/**
	 * @see com.bilgidoku.rom.smtp.hook.RcptHook#doRcpt(com.bilgidoku.rom.smtp.SMTPSession,
	 *      org.apache.SmtpMailAddress.MailAddress, org.apache.SmtpMailAddress.MailAddress)
	 */
	public HookResult doRcpt(SMTPSession session, SmtpMailAddress sender, SmtpMailAddress rcpt) {
		// No need spf check for local users
		if (session.getProtocolUser() == null) {
			// Check if session is blocklisted
			if (session.getSpfBlockListed() != null) {
				return new HookResult(HookReturnCode.DENY, DSNStatus.getStatus(DSNStatus.PERMANENT,
						DSNStatus.SECURITY_AUTH) + " " + session.getSpfTempBlockListed());
			} else if (session.getSpfTempBlockListed() != null) {
				return new HookResult(HookReturnCode.DENYSOFT, SMTPRetCode.LOCAL_ERROR, DSNStatus.getStatus(
						DSNStatus.TRANSIENT, DSNStatus.NETWORK_DIR_SERVER)
						+ " "
						+ "Temporarily rejected: Problem on SPF lookup");
			}
		}
		return new HookResult(HookReturnCode.DECLINED);
	}

	/**
	 * @see com.bilgidoku.rom.smtp.hook.MailHook#doMail(com.bilgidoku.rom.smtp.SMTPSession,
	 *      org.apache.SmtpMailAddress.MailAddress)
	 */
	public HookResult doMail(SMTPSession session, SmtpMailAddress sender) {
		// No need spf check for local users
		if (session.getProtocolUser() == null) {
			doSPFCheck(session, sender);
		}
		return new HookResult(HookReturnCode.DECLINED);
	}

	/**
	 * @see org.apache.james.smtpserver.JamesMessageHook#onMessage(com.bilgidoku.rom.smtp.SMTPSession,
	 *      com.Email.bilgidoku.rom.mail.mailprocess.Mail)
	 */
	public HookResult onMessage(SMTPSession session, Email mail) {
		// Store the spf header as attribute for later using

		//		mail.setAttribute(SPF_HEADER_MAIL_ATTRIBUTE_NAME, (String) session.getAttachment(SPF_HEADER, State.Transaction));
		return null;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
