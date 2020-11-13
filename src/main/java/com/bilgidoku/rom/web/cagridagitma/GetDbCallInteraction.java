package com.bilgidoku.rom.web.cagridagitma;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.pg.dict.CommonSession;
import com.bilgidoku.rom.pg.dict.MethodInteract;
import com.bilgidoku.rom.pg.dict.RomDomain;
import com.bilgidoku.rom.shared.Cookie;
import com.bilgidoku.rom.shared.Postman;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.shared.json.JSONArray;

import io.netty.handler.codec.http.multipart.FileUpload;

public class GetDbCallInteraction extends MethodInteract implements Postman {


	private final MethodInteract parent;
//	private Paramer paramer;
	
	public GetDbCallInteraction(MethodInteract parent) {
		super(parent.getDomain(), parent.getSession());
		this.parent = parent;

	}

	/**
	 * Todo:
	 */
	@Override
	public String postManGetJson(String href) throws RunException {
		try {
			setParamer(href);
			return IcCagriDagitmaGorevlisi.tek().call(this).toString();
		} catch (KnownError e) {
			throw new RunException("Error while getting json:" + href, e);
		}
	}

	@Override
	public String postManSynch(Object session, String href, String data) throws RunException {
		throw new RuntimeException("Shouldnt be called(server side)-postSynch");
	}

	@Override
	public String postManSubmit(String formId, String resetFields) throws RunException {
		throw new RunException("Server side submitting not accepted");
	}
	
	@Override
	public void sendResponse(String s, Date ts, Long cache) throws KnownError, NotInlineMethodException {
		throw new KnownError("Server side sendResponse not possible");
	}

	@Override
	public List<FileUpload> getFilesParam(String paramName, boolean b) throws NotInlineMethodException {
		throw new NotInlineMethodException(paramer.uri.toString());
	}

	@Override
	public FileUpload getFileParam(String paramName, boolean b) throws NotInlineMethodException {
		throw new NotInlineMethodException(paramer.uri.toString());
	}

	@Override
	public String getReqLang() {
		return parent.getReqLang();
	}

	@Override
	public Postman getPostman() {
		return null;
	}

	@Override
	public CommonSession getSession() {
		return parent.getSession();
	}

	@Override
	public Cookie getCookie() {
		return parent.getCookie();
	}

	
	@Override
	public RomDomain getDomain() {
		return parent.getDomain();
	}
	

	@Override
	public String getParam(String name, Integer minSize, Integer maxSize, boolean notNull) throws ParameterError {
		return paramer.getParam(name, minSize, maxSize, notNull);
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
	public boolean isInitial() {
		return false;
	}

	@Override
	public int getBw() {
		return parent.getBw();
	}

	@Override
	public JSONArray getLangs() {
		return parent.getLangs();
	}

	@Override
	public boolean isACookie(String name) {
		return parent.isACookie(name);
	}
}
