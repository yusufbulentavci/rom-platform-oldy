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

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.bilgidoku.rom.mail.MailOnDb;
import com.bilgidoku.rom.pop3.POP3Response;
import com.bilgidoku.rom.pop3.POP3Session;
import com.bilgidoku.rom.pop3.POP3StreamResponse;
import com.bilgidoku.rom.protokol.protocols.Request;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.protokol.protocols.handler.CommandHandler;
import com.bilgidoku.rom.shared.err.KnownError;

/**
 * Handles RETR command
 */
public class RetrCmdHandler implements CommandHandler<POP3Session> {

    private static final Collection<String> COMMANDS = Collections.unmodifiableCollection(Arrays.asList("RETR"));
    private static final Response SYNTAX_ERROR = new POP3Response(POP3Response.ERR_RESPONSE, "Usage: RETR [mail number]").immutable();
    private static final Response ERROR_MESSAGE_RETRIEVE = new POP3Response(POP3Response.ERR_RESPONSE, "Error while retrieving message.").immutable();

    /**
     * Handler method called upon receipt of a RETR command. This command
     * retrieves a particular mail message from the mailbox.
     */
    @SuppressWarnings("unchecked")
    public Response onCommand(POP3Session session, Request request) {
        String parameters = request.getArgument();
        if (session.getHandlerState() == POP3Session.TRANSACTION) {
            int num = 0;
            try {
                num = Integer.parseInt(parameters.trim());
            } catch (Exception e) {
                return SYNTAX_ERROR;
            }
			if (session.isDeleted(num)) {
				StringBuilder responseBuffer = new StringBuilder(64).append("Message (").append(num)
						.append(") already deleted.");
				return new POP3Response(POP3Response.ERR_RESPONSE, responseBuffer.toString());
			}
			MailOnDb m = session.mail(num);
			if (m == null) {
				StringBuilder responseBuffer = new StringBuilder(64).append("Message (").append(num)
						.append(") does not exist.");
				return new POP3Response(POP3Response.ERR_RESPONSE, responseBuffer.toString());
			}
			
			try {
			  InputStream in = new CRLFTerminatedInputStream(new ExtraDotInputStream(m.getInputStream()));
              return new POP3StreamResponse(POP3Response.OK_RESPONSE, "Message follows", in);
//                MessageMetaData data = MessageMetaDataUtils.getMetaData(session, num);
//
//                if (data == null) {
//                    StringBuilder responseBuffer = new StringBuilder(64).append("Message (").append(num).append(") does not exist.");
//                    response = new POP3Response(POP3Response.ERR_RESPONSE, responseBuffer.toString());
//                    return response;
//                }
//                List<Long> deletedUidList = (List<Long>) session.getAttachment(POP3Session.DELETED_UID_LIST, State.Transaction);
//
//                Long uid = data.getUid();
//                if (deletedUidList.contains(uid) == false) {
//                    InputStream content = session.getUserMailbox().getMessage(uid);
//
//                    if (content != null) {
//                        InputStream in = new CRLFTerminatedInputStream(new ExtraDotInputStream(content));
//                        response = new POP3StreamResponse(POP3Response.OK_RESPONSE, "Message follows", in);
//                        return response;
//                    } else {
//                        StringBuilder responseBuffer = new StringBuilder(64).append("Message (").append(num).append(") does not exist.");
//                        response = new POP3Response(POP3Response.ERR_RESPONSE, responseBuffer.toString());
//                    }
//                } else {
//                    StringBuilder responseBuffer = new StringBuilder(64).append("Message (").append(num).append(") already deleted.");
//                    response = new POP3Response(POP3Response.ERR_RESPONSE, responseBuffer.toString());
//                }
            } catch (KnownError ioe) {
                return ERROR_MESSAGE_RETRIEVE;
            }
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
