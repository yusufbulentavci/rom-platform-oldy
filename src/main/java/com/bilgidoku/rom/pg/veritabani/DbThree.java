package com.bilgidoku.rom.pg.veritabani;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;
import org.postgresql.util.PGobject;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.util.Assert;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.dosya.DosyaGorevlisi;

public class DbThree implements AutoCloseable, DbSetGet {

	protected final String query;
	protected final Connection connection;
	private static final MC mc = new MC(DbThree.class);
	protected Statement statement = null;
	private ResultSet resultSet = null;
	private int columnIndex = 1;
	private int[] trueFieldList;
	private static final Astate c1 = mc.c("resultset-close");

	public DbThree(String query) throws KnownError {
		this.query = query;
		connection = VeritabaniGorevlisi.tek().getConnection();
		setQuery(query);
	}

	public void setTransactional() throws KnownError {
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorOccured(connection, "autocommit=false");
		}
	}

	public void commit() throws KnownError {
		try {
			connection.commit();
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errGeneric(connection, "commit", e);
		}
	}

	public void rollback() throws KnownError {
		try {
			connection.rollback();
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorOccured(connection, "rollback");
		}
	}

	public long insertFile(InputStream stream, String adi, Integer omru) throws KnownError {

		setTransactional();
		try {
			// Get the Large Object Manager to perform operations with
			LargeObjectManager lobj = connection.unwrap(org.postgresql.PGConnection.class).getLargeObjectAPI();
			// Create a new large object
			long oid = lobj.createLO(LargeObjectManager.READ | LargeObjectManager.WRITE);
			// Open the large object for writing
			LargeObject obj = lobj.open(oid, LargeObjectManager.WRITE);

			DosyaGorevlisi.tek().ekle(obj.getInputStream(), adi, omru, oid);

			// Close the large object
			obj.close();
			return oid;
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errGeneric(connection, "insert file", e);
		}
	}

	public File getFile(long oid) throws KnownError {
		setTransactional();
		try {
			// Get the Large Object Manager to perform operations with
			LargeObjectManager lobj = connection.unwrap(org.postgresql.PGConnection.class).getLargeObjectAPI();

			try (LargeObject obj = lobj.open(oid, LargeObjectManager.READ);) {
				int id = DosyaGorevlisi.tek().ekle(obj.getInputStream(), null, null, oid);
				return DosyaGorevlisi.tek().dosyaById(id);
			}
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errGeneric(connection, "get file", e);
		}
	}

	private void setQuery(String query) throws KnownError {
		try {
			statement = connection.prepareStatement(query);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorOccured(connection, query);
		}
	}

	public void replaceQuery(String query) throws KnownError {
		closeStatement();
		setQuery(query);
	}

	public boolean next() throws KnownError {
		begin();
		try {
			if (resultSet == null)
				throw new KnownError("No data found");

			return resultSet.next();
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorOccured(connection, query);
		}
	}
	
	public ResultSetMetaData metadata() throws KnownError {
		begin();
		try {
			if (resultSet == null)
				return null;

			return resultSet.getMetaData();
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorOccured(connection, query);
		}
	}

	private void begin() {
		columnIndex = 1;
	}

	public void checkedNext() throws KnownError {
		begin();
		try {
			if (resultSet == null)
				throw new KnownError("No data found");

			if (!resultSet.next()) {
				throw new KnownError("No query result");
			}
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorOccured(connection, query);
		}
	}

	public boolean executeQuery() throws KnownError {
		try {
			this.trueFieldList = null;
			resultSet = ps().executeQuery();
		} catch (SQLException e) {
			String sqlState = e.getSQLState();
			// No data found
			if (sqlState != null && sqlState.equals("P0002")) {
				return false;
			}
			throw VeritabaniGorevlisi.tek().errExecute(connection, query, e);
		}
		return true;
	}

	public DbThree eq() throws KnownError {
		boolean b = executeQuery();
		if (!b) {
			throw new KnownError("Should return value but not: " + query);
		}
		b = next();
		if (!b) {
			throw new KnownError("Should return value but not: " + query);
		}
		return this;
	}

	public void checkedExecute() throws KnownError {
		Assert.beTrue(execute());
	}

	public boolean execute() throws KnownError {
		try {
			boolean ret = ps().execute();
			begin();
			return ret;
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errExecute(connection, statement.toString(), e);
		}
	}

	private static final Astate c2 = mc.c("statement-close").noFailure();
	private static final Astate c3 = mc.c("connection-close").noFailure();

	public void close() {
		if (resultSet != null)
			try {
				resultSet.close();
			} catch (SQLException e) {
				c1.more();
			}
		if (statement != null)
			try {
				statement.close();
			} catch (SQLException e) {
				c2.more();
			}
		if (connection != null) {
			try {
				SQLWarning wns = connection.getWarnings();
				while (wns != null) {
					wns.getMessage();
					wns = wns.getNextWarning();

					connection.clearWarnings();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				Sistem.printStackTrace(e1);
			}

			try {
				connection.close();
			} catch (SQLException e) {
				c3.more();
			}
		}
	}

	public void closeStatement() {
		begin();
		if (resultSet != null)
			try {
				resultSet.close();
			} catch (SQLException e) {
				c1.more();
			}
		if (statement != null)
			try {
				statement.close();
			} catch (SQLException e) {
				c2.more();
			}
	}

	public void closeResultSet() {
		begin();
		if (resultSet != null)
			try {
				resultSet.close();
			} catch (SQLException e) {
				c1.more();
			}
	}

	public DbThree setStringArray(String[] items) throws KnownError {
		try {
			Array a = con().createArrayOf("text", items);
			ps().setArray(columnIndex++, a);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setIntArray(Integer[] items) throws KnownError {
		try {
			Array a = con().createArrayOf("int", items);
			ps().setArray(columnIndex++, a);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setArray(String sqlName, Object[] objs) throws KnownError {

		try {
			Array a = con().createArrayOf(sqlName, objs);
			ps().setArray(columnIndex++, a);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setAsciiStream(InputStream x) throws KnownError {
		try {
			ps().setAsciiStream(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setAsciiStream(InputStream x, int length) throws KnownError {
		try {
			ps().setAsciiStream(columnIndex++, x, length);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setAsciiStream(InputStream x, long length) throws KnownError {
		try {
			ps().setAsciiStream(columnIndex++, x, length);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setBigDecimal(BigDecimal x) throws KnownError {
		try {
			ps().setBigDecimal(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setBinaryStream(InputStream x) throws KnownError {
		try {
			ps().setBinaryStream(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;

	}

	public DbThree setBinaryStream(InputStream x, int length) throws KnownError {
		try {
			ps().setBinaryStream(columnIndex++, x, length);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setBinaryStream(InputStream x, long length) throws KnownError {
		try {
			ps().setBinaryStream(columnIndex++, x, length);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;

	}

	public DbThree setBlob(Blob x) throws KnownError {
		try {
			ps().setBlob(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setBlob(InputStream inputStream) throws KnownError {
		try {
			ps().setBlob(columnIndex++, inputStream);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setBlob(InputStream inputStream, long length) throws KnownError {
		try {
			ps().setBlob(columnIndex++, inputStream, length);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;

	}

	public DbThree setBoolean(boolean x) throws KnownError {
		try {
			ps().setBoolean(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setByte(byte x) throws KnownError {
		try {
			ps().setByte(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setBytes(byte[] x) throws KnownError {
		try {
			ps().setBytes(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setCharacterStream(Reader reader) throws KnownError {
		try {
			ps().setCharacterStream(columnIndex++, reader);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setCharacterStream(Reader reader, int length) throws KnownError {
		try {
			ps().setCharacterStream(columnIndex++, reader, length);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setCharacterStream(Reader reader, long length) throws KnownError {
		try {
			ps().setCharacterStream(columnIndex++, reader, length);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setClob(Clob x) throws KnownError {
		try {
			ps().setClob(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setClob(Reader reader) throws KnownError {
		try {
			ps().setClob(columnIndex++, reader);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setClob(Reader reader, long length) throws KnownError {
		try {
			ps().setClob(columnIndex++, reader, length);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setDate(Date x) throws KnownError {
		try {
			ps().setDate(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setDate(Date x, Calendar cal) throws KnownError {
		try {
			ps().setDate(columnIndex++, x, cal);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setDouble(double x) throws KnownError {
		try {
			ps().setDouble(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setFloat(float x) throws KnownError {
		try {
			ps().setFloat(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setInt(Integer x) throws KnownError {
		try {
			if (x == null) {
				ps().setNull(columnIndex++, java.sql.Types.INTEGER);
			} else {
				ps().setInt(columnIndex++, x);
			}
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;

	}


	public DbThree setShort(Short x) throws KnownError {
		try {
			if (x == null) {
				ps().setNull(columnIndex++, java.sql.Types.SMALLINT);
			} else {
				ps().setShort(columnIndex++, x);
			}
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;

	}
	
	public DbThree setLong(Long x) throws KnownError {
		try {
			if (x == null) {
				ps().setNull(columnIndex++, java.sql.Types.BIGINT);
			} else {
				ps().setLong(columnIndex++, x);
			}
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;

	}
	
//	public DbThree setLong(long x) throws KnownError {
//		try {
//			ps().setLong(columnIndex++, x);
//		} catch (SQLException e) {
//			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
//		}
//		return this;
//	}


	public DbThree setNCharacterStream(Reader value) throws KnownError {
		try {
			ps().setNCharacterStream(columnIndex++, value);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setNCharacterStream(Reader value, long length) throws KnownError {
		try {
			ps().setNCharacterStream(columnIndex++, value, length);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setNClob(NClob value) throws KnownError {
		try {
			ps().setNClob(columnIndex++, value);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setNClob(Reader reader) throws KnownError {
		try {
			ps().setNClob(columnIndex++, reader);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setNClob(Reader reader, long length) throws KnownError {
		try {
			ps().setNClob(columnIndex++, reader, length);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setNString(String value) throws KnownError {
		try {
			ps().setNString(columnIndex++, value);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setNull(int sqlType, String typeName) throws KnownError {
		try {
			ps().setNull(columnIndex++, sqlType, typeName);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;

	}

	public DbThree setObject(Object x) throws KnownError {
		try {
			ps().setObject(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;

	}

	public DbThree setObject(Object x, int targetSqlType) throws KnownError {
		try {
			ps().setObject(columnIndex++, x, targetSqlType);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;

	}

	public DbThree setObject(Object x, int targetSqlType, int scaleOrLength) throws KnownError {
		try {
			ps().setObject(columnIndex++, x, targetSqlType, scaleOrLength);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;

	}

	public DbThree setRef(Ref x) throws KnownError {
		try {
			ps().setRef(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;

	}

	public DbThree setRowId(RowId x) throws KnownError {
		try {
			ps().setRowId(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;

	}

	public DbThree setSQLXML(SQLXML xmlObject) throws KnownError {
		try {
			ps().setSQLXML(columnIndex++, xmlObject);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setShort(short x) throws KnownError {
		try {
			ps().setShort(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setString(String x) throws KnownError {
		try {
			ps().setString(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public DbThree setTime(Time x) throws KnownError {
		try {
			ps().setTime(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}

		return this;
	}

	public DbThree setTime(Time x, Calendar cal) throws KnownError {
		try {
			ps().setTime(columnIndex++, x, cal);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}

		return this;
	}

	public DbThree setTimestamp(Timestamp x) throws KnownError {
		try {
			ps().setTimestamp(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}

		return this;
	}

	public DbThree setTimestamp(Timestamp x, Calendar cal) throws KnownError {
		try {
			ps().setTimestamp(columnIndex++, x, cal);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}

		return this;
	}

	public DbThree setURL(URL x) throws KnownError {
		try {
			ps().setURL(columnIndex++, x);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}

		return this;
	}

	public DbThree setUnicodeStream(InputStream x, int length) throws KnownError {
		try {
			ps().setUnicodeStream(columnIndex++, x, length);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;
	}

	public PreparedStatement ps() {
		return (PreparedStatement) statement;
	}

	public ResultSet rs() {
		return resultSet;
	}

	private Connection con() {
		return connection;
	}

	private KnownError errorGet(SQLException e) {
		return VeritabaniGorevlisi.tek().errorGet(connection, "At index:" + columnIndex + " Query:" + query, e);
	}

	public Integer getInt() throws KnownError {

		try {
			int ret = resultSet.getInt(columnIndex++);
			if (resultSet.wasNull())
				return null;
			return ret;
		} catch (SQLException e) {
			throw errorGet(e);
		}
	}

	public Short getShort() throws KnownError {
		try {
			return resultSet.getShort(columnIndex++);
		} catch (SQLException e) {
			throw errorGet(e);
		}
	}

	public long getLong() throws KnownError {
		try {
			return resultSet.getLong(columnIndex++);
		} catch (SQLException e) {
			throw errorGet(e);
		}
	}

	public boolean getBoolean() throws KnownError {
		try {
			return resultSet.getBoolean(columnIndex++);
		} catch (SQLException e) {
			throw errorGet(e);
		}
	}

	@Override
	public Object getArray() throws KnownError {
		try {
			Array arr = resultSet.getArray(columnIndex++);
			if (arr == null)
				return null;
			return arr.getArray();
		} catch (SQLException e) {
			throw errorGet(e);
		}
	}

	public String[] getStringArray() throws KnownError {
		try {
			Array array = resultSet.getArray(columnIndex++);
			if (array != null) {
				Object a = array.getArray();
				if (a instanceof String[]) {
					return (String[]) array.getArray();
				} else {
					Object[] tp = (Object[]) a;
					String[] ret = new String[tp.length];
					for (int i = 0; i < tp.length; i++) {
						PGobject p = (PGobject) tp[i];
						ret[i] = p.getValue();
					}
					return ret;
				}
			}
			return null;
		} catch (SQLException e) {
			throw errorGet(e);
		}
	}

	/**
	 * 
	 * 
	 * 
	 * @param typeName example "rom"."langs"
	 * @return
	 * @throws KnownError
	 */
	public String[] getStringArrayTyped(String typeName) throws KnownError {
		try {
			Array array = resultSet.getArray(columnIndex++);
			if (array != null) {
				Object a = array.getArray();
				Object[] tp = (Object[]) a;
				String[] ret = new String[tp.length];
				for (int i = 0; i < tp.length; i++) {
					PGobject p = (PGobject) tp[i];
					ret[i] = p.getValue();
				}
				return ret;

			}
			return null;
		} catch (SQLException e) {
			throw errorGet(e);
		}
	}

	public BigDecimal getBigDecimal() throws KnownError {
		try {
			return resultSet.getBigDecimal(columnIndex++);
		} catch (SQLException e) {
			throw errorGet(e);
		}
	}

	public byte getByte() throws KnownError {
		try {
			return resultSet.getByte(columnIndex++);
		} catch (SQLException e) {
			throw errorGet(e);
		}
	}

	public Date getDate() throws KnownError {
		try {
			return resultSet.getDate(columnIndex++);
		} catch (SQLException e) {
			throw errorGet(e);
		}
	}

	public double getDouble() throws KnownError {
		try {
			return resultSet.getDouble(columnIndex++);
		} catch (SQLException e) {
			throw errorGet(e);
		}
	}

	public float getFloat() throws KnownError {
		try {
			return resultSet.getFloat(columnIndex++);
		} catch (SQLException e) {
			throw errorGet(e);
		}
	}

	public Time getTime() throws KnownError {
		try {
			return resultSet.getTime(columnIndex++);
		} catch (SQLException e) {
			throw errorGet(e);
		}
	}

	public Timestamp getTimestamp() throws KnownError {
		try {
			return resultSet.getTimestamp(columnIndex++);
		} catch (SQLException e) {
			throw errorGet(e);
		}
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public boolean wasNull() throws KnownError {
		try {
			return resultSet.wasNull();
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorOccured(connection, query);
		}
	}

	public Object getObject(String string) throws KnownError {
		try {
			return resultSet.getObject(string);
		} catch (SQLException e) {
			throw errorGet(e);
		}
	}

	public DbThree setNull(int sqlType) throws KnownError {
		try {
			ps().setNull(columnIndex++, sqlType);
		} catch (SQLException e) {
			throw VeritabaniGorevlisi.tek().errorSet(connection, query, e);
		}
		return this;

	}

	public String getString() throws KnownError {
		try {

			return resultSet.getString(getIncColumnIndex());
		} catch (SQLException e) {
			throw errorGet(e);
		}
	}

	@Override
	public void setTrueOrder(int[] trueFieldList) {
		this.trueFieldList = trueFieldList;
	}

	@Override
	public String[] genTrueFieldNames() throws KnownError {
		try {
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String[] clmns = new String[rsmd.getColumnCount()];
			for (int i = 0; i < clmns.length; i++) {
				clmns[i] = rsmd.getColumnName(i + 1);
			}
			return clmns;
		} catch (SQLException e) {
			throw new KnownError("Column names couldnt be retrieved", e);
		}

	}

	private int getIncColumnIndex() {
		int ord = columnIndex++;
		if (trueFieldList != null)
			ord = trueFieldList[ord];
		return ord;
	}


}
