package com.bilgidoku.rom.pg.dict;

import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.shared.err.SecurityError;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.shared.Cookie;
import com.bilgidoku.rom.shared.MinRequest;

import io.netty.handler.codec.http.multipart.FileUpload;

public interface CallInteraction extends MinRequest {
	public CommonSession getSession();

	public Cookie getCookie();

	public List<FileUpload> getFilesParam(String paramName, boolean b) throws NotInlineMethodException, ParameterError;

	public int getHostId();

	public FileUpload getFileParam(String paramName, boolean b) throws NotInlineMethodException, ParameterError;

	public String getParam(String name, Integer minSize, Integer maxSize, boolean notNull) throws ParameterError;

	public String[] getAuditParams(String[] names) throws ParameterError;

	public Boolean getBoolean(String name, boolean notNull) throws ParameterError;

	public Boolean getBooleanDefault(String name, boolean defaultVal) throws ParameterError;

	public Integer getIntParam(String name, boolean notNull) throws ParameterError;

	public Long[] getLongParams(String name, boolean notNull) throws ParameterError;

	public Long getLongParam(String name, boolean notNull) throws ParameterError;

	public void paramOverride(String param, String value);

	public String getUri();

	public Format getFormat();

	public void checkRole(int rls) throws SecurityError;

	public boolean isContainer();

	public Map<String, String> getOParams();

	public JSONObject getJsonParam(String name, Integer minSize, Integer maxSize, boolean notNull)
			throws ParameterError;

	public String getRid();

	public RomDomain getDomain();

	public int[] getIntParams(String name, boolean notNull) throws ParameterError;

}
