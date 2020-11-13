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
package com.bilgidoku.rom.protokol;

import com.bilgidoku.rom.protokol.protocols.ProtocolSession;
import com.bilgidoku.rom.protokol.protocols.ProtocolSessionImpl;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.protokol.protocols.handler.LineHandler;

//import org.jboss.netty.buffer.ChannelBuffer;
//import org.jboss.netty.channel.ChannelHandlerContext;
//import org.jboss.netty.channel.ChannelUpstreamHandler;
//import org.jboss.netty.channel.MessageEvent;
//import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * {@link ChannelUpstreamHandler} implementation which will call a given {@link LineHandler} implementation
 *
 * @param <S>
 */
public class LineHandlerUpstreamHandler<S extends ProtocolSession> extends ChannelInboundHandlerAdapter  {

    private final LineHandler<S> handler;
    private final S session;
    
    public LineHandlerUpstreamHandler(S session, LineHandler<S> handler) {
        this.handler = handler;
        this.session = session;
    }
    
    
    
    @Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	ByteBuf bb=(ByteBuf) msg;
        Response response = handler.onLine(session, bb.nioBuffer()); 
        if (response != null) {
            // TODO: This kind of sucks but I was not able to come up with something more elegant here
            ((ProtocolSessionImpl)session).getProtocolTransport().writeResponse(response, session);
//            JSONObject jr=response.toReport();
//			JSONObject sr = session.toReport();
//			RunTime.log(LogCmds.out, false, 10, "str", "Session:"+sr.toString() + "\nResponse:"+jr.toString());
			
        }
	}



}
