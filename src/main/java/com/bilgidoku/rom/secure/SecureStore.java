package com.bilgidoku.rom.secure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedKeyManager;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.shredzone.acme4j.util.KeyPairUtils;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.util.Assert;
import com.bilgidoku.rom.min.Sistem;




/*
 * Saklanmamis key olamaz 
 * Sertifika yoksa almamisizdir 
 * Self signed sertifika kullanilmayacak. ACME beklenebilmeli.
 *
 */ 
public class SecureStore{

    private final File ksfile;
    private static final char[] storePass = "Ihooz1chphod7Jeo".toCharArray();
    private KeyStore keyStore; 

    public SecureStore(String path) throws KnownError{
	ksfile = new File(path, "store.pkcs12");	
	load();	
    }

    private void load() throws KnownError{
	try {
	    keyStore = KeyStore.getInstance("PKCS12");
	    if (ksfile.exists()) {
		FileInputStream ksStream = new FileInputStream(ksfile);
		keyStore.load(ksStream, storePass);
		ksStream.close();
		Sistem.outln("KeyStore loaded");
	    } else {
		Sistem.outln("KeyStore NOT FOUND" + ksfile.toString());
		keyStore.load(null, storePass);
	    }
	} catch (Exception e) {
	    throw new KnownError(e);
	}
    }

    public void save() throws KnownError {
	try (FileOutputStream fos = new FileOutputStream(this.ksfile);) {
	    keyStore.store(fos, storePass);
	} catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
	    throw new KnownError(e);
	}
    }

    public KeyPair loadRootKeyPair() throws KnownError{
	try (FileReader fr = new FileReader("/home/rompg/rom/data/code/acme/keypair.pem")) {
	      return KeyPairUtils.readKeyPair(fr);
	}catch(Exception e){
	    throw new KnownError(e);
	}

    }

    public Set<String> aliases() throws KnownError {
	Set<String> ret = new HashSet<String>();
	Enumeration<String> als;
	try {
	    als = keyStore.aliases();
	    while (als.hasMoreElements()) {
		ret.add(als.nextElement());
	    }
	    return ret;
	} catch (KeyStoreException e) {
	    throw new KnownError(e);
	}
    }


    public Set<String> domains() throws KnownError {
	Set<String> ret = new HashSet<String>();
	Enumeration<String> als;
	try {
	    als = keyStore.aliases();
	    while (als.hasMoreElements()) {
		String a = als.nextElement();
		a = a.replaceFirst("^home\\.", "");
		a = a.replaceFirst("^www\\.", "");
		ret.add(a);
	    }
	    return ret;
	} catch (KeyStoreException e) {
	    throw new KnownError(e);
	}

    }

    public X509Certificate getCert(String alias) throws KnownError {
	X509ExtendedKeyManager km = getKeyManager();
	X509Certificate[] signerCert = km.getCertificateChain(alias);
	Assert.notNull(signerCert);
	Assert.beTrue(signerCert.length > 0);
	X509Certificate cert = signerCert[0];
	return cert;	
    }

    public void deleteCert(String alias){
	try {
	    keyStore.deleteEntry(alias);
	} catch (KeyStoreException e) {
	}
    }


    public static X500Name X500FromAlias(String alias){
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("cn=");
	stringBuilder.append(alias);
	stringBuilder.append(",ou=");
	stringBuilder.append(alias);
	stringBuilder.append(",o=");
	stringBuilder.append(alias);
	String dname = stringBuilder.toString();

	return new X500Name(dname);		
    }


    public void createKey(String domain) throws KnownError{
	try{
	KeyPair keyPair = KeyPairUtils.createKeyPair(2048);	
	for(String alias:new String[]{domain,"www."+domain,"home."+domain}){
	    keyStore.setKeyEntry(alias, (Key)keyPair.getPrivate(), storePass, new Certificate[]{tempCert(alias, keyPair)});
	}
	save();
	}catch(Exception e){
	    throw new KnownError(e);
	}
    }

    public void updateCert(String alias, Certificate cert) throws KnownError{
	try{
	Key key=keyStore.getKey(alias, storePass);
	keyStore.setKeyEntry(alias, key, storePass, new Certificate[]{cert});
	save();
	}catch(Exception e){
	    throw new KnownError(e);
	}

    }

    public X509Certificate tempCert(String alias, KeyPair keyPair) throws KnownError{  
	try{
	X509V3CertificateGenerator cert = new X509V3CertificateGenerator();   
	cert.setSerialNumber(BigInteger.valueOf(-1));   //or generate a random number  
	cert.setSubjectDN(new X509Principal("CN="+alias));  //see examples to add O,OU etc  
	cert.setIssuerDN(new X509Principal("CN="+alias)); //same since it is self-signed  
	cert.setPublicKey(keyPair.getPublic());  
	cert.setSignatureAlgorithm("SHA1WithRSAEncryption");   
	PrivateKey signingKey = keyPair.getPrivate();    
	return cert.generate(signingKey, "BC");  
	}catch(Exception e){
	    throw new KnownError(e);
	}

    }

	public SSLContext getSslContext() throws KnownError {

		try {
			X509ExtendedKeyManager x509KeyManager = getKeyManager();

			if (x509KeyManager == null)
				throw new KnownError("KeyManagerFactory did not create an X509ExtendedKeyManager");

			TrustManagerFactory tmf = trustManager();

			SniKeyManager sniKeyManager = new SniKeyManager(x509KeyManager);

			SSLContext context = SSLContext.getInstance("TLSv1");
			context.init(new KeyManager[] { sniKeyManager }, tmf.getTrustManagers(), null);
			return context;
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			throw new KnownError(e);
		}

	}

   private KeyManagerFactory keyManagerFactory() throws KnownError {
	KeyManagerFactory factory;
	try {
	    factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
	    factory.init(keyStore, storePass);
	    return factory;
	} catch (NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException e) {
	    throw new KnownError(e);
	}
    }


    private TrustManagerFactory trustManager() throws KnownError {
	try {
	    TrustManagerFactory tmf;
	    tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	    tmf.init(keyStore);
	    return tmf;
	} catch (NoSuchAlgorithmException | KeyStoreException e) {
	    throw new KnownError(e);
	}
    }

    private X509ExtendedKeyManager getKeyManager() throws KnownError {
	X509ExtendedKeyManager x509KeyManager=null; 
	KeyManagerFactory factory = keyManagerFactory();
	for (KeyManager keyManager : factory.getKeyManagers()) {
	    if (keyManager instanceof X509ExtendedKeyManager) {
		x509KeyManager = (X509ExtendedKeyManager) keyManager;
	    }
	}
	return x509KeyManager;

    }


}
