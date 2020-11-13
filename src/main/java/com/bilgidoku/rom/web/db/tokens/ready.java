package com.bilgidoku.rom.web.db.tokens;

import com.bilgidoku.rom.haber.HaberGorevlisi;
import com.bilgidoku.rom.haber.TalkUtil;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.dict.BeforeHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.web.tokens.Token;
import com.bilgidoku.rom.web.tokens.TokenGet;

public class ready extends BeforeHook {
	private static final MC mc=new MC(ready.class);
	
	

	private static final Astate _hook=mc.c("hook.notoken.dictate");

	@Override
	public boolean hook(HookScope scope) throws KnownError, NotInlineMethodException, KnownError, ParameterError {
		_hook.more();
		int hostId = scope.getHostId();
		String uri = scope.getUri();

		Token token = new TokenGet().get(hostId, uri);
		if (token == null) {
			_hook.fail(1,null,scope);
			throw new KnownError("Token not found;").security();
		}

		try {
			JSONObject jo = new JSONObject();
			jo.put("s", token.dataServer);
			jo.put("c", token.dataClient);
			
			JSONObject msg = TalkUtil.m(hostId,token.world,token.cmd,jo);
			HaberGorevlisi.tek().send(msg, scope.getRequest().getRid());
		} catch (JSONException e) {
			_hook.fail(2,e,scope);
			throw err(e);
		}

		return true;
	}

	@Override
	public void undo(HookScope scope) throws KnownError, KnownError, ParameterError {
		// TODO Auto-generated method stub

	}

}
