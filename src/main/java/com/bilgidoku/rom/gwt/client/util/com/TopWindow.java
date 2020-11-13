package com.bilgidoku.rom.gwt.client.util.com;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;

public class TopWindow extends CompBase{
	
	public static final CompInfo info=new CompInfo("+topwindow", 500, new String[]{}, new String[]{"_wndtop"}, null);
	public static final CompFactory factory=new CompFactory() {
		
		@Override
		public CompInfo info() {
			return info;
		}
		
		@Override
		public Comp create() {
			return new TopWindow();
		}
	};
	
	private Master master=null;

	public TopWindow(){
		
		Window.addCloseHandler(new CloseHandler<Window>() {
			
			@Override
			public void onClose(CloseEvent<Window> event) {
				RomEntryPoint.com().post("*wnd.closing");
			}
		});
		
		Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {
			
			@Override
			public boolean execute() {
				checkMaster();
				return true;
			}
		}, 3000);
		checkMaster();
	}

	@Override
	public boolean handle(String cmd, JSONObject cjo) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	protected void checkMaster() {
		long now = System.currentTimeMillis();
		if (this.master!=null) {
			this.master.tick();
			
			return;
		}
		String sot = RomEntryPoint.com().get(FrameCom.SESSION_ONLINE_TIME);
		if (sot == null) {
			bemaster();
			return;
		}

		long sotl = Long.parseLong(sot);
		if (now > sotl + 10000) {
			bemaster();
			return;
		}

	}

	private void bemaster() {
		Sistem.outln("GOING TO BE MASTER");
		this.master=new Master();
		this.master.start();
	}


	@Override
	public CompInfo compInfo() {
		return info;
	}






}
