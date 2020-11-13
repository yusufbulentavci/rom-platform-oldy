package com.bilgidoku.rom.pg.aynilama;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.postgresql.PGConnection;
import org.postgresql.PGProperty;
import org.postgresql.replication.PGReplicationStream;

import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.pg.sqlunit.model.Table;
import com.bilgidoku.rom.shared.err.KnownError;

import net.sf.clipsrules.jni.AkilGorevlisi;

public class ReplikeEt implements Runnable {

	private String url;
	private Map<String, Table> clipsTables;

	public ReplikeEt(String url, Map<String, Table> clipsTables) {
		this.url = url;
		this.clipsTables = clipsTables;
	}

	@Override
	public void run() {
		try {
			String user = "yuzrun";
			String password = "jvl5SSFq8fsemod";
			Properties props = new Properties();
			PGProperty.USER.set(props, user);
			PGProperty.PASSWORD.set(props, password);
			PGProperty.REPLICATION.set(props, "database");
			PGProperty.PREFER_QUERY_MODE.set(props, "simple");
			PGProperty.ASSUME_MIN_SERVER_VERSION.set(props, "9.4");

//			Connection con = DriverManager.getConnection("jdbc:postgresql://rom.intranet:5445/testdb", props);
			Connection con = DriverManager.getConnection(url, props);

			PGConnection replConnection = con.unwrap(PGConnection.class);
			replConnection.getReplicationAPI().createReplicationSlot().logical().withTemporaryOption()
					.withSlotName("aynilama").withOutputPlugin("wal2json").make();
			// https://github.com/eulerto/wal2json#parameters
			PGReplicationStream stream = replConnection.getReplicationAPI().replicationStream().logical()
					.withSlotOption("add-tables", "bilgi.*")
//			            .withSlotOption("filter-tables", "bilgi.*,yurt.*")
					.withSlotName("aynilama").withStatusInterval(20, TimeUnit.SECONDS).start();

			while (true) {
				// non blocking receive message
				ByteBuffer msg = stream.readPending();

				if (msg == null) {
					TimeUnit.MILLISECONDS.sleep(10L);
					continue;
				}

				int offset = msg.arrayOffset();
				byte[] source = msg.array();
				int length = source.length - offset;
				String str = new String(source, offset, length);
				System.out.println(str);
				JSONObject obj = new JSONObject(str);
				bildir(obj);
				// feedback
				stream.setAppliedLSN(stream.getLastReceiveLSN());
				stream.setFlushedLSN(stream.getLastReceiveLSN());
			}
		} catch (Exception e) {
			e.printStackTrace();
//			throw new KnownError(e);
		}
	}

	private void bildir(JSONObject obj) {
		try {
			JSONArray changes = obj.getJSONArray("change");
			for (int i = 0; i < changes.length(); i++) {
				JSONObject c = changes.getJSONObject(i);
				String kind = c.getString("kind");
				String schema = c.getString("schema");
				String table = c.getString("table");
				if (kind.equals("delete")) {
					deleteOp(c, table);
//					 String query = "(find-fact " + 
//		                        "((" + variable + " " + deftemplate + ")) " + condition + ")";
//		                        
//		      PrimitiveValue pv = eval(query);
//		      
//		      if (! pv.isMultifield())
//		        { return null; }
//		      
//		      MultifieldValue mv = (MultifieldValue) pv;
//		     
//		      if (mv.size() == 0)
//		        { return null; }
//		        
//		      return (FactAddressValue) mv.get(0);
					return;
				}
				if (kind.equals("insert") || kind.equals("update")) {
					JSONArray columnNames = c.getJSONArray("columnnames");
					JSONArray columnValues = c.getJSONArray("columnvalues");
					JSONArray columnTypes = c.getJSONArray("columntypes");
					if (kind.equals("insert")) {
						insertOp(table, columnNames, columnValues);
					} else {
						JSONObject oldKeys=c.getJSONObject("oldkeys");
						JSONArray keyValues = oldKeys.getJSONArray("keyvalues");
						modifyOp(table, keyValues, columnNames, columnValues);
						// deleteOp(c, table);
//						insertOp(table, columnNames, columnValues);
//{"change":[{"kind":"update","schema":"bilgi","table":"menu","columnnames":["id","ust"],"columntypes":["text","text"],"columnvalues":["kim","ustum1"],"oldkeys":{"keynames":["id"],"keytypes":["text"],"keyvalues":["kim"]}}]}
					}
				}
			}
		} catch (JSONException | KnownError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

	}

	private void modifyOp(String table, JSONArray keyValues, JSONArray fieldNames, JSONArray vals) throws KnownError {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("(do-for-fact ");
			sb.append("((");
			sb.append("?row ");
			sb.append(table);
			sb.append(")) ");
			sb.append("(eq ?row:id ");
			sb.append(keyValues.get(0).toString());
			sb.append(") ");
			sb.append("(modify ?row ");
			for (int i = 0; i < fieldNames.length(); i++) {
				if (fieldNames.getString(i).equals("id"))
					continue;
//				if (vals.get(i).equals(oldVals.get(i)))
//					continue;
				sb.append("(");
				sb.append(fieldNames.getString(i));
				sb.append(" ");
				sb.append(vals.getString(i));
				sb.append(") ");
			}
			sb.append(") )");
			System.out.println("UPDATE:" + sb.toString());
			AkilGorevlisi.tek().modifyById(sb.toString());
		} catch (JSONException e) {
			throw new KnownError("modifyOp:", e);
		}
	}

	private void insertOp(String table, JSONArray columnNames, JSONArray columnValues)
			throws JSONException, KnownError {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(table);
		sb.append(" ");
		for (int j = 0; j < columnNames.length(); j++) {
			sb.append("(");
			sb.append(columnNames.getString(j));
			sb.append(" ");
			sb.append(columnValues.getString(j));
			sb.append(") ");
			sb.append(" ");
		}
		sb.append(" )");
		AkilGorevlisi.tek().addFact(sb.toString());
	}

	private void deleteOp(JSONObject c, String table) throws JSONException, KnownError {
		JSONObject oldkeys = c.getJSONObject("oldkeys");
		JSONArray keyvalues = oldkeys.getJSONArray("keyvalues");
		JSONArray keytypes = oldkeys.getJSONArray("keytypes");
		AkilGorevlisi.tek().deleteById(table, quote(keytypes.getString(0), keyvalues.getString(0)));
	}

	private String quote(String type, String val) {
//		if(type.equals("text"))
//			return "\""+val+"\"";
		return val;
	}

}
