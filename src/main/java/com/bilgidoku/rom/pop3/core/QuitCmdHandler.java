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
 * Handles QUIT command
 */
public class QuitCmdHandler implements CommandHandler<POP3Session> {
    private static final Collection<String> COMMANDS = Collections.unmodifiableCollection(Arrays.asList("QUIT"));
    private static final Response SIGN_OFF;
    private static final Response SIGN_OFF_NOT_CLEAN;
    static {
        POP3Response response = new POP3Response(POP3Response.OK_RESPONSE, "rom-server signing off.");
        response.setEndSession(true);
        SIGN_OFF = response.immutable();
        
        response = new POP3Response(POP3Response.ERR_RESPONSE, "Some deleted messages were not removed");
        response.setEndSession(true);
        SIGN_OFF_NOT_CLEAN = response.immutable();
    }
    /**
     * Handler method called upon receipt of a QUIT command. This method handles
     * cleanup of the POP3Handler state.
     */
    @SuppressWarnings("unchecked")
    public Response onCommand(POP3Session session, Request request) {
        Response response = SIGN_OFF;
        if (session.getHandlerState() == POP3Session.AUTHENTICATION_READY || session.getHandlerState() == POP3Session.AUTHENTICATION_USERSET) {
            return SIGN_OFF;
        }
        
        try {
			session.commit();
		} catch (KnownError e) {
			Sistem.printStackTrace(e, "Pop3 Quit error");
			response = SIGN_OFF_NOT_CLEAN;
		}
        
//        List<Long> toBeRemoved = (List<Long>) session.getAttachment(key, state)
//        List<Long> toBeRemoved = (List<Long>) session.getAttachment(POP3Session.DELETED_UID_LIST, State.Transaction);
//        Mailbox mailbox = session.getUserMailbox();
//        try {
//            Long[] uids = toBeRemoved.toArray(new Long[toBeRemoved.size()]);
//            mailbox.remove(uids);
//            response = SIGN_OFF;
//        } catch (Exception ex) {
//            response = SIGN_OFF_NOT_CLEAN;
////            session.getLogger().error("Some deleted messages were not removed", ex);
//        }
//        try {
//            mailbox.close();
//        } catch (KnownError e) {
//            // ignore on close
//        }
        return response;
    }

    /**
     * @see com.bilgidoku.rom.protokol.protocols.handler.CommandHandler#getImplCommands()
     */
    public Collection<String> getImplCommands() {
        return COMMANDS;
    }

}
