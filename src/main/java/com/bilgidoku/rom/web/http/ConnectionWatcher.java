package com.bilgidoku.rom.web.http;

import java.util.concurrent.atomic.AtomicLong;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;

public class ConnectionWatcher extends ChannelDuplexHandler{
	private AtomicLong totalRead=new AtomicLong();
	private AtomicLong totalWrite=new AtomicLong();
	
	 /**
     * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	totalRead.addAndGet(calculateSize(msg));
//    	com.bilgidoku.rom.gunluk.Sistem.errln("Total read:"+totalRead.get());
    	super.channelRead(ctx, msg);
    }

    /**
     * Calls {@link ChannelHandlerContext#write(Object, ChannelPromise)} to forward
     * to the next {@link ChannelOutboundHandler} in the {@link ChannelPipeline}.
     *
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
    	totalWrite.addAndGet(calculateSize(msg));
//    	com.bilgidoku.rom.gunluk.Sistem.errln("Total write:"+totalWrite.get());
    	super.write(ctx, msg, promise);
    }
    
    /**
     * Calculate the size of the given {@link Object}.
     *
     * This implementation supports {@link ByteBuf} and {@link ByteBufHolder}. Sub-classes may override this.
     * @param msg       the msg for which the size should be calculated
     * @return size     the size of the msg or {@code -1} if unknown.
     */
    protected long calculateSize(Object msg) {
        if (msg instanceof ByteBuf) {
            return ((ByteBuf) msg).readableBytes();
        }
        if (msg instanceof ByteBufHolder) {
            return ((ByteBufHolder) msg).content().readableBytes();
        }
        return 0;
    }

	public long getWriteByteSize() {
		return this.totalWrite.get();
	}

	public long getReadByteSize() {
		return this.totalRead.get();
	}

}
