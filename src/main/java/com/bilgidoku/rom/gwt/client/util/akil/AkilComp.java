package com.bilgidoku.rom.gwt.client.util.akil;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.util.com.Comp;
import com.bilgidoku.rom.gwt.client.util.com.CompBase;
import com.bilgidoku.rom.gwt.client.util.com.CompFactory;
import com.bilgidoku.rom.gwt.client.util.com.CompInfo;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONObject;

public class AkilComp extends CompBase implements AkilCb {

	public final static CompInfo info = new CompInfo("+akil", 50, new String[] { "*akil.soyledi" },
			new String[] { "_wndtop", "+topwindow", "user", "+actionbar" }, new String[] { "userneed", "local" });

	public static final CompFactory factory = new CompFactory() {

		@Override
		public CompInfo info() {
			return info;
		}

		@Override
		public Comp create() {
			return new AkilComp();
		}
	};

	@Override
	public CompInfo compInfo() {
		return info;
	}

	private AkilDlg dlg;
	private List<Soru> bekleyenSorular=new ArrayList<>();
	
	
	public AkilComp() {
//		this.dlg = new AkilDlg(this);
	}

	@Override
	public boolean handle(String cmd, JSONObject cjo) throws RunException {
		if (!cmd.equals("*akil.soyledi")) {
			return false;
		}
		String eylem = cjo.getString("eylem");
		String yazi = cjo.optString("yazi");
		String soru = cjo.optString("soru");
		String tip = cjo.optString("yazi");
		JSONArray olasi = cjo.optArray("olasi");
		Sistem.outln("geldi");
		if (eylem.equals("menu")) {
			dlg.updateMenu(olasi);
			return true;
		} else if (eylem.equals("soru")) {
			soruGeldi(new Soru(soru, yazi, tip, olasi));
		}
		dlg.soylendi(eylem);
		return true;
	}

	private void soruGeldi(Soru soru) {
		if(!dlg.soruVarmi(soru))
			return;
		bekleyenSorular.add(soru);
	}

	@Override
	public void yanitlandi() {
		if(bekleyenSorular.size()==0)
			return;
		Soru soru=bekleyenSorular.remove(0);
		soruGeldi(soru);
	}

}
