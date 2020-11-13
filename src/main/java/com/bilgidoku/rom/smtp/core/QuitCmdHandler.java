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
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.QuitHook;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.smtp.SMTPSession;

/**
 * Handles QUIT command
 */
public class QuitCmdHandler extends AbstractHookableCmdHandler<QuitHook> {
	private static final MC mc=new MC(QuitCmdHandler.class);

    /**
     * The name of the command handled by the command handler
     */
    private static final Collection<String> COMMANDS = Collections.unmodifiableCollection(Arrays.asList("QUIT"));

    private static final Response SYNTAX_ERROR;
    static {
        SMTPResponse response = new SMTPResponse(
                SMTPRetCode.SYNTAX_ERROR_COMMAND_UNRECOGNIZED, DSNStatus
                .getStatus(DSNStatus.PERMANENT,
                        DSNStatus.DELIVERY_INVALID_ARG)
                + " Unexpected argument provided with QUIT command");
        response.setEndSession(true);
        SYNTAX_ERROR = response.immutable();
    }
    
    private static final Astate quiterror=mc.c("quit-syntax-error");
    
    /**
     * Handler method called upon receipt of a QUIT command. This method informs
     * the client that the connection is closing.
     * 
     * @param session
     *            SMTP session object
     * @param argument
     *            the argument passed in with the command by the SMTP client
     */
    private Response doQUIT(SMTPSession session, String argument) {
        if ((argument == null) || (argument.length() == 0)) {
            StringBuilder response = new StringBuilder();
            response.append(
                    DSNStatus.getStatus(DSNStatus.SUCCESS,
                            DSNStatus.UNDEFINED_STATUS)).append(" ").append(
                    session.getConfiguration().getHelloName()).append(
                    " Service closing transmission channel");
            SMTPResponse ret = new SMTPResponse(SMTPRetCode.SYSTEM_QUIT, response);
            ret.setEndSession(true);
            return ret;
        } else {
        	quiterror.more();
            return SYNTAX_ERROR;
        }
    }

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
        return doQUIT(session, parameters);
    }

    /**
     * @see com.bilgidoku.rom.smtp.core.AbstractHookableCmdHandler#doFilterChecks(com.bilgidoku.rom.smtp.SMTPSession,
     *      java.lang.String, java.lang.String)
     */
    protected Response doFilterChecks(SMTPSession session, String command,
            String parameters) {
        return null;
    }

    /**
     * @see com.bilgidoku.rom.smtp.core.AbstractHookableCmdHandler#getHookInterface()
     */
    protected Class<QuitHook> getHookInterface() {
        return QuitHook.class;
    }

    /**
     * {@inheritDoc}
     */
    protected HookResult callHook(QuitHook rawHook, SMTPSession session, String parameters) {
        return rawHook.doQuit(session);
    }

}
