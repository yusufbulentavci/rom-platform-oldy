package com.bilgidoku.rom.mail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.stringtemplate.v4.STErrorListener;
import org.stringtemplate.v4.misc.STMessage;

import com.bilgidoku.rom.epostatemel.javam.mail.MessagingException;
import com.bilgidoku.rom.epostatemel.javam.mail.Session;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.AddressException;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.AttachDeal;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.MailAddress;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.MimeMessage;
import com.bilgidoku.rom.epostatemel.javam.rom.JsonMime;
import com.bilgidoku.rom.gunluk.GunlukGorevlisi;
import com.bilgidoku.rom.gunluk.LogCmds;
import com.bilgidoku.rom.gunluk.LogParams;
import com.bilgidoku.rom.haber.HaberGorevlisi;
import com.bilgidoku.rom.haber.NodeTalkMethod;
import com.bilgidoku.rom.haber.TalkResult;
import com.bilgidoku.rom.haber.TalkUtil;
import com.bilgidoku.rom.ilk.gorevli.GorevliDir;
import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.ilk.util.Bmap;
import com.bilgidoku.rom.ilk.util.Genel;
import com.bilgidoku.rom.ilk.util.HostingUtils;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.kurum.KurumGorevlisi;
import com.bilgidoku.rom.mail.dbop.ContactEmailRel;
import com.bilgidoku.rom.mail.dbop.MailDelivered;
import com.bilgidoku.rom.mail.dbop.MailDestroy;
import com.bilgidoku.rom.mail.dbop.MailGet;
import com.bilgidoku.rom.mail.dbop.MailList;
import com.bilgidoku.rom.mail.maildo.LocalDelivery;
import com.bilgidoku.rom.mail.maildo.RemoteDelivery;
import com.bilgidoku.rom.mail.maildo.ToLog;
import com.bilgidoku.rom.mail.match.RecipientIsLocal;
import com.bilgidoku.rom.mail.match.RecipientIsToPostmaster;
import com.bilgidoku.rom.mail.send.Delivery;
import com.bilgidoku.rom.mail.template.MailTemplating;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.pg.dbfs.DbfsGorevlisi;
import com.bilgidoku.rom.pg.dict.RomDomain;
import com.bilgidoku.rom.run.KosuGorevlisi;
import com.bilgidoku.rom.run.timer.EveryHour;
import com.bilgidoku.rom.secure.GuvenlikGorevlisi;
import com.bilgidoku.rom.session.OturumGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;


/**
 * State: matchers: recipientIsLocal: recipientIsPostmaster:
 * 
 * mailActions: localDelivery: remoteDelivery: ToLog:
 * 
 * mailTemplating:
 * 
 * mailAddress: bouncer: tlosBot:
 * 
 * remoteDeliverHourLimit: remoteDeliverLimiting:
 * 
 * Use: dbfsService: talkService: logService: secureServicpublic static final int MAILER_TLOS = 0;
	public static final int MAILER_DOMAIN_BOT = 1;
	public static final int MAILER_BOUNCE = 2;e: runService:
 * hostService: sessionService:
 * 
 * Todo: postmaster handling
 * 
 * Procedure: mail process: for every recipient: if local delivery -> deliver
 * locally else if mail has no username/ not auth -> alert on guardian, ignore
 * mail else remote delivery
 * 
 * user send mail:
 * MailOnDb+AttachDeal--JsonMime-->MimeMessage--dbfsService-->mail json
 * object--TalkUtil-->cmd json object--talkService
 * 
 * 
 * listInbox: select * from rom.mails_list()
 * 
 * storeMail: from recipient email; get domain/host, get user mailbox=case spam;
 * ContactEmailRel then spam else inbox dbfs symlink for newly learned host id
 * write db mailbox
 *
 * deleteMail: from db
 * 
 * remoteDeliver: email-->email json object--talkutil-->email deliver.remote
 * command-->talkService
 * 
 * log: special postmaster log containing to and email name
 * 
 * deliveryFailed: if no sender or sender equals bouncer, ignore create mail
 * summary send via template
 * 
 * sendViaTemplate:
 * parameters--MailTemplating-->MimeMessage--dbfsService-->Email--TalkUtil-->
 * mail.process cmd-->talkService
 * 
 * sendSystemMail: parameters--Resolve local address-->senpublic static final int MAILER_TLOS = 0;
	public static final int MAILER_DOMAIN_BOT = 1;
	public static final int MAILER_BOUNCE = 2;dViaTemplate
 * 
 * deliverremote: has total hour limit if over returns retry create emailimpl
 * delivery->deliver(GuvenlikGorevlisi.tek().getDkim)
 * 
 * 
 * @author avci
 */
public class EpostaGorevlisi extends GorevliDir
		implements MailProcessContext, STErrorListener, EveryHour {
	public static final int NO=24;

	public static EpostaGorevlisi tek(){
		if(tek==null) {
			synchronized (EpostaGorevlisi.class) {
				if(tek==null) {
					tek=new EpostaGorevlisi();
					tek.giris();
				}
			}
		}
		return tek;
	}
	
	static EpostaGorevlisi tek;
	
	public EpostaGorevlisi() {
		super("Eposta", NO);
		remoteDeliverHourLimit = 200;
		try {
			bouncer = new MailAddress("bounce", Genel.getHostName());
			tlosBot = new MailAddress("rombot", "tlos.net");
			// sysmailer = new MailAddress("bot",
			// RunTime.one.getInternetDomainName());
		} catch (AddressException e) {
			throw new RuntimeException("Invalid bounce address", e);
		}
	}
	
	@Override
	protected void kur() throws com.bilgidoku.rom.shared.err.KnownError {
		KosuGorevlisi.tek().waitHour(this);

	}
	
	public static final int MAILER_TLOS = 0;
	public static final int MAILER_DOMAIN_BOT = 1;
	public static final int MAILER_BOUNCE = 2;
	
	private static final MC mc = new MC(EpostaGorevlisi.class);

	
	private final RecipientIsLocal isLocal = new RecipientIsLocal(this);
	private final RecipientIsToPostmaster isToPostmaster = new RecipientIsToPostmaster(this);

	private final LocalDelivery localDelivery = new LocalDelivery(this);
	private final RemoteDelivery remoteDelivery = new RemoteDelivery(this, null);
	private final ToLog toLog = new ToLog(this, "postmaster");

	private final MailTemplating templating = new MailTemplating();

	private final MailAddress bouncer;
	private final MailAddress tlosBot;
	private int remoteDeliverHourLimit;



	@Override
	public void selfDescribe(JSONObject jo) {

	}

	private static final Astate process = mc.c("process");
	private static final Astate fail = mc.c("fail");

	@NodeTalkMethod(cmd = "mail.process")
	public TalkResult process(JSONObject jo) {
		EmailImpl mail;
		try {
			process.more();
			mail = new EmailImpl(TalkUtil.data(jo));

			checkPostmaster(mail);

			return TalkResult.success;

		} catch (JSONException e) {
			fail.more("mail process:" + jo.toString(), e);
			return TalkResult.failed;
		} catch (KnownError e) {
			fail.more("mail process:" + jo.toString(), e);
			if (e.isTemporary())
				return TalkResult.retry;
			return TalkResult.failed;
		}
	}

	@NodeTalkMethod(cmd = "mail.process", err = true)
	public TalkResult processerr(JSONObject jo) {
		// EmailImpl mail;
		// try {
		// mail = new EmailImpl(Talk.data(jo));
		// checkLocalDelivery(mail);
		// return TalkResult.success;
		//
		// } catch (JSONException e) {
		// com.bilgidoku.rom.gunluk.Sistem.errln("mail process:" +
		// jo.toString());
		// Sistem.printStackTrace(e);
		// return TalkResult.failed;
		// } catch (KnownError e) {
		// e.report("mail.process:" + jo.toString());
		// if (e.isTemporary())
		// return TalkResult.retry;
		// return TalkResult.failed;
		// }
		return TalkResult.success;
	}

	public void checkPostmaster(Email mail) throws KnownError {

		Collection<MailAddress> matched = isToPostmaster.match(mail);
		if (matched != null && matched.size() > 0) {
			if (mail.getRecipients().size() == matched.size()) {
				toLog.service(mail);
			} else {
				mail.getRecipients().removeAll(matched);
				checkLocalDelivery(mail);
				Email newMail = new EmailImpl(mail.getJson());
				newMail.setRecipients(matched);
				toLog.service(newMail);
			}
		} else {
			checkLocalDelivery(mail);
		}

	}

	private static final Astate _localDelivery = mc.c("local-delivery");
	private static final Astate _remoteDelivery = mc.c("remote-delivery");

	public void checkLocalDelivery(Email mail) throws KnownError {

		Collection<MailAddress> matched = isLocal.match(mail);
		if (matched != null && matched.size() > 0) {
			if (mail.getRecipients().size() == matched.size()) {
				localDelivery.service(mail);
				_localDelivery.more();
			} else {
				mail.getRecipients().removeAll(matched);
				remoteDelivery.service(mail);
				_remoteDelivery.more();
				Email newMail = new EmailImpl(mail.getJson());
				newMail.setRecipients(matched);
				localDelivery.service(newMail);
				_localDelivery.more();
			}
		} else {
			remoteDelivery.service(mail);
			_remoteDelivery.more();
		}

	}

	public void createDomain(int hostId, String hostName) {
		// TODO Auto-generated method stub

	}

	public void mailSubscription(int hostId, String adminName) {
		KurumGorevlisi.tek().host(hostId);
	}

	private static final Astate _userSendMail = mc.c("user-send-mail");

	public void userSendMail(Integer hostId, String userName, String uri, String sender, String rid) throws KnownError {
		try {
			_userSendMail.more();
			MailOnDb mo = new MailGet().get(hostId, uri);
			AttachDeal aa = new AttachAdderDbfs(hostId, mo.dbfs);
			// MimeMessage mm = new MimeMessage(aa, mo.mime);
			MimeMessage mm = JsonMime.toMime(aa, mo.mime);
			String k = DbfsGorevlisi.tek().make(0, mm);
			DbfsGorevlisi.tek().symlink(k, hostId);

			JSONObject mail = EmailImpl.createJson(hostId, userName, sender, mo.attachMime(k), mo.mime,
					Genel.getHostName(), Genel.getIp(), rid);

			JSONObject msg = TalkUtil.m(hostId, "mail.process", mail);
			HaberGorevlisi.tek().send(msg, rid);
		} catch (JSONException e) {
			_userSendMail.fail(e);
			throw new KnownError(e);
		}
	}

	private static final Astate _listInbox = mc.c("list-inbox");

	public List<MailOnDb> listInbox(int hostIdIntra, String userName) throws KnownError {
		_listInbox.more();
		return new MailList().list(hostIdIntra, "/_/mails/" + userName + "/inbox");
	}

	@Override
	public void sendMail(Email mail) throws KnownError {
		Sistem.errln("Send Mail not implemented yet");
	}

	@Override
	public MailAddress getPostmaster() {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public Set<MailAddress> getMailServers(String domain) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	private static final Astate _isLocalEmail = mc.c("is-local-email");

	@Override
	public boolean isLocalEmail(MailAddress recipient) throws KnownError {
		return KurumGorevlisi.tek().containsDomain(recipient.getDomain());
	}

	private static final Astate _storeMail = mc.c("store-mail");

	@Override
	public void storeMail(MailAddress recipient, Email mail) throws KnownError {
		_storeMail.more();
		// Find hostId and user for recipient
		RomDomain domain = KurumGorevlisi.tek().getDomain(recipient.getDomain());
		if (domain == null) {
			throw new KnownError("Unexpected!! Domain not found for:" + recipient.toString()).internalError();
		}

		String userName = recipient.getLocalPart();
		String cid = OturumGorevlisi.tek().getCidByUserName(domain, userName);
		if (cid == null) {
			_storeMail.fail("cid not found", userName, domain);
			throw new KnownError("User:" + recipient.getLocalPart() + " not found in domain:" + domain.getEmailDomain())
					.notFound(recipient.toString());
		}

		String mailbox;

		Integer rel = new ContactEmailRel().get(domain.getIntra(), cid, mail.getSender().toString());
		if (rel != null && rel == 0) {
			mailbox = MailUtils.getSpam(userName);
		} else {
			mailbox = MailUtils.getInbox(userName);
		}
		// JsonMime.blindCopy(jo);

		// Create or get symlink for common dbfs
		mail.symLink(domain.getIntra());

		// Add mail to db mailbox
		String mailUri = new MailDelivered().deliver(domain.getIntra(), mailbox, cid, mail.getMimeJson(), mail.dbfs(), mail.getRemoteHost(), mail.getRemoteAddr());

		GunlukGorevlisi.tek().response(mail.getRequestId(), true, "stored");
		// Find and notify user session for new mail
	}

	private static final Astate _remoteDeliver = mc.c("remoteDeliver");

	@Override
	public void remoteDeliver(Email mail) throws KnownError {
		try {
			_remoteDeliver.more();
			JSONObject jo = mail.getJson();
			HaberGorevlisi.tek().send(TalkUtil.m(0, "mail.deliverremote", jo), mail.getRequestId());
		} catch (JSONException e) {
			_remoteDeliver.fail(e);
			throw new KnownError(e);
		}
	}

	@Override
	public void log(String to, Email mail) {
		GunlukGorevlisi.tek().log(LogCmds.postmaster, true, 0, LogParams.str, "to:" + to + " name:" + mail.getName());
	}

	private static final Astate _deliveryFailed = mc.c("deliveryFailed");

	@Override
	public void deliveryFailed(Email mail, Collection<MailAddress> rcpts) {
		_deliveryFailed.more();
		if (mail.getSender() == null || mail.getSender().equals(bouncer)) {
			_deliveryFailed.fail(mail.getName() + " has no sender or sender equals bouncer; ignored.");
			return;
		}

		try {
			StringBuilder sb = new StringBuilder();
			mail.summary(sb);
			// mail.getInputMessage().summary(sb);
			Map<String, Object> params = new Bmap().key("summary").val(sb.toString()).key("reason").val("").ret();

			ArrayList<MailAddress> frcpts = new ArrayList<MailAddress>(rcpts);

			ArrayList<MailAddress> al = new ArrayList<MailAddress>();
			al.add(mail.getSender());

			sendSystemMail("en", "DeliveryFailed", mail.getSender().toString(),
					new Bmap().key("summary").val(sb.toString()).key("failedrecs").val(frcpts).ret(), MAILER_BOUNCE,
					null, mail.getRequestId());
			GunlukGorevlisi.tek().response(mail.getRequestId(), false, "deliveryfailed");
		} catch (KnownError e) {
			_deliveryFailed.fail(e, "Failed to make delivery failed response.");
		}

	}

	private static final Astate notificationMail = mc.c("notification.mail");

	public void sendNotificationMail(int hostId, String cid, String app, String code, JSONArray inref, int times,
			JSONArray titles, JSONArray users) {
		notificationMail.more();

		try {
			RomDomain dom = KurumGorevlisi.tek().getDomain(HostingUtils.hostIdIntra(hostId));
			if (dom == null) {
				notificationMail.failed("Domain not found, HostId", hostId);
				return;
			}

			String sender = "postmaster@" + dom.getEmailDomain();

			String domainName = dom.getDomainName();
			String email = OturumGorevlisi.tek().getContactEmail(hostId, cid);
			if (email == null) {
				notificationMail.failed("Email not found; HostId,Cid", hostId, cid);
				return;
			}

			List<MailAddress> rcpts = new ArrayList<>();
			rcpts.add(new MailAddress(email));

			String contactName = OturumGorevlisi.tek().getContactName(hostId, cid);

			String ref = inref.getString(0);
			String title = titles.getString(0);
			String user = users.getString(0);

			Map<String, Object> params = new Bmap().key("ref").val(ref).key("title").val(title).key("username")
					.val(user).key("contactname").val(contactName).key("domainname").val(domainName).ret();

			sendViaTemplate("en", "rombot", "Notification-" + app + "-" + code, sender, rcpts, "1", hostId,
					params);

		} catch (KnownError | JSONException | AddressException e) {
			notificationMail.failed(e);
		}

	}

	private static final Astate _sendSystemMail = mc.c("sendSystemMail");

	/**
	 * 
	 * 
	 * 
	 * @param lang
	 * @param templateName
	 * @param rcpt
	 * @param params
	 * @param senderId
	 * @param domain
	 *            : Must be a local domain
	 * @throws KnownError
	 */
	public void sendSystemMail(String lang, String templateName, String rcpt, Map<String, Object> params, int senderId,
			String domain, String rid) throws KnownError {
		try {
			_sendSystemMail.more();
			List<MailAddress> mm = new ArrayList<MailAddress>();
			mm.add(new MailAddress(rcpt));
			String world;
			String emailFrom;
			if (senderId == MAILER_TLOS) {
				// if (RomEnvFactory.getMine().isDev) {
				world = Genel.getHostName();
				emailFrom = "rombot@" + world;
				// } else {
				// world = RomEnvFactory.tlosEnv().domain;
				// emailFrom = tlosBot.toString();
				// }
			} else if (senderId == MAILER_DOMAIN_BOT) {
				world = Genel.getHostName();
				emailFrom = "rombot@" + domain;
			} else if (senderId == MAILER_BOUNCE) {
				world = Genel.getHostName();
				emailFrom = bouncer.toString();
			} else {
				_sendSystemMail.fail("Unknown sernderıd:" + senderId);
				throw new KnownError("Unknown sernderıd:" + senderId);
			}
			sendViaTemplate(lang, "rombot", templateName, emailFrom, mm, rid, 0, params);
		} catch (AddressException e) {
			_sendSystemMail.fail(e);
			throw new KnownError(e);
		}
	}

	private static final Astate _sendViaTemplate = mc.c("sendViaTemplate");

	public void sendViaTemplate(String lang, String user, String templateName, String sender,
			List<MailAddress> rcpts, String rid,
			int hostId, Map<String, Object> params, Object... paramList) throws KnownError {
		_sendViaTemplate.more();
		final String world = Genel.getHostName();
		
		if(params==null)
			params=new HashMap<>(); 
		
		params.put("recipients", rcpts);
		if(paramList!=null){
			for(int i=0; i<paramList.length; i+=2){
				params.put((String)paramList[i], paramList[i+1]);
			}
		}
		String[] resp = templating.doit(lang, templateName, params);
		try {
			MimeMessage mm = new MimeMessage(resp[0], resp[1], sender.toString(), rcpts);
			File f = DbfsGorevlisi.tek().tempFile(".m.txt");
			try (FileOutputStream fo = new FileOutputStream(f);) {
				mm.writeTo(fo);
			}
			EmailImpl mail = EmailImpl.create(Genel.getHostName(), Genel.getIp(), hostId, user, 0,
					new MailAddress(sender), rcpts, f);
			HaberGorevlisi.tek().send(TalkUtil.m(0, world, "mail.process", mail.getJson()), rid);

		} catch (MessagingException | IOException | JSONException e) {
			_sendViaTemplate.fail(e);
			throw new KnownError(e);
		}
	}

	private static final Astate overDeliverLimit = mc.c("over-deliver-limit");
	private static final Astate _deliverremote = mc.c("deliverremote");

	@NodeTalkMethod(cmd = "mail.deliverremote", retrypattern = 0)
	public TalkResult deliverremote(JSONObject jo) throws KnownError {

		_deliverremote.more();

		if (remoteDeliverLimiting.get() > remoteDeliverHourLimit) {
			overDeliverLimit.more(jo.toString());
			return TalkResult.retry;
		}

		try {
			final Session session = Session.getInstance();
			Email mail;
			mail = new EmailImpl(TalkUtil.data(jo));

			String key = mail.getName();
			// if (isDebug) {
			// String message = Thread.currentThread().getName() + " will
			// process mail " + key;
			// log(message);
			// }

			Delivery delivery = new Delivery(mail, session);

			return delivery.deliver(GuvenlikGorevlisi.tek().getDkimPrivate());
			// // Deliver message
			// if () {
			// return TalkResult.success;
			// } else {
			// // if (usePriority) {
			// // Use lowest priority for retries. See
			// // JAMES-1311
			// mail.setPriority(MailPrioritySupport.LOW_PRIORITY);
			// // }
			// return TalkResult.retry;
			// }

		} catch (JSONException e) {
			_deliverremote.fail(e);
			throw new KnownError(e);
		}
	}

	@Override
	public void compileTimeError(STMessage msg) {
		Sistem.errln("MailService compileTimeError:" + msg.toString());
	}

	@Override
	public void runTimeError(STMessage msg) {
		Sistem.errln("MailService runTimeError:" + msg.toString());
	}

	@Override
	public void IOError(STMessage msg) {
		Sistem.errln("MailService ioError:" + msg.toString());
	}

	@Override
	public void internalError(STMessage msg) {
		Sistem.errln("MailService internalError:" + msg.toString());
	}

	public void mailUnsubscribe(Integer hostId, String userName) {
		Sistem.errln("MailService mailUnsubscribe not implemented");
	}

	public void deleteMail(int hostId, String uri) throws KnownError {
		new MailDestroy().destroy(hostId, uri);
	}

	private AtomicInteger remoteDeliverLimiting = new AtomicInteger();

	private static final Astate _everyHour = mc.c("everyHour");

	@Override
	public void everyHour(int year, int month, int day, int hour) {
		_everyHour.more();
		remoteDeliverLimiting.set(0);
	}

}
