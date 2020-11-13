package com.bilgidoku.rom.type;

import java.util.HashMap;
import java.util.Map;

import com.bilgidoku.rom.pg.dict.DaoCall;
import com.bilgidoku.rom.pg.dict.DispatchMethod;
import com.bilgidoku.rom.pg.dict.MethodControl;
import com.bilgidoku.rom.pg.dict.Net;
import com.bilgidoku.rom.pg.dict.TypeControl;
import com.bilgidoku.rom.pg.dict.UriHierarychy;

import io.netty.handler.codec.http.HttpMethod;

public class DispatchBase {

	protected static final String admin = "";

	CustomNode[] customNode = new CustomNode[0];

	private TypeControl writing;
	private Map<String, TypeControl> typeControls = new HashMap<String, TypeControl>();
	private Map<String, TypeControl> typeControlsByDbName = new HashMap<String, TypeControl>();

	private Map<String, DispatchMethod> breeds = new HashMap<String, DispatchMethod>();

	public DispatchBase() {
	}

	final protected void add(boolean isService, boolean isCustom, String prefix, String method, long am, String[] roles, HttpMethod formMethod,
			boolean isBreed, String schemaName, String tableName, String superType, UriHierarychy hier, Net net, DaoCall dao, int cpu, String mime) {
		if (isCustom) {
			CustomNode[] c = customNode;
			customNode = new CustomNode[c.length + 1];
			customNode[c.length] = new CustomNode(prefix, dao, net, null);
			for (int i = 0; i < c.length; i++)
				customNode[i] = c[i];
			return;
		}

		if (prefix.endsWith("/") && prefix.length() > 1) {
			prefix = prefix.substring(0, prefix.length() - 1);
		}
		if (method.startsWith("/")) {
			method = method.substring(1);
		}

		MethodControl mc = new MethodControl(am, mime);
		DispatchMethod dn = new DispatchMethod(mc, dao);
		if (isBreed) {
			breeds.put(schemaName + "-" + tableName, dn);
			return;
		}
		TypeControl tc = typeControls.get(prefix);
		if (tc == null) {
			tc = new TypeControl(net, hier, isService, schemaName, tableName, superType, cpu);
			typeControls.put(prefix, tc);
			if (!isService) {
				typeControlsByDbName.put(tc.getDbName(), tc);
			}
			if (prefix.equals("/_/writings")) {
				this.writing = tc;
			}
		}
		tc.addMethod(method, dn);
	}

	final public CustomNode matchCustom(String path) {
		for (CustomNode dn : customNode) {
			if (path.startsWith(dn.prefix)) {
				return dn;
			}
		}
		return null;
	}

	final public DispatchMethod getBreed(String schema, String table) {
		return breeds.get(schema + "-" + table);
	}

	final public DispatchMethod getMethod(String prefix) {
		return breeds.get(prefix);
	}

	final public TypeControl getTypeControl(String prefix) {
		return typeControls.get(prefix);
	}

	final public boolean isWriting(TypeControl tc) {
		return tc == this.writing;
	}

	final public void resolveInheritance() {
		for (TypeControl tc : typeControlsByDbName.values()) {
			tc.resolveInheritance(typeControlsByDbName);
		}
	}
}
