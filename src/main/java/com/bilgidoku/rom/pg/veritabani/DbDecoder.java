package com.bilgidoku.rom.pg.veritabani;

import java.sql.ResultSet;
import java.util.List;

public interface DbDecoder<T> {
	List<T> decode(String hostName, ResultSet rs);
}
