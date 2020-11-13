package com.bilgidoku.rom.pop3;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.protokol.protocols.ProtocolSessionActivity;

public class POP3SessionActivity extends ProtocolSessionActivity{

	@Override
	public boolean isOnline() {
		// Is the users checking mailbox in every 30 minutes
		return Math.abs(Sistem.millis()-lastActivity)<1000*60*30;
	}

}
