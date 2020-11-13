package com.bilgidoku.rom.smtp;

import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.mail.EpostaGorevlisi;
import com.bilgidoku.rom.pg.dict.RomUser;
import com.bilgidoku.rom.protokol.protocols.ProtocolConfiguration;
import com.bilgidoku.rom.protokol.protocols.ProtocolSession;
import com.bilgidoku.rom.protokol.protocols.ProtocolSessionExtension;
import com.bilgidoku.rom.protokol.protocols.ProtocolTransport;

public class SmtpSessionExtension extends ProtocolSessionExtension<SMTPSessionActivity>{
	

	
	
	private SmtpSessionExtension() {
		super("smtp");
	}
	
	static SmtpSessionExtension one;
	public static SmtpSessionExtension one() {
		if(one==null)
			one=new SmtpSessionExtension();
		return one;
	}
	
	

	@Override
	protected ProtocolSession<SMTPSessionActivity> construct(ProtocolTransport transport, ProtocolConfiguration config) {
		return new SMTPSessionImpl(transport, config);
	}

	
	@Override
	public void waitingChanged(int hostId, String cid, String app, String code, JSONArray inref, int times, JSONArray title, JSONArray user) {
		EpostaGorevlisi.tek().sendNotificationMail(hostId, cid, app, code, inref, times, title, user);
		
	}



	@Override
	protected boolean authFailed(int moduleId, String ipAddress, String string, Object object) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	protected RomUser getRomUserByEmail(ProtocolSession<SMTPSessionActivity> ses, String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
