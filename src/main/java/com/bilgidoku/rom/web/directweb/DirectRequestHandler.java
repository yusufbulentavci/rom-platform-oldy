package com.bilgidoku.rom.web.directweb;

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
import java.util.Date;
import java.util.List;

import com.bilgidoku.rom.ilk.file.FromResource;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.mime.MimeGorevlisi;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.dict.Format;
import com.bilgidoku.rom.session.ConnectionSession;
import com.bilgidoku.rom.session.IpStat;
import com.bilgidoku.rom.shared.err.KnownError;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressiveFutureListener;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
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
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCounted;

public class DirectRequestHandler extends ChannelInboundHandlerAdapter implements  ConnectionSession {

	final static private MC mc = new MC(DirectRequestHandler.class);
//	final static private PropertyService ps = ServiceDiscovery.getService(PropertyService.class);
	public static final String RESPONSE_FORMAT_PARAM = "outform";
	

//	static private String SECRET = ps.getString("direct.secret");
//	static private String SECRETNAME = ps.getString("direct.secretName");

	private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);

	

	
	public static final Long HTTP_CACHE_SECONDS = new Long(60 * 60 * 24 * 7);
	private boolean sessionChanged = false;

	private ChannelHandlerContext ctx;
	private HttpRequest request;
	private String country;
	private String ip;
	private InetSocketAddress ipAddress;

	private DirectSession session;
	private boolean cookiesSent;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws KnownError {

		try {
			if (this.ipAddress == null) {
				SocketAddress a = ctx.channel().remoteAddress();
				if (!(a instanceof InetSocketAddress)) {
					throw new KnownError("Not a inet socket");
				}
				ipAddress = (InetSocketAddress) a;
				ip = ipAddress.getHostName();

//				ChannelPipeline pipeline = ctx.channel().pipeline();
//				NioSocketChannel nio = (NioSocketChannel) ctx.channel();
				assert (ipAddress != null);
			}
			
			if (!(msg instanceof HttpContent)) {
				return;
			}
			handleHttpRequest(ctx, (HttpRequest) msg);
		} catch (KnownError e) {
			sendError(ctx, HttpResponseStatus.valueOf(e.httpCode()));
			Sistem.printStackTrace(e);
		}finally{
			ReferenceCounted rc=(ReferenceCounted) msg;
			rc.release();
		}

	}

	private void handleHttpRequest(ChannelHandlerContext ctx, HttpRequest request) throws KnownError {
		this.ctx=ctx;
		this.request=request;
		QueryStringDecoder queryString = new QueryStringDecoder(request.getUri());
//		boolean auth = false;
		if (session == null) {
			String cookieHeader = request.headers().get(HttpHeaders.Names.COOKIE);
			session=DirectSessionGorevlisi.tek().getDirectSession("rom.internet", ipAddress, cookieHeader);
		}


		String uriPath = queryString.path();
		if (uriPath.equals("/favicon.ico")) {
			// String fi = KurumGorevlisi.tek().getFavicon(session.getHostId());
			// File f = null;
			// if (fi == null) {
			// f =
			// hostingFileService.getCommonFile("/_public/images/favicon.ico",
			// true, true);
			// fi = "/_public/images/favicon.ico";
			// } else {
			// f = hostingFileService.getFile(session.getHostId(), fi, true,
			// true);
			// }
			// sendFile(f, fi, e, false, new Long(60 * 60 * 24 * 30));
			return;
		}

		HttpMethod method = request.getMethod();
		if (method == HttpMethod.POST) {
			makePost(request, queryString);
			// }
		} else {
			makeGetOrHead(request, queryString);
		}

	}

	private void makeGetOrHead(HttpRequest request, QueryStringDecoder queryString) throws KnownError {
		Format format = getFormat(request, queryString);
		System.out.println(queryString);
		if(queryString.rawPath().equals("/")) {
			String buf="error";
			try {
				buf = FromResource.loadString("webdirect/web.html");
				sendResponse(buf, request, "text/html; charset=UTF-8", null, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

//		RomHttpResponse response = new RomHttpResponse(this, request, request.getUri(), queryString, null, format);
//		dispatch(response);
	}

	private Format getFormat(HttpRequest request, QueryStringDecoder queryString) {
		if (request.getMethod().equals(HttpMethod.DELETE))
			return Format.JSON;
		List<String> fl = queryString.parameters().get(RESPONSE_FORMAT_PARAM);
		if (fl == null || fl.size() == 0) {
			return Format.JSON;
		}
		return Format.fromStr(fl.get(0));
	}

	private void makePost(HttpRequest request, QueryStringDecoder queryString) throws KnownError {
		HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(factory, request);
		List<InterfaceHttpData> datas = decoder.getBodyHttpDatas();
		System.out.println(queryString);
		
//		RomHttpResponse response = new RomHttpResponse(this, request, request.getUri(), queryString, datas, null);
//		dispatch(response);
	}

//	private void dispatch(RomHttpResponse response) throws KnownError {
//		CagriDagitmaGorevlisi.tek().directHandle(response);
//	}

	public void sendFile(File file, String fileName, HttpRequest request, boolean attach, Long cache) throws KnownError {
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(file, "r");
			long fileLength;
			fileLength = raf.length();

			HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
			setContentLength(response, fileLength);
			setContentTypeHeader(response, getMime(fileName));
//			if (request.getMethod().equals(HttpMethod.GET))
//				setDateAndCacheHeaders(response, new Date(file.lastModified()), HTTP_CACHE_SECONDS);

			if (isKeepAlive(request)) {
				response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
			}
			if (attach)
				response.headers().add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

			// Write the initial line and the header.
			ctx.write(response);

			// Write the content.
			ChannelFuture writeFuture;
			if (ctx.pipeline().get(SslHandler.class) != null) {
				// Cannot use zero-copy with HTTPS.
				try {
					writeFuture = ctx.writeAndFlush(new ChunkedFile(raf, 0, fileLength, 8 * 8192));
				} catch (IOException e1) {
					throw new KnownError(e1).internalError();
				}
			} else {
				fileTransfer(raf, fileLength);
			}
		} catch (FileNotFoundException fnfe) {
			throw new KnownError().notFound(request.getUri());
		} catch (IOException e1) {
			throw new InternalError();
		}

	}


	public void sendResponse(StringBuilder buf, HttpRequest request, Date ts, Long cache) throws KnownError {
		sendResponse(buf.toString(), request, "text/html; charset=UTF-8", ts, cache);
	}

	public void sendResponse(String buf, HttpRequest request, Date ts, Long cache) throws KnownError {
		this.sendResponse(buf, request, "text/html; charset=UTF-8", ts, cache);
	}

	public void sendResponse(String buf, HttpRequest request, String contentType, Date ts, Long cache)
			throws KnownError {
		// Decide whether to close the connection or not.
		boolean keepAlive = isKeepAlive(request);
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.copiedBuffer(buf.toString(),
				CharsetUtil.UTF_8));

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


		// Write the response.
		ChannelFuture lastContentFuture = ctx.writeAndFlush(response);
		// ChannelFuture lastContentFuture =
		// ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);

		// Close the non-keep-alive connection after the write operation is
		// done.
		if (!keepAlive) {
			lastContentFuture.addListener(ChannelFutureListener.CLOSE);
		}
	}

	
	private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) throws KnownError {
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status, Unpooled.copiedBuffer(
				status.toString(), CharsetUtil.UTF_8));
		response.headers().add(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");

		ChannelFuture lastContentFuture = ctx.writeAndFlush(response);
		// Close the connection as soon as the error message is sent.
		lastContentFuture.addListener(ChannelFutureListener.CLOSE);
	}


	private String getMime(String lastPart) throws KnownError {
		String mime = MimeGorevlisi.tek().getMimeOfFile(lastPart);
		return mime;
	}
	
	public void setContentTypeHeader(HttpResponse response, String mime) {
		response.headers().add(HttpHeaders.Names.CONTENT_TYPE, mime);
	}
	
//	public void setDateAndCacheHeaders(HttpResponse response, Date ts, Long cache) {
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
//	}
	
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
				// com.bilgidoku.rom.gunluk.Sistem.errln("Transfer progress: " + progress);
				// } else {
				// com.bilgidoku.rom.gunluk.Sistem.errln("Transfer progress: " + progress + " / " +
				// total);
				// }
			}

			@Override
			public void operationComplete(ChannelProgressiveFuture future) throws Exception {
				// com.bilgidoku.rom.gunluk.Sistem.errln("Transfer complete.");
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
		// // com.bilgidoku.rom.gunluk.Sistem.errln("=====================");
		// // com.bilgidoku.rom.gunluk.Sistem.errln("amount:" + amount + " current:" +
		// // current + " total:" + total);
		// // com.bilgidoku.rom.gunluk.Sistem.errln("=====================");
		// // session.sendProgressEvent(fileName, total, current);
		// }
		// });
	}

	
	@Override
	public int moduleId() {
		return IpStat.MODULE_HTTP;
	}

	@Override
	public String appName() {
		return "directclient";
	}

	@Override
	public String conId() {
		return "";
	}

	@Override
	public JSONObject report() {
		return new JSONObject();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msg(int mode, String from, String title, String body) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean inStreamingMode() {
		return false;
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
		this.country=country;
	}

	@Override
	public void setIp(String ip) {
		this.ip=ip;
	}

	private void setCookies(HttpResponse response) throws KnownError {
		if (session == null)
			return;
		if (!cookiesSent || this.sessionChanged || session.isCookieDirty()) {
			// com.bilgidoku.rom.ServiceDiscovery.log.Sistem.errln("Send new
			// cookie");
			this.sessionChanged=false;
			cookiesSent = true;
			String[] cookieHeaders = session.getCookieHeaders();
			for (String cstr : cookieHeaders) {
				response.headers().add(HttpHeaders.Names.SET_COOKIE, cstr);
			}
		}
	}


}
