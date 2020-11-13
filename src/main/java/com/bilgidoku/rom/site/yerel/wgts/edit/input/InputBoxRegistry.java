package com.bilgidoku.rom.site.yerel.wgts.edit.input;

import com.bilgidoku.rom.shared.Att;

public abstract class InputBoxRegistry {

	abstract public boolean iCanInput(Att att);

	abstract public InputBox create(Att att, String value);

}
