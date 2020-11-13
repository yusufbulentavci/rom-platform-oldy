/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package com.bilgidoku.rom.protokol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.AttributeKey;

import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.SSLEngine;

import com.bilgidoku.rom.gunluk.GunlukGorevlisi;
import com.bilgidoku.rom.gunluk.LogCmds;
import com.bilgidoku.rom.gunluk.LogParams;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.protokol.protocols.Encryption;
import com.bilgidoku.rom.protokol.protocols.Protocol;
import com.bilgidoku.rom.protokol.protocols.ProtocolSession;
import com.bilgidoku.rom.protokol.protocols.ProtocolSessionImpl;
import com.bilgidoku.rom.protokol.protocols.ProtocolTransport;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.protokol.protocols.future.FutureResponse;
import com.bilgidoku.rom.protokol.protocols.handler.ConnectHandler;
import com.bilgidoku.rom.protokol.protocols.handler.DisconnectHandler;
import com.bilgidoku.rom.protokol.protocols.handler.LineHandler;
import com.bilgidoku.rom.protokol.protocols.handler.ProtocolHandlerChain;
import com.bilgidoku.rom.protokol.protocols.handler.ProtocolHandlerResultHandler;

public abstract class BasicChannelUpstreamHandler extends
		SimpleChannelInboundHandler<ByteBuf> {
	private static final MC mc = new MC(BasicChannelUpstreamHandler.class);
	protected final Protocol protocol;
	protected final ProtocolHandlerChain chain;
	protected final Encryption secure;
	protected ChannelHandlerContext ctx;

	public BasicChannelUpstreamHandler(Protocol protocol) {
		this(protocol, null);
	}

	public BasicChannelUpstreamHandler(Protocol protocol, Encryption secure) {
		this.protocol = protocol;
		this.chain = protocol.getProtocolChain();
		this.secure = secure;
	}

	private static final AttributeKey<ProtocolSession> PROTOCOL_SESSION_ATTR_KEY = AttributeKey
			.valueOf("pr.ses");

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		ctx.attr(PROTOCOL_SESSION_ATTR_KEY).set(createSession(ctx));
		this.ctx=ctx;
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		cleanup(ctx);
	}

	/**
	 * Cleanup the channel
	 * 
	 * @param ctx
	 */
	protected void cleanup(ChannelHandlerContext ctx) {
		ProtocolSession session = getSession(ctx);
		if (session != null) {
			try{
				GunlukGorevlisi.tek().log(LogCmds.protocollog, true, 5, LogParams.trace, session.reportCommandLog());
				this.sessionTerminating(session);
			}finally{
				session.resetState();
				session = null;
			}
		}
	}

	protected abstract void sessionTerminating(ProtocolSession session);

	protected ProtocolSession getSession(ChannelHandlerContext ctx) {
		return (ProtocolSession) ctx.attr(PROTOCOL_SESSION_ATTR_KEY).get();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		List<ConnectHandler> connectHandlers = chain
				.getHandlers(ConnectHandler.class);
		List<ProtocolHandlerResultHandler> resultHandlers = chain
				.getHandlers(ProtocolHandlerResultHandler.class);
		ProtocolSession session = getSession(ctx);
		conncount.more();
		// session.getLogger().info("Connection established from " +
		// session.getRemoteAddress().getAddress().getHostAddress());
		if (connectHandlers != null) {
			for (int i = 0; i < connectHandlers.size(); i++) {
				ConnectHandler cHandler = connectHandlers.get(i);
//				com.bilgidoku.rom.min.Sistem.outln("ConnectHandler:"
//						+ cHandler.getClass());

				long start = Sistem.millis();
				Response response = connectHandlers.get(i).onConnect(session);
				long executionTime = Sistem.millis() - start;

				for (int a = 0; a < resultHandlers.size(); a++) {
					// Disable till PROTOCOLS-37 is implemented
					if (response instanceof FutureResponse) {
						// session.getLogger().debug("ProtocolHandlerResultHandler are not supported for FutureResponse yet");
						unsupported.more();
						break;
					}
					Sistem.outln("ResultHandler:"
							+ resultHandlers.get(i).getClass());
					resultHandlers.get(a).onResponse(session, response,
							executionTime, cHandler);
				}
				if (response != null) {
					// TODO: This kind of sucks but I was able to come up with
					// something more elegant here
					((ProtocolSessionImpl) session).getProtocolTransport()
							.writeResponse(response, session);
				}

			}
		}
	}

	private static final Astate conncount = mc.c("channel-connected");
	private static final Astate unsupported = mc.c("unsupported");

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		List<DisconnectHandler> connectHandlers = chain
				.getHandlers(DisconnectHandler.class);
		ProtocolSession session = getSession(ctx);
		if (connectHandlers != null) {
			for (int i = 0; i < connectHandlers.size(); i++) {
				connectHandlers.get(i).onDisconnect(session);
			}
		}
	}

	protected ProtocolSession createSession(ChannelHandlerContext ctx)
			throws Exception {
		SSLEngine engine = null;
		if (secure != null) {
			engine = secure.getContext().createSSLEngine();
			String[] enabledCipherSuites = secure.getEnabledCipherSuites();
			if (enabledCipherSuites != null && enabledCipherSuites.length > 0) {
				engine.setEnabledCipherSuites(enabledCipherSuites);
			}
		}

		return protocol.newSession(new NettyProtocolTransport(ctx.channel(),
				engine));
	}

	private static final Astate toolongframe = mc.c("too-long-frame");
	private static final Astate unableprocess = mc.c("unable-to-process");
	private static final Astate unableprocessnosession = mc
			.c("unable-to-process-no-session");

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e)
			throws Exception {
		Sistem.printStackTrace(e);
		Channel channel = ctx.channel();
		ProtocolSession session = getSession(ctx);
		if (e.getCause() instanceof TooLongFrameException && session != null) {
			toolongframe.more();
			Response r = session.newLineTooLongResponse();
			ProtocolTransport transport = ((ProtocolSessionImpl) session)
					.getProtocolTransport();
			if (r != null) {
				transport.writeResponse(r, session);
			}
		} else {
			if ((channel.isActive() || channel.isOpen()) && session != null) {
				ProtocolTransport transport = ((ProtocolSessionImpl) session)
						.getProtocolTransport();

				Response r = session.newFatalErrorResponse();
				if (r != null) {
					transport.writeResponse(r, session);
				}
				transport.writeResponse(Response.DISCONNECT, session);
			}
			if (session != null) {
				unableprocess.more();
			} else {
				unableprocessnosession.more();
			}
			cleanup(ctx);
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buf)
			throws Exception {
		ProtocolSession session = getSession(ctx);
		LinkedList<LineHandler> lineHandlers = chain
				.getHandlers(LineHandler.class);
		LinkedList<ProtocolHandlerResultHandler> resultHandlers = chain
				.getHandlers(ProtocolHandlerResultHandler.class);

		if (lineHandlers.size() > 0) {
			LineHandler lHandler = (LineHandler) lineHandlers.getLast();
//			com.bilgidoku.rom.min.Sistem.outln("LineHandler:"
//					+ lHandler.getClass());
			long start = System.currentTimeMillis();
			Response response = lHandler.onLine(session, buf.nioBuffer());
			long executionTime = System.currentTimeMillis() - start;

			for (int i = 0; i < resultHandlers.size(); i++) {
				// Disable till PROTOCOLS-37 is implemented
				if (response instanceof FutureResponse) {
					unsupported.more("message received");
					// pSession.getLogger().debug("ProtocolHandlerResultHandler are not supported for FutureResponse yet");
					break;
				}
				Sistem.outln("ResultHandler:"
						+ resultHandlers.get(i).getClass());
				response = resultHandlers.get(i).onResponse(session, response,
						executionTime, lHandler);
			}
			log(session, response);

		}
	}

	private void log(ProtocolSession session, Response response)
			throws JSONException {
		if (response != null) {
			// TODO: This kind of sucks but I was able to come up with
			// something more elegant here
			((ProtocolSessionImpl) session).getProtocolTransport()
					.writeResponse(response, session);

			if (!response.isSuccess() || response.isImportant()) {
				JSONObject jr = response.toReport();
				JSONObject sr = session.toReport();
				GunlukGorevlisi.tek().log(LogCmds.out, false, 10, "str", "Session:"+sr.toString() + "\nResponse:"+jr.toString());
			}
		}
	}

}
