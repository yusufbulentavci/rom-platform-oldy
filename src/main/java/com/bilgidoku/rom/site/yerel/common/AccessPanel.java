package com.bilgidoku.rom.site.yerel.common;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.JsonResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.ResourcesDao;
import com.bilgidoku.rom.shared.CRoleMask;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;


class MaskUnit extends HorizontalPanel{
	CheckBox read=new CheckBox("read");
	CheckBox write=new CheckBox("write");
	CheckBox exec=new CheckBox("exec");
	MaskUnit(int roleId, int val){
		exec.setValue((val&1)!=0);
		write.setValue((val&2)!=0);
		read.setValue((val&4)!=0);
		add(read);
		add(write);
		add(exec);
	}
	
	public int getValue(){
		return (read.getValue()?4:0)+(write.getValue()?2:0)+(exec.getValue()?1:0);
	}
	
}

public class AccessPanel extends VerticalPanel{
	private final List<MaskUnit> mus=new ArrayList<>();
	protected long initialMask;
	private boolean dirty;
	private String uri;
	
	public AccessPanel(String uri){
		this.uri=uri;
		ResourcesDao.getaccess(uri, new JsonResponse(){
			@Override
			public void ready(Json value) {
				if(value==null)
					return;
				JSONObject obj=value.getValue().isObject();
				initialMask=Long.parseLong(obj.get("mask").isString().stringValue());
				initialize( );
			}

		});
	}
	private void initialize() {
		Grid grid = new Grid(CRoleMask.rolesByName.length, 2);
		for(int i=0; i<CRoleMask.rolesByName.length; i++){
			HorizontalPanel vp = new HorizontalPanel();
			grid.setWidget(i, 0, new Label(CRoleMask.rolesByName[i]));
			
			MaskUnit mu = new MaskUnit(i, CRoleMask.getMaskTokenOfRole(initialMask, i));
			grid.setWidget(i, 1, mu);

			mus.add(mu);
		}
		
		this.add(grid);
	}
	
	private long refreshValue(){
		int[] us=new int[mus.size()];
		for(int i=0; i<us.length; i++){
			us[i]=mus.get(i).getValue();
		}
		long l=CRoleMask.buildMask(us);
		
		this.dirty=(l!=initialMask);
		this.initialMask=l;
		return l;
	}
	
	
	public void save(){
		refreshValue();
		if(dirty){
			ResourcesDao.setmask(initialMask, uri, new StringResponse(){});
			dirty=false;
		}
	}
	
	

}
