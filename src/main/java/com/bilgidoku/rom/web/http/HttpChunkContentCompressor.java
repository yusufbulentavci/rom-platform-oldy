package com.bilgidoku.rom.web.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.HttpContentCompressor;

/**
* @author <a href="mailto:nmaurer@redhat.com">Norman Maurer</a>
*/
public class HttpChunkContentCompressor extends HttpContentCompressor {

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
    if (msg instanceof ByteBuf) {
      // convert ByteBuf to HttpContent to make it work with compression. This is needed as we use the
      // ChunkedWriteHandler to send files when compression is enabled.
      msg = new DefaultHttpContent((ByteBuf) msg);
    }
    super.write(ctx, msg, promise);
  }
}