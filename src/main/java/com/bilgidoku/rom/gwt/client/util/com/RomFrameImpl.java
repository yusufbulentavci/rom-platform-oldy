package com.bilgidoku.rom.gwt.client.util.com;


import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Frame;


/**
 * 
 * one'in content kisminda cikan frame'ler bundan turer
 * Belli url ve parametreler ile urlbuilder kullanilarak content'ini alirlar
 * 
 * 
 * 
 * @see com.bilgidoku.rom.site.yerel.writings.WritingFrame
 * @see com.bilgidoku.rom.site.yerel.common.SvgEditWrapper
 * 
 * @author rompg
 *
 */
public class RomFrameImpl extends Frame implements RomFrame{
	
	private final RomFrameHandler rfh;
	private String initialText;
	private boolean ready=false;
	
	public RomFrameImpl(UrlBuilder urlBuilder, final RomFrameHandler rfh, final String initialText){
		super(urlBuilder.setProtocol(Location.getProtocol()).setHost(Location.getHost()).buildString());
		this.setHeight("100%");
		this.rfh=rfh;
		this.initialText=initialText;
		Scheduler.get().scheduleFixedPeriod(new RepeatingCommand() {
			
			@Override
			public boolean execute() {
				if(FrameCom.isReady(RomFrameImpl.this)){
					ready();
					return false;
				}
				return true;
				
			}
		}, 300);
	}
	
	
	public String getText(){
		if(!ready){
			Window.alert("Frame Component is not ready yet");
			return null;
		}
		return FrameCom.getChildStr(this);
	}
	
	public String getAttr(String name){
		if(!ready){
			Window.alert("Frame Component is not ready yet");
			return null;
		}
		return FrameCom.getChildAttr(this, name);
	}
	
	public void setText(String text){
		if(!ready){
			this.initialText=text;
			return;
		}
		
		FrameCom.setChildStr(this, text);
	}

	
	public void focusItem(String cls, String uri){
		if(rfh!=null)
			rfh.focusItem(cls, uri);
	}
	public void setItem(String cls, String uri){
		if(rfh!=null)
			rfh.setItem(cls, uri);
	}
	public void ready(){
		this.ready=true;
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			
			@Override
			public void execute() {
				if(initialText!=null){
					setText(initialText);
					initialText=null;
				}
				if(rfh!=null)
					rfh.ready();
			}
			
		});		
		
	}

}
