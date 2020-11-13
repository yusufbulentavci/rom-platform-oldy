package com.bilgidoku.rom.web.charge;

import java.math.BigDecimal;
import java.util.List;

import com.bilgidoku.rom.haber.NodeTalkMethod;
import com.bilgidoku.rom.haber.TalkResult;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.Assert;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.veritabani.VeritabaniGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.charge.dbop.CancelTransactionDbOp;
import com.bilgidoku.rom.web.charge.dbop.GetChargingDataVersionDbOp;
import com.bilgidoku.rom.web.charge.dbop.SaleDbOp;


/**
 * FEATURE NAMES
 * provider.img.buy
 * 
 * 
 * @author avci
 *
 */
public class UcretlendirmeGorevlisi extends GorevliDir{
	public static final int NO=32;
		
		public static UcretlendirmeGorevlisi tek(){
			if(tek==null) {
				synchronized (UcretlendirmeGorevlisi.class) {
					if(tek==null) {
						tek=new UcretlendirmeGorevlisi();
						tek.giris();
					}
				}
			}
			return tek;
		}
		
		static UcretlendirmeGorevlisi tek;
		private UcretlendirmeGorevlisi() {
			super("Ucretlendirme", NO);
		}
	
	private static final MC mc = new MC(UcretlendirmeGorevlisi.class);
	
	private static final String DEFAULTNAME = "-";

	

	public void selfDescribe(JSONObject jo) {
	}
	
	private long moneyToCredit(String money) {
		BigDecimal bd = new BigDecimal(money);
		bd = bd.multiply(new BigDecimal(1000000));
		return bd.longValue();
	}

	/**
	 * tepeweb.chargeactions_new(a_host integer, p_ttype text, p_creditchange
	 * bigint, a_contact text, p_description text, p_moneyamount text
	 */
	private static final Astate _newChargeAction = mc.c("newChargeAction");

	private String newChargeAction(int hostId, String ttype, String a_contact, String money, String remoteTid,
			long credit, String desc, String ftid) throws KnownError {
		destur();
		_newChargeAction.more();
		try (DbThree db3 = new DbThree("select * from " + "tepeweb.chargeactions_new(?,?,?,?,?,?,?)")) {
			db3.setInt(hostId);
			db3.setString(ttype);
			db3.setLong(credit);
			db3.setString(a_contact);
			db3.setString(desc);
			db3.setString(money);
			db3.setString(remoteTid);
			db3.executeQuery();
			Assert.beTrue(db3.next());
			return db3.getString();
		} catch (KnownError e) {
			_newChargeAction.failed(e, hostId, ttype, a_contact, money, remoteTid, credit, desc, ftid);
			throw e;
		}
	}

//
//	public String charge(int hostId, long credits, String contact, String reason, String ftid) throws KnownError {
//		return newChargeAction(hostId, "payment", contact, null, null, credits, reason, ftid);
//
//	}


	public String giveBack(int hostId, long credits, String contact, String reason, String ftid) throws KnownError {
		return newChargeAction(hostId, "refund", contact, null, null, credits, reason, ftid);
	}

	private static final Astate _cancelCharge = mc.c("cancelCharge");


	private static final Astate _payment = mc.c("payment.f");


	@NodeTalkMethod(cmd = "charge.payment")
	public TalkResult payment(JSONObject jo) {
		_payment.more();
		destur();
		try {
			JSONObject d = jo.getJSONObject("d");
			String money = d.getString("mc_gross");
			int hostId = d.getInt("hostid");
			String remoteTid = d.getString("remotetid");
			loadCredits(hostId, null, money, remoteTid);
		} catch (JSONException | KnownError e) {
			_payment.failed(e, jo);
			return TalkResult.failed;
		}
		return TalkResult.success;
	}


	@NodeTalkMethod(cmd = "charge.refund")
	public JSONObject refund(JSONObject jo) {
		// TODO Auto-generated method stub
		return null;
	}


	public String loadCredits(int hostId, String a_contact, String money, String remoteTid) throws KnownError {
		destur();
		long credit = moneyToCredit(money);
		return newChargeAction(hostId, "buycredit", a_contact, money, remoteTid, credit, null, remoteTid);
	}

	public String unloadCredits(int hostId, String a_contact, String money, String remoteTid) throws KnownError {
		destur();
		long credit = moneyToCredit(money);
		return newChargeAction(hostId, "refundmoney", a_contact, money, remoteTid, -credit, null, remoteTid);
	}


	public int sale(Integer a_host, String feature, String named, String a_contact, String objid, Long fromDate,
			Long toDate, Integer amount, String remotid, String desc) throws KnownError {
		return new SaleDbOp().doit(a_host, a_contact, feature, featureNamed(named), objid, null, fromDate, toDate, amount, desc, remotid);
	}


	public void cancelTransaction(Integer a_host, Integer tid, String feature, String named, String refid, String why) throws KnownError {
		new CancelTransactionDbOp().doit(a_host, feature, featureNamed(named), tid, refid, why);
	}

	private String featureNamed(String named) {
		if(named==null)
			return DEFAULTNAME;
		return named;
	}


	public Integer getDataVersion() throws KnownError {
		return new GetChargingDataVersionDbOp().doit();
	}

	private static final Astate upgrade = mc.c("upgrade");

	public void upgradeVersion(Integer version, List<String> sql) throws KnownError {
		upgrade.more();
		try{
			sql.add("update dict.envo set chargingdataversion="+version+";");
			VeritabaniGorevlisi.tek().executeCommands(sql);
		}catch(KnownError e){
			upgrade.failed();
			throw e;
		}
		
	}

}
