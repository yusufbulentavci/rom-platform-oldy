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

import java.nio.ByteBuffer;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.protokol.protocols.handler.LineHandler;
import com.bilgidoku.rom.protokol.protocols.handler.ProtocolHandler;
import com.bilgidoku.rom.smtp.SMTPSession;


/**
 * DataLineFilter are used to check the Data stream while the message is
 * being received.
 */
public interface DataLineFilter extends ProtocolHandler{
    
    /**
     * Handle line processing
     * 
     * @param session
     * @param line
     * @param next
     * @throws KnownError 
     */
    Response onLine(SMTPSession session, ByteBuffer line, LineHandler<SMTPSession> next) throws KnownError;
}