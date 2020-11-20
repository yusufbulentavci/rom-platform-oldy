

import com.bilgidoku.rom.haber.HaberGorevlisi;
import com.bilgidoku.rom.pg.PgGorevlisi;
import com.bilgidoku.rom.pg.sqlunit.SqlUnitGorevlisi;
import com.bilgidoku.rom.session.OturumGorevlisi;
import com.bilgidoku.rom.shared.gorevli.GorevliYonetimi;
import com.bilgidoku.rom.web.grow.GenislemeGorevlisi;
import com.bilgidoku.rom.web.http.HttpGorevlisi;

public class Sunucu extends Ayakta {

	public static void main(String[] args) {
		Sunucu s = new Sunucu();
		s.kos();
	}

	@Override
	public void basla() {
		try {
			PgGorevlisi.tek();
			SqlUnitGorevlisi.tek().load("tepeweb.ready");
			SqlUnitGorevlisi.tek().runOnDb();
			OturumGorevlisi.tek();
			HttpGorevlisi.tek();
			HaberGorevlisi.tek();
			GenislemeGorevlisi.tek();
		} catch (Exception e) {
			e.printStackTrace();
			GorevliYonetimi.tek().terminate("Baslarken", e);
		}
	}

	@Override
	void bekle() {
		// TODO Auto-generated method stub

	}

}
