package com.bilgidoku.rom.pg.dict;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.Cookie;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.shared.err.SecurityError;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

public abstract class MethodInteract implements CallInteraction, Closeable {
	private static final MC mc = new MC(MethodInteract.class);

	protected Paramer paramer;
	final RomDomain domain;
	protected final CommonSession session;
	// protected final QueryStringDecoder uri;

	final HttpMethod method;
	protected String resourceId;
	protected Net net;
	private Format format;
	private boolean isOwner = false;
	private RomResource romResource = null;
	private String rid;
	
	private List<DiskFileUpload> toDestroy;

	public void setRid(String rid) {
		this.rid = rid;
	}

	private String rawUri;

	private static final Astate httpParamNull = mc.c("http-param-null");
	private static final Astate httpParamReadIOError = mc.c("httphttp-param-read-io-error");

	public MethodInteract(RomDomain domain, CommonSession session, String rawUri, QueryStringDecoder uri,
			List<InterfaceHttpData> datas, HttpMethod method, Format format) {
		super();
		this.domain = domain;
		this.session = session;
		this.rawUri = rawUri;
		this.paramer = new Paramer(method, uri, datas);
		this.method = method;
		this.format = format;
	}

	public MethodInteract(RomDomain domain, CommonSession session) {
		super();
		this.domain = domain;
		this.session = session;
		this.format = format;
		this.method=HttpMethod.GET;
	}

	public void setParamer(String uri) {
		this.rawUri=uri;
		this.paramer = new Paramer(HttpMethod.GET, new QueryStringDecoder(uri), null);
	}

	@Override
	public String toString() {
		return "MethodInteract:hostId:" + domain.getHostId() + "uri" + paramer.uri;
	}

	public HttpMethod getMethod() {
		return this.method;
	}

	public CommonSession getSession() {
		return this.session;
	}

	public Cookie getCookie() {
		return session.getCookie();
	}

	public int getHostId() {
		if (this.net == Net.INTER)
			return this.session.getInterHostId();
		if (this.net == Net.INTRA)
			return this.session.getIntraHostId();
		if (this.net == Net.ONE)
			return -1;

		return this.domain.getHostId();
	}

	public void setNet(Net net) {
		this.net = net;
	}

	public String getUri() {
		return resourceId;
	}

	public abstract void sendResponse(String s, Date ts, Long cache) throws KnownError, NotInlineMethodException;

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	public void setResource(String resource) {
		this.resourceId = resource;
	}

	@Override
	public void checkRole(int rls) throws SecurityError {
		int roles = getSession().getRole();
		if (isOwner) {
			roles = roles | RoleMask.owner;
		}

		if ((roles & rls) == 0) {
			Sistem.errln("Method needs roles '" + RoleMask.roleToString(rls) + "' but user have roles '"
					+ RoleMask.roleToString(getSession().getRole()) + "'");
			throw new SecurityError();
		}
	}

	public String[] getAuditParams(String[] names) throws ParameterError {
		if (names == null || names.length == 0)
			return null;
		String[] ret = new String[names.length];
		for (int i = 0; i < names.length; i++) {
			ret[i] = paramer.getParam(names[i]);
		}
		return ret;
	}

	// public void setIsOwner(boolean owner) {
	// isOwner=owner;
	// }

	public RomResource getRomResource() {
		return romResource;
	}

	public void setRomResource(RomResource romResource) {
		this.romResource = romResource;
		String cid = session.getCid();
		if (romResource != null && cid != null && romResource.getOwner() != null
				&& cid.equals(romResource.getOwner())) {
			isOwner = true;
		}
	}

	public boolean isContainer() {
		return this.romResource.isContainer();
	}

	public String getRawUri() {
		return rawUri;
	}

	public String getRid() {
		return this.rid;
	}

	@Override
	public RomDomain getDomain() {
		return this.domain;
	}

	@Override
	public List<FileUpload> getFilesParam(String paramName, boolean b) throws NotInlineMethodException, ParameterError {
		return paramer.getFilesParam(paramName, b);
	}

	@Override
	public FileUpload getFileParam(String paramName, boolean b) throws NotInlineMethodException, ParameterError {
		FileUpload fu = paramer.getFileParam(paramName, b);
		if(fu!=null && fu.isInMemory()){
			DiskFileUpload fuu = new DiskFileUpload(fu.getName(), fu.getFilename(), fu.getContentType(), fu.getContentTransferEncoding(), fu.getCharset(), fu.length());
			try {
				ByteBuf bb = fu.getByteBuf();
				bb.retain();
				fuu.setContent(fu.getByteBuf());
				if(toDestroy==null)
					toDestroy=new ArrayList<>();
				toDestroy.add(fuu);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return fuu;
		}
		return fu;
	}

	@Override
	public String getParam(String name, Integer minSize, Integer maxSize, boolean notNull) throws ParameterError {
		return paramer.getParam(name, minSize, maxSize, notNull);
	}
	

	public String getParam(String name) throws ParameterError{
		return paramer.getParam(name);
	}

	@Override
	public Boolean getBoolean(String name, boolean notNull) throws ParameterError {
		return paramer.getBoolean(name, notNull);
	}

	@Override
	public Boolean getBooleanDefault(String name, boolean defaultVal) throws ParameterError {
		return paramer.getBooleanDefault(name, defaultVal);
	}

	@Override
	public Integer getIntParam(String name, boolean notNull) throws ParameterError {
		return paramer.getIntParam(name, notNull);
	}

	@Override
	public Long[] getLongParams(String name, boolean notNull) throws ParameterError {
		return paramer.getLongParams(name, notNull);
	}

	@Override
	public Long getLongParam(String name, boolean notNull) throws ParameterError {
		return paramer.getLongParam(name, notNull);
	}

	@Override
	public void paramOverride(String param, String value) {
		paramer.paramOverride(param, value);
	}

	@Override
	public Map<String, String> getOParams() {
		return paramer.getOParams();
	}

	@Override
	public JSONObject getJsonParam(String name, Integer minSize, Integer maxSize, boolean notNull)
			throws ParameterError {
		return paramer.getJsonParam(name, minSize, maxSize, notNull);
	}

	public String getPath() {
		return paramer.getPath();
	}
	
	@Override
	public int[] getIntParams(String name, boolean notNull) throws ParameterError {
		return paramer.getIntParams(name, notNull);
	}
	
	@Override
	public void close() {
		if(toDestroy!=null){
			for (DiskFileUpload diskFileUpload : toDestroy) {
				diskFileUpload.delete();
			}
		}
		
	}
}