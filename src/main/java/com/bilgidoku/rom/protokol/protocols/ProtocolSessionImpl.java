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

package com.bilgidoku.rom.protokol.protocols;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.EventSource;
import com.bilgidoku.rom.ilk.util.EventSourceImpl;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.dict.CommonSession;
import com.bilgidoku.rom.pg.dict.RomDomain;
import com.bilgidoku.rom.pg.dict.RomUser;
import com.bilgidoku.rom.protokol.protocols.handler.LineHandler;
import com.bilgidoku.rom.shared.Cookie;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.yerel.YerelGorevlisi;

/**
 * Basic implementation of {@link ProtocolSession}
 * 
 * 
 */
public abstract class ProtocolSessionImpl<PSA extends ProtocolSessionActivity> implements ProtocolSession<PSA> {
	private static MC mc = new MC(ProtocolSessionImpl.class);

	private final ProtocolTransport transport;
	// private final Map<String, Object> connectionState;
	// private final Map<String, Object> sessionState;
	protected final ProtocolConfiguration config;
	private int id;
	private final static Charset CHARSET = Charset.forName("US-ASCII");
	private final static String DELIMITER = "\r\n";
	private final static AtomicInteger IDGEN = new AtomicInteger();

	protected final long startTime;
	// protected RomDomain domain;
	private String user=null;
	protected RomUser romUser=null;
	protected String ipAddress;
	private Collection<ByteBuffer> bufferedLines;
	
	private final EventSource<CommonSession> lifeCycleSource=new EventSourceImpl<>();

	// private JSONObject report;

	public ProtocolSessionImpl(ProtocolTransport transport, ProtocolConfiguration config) {
		this.transport = transport;
		this.startTime = System.currentTimeMillis();
		// this.connectionState = new HashMap<String, Object>();
		// this.sessionState = new HashMap<String, Object>();
		this.config = config;
		this.id = IDGEN.getAndIncrement();
		this.ipAddress = getRemoteAddress().getHostString();
		try {
			this.country=YerelGorevlisi.tek().getCountryCode(ipAddress);
			this.countryLang=YerelGorevlisi.tek().langOfCountry(country);
		} catch (KnownError e) {
			Sistem.printStackTrace(e);
		}

	}

	@Override
	public JSONObject toReport(){
		// if(report!=null)
		// return this.report;

		JSONObject jo = new JSONObject();
		jo.safePut("tr", protocolName());
		jo.safePut("st", startTime);
		jo.safePut("ip", ipAddress);
		if (user != null) {
			jo.safePut("u", user);
		}
		if (romUser != null) {
			jo.safePut("h", romUser.getIntraHostId());
			jo.safePut("ru", romUser.getCid());
		}

		return jo;
	}

	public InetSocketAddress getLocalAddress() {
		return transport.getLocalAddress();
	}

	public InetSocketAddress getRemoteAddress() {
		return transport.getRemoteAddress();
	}

	/**
	 * @see com.bilgidoku.rom.protokol.protocols.ProtocolSession#getProtocolUser()
	 */
	public String getProtocolUser() {
		return user;
	}

	/**
	 * @see com.bilgidoku.rom.protokol.protocols.ProtocolSession#setProtocolUser(java.lang.String)
	 */
	public void setProtocolUser(String user) {
		this.user = user;
	}

	/**
	 * Return the wrapped {@link ProtocolTransport} which is used for this
	 * {@link ProtocolSession}
	 * 
	 * @return transport
	 */
	public ProtocolTransport getProtocolTransport() {
		return transport;
	}

	/**
	 * @see com.bilgidoku.rom.protokol.protocols.ProtocolSession#isStartTLSSupported()
	 */
	public boolean isStartTLSSupported() {
		return transport.isStartTLSSupported();
	}

	/**
	 * @see com.bilgidoku.rom.protokol.protocols.ProtocolSession#isTLSStarted()
	 */
	public boolean isTLSStarted() {
		return transport.isTLSStarted();
	}

	/**
	 * @see com.bilgidoku.rom.protokol.protocols.ProtocolSession#getSessionID()
	 */
	public String getSessionID() {
		return id + "";
	}
	
	@Override
	public String getSid() {
		return id + "";
	}

	// /**
	// * @see com.bilgidoku.rom.protokol.protocols.ProtocolSession#getConnectionState()
	// */
	// public Map<String, Object> getConnectionState() {
	// return connectionState;
	// }
	//
	// /**
	// * @see com.bilgidoku.rom.protokol.protocols.ProtocolSession#getState()
	// */
	// public Map<String, Object> getState() {
	// return sessionState;
	// }

	/**
	 * This implementation just returns <code>null</code>. Sub-classes should
	 * overwrite this if needed
	 */
	public Response newLineTooLongResponse() {
		return null;
	}

	/**
	 * This implementation just returns <code>null</code>. Sub-classes should
	 * overwrite this if needed
	 */
	public Response newFatalErrorResponse() {
		return null;
	}

	/**
	 * This implementation just clears the sessions state. Sub-classes should
	 * overwrite this if needed
	 */
	public void resetState() {
		// sessionState.clear();
	}

	/**
	 * @see com.bilgidoku.rom.protokol.protocols.ProtocolSession#getConfiguration()
	 */
	public ProtocolConfiguration getConfiguration() {
		return config;
	}

	// public Object setAttachment(String key, Object value, State state) {
	// if (state == State.Connection) {
	// if (value == null) {
	// return connectionState.remove(key);
	// } else {
	// return connectionState.put(key, value);
	// }
	// } else {
	// if (value == null) {
	// return sessionState.remove(key);
	// } else {
	// return sessionState.put(key, value);
	// }
	// }
	// }
	//
	// public Object getAttachment(String key, State state) {
	// if (state == State.Connection) {
	// return connectionState.get(key);
	// } else {
	// return sessionState.get(key);
	// }
	// }

	/**
	 * Returns a Charset for US-ASCII
	 */
	public Charset getCharset() {
		return CHARSET;
	}

	/**
	 * Returns "\r\n";
	 */
	public String getLineDelimiter() {
		return DELIMITER;
	}

	public void popLineHandler() {
		transport.popLineHandler();
	}

	public int getPushedLineHandlerCount() {
		return transport.getPushedLineHandlerCount();
	}

	public <T extends ProtocolSession<PSA>> void pushLineHandler(LineHandler<T> overrideCommandHandler) {
		transport.pushLineHandler(overrideCommandHandler, this);
	}

	@Override
	public int getInterHostId() {
		if (romUser == null)
			return -1;
		return this.romUser.getInterHostId();
	}

	@Override
	public int getIntraHostId() {
		if (romUser == null)
			return -1;
		return this.romUser.getIntraHostId();
	}

//	@Override
//	public int getHostId() {
//		if (romUser == null)
//			return -1;
//		return this.romUser.getDomain().getHostId();
//	}

	@Override
	public RomDomain getDomain() {
		return romUser.getDomain();
	}


	@Override
	public String getCid() {
		return romUser.getCid();
	}

	@Override
	public Cookie getCookie() {
		return null;
	}

	@Override
	public String getUserName() {
		return romUser.getName();
	}

	//	@Override
	//	public RomUser getUser() {
	//		return romUser;
	//	}

	@Override
	public String getIpAddress() {
		return ipAddress;
	}

	@Override
	public void visiting(String self) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isGuest() {
		return romUser==null || romUser.isGuest();
	}

	@Override
	public void appendCpu(int cpu) {
		// TODO Auto-generated method stub

	}

	@Override
	public int screenWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void changeUser(RomUser ru) {
		this.romUser = ru;
	}

	@Override
	public Collection<ByteBuffer> getBufferedLines() {
		return this.bufferedLines;
	}

	@Override
	public Collection<ByteBuffer> setBufferedLines(Collection<ByteBuffer> lines) {
		Collection<ByteBuffer> ret = bufferedLines;
		bufferedLines = lines;
		return ret;
	}

	@Override
	public void loggedIn(RomUser user) {
		changeUser(user);
		setProtocolUser(user.getName());
	}

	@Override
	public String getUserEmail() {
		if (romUser == null)
			return null;
		return romUser.getEmail();
	}

	private int commandLogIndex = 29;
	private CommandLog[] commandLog = new CommandLog[30];


	private String country;


	private String countryLang;

	@Override
	public CommandLog addCommandLog(String command, String arg) {
		CommandLog cl = new CommandLog(command, arg);
		synchronized (commandLog) {
//			commandLog[commandLogIndex] = null;
			commandLogIndex = (commandLogIndex + 1) % commandLog.length;
			commandLog[commandLogIndex] = cl;
		}
		return cl;
	}

	public JSONArray reportCommandLog() {
		JSONArray ret = new JSONArray();
		if (commandLog[commandLogIndex] == null)
			return ret;
		try {
			for (int i = 0; i < commandLog.length; i++) {
				CommandLog cl = commandLog[(commandLogIndex + 1 + i) % commandLog.length];
				if (cl == null){
					continue;
				}
				ret.put(cl.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}

	

	@Override
	public String getEmailDomain() {
		if(romUser==null)
			return null;
		return romUser.getDomain().getEmailDomain();
	}

	@Override
	public String getTopLevelDomain() {
		if(romUser==null)
			return null;
		return romUser.getDomain().getEmailDomain();
	}
	
	@Override
	public String getCountry() {
		return this.country;
	}

	@Override
	public String getCountryLang() {
		return this.countryLang;
	}

	@Override
	public EventSource<CommonSession> getLifeCycleSource() {
		return lifeCycleSource;
	}
}
