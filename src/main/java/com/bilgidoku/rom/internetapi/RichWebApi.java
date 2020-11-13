package com.bilgidoku.rom.internetapi;

import com.bilgidoku.rom.gwt.shared.ImageResp;
import com.bilgidoku.rom.internetapi.api.SocialOne;
import com.bilgidoku.rom.shared.err.KnownError;

public interface RichWebApi {
	public ImageResp[] searchImage(
			Integer p_limit,
			Integer p_offset, 
			String p_phrase, 
			String p_size,
			String p_aspect,
			String p_style,
			String p_colors,
			String p_face
			) throws KnownError;
	
	public ImageResp infoMedia(
			String p_id
			) throws KnownError;
	public void buyMedia(
			String p_id
			) throws KnownError;

	public SocialOne socialOneAccessToken(String accessToken);
}
