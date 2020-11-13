package com.bilgidoku.rom.web.uri;

import com.bilgidoku.rom.gwt.araci.server.UriWork;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.type.UriWorkBase;

import io.netty.handler.codec.http.HttpMethod;

public class UriGorevlisi extends GorevliDir{
	public static final int NO=30;
	
	public static UriGorevlisi tek(){
		if(tek==null) {
			synchronized (UriGorevlisi.class) {
				if(tek==null) {
					tek=new UriGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static UriGorevlisi tek;
	private UriGorevlisi() {
		super("Uri", NO);
	}
	
	private UriWorkBase uriWork=new UriWork();;
	

	public void selfDescribe(JSONObject jo) {
		
	}
	
	public Uriz analysis(int hostId, String path, HttpMethod method, String emailDomain) throws KnownError{
		Uriz uri=new Uriz(path,method, emailDomain);
		
		if(uri.hasResource()){
			RomContainer c=getContainer(hostId, uri.resource);
			uri.isContainer(c!=null);
		}
		
		return uri;
	}
	
	private RomContainer getContainer(int p_hostid, String uri) throws KnownError {
		destur();
		try (DbThree db3 = new DbThree("select delegated,ownercid"
				+ " from rom.containers"
				+ " where host_id=? and uri=?")) {
			db3.setInt(p_hostid);
			db3.setString(uri);
			db3.executeQuery();
			if(!db3.next())
				return null;
			
			RomContainer rc=new RomContainer(p_hostid, uri);
			
			return rc;
		}
	}


	public boolean isService(String pre) {
		return uriWork.isService(pre);
	}

}
