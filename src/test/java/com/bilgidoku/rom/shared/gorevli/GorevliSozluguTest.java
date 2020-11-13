package com.bilgidoku.rom.shared.gorevli;

import com.bilgidoku.rom.pg.dict.HttpCallServiceDeclare;

import junit.framework.TestCase;

public class GorevliSozluguTest extends TestCase {

	public void testAnotasyon() {
		GorevliSozlugu.tek().anotasyonlariGetir(HttpCallServiceDeclare.class);
	}

}
