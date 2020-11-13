package com.bilgidoku.rom.web.http;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

import com.bilgidoku.rom.ilk.file.FromResource;
import com.bilgidoku.rom.ilk.gorevli.Dir;
import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.Eye;
import com.bilgidoku.rom.izle.IzlemeGorevlisi;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.kurum.KurumDosyaGorevlisi;
import com.bilgidoku.rom.kurum.KurumGorevlisi;
import com.bilgidoku.rom.mime.MimeGorevlisi;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.dict.CommonRequestHandler;
import com.bilgidoku.rom.pg.dict.Format;
import com.bilgidoku.rom.pg.dict.RomDomain;
import com.bilgidoku.rom.session.ConnectionSession;
import com.bilgidoku.rom.session.IpStat;
import com.bilgidoku.rom.session.OturumGorevlisi;
import com.bilgidoku.rom.shared.Cookie;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.gorevli.Uygulama;
import com.bilgidoku.rom.shared.util.Knife;
import com.bilgidoku.rom.web.cagridagitma.CagriDagitmaGorevlisi;
import com.bilgidoku.rom.web.http.session.AppSession;
import com.bilgidoku.rom.web.http.session.AppSessionExtension;
import com.bilgidoku.rom.web.http.session.PushConn;
import com.bilgidoku.rom.web.http.session.SessionIdGenerationError;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressiveFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.DiskAttribute;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCounted;

enum RequestStates {
	INITIAL, CONSTRUCTED, INACTIVATING, INACTIVE, READCHUNK, HANDLECHUNKEDREQUEST, POST, GETHEAD, DISPATCH, READERROR,
	HANDLEHTTPREQUEST, WEBSOCKHANDSHAKE, EXCEPTION, HANDLEWEBSOCKET,
}

public class HttpRequestHandler extends ChannelInboundHandlerAdapter
		implements CommonRequestHandler, PushConn, ConnectionSession {
	private static AtomicInteger socketidgen = new AtomicInteger(0);

	final static private MC mc = new MC(HttpRequestHandler.class);
	public static final String RESPONSE_FORMAT_PARAM = "outform";

	// final static private PropertyService ps =
	// ServiceDiscovery.getService(PropertyService.class);

	final static private AppSessionExtension sessionExtension = (AppSessionExtension) OturumGorevlisi.tek()
			.getExtension(AppSessionExtension.APP);

	public static final Long HTTP_CACHE_SECONDS = new Long(60 * 60 * 24 * 7);

	// final static private Astate creatingChunkError =
	// mc.c("creating-chunk-error");

	private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); // Disk

	static {
		DiskFileUpload.deleteOnExitTemporaryFile = true; // should delete file
		// on exit (in normal
		// exit)
		DiskFileUpload.baseDirectory = null; // system temp directory
		DiskAttribute.deleteOnExitTemporaryFile = true; // should delete file on
		// exit (in normal exit)
		DiskAttribute.baseDirectory = null; // system temp directory
	}

	public static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
	public static final String HTTP_DATE_GMT_TIMEZONE = "GMT";

	private final SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);

	private HttpPostRequestDecoder decoder = null;
	private volatile boolean readingChunks = false;

	private AppSession session;
	private RomDomain domain;
	private InetSocketAddress ipAddress;
	private boolean ssl = false;
	private ChannelHandlerContext ctx;
	private HttpRequest request;
	private long totalGot = 0;
	private boolean sessionChanged = false;
	private QueryStringDecoder queryString;

	private WebSocketServerHandshaker handshaker;
	private final int socketid;
	private Long webSocketOnline = null;

	// private String webSocketApp = null;
	// private String webSocketSubject = null;

	private RomHttpResponse romResponse;

	private long stateChangeTime = Sistem.millis();
	private RequestStates state = RequestStates.INITIAL;

	private ReqTrace reqTrace;

	private final boolean redirectSecure;

	private boolean cookiesSent = false;

	public HttpRequestHandler(ReqTrace st, boolean redirectSecure) {
		super();
		this.redirectSecure = redirectSecure;

		socketid = socketidgen.getAndIncrement();
		this.reqTrace = st;

		if (Eye.on) {
			Sistem.outln("Created " + this.socketid);
		}

		dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));
		state(RequestStates.CONSTRUCTED);

		// com.bilgidoku.rom.ServiceDiscovery.log.Sistem.errln("NEW
		// CONNECTION");
		st.reqHasNoSession(getSockId(), this);
	}

	private void state(RequestStates c) {
		state = c;
		stateChangeTime = Sistem.millis();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		state(RequestStates.INACTIVATING);
		if (Eye.on) {
			mc.trace("Inactive " + this.socketid);
		}
		if (session != null) {
			ConnectionWatcher cw = (ConnectionWatcher) ctx.pipeline().get("watcher");
			long[] rw = new long[2];
			rw[0] = cw.getReadByteSize();
			rw[1] = cw.getWriteByteSize();

			if (Eye.on) {
				mc.trace("Inactive /hasSession" + this.socketid);
			}
			session.connClosed(this, rw);
		} else {
			if (Eye.on) {
				mc.trace("Inactive /noSession" + this.socketid);
			}

			reqTrace.reqInactive(this.socketid);
		}
//		if (decoder != null) {
//			decoder.destroy();
//		}
		super.channelInactive(ctx);
		state(RequestStates.INACTIVE);
	}

	private boolean noRead = true;

	private String ip;

	private String country;

	final static private Astate incompatibleDataDecoderException = mc.c("http-incompatible-data-decoder-exception");
	final static private Astate notEnoughDataDecoderException = mc.c("http-not-enough-data-decoder-exception");
	final static private Astate hostNotFoundCounter = mc.c("host-param-not-found");
	final static private Astate unknownDomainWarning = mc.c("http-unknown-domain");
	final static private Astate httpMethodNotSupportedWarning = mc.c("http-method-not-supported");
	final static private Astate handledRomException = mc.c("http-handled-rom-exception");
	final static private Astate nullPointerException = mc.c("null-pointer-exception");
	final static private Astate errorDataDecoderException = mc.c("http-error-data-decoder-exception");
	final static private Astate generalMsgException = mc.c("general-msg-exception");
	final static private Astate badrmsqlstate = mc.c("bad-rm-sqlstate");

	final static private Astate _nettyRefCountFailed = mc.c("nettyRefCountFailed");
	final static private Astate _nettyRefCountSuc = mc.c("nettyRefCountSuc");

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws KnownError {
		try {
			this.ctx = ctx;
			if (Eye.on) {
				if (noRead) {
					mc.trace("First read");
				}
			}
			noRead = true;

			if (readingChunks) {
				retrieveChunk((HttpContent) msg);
				return;
			}
			totalGot = 0;
			if (ipAddress == null) {
				SocketAddress a = ctx.channel().remoteAddress();
				if (!(a instanceof InetSocketAddress)) {
					throw new KnownError("Not a inet socket");
				}
				ipAddress = (InetSocketAddress) a;

				ChannelPipeline pipeline = ctx.channel().pipeline();
				NioSocketChannel nio = (NioSocketChannel) ctx.channel();
				if (nio.localAddress().getPort() % 11000 == 443) {
					this.ssl = true;
				}
				assert (ipAddress != null);
			}
			if (msg instanceof HttpRequest) {
				handleHttpRequest(ctx, (HttpRequest) msg);
			} else if (msg instanceof WebSocketFrame) {
				handleWebSocketFrame(ctx, (WebSocketFrame) msg);
			}

		} catch (KnownError e) {
			state(RequestStates.READERROR);
			// sendError(e.httpCode());
			if (e.isNotFound())
				IzlemeGorevlisi.tek().addToSet("notfound", e.getSubject());
			if (!e.isSilent())
				Sistem.printStackTrace(e);

			sendError(HttpResponseStatus.valueOf(e.httpCode()));
		} catch (SessionIdGenerationError e) {
			state(RequestStates.READERROR);
			Sistem.printStackTrace(e);
			try {
				sendError(HttpResponseStatus.INTERNAL_SERVER_ERROR);
			} catch (Throwable tt) {
			}
		} catch (Throwable t) {
			state(RequestStates.READERROR);
			Sistem.printStackTrace(t);
			try {
				sendError(HttpResponseStatus.INTERNAL_SERVER_ERROR);
			} catch (Throwable tt) {
			}
			throw t;
		} finally {
			try {
				ReferenceCounted rc = (ReferenceCounted) msg;
				rc.release();
				_nettyRefCountSuc.more();
			} catch (Exception e) {
//				com.bilgidoku.rom.min.Sistem.printStackTrace(e, "Release failed/channelread:" + msg.getClass());
				_nettyRefCountFailed.more();
			}
		}

	}

	final static private Astate _webSocketAlreadyHaveSid = mc.c("webSocketAlreadyHaveSid");

	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		// webSocket.handle(session, ctx, frame);
		state(RequestStates.HANDLEWEBSOCKET);

		// will.param("websocket");
		if (frame instanceof CloseWebSocketFrame) {
			webSocketClosed(ctx, frame);
			return;
		} else if (frame instanceof PingWebSocketFrame) {
			ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
			return;
		} else if (!(frame instanceof TextWebSocketFrame)) {
			throw new UnsupportedOperationException(
					String.format("%s frame types not supported", frame.getClass().getName()));
		}

		if (Eye.on) {
			mc.trace("TextWebSocketFrame:" + socketid);
		}

		if (session != null) {
			session.rtReady(this);
			_webSocketAlreadyHaveSid.more();
			return;
		}

		String text = ((TextWebSocketFrame) frame).text();
		if (Eye.on) {
			mc.trace("TextWebSocketFrame:" + socketid + " " + text);
		}

		try {
			JSONArray reqJson = new JSONArray(text);

			getSessionDeep(reqJson.getString(0), reqJson.getString(1), reqJson.getString(3),
					KurumGorevlisi.tek().getDomain(reqJson.getString(2)));
			if (Eye.on) {
				mc.trace("Websocket has a sid now:" + socketid);
			}
		} catch (Exception e) {
			Sistem.printStackTrace(e);
		}

		session.rtReady(this);

		// ctx.channel().write(new
		// TextWebSocketFrame(request.toUpperCase()));
		//
		// gotMsg(session, ctx,frame);
	}

	private void webSocketClosed(ChannelHandlerContext ctx, WebSocketFrame frame) {
		handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame);
		webSocketOnline = null;
		session.rtOffline(this);
		if (Eye.on) {
			mc.trace("CloseWebSocket:" + socketid);
		}
	}

	public void handleHttpRequest(ChannelHandlerContext ctx, HttpRequest e)
			throws KnownError, SessionIdGenerationError {
		request = e;

		state(RequestStates.HANDLEHTTPREQUEST);

		HttpMethod method = request.getMethod();
		if (method == null || (request.getMethod() != HttpMethod.GET && request.getMethod() != HttpMethod.POST
				&& request.getMethod() != HttpMethod.HEAD && request.getMethod() != HttpMethod.DELETE)) {
			httpMethodNotSupportedWarning.more();
			throw new KnownError(method + " method not supported").notImplemented();
		}
		String cookieStr = request.headers().get(HttpHeaders.Names.COOKIE);
		if (session == null) {
			String host = request.headers().get(HttpHeaders.Names.HOST);
			if (host == null) {
				hostNotFoundCounter.more();
				throw new KnownError("Host not found").badRequest().setSilent();
			}

			try {
				domain = KurumGorevlisi.tek().getDomain(host);
			} catch (KnownError ke) {
				hostNotFoundCounter.more();
				throw new KnownError("Host not found").notFound(host).setSilent();
			}

//			new DbThree("select forcehttps from rom.org where host_id=").setString(host);

			if (redirectSecure && !ssl && domain.isForceHttps()) {
				redirectHttps(host);
				return;
			}

			String userAgent = request.headers().get(HttpHeaders.Names.USER_AGENT);
			getSession(cookieStr, userAgent, domain);
			session.connReady(this);
		}

		session.touch();
		// else {
		// if (cookieStr!=null && cookieStr.length()>0 &&
		// !session.getSid().equals(cookieStr)) {
		// com.bilgidoku.rom.ServiceDiscovery.log.Sistem.errln("Session mismatch
		// connection:"
		// +
		// session.getCid());
		// com.bilgidoku.rom.ServiceDiscovery.log.Sistem.errln(" request:" +
		// session.getCid());
		// sessionChanged = true;
		// throw new RomRequestException(HttpResponseStatus.FORBIDDEN);
		// // getSession();
		// // cookieStr=session.getSid();
		// // throw new RomRequestException(
		// // HttpResponseStatus.BAD_REQUEST, "S");
		// }
		// }
		// mc.setThrActive(session);
		this.queryString = new QueryStringDecoder(request.getUri());
		String uriPath = this.queryString.path();
		if (Eye.on) {
			mc.trace("Uri:" + uriPath);
		}
		// com.bilgidoku.rom.min.Sistem.outln(uriPath);
		// will.param(uriPath);
		if (uriPath.equals("/favicon.ico")) {
//			String fi = KurumGorevlisi.tek().getFavicon(domain.getHostId());
			String fi=null;
			File f = null;
			if (fi == null) {

				f = new File(Dir.WwwDir + "/img/etc/favicon.ico");
//				f = hostingFileService.getCommonFile("/_static/img/etc/favicon.ico", true, true);
				fi = "/_static/img/etc/favicon.ico";
			} else {
				f = KurumDosyaGorevlisi.tek().getFile(domain.getHostId(), fi, true, false);
				if (f == null) {
					throw new KnownError().notFound(fi);
				}
			}
			sendFile(f, fi, e, false, new Long(60 * 60 * 24 * 30));
			return;
		} else if (uriPath.equals("/_romevents")) {
			webSocketHandShake();
			return;
		}
		if (method == HttpMethod.POST) {
			if (decoder != null) {
				decoder.destroy();
				decoder = null;
			}
			decoder = new HttpPostRequestDecoder(factory, request);

			if (HttpHeaders.isTransferEncodingChunked(request)) {
				readingChunks = true;
				// com.bilgidoku.rom.ServiceDiscovery.log.Sistem.errln("<<<<<<<<"
				// + request.getContentLength() + ">>>>>>>>>>>");
				// List<String> romprogress =
				// this.queryString.getParameters().get("romprogress");
				// if (this.session != null && romprogress != null) {
				// // session.inUpload(romprogress.get(0));
				//
				// }
			} else {
				// Not chunk version
				try {
					makePost(request);
				} finally {
					decoder.destroy();
					decoder = null;
				}
			}
		} else {
			makeGetOrHead(request);
		}

	}

	private static final Astate unsupportedVersionCount = mc.c("unsupportedversion");

	private void webSocketHandShake() {
		state(RequestStates.WEBSOCKHANDSHAKE);
		// WebSocketServerHandshakerFactory wsFactory = new
		// WebSocketServerHandshakerFactory(
		// getWebSocketLocation(req), null, false);
		// handshaker = wsFactory.newHandshaker(req);
		// if (handshaker == null) {
		// WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
		// } else {
		// handshaker.handshake(ctx.channel(), req);
		// }

		String socLoc;

		if (ssl) {
			socLoc = "wss://" + domain.getDomainName() + ":" + (Uygulama.tek().isTest() ? "11443" : "443")
					+ "/_romevents";
		} else {

			socLoc = "ws://" + domain.getDomainName() + ":" + 80 + "/_romevents";
		}

		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(socLoc, null, false);
		handshaker = wsFactory.newHandshaker(request);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
			if (Eye.on)
				mc.trace("We have a web socket:" + this.webSocketOnline);
			unsupportedVersionCount.more();

		} else {
			handshaker.handshake(ctx.channel(), (FullHttpRequest) request);
			this.webSocketOnline = Sistem.millis();
			if (Eye.on)
				mc.trace("We have a web socket:" + this.webSocketOnline);
		}
	}

	private void getSession(String cookieStr, String userAgent, RomDomain domain)
			throws KnownError, SessionIdGenerationError {
		String referrer = request.headers().get(HttpHeaders.Names.REFERER);
		getSessionDeep(cookieStr, userAgent, referrer, domain);
		// will.setListener(session);
	}

	private void getSessionDeep(String cookieStr, String userAgent, String referrer, RomDomain domain2)
			throws KnownError, SessionIdGenerationError {
		this.domain = domain2;

		// if (domain.error() || domain.getRomDomain()==null) {
		// unknownDomainWarning.more();
		// throw new KnownError("Domain not found:" + host+
		// " domain:"+domain.getDomainName()).badRequest();
		// }
		session = sessionExtension.getAppSession(domain, ipAddress, cookieStr, userAgent, referrer);

		session.connReady(this);
		if (Eye.on) {
			mc.trace("Req has session:" + socketid);
		}

		reqTrace.hasSession(getSockId());
	}

	private void makeGetOrHead(HttpRequest request) throws KnownError {
		state(RequestStates.GETHEAD);
		Format format = getFormat();
		RomHttpResponse response = new RomHttpResponse(this, request, request.getUri(), queryString, null, format);
		dispatch(response);
	}

	private void makePost(HttpRequest request) throws KnownError {
		state(RequestStates.POST);
		List<InterfaceHttpData> datas = decoder.getBodyHttpDatas();
		RomHttpResponse response = new RomHttpResponse(this, request, request.getUri(), queryString, datas, null);
		dispatch(response);
	}

	private Format getFormat() {
		if (request.getMethod().equals(HttpMethod.DELETE))
			return Format.JSON;
		List<String> fl = queryString.parameters().get(RESPONSE_FORMAT_PARAM);
		if (fl == null || fl.size() == 0) {
			return Format.HTML;
		}
		return Format.fromStr(fl.get(0));
	}

	//
	// private static final Astate romReqExp = mc.c("rom-request-exception");
	// private static final Astate romExp = mc.c("rom-exception");
	// private static final Astate romSqlExp = mc.c("rom-sql-exception");
	// private static final Astate romJsonExp = mc.c("rom-json-exception");

	private void dispatch(RomHttpResponse response) throws KnownError {
		state(RequestStates.DISPATCH);
		this.romResponse = response;
		try {
			CagriDagitmaGorevlisi.tek().romHandle(response);
		} finally {
			response.close();
		}
	}

	private void retrieveChunk(HttpContent chunk) throws KnownError {
		state(RequestStates.READCHUNK);
		decoder.offer(chunk);
		if (chunk instanceof LastHttpContent) {
			state(RequestStates.HANDLECHUNKEDREQUEST);
			readingChunks = false;
			// syso("lastchunk");
			totalGot = 0;
			// HttpRequest request = (HttpRequest) e.getMessage();
			QueryStringDecoder uri = new QueryStringDecoder(request.getUri());
			try {
				makePost(request);
			} finally {
				decoder.destroy();
				decoder = null;
			}
		} else {
//			 totalGot += chunk.getContent().writerIndex();
			// syso("chunk:" + totalGot);
			// com.bilgidoku.rom.ServiceDiscovery.log.Sistem.errln("(((((((((" +
			// totalGot + "))))))))))))");
			// session.sendProgressEvent(queryString.getPath(),
			// request.getContentLength(0), totalGot);

		}
	}

	public ChannelHandlerContext getChannelHandlerContext() {
		return this.ctx;
	}

	public AppSession getSession() {
		return this.session;
	}

	// public RomDomain getDomain() {
	// return this.session.getDomain();
	// }

	public Cookie getCookie() {
		return session;
	}

	public String getCookie(String cookieName) {
		if (cookieName.equals("sid")) {

		}
		return null;
	}

	static private Astate connIOErrorCounter = mc.c("http-conn-io-error");

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		state(RequestStates.EXCEPTION);
		connIOErrorCounter.more();
		if (cause instanceof IOException) {
			// Nothing to do
			return;
		}
		// super.exceptionCaught(ctx, cause);
		// com.bilgidoku.rom.ServiceDiscovery.log.Sistem.errln(cause.getMessage());
		Sistem.printStackTrace(cause);
	}

	final static private Astate fileNotFound = mc.c("file-not-found");
	final static private Astate fileLengthGettingError = mc.c("file-length-getting-error");

	private void setCookies(HttpResponse response) throws KnownError {
		if (session == null)
			return;
		if (!cookiesSent || this.sessionChanged || session.cookieDirty()) {
			// com.bilgidoku.rom.ServiceDiscovery.log.Sistem.errln("Send new
			// cookie");
			this.sessionChanged = false;
			cookiesSent = true;
			String[] cookieHeaders = session.getCookieHeaders();
			for (String cstr : cookieHeaders) {
				response.headers().add(HttpHeaders.Names.SET_COOKIE, cstr);
			}
		}
	}

	private String getMime(String lastPart) throws KnownError {
		String mime = MimeGorevlisi.tek().getMimeOfFile(lastPart);
		return mime;
	}

	final static private Astate dateParsingErrorCounter = mc.c("http-date-parsing-error");

	public Date getHeaderIfModifiedSince(HttpRequest request) throws KnownError {
		String ifModifiedSince = request.headers().get(HttpHeaders.Names.IF_MODIFIED_SINCE);
		if (ifModifiedSince == null || ifModifiedSince.equals(""))
			return null;

		try {
			return dateFormatter.parse(ifModifiedSince);
		} catch (ParseException e1) {
			dateParsingErrorCounter.more();
			throw new KnownError(e1).badRequest();
		}
	}

	/**
	 * Sets the Date header for the HTTP response
	 * 
	 * @param response HTTP response
	 */
	public void setDateHeader(HttpResponse response) {
		Calendar time = new GregorianCalendar();
		response.headers().add(HttpHeaders.Names.DATE, dateFormatter.format(time.getTime()));
	}

	public void setLocation(HttpResponse response, String location) {
		response.headers().add(HttpHeaders.Names.LOCATION, location);
	}

	/**
	 * Sets the Date and Cache headers for the HTTP Response
	 * 
	 * @param response    HTTP response
	 * @param fileToCache file to extract content type
	 */
	public void setDateAndCacheHeaders(HttpResponse response, Date ts, Long cache) {
//		if (ts == null || cache == null || cache.longValue() == 0L) {
//			response.headers().add(HttpHeaders.Names.CACHE_CONTROL, "no-cache");
//			return;
//		}
//
//		SimpleDateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.US);
//		dateFormatter.setTimeZone(TimeZone.getTimeZone(HTTP_DATE_GMT_TIMEZONE));
//
//		// Date header
//		Calendar time = new GregorianCalendar();
//		response.headers().add(HttpHeaders.Names.DATE, dateFormatter.format(time.getTime()));
//
//		// Add cache headers
//		time.add(Calendar.SECOND, cache.intValue());
//		response.headers().add(HttpHeaders.Names.EXPIRES, dateFormatter.format(time.getTime()));
//		response.headers().add(HttpHeaders.Names.CACHE_CONTROL, "private, max-age=" + cache);
//		response.headers().add(HttpHeaders.Names.LAST_MODIFIED, dateFormatter.format(ts));
	}

	/**
	 * Sets the content type header for the HTTP Response
	 * 
	 * @param response HTTP response
	 * @param file     file to extract content type
	 */
	public void setContentTypeHeader(HttpResponse response, String mime) {
		// MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
		// response.headers().add(HttpHeaders.Names.CONTENT_TYPE,
		// mimeTypesMap.getContentType(file.getPath()));
		response.headers().add(HttpHeaders.Names.CONTENT_TYPE, mime);
	}

	public Integer getSockId() {
		return this.socketid;
	}

	public Long isOkForRt() {
		return webSocketOnline;
	}

	// ////////////////////////////////////////////////////////////
	// Sending

	final static private Astate _sendRt = mc.c("sendRt");

	public void sendRt(String msg) {
		ctx.channel().write(new TextWebSocketFrame(msg));
		ctx.channel().flush();
		_sendRt.more();
	}

	private void sendError(HttpResponseStatus status) throws KnownError {
		FullHttpResponse response = null;

		// Format format = getFormat();
		// if(format!=null && Format.JSON){
		// response = new DefaultFullHttpResponse(HTTP_1_1, status,
		// Unpooled.copiedBuffer(
		// html, CharsetUtil.UTF_8));
		// response.headers().add(HttpHeaders.Names.CONTENT_TYPE,
		// "text/html; charset=UTF-8");
		// return;
		// }

		if (status.code() == 404) {

			try {
				String html;
				if (session == null) {
					html = "NOT FOUND";
				} else {
//					String fn = "404-"
//							+ (session != null ? ((.equals("tr") ? "tr" : "en") + ".html") : "");
					String fn = "404-en.html";
					html = FromResource.loadString("/htmlTemplates/" + fn);
				}

				response = new DefaultFullHttpResponse(HTTP_1_1, status,
						Unpooled.copiedBuffer(html, CharsetUtil.UTF_8));
				response.headers().add(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
			} catch (IOException e) {
				Sistem.printStackTrace(e);
			}

		}
		if (response == null) {

			response = new DefaultFullHttpResponse(HTTP_1_1, status,
					Unpooled.copiedBuffer(status.toString(), CharsetUtil.UTF_8));
			response.headers().add(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
		}

		setCookies(response);

		ChannelFuture lastContentFuture = ctx.writeAndFlush(response);
		// Close the connection as soon as the error message is sent.
		lastContentFuture.addListener(ChannelFutureListener.CLOSE);

		requestSatisfied(false, status.code() + "");
	}

	private void redirectHttps(String domain) throws KnownError {
		FullHttpResponse response = null;

//		String html = FromResource.loadString("/htmlTemplates/https.html");
		String html = " <html><head>" + "<meta http-equiv=\"Refresh\" content=\"0;URL=https://" + domain
				+ this.request.getUri() + "\" />" + "</head>" + "<body>Redirecting to secure https connection</body>"
				+ "</html>";

		response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK,
				Unpooled.copiedBuffer(html, CharsetUtil.UTF_8));

		response.headers().add(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");

		ChannelFuture lastContentFuture = ctx.writeAndFlush(response);
		// Close the connection as soon as the error message is sent.
		lastContentFuture.addListener(ChannelFutureListener.CLOSE);

		requestSatisfied(false, "tohttps");
	}

	public void sendFile(File file, final String fileName, HttpRequest request, boolean attach, Long cache)
			throws KnownError, KnownError {
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(file, "r");
			long fileLength;
			fileLength = raf.length();

			HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
			setContentLength(response, fileLength);
			setContentTypeHeader(response, getMime(fileName));
			if (request.getMethod().equals(HttpMethod.GET)) {
				if (cache != null)
					setDateAndCacheHeaders(response, new Date(file.lastModified()), HTTP_CACHE_SECONDS);
			}

			setCookies(response);
			if (isKeepAlive(request)) {
				response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
			}
			if (attach)
				response.headers().add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

			// Write the initial line and the header.
			ctx.write(response);

			// Write the content.
			ChannelFuture writeFuture;
			// if (ctx.pipeline().get(SslHandler.class) != null) {
			// // Cannot use zero-copy with HTTPS.
			// try {
			// writeFuture = ctx.writeAndFlush(new ChunkedFile(raf, 0,
			// fileLength, 8 * 8192));
			// } catch (IOException e1) {
			// creatingChunkError.more();
			// throw new KnownError(e1).internalError();
			// }
			// } else {
			fileTransfer(raf, fileLength);
			requestSatisfied(true, "file");
		} catch (FileNotFoundException fnfe) {
			fileNotFound.more();
			throw new KnownError().notFound(request.getUri());
		} catch (IOException e1) {
			fileLengthGettingError.more();
			throw new InternalError();
		}

		// Decide whether to close the connection or not.
	}

	private void fileTransfer(RandomAccessFile raf, long fileLength) throws IOException {

		ChannelFuture sendFileFuture;
		// if (fileLength < 5000) {
		// sendFileFuture = ctx.write(new DefaultFileRegion(raf.getChannel(), 0,
		// fileLength), ctx.newProgressivePromise());
		// } else {
		sendFileFuture = ctx.write(new ChunkedFile(raf, 0, fileLength, 8192), ctx.newProgressivePromise());
		// }

		sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
			@Override
			public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) {
				// if (total < 0) { // total unknown
				// com.bilgidoku.rom.ServiceDiscovery.log.Sistem.errln("Transfer
				// progress: "
				// + progress);
				// } else {
				// com.bilgidoku.rom.ServiceDiscovery.log.Sistem.errln("Transfer
				// progress: "
				// + progress + " / " +
				// total);
				// }
			}

			@Override
			public void operationComplete(ChannelProgressiveFuture future) throws Exception {
				// com.bilgidoku.rom.ServiceDiscovery.log.Sistem.errln("Transfer
				// complete.");
			}
		});

		ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
		// Decide whether to close the connection or not.
		if (!isKeepAlive(request)) {
			// Close the connection when the whole content is written out.
			lastContentFuture.addListener(ChannelFutureListener.CLOSE);
		}

		// No encryption - use zero-copy.
		// final FileRegion region = new DefaultFileRegion(raf.channel(), 0,
		// fileLength);
		// writeFuture = ch.write(region);
		// writeFuture.addListener(new ChannelFutureProgressListener() {
		// @Override
		// public void operationComplete(ChannelFuture future) {
		// // session.sendProgressEvent(fileName, 0, 0);
		// region.releaseExternalResources();
		// }
		//
		// @Override
		// public void operationProgressed(ChannelFuture future, long amount,
		// long current, long total) {
		// //
		// com.bilgidoku.rom.ServiceDiscovery.log.Sistem.errln("=====================");
		// // com.bilgidoku.rom.ServiceDiscovery.log.Sistem.errln("amount:" +
		// amount + " current:" +
		// // current + " total:" + total);
		// //
		// com.bilgidoku.rom.ServiceDiscovery.log.Sistem.errln("=====================");
		// // session.sendProgressEvent(fileName, total, current);
		// }
		// });
	}

	public void sendNotModified() throws KnownError {
		HttpResponse response = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.NOT_MODIFIED);
		setDateHeader(response);
		setCookies(response);

		// Close the connection as soon as the error message is sent.
		ctx.channel().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		requestSatisfied(true, "notmodified");
	}

	public void redirectLogin(String uri) throws KnownError {
		HttpResponse response = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.TEMPORARY_REDIRECT);
		setLocation(response, "_public/login.html?redirect=" + Knife.urlEncode(uri));
		setCookies(response);

		// Close the connection as soon as the error message is sent.
		ctx.channel().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		requestSatisfied(true, "redirectlogin");
	}

	public void sendResponse(StringBuilder buf, HttpRequest request, Date ts, Long cache) throws KnownError {
		sendResponse(buf.toString(), request, "text/html; charset=UTF-8", ts, cache);
	}

	public void sendResponse(String buf, HttpRequest request, Date ts, Long cache) throws KnownError {
		sendResponse(buf, request, "text/html; charset=UTF-8", ts, cache);
	}

	void requestSatisfied(boolean suc, String desc) {
		if (romResponse == null)
			return;

		romResponse.requestSatisfied(suc, desc);
		romResponse = null;
	}

	public void sendResponse(String buf, HttpRequest request, String contentType, Date ts, Long cache)
			throws KnownError {
		// Decide whether to close the connection or not.
		boolean keepAlive = isKeepAlive(request);
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK,
				Unpooled.copiedBuffer(buf.toString(), CharsetUtil.UTF_8));

		// Build the response object.
		response.headers().add(CONTENT_TYPE, contentType);

		if (keepAlive) {
			// Add 'Content-Length' header only for a keep-alive connection.
			response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
			// Add keep alive header as per:
			// -
			// http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
			response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
		}

		setCookies(response);
		if (request.getMethod().equals(HttpMethod.GET))
			setDateAndCacheHeaders(response, ts, cache);

		// Write the response.
		ChannelFuture lastContentFuture = ctx.writeAndFlush(response);
		// ChannelFuture lastContentFuture =
		// ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);

		// Close the non-keep-alive connection after the write operation is
		// done.l
		if (!keepAlive) {
			lastContentFuture.addListener(ChannelFutureListener.CLOSE);
		}
		requestSatisfied(true, "ok");
	}

	@Override
	public int moduleId() {
		return IpStat.MODULE_HTTP;
	}

	@Override
	public String conId() {
		return "" + socketid;
	}

	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject().safePut("state", state.toString());
		if (webSocketOnline != null) {
			jo.safePut("sock", webSocketOnline);
		}
		return jo;
	}

	@Override
	public void close() {
		if (ctx != null) {
			try {
				ctx.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public String appName() {
		return "hostweb";
	}

	@Override
	public void msg(int mode, String from, String title, String body) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean inStreamingMode() {
		return this.readingChunks;
	}

	@Override
	public RomDomain getDomain() {
		return domain;
	}

	@Override
	public String getCountry() {
		return this.country;
	}

	@Override
	public String getIp() {
		return ip;
	}

	@Override
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public void setIp(String ip) {
		this.ip = ip;
	}

}
