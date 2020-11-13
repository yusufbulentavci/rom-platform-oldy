package com.bilgidoku.rom.haber.world;

import com.bilgidoku.rom.haber.TalkUtil;
import com.bilgidoku.rom.haber.fastQueue.Cmd;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.min.Sistem;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class WorldConnection extends SimpleChannelInboundHandler<JSONObject> {

	// private final List<JSONObject> toSend = new ArrayList<JSONObject>();
	// public final Integer id;
	// private InetSocketAddress remote;
	private WorldSessionMng talkService;
	private WorldSession session;
	private ChannelHandlerContext ctx;
	private boolean watch;

	// public WorldConnection(WorldSessionMng talkService, String aims) {
	// id = idCounter.getAndIncrement();
	// this.talkService = talkService;
	// this.env = RomEnvFactory.getByName(aims);
	// setRemoteName(aims);
	// }
	//
	// public WorldConnection(WorldSessionMng talkService,
	// InetSocketAddress remoteAddress) {
	// this.remote = remoteAddress;
	// id = idCounter.getAndIncrement();
	// this.talkService = talkService;
	// this.env = RomEnvFactory.getByIp(Genel.parseIp(remote.getHostString()));
	// if (env != null) {
	// setRemoteName(env.domain);
	// }
	// }

	public WorldConnection(WorldSessionMng worlder, WorldSession session) {
		this.talkService = worlder;
		this.session = session;
//		if (this.session != null) {
//			talkService.newCon(this);
//		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.ctx = ctx;
		if(session!=null){
			session.addCon(this);
		}
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		talkService.endCon(this);
		super.channelInactive(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
//		new KnownError("",cause)
		Sistem.printStackTrace(cause);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, JSONObject msg)
			throws Exception {
		if (session == null) {
			session = talkService.newSession(TalkUtil.from(msg));
			talkService.newCon(this);
		}
		talkService.newMsg(this, msg);
		// lastMultiplier = msg;
		// factorial = factorial.multiply(msg);
		// ctx.writeAndFlush(factorial);
	}

	
	public void send(final Cmd msg) {
		ctx.writeAndFlush(msg.getJo()).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future)
					throws Exception {
				if (future.isSuccess()) {
					// Connection established use handler provided results
					talkService.sent(WorldConnection.this, msg);
				} else {
					talkService.sentFailed(WorldConnection.this, msg);
					ctx.close();
				}
			}
		});
	}

	public void send(final JSONObject jo) {
		ctx.writeAndFlush(jo).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future)
					throws Exception {
				if (future.isSuccess()) {
					// Connection established use handler provided results
				} else {
					ctx.close();
				}
			}
		});
	}

	public WorldSession getSession() {
		return session;
	}

	public void watch(boolean b) {
		this.watch=b;
	}

}
