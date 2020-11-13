package com.bilgidoku.rom.secure;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.net.ssl.ExtendedSSLSession;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.StandardConstants;
import javax.net.ssl.X509ExtendedKeyManager;

import com.bilgidoku.rom.ilk.util.Genel;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;


public final class SniKeyManager extends X509ExtendedKeyManager {
	private static final MC mc=new MC(SniKeyManager.class);
	private final X509ExtendedKeyManager keyManager;
	private final String defaultAlias = Genel.getHostName();

	public SniKeyManager(X509ExtendedKeyManager x509KeyManager) {
		this.keyManager = x509KeyManager;
	}

	@Override
	public String[] getClientAliases(String keyType, Principal[] issuers) {
		throw new UnsupportedOperationException(); // we don't use client mode
	}

	@Override
	public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
		throw new UnsupportedOperationException(); // as above
	}

	@Override
	public String chooseEngineClientAlias(String[] keyType, Principal[] issuers, SSLEngine engine) {
		throw new UnsupportedOperationException(); // as above
	}

	@Override
	public String[] getServerAliases(String keyType, Principal[] issuers) {
		return keyManager.getServerAliases(keyType, issuers);
	}

	@Override
	public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
		throw new UnsupportedOperationException(); // Netty does not use
													// SSLSocket
	}

	private static final Astate choose=mc.c("choose");
	private static final Astate cwww=mc.c("www");
	private static final Astate chome=mc.c("home");
	private static final Astate crock=mc.c("rock");
	private static final Astate nohostname=mc.c("noHostName");
	private static final Astate defaulhostname=mc.c("defHostName");
	
	@Override
	public String chooseEngineServerAlias(String keyType, Principal[] issuers, SSLEngine engine) {
		choose.more();
		ExtendedSSLSession session = (ExtendedSSLSession) engine.getHandshakeSession();

		// Pick first SNIHostName in the list of SNI names.
		String hostname = null;
		for (SNIServerName name : session.getRequestedServerNames()) {
			if (name.getType() == StandardConstants.SNI_HOST_NAME) {
				hostname = ((SNIHostName) name).getAsciiName();
				break;
			}
		}

		if(hostname==null){
			nohostname.more();
			return defaultAlias;
		}
		
		if(hostname.startsWith("www.")){
			hostname=hostname.substring(4);
			cwww.more();
		}
		if(hostname.startsWith("home.")){
			hostname=hostname.substring(5);
			chome.more();
		}

		if(hostname.endsWith("rock-server.net")){
			hostname="rock-server.net";
			crock.more();
		}
		
		X509Certificate[] cc = getCertificateChain(hostname);
		PrivateKey pk = getPrivateKey(hostname);
		
		// If we got given a hostname over SNI, check if we have a cert and key
		// for that hostname. If so, we use it.
		// Otherwise, we fall back to the default certificate.
		if (hostname != null && cc != null &&  pk != null)
			return hostname;
		else{
			defaulhostname.more();
			return defaultAlias;
		}
	}

	@Override
	public X509Certificate[] getCertificateChain(String alias) {
		return keyManager.getCertificateChain(alias);
	}

	@Override
	public PrivateKey getPrivateKey(String alias) {
		if(alias.startsWith("www.")){
			alias=alias.substring(4);
		}
		return keyManager.getPrivateKey(alias);
	}
}
