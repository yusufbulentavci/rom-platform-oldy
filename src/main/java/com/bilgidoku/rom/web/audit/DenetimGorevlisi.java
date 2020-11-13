package com.bilgidoku.rom.web.audit;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.bilgidoku.rom.gwt.shared.AuditItem;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.dict.HttpCallMethod;
import com.bilgidoku.rom.pg.dict.HttpCallServiceDeclare;
import com.bilgidoku.rom.pg.dict.hcp;
import com.bilgidoku.rom.run.KosuGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;

@HttpCallServiceDeclare(uri = "/_audit", name = "Denetim", paket="com.bilgidoku.rom.web.audit")
public class DenetimGorevlisi extends GorevliDir implements Runnable{
	public static final int NO = 44;

	public static DenetimGorevlisi tek() {
		if (tek == null) {
			synchronized (DenetimGorevlisi.class) {
				if (tek == null) {
					tek = new DenetimGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static DenetimGorevlisi tek;

	private DenetimGorevlisi() {
		super("Denetim", NO);
	}
	
	
	
	private final BlockingQueue<AuditItem> queue = new LinkedBlockingQueue<AuditItem>();
	
	

	public void selfDescribe(JSONObject jo) {
		jo.safePut("queuesize", queue.size());
	}
	
	@Override
	public void kur(){
		KosuGorevlisi.tek().thread(this, "audit-writer");
	}


	public void write(int hostId, String cid, String method, String uri, String[] fieldNames, String[] fieldValues) throws KnownError {
		destur();
		queue.add(new AuditItem(hostId, cid, method, uri, fieldNames, fieldValues));
	}

	@Override
	public void run() {
		CreateAuditDbOp dbop=new CreateAuditDbOp();
		while (mayi()) { // Safety while
			try { // Catch unexpected exception
				while (mayi()) { // Per message queue

					if (!mayi()) { // Returns only if system is going
												// down
						return;
					}

					AuditItem cmd = pop();
					if (cmd == null) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
						}
						continue;
					}
					dbop.doit(cmd);
					
				}
			} catch (Exception e) {
				Sistem.printStackTrace(e,"Unexpected auditservice run error(ignoring):");
			}
		}
	}
	

	public AuditItem pop() {
		while (mayi()) {
			try {
				AuditItem l = null;
				while (l == null){
					l = queue.take();
				}
				return l;
			} catch (InterruptedException e) {
			}
		}
		return null;
	}


	
	@HttpCallMethod(http = "post", roles="admin")
	public AuditItem[] list(@hcp(n = "a_host") Integer a_host, 
			@hcp(n = "p_fromtime") Long p_fromtime, @hcp(n = "p_totime") Long p_totime,
			@hcp(n = "p_cid") String p_cid) throws KnownError {
		
		List<AuditItem> ws = new ListAuditDbOp().doit(a_host, p_fromtime, p_totime, p_cid);

		AuditItem[] ret=ws.toArray(new AuditItem[ws.size()]);
		return ret;
	}
}
