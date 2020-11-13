package com.bilgidoku.rom.web.http;

import java.net.InetSocketAddress;

import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.run.KosuGorevlisi;
import com.bilgidoku.rom.session.OturumGorevlisi;
import com.bilgidoku.rom.shared.gorevli.Uygulama;
import com.bilgidoku.rom.web.http.session.AppSessionExtension;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpGorevlisi extends GorevliDir {

	public static final int NO = 16;

	public static HttpGorevlisi tek() {
		if (tek == null) {
			synchronized (HttpGorevlisi.class) {
				if (tek == null) {
					tek = new HttpGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static HttpGorevlisi tek;

	private HttpGorevlisi() {
		super("Http", NO);
		this.port = 80;
		this.sslPort = 443;
		this.redirectSecure = true;
		OturumGorevlisi.tek().addExtension(AppSessionExtension.one());
	}

	private NioEventLoopGroup bossGroup;
	private NioEventLoopGroup workerGroup;

	private int port;
	private HttpServerPipelineFactory pipeLineFactory;
	private Integer sslPort;
	private boolean redirectSecure;

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


	public void selfDescribe(JSONObject jo) {
		jo.safePut("nosessionreqs", pipeLineFactory.noSessionReqCount());
	}

	@Override
	public void kur() {

		try {
			this.pipeLineFactory = new HttpServerPipelineFactory(redirectSecure);
			ServerBootstrap b = new ServerBootstrap();

			bossGroup = new NioEventLoopGroup(2, KosuGorevlisi.tek().threadGroup("http-listen"));
			workerGroup = new NioEventLoopGroup(10, KosuGorevlisi.tek().threadGroup("http-work"));
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(pipeLineFactory);
			b.option(ChannelOption.SO_BACKLOG, 200);
			b.option(ChannelOption.SO_REUSEADDR, true);
			// b.option(ChannelOption.SO_, true);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			try {
				Uygulama.tek().reRoot();
				System.out.flush();
				b.bind(new InetSocketAddress("rom.internet", this.sslPort)).sync().channel().closeFuture();
				b.bind(new InetSocketAddress("rom.internet", this.port)).sync().channel().closeFuture();
			} finally {
				Uygulama.tek().dropRoot();
			}
		} catch (InterruptedException e) {
			Sistem.printStackTrace(e);
		}
	}

	@Override
	public void bitir(boolean gracefully) {
		if (bossGroup != null)
			bossGroup.shutdownGracefully();
		if (workerGroup != null)
			workerGroup.shutdownGracefully();
	}

}
