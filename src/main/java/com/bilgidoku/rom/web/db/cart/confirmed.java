package com.bilgidoku.rom.web.db.cart;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.dict.AfterHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;

/**
 * https://developer.paypal.com/docs/classic/paypal-payments-standard/integration-guide/encryptedwebpayments/
 * Paypal waits... cert_id=Z24MFU6DSHBXQ cmd=_xclick business=sales@company.com
 * item_name=Handheld Computer item_number=1234 custom=sc-id-789 amount=500.00
 * currency_code=USD tax=41.25 shipping=20.00 address_override=1 address1=123
 * Main St city=Austin state=TX zip=94085 country=US no_note=1
 * cancel_return=http://www.company.com/cancel.htm
 * 
 * 
 * 
 * 
 * <form action="https://www.paypal.com/cgi-bin/webscr" method="post">
 * <input type="hidden" name="cmd" value="_cart">
 * <input type="hidden" name="upload" value="1">
 * <input type="hidden" name="business" value="seller@dezignerfotos.com">
 * <input type="hidden" name="item_name_1" value="Item Name 1">
 * <input type="hidden" name="amount_1" value="1.00">
 * <input type="hidden" name="shipping_1" value="1.75">
 * <input type="hidden" name="item_name_2" value="Item Name 2">
 * <input type="hidden" name="amount_2" value="2.00">
 * <input type="hidden" name="shipping_2" value="2.50">
 * <input type="submit" value="PayPal"> </form>
 * 
 * 
 * 
 * 
 * @author avci
 *
 */
public class confirmed extends AfterHook {
	private static final MC mc = new MC(confirmed.class);


	private static Astate called = mc.c("called");

	/**
	 * 
	 */
	@Override
	public void hook(HookScope scope, Object resp) throws KnownError, NotInlineMethodException, ParameterError {
//
//		called.more();
//
//		if (resp == null) {
//			called.failed();
//			return;
//		}
//		try {
//			JSONObject jo = (JSONObject) resp;
//
//			Integer hostId = jo.getInt("host_id");
//			String uri = jo.getString("uri");
//			String ownercid = jo.getString("ownercid");
//			Info info=DbDao.getInfo(hostId, null, null);
//			
//			
//			Assert.notNull(info.address);
//			JSONObject ja = info.address.getObject();
//			
//			
//			
//
//			String orgEmail=ja.getString("email");
//			Assert.notNull(orgEmail);
//
//			String contactEmail;
//			try (DbThree d3 = new DbThree("select email from rom.contacts where host_id=?,ownercid=?")) {
//				d3.setInt(hostId);
//				d3.setString(ownercid);
//				d3.checkedNext();
//				contactEmail = d3.getString();
//			}
//
//			
//
//			mailService.sendViaTemplate("en", orgEmail, "CartConfirmed", orgEmail,
//					Arrays.asList(new MailAddress(contactEmail)), "0", hostId, null, "orgemail", orgEmail,
//					"contactemail", contactEmail);
//
//			JSONArray totalPrice = jo.optJSONArray("totalprice");
//			JSONArray discountPrice = jo.optJSONArray("discountprice");
//			JSONArray itemsPrice = jo.optJSONArray("itemsprice");
//			JSONArray shipPrice = jo.optJSONArray("shipprice");
//			JSONArray vatPrice = jo.optJSONArray("vatprice");
//
//			JSONObject calcDetails = jo.optJSONObject("calcdetails");
//			JSONObject shipAddr = jo.optJSONObject("shipaddr");
//
//			if (shipAddr == null || totalPrice == null || itemsPrice == null || calcDetails == null
//					|| calcDetails.length() == 0)
//				return;

//			Organization organi = KurumGorevlisi.tek().getOrganization(hostId);
//			HostStat host = KurumGorevlisi.tek().host(hostId);
//			if (host.domainalias == null || host.domainalias.length == 0) {
//				throw new KnownError("Organization should have domain name");
//			}
//
//			JSONObject paypalStyle = null;
//			if (organi.payStyle != null) {
//				paypalStyle = organi.payStyle.optJSONObject("paypal");
//			}
//
//			if (paypalStyle == null)
//				throw new KnownError("Organization should fill pay style in order to make paypal use");
//
//			String paypalId = paypalStyle.optString("paypalid");
//			String certId = paypalStyle.optString("certid");
//
//			StringBuilder sb = new StringBuilder();
//			add(sb, "certid", certId);
//			add(sb, "cmd", "_cart");
//			add(sb, "upload", "1");
//			add(sb, "business", paypalId);
//
//		} catch (JSONException e) {
//			called.failed();
//		}

	}

	private void add(StringBuilder sb, String key, String value) {
		sb.append(key).append("=").append(value).append("\n");
	}

	@Override
	public void undo(HookScope scope) throws KnownError {

	}

}
