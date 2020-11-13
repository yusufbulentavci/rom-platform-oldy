package com.bilgidoku.rom.web.online;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.pg.dict.HttpCallServiceDeclare;
import com.bilgidoku.rom.session.OturumGorevlisi;
import com.bilgidoku.rom.web.http.session.AppSessionExtension;

class Dlg {
	Set<OnlineListener> listeners = new HashSet<OnlineListener>();

	public synchronized void add(OnlineListener l) {
		listeners.add(l);
	}

	public synchronized void comment(String dlgUri, String commentUri) {
		JSONObject jo = new JSONObject();
		try {
			jo.put("dlg", dlgUri);
			jo.put("comment", commentUri);
		} catch (JSONException e) {
		}
		for (OnlineListener it : listeners) {
			it.comment(jo);
		}
	}
}

@HttpCallServiceDeclare(uri = "/_dlgsrv", name = "Online", roles = "contact", paket="com.bilgidoku.rom.web.online")
public class OnlineGorevlisi extends GorevliDir {

	public static final int NO = 48;

	public static OnlineGorevlisi tek() {
		if (tek == null) {
			synchronized (OnlineGorevlisi.class) {
				if (tek == null) {
					tek = new OnlineGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}

	static OnlineGorevlisi tek;

	private OnlineGorevlisi() {
		super("Online", NO);
	}

	private AppSessionExtension sessionService;
	private Map<String, Dlg> dlgs = new ConcurrentHashMap<String, Dlg>();
	
	@Override
	public void kur() {
		sessionService = (AppSessionExtension) OturumGorevlisi.tek().getExtension(AppSessionExtension.APP);
	}




	@Override
	public void selfDescribe(JSONObject jo) {
		jo.safePut("dlgs", dlgs.size());
	}


	public void listen(int hostId, String dlgUri, OnlineListener l) {
		String id = id(hostId, dlgUri);
		Dlg dlg = getCreateDlg(id);
		dlg.add(l);
	}

	private Dlg getCreateDlg(String id) {
		Dlg dlg = getDlg(id);
		if (dlg != null)
			return dlg;
		dlg = new Dlg();
		dlgs.put(id, dlg);
		return dlg;
	}

	private Dlg getDlg(String id) {
		return dlgs.get(id);
	}

	private String id(int hostId, String dlgUri) {
		return hostId + "@" + dlgUri;
	}


	public void comment(int hostId, String dlgUri, String commentUri) {
		Dlg dlg = getDlg(id(hostId, dlgUri));
		dlg.comment(dlgUri, commentUri);
	}
	//
	//
	//
	// @HttpCallMethod(http = "post")
	// public String rtdlgcreate(
	// @hcp(n = "a_session") AppSession session,
	// @hcp(n = "p_app") String p_app,
	// @hcp(n = "p_subject") String p_subject,
	// @hcp(n = "p_isopen") Boolean p_isopen,
	// @hcp(n = "p_ispermanent") Boolean p_ispermanent,
	// @hcp(n = "p_users") String[] p_users,
	// @hcp(n = "p_cids") String[] p_cids
	// ) throws KnownError {
	// AppHostSession
	// hs=sessionService.getAppHostSession(session.domain.getHostId());
	// return hs.createRtDialog(session, p_app, p_subject, p_isopen,
	// p_ispermanent, p_users, p_cids);
	// }
	//
	// private static final Astate _rtdlgdesk=mc.c("rtdlgdesk");
	//
	//
	// @HttpCallMethod(http = "post")
	// public String rtdlgdesk(
	// @hcp(n = "a_session") AppSession session,
	// @hcp(n = "p_subject") String p_subject
	// ) throws KnownError {
	// destur();
	// _rtdlgdesk.more();
	// try {
	// AppHostSession
	// hs=sessionService.getAppHostSession(session.domain.getHostId());
	// RomUser du = hs.getDeskUser();
	// if(du==null){
	// throw new KnownError();
	// }
	// String[] users = new String[]{du.getName()};
	// return hs.createRtDialog(session, "im", p_subject, false, false, users,
	// null);
	// } catch (KnownError e) {
	// _rtdlgdesk.fail(session,p_subject);
	// throw e;
	// }
	// }
	//
	// private static final Astate _rtdlgmsg=mc.c("rtdlgmsg");
	//
	//
	// @HttpCallMethod(http = "post")
	// public Integer rtdlgmsg(
	// @hcp(n = "a_session") AppSession session,
	// @hcp(n = "p_dlgid") String p_dlgid,
	// @hcp(n = "p_msg") String p_msg
	// ) throws KnownError {
	// destur();
	// _rtdlgmsg.more();
	//
	// try {
	// AppHostSession
	// hs=sessionService.getAppHostSession(session.domain.getHostId());
	// RtDialog dlg=getRtDialog(p_dlgid, hs);
	// String msg=dlg.nextMsg(session.getaName(), p_msg,"c");
	// return broadcastToDlg(hs, dlg, msg);
	// } catch (KnownError e) {
	// _rtdlgmsg.failed(e,session,p_dlgid,p_msg);
	// throw e;
	// }
	// }
	// private static final Astate _rtdlgmark=mc.c("rtdlgmark");
	//
	// @HttpCallMethod(http = "post")
	// public Integer rtdlgmark(
	// @hcp(n = "a_session") AppSession session,
	// @hcp(n = "p_dlgid") String p_dlgid,
	// @hcp(n = "p_x") Integer p_x,
	// @hcp(n = "p_y") Integer p_y
	// ) throws KnownError {
	// destur();
	// _rtdlgmark.more();
	//
	// try {
	// AppHostSession
	// hs=sessionService.getAppHostSession(session.domain.getHostId());
	// RtDialog dlg=getRtDialog(p_dlgid, hs);
	// String msg=dlg.nextMsg(session.getaName(), p_x+"é"+p_y,"w");
	// return broadcastToDlg(hs, dlg, msg);
	// } catch (KnownError e) {
	// _rtdlgmark.failed(e,session,p_dlgid,p_x,p_y);
	// throw e;
	// }
	// }
	//
	// private static final Astate _rtdlgunmark=mc.c("rtdlgunmark");
	//
	// @HttpCallMethod(http = "post")
	// public Integer rtdlgunmark(
	// @hcp(n = "a_session") AppSession session,
	// @hcp(n = "p_dlgid") String p_dlgid
	// ) throws KnownError {
	// destur();
	//
	// _rtdlgunmark.more();
	// try {
	// AppHostSession
	// hs=sessionService.getAppHostSession(session.domain.getHostId());
	// RtDialog dlg=getRtDialog(p_dlgid, hs);
	// String msg=dlg.nextMsg(session.getaName(), "","u");
	// return broadcastToDlg(hs, dlg, msg);
	// } catch (KnownError e) {
	// _rtdlgunmark.failed(e,session,p_dlgid);
	// throw e;
	// }
	// }
	//
	// static long maxfilelen=10*1024*1024;
	// private static final Astate _rtdlgboardmime=mc.c("rtdlgboardmime");
	//
	// @HttpCallMethod(http = "post")
	// public Integer rtdlgboardmime(
	// @hcp(n = "a_session") AppSession session,
	// @hcp(n = "p_dlgid") String p_dlgid,
	// @hcp(n = "f_msg") String f_msg
	// ) throws KnownError {
	// destur();
	// _rtdlgboardmime.more();
	//
	// try {
	// AppHostSession
	// hs=sessionService.getAppHostSession(session.getDomain().getHostId());
	// RtDialog dlg=getRtDialog(p_dlgid, hs);
	//
	// File f = RunTime.dbfs().get(session.getDomain().getHostId(), f_msg);
	// String mime=contentMatcherService.getMimeOfFile(f.getName());
	//
	// long len = f.length();
	// if(f.length()>maxfilelen){
	// throw new KnownError().badRequest();
	// }
	//
	// try(FileInputStream fr=new FileInputStream(f)){
	// byte[] all=new byte[(int) f.length()];
	// int k=fr.read(all);
	// if(k!=f.length())
	// throw new KnownError().badRequest();
	// String msg="data:"+mime+";base64,"+Base64.encodeBase64String(all);
	// msg=dlg.nextMsg(session.getaName(), msg,"b");
	// return broadcastToDlg(hs, dlg, msg);
	// } catch (IOException e) {
	// throw new KnownError(e).badRequest();
	// }
	// } catch (KnownError e) {
	// _rtdlgboardmime.failed(e,session,p_dlgid,f_msg);
	// throw e;
	// }
	// }
	// private static final Astate _rtdlgboarderase=mc.c("rtdlgboarderase");
	//
	// @HttpCallMethod(http = "post")
	// public Integer rtdlgboarderase(
	// @hcp(n = "a_session") AppSession session,
	// @hcp(n = "p_dlgid") String p_dlgid
	// ) throws KnownError {
	// destur();
	// _rtdlgboarderase.more();
	//
	// try {
	// AppHostSession
	// hs=sessionService.getAppHostSession(session.getDomain().getHostId());
	// RtDialog dlg=getRtDialog(p_dlgid, hs);
	// String msg=dlg.nextMsg(session.getaName(), "","e");
	// return broadcastToDlg(hs, dlg, msg);
	// } catch (KnownError e) {
	// _rtdlgboarderase.failed(e,session,p_dlgid);
	// throw e;
	// }
	// }
	//
	// private static final Astate _getRtDialog=mc.c("getRtDialog");
	//
	// private RtDialog getRtDialog(String p_dlgid, AppHostSession hs) throws
	// KnownError{
	// _getRtDialog.more();
	// RtDialog d = hs.getRtDialog(p_dlgid);
	// if(d==null){
	// _getRtDialog.fail(p_dlgid,hs);
	// throw new KnownError().badRequest();
	// }
	// return d;
	// }
	//
	// private static final Astate _rtdlgsay=mc.c("rtdlgsay");
	//
	// @HttpCallMethod(http = "post")
	// public Integer rtdlgsay(
	// @hcp(n = "a_session") AppSession session,
	// @hcp(n = "p_dlgid") String p_dlgid,
	// @hcp(n = "p_msg") String p_msg
	// ) throws KnownError {
	// destur();
	// _rtdlgsay.more();
	//
	// try {
	// AppHostSession
	// hs=sessionService.getAppHostSession(session.getDomain().getHostId());
	// RtDialog dlg=getRtDialog(p_dlgid, hs);
	// String msg=dlg.nextMsg(session.getaName(), p_msg,"m");
	// return broadcastToDlg(hs, dlg, msg);
	// } catch (KnownError e) {
	// _rtdlgsay.failed(e,session,p_dlgid,p_msg);
	// throw e;
	// }
	// }
	//
	// private int broadcastToDlg(AppHostSession hs, RtDialog dlg, String msg) {
	// int i=0;
	// for (String sid : dlg.sids) {
	// AppSession s=hs.getSessionsBySid(sid);
	// if(s==null)
	// continue;
	// s.rtMsg(dlg.getResource(), dlg.getSubject(), msg);
	// i++;
	// }
	// for (String sid : dlg.users) {
	// AppSession s=hs.getSessionsByUserName(sid);
	// if(s==null)
	// continue;
	// s.rtMsg(dlg.getResource(), dlg.getSubject(), msg);
	// i++;
	// }
	// for (String sid : dlg.cids) {
	// AppSession s=hs.getSessionsByCid(sid);
	// if(s==null)
	// continue;
	// s.rtMsg(dlg.getResource(), dlg.getSubject(), msg);
	// i++;
	// }
	// return i;
	// }
	//

}
