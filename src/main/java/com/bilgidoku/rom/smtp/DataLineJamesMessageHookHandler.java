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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.bilgidoku.rom.dkim.DKIMVerifier;
import com.bilgidoku.rom.dkim.HeaderSkippingOutputStream;
import com.bilgidoku.rom.dkim.MimeMessageHeaders;
import com.bilgidoku.rom.dkim.api.BodyHasher;
import com.bilgidoku.rom.dkim.api.Headers;
import com.bilgidoku.rom.dkim.api.SignatureRecord;
import com.bilgidoku.rom.dkim.exceptions.FailException;
import com.bilgidoku.rom.epostatemel.javam.mail.MessagingException;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.AddressException;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.MailAddress;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.MimeMessage;
import com.bilgidoku.rom.epostatemel.mail.util.CRLFOutputStream;
import com.bilgidoku.rom.gunluk.GunlukGorevlisi;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.mail.Email;
import com.bilgidoku.rom.mail.EmailImpl;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.protokol.LifecycleUtil;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.protokol.protocols.handler.ExtensibleHandler;
import com.bilgidoku.rom.protokol.protocols.handler.LineHandler;
import com.bilgidoku.rom.protokol.protocols.handler.WiringException;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.smtp.core.AbstractHookableCmdHandler;
import com.bilgidoku.rom.smtp.core.DataLineFilter;
import com.bilgidoku.rom.smtp.core.fastfail.RomOnMail;
import com.bilgidoku.rom.smtp.dsn.DSNStatus;
import com.bilgidoku.rom.smtp.hook.Hook;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.HookResultHook;
import com.bilgidoku.rom.smtp.hook.MessageHook;

/**
 * Handles the calling of JamesMessageHooks
 */
public class DataLineJamesMessageHookHandler implements DataLineFilter, ExtensibleHandler {
	private static final MC mc = new MC(DataLineJamesMessageHookHandler.class);

	public static final String DKIM_AUTH_RESULT_ATTRIBUTE = "jDKIM.AUTHRESULT";

	private List<JamesMessageHook> messageHandlers;

	private List<HookResultHook> rHooks;

	private List<MessageHook> mHandlers;

	private static final Astate x1 = mc.c("unexpected-error-parsing-data");
	private static final Astate x2 = mc.c("invalid-email-address");

	protected static List<SignatureRecord> verify(DKIMVerifier verifier, MimeMessage message, boolean forceCRLF)
			throws MessagingException, FailException {
		Headers headers = new MimeMessageHeaders(message);
		BodyHasher bh = verifier.newBodyHasher(headers);
		try {
			if (bh != null) {
				OutputStream os = new HeaderSkippingOutputStream(bh.getOutputStream());
				if (forceCRLF)
					os = new CRLFOutputStream(os);
				message.writeTo(os);
				bh.getOutputStream().close();
			}

		} catch (IOException e) {
			throw new MessagingException("Exception calculating bodyhash: " + e.getMessage(), e);
		}

		return verifier.verify(bh);
	}

	private static final Astate blockedCounter=mc.c("spam_blocked-count");
	
	public Response onLine(SMTPSession session, ByteBuffer lineByteBuffer, LineHandler<SMTPSession> next) {

		byte[] line = new byte[lineByteBuffer.remaining()];
		lineByteBuffer.get(line, 0, line.length);

		try {
			OutputStream out = session.getMimeStreamSource();

			// 46 is "."
			// Stream terminated
			if (line.length == 3 && line[0] == 46) {
				out.flush();
				out.close();

				List<SmtpMailAddress> recipientCollection = session.getRcptList();
				SmtpMailAddress sender = session.getSender();

				List<MailAddress> rcpts = new ArrayList<MailAddress>();
				for (SmtpMailAddress address : recipientCollection) {
					rcpts.add(new MailetMailAddressAdapter(address));
				}

				File f = session.getMimeFile();
				int spam;
				try {
					spam = RomOnMail.check(session);
				} catch (BlockMail e1) {
					blockedCounter.more();
					return new SMTPResponse(SMTPRetCode.AUTH_FAILED, e1.getReason());
				}
				
				
				try {

//					   mailImpl.setRemoteHost(session.getRemoteAddress().getHostName());
//			            mailImpl.setRemoteAddr(session.getRemoteAddress().getAddress().getHostAddress());
//					
					
					EmailImpl mail = EmailImpl.create(session.getRemoteAddress().getHostName(), session.getRemoteAddress().getAddress().getHostAddress(),
							session.optHostId(), session.optUserName(), spam, new MailetMailAddressAdapter(sender), rcpts, f);

					Response response = processExtensions(session, mail);

					if(response!=null && !response.isSuccess()){
						GunlukGorevlisi.tek().response(mail.getRequestId(), false, "smtp");
					}
					
					session.popLineHandler();
					return response;

				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					Sistem.printStackTrace(e);
				}
				// catch (KnownError e) {
				// // TODO probably return a temporary problem
				// x1.more(e);
				// return new SMTPResponse(SMTPRetCode.LOCAL_ERROR,
				// "Unexpected error handling DATA stream.");
				// }
				finally {
					// LifecycleUtil.dispose(mmiss);
				}

				// DotStuffing.
			} else if (line[0] == 46 && line[1] == 46) {
				out.write(line, 1, line.length - 1);
				// Standard write
			} else {
				// TODO: maybe we should handle the Header/Body recognition here
				// and if needed let a filter to cache the headers to apply some
				// transformation before writing them to output.
				out.write(line);
			}
		} catch (IOException e) {
			Sistem.printStackTrace(e);
			// LifecycleUtil.dispose(mmiss);
			SMTPResponse response = new SMTPResponse(SMTPRetCode.LOCAL_ERROR, DSNStatus.getStatus(DSNStatus.TRANSIENT,
					DSNStatus.UNDEFINED_STATUS) + " Error processing message: " + e.getMessage());
			x1.more();
			return response;
		} catch (AddressException e) {
			Sistem.printStackTrace(e);
			// LifecycleUtil.dispose(mmiss);
			SMTPResponse response = new SMTPResponse(SMTPRetCode.LOCAL_ERROR, DSNStatus.getStatus(DSNStatus.TRANSIENT,
					DSNStatus.UNDEFINED_STATUS) + " Error processing message: " + e.getMessage());
			x2.more();
			return response;
		} catch (KnownError e) {
			Sistem.printStackTrace(e);
			// LifecycleUtil.dispose(mmiss);
			SMTPResponse response = new SMTPResponse(SMTPRetCode.LOCAL_ERROR, DSNStatus.getStatus(DSNStatus.TRANSIENT,
					DSNStatus.UNDEFINED_STATUS) + " Internal error: ");
			return response;
		}
		return null;
	}

	private static final Astate x3 = mc.c("unable-to-obtain-output-stream");

	/**
	 * @param session
	 */
	protected Response processExtensions(SMTPSession session, Email mail) {
		if (mail != null && messageHandlers != null) {
			try {
				OutputStream out = null;
				out = session.getMimeStreamSource();

				for (int i = 0; i < mHandlers.size(); i++) {
					MessageHook rawHandler = mHandlers.get(i);
					// session.getLogger().debug("executing james message handler "
					// + rawHandler);
					long start = System.currentTimeMillis();

					HookResult hRes = rawHandler.onMessage(session, new MailToMailEnvelopeWrapper(mail, out));
					long executionTime = System.currentTimeMillis() - start;

					if (rHooks != null) {
						for (int i2 = 0; i2 < rHooks.size(); i2++) {
							Object rHook = rHooks.get(i2);
							// session.getLogger().debug("executing hook " +
							// rHook);
							hRes = ((HookResultHook) rHook).onHookResult(session, hRes, executionTime, rawHandler);
						}
					}

					SMTPResponse response = AbstractHookableCmdHandler.calcDefaultSMTPResponse(hRes);

					// if the response is received, stop processing of command
					// handlers
					if (response != null) {
						return response;
					}
				}

				int count = messageHandlers.size();
				for (int i = 0; i < count; i++) {
					Hook rawHandler = (Hook) messageHandlers.get(i);
					// session.getLogger().debug("executing james message handler "
					// + rawHandler);
					long start = System.currentTimeMillis();
					HookResult hRes = ((JamesMessageHook) rawHandler).onMessage(session, (Email) mail);
					long executionTime = System.currentTimeMillis() - start;
					if (rHooks != null) {
						for (int i2 = 0; i2 < rHooks.size(); i2++) {
							Object rHook = rHooks.get(i2);
							// session.getLogger().debug("executinghook " +
							// rHook);
							hRes = ((HookResultHook) rHook).onHookResult(session, hRes, executionTime, rawHandler);
						}
					}

					SMTPResponse response = AbstractHookableCmdHandler.calcDefaultSMTPResponse(hRes);

					// if the response is received, stop processing of command
					// handlers
					if (response != null) {
						return response;
					}
				}
			} finally {
				// Dispose the mail object and remove it
				if (mail != null) {
					LifecycleUtil.dispose(mail);
					mail = null;
				}
				// do the clean up
				session.resetState();
			}
		}
		return null;
	}

	/**
	 * @see com.bilgidoku.rom.protokol.protocols.handler.ExtensibleHandler#wireExtensions(java.lang.Class,
	 *      java.util.List)
	 */
	public void wireExtensions(Class interfaceName, List extension) throws WiringException {
		if (JamesMessageHook.class.equals(interfaceName)) {
			this.messageHandlers = extension;
			if (messageHandlers == null || messageHandlers.size() == 0) {
				throw new WiringException("No messageHandler configured");
			}
		} else if (MessageHook.class.equals(interfaceName)) {
			this.mHandlers = extension;
		} else if (HookResultHook.class.equals(interfaceName)) {

			this.rHooks = extension;
		}
	}

	/**
	 * @see com.bilgidoku.rom.protokol.protocols.handler.ExtensibleHandler#getMarkerInterfaces()
	 */
	public List<Class<?>> getMarkerInterfaces() {
		List<Class<?>> classes = new LinkedList<Class<?>>();
		classes.add(JamesMessageHook.class);
		classes.add(MessageHook.class);
		classes.add(HookResultHook.class);
		return classes;
	}

	protected class MailToMailEnvelopeWrapper implements MailEnvelope {
		private Email mail;
		private OutputStream out;

		public MailToMailEnvelopeWrapper(Email mail, OutputStream out) {
			this.mail = mail;
			this.out = out;
		}

		/**
		 * @throws KnownError
		 * @see com.bilgidoku.rom.smtp.MailEnvelope#getMessageInputStream()
		 */
		public InputStream getMessageInputStream() throws IOException, KnownError {
			return mail.getInputStream();
		}

		/**
		 * @see com.bilgidoku.rom.smtp.MailEnvelope#getMessageOutputStream()
		 */
		public OutputStream getMessageOutputStream() throws IOException {
			return out;
		}

		/**
		 * @see com.bilgidoku.rom.smtp.MailEnvelope#getRecipients()
		 */
		public List<SmtpMailAddress> getRecipients() {

			ArrayList<SmtpMailAddress> aa = new ArrayList<SmtpMailAddress>();
			for (MailAddress mailAddress : mail.getRecipients()) {
				try {
					aa.add(new ProtocolMailAddressAdapter(mailAddress));
				} catch (MailAddressException e) {
					// TODO Auto-generated catch block
					Sistem.printStackTrace(e);
				}
			}
			return aa;
		}

		/**
		 * @see com.bilgidoku.rom.smtp.MailEnvelope#getSender()
		 */
		public SmtpMailAddress getSender() {
			try {
				return new ProtocolMailAddressAdapter(mail.getSender());
			} catch (MailAddressException e) {
				// should not occur here, cause it should have happened before
				throw new RuntimeException(e);
			}
		}

		/**
		 * @throws KnownError
		 * @see com.bilgidoku.rom.smtp.MailEnvelope#getSize()
		 */
		public long getSize() throws KnownError {
			return (long) mail.getMailSize();
		}

	}
}
