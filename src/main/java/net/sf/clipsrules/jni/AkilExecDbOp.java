package net.sf.clipsrules.jni;

import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.veritabani.DbOp;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.shared.err.KnownError;

public class AkilExecDbOp extends DbOp{
	private static final MC mc=new MC(AkilExecDbOp.class);
	

//	rom.contacts_cidbyemail(
//			a_host int, p_email text
//		)
//	private static final String query="select * "
//			+ "from rom.contacts_cidbyemail(?, ?)";
	

	public void doit(String query) throws KnownError{
		System.out.println(query);
		try(DbThree db3=new DbThree(query)){
			db3.execute();
		}
	}
	
}
