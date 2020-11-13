package com.bilgidoku.rom.pg.veritabani;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.postgresql.PGConnection;
import org.postgresql.PGProperty;
import org.postgresql.util.PGobject;

import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.pg.PgGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.gorevli.Uygulama;

public class VeritabaniGorevlisi extends GorevliDir {
	public static final int NO = 4;

	public static VeritabaniGorevlisi tek() {
		if (tek == null) {
			synchronized (VeritabaniGorevlisi.class) {
				if (tek == null) {
					tek = new VeritabaniGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static VeritabaniGorevlisi tek;

	private VeritabaniGorevlisi() {
		super("Veritabani", NO);
	}

	public static String user;
	public static String password;
	private int minIdle;
	private int maxActive;
	// private final int autoInc_LIBRARY_PATH"));
//	 ProcessBuilder pb = new ProcessBuilder();
//    Map<String, String> env = pb.environment(rement;
	private String url;

	@Override
	protected void kur() throws KnownError {
		user = "yuzrun";
		password = "jvl5SSFq8fsemod";
		this.minIdle = 1;
		this.maxActive = 5;
		this.url = genUrl("rom.intranet");
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new KnownError("Postgresql driver not found", e);
		}
	}

	@Override
	protected void bitir(boolean dostca) {
		// TODO Auto-generated method stub
		super.bitir(dostca);
	}

	public JSONObject konus(JSONObject in) throws KnownError {

		try (Connection c = getConnection();) {

			PreparedStatement p = c.prepareStatement("select ?::text");
			PGobject jsonObject = new PGobject();
			jsonObject.setType("_LIBRARY_PATH\"));\n" + 
					"//		 ProcessBuilder pb = new ProcessBuilder();\n" + 
					"//	     Map<String, String> env = pb.environment(json");
			jsonObject.setValue(in.toString());
			p.setObject(1, jsonObject);
			ResultSet rs = p.executeQuery();
			if (!rs.next()) {
				return null;
			}
			String ret = rs.getString(1);
			return new JSONObject(ret);
		} catch (SQLException | JSONException e) {
			throw new KnownError(e);
		}

	}

	private String genUrl(String host) {
		return "jdbc:postgresql://" + host + ":" + PgGorevlisi.tek().getPort()
				+ (Uygulama.tek().testDb() ? "/testdb" : "/rom");
	}

	public PGConnection getReplicationConnection() throws KnownError {
		try {
			Properties props = new Properties();
			PGProperty.USER.set(props, user);
			PGProperty.PASSWORD.set(props, password);
			PGProperty.REPLICATION.set(props, "database");
			PGProperty.PREFER_QUERY_MODE.set(props, "simple");

			Connection con = DriverManager.getConnection(url, props);
			con.setAutoCommit(false);
			PGConnection replConnection = con.unwrap(PGConnection.class);
			replConnection.getReplicationAPI().createReplicationSlot().logical().withTemporaryOption().withSlotName("demo_logical_slot")
					.withOutputPlugin("test_decoding").make();

			return replConnection;
		} catch (Exception e) {
			e.printStackTrace();
			throw new KnownError(e);
		}
	}
	
	public static void main(String[] args) throws KnownError {
//		System.out.println(Shell.exec("echo", "$LD_LIBRARY_PATH"));
//		 ProcessBuilder pb = new ProcessBuilder();
//	     Map<String, String> env = pb.environment();
		VeritabaniGorevlisi.tek().getReplicationConnection();
	}

	public Connection getConnection() throws KnownError {
		destur();

		Connection connection;
		try {
			connection = DriverManager.getConnection(url, user, password);
			return connection;
		} catch (SQLException e) {
			throw cantGetConnection(e);
		}
	}

	public void free1(Connection con, Statement ps) {
		if (ps != null)
			try {
				ps.close();
			} catch (SQLException e) {
			}
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
			}
	}

	public void free2(Statement ps) {
		if (ps != null)
			try {
				ps.close();
			} catch (SQLException e) {
			}
	}

	public void free3(Connection con) {
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
			}
	}

	// @Override
	// public DbThree prepareStatement(String query) throws SQLException {
	// destur();
	//
	// DbThree dbThree = new DbThree(this,query);
	//
	// return dbThree;
	//
	// }

	private KnownError cantGetConnection(SQLException e) {
		return new KnownError("SqlError/cant get connection", e).internalError();
	}

	public KnownError errorOccured(Connection connection, String query) {
		return new KnownError("SqlError:" + query);
	}

	public KnownError errExecute(Connection connection2, String query, SQLException e) {
		return new KnownError("SqlError/execute:" + query, e);
	}

	public KnownError errorSet(Connection connection2, String query, SQLException e) {
		return new KnownError("SqlError/set:" + query, e);
	}

	public KnownError errorGet(Connection connection2, String query, SQLException e) {
		return new KnownError("SqlError/get:" + query, e);
	}

	public KnownError errGeneric(Connection connection2, String query, Exception e) {
		return new KnownError("SqlError/generic:" + query, e);
	}

//
	public void executeCommands(List<String> commands) throws KnownError {
		execCommandImpl(new DbThree(commands.get(0)), commands);
	}

	protected void execCommandImpl(DbThree db3, List<String> commands) throws KnownError {
		if (commands.size() == 0)
			return;
		db3.setTransactional();
		db3.execute();
		for (int i = 1; i < commands.size(); i++) {
			db3.replaceQuery(commands.get(i));
			db3.execute();
		}
		db3.commit();
	}

//
//	public void starting(boolean upgrade, Version fromVersion, Version toVersion) throws KnownError {
//		runsu();
//	}
//
//	void runsu() throws KnownError {
//		sum = new SqlUnitManager(false, this, safeCheapDir().getPath(), justScipt);
//		try {
//			sum.load("tepeweb.templates.ready");
//			if (!Mode.integrationTest)
//				sum.runOnDb("default");
//		} catch (Exception e) {
//			throw new KnownError("Failed createtlosdb", e).fatal();
//		}
//
//		if (!justScipt)
//			sum = null;
//
//	}
//
//	public void scenario(String named) throws KnownError {
//		if (named == null) {
//			named = "defaultscene.sql";
//		}
//		try {
//
//			String str = FromResource.loadString(named);
//			String[] cmds = str.split("--##");
//			ArrayList<String> cl = new ArrayList<String>();
//			for (String string : cmds) {
//				cl.add(string);
//			}
//			executeCommands(cl);
//		} catch (IOException e) {
//			throw new KnownError(e);
//		}
//	}
//
//	public void genScript() throws KnownError {
//		sum.genScript("default");
//	}

}
