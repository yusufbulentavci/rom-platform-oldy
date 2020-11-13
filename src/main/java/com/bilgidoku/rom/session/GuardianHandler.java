package com.bilgidoku.rom.session;

import java.net.InetSocketAddress;

import com.bilgidoku.rom.izle.Eye;
import com.bilgidoku.rom.yerel.YerelGorevlisi;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;

/**
 * {@link ChannelUpstreamHandler} which limit connections per IP
 * 
 * This handler must be used as singleton when adding it to the
 * {@link ChannelPipeline} to work correctly
 *
 * TODO: Remove when its committed to NETTY.
 * https://jira.jboss.org/jira/browse/NETTY-311
 */
@Sharable
public class GuardianHandler extends ChannelInboundHandlerAdapter {

	
	final int from;

	private ConnectionSession cs;
	private String remoteIp;
	private String country;

	public GuardianHandler(int from) {
		this.from = from;

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
		this.remoteIp = remoteAddress.getAddress().getHostAddress();
		this.country=YerelGorevlisi.tek().getCountryCode(remoteAddress);

		try {
			Eye.myIpIs(remoteIp);
			this.cs = (ConnectionSession) ctx.pipeline().get("handler");
			this.cs.setCountry(country);
			this.cs.setIp(remoteIp);

			if (OturumGorevlisi.tek().connect(from, remoteIp, cs)) {
				ctx.channel().close();
			}

			super.channelActive(ctx);

		} finally {
			Eye.myIpIs(null);
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

		try {

			Eye.myIpIs(remoteIp);
			OturumGorevlisi.tek().disconnect(from, remoteIp, cs);

			super.channelInactive(ctx);
		} finally {
			Eye.myIpIs(null);
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// InetSocketAddress remoteAddress = (InetSocketAddress)
		// ctx.channel().remoteAddress();
		// String remoteIp = remoteAddress.getAddress().getHostAddress();

		try {
			Eye.myIpIs(remoteIp);
			if (cs == null || !cs.inStreamingMode()) {
				if (OturumGorevlisi.tek().hit(from, remoteIp, "", cs)) {
					ctx.channel().close();
				}
			}

			super.channelRead(ctx, msg);
		} finally {
			Eye.myIpIs(null);
		}
	}

}
