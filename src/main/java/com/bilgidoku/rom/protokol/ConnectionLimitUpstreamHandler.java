package com.bilgidoku.rom.protokol;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;

import java.util.concurrent.atomic.AtomicInteger;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;


/**
 * {@link ChannelUpstreamHandler} which limit the concurrent connection. 
 * 
 * This handler must be used as singleton when adding it to the {@link ChannelPipeline} to work correctly
 *
 * TODO: Remove when its committed to NETTY. 
 *       https://jira.jboss.org/jira/browse/NETTY-311
 */
@Sharable
public class ConnectionLimitUpstreamHandler extends ChannelInboundHandlerAdapter{
	private MC mc=new MC(ConnectionLimitUpstreamHandler.class);
	
	
    private final AtomicInteger connections = new AtomicInteger(0);
    private volatile int maxConnections = -1;
    
    public ConnectionLimitUpstreamHandler(int maxConnections) {
        this.maxConnections = maxConnections;
    }
    
    public int getConnections() {
        return connections.get();
    }
    
    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }
    
    Astate x1=mc.c("conn-limit-force-to-close");
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	if (maxConnections > 0) {
            int currentCount = connections.incrementAndGet();
            
            if (currentCount > maxConnections) {
            	x1.more(ctx.channel().remoteAddress().toString());
                ctx.channel().close();
            }
        }
    	super.channelActive(ctx);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	if (maxConnections > 0) {
            connections.decrementAndGet();
        }
    	super.channelInactive(ctx);
    }
}
