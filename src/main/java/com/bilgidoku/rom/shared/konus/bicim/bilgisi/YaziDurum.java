package com.bilgidoku.rom.shared.konus.bicim.bilgisi;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.konus.bicim.Cumle;
import com.bilgidoku.rom.shared.konus.bicim.Kelime;
import com.bilgidoku.rom.shared.konus.bicim.ek.ZamanEki;
import com.bilgidoku.rom.shared.konus.bicim.oge.Oge;
import com.bilgidoku.rom.shared.util.Kontrol;
import com.bilgidoku.rom.shared.util.StringUtil;

public class YaziDurum {
	private Cumle cumle;
	private Oge oge;
	private Kelime kelime;
	private boolean fiilmi;

	private List<Character> gelis=new ArrayList<>();

	public boolean eylemmi() throws KnownError {
		return kelime.eylemmi();
	}


	public char sonHarf() {
		if(gelis.size()==0)
			return 0;
		return gelis.get(gelis.size() - 1);
	}
	
	
	public char sonUnlu() throws KnownError {
		for(int i=gelis.size()-1; i>=0; i--) {
			char c=gelis.get(i);
			if(Unlu.unlumu(c)) {
				return c;
			}
		}
		return 0;
	}
	
	public char sondanBirOncekiUnlu() throws KnownError {
		boolean ding=false;
		for(int i=gelis.size()-1; i>=0; i--) {
			char c=gelis.get(i);
			if(Unlu.unlumu(c)) {
				if(!ding) {
					ding=true;
					continue;
				}
				return c;
			}
		}
		return 0;
	}

	
	public void sonHarfiDegistir(char yeniHarf) {
		gelis.set(gelis.size()-1, yeniHarf);
	}

	public void setCumle(Cumle cumle) {
		this.cumle=cumle;
	}

	public void setOge(Oge oge2) {
		this.oge=oge2;
	}

	public void setKelime(Kelime kelime2) {
		this.kelime=kelime2;
	}

	public void sifirla(List<String> heceli) {
		gelis.clear();
		for (String string : heceli) {
			yaziyaEkle(string);
		}
	}
	
	public void yaziyaEkle(String e) {
		for (Character character : e.toCharArray()) {
			gelis.add(character);
		}
	}
	public void yaziyaEkle(char e) {
		gelis.add(e);
	}

	public String yaz() {
		StringBuilder sb=new StringBuilder();
		for (Character character : gelis) {
			sb.append(character);
		}
		return sb.toString();
	}


	public boolean yaziTekHecemi() {
		boolean dut=false;
		for (Character c : gelis) {
			if(Unlu.unlumu(c)) {
				if(!dut) {
					dut=true;
					continue;
				}
				return false;
			}
		}
		return true;
	}

	public boolean zamani(char c) throws KnownError {
		ZamanEki ze = cumle.zamani();
		Kontrol.bosDegil(ze);
		return ze.karsilastir(c);
	}
	

	

}
