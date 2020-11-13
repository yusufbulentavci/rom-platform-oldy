package com.bilgidoku.rom.protokol;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;





/**
 * Abstract base class which should get used if you MAY need an {@link ExecutionHandler}
 * 
 *
 */
public abstract class AbstractExecutorAwareChannelPipelineFactory extends AbstractSSLAwareChannelPipelineFactory{


    public AbstractExecutorAwareChannelPipelineFactory(int timeout, int maxConnections) {
        super(timeout, maxConnections);
    }
    public AbstractExecutorAwareChannelPipelineFactory(int timeout, int maxConnections, String maxConnectsPerIp, String[] enabledCipherSuites) {
        super(timeout, maxConnections, enabledCipherSuites);
    }
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
    	super.initChannel(ch);
        ChannelPipeline pipeLine = ch.pipeline();
        pipeLine.addBefore(HandlerConstants.CORE_HANDLER, "countHandler", getConnectionCountHandler());
    }

    
    /**
     * REturn the {@link ConnectionCountHandler} to use
     * 
     * @return cHandler
     */
    protected abstract ConnectionCountHandler getConnectionCountHandler();
}
