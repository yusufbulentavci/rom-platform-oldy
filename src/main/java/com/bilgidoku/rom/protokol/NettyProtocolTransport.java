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

//import org.jboss.netty.buffer.ChannelBuffers;
//import org.jboss.netty.channel.Channel;
//import org.jboss.netty.channel.ChannelFutureListener;
//import org.jboss.netty.channel.DefaultFileRegion;
//import org.jboss.netty.handler.ssl.SslHandler;
//import org.jboss.netty.handler.stream.ChunkedStream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.util.Iterator;

import javax.net.ssl.SSLEngine;

import com.bilgidoku.rom.protokol.protocols.AbstractProtocolTransport;
import com.bilgidoku.rom.protokol.protocols.CombinedInputStream;
import com.bilgidoku.rom.protokol.protocols.ProtocolSession;
import com.bilgidoku.rom.protokol.protocols.handler.LineHandler;

/**
 * A Netty implementation of a ProtocolTransport
 */
public class NettyProtocolTransport extends AbstractProtocolTransport {
    
    private final Channel channel;
    private final SSLEngine engine;
    private int lineHandlerCount = 0;
    
    public NettyProtocolTransport(Channel channel, SSLEngine engine) {
        this.channel = channel;
        this.engine = engine;
    }

    /**
     * @see com.bilgidoku.rom.protokol.protocols.ProtocolTransport#getRemoteAddress()
     */
    public InetSocketAddress getRemoteAddress() {
        return (InetSocketAddress) channel.remoteAddress();
    }
//
//    /**
//     * @see com.bilgidoku.rom.protokol.protocols.ProtocolTransport#getSockId()
//     */
//    public String getId() {
//        return Integer.toString(channel.id());
//    }

    /**
     * @see com.bilgidoku.rom.protokol.protocols.ProtocolTransport#isTLSStarted()
     */
    public boolean isTLSStarted() {
        return channel.pipeline().get(SslHandler.class) != null;
    }

    /**
     * @see com.bilgidoku.rom.protokol.protocols.ProtocolTransport#isStartTLSSupported()
     */
    public boolean isStartTLSSupported() {
        return engine != null;
    }


    /**
     * @see com.bilgidoku.rom.protokol.protocols.ProtocolTransport#popLineHandler()
     */
    public void popLineHandler() {
        if (lineHandlerCount > 0) {
            channel.pipeline().remove("lineHandler" + lineHandlerCount);
            lineHandlerCount--;
        }
    }

    /**
     * @see com.bilgidoku.rom.protokol.protocols.ProtocolTransport#getPushedLineHandlerCount()
     */
    public int getPushedLineHandlerCount() {
        return lineHandlerCount;
    }

    /**
     * Add the {@link SslHandler} to the pipeline and start encrypting after the next written message
     */
    private void prepareStartTLS() {
        SslHandler filter = new SslHandler(engine, true);
        filter.engine().setUseClientMode(false);
        channel.pipeline().addFirst(HandlerConstants.SSL_HANDLER, filter);
    }

    @Override
    protected void writeToClient(byte[] bytes, ProtocolSession session, boolean startTLS) {
        if (startTLS) {
            prepareStartTLS();
        }
        ByteBuf buffer = Unpooled.copiedBuffer(bytes);
        channel.write(buffer);
        channel.flush();
    }

    @Override
    protected void close() {
        channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }


    @Override
    protected void writeToClient(InputStream in, ProtocolSession session, boolean startTLS) {
        if (startTLS) {
            prepareStartTLS();
        }
        if (!isTLSStarted()) {
            if (in instanceof FileInputStream) {
                FileChannel fChannel = ((FileInputStream) in).getChannel();
                try {
                    channel.writeAndFlush(new DefaultFileRegion(fChannel, 0, fChannel.size()));

                } catch (IOException e) {
                    // We handle this later
                    channel.writeAndFlush(new ChunkedStream(new ExceptionInputStream(e)));
                }
                return;

            } else if (in instanceof CombinedInputStream) {
                Iterator<InputStream> streams = ((CombinedInputStream) in).iterator();
                while(streams.hasNext()) {
                    InputStream pIn = streams.next();
                    if (pIn instanceof FileInputStream) {
                        FileChannel fChannel = ((FileInputStream) in).getChannel();
                        try {
                            channel.writeAndFlush(new DefaultFileRegion(fChannel, 0, fChannel.size()));
                            return;

                        } catch (IOException e) {
                            // We handle this later
                            channel.writeAndFlush(new ChunkedStream(new ExceptionInputStream(e)));
                        }                    
                    } else {
                        channel.writeAndFlush(new ChunkedStream(in));
                    }
                }
                return;
            }
        } 
        channel.writeAndFlush(new ChunkedStream(in));
    }

//    /*
//     * (non-Javadoc)
//     * @see com.bilgidoku.rom.protokol.protocols.ProtocolTransport#setReadable(boolean)
//     */
//    public void setReadable(boolean readable) {
//        channel.setReadable(readable);
//    }
//
//    /*
//     * (non-Javadoc)
//     * @see com.bilgidoku.rom.protokol.protocols.ProtocolTransport#isReadable()
//     */
//    public boolean isReadable() {
//        return channel.isReadable();
//    }

    /*
     * (non-Javadoc)
     * @see com.bilgidoku.rom.protokol.protocols.ProtocolTransport#getLocalAddress()
     */
    public InetSocketAddress getLocalAddress() {
        return (InetSocketAddress) channel.localAddress();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void pushLineHandler(LineHandler<? extends ProtocolSession> overrideCommandHandler, ProtocolSession session) {
        lineHandlerCount++;
        // Add the linehandler in front of the coreHandler so we can be sure 
        // it is executed with the same ExecutorHandler as the coreHandler (if one exist)
        // 
        // See JAMES-1277
        channel.pipeline().addBefore(HandlerConstants.CORE_HANDLER, "lineHandler" + lineHandlerCount, new LineHandlerUpstreamHandler(session, overrideCommandHandler));
    }
    
   
    /**
     * {@link InputStream} which just re-throw the {@link IOException} on the next {@link #read()} operation.
     * 
     *
     */
    private static final class ExceptionInputStream extends InputStream {
        private final IOException e;

        public ExceptionInputStream(IOException e) {
            this.e = e;
        }
        
        @Override
        public int read() throws IOException {
            throw e;
        }
        
    }

}
