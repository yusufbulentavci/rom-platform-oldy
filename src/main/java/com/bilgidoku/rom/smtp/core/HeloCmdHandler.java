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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.bilgidoku.rom.smtp.SMTPResponse;
import com.bilgidoku.rom.smtp.SMTPRetCode;
import com.bilgidoku.rom.smtp.dsn.DSNStatus;
import com.bilgidoku.rom.smtp.hook.HeloHook;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.protokol.protocols.ProtocolSession;
import com.bilgidoku.rom.smtp.SMTPSession;
import com.bilgidoku.rom.protokol.protocols.ProtocolSession.State;

/**
 * Handles HELO command
 */
public class HeloCmdHandler extends AbstractHookableCmdHandler<HeloHook> {

	private static final String COMMAND_NAME = "HELO";
    /**
     * The name of the command handled by the command handler
     */
    private static final Collection<String> COMMANDS = Collections.unmodifiableCollection(Arrays.asList(COMMAND_NAME));

    private static final Response DOMAIN_REQUIRED =  new SMTPResponse(SMTPRetCode.SYNTAX_ERROR_ARGUMENTS,
            DSNStatus.getStatus(DSNStatus.PERMANENT,
                    DSNStatus.DELIVERY_INVALID_ARG)
                    + " Domain address required: " + COMMAND_NAME).immutable();
    /**
     * @see com.bilgidoku.rom.protokol.protocols.handler.CommandHandler#getImplCommands()
     */
    public Collection<String> getImplCommands() {
        return COMMANDS;
    }

    /**
     * @see com.bilgidoku.rom.smtp.core.AbstractHookableCmdHandler#doCoreCmd(com.bilgidoku.rom.smtp.SMTPSession,
     *      java.lang.String, java.lang.String)
     */
    protected Response doCoreCmd(SMTPSession session, String command,
            String parameters) {
    	session.setHeloMode(COMMAND_NAME);
        StringBuilder response = new StringBuilder();
        response.append(session.getConfiguration().getHelloName()).append(
                " Hello ").append(parameters).append(" [").append(
                session.getRemoteAddress().getAddress().getHostAddress()).append("])");
        return new SMTPResponse(SMTPRetCode.MAIL_OK, response);
    }

    /**
     * @see com.bilgidoku.rom.smtp.core.AbstractHookableCmdHandler#doFilterChecks(com.bilgidoku.rom.smtp.SMTPSession,
     *      java.lang.String, java.lang.String)
     */
    protected Response doFilterChecks(SMTPSession session, String command,
            String parameters) {
        session.resetState();

        if (parameters == null) {
            return DOMAIN_REQUIRED;
        } else {
        	session.setHeloName(parameters);
            // store provided name
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    protected Class<HeloHook> getHookInterface() {
        return HeloHook.class;
    }


    /**
     * {@inheritDoc}
     */
    protected HookResult callHook(HeloHook rawHook, SMTPSession session, String parameters) {
        return rawHook.doHelo(session, parameters);
    }


}
