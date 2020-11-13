package com.bilgidoku.rom.smtp.core.fastfail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang.mutable.MutableBoolean;

import com.bilgidoku.rom.dkim.DKIMVerifier;
import com.bilgidoku.rom.dkim.HeaderSkippingOutputStream;
import com.bilgidoku.rom.dkim.MimeMessageHeaders;
import com.bilgidoku.rom.dkim.api.BodyHasher;
import com.bilgidoku.rom.dkim.api.Headers;
import com.bilgidoku.rom.dkim.api.SignatureRecord;
import com.bilgidoku.rom.dkim.exceptions.FailException;
import com.bilgidoku.rom.dmarc.DmarcHandler;
import com.bilgidoku.rom.dmarc.DmarcResponse;
import com.bilgidoku.rom.dns.exceptions.SPFErrorConstants;
import com.bilgidoku.rom.epostatemel.javam.mail.MessagingException;
import com.bilgidoku.rom.epostatemel.javam.mail.Session;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.MimeMessage;
import com.bilgidoku.rom.epostatemel.mail.util.CRLFOutputStream;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.gorevli.Uygulama;
import com.bilgidoku.rom.smtp.BlockMail;
import com.bilgidoku.rom.smtp.SMTPSession;
import com.bilgidoku.rom.smtp.SmtpMailAddress;
import com.bilgidoku.rom.spf.SPF;
import com.bilgidoku.rom.spf.executor.SPFResult;



public class RomOnMail {
	private static final MC mc = new MC(RomOnMail.class);

	private static final String ROM_DKIM = "rom.dkim";
	private static final String ROM_SPF = "rom.spf";
	private static final String ROM_MAYBESPAM = "rom.maybespam";
	private static final DmarcHandler dmarcHandler = new DmarcHandler();

	protected static final DKIMVerifier verifier = new DKIMVerifier();
	private static final SPF spfVerifier = new SPF();

	private static final Astate spamCheck=mc.c("spam-check");
	private static final Astate spamFound=mc.c("spam-found");
	private static final Astate spamFoundSpf=mc.c("spam-found-spf");
	
	public static int check(SMTPSession session) throws KnownError, BlockMail {
		spamCheck.more();
		SmtpMailAddress sender = session.getSender();
		File f = session.getMimeFile();
		MimeMessage mm = null;
		try (FileInputStream is = new FileInputStream(f)) {
			mm = new MimeMessage(Session.getInstance(), is);

			//			message.setHeader(RFC2822Headers.RETURN_PATH, (mail.getSender() == null ? "<>" : "<" + mail.getSender()
			//			+ ">"));

			if (session.getProtocolUser() != null) {
				mm.setHeader("rom.user", "true");
				return 0;
			}

			// String explanation;
			Boolean dkim = null;
			Boolean spf = null;
			Boolean dmarc = null;
			String senderHost = sender.getDomain();

			// Address senderHeader = mm.getSenderRom();
			// if (senderHeader == null) {
			// throw new KnownError("No sender header");
			// }
			// if(senderHeader instanceof InternetAddress){
			// MailAddress ma = new
			// MailAddress((InternetAddress)senderHeader);
			// senderHost=ma.getDomain();
			// }else{
			// senderHost=((NewsAddress)senderHeader).getHost();
			// }

			MutableBoolean mb=new MutableBoolean(false);
			
			dkim = handleDkim(session, mb, mm, dkim);

			spf = handleSpf(session, mb, sender, mm);

			handleBlockList(session, mb, mm);

			handleDmarc(mm, mb, dkim, spf, dmarc, senderHost);

			if(mb.isTrue()){
				setSpam(mm);
				spamFound.more();
			}
			
			return mb.booleanValue()?1:0;

		} catch (MessagingException | IOException e) {
			throw new KnownError("Cant open new message", e);
		} finally {

			try {
				if (mm != null)
					mm.writeTo(new FileOutputStream(f));
			} catch (MessagingException | IOException e1) {
				Sistem.printStackTrace(e1);
			}
		}

	}

	static void setSpam(MimeMessage mm) {
		try {
			mm.setHeader(ROM_MAYBESPAM, "true");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	private static void handleBlockList(SMTPSession session, MutableBoolean mb, MimeMessage mm) {
		ServerAttitude sa = ZenSpam.learn(session.getIpAddress());
		if (sa == ServerAttitude.NOTLISTED || sa==ServerAttitude.POLICYBLOCK) {
			return;
		}
		try {
			mm.setHeader("rom.blocklist", sa.toString().toLowerCase());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		mb.setValue(true);
	}

	static void handleDmarc(MimeMessage mm, MutableBoolean mb, Boolean dkim, Boolean spf, Boolean dmarc, String senderHost)
			throws BlockMail {
		DmarcResponse dmarcResp = dmarcHandler.auth(senderHost, spf, dkim);
		if (dmarcResp != null) {
			dmarc = dmarcResp.isSuc();
			// dmarc basarisiz ise fail edicek ama bir kosulumuz daha var
			// development modunda iken localhost.zzz'den geliyorsa fail etme
			if (!dmarc) { //&& !(Mode.deve() && senderHost.equals("localhost.zzz")
				mm.setHeaderRom("rom.dmarc", "fail; " + dmarcResp.explain);
				if (dmarcResp.policyReject != null && dmarcResp.policyReject) {
					throw new BlockMail(dmarcResp.explain);
				}
			} else {
				mm.setHeaderRom("rom.dmarc", "pass; " + dmarcResp.explain);
			}
		}

		Boolean auth = (dmarc != null) ? dmarc : (spf != null && dkim == null ? spf
				: (spf == null && dkim != null ? dkim : (spf != null && dkim != null ? (spf && dkim) : null)));
		if (auth != null || auth)
			return;
		mb.setValue(true);
	}

	

	static Boolean handleSpf(SMTPSession session, MutableBoolean failed, SmtpMailAddress sender, MimeMessage mm) throws BlockMail {
		String ip = session.getRemoteAddress().getAddress().getHostAddress();
		String heloEhlo = session.getCurHeloName();
		SPFResult spfResult = spfVerifier.checkSPF(ip, sender.toString(), heloEhlo);

		session.setSpfHeader(spfResult.getHeaderText());
		mm.setHeaderRom(ROM_SPF, spfResult.getHeaderText());

		String rtext = spfResult.getResult();
		if (rtext.equals(SPFErrorConstants.FAIL_CONV)) {
			throw new BlockMail("SPF failed;" + spfResult.getHeaderText());
		}

		if (rtext.equals(SPFErrorConstants.PASS_CONV)) {
			return true;
		}

		if (rtext.equals(SPFErrorConstants.SOFTFAIL_CONV)) {
			if(Uygulama.tek().isProd()){
				spamFoundSpf.more();
				failed.setValue(true);
			}
		}
		return false;
	}

	static Boolean handleDkim(SMTPSession session, MutableBoolean failed, MimeMessage mm, Boolean dkim) {
		try {

			List<SignatureRecord> res = verify(verifier, mm, true);
			if (res == null || res.isEmpty()) {
				dkim = null;
				// neutral
				mm.setHeaderRom(ROM_DKIM, "neutral (no signatures)");
				session.setDkimHeader("neutral (no signatures)");
			} else {
				dkim = true;
				// pass
				StringBuilder msg = new StringBuilder();
				msg.append("pass");
				for (SignatureRecord rec : res) {
					msg.append(" (");
					msg.append("identity ");
					msg.append(rec.getIdentity().toString());
					msg.append(")");
				}
				String dh = msg.toString();
				mm.setHeaderRom(ROM_DKIM, dh);
				session.setDkimHeader(dh);
			}
		} catch (FailException e) {
			dkim = false;
			String dh = "fail ("
					+ (e.getRelatedRecordIdentity() != null ? "identity " + e.getRelatedRecordIdentity() + ": " : "")
					+ e.getMessage() + ")";
			mm.setHeaderRom(ROM_DKIM, dh);
			session.setDkimHeader(dh);
			// fail
		} catch (MessagingException e) {
			Sistem.errln("dkim");
			Sistem.printStackTrace(e);
		}
		return dkim;
	}

	protected static List<SignatureRecord> verify(DKIMVerifier verifier, MimeMessage message, boolean forceCRLF)
			throws MessagingException, FailException {
		Headers headers = new MimeMessageHeaders(message);
		BodyHasher bh = verifier.newBodyHasher(headers);
		try {
			if (bh != null) {
				OutputStream os = new HeaderSkippingOutputStream(bh.getOutputStream());
				if (forceCRLF)
					os = new CRLFOutputStream(os);
				message.writeTo(os);
				bh.getOutputStream().close();
			}

		} catch (IOException e) {
			throw new MessagingException("Exception calculating bodyhash: " + e.getMessage(), e);
		}

		return verifier.verify(bh);
	}

}

//String rtext = spfResult.getResult();
//if ((rtext.equals(SPFErrorConstants.FAIL_CONV))
////						|| (rtext.equals(SPFErrorConstants.SOFTFAIL_CONV))// &&
//		// blockSoftFail=true
////		|| (rtext.equals(SPFErrorConstants.PERM_ERROR_CONV)// &&
////															// blockPermError=true
//		)) {
//
//	// if
//	// (spfResult.equals(SPFErrorConstants.PERM_ERROR_CONV))
//	// {
//	// explanation =
//	// "Block caused by an invalid SPF record";
//	// }
//	spf = false;
//
//	// session.setAttachment(SPF_DETAIL, explanation,
//	// State.Transaction);
//	// session.setAttachment(SPF_BLOCKLISTED, "true",
//	// State.Transaction);
//
//} else if (rtext.equals(SPFErrorConstants.TEMP_ERROR_CONV)) {
//	// session.setAttachment(SPF_TEMPBLOCKLISTED,
//	// "true", State.Transaction);
//	spf = false;
//} else if (rtext.equals(SPFErrorConstants.PASS_CONV)) {
//	spf = true;
//}
//
//if (block) {
//	return new SMTPResponse(SMTPRetCode.AUTH_FAILED, "SPF failed;" + spfResult.getHeaderText());
//}
//return spf;

