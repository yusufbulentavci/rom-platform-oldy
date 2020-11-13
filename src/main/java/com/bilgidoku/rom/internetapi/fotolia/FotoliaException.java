package com.bilgidoku.rom.internetapi.fotolia;

public class FotoliaException extends Exception{

	public FotoliaException(int hostId, String fotoliaId) {
		super("HostId:"+hostId+" fotoliaid:"+fotoliaId);
	}
}
