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

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import com.bilgidoku.rom.smtp.MailEnvelopeImpl;
import com.bilgidoku.rom.smtp.SMTPResponse;
import com.bilgidoku.rom.smtp.SMTPRetCode;
import com.bilgidoku.rom.smtp.dsn.DSNStatus;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.HookResultHook;
import com.bilgidoku.rom.smtp.hook.HookReturnCode;
import com.bilgidoku.rom.smtp.hook.MessageHook;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.protokol.protocols.handler.ExtensibleHandler;
import com.bilgidoku.rom.protokol.protocols.handler.LineHandler;
import com.bilgidoku.rom.protokol.protocols.handler.WiringException;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.protokol.protocols.ProtocolSession;
import com.bilgidoku.rom.smtp.SMTPSession;

/**
 * This class handles the actual calling of the {@link MessageHook}
 * implementations to queue the message. If no {@link MessageHook} return OK or
 * DECLINED it will write back an error to the client to report the problem
 * while trying to queue the message
 * 
 */
public class DataLineMessageHookHandler implements DataLineFilter,
		ExtensibleHandler {
	private final static MC mc = new MC(DataLineMessageHookHandler.class);

	private static final Response ERROR_PROCESSING_MESSAGE = new SMTPResponse(
			SMTPRetCode.LOCAL_ERROR, DSNStatus.getStatus(DSNStatus.TRANSIENT,
					DSNStatus.UNDEFINED_STATUS) + " Error processing message")
			.immutable();

	private List<?> messageHandlers;

	private List<?> rHooks;

	private static final Astate pme = mc.c("process-line-io-error");

	public Response onLine(final SMTPSession session, ByteBuffer line,
			LineHandler<SMTPSession> next) {
		MailEnvelopeImpl env = session.getMailEnvelope();
		OutputStream out = env.getMessageOutputStream();
		try {
			// 46 is "."
			// Stream terminated
			int c = line.get();
			if (line.remaining() == 2 && c == 46) {
				out.flush();
				out.close();

				Response response = processExtensions(session, env);
				session.popLineHandler();
				session.resetState();
				return response;

				// DotStuffing.
			} else if (c == 46 && line.get() == 46) {
				byte[] bline = readBytes(line);
				out.write(bline, 1, bline.length - 1);
				// Standard write
			} else {
				// TODO: maybe we should handle the Header/Body recognition here
				// and if needed let a filter to cache the headers to apply some
				// transformation before writing them to output.
				out.write(readBytes(line));
			}
			out.flush();
		} catch (IOException e) {
			pme.more();
			session.resetState();
			return ERROR_PROCESSING_MESSAGE;
		}
		return null;
	}

	private byte[] readBytes(ByteBuffer line) {
		line.rewind();
		byte[] bline;
		if (line.hasArray()) {
			bline = line.array();
		} else {
			bline = new byte[line.remaining()];
			line.get(bline);
		}
		return bline;
	}

	/**
	 * @param session
	 */
	protected Response processExtensions(SMTPSession session,
			MailEnvelopeImpl mail) {

		if (mail != null && messageHandlers != null) {
			int count = messageHandlers.size();
			for (int i = 0; i < count; i++) {
				MessageHook rawHandler = (MessageHook) messageHandlers.get(i);
//				session.getLogger().debug(
//						"executing message handler " + rawHandler);

				long start = System.currentTimeMillis();
				HookResult hRes = rawHandler.onMessage(session, mail);
				long executionTime = System.currentTimeMillis() - start;

				if (rHooks != null) {
					for (int i2 = 0; i2 < rHooks.size(); i2++) {
						Object rHook = rHooks.get(i2);
//						session.getLogger().debug("executing hook " + rHook);

						hRes = ((HookResultHook) rHook).onHookResult(session,
								hRes, executionTime, rawHandler);
					}
				}

				SMTPResponse response = AbstractHookableCmdHandler
						.calcDefaultSMTPResponse(hRes);

				// if the response is received, stop processing of command
				// handlers
				if (response != null) {
					return response;
				}
			}

			// Not queue the message!
			SMTPResponse response = AbstractHookableCmdHandler
					.calcDefaultSMTPResponse(new HookResult(HookReturnCode.DENY));
			return response;

		}

		return null;
	}

	/**
	 * @see com.bilgidoku.rom.protokol.protocols.handler.ExtensibleHandler#wireExtensions(java.lang.Class,
	 *      java.util.List)
	 */
	@SuppressWarnings("rawtypes")
	public void wireExtensions(Class interfaceName, List extension)
			throws WiringException {
		if (MessageHook.class.equals(interfaceName)) {
			this.messageHandlers = extension;
			checkMessageHookCount(messageHandlers);
		} else if (HookResultHook.class.equals(interfaceName)) {
			this.rHooks = extension;
		}
	}

	protected void checkMessageHookCount(List<?> messageHandlers)
			throws WiringException {
		if (messageHandlers.size() == 0) {
			throw new WiringException("No messageHandler configured");
		}
	}

	/**
	 * @see com.bilgidoku.rom.protokol.protocols.handler.ExtensibleHandler#getMarkerInterfaces()
	 */
	public List<Class<?>> getMarkerInterfaces() {
		List<Class<?>> classes = new LinkedList<Class<?>>();
		classes.add(MessageHook.class);
		classes.add(HookResultHook.class);
		return classes;
	}

}