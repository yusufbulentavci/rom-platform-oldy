package com.bilgidoku.rom.gwt.client.util.cmd.client;

import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.xml.Elem;

public abstract class ClientCommandBase extends Command{

	public ClientCommandBase(String prefix) {
		super(prefix);
	}

	@Override
	protected void execute(Elem curElem, RenderCallState rz) throws RunException {
		throw new RunException("Client comment wanna be executed in server");
	}

	

}
