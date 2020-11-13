package com.bilgidoku.rom.protokol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;


/**
 * {@link IdleStateHandler} implementation which disconnect the {@link Channel} after a configured
 * idle timeout. Be aware that this handle is not thread safe so it can't be shared across pipelines
 *
 */
public class TimeoutHandler extends IdleStateHandler{

    public TimeoutHandler(int readerIdleTimeSeconds) {
        super(readerIdleTimeSeconds, 0, 0);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent event) throws Exception {
        if (event.state().equals(IdleState.READER_IDLE)) {
            ctx.channel().close();
        }
    }


}