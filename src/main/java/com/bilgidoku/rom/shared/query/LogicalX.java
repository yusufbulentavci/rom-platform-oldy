package com.bilgidoku.rom.shared.query;

public abstract class LogicalX extends LogicRelation {

	private String xAsText;
	private String sql;

	public LogicalX(String name, String xAsText, String sql) {
		super(name);
		this.xAsText=xAsText;
		this.sql=sql;
	}

	@Override
	public void asText(StringBuilder sb) {
		sb.append(" ");

		if (this.subs.size() == 1) {
			this.subs.get(0).asText(sb);
			return;
		}

		for (int i = 0; i < this.subs.size(); i++) {
			SuchCriteria it = this.subs.get(i);
			if (i != this.subs.size() - 1) {
				it.asText(sb);
				sb.append(" ");
				sb.append(xAsText);
			}
		}

	}

	@Override
	public void buildQuery(QueryContext qc) {
		qc.append(" (");

		if (this.subs.size() == 1) {
			this.subs.get(0).buildQuery(qc);
			return;
		}

		for (int i = 0; i < this.subs.size(); i++) {
			SuchCriteria it = this.subs.get(i);
			if (i != this.subs.size() - 1) {
				it.buildQuery(qc);
				qc.append(" ");
				qc.append(sql);
			}
		}

		qc.append(")");
	}

}
