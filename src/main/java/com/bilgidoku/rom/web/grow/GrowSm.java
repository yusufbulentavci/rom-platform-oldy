package com.bilgidoku.rom.web.grow;

public class GrowSm {
//	extends Sm implements GrowSmMBean 
	
//	public GrowSm(ServiceInfo si) {
//		super(si);
//	}
//
//	@Override
//       	public Integer trNewHost(String hostName, String email, String adminName, String password) throws KnownError{
//		GrowService g=ServiceDiscovery.getService(GrowService.class);
//		return g.newHost(hostName, email, adminName, password, "TR", "tr");
//	}
//	@Override
//	public void grow(String json) throws KnownError {
//		GrowService g=ServiceDiscovery.getService(GrowService.class);
//		try {
//			JSONObject jo=new JSONObject(json);
//			g.grow(jo);
//		
//		} catch (JSONException e) {
//			throw new KnownError(e);
//		}
//	}
//
//	@Override
//	public void tmlos() throws KnownError {
//		throw new RuntimeException("TMLOS is not used");
////		try {
////			JSONObject dataClient = new JSONObject();
////			dataClient.put("hostname", RomEnvFactory.getMine().isDev?"mlos.net":"tlos.net");
////			dataClient.put("lang", "en");
////			dataClient.put("admin", "admin");
////			dataClient.put("credential", "aghhbaaghhba");
////			dataClient.put("manual", true);
////			JSONObject contact = techcontact();
////			dataClient.put("contact", contact);
////
////			dataClient.put("business", "Tlos");
////			dataClient.put("cc", "TR");
////
////			JSONObject dataServer = new JSONObject();
////			dataServer.put("email", contact.getString("email"));
////			dataServer.put("money", 100000);
////
////			JSONObject jo = new JSONObject();
////			jo.put("s", dataServer);
////			jo.put("c", dataClient);
////
////			JSONObject m=new JSONObject();
////			m.put("d", jo);
////			
////			GrowService g=ServiceDiscovery.getService(GrowService.class);
////			g.grow(m);
////			
////			
////		} catch (JSONException e) {
////			throw new KnownError(e);
////		}
//		
//		
//	}
//	
//	public JSONObject techcontact() throws KnownError {
//		JSONObject ret = new JSONObject();
//		try {
//			ret.put("firstName", "Yusuf Bülent").put("lastName", "Avci")
//					.put("organization", "Bilgidoku Technology")
//					.put("email", "yusufbulentavci@gmail.com")
//					.put("address", "Mimarsinan mah. Silivri")
//					.put("countryCode", "TR").put("city", "Istanbul")
//					.put("phone", "902127290677");
//			return ret;
//		} catch (JSONException e) {
//			throw new KnownError(e);
//		}
//	}

}
