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

package com.bilgidoku.rom.protokol.protocols;

import com.bilgidoku.rom.protokol.protocols.handler.ProtocolHandlerChain;


/**
 * Basic {@link Protocol} implementation 
 *
 */
public abstract class ProtocolImpl implements Protocol{
    private final ProtocolHandlerChain chain;
    private final ProtocolConfiguration config;

    public ProtocolImpl(ProtocolHandlerChain chain, ProtocolConfiguration config) {
        this.chain = chain;
        this.config = config;
    }
    
    public ProtocolHandlerChain getProtocolChain() {
        return chain;
    }

    public abstract ProtocolSession newSession(ProtocolTransport transport); 
//    {
//        return new ProtocolSessionImpl(transport, config);
//    }

    public ProtocolConfiguration getConfiguration() {
        return config;
    }

}
