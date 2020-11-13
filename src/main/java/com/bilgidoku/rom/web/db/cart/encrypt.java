package com.bilgidoku.rom.web.db.cart;

import java.util.Iterator;

import com.bilgidoku.rom.gwt.shared.HostStat;
import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.kurum.KurumGorevlisi;
import com.bilgidoku.rom.kurum.Organization;
import com.bilgidoku.rom.pg.dict.AfterHook;
import com.bilgidoku.rom.pg.dict.HookScope;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.shared.util.RomCurrency;

/**
 * https://developer.paypal.com/docs/classic/paypal-payments-standard/integration-guide/encryptedwebpayments/
 * Paypal waits... 
cert_id=Z24MFU6DSHBXQ
cmd=_xclick
business=sales@company.com
item_name=Handheld Computer
item_number=1234
custom=sc-id-789
amount=500.00
currency_code=USD
tax=41.25
shipping=20.00
address_override=1
address1=123 Main St
city=Austin
state=TX
zip=94085
country=US
no_note=1
cancel_return=http://www.company.com/cancel.htm




<form action="https://www.paypal.com/cgi-bin/webscr" method="post">
<input type="hidden" name="cmd" value="_cart">
<input type="hidden" name="upload" value="1">
<input type="hidden" name="business" value="seller@dezignerfotos.com">
<input type="hidden" name="item_name_1" value="Item Name 1">
<input type="hidden" name="amount_1" value="1.00">
<input type="hidden" name="shipping_1" value="1.75">
<input type="hidden" name="item_name_2" value="Item Name 2">
<input type="hidden" name="amount_2" value="2.00">
<input type="hidden" name="shipping_2" value="2.50">
<input type="submit" value="PayPal">
</form>

 * 
 * 
 * 
 * @author avci
 *
 */
public class encrypt extends AfterHook {
	private static final MC mc = new MC(encrypt.class);



	private static Astate called = mc.c("called");

	/**
	 * 
	 */
	@Override
	public void hook(HookScope scope, Object resp) throws KnownError, NotInlineMethodException, ParameterError {

		called.more();

		if (resp == null) {
			called.failed();
			return;
		}
		try {

			JSONObject jo = (JSONObject) resp;

			String payStyle = jo.optString("paystyle");
			if (payStyle == null || !payStyle.equals("paypal")) {
				return;
			}

			Integer hostId = jo.getInt("host_id");
			String uri = jo.getString("uri");
			JSONArray totalPrice = jo.optJSONArray("totalprice");
			JSONArray discountPrice = jo.optJSONArray("discountprice");
			JSONArray itemsPrice = jo.optJSONArray("itemsprice");
			JSONArray shipPrice = jo.optJSONArray("shipprice");
			JSONArray vatPrice = jo.optJSONArray("vatprice");

			JSONObject calcDetails = jo.optJSONObject("calcdetails");
			JSONObject shipAddr = jo.optJSONObject("shipaddr");

			if (shipAddr == null || totalPrice == null || itemsPrice == null || calcDetails == null
					|| calcDetails.length() == 0)
				return;

			Organization organi = KurumGorevlisi.tek().getOrganization(hostId);
			HostStat host=KurumGorevlisi.tek().host(hostId);
			if(host.domainalias==null || host.domainalias.length==0){
				throw new KnownError("Organization should have domain name");
			}

			JSONObject paypalStyle = null;
			if (organi.payStyle != null) {
				paypalStyle = organi.payStyle.optJSONObject("paypal");
			}

			if (paypalStyle == null)
				throw new KnownError("Organization should fill pay style in order to make paypal use");

			String paypalId = paypalStyle.optString("paypalid");
			String certId = paypalStyle.optString("certid");

			StringBuilder sb = new StringBuilder();
			add(sb, "certid", certId);
			add(sb, "cmd", "_cart");
			add(sb, "upload", "1");
			add(sb, "business", paypalId);

			Iterator<String> ite = calcDetails.keys();
			String currencyCode=null;
			int ind = 1;
			while (ite.hasNext()) {
				String key = ite.next();
				JSONObject value = calcDetails.getJSONObject(key);
				String wTitle = value.getString("wtitle");
				if (wTitle.length() == 0)
					return;
				String amount = value.getString("amount");
				JSONObject tariff = value.getJSONObject("tariff");
				JSONArray price = tariff.getJSONArray("price");

				String cc=price.getString(1);
				if(currencyCode==null){
					currencyCode=cc;
				}else{
					if(!currencyCode.equals(cc)){
						called.more();
						return;
					}
				}
				add(sb, "item_name_" + ind, wTitle);
				add(sb, "amount_" + ind, RomCurrency.toAmount(price.getLong(0), cc));
				add(sb, "quantity_" + ind, amount);
			}
			
			add(sb,"currency_code",currencyCode);
			
			String page=sb.toString();
			
			//byte[] signed = secureService.sign("home."+host.domainalias[0], page.getBytes());
			//byte[] enveloped=secureService.cmsEnvelope("paypal", signed);
			//String inPerm=new String(secureService.DERtoPEM(enveloped, "PKCS7"));
			
			//JSONObject nesting=jo.optJSONObject("nesting");
			//if(nesting==null){
			//	nesting=new JSONObject();
			//	nesting.put("encryptedcart", inPerm);
			//	jo.put("nesting", nesting);
			//}else{
			//	nesting.put("encryptedcart", inPerm);
			//}
			
			
		} catch (JSONException e) {
			called.failed();
		}

	}

	private void add(StringBuilder sb, String key, String value) {
		sb.append(key).append("=").append(value).append("\n");
	}

	@Override
	public void undo(HookScope scope) throws KnownError {

	}

}
