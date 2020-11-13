package com.bilgidoku.rom.shared.cmds.client;

import com.bilgidoku.rom.shared.Att;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.Tag;
import com.bilgidoku.rom.shared.TagImpl;
import com.bilgidoku.rom.shared.code.Command;
import com.bilgidoku.rom.shared.code.RenderCallState;
import com.bilgidoku.rom.shared.xml.Elem;

public class CommentCommand extends ClientCommandBase {

	public CommentCommand() {
		super("c:comment");
	}

	@Override
	public Tag getDef() {
		return new TagImpl("c:comment", false, "cmd", new Att[] {}, emptylist, null, "");
	}


}
