package com.bilgidoku.rom.protokol.testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.bilgidoku.rom.ilk.file.FromResource;
import com.bilgidoku.rom.min.Sistem;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * Handles a client-side channel.
 */
@Sharable
class TelnetClientHandler extends SimpleChannelInboundHandler<String> {

	private String[] dlg;

	boolean sending = false;
	int index = 0;

	public TelnetClientHandler(String res) throws IOException {
		String all = FromResource.loadString(res);
		this.dlg = all.split("---\n");
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println("<<" + msg);
		step(ctx, msg);
		step(ctx, null);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

//	@Override
//	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		step(ctx, null);
//	}

	public void step(ChannelHandlerContext ch, String mext) {
		try {
			int i = index++;
			
			if(i == dlg.length){
				System.out.println("DONE");
				ch.close();
				return;
			}

			String text = dlg[i].trim();
			if (text.length() == 0) {
				throw new RuntimeException("empty at index:" + i);

			}
			if (sending)
				write(ch, text);
			else {
				String read = mext;
				if (!read.matches(text)) {
					throw new RuntimeException("Pattern not matched;\nExpected:" + text + "\nReceived:" + read);
				}
			}
			sending = !sending;

//

		} finally {

		}
	}

	private ChannelFuture write(ChannelHandlerContext ch, String text) {
		return ch.writeAndFlush(text + "\r\n");
	}

}

class TelnetClientInitializer extends ChannelInitializer<SocketChannel> {

	private final StringDecoder DECODER = new StringDecoder();
	private final StringEncoder ENCODER = new StringEncoder();

	private final TelnetClientHandler CLIENT_HANDLER;

	private final SslContext sslCtx;
	private String host;
	private int port;

	public TelnetClientInitializer(SslContext sslCtx, String res, String host, int port) throws IOException {
		this.sslCtx = sslCtx;
		this.host = host;
		this.port = port;
		CLIENT_HANDLER = new TelnetClientHandler(res);
	}

	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline pipeline = ch.pipeline();

		if (sslCtx != null) {
			pipeline.addLast(sslCtx.newHandler(ch.alloc(), host, port));
		}

		// Add the text line codec combination first,
		pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast(DECODER);
		pipeline.addLast(ENCODER);

		// and then business logic.
		pipeline.addLast(CLIENT_HANDLER);
	}
}

/**
 * Simplistic telnet client.
 */
public final class TelnetClient {

	final boolean SSL;
	final String HOST;
	final int PORT;
	private String resource;
	EventLoopGroup group;

	public TelnetClient(String resource, boolean ssl, String host, int port) {
		this.resource = resource;
		this.SSL = ssl;
		this.HOST = host;
		this.PORT = port;
	}

	public void start() throws Exception {
		// Configure SSL.
		final SslContext sslCtx;
		if (SSL) {
			sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		} else {
			sslCtx = null;
		}

		group = new NioEventLoopGroup();
		// try {
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class)
				.handler(new TelnetClientInitializer(sslCtx, resource, HOST, PORT));

		// Start the connection attempt.
		Channel ch = b.connect(HOST, PORT).sync().channel();

		// Read commands from the stdin.
		// ChannelFuture lastWriteFuture = null;
		// BufferedReader in = new BufferedReader(new
		// InputStreamReader(System.in));
		// for (;;) {
		// String line = in.readLine();
		// if (line == null) {
		// break;
		// }
		//
		// // Sends the received line to the server.
		// lastWriteFuture = ch.writeAndFlush(line + "\r\n");
		//
		// // If user typed the 'bye' command, wait until the server closes
		// // the connection.
		// if ("bye".equals(line.toLowerCase())) {
		// ch.closeFuture().sync();
		// break;
		// }
		// }
		//
		// // Wait until all messages are flushed before closing the channel.
		// if (lastWriteFuture != null) {
		// lastWriteFuture.sync();
		// }
		// } finally {
		// group.shutdownGracefully();
		// }
	}

}