package com.bilgidoku.rom.pop3;

import com.bilgidoku.rom.dns.netmatcher.NetMatcher;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pop3.core.PassCmdHandler;
import com.bilgidoku.rom.protokol.BasicChannelUpstreamHandler;
import com.bilgidoku.rom.protokol.ProtocolServiceAbstraction;
import com.bilgidoku.rom.protokol.protocols.Encryption;
import com.bilgidoku.rom.protokol.protocols.ProtocolConfiguration;
import com.bilgidoku.rom.protokol.protocols.ProtocolSession;
import com.bilgidoku.rom.protokol.protocols.ProtocolTransport;
import com.bilgidoku.rom.protokol.protocols.handler.WiringException;
import com.bilgidoku.rom.session.ConnectionSession;
import com.bilgidoku.rom.session.IpStat;
import com.bilgidoku.rom.session.OturumGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;



public class Pop3Gorevlisi extends ProtocolServiceAbstraction implements ProtocolConfiguration {
	
	public static final int NO=55;
	
	public static Pop3Gorevlisi tek(){
		if(tek==null) {
			synchronized (Pop3Gorevlisi.class) {
				if(tek==null) {
					tek=new Pop3Gorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static Pop3Gorevlisi tek;
	private Pop3Gorevlisi() {
		super("pop3", NO, listen, 1, 10, 10, false, true);
		this.sessionExtension=new Pop3SessionExtension();
		OturumGorevlisi.tek().addExtension(sessionExtension);
	}
	
	

	private final static MC mc = new MC(Pop3Gorevlisi.class);
	
//	private final static SessionService sessionService=ServiceDiscovery.getService(SessionService.class);


//	private final static String greeting = ps.getString("pop3.server.greeting");
//	private final static String listenAddrs = ps.getString("pop3.server.listenAddrs");

	private NetMatcher authorizedNetworks = null;

	private Pop3SessionExtension sessionExtension;
	private static final String[] listen=new String[]{"rom.internet:995"};
	
	

	
	@Override
	public void selfDescribe(JSONObject jo) {
		jo.safePut("session", sessionExtension.selfDescribe());
	}
	
	@Override
	protected void preInit(){
//        if (authorizedAddresses != null) {
//            java.util.StringTokenizer st = new java.util.StringTokenizer(authorizedAddresses, ", ", false);
            java.util.Collection<String> networks = new java.util.ArrayList<String>();
//            while (st.hasMoreTokens()) {
//                String addr = st.nextToken();
//                networks.add(addr);
//            }
            authorizedNetworks = new NetMatcher(networks);
//        }
        
        
        
        
        
    }

//	@Override
//	public void start() {
//		try {
//			bind();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			Sistem.printStackTrace(e);
//		}
//	}
//
//
//	private final static Counter sc=mc.c("setup-handlers");
//	@Override
//	protected ProtocolHandler[] setUpHandlers() {
//		sc.more();
//		return new ProtocolHandler[] { new WelcomeMessageHandler(), new CommandDispatcher(), new CapaCmdHandler(), new UserCmdHandler(),
//				new PassCmdHandler(), new ListCmdHandler(), new UidlCmdHandler(), new RsetCmdHandler(), new DeleCmdHandler(), new NoopCmdHandler(),
//				new RetrCmdHandler(), new TopCmdHandler(), new StatCmdHandler(), new QuitCmdHandler(), new UnknownCmdHandler(),
//				new StlsCmdHandler(), };
//	}
//
//	@Override
//	public Class[] prefferedServices() {
//		return null;
//	}
//
//	@Override
//	protected ChannelUpstreamHandler createUpstream() {
//		// TODO Auto-generated method stub
//		return null;
//	}


	

	@Override
	public String getSoftwareName() {
		return "rom-server";
	}

	
    /**
     * Return the default port which will get used for this server if non is
     * specify in the configuration
     * 
     * @return port
     */
    protected int getDefaultPort() {
        return 110;
    }

    public String getServiceType() {
        return "POP3 Service";
    }

	@Override
	protected ConnectionSession createUpstream() throws KnownError  {
        POP3ProtocolHandlerChain protocolHandlerChain;
		try {
			protocolHandlerChain = new POP3ProtocolHandlerChain(new PassCmdHandler());
			POP3Protocol transport = new POP3Protocol(protocolHandlerChain, this) {
				
				@Override
				public ProtocolSession newSession(ProtocolTransport transport) {
					return sessionExtension.createSession(transport, Pop3Gorevlisi.this);
				}
				
			};
			
			return new POP3ChannelUpstreamHandler(transport, getEncryption());
		} catch (WiringException e) {
			throw new KnownError(e);
		}
	}
	
	public String getGreeting() {
		return "rom-server";
	}

	@Override
	public String getProtocolName() {
		return "pop3";
	}
	
	class POP3ChannelUpstreamHandler extends BasicChannelUpstreamHandler implements ConnectionSession{

		private String ip;
		private String country;

		public POP3ChannelUpstreamHandler(POP3Protocol transport, Encryption encryption) {
			super(transport, encryption);
		}

		@Override
		protected void sessionTerminating(ProtocolSession session) {
			POP3Session psession=(POP3Session) session;
			sessionExtension.logout(psession);
		}

		@Override
		public int moduleId() {
			return IpStat.MODULE_POP3;
		}

		@Override
		public String appName() {
			return "pop3";
		}

		@Override
		public String conId() {
			return "";
		}

		@Override
		public JSONObject report() {
			if(ctx==null)
				return new JSONObject();
			
			return getSession(ctx).toReport();
		}

		@Override
		public void close() {
			if(ctx!=null){
				try{
					ctx.close();
				}catch(Exception e){
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
			this.country=country;
		}

		@Override
		public void setIp(String ip) {
			this.ip=ip;
		}

	}

	@Override
	public int getModuleId() {
		return IpStat.MODULE_POP3;
	}


}
