package com.bilgidoku.rom.web.charge;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.bilgidoku.rom.pg.dict.MethodInteract;
import com.bilgidoku.rom.shared.err.ParameterError;

public class IPNData {

	private Map<String,Object> map=new HashMap<String,Object>();

	public IPNData(MethodInteract req) throws ParameterError {
		put("business",req);
		put("receiver_email",req);
		put("receiver_id",req);
		put("item_name",req);
		put("item_number",req);
		put("invoice",req);
		put("custom",req);
		put("option_name1",req);
		put("option_selection1",req);
		put("option_name2",req);
		put("option_selection2",req);
		put("payment_status",req);
		put("pending_reason",req);
		put("reason_code",req);
		put("payment_date",req);
		put("txn_id",req);
		put("parent_txn_id",req);
		put("tn_type",req);
		put("subscr_ signup",req);
		put("subscr_ cancel",req);
		put("subscr_ modify",req);
		put("subscr_payment",req);
		put("subscr_ failed",req);
		put("subscr_eot",req);
		put("mc_gross",req);
		put("mc_fee",req);
		put("mc_currency",req);
		put("settle_amount",req);
		put("echange_rate",req);
		put("payment_gross",req);
		put("payment_fee",req);
		put("first_name",req);
		put("last_name",req);
		put("payer_business_name",req);
		put("address_name",req);
		put("address_street",req);
		put("address_city",req);
		put("address_state",req);
		put("address_zip",req);
		put("address_ country",req);
		put("payer_email",req);
		put("payer_id",req);
		put("payer_status",req);
		put("payment_type",req);
		put("subscr_date",req);
		put("subscr_effective",req);
		put("period1",req);
		put("period2",req);
		put("period3",req);
		put("amount1",req);
		put("amount2",req);
		put("amount3",req);
		put("mc_amount1",req);
		put("mc_amount2",req);
		put("mc_amount3",req);
		put("recurring",req);
		put("reattempt",req);
		put("retry_at",req);
		put("recur_times",req);
		put("username",req);
		put("password",req);
		put("subscr_id",req);
//		if(get("payer_email").equals("avci.y_1348212871_per@gmail.com")){
//			map.put("payer_email", "avci.yusuf@gmail.com");
//		}
	}

	private void put(String string, MethodInteract req) throws ParameterError  {
		String val=req.getParam(string);
		if(val!=null){
			this.map.put(string,val);
		}
		
	}

	public void out() {
//		syso("===== PayPal IPN event: ===========");
//		for (Entry<String, Object> entry:map.entrySet()) {
//			syso(entry.getKey()+":"+entry.getValue());
//		}
//		syso("===== End =========================");
	}

	public boolean paymentCompleted() {
		String payment_status=(String) map.get("payment_status");
		return payment_status!=null && payment_status.equals("Completed");
	}

	public String get(String string) {
		return (String) map.get(string);
	}

	public String payerName() {
		
		String fn=(String) map.get("first_name");
		String ln=(String) map.get("last_name");
		return fn+" "+ln;
	}

	public BigDecimal getBigDecimal(String key) {
		String bd=(String) map.get(key);
		if(bd==null)
			return null;
		return new BigDecimal(bd);
	}

	public Integer getInteger(String key) {
		String bd=(String) map.get(key);
		if(bd==null)
			return null;
		return Integer.parseInt(bd);
	}

	public Long getLong(String key) {
		String bd=(String) map.get(key);
		if(bd==null)
			return null;
		return Long.parseLong(bd);
	}




	
	
}
