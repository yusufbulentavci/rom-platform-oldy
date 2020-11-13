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

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.smtp.MailEnvelope;
import com.bilgidoku.rom.smtp.MailEnvelopeImpl;
import com.bilgidoku.rom.smtp.SMTPResponse;
import com.bilgidoku.rom.smtp.SMTPRetCode;
import com.bilgidoku.rom.smtp.SmtpMailAddress;
import com.bilgidoku.rom.smtp.dsn.DSNStatus;
import com.bilgidoku.rom.protokol.protocols.Request;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.protokol.protocols.handler.CommandHandler;
import com.bilgidoku.rom.protokol.protocols.handler.ExtensibleHandler;
import com.bilgidoku.rom.protokol.protocols.handler.LineHandler;
import com.bilgidoku.rom.protokol.protocols.handler.WiringException;
import com.bilgidoku.rom.protokol.protocols.ProtocolSession;
import com.bilgidoku.rom.smtp.SMTPSession;

/**
 * handles DATA command
 */
public class DataCmdHandler implements CommandHandler<SMTPSession>, ExtensibleHandler {

	private static final Response NO_RECIPIENT = new SMTPResponse(SMTPRetCode.BAD_SEQUENCE, DSNStatus.getStatus(
			DSNStatus.PERMANENT, DSNStatus.DELIVERY_OTHER) + " No recipients specified").immutable();
	private static final Response NO_SENDER = new SMTPResponse(SMTPRetCode.BAD_SEQUENCE, DSNStatus.getStatus(
			DSNStatus.PERMANENT, DSNStatus.DELIVERY_OTHER) + " No sender specified").immutable();
	private static final Response UNEXPECTED_ARG = new SMTPResponse(SMTPRetCode.SYNTAX_ERROR_COMMAND_UNRECOGNIZED,
			DSNStatus.getStatus(DSNStatus.PERMANENT, DSNStatus.DELIVERY_INVALID_ARG)
					+ " Unexpected argument provided with DATA command").immutable();
	private static final Response DATA_READY = new SMTPResponse(SMTPRetCode.DATA_READY,
			"Ok Send data ending with <CRLF>.<CRLF>").immutable();
	private static final Collection<String> COMMANDS = Collections.unmodifiableCollection(Arrays.asList("DATA"));

	public static final class DataConsumerLineHandler implements LineHandler<SMTPSession> {

		public SMTPResponse onLine(SMTPSession session, ByteBuffer line) {
			// Discard everything until the end of DATA session
			if (line.remaining() == 3 && line.get() == 46) {
				session.popLineHandler();
			}
			return null;
		}
	}

	public static final class DataLineFilterWrapper implements LineHandler<SMTPSession> {

		private DataLineFilter filter;
		private LineHandler<SMTPSession> next;

		public DataLineFilterWrapper(DataLineFilter filter, LineHandler<SMTPSession> next) {
			this.filter = filter;
			this.next = next;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.bilgidoku.rom.protokol.protocols.handler.LineHandler#onLine(com.bilgidoku
		 * .rom.protocols.ProtocolSession, java.nio.ByteBuffer)
		 */
		public Response onLine(SMTPSession session, ByteBuffer line) throws KnownError {
			line.rewind();
			Response r = filter.onLine(session, line, next);
			return r;
		}

	}

//	public final static String MAILENV = "MAILENV";

	private LineHandler<SMTPSession> lineHandler;

	/**
	 * process DATA command
	 * 
	 */
	public Response onCommand(SMTPSession session, Request request) {
		String parameters = request.getArgument();
		Response response = doDATAFilter(session, parameters);

		if (response == null) {
			return doDATA(session, parameters);
		} else {
			return response;
		}
	}

	/**
	 * Handler method called upon receipt of a DATA command. Reads in message
	 * data, creates header, and delivers to mail server service for delivery.
	 * 
	 * @param session
	 *            SMTP session object
	 * @param argument
	 *            the argument passed in with the command by the SMTP client
	 */
	protected Response doDATA(SMTPSession session, String argument) {
		MailEnvelopeImpl env = createEnvelope(
				session,
				session.getSender(),
				new ArrayList<SmtpMailAddress>(session.getRcptList()));
//		session.setAttachment(MAILENV, env, ProtocolSession.State.Transaction);
		session.setMailEnvelope(env);
		session.pushLineHandler(lineHandler);

		return DATA_READY;
	}

	protected MailEnvelopeImpl createEnvelope(SMTPSession session, SmtpMailAddress sender, List<SmtpMailAddress> recipients) {
		MailEnvelopeImpl env = new MailEnvelopeImpl();
		env.setRecipients(recipients);
		env.setSender(sender);
		return env;
	}

	/**
	 * @see com.bilgidoku.rom.protokol.protocols.handler.CommandHandler#getImplCommands()
	 */
	public Collection<String> getImplCommands() {
		return COMMANDS;
	}

	/**
	 * @see com.bilgidoku.rom.protokol.protocols.handler.ExtensibleHandler#getMarkerInterfaces()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getMarkerInterfaces() {
		List classes = new LinkedList();
		classes.add(DataLineFilter.class);
		return classes;
	}

	/**
	 * @see com.bilgidoku.rom.protokol.protocols.handler.ExtensibleHandler#wireExtensions(java.lang.Class,
	 *      java.util.List)
	 */
	@SuppressWarnings("rawtypes")
	public void wireExtensions(Class interfaceName, List extension) throws WiringException {
		if (DataLineFilter.class.equals(interfaceName)) {

			LineHandler<SMTPSession> lineHandler = new DataConsumerLineHandler();
			for (int i = extension.size() - 1; i >= 0; i--) {
				lineHandler = new DataLineFilterWrapper((DataLineFilter) extension.get(i), lineHandler);
			}

			this.lineHandler = lineHandler;
		}
	}

	protected Response doDATAFilter(SMTPSession session, String argument) {
		if ((argument != null) && (argument.length() > 0)) {
			return UNEXPECTED_ARG;
		}
		if (session.getSender() == null) {
			return NO_SENDER;
		} else if (session.getRcptList() == null) {
			return NO_RECIPIENT;
		}
		return null;
	}

	protected LineHandler<SMTPSession> getLineHandler() {
		return lineHandler;
	}

}
