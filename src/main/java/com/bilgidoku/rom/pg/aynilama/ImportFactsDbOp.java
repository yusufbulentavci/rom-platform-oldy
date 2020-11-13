package com.bilgidoku.rom.pg.aynilama;

import com.bilgidoku.rom.pg.sqlunit.model.Field;
import com.bilgidoku.rom.pg.sqlunit.model.Table;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

import net.sf.clipsrules.jni.AkilGorevlisi;

public class ImportFactsDbOp extends DbOp {
	private Table tbl;

	public ImportFactsDbOp(Table tbl) {
		this.tbl = tbl;
	}

	public void doit() throws KnownError {

		try (DbThree db3 = new DbThree("select * from " + tbl.getDbName())) {
			if(!db3.executeQuery())
				return;
			
			while (db3.next()) {
				StringBuilder sb = new StringBuilder();
				sb.append("(");
				sb.append(tbl.getName());
				sb.append(" ");
				for(Field f:tbl.getFields()) {
					sb.append("(");
					sb.append(f.name);
					sb.append(" ");
					sb.append(db3.getString());
					sb.append(")");
					sb.append(" ");
				}
				sb.append(")");
				AkilGorevlisi.tek().addFact(sb.toString());
			}
		}
	}

}
