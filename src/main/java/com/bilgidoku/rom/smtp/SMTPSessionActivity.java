package com.bilgidoku.rom.smtp;

import com.bilgidoku.rom.protokol.protocols.ProtocolSessionActivity;

public class SMTPSessionActivity extends ProtocolSessionActivity{

	@Override
	public boolean isOnline() {
		return false;
	}

}
