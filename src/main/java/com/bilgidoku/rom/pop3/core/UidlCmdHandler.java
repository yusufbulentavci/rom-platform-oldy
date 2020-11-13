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
import java.util.HashSet;
import java.util.Set;

import com.bilgidoku.rom.mail.MailOnDb;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pop3.POP3Response;
import com.bilgidoku.rom.pop3.POP3Session;
import com.bilgidoku.rom.protokol.protocols.Request;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.protokol.protocols.handler.CommandHandler;
import com.bilgidoku.rom.shared.err.KnownError;

/**
 * Handles UIDL command
 */
public class UidlCmdHandler implements CommandHandler<POP3Session>, CapaCapability {
    private static final Collection<String> COMMANDS = Collections.unmodifiableCollection(Arrays.asList("UIDL"));
    private static final Set<String> CAPS = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("UIDL")));

    /**
     * Handler method called upon receipt of a UIDL command. Returns a listing
     * of message ids to the client.
     */
    @SuppressWarnings("unchecked")
    public Response onCommand(POP3Session session, Request request) {
//        POP3Response response = null;
        String parameters = request.getArgument();
		if (session.getHandlerState() == POP3Session.TRANSACTION) {
//            List<MessageMetaData> uidList = (List<MessageMetaData>) session.getAttachment(POP3Session.UID_LIST, State.Transaction);
//            List<Long> deletedUidList = (List<Long>) session.getAttachment(POP3Session.DELETED_UID_LIST, State.Transaction);
            //                String identifier = session.getUserMailbox().getIdentifier();
			if (parameters == null) {

			    
				try {
					POP3Response response = new POP3Response(POP3Response.OK_RESPONSE, "unique-id listing follows");
					for (int i = 0; i < session.mails().size(); i++) {
						if (session.isDeleted(i))
							continue;
						MailOnDb m = session.mail(i+1);
			            StringBuilder responseBuffer = new StringBuilder().append(i + 1).append(" ").append(m.getUid());
			            response.appendLine(responseBuffer.toString());
					}
					response.appendLine(".");
					return response;
				} catch (KnownError e) {
					Sistem.printStackTrace(e);
					return POP3Response.ERR;
				}
			    
//			    for (int i = 0; i < uidList.size(); i++) {
//			        MessageMetaData metadata = uidList.get(i);
//			        if (deletedUidList.contains(metadata.getUid()) == false) {
//			            StringBuilder responseBuffer = new StringBuilder().append(i + 1).append(" ").append(metadata.getUid());
//			            response.appendLine(responseBuffer.toString());
//			        }
//			    }

			} else {
			    int num = 0;
			    try {
			        num = Integer.parseInt(parameters);
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

		            StringBuilder responseBuffer = new StringBuilder(64).append(num).append(" ").append(m.getUid());
		            return new POP3Response(POP3Response.OK_RESPONSE, responseBuffer.toString());
			        
//			        MessageMetaData metadata = MessageMetaDataUtils.getMetaData(session, num);
//
//			        if (metadata == null) {
//			            StringBuilder responseBuffer = new StringBuilder(64).append("Message (").append(num).append(") does not exist.");
//			            return  new POP3Response(POP3Response.ERR_RESPONSE, responseBuffer.toString());
//			        }
//
//			        if (deletedUidList.contains(metadata.getUid()) == false) {
//			            StringBuilder responseBuffer = new StringBuilder(64).append(num).append(" ").append(metadata.getUid());
//			            response = new POP3Response(POP3Response.OK_RESPONSE, responseBuffer.toString());
//			        } else {
//			            StringBuilder responseBuffer = new StringBuilder(64).append("Message (").append(num).append(") already deleted.");
//			            response = new POP3Response(POP3Response.ERR_RESPONSE, responseBuffer.toString());
//			        }
			    } catch (IndexOutOfBoundsException npe) {
			        StringBuilder responseBuffer = new StringBuilder(64).append("Message (").append(num).append(") does not exist.");
			        return new POP3Response(POP3Response.ERR_RESPONSE, responseBuffer.toString());
			    } catch (NumberFormatException nfe) {
			        StringBuilder responseBuffer = new StringBuilder(64).append(parameters).append(" is not a valid number");
			        return new POP3Response(POP3Response.ERR_RESPONSE, responseBuffer.toString());
			    } catch (KnownError e) {
					return POP3Response.ERR;
				}
			}
            
        } else {
            return POP3Response.ERR;
        }
    }

    /**
     * @see org.apache.james.pop3server.core.CapaCapability#getImplementedCapabilities(com.bilgidoku.rom.session.james.pop3server.POP3Session)
     */
    @SuppressWarnings("unchecked")
    public Set<String> getImplementedCapabilities(POP3Session session) {
        if (session.getHandlerState() == POP3Session.TRANSACTION) {
            return CAPS;
        } else {
            return Collections.EMPTY_SET;
        }
    }

    /**
     * @see com.bilgidoku.rom.protokol.protocols.handler.CommandHandler#getImplCommands()
     */
    public Collection<String> getImplCommands() {
        return COMMANDS;
    }
}
