package com.bilgidoku.rom.smtp;

import com.bilgidoku.rom.dns.netmatcher.NetMatcher;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.protokol.BasicChannelUpstreamHandler;
import com.bilgidoku.rom.protokol.LifecycleUtil;
import com.bilgidoku.rom.protokol.ProtocolServiceAbstraction;
import com.bilgidoku.rom.protokol.protocols.Encryption;
import com.bilgidoku.rom.protokol.protocols.ProtocolSession;
import com.bilgidoku.rom.protokol.protocols.ProtocolSessionExtension;
import com.bilgidoku.rom.protokol.protocols.ProtocolTransport;
import com.bilgidoku.rom.protokol.protocols.handler.WiringException;
import com.bilgidoku.rom.session.ConnectionSession;
import com.bilgidoku.rom.session.IpStat;
import com.bilgidoku.rom.session.OturumGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;

public class SmtpGorevlisi extends ProtocolServiceAbstraction implements SMTPConfiguration {
	private final static MC mc = new MC(SmtpGorevlisi.class);
	
	public static final int NO=54;

	public static SmtpGorevlisi tek(){
		if(tek==null) {
			synchronized (SmtpGorevlisi.class) {
				if(tek==null) {
					tek=new SmtpGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static SmtpGorevlisi tek;
	
	public SmtpGorevlisi() {
		super("Smtp", NO, listen, 2, 20, 10, false, true);
		maxMessageSize = 10000 * 1024;
		this.sslPort = 587;
		OturumGorevlisi.tek().addExtension(SmtpSessionExtension.one());
	}
	
	@Override
	protected void kur() throws KnownError {
		super.kur();
	}
	


	private ProtocolSessionExtension sessionExtension;

	private final int maxMessageSize;
	private boolean heloEhloEnforcement = true;
	private boolean addressBracketsEnforcement = true;
	private boolean verifyIdentity;

	/**
	 * Whether authentication is required to use this SMTP server.
	 */
	private final static int AUTH_DISABLED = 0;
	private final static int AUTH_REQUIRED = 1;
	private final static int AUTH_ANNOUNCE = 2;
	private int authRequired = AUTH_DISABLED;

	/**
	 * This is a Network Matcher that should be configured to contain authorized
	 * networks that bypass SMTP AUTH requirements.
	 */
	private NetMatcher authorizedNetworks = null;

	// private SMTPChannelUpstreamHandler upstream;

	// private final static Astate le = mc.c("smtp-service-load-error");
	// "rom.internet:" + Mode.priorityPort(25)

	private static final String[] listen = new String[] { "rom.internet:" + 587,
			"rom.internet:" + 25 };

	

	@Override
	public void selfDescribe(JSONObject jo) {
		jo.safePut("session", sessionExtension.selfDescribe());
	}

	@Override
	protected void preInit() {
		// if (authorizedAddresses != null) {
		// java.util.StringTokenizer st = new
		// java.util.StringTokenizer(authorizedAddresses, ", ", false);
		java.util.Collection<String> networks = new java.util.ArrayList<String>();
		// while (st.hasMoreTokens()) {
		// String addr = st.nextToken();
		// networks.add(addr);
		// }
		authorizedNetworks = new NetMatcher(networks);
		// }

		// SMTPProtocolHandlerChain protocolHandlerChain=new
		// SMTPProtocolHandlerChain();
		// SMTPProtocol transport = new SMTPProtocol(protocolHandlerChain, this)
		// {
		//
		// @Override
		// public ProtocolSession newSession(ProtocolTransport transport) {
		// return new SMTPSessionImpl(transport, SmtpServiceImpl.this);
		// }
		//
		// };
		// upstream = new SMTPChannelUpstreamHandler(transport,
		// getEncryption());

	}

	

	@Override
	public String getSoftwareName() {
		return "Rom smtp server";
	}

	@Override
	public long getMaxMessageSize() {
		return maxMessageSize;
	}

	@Override
	public boolean isRelayingAllowed(String remoteIP) {
		return false;
	}

	@Override
	public boolean isAuthRequired(String remoteIP) {
		return false;
	}

	@Override
	public boolean useHeloEhloEnforcement() {
		return heloEhloEnforcement;
	}

	@Override
	public boolean useAddressBracketsEnforcement() {
		return addressBracketsEnforcement;
	}

	@Override
	public boolean verifyIdentity() {
		return verifyIdentity;
	}

	@Override
	protected ConnectionSession createUpstream() throws KnownError {
		SMTPProtocolHandlerChain protocolHandlerChain;
		try {
			protocolHandlerChain = new SMTPProtocolHandlerChain();
		} catch (WiringException e) {
			throw new KnownError("Construction SMTPProtocolHandlerChain", e);
		}
		SMTPProtocol transport = new SMTPProtocol(protocolHandlerChain, this) {

			@Override
			public ProtocolSession newSession(ProtocolTransport transport) {
				return sessionExtension.createSession(transport, SmtpGorevlisi.this);
			}

		};
		SMTPChannelUpstreamHandler upstream = new SMTPChannelUpstreamHandler(transport, getEncryption());
		return upstream;
	}

	// protected ChannelUpstreamHandler createUpstream() {
	// return upstream;
	// }
	/**
	 * Return the default port which will get used for this server if non is
	 * specify in the configuration
	 * 
	 * @return port
	 */
	protected int getDefaultPort() {
		return 25;
	}

	public String getServiceType() {
		return "SMTP Service";
	}

	public void ready() {
		// TODO Auto-generated method stub

	}

	public String name() {
		return SmtpGorevlisi.class.getName();
	}

	public String getGreeting() {
		return "rom-server";
	}

	@Override
	public String getProtocolName() {
		return "smtp";
	}

	class SMTPChannelUpstreamHandler extends BasicChannelUpstreamHandler implements ConnectionSession {

		private String country;
		private String ip;

		public SMTPChannelUpstreamHandler(SMTPProtocol protocol, Encryption encryption) {
			super(protocol, encryption);
		}

		public SMTPChannelUpstreamHandler(SMTPProtocol protocol) {
			super(protocol);
		}

		@Override
		protected void sessionTerminating(ProtocolSession session) {
			SMTPSession smtpSession = (SMTPSession) session;
			sessionExtension.logout(smtpSession);
			// // LifecycleUtil.dispose(
			// smtpSession.getMail();
			// // );
			LifecycleUtil.dispose(smtpSession.getMimeStreamSource());

		}

		@Override
		public int moduleId() {
			return IpStat.MODULE_SMTP;
		}

		@Override
		public String appName() {
			return "smtpin";
		}

		@Override
		public String conId() {
			return "";
		}

		@Override
		public JSONObject report() {
			if (ctx == null) {
				return new JSONObject();
			}

			return getSession(ctx).toReport();
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
			this.country = country;
		}

		@Override
		public void setIp(String ip) {
			this.ip = ip;
		}

	}

	@Override
	public int getModuleId() {
		return IpStat.MODULE_SMTP;
	}

}
