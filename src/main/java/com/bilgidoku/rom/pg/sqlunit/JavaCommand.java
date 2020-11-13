package com.bilgidoku.rom.pg.sqlunit;

import java.sql.Connection;
import java.util.Map;

public interface JavaCommand {
	public void run(Connection connection, Map<String,Object> scope);
}
