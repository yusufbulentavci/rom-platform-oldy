package com.bilgidoku.rom.pg.sqlunit.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.bilgidoku.rom.pg.sqlunit.SqlUnit;
import com.bilgidoku.rom.pg.sqlunit.SuComp;
import com.bilgidoku.rom.pg.sqlunit.rom.RomComp;

public class Data extends SuComp {

	private List<DataColumn> columns;

	private String table;
	private String name;
	private boolean createFunction = false;
	private boolean updateData = false;

	public Data(SqlUnit su, boolean isSql, boolean unitTest, int lineNo) {
		super(su, isSql, unitTest, lineNo, false);
	}

	@Override
	public boolean isLineBased() {
		return true;
	}
	
	@Override
	public List<Command> run() {

		StringBuilder ret = new StringBuilder();
		List<Command> cmds = new ArrayList<Command>();
		if (createFunction) {
			ret.append("create or replace function ");
			ret.append(this.getSu().getSchemaName());
			ret.append(".");
			ret.append(name);
			ret.append("(");
			addParameters(ret);
			ret.append(") returns void as $$\nbegin\n");
		}

		for (Command comm : this.getCommands()) {
			genCmd(comm, ret, cmds);
		}

		if (createFunction) {
			ret.append("end;\n$$ language 'plpgsql';\n");
			cmds.add(new Command(ret.toString(), 0));
			if (updateData) {
				ret = new StringBuilder();
				ret.append("do $$\n");
				ret.append("declare mviews record;\n");
				ret.append("begin\n");
				ret.append("for mviews in select * from ");
				ret.append(table);
				ret.append(" loop\n");
				ret.append("\tperform ");
				ret.append(this.getSu().getSchemaName());
				ret.append(".");
				ret.append(name);
				ret.append("(");
				addParamNames(ret);
				ret.append(");\n");
				ret.append("end loop;\n");
				ret.append("end;\n");
				ret.append("$$;\n");
				cmds.add(new Command(ret.toString(), 1));
			}
		}

		return cmds;
	}

	public List<Command> upgrade(Integer dbVer) {
		List<Command> ret = new LinkedList<Command>();
		ret.addAll(super.upgrade(dbVer));
		ret.addAll(run());
		return ret;
	}

	private void addParameters(StringBuilder ret) {
		boolean one = false;
		for (int i = 0; i < columns.size(); i++) {
			DataColumn dc = columns.get(i);
			if (dc.parametric) {
				if (one) {
					ret.append(",");
				} else
					one = true;
				ret.append("p_");
				ret.append(dc.named);
				ret.append(" ");
				ret.append(dc.field.getTypeHolder().getSqlDefinition());
			}
		}
	}

	private void addParamNames(StringBuilder ret) {
		boolean one = false;
		for (int i = 0; i < columns.size(); i++) {
			DataColumn dc = columns.get(i);
			if (dc.parametric) {
				if (one) {
					ret.append(",");
				} else
					one = true;
				ret.append("mviews.");
				ret.append(dc.named);
			}
		}
	}

	private void genCmd(Command comm, StringBuilder ret, List<Command> cmds) {
		boolean updateSetHas = false;
		StringBuilder updateSet = new StringBuilder();
		boolean updateWhereHas = false;
		StringBuilder updateWhere = new StringBuilder();

		boolean insertColHas = false;
		StringBuilder insertColumns = new StringBuilder();
		StringBuilder insertValues = new StringBuilder();

		String[] values = comm.getCommand().split("\t");
		if (values.length != columns.size()) {
			StringBuilder sb = new StringBuilder();
			for (String string : values) {
				sb.append(string);
				sb.append("|");
			}
			throw new RuntimeException("Columns size not same for declaration(" + columns.size() + ") and data("
					+ values.length + ");\nvalues:" + sb.toString() + "\n Columns:" + columns);
		}
		for (int i = 0; i < columns.size(); i++) {
			DataColumn dc = columns.get(i);
			String value = values[i];
			String valStr = null;
			if (dc.parametric) {
				valStr = "p_" + dc.named;
			} else {
				if (value.equals("null")) {
					valStr = "null";
				} else {
					if (dc.resourceColumn) {
						try {
							value = getResourceStr(value);
						} catch (IOException e) {
							throw new RuntimeException("Resource not found var column value:" + value, e);
						}
					}
					if (value == null) {
						valStr = "null";
					} else {
						valStr = dc.field.getTypeHolder().isToQuote() ? "'" + value.replace("'", "''") + "'" : value;
						if (dc.field.getTypeHolder().needCast()) {
							valStr += "::" + dc.field.getTypeHolder().getSqlDefinition();
						}
					}

				}
			}
			if (dc.autoInc && dc.field.pk) {
				if (updateWhereHas) {
					updateWhere.append(" and ");
				}
				updateWhereHas = true;

				updateWhere.append(dc.named);
				updateWhere.append("=");
				updateWhere.append(valStr);

			} else if (dc.field.pk) {
				if (updateWhereHas) {
					updateWhere.append(" and ");
				}
				updateWhereHas = true;

				updateWhere.append(dc.named);
				updateWhere.append("=");
				updateWhere.append(valStr);

				if (insertColHas) {
					insertColumns.append(",");
					insertValues.append(",");
				}
				insertColHas = true;
				insertColumns.append(dc.named);
				insertValues.append(valStr);
			} else if (dc.autoInc) {

			} else {
				if (insertColHas) {
					insertColumns.append(",");
					insertValues.append(",");
				}
				insertColHas = true;
				insertColumns.append(dc.named);
				insertValues.append(valStr);

				if (dc.onlyInsert && !dc.parametric)
					continue;

				if (updateSetHas) {
					updateSet.append(", ");
				}
				updateSetHas = true;
				updateSet.append(dc.named);
				updateSet.append("=");
				updateSet.append(valStr);

			}
		}

		StringBuilder updateRet = new StringBuilder();
		updateRet.append("update ");
		updateRet.append(table);
		updateRet.append(" set ");
		updateRet.append(updateSet.toString());
		updateRet.append(" where ");
		updateRet.append(updateWhere.toString());
		updateRet.append(";\n");

		StringBuilder insertRet = new StringBuilder();
		insertRet.append("if not found then\n");
		insertRet.append("\t");
		insertRet.append("insert into ");
		insertRet.append(table);
		insertRet.append(" (");
		insertRet.append(insertColumns.toString());
		insertRet.append(") values (");
		insertRet.append(insertValues.toString());
		insertRet.append(");\n");
		insertRet.append("end if;\n");

		if (createFunction) {
			ret.append(updateRet.toString());
			ret.append(insertRet.toString());
		} else {
			cmds.add(new Command("do $$\nbegin\n" + updateRet.toString() + insertRet.toString() + "end;$$;\n", 0));
		}
		// if(createFunction){
		// createFunc.append("insert into ");
		// createFunc.append(table);
		// createFunc.append(" (");
		// createFunc.append(insertColumns.toString());
		// createFunc.append(") values (");
		// createFunc.append(insertValues.toString());
		// createFunc.append(")\n");
		// }
	}

	private String getResourceStr(String value) throws IOException {
		InputStream is = Data.class.getClassLoader().getResourceAsStream(value);
		if (is == null)
			return null;
		StringBuilder out = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		try {

			boolean space = false;
			do {
				String read = in.readLine();
				if (read == null)
					break;
				if (space)
					out.append("\n");
				out.append(read);
				space = true;
			} while (true);
		} finally {
			in.close();
		}
		return out.toString();
	}

	@Override
	public RomComp getComp() {
		return new RomComp("data", this.getSu().getSchemaName(), table + "_" + name, this.getVersion());
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCreateFunction() {
		return createFunction;
	}

	public void setCreateFunction(boolean createFunction) {
		this.createFunction = createFunction;
	}

	public List<DataColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<DataColumn> columns) {
		this.columns = columns;
	}

	public void setUpdateData(boolean updateData) {
		this.updateData = updateData;
	}

}
