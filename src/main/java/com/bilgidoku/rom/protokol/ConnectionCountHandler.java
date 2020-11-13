package com.bilgidoku.rom.protokol;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Count connections
 */
@Sharable
public class ConnectionCountHandler extends ChannelInboundHandlerAdapter {

    public AtomicInteger currentConnectionCount = new AtomicInteger();
    public AtomicLong connectionsTillStartup = new AtomicLong();


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        currentConnectionCount.incrementAndGet();
        connectionsTillStartup.incrementAndGet();
        super.channelActive(ctx);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	currentConnectionCount.decrementAndGet();
    	super.channelActive(ctx);
    }
    
    
    /**
     * Return the count of the current open connections
     * 
     * @return count
     */
    public int getCurrentConnectionCount() {
        return currentConnectionCount.get();
    }

    /**
     * Return the count of all connections which where made till the server
     * was started
     * 
     * @return tillCount
     */
    public long getConnectionsTillStartup() {
        return connectionsTillStartup.get();
    }
}
