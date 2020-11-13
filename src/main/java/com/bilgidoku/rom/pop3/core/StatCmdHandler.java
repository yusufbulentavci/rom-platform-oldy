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

package com.bilgidoku.rom.pop3.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pop3.POP3Response;
import com.bilgidoku.rom.pop3.POP3Session;
import com.bilgidoku.rom.protokol.protocols.Request;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.protokol.protocols.handler.CommandHandler;
import com.bilgidoku.rom.shared.err.KnownError;

/**
 * Handles STAT command
 */
public class StatCmdHandler implements CommandHandler<POP3Session> {
	private static final Collection<String> COMMANDS = Collections.unmodifiableCollection(Arrays.asList("STAT"));

	/**
	 * Handler method called upon receipt of a STAT command. Returns the number
	 * of messages in the mailbox and its aggregate size.
	 */
	@SuppressWarnings("unchecked")
	public Response onCommand(POP3Session session, Request request) {
		if (session.getHandlerState() == POP3Session.TRANSACTION) {
			try {
				session.stat();
				StringBuilder responseBuffer = new StringBuilder(32).append(session.mailCount()).append(" ")
						.append(session.mailSize());
				return new POP3Response(POP3Response.OK_RESPONSE, responseBuffer.toString());
			} catch (KnownError e) {
				Sistem.printStackTrace(e, "While taking stat");
				return POP3Response.ERR;
			}
			//
			// List<MessageMetaData> uidList = (List<MessageMetaData>)
			// session.getAttachment(POP3Session.UID_LIST, State.Transaction);
			// List<Long> deletedUidList = (List<Long>)
			// session.getAttachment(POP3Session.DELETED_UID_LIST,
			// State.Transaction);
			// long size = 0;
			// int count = 0;
			// if (uidList.isEmpty() == false) {
			// List<MessageMetaData> validResults = new
			// ArrayList<MessageMetaData>();
			// for (int i = 0; i < uidList.size(); i++) {
			// MessageMetaData data = uidList.get(i);
			// if (deletedUidList.contains(data.getUid()) == false) {
			// size += data.getSize();
			// count++;
			// validResults.add(data);
			// }
			// }
			// }

		} else {
			return POP3Response.ERR;
		}
	}

	/**
	 * @see com.bilgidoku.rom.protokol.protocols.handler.CommandHandler#getImplCommands()
	 */
	public Collection<String> getImplCommands() {
		return COMMANDS;
	}

}
