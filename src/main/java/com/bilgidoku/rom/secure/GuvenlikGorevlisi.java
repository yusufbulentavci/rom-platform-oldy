package com.bilgidoku.rom.secure;

import java.io.File;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.bilgidoku.rom.gunluk.GunlukGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.gorevli.Dir;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.izle.MC;



/**
 * cd shared/kse-521/
 * 
 * 
 * 
 * @author postgres
 */
public class GuvenlikGorevlisi extends GorevliDir {
	
	public static final int NO=10;
	
	public static GuvenlikGorevlisi tek(){
		if(tek==null) {
			synchronized (GuvenlikGorevlisi.class) {
				if(tek==null) {
					tek=new GuvenlikGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static GuvenlikGorevlisi tek;
	private GuvenlikGorevlisi() {
		super("Guvenlik", NO);
		Security.addProvider(new BouncyCastleProvider());
	}
	
	private static final MC mc = new MC(GuvenlikGorevlisi.class);
	private static final String BC = org.bouncycastle.jce.provider.BouncyCastleProvider.PROVIDER_NAME;

	private static final String secret = "Ihooz1chphod7Jeo";

	private SSLContext context;
	private PrivateKey dkimPrivate;
	private PublicKey dkimPublic;

	private SecureStore store;



	@Override
	protected void kur() throws KnownError {

		File permDir = safeDataDir();
		Dir.existDir(permDir.getPath());
		store = new SecureStore(permDir.getPath());
		
		context = store.getSslContext();
	}

	@Override
	protected void bitir(boolean dostca) {
	}
	


	public SSLEngine getServerSslEngine() {
		SSLEngine engine = context.createSSLEngine();
		engine.setUseClientMode(false);
		return engine;
	}


	public PrivateKey getDkimPrivate() throws KnownError{
	    	KeyPair kp = store.loadRootKeyPair();
		return kp.getPrivate();
	}


	public PublicKey getDkimPublic() throws KnownError{
		KeyPair kp = store.loadRootKeyPair();
		return kp.getPublic();
	}


	public SSLContext getSslContext() throws KnownError {
		return context;
	}

}
