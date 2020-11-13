package com.bilgidoku.rom.pg.dict;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.params.HttpParams;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.FileUpload;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;

public class Paramer {
	private static final MC mc = new MC(Paramer.class);

	public final QueryStringDecoder uri;
	private final HttpMethod method;
	final List<InterfaceHttpData> datas;
	private final Map<String, String> overrideParam = new HashMap<String, String>();

	public Paramer(HttpMethod method, QueryStringDecoder uri, List<InterfaceHttpData> datas) {
		this.method = method;
		this.uri = uri;
		this.datas = datas;
	}

	private static final Astate httpParamNumberFormatError = mc.c("httphttp-param-number-format-error");

	public String[] getParams(String name) throws ParameterError {
		Boolean isPost = method == HttpMethod.POST;
		if (isPost == null || !isPost) {
			List<String> a = uri.parameters().get(name);
			if (a != null) {
				if (a.size() == 0)
					return new String[] {};
				else
					return (String[]) a.toArray();
			}
		}

		if (isPost == null || isPost) {
			List<String> a = new ArrayList<String>();
			if (datas != null) {
				for (InterfaceHttpData data : datas) {
					if (data.getHttpDataType() == HttpDataType.Attribute && data.getName().equals(name)) {
						Attribute att = (Attribute) data;
						try {
							a.add(att.getString());
						} catch (IOException e) {
							httpParamReadIOError.more();
							throw new ParameterError(name, "io", e);
						}
					}
				}
			}
			if (a.size() != 0)
				return (String[]) a.toArray();
		}
		return null;
	}

	public String[] getParams(String name, boolean notNull) throws ParameterError {
		String[] value = getParams(name);
		if (value == null || value.length == 0) {
			if (notNull) {
				httpParamNull.more();
				throw new ParameterError(name, "missing");
			}
			return null;
		}
		return value;
	}

	public Map<String, String> getOParams() {
		Map<String, String> oParams = null;
		Boolean isPost = method == HttpMethod.POST;
		if (isPost == null || !isPost) {
			for (Entry<String, List<String>> it : uri.parameters().entrySet()) {
				if (it.getKey().startsWith("o_")) {
					if (oParams == null)
						oParams = new HashMap<String, String>();
					if (it.getValue() == null || it.getValue().size() == 0) {
						oParams.put(it.getKey(), null);
					}
					oParams.put(it.getKey(), it.getValue().get(0));
				}
			}
		}

		if (isPost == null || isPost) {
			if (datas != null) {
				for (InterfaceHttpData data : datas) {
					if (data.getHttpDataType() == HttpDataType.Attribute && data.getName().startsWith("o_")) {
						Attribute att = (Attribute) data;
						try {
							if (oParams == null)
								oParams = new HashMap<String, String>();
							oParams.put(data.getName(), att.getString());
						} catch (IOException e) {
							httpParamReadIOError.more();
						}
					}
				}
			}

		}
		return oParams;
	}

	public FileUpload getFileParam(String name, boolean notNull) throws NotInlineMethodException, ParameterError {
		FileUpload value = getFileParam(name);
		if (value != null) {
			return value;
		}
		if (notNull) {
			httpParamNull.more();
			throw new ParameterError(name, "missing");
		}
		return null;
	}

	public FileUpload getFileParam(String name) throws ParameterError {
		Boolean isPost = method == HttpMethod.POST;
		if (isPost == null || !isPost) {
			throw new ParameterError(name, "method");
		}

		if (datas != null) {
			for (InterfaceHttpData data : datas) {
				if (data.getHttpDataType() == HttpDataType.FileUpload && data.getName().equals(name)) {
					FileUpload att = (FileUpload) data;
					return att;
				}
			}
		}
		return null;
	}

	public List<FileUpload> getFilesParam(String name, boolean notNull)
			throws ParameterError, NotInlineMethodException {
		List<FileUpload> value = getFilesParam(name);
		if (value.size() > 0) {
			return value;
		}
		if (notNull) {
			httpParamNull.more();
			throw new ParameterError(name, "missing");
		}
		return value;
	}

	private static final Astate httpParamNull = mc.c("http-param-null");
	private static final Astate httpParamReadIOError = mc.c("httphttp-param-read-io-error");

	public List<FileUpload> getFilesParam(String name) throws ParameterError {
		Boolean isPost = method == HttpMethod.POST;
		if (isPost == null || !isPost) {
			throw new ParameterError(name, "method");
		}
		List<FileUpload> fileParams = new ArrayList<FileUpload>();

		for (InterfaceHttpData data : datas) {
			if (data.getHttpDataType() == HttpDataType.FileUpload && data.getName().equals(name)) {
				FileUpload att = (FileUpload) data;
				fileParams.add(att);
			}
		}
		return fileParams;
	}

	public String getParam(String name) throws ParameterError {
		if (overrideParam.size() > 0) {
			String param = overrideParam.get(name);
			if (param != null)
				return param;
		}

		Boolean isPost = method == HttpMethod.POST;
		if (isPost == null || !isPost) {
			List<String> a = uri.parameters().get(name);
			if (a != null) {
				if (a.size() == 0)
					return "";
				else
					return a.get(0);
			}
		}

		if (isPost == null || isPost) {
			if (datas != null) {
				for (InterfaceHttpData data : datas) {
					if (data.getHttpDataType() == HttpDataType.Attribute && data.getName().equals(name)) {
						Attribute att = (Attribute) data;
						try {
							return att.getString();
						} catch (IOException e) {
							httpParamReadIOError.more();
							throw new ParameterError(name, "io", e);
						}
					}
				}
			}
		}
		return null;
	}

	public final void fillPostParams(HttpParams postParams) throws ParameterError {
		if (datas == null)
			return;
		for (InterfaceHttpData data : datas) {
			if (data.getHttpDataType() == HttpDataType.Attribute) {
				Attribute att = (Attribute) data;
				try {
					postParams.setParameter(att.getName(), att.getValue());
				} catch (IOException e) {
					httpParamReadIOError.more();
					throw new ParameterError(att.getName(), "io", e);
				}
			}
		}
	}

	public final StringBuilder fillPostParams() throws ParameterError {

		StringBuilder strBuffer = new StringBuilder("cmd=_notify-validate");
		if (datas != null) {
			for (InterfaceHttpData data : datas) {
				if (data.getHttpDataType() == HttpDataType.Attribute) {
					Attribute att = (Attribute) data;
					try {
						strBuffer.append("&").append(att.getName()).append("=")
								.append(URLEncoder.encode(att.getValue(), "UTF-8"));
					} catch (IOException e) {
						httpParamReadIOError.more();
						throw new ParameterError(att.getName(), "io", e);
					}
				}
			}
		}
		return strBuffer;
	}

	public final String paramsText() throws ParameterError {
		if (datas == null)
			return null;
		StringBuilder strBuffer = new StringBuilder();
		for (InterfaceHttpData data : datas) {
			if (data.getHttpDataType() == HttpDataType.Attribute) {
				Attribute att = (Attribute) data;
				try {
					strBuffer.append("&").append(att.getName()).append("=")
							.append(URLEncoder.encode(att.getValue(), "UTF-8"));
				} catch (IOException e) {
					httpParamReadIOError.more();
					throw new ParameterError(att.getName(), "io", e);
				}
			}
		}
		return strBuffer.toString();
	}

	private static final Astate httpParamShort = mc.c("http-param-short");

	public Integer getIntParam(String name, boolean notNull) throws ParameterError {
		String ival = getParam(name, null, null, notNull);
		if (ival == null)
			return null;
		try {
			return Integer.parseInt(ival);
		} catch (NumberFormatException e) {
			httpParamNumberFormatError.more();
			throw new ParameterError(name, "format");
		}
	}

	public Long[] getLongParams(String name, boolean notNull) throws ParameterError {
		String[] ival = getParams(name);
		if (ival == null || ival.length == 0) {
			if (notNull) {
				httpParamNull.more();
				throw new ParameterError(name, "missing");
			}
			return null;
		}

		Long[] longvals = new Long[ival.length];
		for (int i = 0; i < ival.length; i++) {
			String val = ival[i];
			try {
				longvals[i] = Long.parseLong(val);
			} catch (NumberFormatException e) {
				httpParamNumberFormatError.more();
				throw new ParameterError(name, "format");
			}
		}
		return longvals;

	}

	private static final Astate httpParamLong = mc.c("http-param-long");
	private static final Astate encodingUtf8NotSupported = mc.c("sys-encoding-utf8-not-supported");

	public String getParam(String name, Integer minSize, Integer maxSize, boolean notNull) throws ParameterError {
		String value = getParam(name);
		if (value == null || value.length() == 0) {
			if (notNull) {
				httpParamNull.more();
				throw new ParameterError(name, "missing");
			}
			return null;
		}

		if (minSize != null && minSize > value.length()) {
			httpParamShort.more();
			throw new ParameterError(name, "short");
		}

		if (maxSize != null && maxSize < value.length()) {
			httpParamLong.more();
			throw new ParameterError("name", "long");
		}

		return value;
	}

	public Long getLongParam(String name, boolean notNull) throws ParameterError {
		String ival = getParam(name, null, null, notNull);
		if (ival == null)
			return null;
		try {
			return Long.parseLong(ival);
		} catch (NumberFormatException e) {
			httpParamNumberFormatError.more();
			throw new ParameterError(name, "format");
		}
	}

	public Boolean getBoolean(String name, boolean notNull) throws ParameterError {
		String param = getParam(name, null, null, notNull);
		if (param == null)
			return null;
		return param != null && param.equals("true");
	}

	public Boolean getBooleanDefault(String name, boolean defaultVal) throws ParameterError {
		String param = getParam(name, null, null, false);
		if (param == null)
			return defaultVal;
		return param.equals("true");
	}

	public void paramOverride(String param, String value) {
		this.overrideParam.put(param, value);
	}

	public String getPath() {
		String path = this.uri.path();
		if (path == null)
			return "/";
		return path;
	}

	public JSONObject getJsonParam(String name, Integer minSize, Integer maxSize, boolean notNull)
			throws ParameterError {
		String ival = getParam(name, minSize, maxSize, notNull);
		if (ival == null)
			return null;
		try {
			return new JSONObject(ival);
		} catch (JSONException e) {
			httpParamNumberFormatError.more();
			throw new ParameterError(name, "format");
		}
	}

	public int[] getIntParams(String name, boolean notNull) throws ParameterError {
		String[] ival = getParams(name);
		if (ival == null || ival.length == 0) {
			if (notNull) {
				httpParamNull.more();
				throw new ParameterError(name, "missing");
			}
			return null;
		}

		int[] longvals = new int[ival.length];
		for (int i = 0; i < ival.length; i++) {
			String val = ival[i];
			try {
				longvals[i] = Integer.parseInt(val);
			} catch (NumberFormatException e) {
				httpParamNumberFormatError.more();
				throw new ParameterError(name, "format");
			}
		}
		return longvals;

	}

}
