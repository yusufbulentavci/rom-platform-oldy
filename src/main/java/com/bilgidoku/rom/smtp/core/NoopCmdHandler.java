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
import com.bilgidoku.rom.protokol.protocols.Request;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.protokol.protocols.handler.CommandHandler;
import com.bilgidoku.rom.smtp.SMTPSession;

/**
  * Handles NOOP command
  */
public class NoopCmdHandler implements CommandHandler<SMTPSession> {

    /**
     * The name of the command handled by the command handler
     */
    private static final Collection<String> COMMANDS = Collections.unmodifiableCollection(Arrays.asList("NOOP"));

    private static final Response NOOP = new SMTPResponse(SMTPRetCode.MAIL_OK, DSNStatus.getStatus(DSNStatus.SUCCESS,DSNStatus.UNDEFINED_STATUS)+" OK").immutable();

    /**
     * Handler method called upon receipt of a NOOP command.
     * Just sends back an OK and logs the command.
     *
     */
    public Response onCommand(SMTPSession session, Request request) {
        return NOOP;
    }
    
    /**
     * @see com.bilgidoku.rom.protokol.protocols.handler.CommandHandler#getImplCommands()
     */
    public Collection<String> getImplCommands() {
    	return COMMANDS;
    }

}
