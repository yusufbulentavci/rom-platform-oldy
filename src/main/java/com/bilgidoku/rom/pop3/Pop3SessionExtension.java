package com.bilgidoku.rom.pop3;

import com.bilgidoku.rom.pg.dict.RomUser;
import com.bilgidoku.rom.protokol.protocols.ProtocolConfiguration;
import com.bilgidoku.rom.protokol.protocols.ProtocolSession;
import com.bilgidoku.rom.protokol.protocols.ProtocolSessionExtension;
import com.bilgidoku.rom.protokol.protocols.ProtocolTransport;

public class Pop3SessionExtension extends ProtocolSessionExtension<POP3SessionActivity>{

	public Pop3SessionExtension() {
		super("pop3");
	}

	@Override
	protected ProtocolSession<POP3SessionActivity> construct(ProtocolTransport transport, ProtocolConfiguration config) {
		return new POP3SessionImpl(transport, config);
	}

	@Override
	public void check() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean authFailed(int moduleId, String ipAddress, String string, Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected RomUser getRomUserByEmail(ProtocolSession<POP3SessionActivity> ses, String username) {
		// TODO Auto-generated method stub
		return null;
	}



}
