package com.bilgidoku.rom.shared.events;


public interface HtmlEvent extends WebEvent{

	public String value();
	
	public Object params();
	
	public String changeid();
	public int clientx();
	public int clienty();

	public int screenx();
	public int screeny();

	public boolean mk();

	public boolean altkey();
	
	public boolean ctrlkey();
	
	public boolean shiftkey();
	
	public boolean metakey();
	
	public int button();

	public String targettag();

	public String targetid();

}
