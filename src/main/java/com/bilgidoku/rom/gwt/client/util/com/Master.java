package com.bilgidoku.rom.gwt.client.util.com;

public class Master{
	
	
	private WebSocketClient sock;
	
	
	
	
	
	public void start(){
		tick();
		if (sock == null)
			this.sock = new WebSocketClient();

		sock.connect("online");
	}

	


	public void tick() {
//		RomEntryPoint.com().changeData(FrameCom.SESSION_ONLINE_TIME, System.currentTimeMillis() + "");
	}


	

}
