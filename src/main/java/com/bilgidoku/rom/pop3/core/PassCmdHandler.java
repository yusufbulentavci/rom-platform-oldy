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

import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pop3.POP3Response;
import com.bilgidoku.rom.pop3.POP3Session;
import com.bilgidoku.rom.protokol.protocols.ProtocolSessionExtension;
import com.bilgidoku.rom.protokol.protocols.Request;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.session.OturumGorevlisi;

/**
 * Handles PASS commands.
 */
public class PassCmdHandler extends RsetCmdHandler {
	
	private final static MC mc = new MC(PassCmdHandler.class);

	private final static ProtocolSessionExtension sessionExtension = (ProtocolSessionExtension) OturumGorevlisi.tek().getExtension("pop3");
	
    private static final Collection<String> COMMANDS = Collections.unmodifiableCollection(Arrays.asList("PASS"));
    private static final Response UNEXPECTED_ERROR = new POP3Response(POP3Response.ERR_RESPONSE, "Unexpected error accessing mailbox").immutable();
    protected static final Response AUTH_FAILED = new POP3Response(POP3Response.ERR_RESPONSE, "Authentication failed.").immutable();

    /**
     * Handler method called upon receipt of a PASS command. Reads in and
     * validates the password.
     */
    public Response onCommand(POP3Session session, Request request) {
        String parameters = request.getArgument();
        if (session.getHandlerState() == POP3Session.AUTHENTICATION_USERSET && parameters != null) {
            return doAuth(session, session.getProtocolUser(), parameters);
        } else {
            session.setHandlerState(POP3Session.AUTHENTICATION_READY);
            return AUTH_FAILED;
        }
    }
    
    
    /**
     * Authenticate a user and return the {@link Response}
     * 
     * @param session
     * @param user
     * @param pass
     * @return response
     */
    protected final Response doAuth(POP3Session session, String user, String pass) {
        try {
        	int ret=sessionExtension.protocolLogin(session, user, pass);
    		if (ret==0) {
    			stat(session);
                session.setHandlerState(POP3Session.TRANSACTION);
                StringBuilder responseBuffer = new StringBuilder(64).append("Welcome ").append(session.getProtocolUser());
                return  new POP3Response(POP3Response.OK_RESPONSE, responseBuffer.toString());
    		}else {
    			session.setHandlerState(POP3Session.AUTHENTICATION_READY);
    			return AUTH_FAILED;
    		}
        	

        } catch (Exception e) {
//            session.getLogger().error("Unexpected error accessing mailbox for " + session.getUser(), e);
            session.setHandlerState(POP3Session.AUTHENTICATION_READY);
            return UNEXPECTED_ERROR;
        }
    }

    /**
     * @see com.bilgidoku.rom.protokol.protocols.handler.CommandHandler#getImplCommands()
     */
    public Collection<String> getImplCommands() {
        return COMMANDS;
    }

}
