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

package com.bilgidoku.rom.protokol.protocols.future;

import java.io.InputStream;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.protokol.protocols.StreamResponse;


/**
 * Special {@link FutureResponse} which wraps a {@link StreamResponse} and so provide an async way to get notified about ready responses
 * 
 *
 */
public class FutureStreamResponseImpl extends FutureResponseImpl implements StreamResponse{
	private static final MC mc=new MC(FutureStreamResponseImpl.class);

	private static final Astate responsekind=mc.c("response-is-not-stream");
    /**
     * Set the {@link StreamResponse} to wrap. If a non {@link StreamResponse} is set this implementation will throw an {@link IllegalArgumentException}
     * 
     */
    @Override
    public void setResponse(Response response) {
        if (response instanceof StreamResponse) {
            super.setResponse(response);
        } else {
        	responsekind.more();
            throw new IllegalArgumentException("Response MUST be of type " + StreamResponse.class.getName());
        }
    }

    public InputStream getStream() {
        checkReady();
        return ((StreamResponse) response).getStream();
        
    }
}
