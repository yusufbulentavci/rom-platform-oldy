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



package com.bilgidoku.rom.protokol.protocols.handler;


import java.util.Collection;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.protokol.protocols.ProtocolSession;
import com.bilgidoku.rom.protokol.protocols.Request;
import com.bilgidoku.rom.protokol.protocols.Response;



/**
 * Custom command handlers must implement this interface
 * The command handlers will be Server wide common to all the handlers,
 * therefore the command handlers must store all the state information
 * in the Session object
 */
 public interface CommandHandler<Session extends ProtocolSession> extends ProtocolHandler{
    /**
     * Handle the command
     * @throws KnownError 
    **/
    Response onCommand(Session session, Request request) throws KnownError;
    
    /**
     * Return a Collection of implemented commands
     * 
     * @return Collection which contains implemented commands
     */
    Collection<String> getImplCommands();

}
