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

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bilgidoku.rom.protokol.protocols.ProtocolSession;
import com.bilgidoku.rom.protokol.protocols.Response;


/**
 * A special {@link LineHandler} which will "buffer" the received lines till a point and the push them all at
 * one to the {@link #onLines(ProtocolSession, Collection)} method
 * 
 *
 * @param <S>
 */
public abstract class MultiLineHandler<S extends ProtocolSession> implements LineHandler<S>{

    
    @SuppressWarnings("unchecked")
    public Response onLine(S session, ByteBuffer line) {
        Collection<ByteBuffer> lines = (List<ByteBuffer>) session.getBufferedLines();
        if (lines == null)  {
            lines = new ArrayList<ByteBuffer>();
            session.setBufferedLines(lines);
        }
        lines.add(line);
        if (isReady(session, line)) {
            return onLines(session, (Collection<ByteBuffer>) session.setBufferedLines(null));
        }
        return null;
    }

    /**
     * Return <code>true</code> if the buffered lines are ready to get pushed to the {@link #onLines(ProtocolSession, Collection)} method
     * 
     * @param session
     * @param line
     * @return ready
     */
    protected abstract boolean isReady(S session, ByteBuffer line);
    
    /**
     * Handle the buffered lines
     * 
     * @param session
     * @param lines
     * @return response
     */
    protected abstract Response onLines(S session, Collection<ByteBuffer> lines);
}
