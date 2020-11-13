package com.bilgidoku.rom.pg.sqlunit.rom;

import java.util.LinkedList;
import java.util.List;

import com.bilgidoku.rom.pg.sqlunit.model.Command;

public class RomComp {
	public String compType;
	public String schemaName;
	public String named;
	public int ver;
	
	

	public RomComp(String compType, String schemaName, String named, int ver) {
		this.compType = compType;
		this.schemaName = schemaName;
		this.named = named;
		this.ver = ver;

		if (compType.equals("function") && !schemaName.equals("public") 
				&& named.startsWith(schemaName + ".")) {
			this.named=named.substring((schemaName + ".").length());
		}

	}

	public String getId() {
		return schemaName + "_" + compType + "_" + named;
	}

	public List<Command> drop() {
		if (compType.equals("data")) {
			return null;
		}
		StringBuilder sb = new StringBuilder();

		if (compType.equals("function")) {
			sb.append("select dropfunction('");
			sb.append(schemaName);
			sb.append("','");
			sb.append(named);
			sb.append("');");
		} else {

			sb.append("drop ");
			sb.append(compType);
			sb.append(" if exists ");
			if (compType.equals("trigger")) {
				sb.append(named);
				sb.append(" on ");
				String[] ts = named.split("_");
				if (ts.length < 3) {
					throw new RuntimeException("Problem in dropping trigger. "
							+ "Notation syntax of trigger name failed, should be tablename_XX." + named);
				}
				sb.append(ts[0]);
				sb.append(".");
				sb.append(ts[1]);
			} else if (compType.equals("table") || compType.equals("type")) {
				sb.append(schemaName);
				sb.append(".");
				sb.append(named);
			} else {
				sb.append(named);
			}
			sb.append(" cascade;");
		}
		List<Command> cmds = new LinkedList<Command>();
		cmds.add(new Command(sb.toString(), 0));
		return cmds;
	}
	
	public String toString(){
		return "romcmp->type:"+compType+" sch:"+schemaName+" nm:"+named+" ver:"+ver;
	}

}
