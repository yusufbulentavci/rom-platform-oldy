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

package com.bilgidoku.rom.smtp.core.esmtp;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.bilgidoku.rom.smtp.SMTPResponse;
import com.bilgidoku.rom.smtp.SMTPRetCode;
import com.bilgidoku.rom.smtp.SMTPStartTlsResponse;
import com.bilgidoku.rom.smtp.dsn.DSNStatus;
import com.bilgidoku.rom.protokol.protocols.Request;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.protokol.protocols.handler.CommandHandler;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.smtp.SMTPSession;

/**
 * Handles STARTTLS command
 */
public class StartTlsCmdHandler implements CommandHandler<SMTPSession>, EhloExtension {
	private static final MC mc=new MC(StartTlsCmdHandler.class);
    /**
     * The name of the command handled by the command handler
     */
    private final static String COMMAND_NAME = "STARTTLS";
    private final static Collection<String> COMMANDS = Collections.unmodifiableCollection(Arrays.asList(COMMAND_NAME));
    private final static List<String> FEATURES = Collections.unmodifiableList(Arrays.asList(COMMAND_NAME));

    private static final Response TLS_ALREADY_ACTIVE = new SMTPResponse("500", DSNStatus.getStatus(DSNStatus.PERMANENT, DSNStatus.DELIVERY_INVALID_CMD) + " TLS already active RFC2487 5.2").immutable();
    private static final Response READY_FOR_STARTTLS = new SMTPStartTlsResponse("220", DSNStatus.getStatus(DSNStatus.SUCCESS, DSNStatus.UNDEFINED_STATUS) + " Ready to start TLS").immutable();
    private static final Response SYNTAX_ERROR = new SMTPResponse("501 " + DSNStatus.getStatus(DSNStatus.PERMANENT, DSNStatus.DELIVERY_INVALID_ARG) + " Syntax error (no parameters allowed) with STARTTLS command").immutable();
    private static final Response NOT_SUPPORTED = new SMTPResponse(SMTPRetCode.SYNTAX_ERROR_COMMAND_UNRECOGNIZED, DSNStatus.getStatus(DSNStatus.PERMANENT, DSNStatus.DELIVERY_INVALID_CMD) +" Command " + COMMAND_NAME +" unrecognized.").immutable();
    /**
     * @see com.bilgidoku.rom.protokol.protocols.handler.CommandHandler#getImplCommands()
     */
    public Collection<String> getImplCommands() {
        return COMMANDS;
    }

    private static final Astate tala=mc.c("starttls-already-active");
    private static final Astate tse=mc.c("starttls-syntax-error");
    private static final Astate tns=mc.c("starttls-not-supported");
    /**
     * Handler method called upon receipt of a STARTTLS command. Resets
     * message-specific, but not authenticated user, state.
     * 
     */
    public Response onCommand(SMTPSession session, Request request) {
        if (session.isStartTLSSupported()) {
            if (session.isTLSStarted()) {
            	tala.more();
                return TLS_ALREADY_ACTIVE;
            } else {
                String parameters = request.getArgument();
                if ((parameters == null) || (parameters.length() == 0)) {
                    return READY_FOR_STARTTLS;
                } else {
                	tse.more();
                    return SYNTAX_ERROR;
                } 
            }

        } else {
        	tns.more();
            return NOT_SUPPORTED;
        }
    }

    /**
     * @see com.bilgidoku.rom.smtp.core.esmtp.EhloExtension#getImplementedEsmtpFeatures(com.bilgidoku.rom.smtp.SMTPSession)
     */
    @SuppressWarnings("unchecked")
    public List<String> getImplementedEsmtpFeatures(SMTPSession session) {
        // SMTP STARTTLS
        if (!session.isTLSStarted() && session.isStartTLSSupported()) {
            return FEATURES;
        } else {
            return Collections.EMPTY_LIST;
        }

    }

}
