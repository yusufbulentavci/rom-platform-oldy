package com.bilgidoku.rom.shared.gorevli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.json.JSONObject;

public class GorevliYonetimi {
	static GorevliYonetimi tek;

	public static GorevliYonetimi tek() {
		if (tek == null) {
			tek = new GorevliYonetimi();
			Uygulama.tek().gorevliYaratildi(tek);
		}
		return tek;
	}

	private JSONObject ortam;
	private Map<String, Gorevli> tumu = new HashMap<String, Gorevli>();
	private List<Gorevli> sirali=new ArrayList<>();

	private List<GorevliIzle> izleyenler = new ArrayList<>();

	Gorevli yeni(Gorevli o) {

		tumu.put(o.getKod(), o);
		if (o instanceof GorevliIzle) {
			GorevliIzle go = (GorevliIzle) o;
			izleyenler.add(go);
			for (Gorevli g : tumu.values()) {
				go.yeniGorevli(g);
			}
		}

		for (GorevliIzle gorevliIzle : izleyenler) {
			gorevliIzle.yeniGorevli(o);
		}
		return o;
	}

	public JSONObject ortam() {
		return ortam;
	}

	
	public synchronized void terminate(String string, Object object) {
		
		for(int i=sirali.size()-1; i>=0; i--) {
			Gorevli s = sirali.get(i);
			try {
			s.bitir(true);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.exit(0);
	}

	public void gorevliTesti(Gorevli gorevli) {
		try {
			Sistem.outln("Gorevli testi basliyor:" + gorevli.getKod());
			Thread.sleep(1000);

		} catch (InterruptedException e) {
		}
	}

}
