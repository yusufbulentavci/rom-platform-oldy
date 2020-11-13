package com.bilgidoku.rom.pg.veritabani;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.postgresql.PGConnection;
import org.postgresql.PGProperty;
import org.postgresql.replication.PGReplicationStream;

import com.bilgidoku.rom.shared.err.KnownError;

public class Testi {
	public static PGConnection getReplicationConnection() throws KnownError {
		try {
			String user = "yuzrun";
			String password = "jvl5SSFq8fsemod";
			Properties props = new Properties();
			PGProperty.USER.set(props, user);
			PGProperty.PASSWORD.set(props, password);
			PGProperty.REPLICATION.set(props, "database");
			PGProperty.PREFER_QUERY_MODE.set(props, "simple");
			PGProperty.ASSUME_MIN_SERVER_VERSION.set(props, "9.4");

			Connection con = DriverManager.getConnection("jdbc:postgresql://rom.intranet:5445/testdb", props);
			PGConnection replConnection = con.unwrap(PGConnection.class);
			replConnection.getReplicationAPI().createReplicationSlot().logical().withTemporaryOption()
					.withSlotName("test_slot").withOutputPlugin("wal2json").make();
			PGReplicationStream stream =
			        replConnection.getReplicationAPI()
			            .replicationStream()
			            .logical()
			            .withSlotName("test_slot")
			            .withStatusInterval(20, TimeUnit.SECONDS)
			            .start();

			
			while (true) {
			      //non blocking receive message
			      ByteBuffer msg = stream.readPending();

			      if (msg == null) {
			        TimeUnit.MILLISECONDS.sleep(10L);
			        continue;
			      }

			      int offset = msg.arrayOffset();
			      byte[] source = msg.array();
			      int length = source.length - offset;
			      System.out.println(new String(source, offset, length));

			      //feedback
			      stream.setAppliedLSN(stream.getLastReceiveLSN());
			      stream.setFlushedLSN(stream.getLastReceiveLSN());
			    }
		} catch (Exception e) {
			e.printStackTrace();
			throw new KnownError(e);
		}
	}

	public static void main(String[] args) throws KnownError {
		Testi.getReplicationConnection();
//		System.out.println(Shell.exec("echo", "$LD_LIBRARY_PATH"));
//		 ProcessBuilder pb = new ProcessBuilder();
//	     Map<String, String> env = pb.environment();
	}
}
