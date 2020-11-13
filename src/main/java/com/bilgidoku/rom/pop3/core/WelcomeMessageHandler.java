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

import com.bilgidoku.rom.pop3.POP3Response;
import com.bilgidoku.rom.pop3.POP3Session;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.protokol.protocols.handler.ConnectHandler;

public class WelcomeMessageHandler implements ConnectHandler<POP3Session> {

    /**
     * @see com.bilgidoku.rom.protokol.protocols.handler.ConnectHandler
     * #onConnect(com.bilgidoku.rom.session.james.pop3server.POP3Session)
     */
    public Response onConnect(POP3Session session) {
        StringBuilder responseBuffer = new StringBuilder();
        
        // Generate the timestamp which can be also used with APOP. See RFC1939 APOP
        responseBuffer.append("<").append(session.getSessionID()).append(".").append(System.currentTimeMillis()).append("@").append(session.getConfiguration().getHelloName()).append("> ");
        
        // store the timestamp for later usage
        session.setApopTimestamp(responseBuffer.toString());
        
        // complete the response banner and send it back to the client
        responseBuffer.append("POP3 server (").append(session.getConfiguration().getSoftwareName()).append(") ready ");
        POP3Response response = new POP3Response(POP3Response.OK_RESPONSE, responseBuffer.toString());
        return response;
    }

}