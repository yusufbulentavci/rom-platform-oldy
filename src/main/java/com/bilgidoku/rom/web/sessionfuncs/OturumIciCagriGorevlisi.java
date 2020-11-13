package com.bilgidoku.rom.web.sessionfuncs;

import com.bilgidoku.rom.gwt.server.common.Json;
import com.bilgidoku.rom.haber.NodeTalkMethod;
import com.bilgidoku.rom.haber.TalkResult;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.kurum.DomainName;
import com.bilgidoku.rom.pg.dict.HttpCallMethod;
import com.bilgidoku.rom.pg.dict.HttpCallServiceDeclare;
import com.bilgidoku.rom.pg.dict.hcp;
import com.bilgidoku.rom.session.OturumGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.web.http.HttpCallHandler;
import com.bilgidoku.rom.web.http.RomHttpHandler;
import com.bilgidoku.rom.web.http.session.AppHostSession;
import com.bilgidoku.rom.web.http.session.AppSession;
import com.bilgidoku.rom.web.http.session.AppSessionExtension;
import com.bilgidoku.rom.web.sessionfuncs.rtcmds.RtExchange;
import com.bilgidoku.rom.web.sessionfuncs.rtcmds.RtPresence;
import com.bilgidoku.rom.web.sessionfuncs.rtcmds.RtSay;

import net.sf.clipsrules.jni.AkilGorevlisi;

@HttpCallServiceDeclare(uri = "/_sesfuncs", name = "OturumIciCagri", paket="com.bilgidoku.rom.web.sessionfuncs")
public class OturumIciCagriGorevlisi extends GorevliDir implements HttpCallHandler {
	public static final int NO=39;
		
		public static OturumIciCagriGorevlisi tek(){
			if(tek==null) {
				synchronized (OturumIciCagriGorevlisi.class) {
					if(tek==null) {
						tek=new OturumIciCagriGorevlisi();
						tek.giris();
					}
				}
			}
			return tek;
		}
		
		static OturumIciCagriGorevlisi tek;
		private OturumIciCagriGorevlisi() {
			super("OturumIciCagri", NO);
		}

	@Override
	public void selfDescribe(JSONObject jo) {

	}

	final static private MC mc = new MC(OturumIciCagriGorevlisi.class);
	final private AppSessionExtension sessionService = (AppSessionExtension) OturumGorevlisi.tek().getExtension(AppSessionExtension.APP);

//	final static private DomainService ds = ServiceDiscovery.getService(DomainService.class);
	// private static final HostService
	// hostService=RunTime.getService(HostService.class);
	// private static final HostingFileService
	// hostFileService=ServiceDiscovery.getService(HostingFileService.class);
	// private static final MimeService
	// contentMatcherService=ServiceDiscovery.getService(MimeService.class);

	// private static final MailQueueService
	// mailQueueService=RunTime.getService(MailQueueService.class);

	//
	// @HttpCallMethod(http = "post")
	// public String upload(@hcp(n = "a_session") AppSession session,
	// @hcp(n = "f_attach") String f_attach) throws KnownError {
	// destur();
	// session.usingFile(f_attach);
	// return f_attach;
	// }

//	@Override
//	@HttpCallMethod(http = "post", roles = "user")
//	public Boolean checkdomain(@hcp(n = "p_domain") String p_domain) throws KnownError {
//		destur();
//		return ds.domainCheck(p_domain).isAvailable;
//	}

	@HttpCallMethod(http = "post")
	public String hostName(@hcp(n = "a_session") AppSession session) throws KnownError {
		destur();
		return session.getEmailDomain();
	}

	@HttpCallMethod(http = "post")
	public Json userAgent(@hcp(n = "a_session") AppSession session) throws KnownError {
		destur();
		return new Json(session.getUserAgent().toJson());
	}

	@HttpCallMethod(http = "post", roles = "user")
	public Boolean hasDomain(@hcp(n = "a_session") AppSession session) throws KnownError {
		destur();
		return !session.getEmailDomain().endsWith(DomainName.freeDomain);
	}

	private AppHostSession hostSession(AppSession session) throws KnownError {
		AppHostSession hs = sessionService.getHostSession(session.getHostId());
		return hs;
	}

	@HttpCallMethod(http = "post")
	public Boolean rtpresence(@hcp(n = "a_session") AppSession session, @hcp(n = "p_presence") String p_presence,
			@hcp(n = "p_code") Integer p_code) throws KnownError {
		session.setPresence(p_code, p_presence);

		AppHostSession hs = hostSession(session);

		hs.broadcast(new RtPresence(session.getCid(), p_code, p_presence), false);

		return true;
	}

	@HttpCallMethod(http = "post")
	public String[] rtonlines(@hcp(n = "a_session") AppSession session) throws KnownError {
		AppHostSession hs = hostSession(session);

		return hs.getOnlineCids(session.isUser());
	}

	private static final Astate _rtCidNotFound = mc.c("rtCidNotFound");

	private static final Astate _rtsay = mc.c("rtsay");

	@HttpCallMethod(http = "post")
	public Boolean rtsay(@hcp(n = "a_session") AppSession session, @hcp(n = "p_cid") String p_cid,
			@hcp(n = "p_msg") String p_msg) throws KnownError {
		destur();
		_rtsay.more();

		try {
			AppHostSession hs = hostSession(session);
			AppSession ses = hs.getSessionsByCid(p_cid);
			if (ses == null) {
				_rtCidNotFound.more();
				return false;
			}
			return ses.rtMsg(new RtSay(session.getCid(), p_cid, p_msg));
		} catch (KnownError e) {
			_rtsay.failed(e, session, p_cid, p_msg);
			throw e;
		}
	}

	private static final Astate _rtexchange = mc.c("rtExchange");

	@HttpCallMethod(http = "post")
	public Boolean rtexchange(@hcp(n = "a_session") AppSession session, @hcp(n = "p_cid") String p_cid,
			@hcp(n = "p_subcmd") String p_subcmd, @hcp(n = "p_text") String p_text, @hcp(n = "p_ext") Json p_ext)
			throws KnownError {
		destur();
		_rtexchange.more();

		try {
			AppHostSession hs = hostSession(session);
			AppSession ses = hs.getSessionsByCid(p_cid);
			if (ses == null) {
				_rtCidNotFound.more();
				return false;
			}

			JSONObject ext = (p_ext == null ? null : p_ext.getObject());
			return ses.rtMsg(new RtExchange(session.getCid(), p_cid, p_subcmd, p_text, ext));
		} catch (KnownError e) {
			_rtexchange.failed(e, session, p_cid, p_text, p_ext);
			throw e;
		}
	}

	private static final Astate _akil= mc.c("akil");

	@HttpCallMethod(http = "post")
	public Boolean akil(@hcp(n = "a_session") AppSession session, 
			@hcp(n = "p_eylem") String p_eylem,
			@hcp(n = "p_json") Json p_json)
			throws KnownError {
		destur();
		_akil.more();

		try {
			JSONObject ext = (p_json == null ? null : p_json.getObject());
			return AkilGorevlisi.tek().kisiSoyledi(session.getHostId(), session.getCint(), p_eylem, ext);
		} catch (KnownError e) {
			_akil.failed(e, session, p_eylem, p_json);
			throw e;
		}
	}
	
	
	// @HttpCallMethod(http = "post")
	// public String rtdlgcreate(
	// @hcp(n = "a_session") AppSession session,
	// @hcp(n = "p_app") String p_app,
	// @hcp(n = "p_subject") String p_subject,
	// @hcp(n = "p_isopen") Boolean p_isopen,
	// @hcp(n = "p_ispermanent") Boolean p_ispermanent,
	// @hcp(n = "p_cids") String[] p_cids
	// ) throws KnownError {
	// AppHostSession hs = hostSession(session);
	// return hs.createRtDialog(session, p_app, p_subject, p_isopen,
	// p_ispermanent, p_cids);
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
	// AppHostSession hs = hostSession(session);
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
	// AppHostSession hs = hostSession(session);
	// RtDialog dlg=getRtDialog(p_dlgid, hs);
	// String msg=dlg.nextMsg(session.getaName(), p_msg,"c");
	// return dlg.broadcastToDlg(hs, msg);
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
	// AppHostSession hs = hostSession(session);
	// RtDialog dlg=getRtDialog(p_dlgid, hs);
	// String msg=dlg.nextMsg(session.getaName(), p_x+"é"+p_y,"w");
	// return dlg.broadcastToDlg(hs, msg);
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
	// AppHostSession hs = hostSession(session);
	// RtDialog dlg=getRtDialog(p_dlgid, hs);
	// String msg=dlg.nextMsg(session.getaName(), "","u");
	// return dlg.broadcastToDlg(hs, msg);
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
	// AppHostSession hs = hostSession(session);
	// RtDialog dlg=getRtDialog(p_dlgid, hs);
	//
	// File f = RunTime.dbfs().get(session.domain.getHostId(), f_msg);
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
	// return dlg.broadcastToDlg(hs, msg);
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
	// AppHostSession hs = hostSession(session);
	// RtDialog dlg=getRtDialog(p_dlgid, hs);
	// return dlg.broadcastToDlg(hs, new
	// RtBoardErase(session.getCid()).getJo());
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

	@Override
	public RomHttpHandler getCustomService() {
		return null;
	}

	@NodeTalkMethod(cmd = "s.dlgcmd")
	public TalkResult dlgcmd(JSONObject jo) {
		sessionService.dlgCmd(jo);
		return TalkResult.success;

	}

}
