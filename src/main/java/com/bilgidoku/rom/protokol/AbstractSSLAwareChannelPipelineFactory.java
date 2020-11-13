package com.bilgidoku.rom.protokol;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

public abstract class AbstractSSLAwareChannelPipelineFactory extends AbstractChannelPipelineFactory{

    
    private String[] enabledCipherSuites = null;

    public AbstractSSLAwareChannelPipelineFactory(int timeout,
            int maxConnections) {
        super(timeout, maxConnections);
    }

    public AbstractSSLAwareChannelPipelineFactory(int timeout,
            int maxConnections, String[] enabledCipherSuites) {
        this(timeout, maxConnections);
        
        // We need to copy the String array becuase of possible security issues.
        // See https://issues.apache.org/jira/browse/PROTOCOLS-18
        if (enabledCipherSuites != null) {
            this.enabledCipherSuites = new String[enabledCipherSuites.length];
            for (int i = 0; i < enabledCipherSuites.length; i++) {
                this.enabledCipherSuites[i] = new String(enabledCipherSuites[i]);
            }
        }
    }

    
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
    	super.initChannel(ch);
        ChannelPipeline pipeline =  ch.pipeline();

        if (isSSLSocket(ch)) {
            // We need to set clientMode to false.
            // See https://issues.apache.org/jira/browse/JAMES-1025
            SSLEngine engine = getSSLContext().createSSLEngine();
            engine.setUseClientMode(false);
            if (enabledCipherSuites != null && enabledCipherSuites.length > 0) {
                engine.setEnabledCipherSuites(enabledCipherSuites);
            }
            pipeline.addFirst(HandlerConstants.SSL_HANDLER, new SslHandler(engine));
        }
    }

    /**
     * Return if the socket is using SSL/TLS
     * @param ch 
     * 
     * @return isSSL
     */
    protected abstract boolean isSSLSocket(SocketChannel ch);
    
    /**
     * Return the SSL context
     * 
     * @return context
     */
    protected abstract SSLContext getSSLContext();
}
