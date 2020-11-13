package com.bilgidoku.rom.web.uygulamayonetim;

import java.net.InetSocketAddress;

import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.run.KosuGorevlisi;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

class DirectServerPipelineFactory extends ChannelInitializer<SocketChannel> {
	private static final MC mc = new MC(DirectServerPipelineFactory.class);

	private static final Astate gpc = mc.c("get-pipeline");

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		gpc.more();
		// Create a default pipeline implementation.
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast("decoder", new HttpRequestDecoder());

		pipeline.addLast("aggregator", new HttpObjectAggregator(100111222));

		pipeline.addLast("encoder", new HttpResponseEncoder());

		// Remove the following line if you don't want automatic content
		// compression.

		// pipeline.addLast("deflater", new HttpContentCompressor());

		pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());

		pipeline.addLast("handler", new DirectRequestHandler());
	}
}

public class UygulamaYonetimGorevlisi extends GorevliDir {
	public static final int NO=40;
		
		public static UygulamaYonetimGorevlisi tek(){
			if(tek==null) {
				synchronized (UygulamaYonetimGorevlisi.class) {
					if(tek==null) {
						tek=new UygulamaYonetimGorevlisi();
						tek.giris();
					}
				}
			}
			return tek;
		}
		
		static UygulamaYonetimGorevlisi tek;
		private UygulamaYonetimGorevlisi() {
			super("UygulamaYonetim", NO);
			port = 8889;
			hostName = "rom.internet";
		}
	

	private final int port;
	private final String hostName;
	private EventLoopGroup directListen;
	private NioEventLoopGroup directWork;

	@Override
	public void selfDescribe(JSONObject jo) {
	}

	// private NioEventLoopGroup bossGroup;
	// private NioEventLoopGroup workerGroup;

	// final static private Counter acceptSocketCount = monitor.counter("http",
	// "rom", "accept-socket-count");
	// final static private Counter acceptSocketIOErrorCount =
	// monitor.counter("http", "rom", "accept-socket-io-error");
	//
	// private static boolean
	// tcpNoDelay=ps.getBoolean("http.service.conn.tcpNoDelay");
	// private static int soTimeout=ps.getInt("http.service.conn.soTimeout");
	// private static boolean
	// soKeepalive=ps.getBoolean("http.service.conn.soKeepalive");
	// private static int soLinger=ps.getInt("http.service.conn.soLinger");

	@Override
	public void kur() {

		try {
			directListen = new NioEventLoopGroup(1, KosuGorevlisi.tek().threadGroup("direct-listen"));
			directWork = new NioEventLoopGroup(1, KosuGorevlisi.tek().threadGroup("direct-work"));
			ServerBootstrap b = new ServerBootstrap();
			b.group(directListen, directWork).channel(NioServerSocketChannel.class)
					.childHandler(new DirectServerPipelineFactory());
			b.option(ChannelOption.SO_BACKLOG, 200);
			b.option(ChannelOption.SO_REUSEADDR, true);
			// b.option(ChannelOption.SO_, true);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.bind(new InetSocketAddress(hostName, port)).sync().channel().closeFuture();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Sistem.printStackTrace(e);
		}

		// ServerBootstrap bootstrap = new ServerBootstrap(new
		// NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
		// Executors.newCachedThreadPool()));
		//
		// bootstrap.setOption("backlog", 200);
		// bootstrap.setOption("reuseAddress", true);
		// bootstrap.setOption("child.tcpNoDelay", true);
		// bootstrap.setOption("child.keepAlive", true);
		//
		// // Set up the event pipeline factory.
		// bootstrap.setPipelineFactory(new HttpServerPipelineFactory());
		//// bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
		//// @Override
		//// public ChannelPipeline getPipeline() throws Exception {
		//// return Channels.pipeline(new EchoServerHandler());
		//// }
		//// });
		//
		// // Bind and start to accept incoming connections.
		// bootstrap.bind(new InetSocketAddress(hostName, port));

	}
	@Override
	public void bitir(boolean gracefully) {
		if (this.directListen != null)
			this.directListen.shutdownGracefully();
		if (this.directWork != null)
			this.directWork.shutdownGracefully();
	}

	

}
