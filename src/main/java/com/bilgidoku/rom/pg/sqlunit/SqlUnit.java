package com.bilgidoku.rom.pg.sqlunit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bilgidoku.rom.pg.sqlunit.model.Command;

public class SqlUnit {
	private Set<String> depends = new HashSet<String>();
	private boolean hasUnitTest = false;

	private List<SuComp> comps = new ArrayList<SuComp>();

	public final String named;
	private String pack;

	public SqlUnit(String named, String pack) {
		this.named = named;
		this.pack = pack;
		if(!named.endsWith(".base")) {
			if(pack==null) {
				depends.add("base");
			}else {
				depends.add(pack+".base");
			}
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SqlUnit ");
		sb.append(this.named);
		sb.append("\ndepends:");
		for (String str : depends) {
			sb.append(str);
			sb.append(" ");
		}

		return sb.toString();
	}

	public void addCommand(SuComp comp, String command, int line) {
		command = command.trim();
		if (command.length() == 0)
			return;
		comp.addCommand(new Command(command, line));
	}
	
	public void addUpgrade(SuComp comp, int version, String command, int line) {
		
		if(comp.getSu()==null){
			comps.add(comp);
		}
		
		command = command.trim();
		if (command.length() == 0)
			return;
		
		comp.addUpgrade(version, new Command(command, line));
	}

	public void addDependency(String depend) {
		System.out.println("-------=-"+depend);
		this.depends.add(depend);
	}

	public Set<String> getDepends() {
		return depends;
	}

	public boolean hasUnitTest() {
		return hasUnitTest;
	}

	public List<SuComp> getComps() {
		return comps;
	}

	public boolean isHasUnitTest() {
		return hasUnitTest;
	}

	public void setHasUnitTest(boolean hasUnitTest) {
		this.hasUnitTest = hasUnitTest;
	}
	
	public String getSchemaName(){
		int li = named.indexOf('/');
		if (li < 0)
			return "public";
		return named.substring(0, li);
	}

	public void addComp(SuComp parseComp) {
		this.comps.add(parseComp);
	}

	public String getNamed() {
		return named;
	}
}
