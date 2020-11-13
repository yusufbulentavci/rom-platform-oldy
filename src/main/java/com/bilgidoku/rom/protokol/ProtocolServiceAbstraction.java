package com.bilgidoku.rom.protokol;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLContext;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.util.Genel;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.protokol.protocols.Encryption;
import com.bilgidoku.rom.protokol.protocols.ProtocolServer;
import com.bilgidoku.rom.run.KosuGorevlisi;
import com.bilgidoku.rom.secure.GuvenlikGorevlisi;
import com.bilgidoku.rom.session.ConnectionSession;
import com.bilgidoku.rom.session.IpStat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Abstract base class for Servers for all Servers
 */
public abstract class ProtocolServiceAbstraction extends GorevliDir implements ProtocolServer {
	private static final MC mc = new MC(ProtocolServiceAbstraction.class);

	private final String serviceName;

	private int maxExecutorThreads;
	private final int backlog;
	protected final int timeout;
	private final int ioWorker;

	// protected int connPerIP;

	private boolean useStartTLS;
	private boolean useSSL;

	protected int connectionLimit;

	private String helloName;

	// private final String x509Algorithm =
	// ps.getString("secure.tls.algorithm");
	// private final String keystore = ps.getString("secure.keyFile.private");
	// private final String secret = ps.getString("secure.secret");

	// private String[] enabledCipherSuites;
	private Encryption encryption;
	private ConnectionCountHandler countHandler = new ConnectionCountHandler();

	protected Integer sslPort = null;

	private ServerBootstrap bootstrap;

	private volatile boolean started;

	private final NioEventLoopGroup bossGroup;
	private final NioEventLoopGroup workerGroup;

	private List<InetSocketAddress> addresses = new ArrayList<InetSocketAddress>();
	private int moduleId;

	private String[] listen;

	// private final String keyStoreFile;

	private static Astate bound = mc.c("bound");
	private static Astate bindUnknownHost = mc.c("bind-unknown-host");
	private static Astate useAndSocketTls = mc.c("use-and-socket-tls-at-the-same-time");

	protected void dur() {
		unbind();
	};

	public ProtocolServiceAbstraction(String kodu, int no, String[] listen, int ioWorkerCount, int maxExecCount,
			int conLimit, boolean startTls, boolean socketTls) {
		super(kodu, no);
		this.serviceName = kodu;
		this.moduleId = IpStat.getModuleIdByName(serviceName);
		this.listen = listen;
		ioWorker = ioWorkerCount;

		maxExecutorThreads = maxExecCount;

		helloName = Genel.getHostName();

		timeout = 300;
		backlog = 200;
		connectionLimit = conLimit;
		// connPerIP = cc.getInt("limitPerIp");

		useStartTLS = startTls;
		useSSL = socketTls;
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();

	}

	protected void kur() throws KnownError {

		List<InetSocketAddress> bindAddresses = new ArrayList<InetSocketAddress>();
		for (int i = 0; i < listen.length; i++) {
			String bind[] = listen[i].split(":");

			InetSocketAddress address;
			String ip = bind[0].trim();
			int port = Integer.parseInt(bind[1].trim());
			if (ip.equals("0.0.0.0") == false) {
				try {
					ip = InetAddress.getByName(ip).getHostName();
				} catch (final UnknownHostException unhe) {
					bindUnknownHost.more();
					// throw new ServiceException(serviceName, unhe,
					// "Unknown host:"+ip);
				}
			}
			address = new InetSocketAddress(ip, port);

			bound.more(serviceName + " ip:" + ip + ":" + port);

			bindAddresses.add(address);
		}
		setListenAddresses(bindAddresses.toArray(new InetSocketAddress[0]));

		if (useSSL && useStartTLS) {
			useAndSocketTls.more();
			// throw new ServiceException(serviceName, null,
			// "Configuration error useSSL and useStartTLS can not be set at
			// the same time");
		}

		// if (useStartTLS || useSSL) {
		// enabledCipherSuites = config.getStringArray("tls.cipherSuite");
		// keystore = config.getString("tls.keystore");
		// secret = config.getString("tls.secret");
		// x509Algorithm = config.getString("tls.algorithm");
		// }
		init();
	};

	private static Astate init = mc.c("initialized");

	public final void init() throws KnownError {
		init.more(serviceName);
		buildSSLContext();
		init.more(serviceName + ": ssl ready");
		preInit();
		init.more(serviceName + ": preInit done");
		bind();
		init.more(serviceName + ": bind done");
		init.more(serviceName + ": initialized done");

	}

	private static Astate destroyed = mc.c("destroyed");

	public final void destroy() {
		unbind();
		postDestroy();
		destroyed.more(serviceName);
	}

	protected void postDestroy() {
		// override me
	}

	protected void preInit() {
	}

	Astate localHostResError = mc.c("unknown-host-for-localhost");
	Astate helloNameConfig = mc.c("found-out-hello-name");

	/**
	 * Return helloName for this server
	 * 
	 * @return helloName
	 */
	public String getHelloName() {
		return helloName;
	}

	protected Encryption getEncryption() {
		return encryption;
	}

	private static Astate nosuchalgo = mc.c("no-such-algo");
	private static Astate certerror = mc.c("certificate-error");
	private static Astate keystoreio = mc.c("keystore-file-io-error");
	private static Astate keystoree = mc.c("keystore-error");
	private static Astate unrecoverkey = mc.c("unrecover-key");
	private static Astate keymng = mc.c("key-management-error");

	private void buildSSLContext() throws KnownError {
		if (useStartTLS || useSSL) {

			encryption = createEncyption();
		}
	}

	Encryption createEncyption() throws KnownError {
		SSLContext context = GuvenlikGorevlisi.tek().getSslContext();
		if (useStartTLS) {
			return Encryption.createStartTls(context, null);
		} else {
			return Encryption.createTls(context, null);
		}
	}

	/**
	 * Return the socket type. The Socket type can be secure or plain
	 * 
	 * @return the socket type ('plain' or 'secure')
	 */
	public String getSocketType() {
		if (encryption != null && !encryption.isStartTLS()) {
			return "secure";
		}
		return "plain";
	}

	public boolean getStartTLSSupported() {
		return encryption != null && encryption.isStartTLS();
	}

	public int getMaximumConcurrentConnections() {
		return connectionLimit;
	}

	public boolean isStarted() {
		return isBound();
	}

	public long getHandledConnections() {
		return countHandler.getConnectionsTillStartup();
	}

	public int getCurrentConnections() {
		return countHandler.getCurrentConnectionCount();
	}

	protected ConnectionCountHandler getConnectionCountHandler() {
		return countHandler;
	}

	public String[] getBoundAddresses() {

		List<InetSocketAddress> addresses = getListenAddresses();
		String[] addrs = new String[addresses.size()];
		for (int i = 0; i < addresses.size(); i++) {
			InetSocketAddress address = addresses.get(i);
			addrs[i] = address.getHostName() + ":" + address.getPort();
		}

		return addrs;
	}

	/**
	 * Configure the bootstrap before it get bound
	 * 
	 * @param bootstrap
	 */
	protected void configureBootstrap(ServerBootstrap bootstrap) {
		// Bind and start to accept incoming connections.
		bootstrap.option(ChannelOption.SO_BACKLOG, backlog);
		bootstrap.option(ChannelOption.SO_REUSEADDR, true);
//		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
	}

	protected abstract ConnectionSession createUpstream() throws KnownError;

	protected ChannelInitializer<SocketChannel> createPipelineFactory() {
		return new AbstractExecutorAwareChannelPipelineFactory(getTimeout(), connectionLimit) {
			@Override
			protected SSLContext getSSLContext() {
				if (encryption == null) {
					return null;
				} else {
					return encryption.getContext();
				}
			}

			@Override
			protected boolean isSSLSocket(SocketChannel ch) {
				return encryption != null && !encryption.isStartTLS()
						&& (sslPort == null || sslPort.equals(ch.localAddress().getPort()));
			}

			@Override
			protected ChannelHandler createCoder() {
				return ProtocolServiceAbstraction.this.createCoder();

			}

			@Override
			protected ConnectionSession createHandler() throws KnownError {
				return ProtocolServiceAbstraction.this.createUpstream();

			}

			@Override
			protected ConnectionCountHandler getConnectionCountHandler() {
				return ProtocolServiceAbstraction.this.getConnectionCountHandler();
			}

			@Override
			public String serviceName() {
				return serviceName;
			}

		};
	}

	protected ChannelHandler createCoder() {
		return null;
	}

	public synchronized void setListenAddresses(InetSocketAddress... addresses) {
		if (started)
			throw new IllegalStateException("Can only be set when the server is not running");
		this.addresses = Collections.unmodifiableList(Arrays.asList(addresses));
	}

	/**
	 * Return the IO worker thread count to use
	 * 
	 * @return ioWorker
	 */
	public int getIoWorkerCount() {
		return ioWorker;
	}

	public synchronized void bind() throws KnownError {
		if (started)
			throw new IllegalStateException("Server running already");

		if (addresses.isEmpty())
			throw new RuntimeException(
					"Please specify at least on socketaddress to which the server should get bound!");

		bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.childHandler(createPipelineFactory());
		configureBootstrap(bootstrap);
		int i = 0;
		try {
//			Mode.reRoot();
			for (i = 0; i < addresses.size(); i++) {
				mc.out("Binding to:" + addresses.get(i));
				bootstrap.bind(addresses.get(i)).sync();

			}
		} catch (Exception e) {
			throw new KnownError("Failed to bind:" + addresses.get(i).toString(), e);
		} finally {
//			Mode.dropRoot();
		}
		started = true;
	}

	public synchronized void unbind() {
		if (started == false)
			return;
		// ChannelPipelineFactory factory = bootstrap.getPipelineFactory();
		// if (factory instanceof ExternalResourceReleasable) {
		// ((ExternalResourceReleasable) factory).releaseExternalResources();
		// }
		// channels.close().awaitUninterruptibly();
		// bootstrap.releaseExternalResources();
		started = false;
	}

	public synchronized List<InetSocketAddress> getListenAddresses() {
		return addresses;
	}

	public int getBacklog() {
		return backlog;
	}

	public int getTimeout() {
		return timeout;
	}

	/**
	 * Create a new {@link Executor} used for dispatch messages to the workers. One
	 * Thread will be used per port which is bound. This can get overridden if
	 * needed, by default it use a {@link Executors#newCachedThreadPool()}
	 * 
	 * @return bossExecutor
	 */
	protected Executor createBossExecutor() {
		return KosuGorevlisi.tek().executorCached(serviceName + "-boss");
	}

	/**
	 * Create a new {@link Executor} used for workers. This can get overridden if
	 * needed, by default it use a {@link Executors#newCachedThreadPool()}
	 * 
	 * @return workerExecutor
	 */
	protected Executor createWorkerExecutor() {
		return KosuGorevlisi.tek().executorCached(serviceName + "-worker");
	}

	public boolean isBound() {
		return started;
	}

}
