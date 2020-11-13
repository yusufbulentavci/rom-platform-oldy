package com.bilgidoku.rom.web.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.SSLEngine;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.Eye;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.secure.GuvenlikGorevlisi;
import com.bilgidoku.rom.session.GuardianHandler;
import com.bilgidoku.rom.session.IpStat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpServerPipelineFactory extends ChannelInitializer<SocketChannel> implements ReqTrace {
	private static final MC mc = new MC(HttpServerPipelineFactory.class);

	private static final Astate gpc = mc.c("get-pipeline");

	private Map<Integer, HttpRequestHandler> reqHasNoSession=new ConcurrentHashMap<>();

	private Boolean redirectSecure;
	

	public HttpServerPipelineFactory(Boolean redirectSecure) {
		this.redirectSecure=redirectSecure;
	}


	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		gpc.more();
		// Create a default pipeline implementation.
		ChannelPipeline pipeline = ch.pipeline();
		NioSocketChannel nio=(NioSocketChannel) ch;
		if (nio.localAddress().getPort()%11000 == 443) {
			SSLEngine engine = GuvenlikGorevlisi.tek().getServerSslEngine();
			pipeline.addLast(	new SslHandler(engine));
		}

//		if (ch instanceof InetSocketAddress) {
//			secureService.getServerSslEngine();
////			InetSocketAddress sa = (InetSocketAddress) ch;
////			com.bilgidoku.rom.gunluk.Sistem.outln("=================="+sa.getHostName());
////			com.bilgidoku.rom.gunluk.Sistem.outln("=================="+sa.getPort());
////			if (sa.getPort() == 443) {
////				
////				secureService.getSslEngine(sa.getHostName());
////				
////				// Uncomment the following line if you want HTTPS
////				// SSLEngine engine =
////				// SecureChatSslContextFactory.getServerContext().createSSLEngine();
////				KeyStore ks;
////				ks = KeyStore.getInstance("JKS");
////				fis = new FileInputStream(fileSystem.getFile("/perm/cert/mac/rom.jks"));
////				ks.load(fis, secret.toCharArray());
////
////				// Set up key manager factory to use our key store
////				KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
////				kmf.init(ks, secret.toCharArray());
////
////				// Initialize the SSLContext to work with our key managers.
////				SSLContext context = SSLContext.getInstance("TLS");
////				context.init(kmf.getKeyManagers(), null, null);
////				SSLEngine engine = context.createSSLEngine();
////				engine.setUseClientMode(false);
////				pipeline.addLast("ssl", new SslHandler(engine));
////			}
//		}

		// pipeline.addLast("decoder", new HttpRequestDecoder());
		//
//		 pipeline.addLast("aggregator", new HttpObjectAggregator(100111222));
		//
		// pipeline.addLast("encoder", new HttpResponseEncoder());

		// Remove the following line if you don't want automatic content
		// compression.

		// pipeline.addLast("deflater", new HttpContentCompressor());

		// pipeline.addLast("codec", new HttpServerCodec());

		// pipeline.addLast("aggegator",
		// new HttpObjectAggregator(512 * 1024));

		pipeline.addLast(new GuardianHandler(IpStat.MODULE_HTTP));
		pipeline.addLast("watcher", new ConnectionWatcher());

		pipeline.addLast("decoder", new HttpRequestDecoder());
		pipeline.addLast("aggregator", new HttpObjectAggregator(100 * 1024 * 1024));
		pipeline.addLast("encoder", new HttpResponseEncoder());

		pipeline.addLast("deflater", new HttpChunkContentCompressor());

		pipeline.addLast("chunkedWriter", new ChunkedWriteHandler());

		pipeline.addLast("handler", new HttpRequestHandler(this, redirectSecure));
	}


	@Override
	public void reqHasNoSession(Integer sockId, HttpRequestHandler httpRequestHandler) {
		if(Eye.on)		
			mc.trace("ReqTrace req added:"+sockId);
		this.reqHasNoSession.put(sockId,httpRequestHandler);
	}


	@Override
	public void hasSession(Integer sockId) {
		if(Eye.on)		
			mc.trace("ReqTrace req removed:"+sockId);
		this.reqHasNoSession.remove(sockId);
	}


	public int noSessionReqCount() {
		return reqHasNoSession.size();
	}


	@Override
	public void reqInactive(int socketid) {
		if(Eye.on)		
			mc.trace("ReqTrace req removed inactive:"+socketid);
		this.reqHasNoSession.remove(socketid);
	}
	
	
}
