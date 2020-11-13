package com.bilgidoku.rom.pg.sqlunit;

import java.util.List;

import com.bilgidoku.rom.pg.sqlunit.model.Field;
import com.bilgidoku.rom.pg.sqlunit.model.Table;

public class TableToDeftemplate {
	public static String gen(StringBuilder sb, Table table) {

		sb.append("(deftemplate ");
		sb.append(table.getName());
		sb.append('\n');
		List<Field> fs = table.getClsFields();
		for (Field f : fs) {
			if (f.getTypeHolder().isArray()) {
				sb.append("(multislot ");
				sb.append(f.name);
				sb.append(')');
			} else {
				sb.append("(slot ");
				sb.append(f.name);
				sb.append(')');
			}
			sb.append('\n');
		}
		sb.append(")\n");
		return sb.toString();
	}

}
