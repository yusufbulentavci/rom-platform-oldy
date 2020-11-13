package com.bilgidoku.rom.haber.world;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.bilgidoku.rom.haber.Msg;
import com.bilgidoku.rom.haber.MsgStatus;
import com.bilgidoku.rom.haber.TalkUtil;
import com.bilgidoku.rom.haber.fastQueue.Cmd;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.err.KnownError;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

public class Worlder implements WorldSessionMng{
	
	class TalkIniter extends ChannelInitializer<SocketChannel> {
		private WorldSession session;

		public TalkIniter(WorldSession ses){
			this.session=ses;
		}

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
//			InetSocketAddress remoteAddress = toInet(ch.remoteAddress());
//			InetSocketAddress localAddress = toInet(ch.localAddress());
			
			ChannelPipeline p = ch.pipeline();
			p.addLast(new ReadTimeoutHandler(60));
			p.addLast(new WriteTimeoutHandler(60));
			p.addLast(new WorldDecoder());
			p.addLast(new WorldEncoder());

//			if(ws!=null){
//				p.addLast(ws);
//			}else{
				p.addLast(new WorldConnection(Worlder.this, session));
//			}
		}

		private InetSocketAddress toInet(SocketAddress a) throws KnownError {
			if (!(a instanceof InetSocketAddress)) {
				throw new KnownError("Not a inet socket");
			}
			InetSocketAddress ipAddress = (InetSocketAddress) a;
			assert (ipAddress != null);
			return ipAddress;
		}

	}
	
	private Map<String,WorldSession> sessions=new ConcurrentHashMap<String,WorldSession>();
	private final MsgStatus talker;
	private int port;
//	private Map<String,List<WorldConnection>> sessionsByName=new ConcurrentHashMap<String,List<WorldConnection>>();
	private String hostName;
	public final NioEventLoopGroup listenThreads;
	public final NioEventLoopGroup childThreads;
	public final NioEventLoopGroup connectThreads;

	public Worlder(MsgStatus talker, String hostName, 
			final NioEventLoopGroup listenThreads, final NioEventLoopGroup childThreads,
			final NioEventLoopGroup connectThreads){
		this.talker=talker;
		this.hostName=hostName;
		this.port=TalkUtil.SERVER_PORT;
		this.listenThreads=listenThreads;
		this.childThreads=childThreads;
		this.connectThreads=connectThreads;
	}
	
	public JSONObject report() {
		return new JSONObject().safePut("sessions", sessions.size());
	}
	
	public void start() {
		try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(listenThreads, childThreads)
             .channel(NioServerSocketChannel.class)
             .childHandler(new TalkIniter(null));
    		b.option(ChannelOption.SO_BACKLOG, 200);
    		b.option(ChannelOption.SO_REUSEADDR, true);
//    		b.option(ChannelOption.SO_, true);
    		b.option(ChannelOption.SO_KEEPALIVE, true);
            b.bind(new InetSocketAddress(hostName, port)).sync().channel().closeFuture();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Sistem.printStackTrace(e);
		} 
		
	}
	
	public void terminate() {
		if(listenThreads!=null)
			listenThreads.shutdownGracefully();
		if(connectThreads!=null)
			connectThreads.shutdownGracefully();
		if(childThreads!=null)
			childThreads.shutdownGracefully();
		
	}
	

	

	private void connect(WorldSession ses) {
			Bootstrap b = new Bootstrap();
			b.group(connectThreads).channel(NioSocketChannel.class)
					.handler(new TalkIniter(ses));

			// Make a new connection.
			b.connect(ses.to, TalkUtil.SERVER_PORT);

			// Get the handler instance to retrieve the answer.
			// FactorialClientHandler handler =
			// (FactorialClientHandler) f.channel().pipeline().last();

			// Print out the answer.
//			System.err.format("Factorial of %,d is: %,d", COUNT,
//					handler.getFactorial());
	}

	
	@Override
	public void endCon(WorldConnection con) {
		if(con.getSession()!=null){
			con.getSession().removeCon(con);
			if(con.getSession().empty()){
				removeSession(con.getSession());
			}
		}
		
	}


	private void removeSession(WorldSession session) {
		this.sessions.remove(session.to);
	}

	@Override
	public void newMsg(WorldConnection talkSession, JSONObject msg) {
		talker.newMsg(msg,"otherworld");
	}

	
	@Override
	public WorldSession newSession(String host) {
		WorldSession ses = sessions.get(host);
		if(ses==null){
			ses=new WorldSession(host);
			sessions.put(host, ses);
		}
		return ses;
	}

	
	@Override
	public void newCon(WorldConnection con) {
		WorldSession ses = con.getSession();
		ses.addCon(con);
		
		ses.sendQueue(con);
	}

	@Override
	public void sent(WorldConnection worldConnection, Cmd msg) {
		talker.sendSuc(msg);
	}

	@Override
	public void sentFailed(WorldConnection worldConnection, Cmd msg) {
		talker.sendFailed(msg);
	}

	
	public void send(Cmd msg) throws KnownError {
		try {
			String to = TalkUtil.to(msg.getJo());
			WorldSession s = getSession(to);
			if(s==null){
				if(msg.line()){
					talker.sendFailed(msg);
					return;
				}
				s=newSession(to);
				connect(s);
			}else{
				if(msg.line() && !s.hasActiveCon()){
					talker.sendFailed(msg);
					return;
				}
			}
			s.send(msg);
		} catch (JSONException e) {
			throw new KnownError("Worlder:"+msg.toString(), e);
		}
	}

	private WorldSession getSession(String name) {
		return this.sessions.get(name);
	}

	public int sessionCount() {
		return sessions.size();
	}

	public void watch(String from, long val) {
		WorldSession ses = this.sessions.get(from);
		if(ses!=null)
			ses.watch(val);
	}

	public void sendWatch(JSONObject jo, long cat) throws KnownError {
		Msg m=new Msg("ws.watch").data(jo).ani().line();
		for (WorldSession it : sessions.values()) {
			it.sendWatch(cat, m);
		}
	}

	
	

}
