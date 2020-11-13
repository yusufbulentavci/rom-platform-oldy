package com.bilgidoku.rom.mail.send;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.bilgidoku.rom.dns.DnsImplement;
import com.bilgidoku.rom.dns.HostAddress;
import com.bilgidoku.rom.dns.MXHostAddressIterator;
import com.bilgidoku.rom.dns.TemporaryResolutionException;
import com.bilgidoku.rom.epostatemel.javam.mail.Address;
import com.bilgidoku.rom.epostatemel.javam.mail.MessagingException;
import com.bilgidoku.rom.epostatemel.javam.mail.SendFailedException;
import com.bilgidoku.rom.epostatemel.javam.mail.Session;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.InternetAddress;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.MailAddress;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.MimeMessage;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.MimeMultipart;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.MimePart;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.ParseException;
import com.bilgidoku.rom.epostatemel.mail.smtp.SMTPAddressFailedException;
import com.bilgidoku.rom.epostatemel.mail.smtp.SMTPSendFailedException;
import com.bilgidoku.rom.epostatemel.mail.smtp.SMTPTransport;
import com.bilgidoku.rom.gunluk.GunlukGorevlisi;
import com.bilgidoku.rom.haber.TalkResult;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.mail.Email;
import com.bilgidoku.rom.shared.err.KnownError;

/**
 * At the moment the mailet package includes 2 mailets: ConvertTo7Bit and
 * DKIMSign
 * 
 * ConvertTo7Bit is simply required because when DKIM signing we have to make
 * sure that the MTAs involved in the delivery of our message won't alter the
 * byte stream of our signed message and 7bit messages are more likely to be
 * transported without further conversions. So, make sure to use ConvertTo7Bit
 * just before DKIMSign mailet.
 * 
 * DKIMSign is the signing mailet. Make sure you use this one as the last mailet
 * before delivery otherwise further JavaMail interactions with a signed message
 * would lead in a broken signature.
 * 
 * Here is a sample configuration for DKIMSign
 * 
 * <mailet match="All" class="DKIMSign"> <signatureTemplate>v=1; s=selector;
 * d=example.com; h=from:to:received:received; a=rsa-sha256; bh=;
 * b=;</signatureTemplate> <privateKey> -----BEGIN RSA PRIVATE KEY-----
 * MIICXAIBAAKBgQDYDaYKXzwVYwqWbLhmuJ66aTAN8wmDR+rfHE8HfnkSOax0oIoT
 * M5zquZrTLo30870YMfYzxwfB6j/Nz3QdwrUD/t0YMYJiUKyWJnCKfZXHJBJ+yfRH
 * r7oW+UW3cVo9CG2bBfIxsInwYe175g9UjyntJpWueqdEIo1c2bhv9Mp66QIDAQAB
 * AoGBAI8XcwnZi0Sq5N89wF+gFNhnREFo3rsJDaCY8iqHdA5DDlnr3abb/yhipw0I
 * /1HlgC6fIG2oexXOXFWl+USgqRt1kTt9jXhVFExg8mNko2UelAwFtsl8CRjVcYQO
 * cedeH/WM/mXjg2wUqqZenBmlKlD6vNb70jFJeVaDJ/7n7j8BAkEA9NkH2D4Zgj/I
 * OAVYccZYH74+VgO0e7VkUjQk9wtJ2j6cGqJ6Pfj0roVIMUWzoBb8YfErR8l6JnVQ
 * bfy83gJeiQJBAOHk3ow7JjAn8XuOyZx24KcTaYWKUkAQfRWYDFFOYQF4KV9xLSEt
 * ycY0kjsdxGKDudWcsATllFzXDCQF6DTNIWECQEA52ePwTjKrVnLTfCLEG4OgHKvl
 * Zud4amthwDyJWoMEH2ChNB2je1N4JLrABOE+hk+OuoKnKAKEjWd8f3Jg/rkCQHj8
 * mQmogHqYWikgP/FSZl518jV48Tao3iXbqvU9Mo2T6yzYNCCqIoDLFWseNVnCTZ0Q
 * b+IfiEf1UeZVV5o4J+ECQDatNnS3V9qYUKjj/krNRD/U0+7eh8S2ylLqD3RlSn9K
 * tYGRMgAtUXtiOEizBH6bd/orzI9V9sw8yBz+ZqIH25Q= -----END RSA PRIVATE KEY-----
 * </privateKey> </mailet>
 * 
 * 
 * If your PEM file is password encrypted then you can add a privateKeyPassword
 * argument to the mailet
 * 
 * 
 * @author avci
 * 
 */

public class Delivery {
	private static final MC mc = new MC(Delivery.class);

	//	private String dkimSignTemplate1 = "v=1; s=mail; d=";
	//	private String dkimSignTemplate2 = "; h=from:to:subject:received; a=rsa-sha256; bh=; b=;";

//	private final static MailService mps = ServiceDiscovery.getService(MailService.class);

	private static final String bounceProcessor = "bounces";

	public final Email mail;
	public MimeMessage message;
	public InternetAddress[] addr;
	public Iterator<HostAddress> targetServers = null;

	public Boolean permanent = null;
	public MessagingException err;

	/** The retry count dnsProblemErrors */
	private int dnsProblemRetry = 0;

	private boolean isDebug = true;

	/** Maximum no. of retries (Defaults to 5). */
	private int maxRetries = 5;

	private Session session;

	private LogDelivery logDelivery;

	public Delivery(Email mail, Session session) {
		this.mail = mail;
		this.session = session;
		this.logDelivery = new LogDelivery(mail, isDebug);
	}

	private static final Astate init = mc.c("init");

	public void init(PrivateKey prKey) throws KnownError {
		init.more();
		this.message = mail.getInputMessage();
		//		try {
		//			convertTo7Bit();
		//		} catch (MessagingException | IOException e1) {
		//			throw new KnownError("PermFail Delivery can not convert to 7 bit", e1);
		//		}
		// Create an array of the recipients as InternetAddress objects
		this.addr = toInternetAddr(mail.getRecipients());

		//		String signTemp = dkimSignTemplate1 + this.mail.getSender().getDomain() + dkimSignTemplate2;
		//		DKIMSigner signer = new DKIMSigner(signTemp, prKey);

		//		SignatureRecord signRecord = signer.newSignatureRecordTemplate(signTemp);
		//		try {
		//			BodyHasher bhj = signer.newBodyHasher(signRecord);
		//			// MimeMessage message = mail.getMessage();
		//			Headers headers = new MimeMessageHeaders(message);
		//			try {
		//				OutputStream os = new HeaderSkippingOutputStream(bhj.getOutputStream());
		//				os = new CRLFOutputStream(os);
		//				message.writeTo(os);
		//				bhj.getOutputStream().close();
		//			} catch (IOException e) {
		//				throw new MessagingException("Exception calculating bodyhash: " + e.getMessage(), e);
		//			}
		//			String signatureHeader = signer.sign(headers, bhj);
		//			message.addHeadHeaderLine(signatureHeader);
		//
		//			//
		//		} catch (PermFailException | MessagingException e) {
		//			throw new KnownError("PermFail while signing: " + e.getMessage(), e);
		//		}
	}

	private InternetAddress[] toInternetAddr(Collection<MailAddress> recipients) {
		InternetAddress addr[] = new InternetAddress[recipients.size()];
		int j = 0;
		for (Iterator<MailAddress> i = recipients.iterator(); i.hasNext(); j++) {
			MailAddress rcpt = i.next();
			addr[j] = rcpt.toInternetAddress();
		}
		return addr;
	}

	private static final Astate target = mc.c("target");
	private static final Astate notarget = mc.c("notarget");

	public boolean targetServers() {
		// Figure out which servers to try to send to. This collection
		// will hold all the possible target servers

		MailAddress rcpt = (MailAddress) mail.getRecipients().iterator().next();
		String host = rcpt.getDomain();

		if (host.equals("localhost")) {
			List<String> lst = new ArrayList<String>();
			lst.add("127.0.0.1");
			targetServers = new MXHostAddressIterator(lst.iterator(), 25, true);
		} else {

			target.more();
			// Lookup the possible targets
			try {
				targetServers = new MXHostAddressIterator(DnsImplement.one().findMXRecords(host).iterator(), false);
			} catch (TemporaryResolutionException e) {
				target.failed();
				logDelivery.log("Temporary problem looking up mail server for host: " + host);
				StringBuilder exceptionBuffer = new StringBuilder(128)
						.append("Temporary problem looking up mail server for host: ").append(host)
						.append(".  I cannot determine where to send this message.");

				// temporary problems
				failed(new MessagingException(exceptionBuffer.toString()), false);
				return true;
			}
		}

		if (!targetServers.hasNext()) {
			notarget.more();
			logDelivery.log("No mail server found for: " + host);
			StringBuilder exceptionBuffer = new StringBuilder(128).append("There are no DNS entries for the hostname ")
					.append(host).append(".  I cannot determine where to send this message.");

			// int retry = 0;
			// try {
			// retry = Integer.parseInt(mail.getErrorMessage());
			// } catch (NumberFormatException e) {
			// // Unable to parse retryCount
			// }
			// if (retry == 0 || retry > dnsProblemRetry) {
			// // The domain has no dns entry.. Return a permanent
			// // error
			// failed(new MessagingException(exceptionBuffer.toString()), true);
			// } else {
			//
			// }
			failed(new MessagingException(exceptionBuffer.toString()), false);
			return true;
		}
		return false;
	}

	private void failed(MessagingException messagingException, boolean permanent) {
		this.permanent = permanent;
		this.err = messagingException;

	}

	public void convertTo7Bit() throws MessagingException, IOException {
		convertTo7Bit(message);
	}

	private static final Astate convert7 = mc.c("convert7");
	private static final Astate convert87 = mc.c("convert7from8");

	private void convertTo7Bit(MimePart msg) throws MessagingException, IOException {
		convert7.more();
		if (msg.isMimeType("multipart/*")) {
			MimeMultipart parts = (MimeMultipart) msg.getContent();
			int count = parts.getCount();
			for (int i = 0; i < count; i++) {
				convertTo7Bit((MimePart) parts.getBodyPart(i));
			}
		} else if ("8bit".equals(msg.getEncoding())) {
			convert87.more();
			// The content may already be in encoded the form (likely with mail
			// created from a
			// stream). In that case, just changing the encoding to
			// quoted-printable will mangle
			// the result when this is transmitted. We must first convert the
			// content into its
			// native format, set it back, and only THEN set the transfer
			// encoding to force the
			// content to be encoded appropriately.

			// if the part doesn't contain text it will be base64 encoded.
			String contentTransferEncoding = msg.isMimeType("text/*") ? "quoted-printable" : "base64";
			msg.setContent(msg.getContent(), msg.getContentType());
			msg.setHeader("Content-Transfer-Encoding", contentTransferEncoding);
			msg.addHeader("X-MIME-Autoconverted",
					"from 8bit to " + contentTransferEncoding + " by " + "rom-server");
		}
	}

	private static final Astate tobounce = mc.c("tobounce");

	/**
	 * Insert the method's description here.
	 * 
	 * @param mail
	 *            org.apache.james.core.MailImpl
	 * @param ex
	 *            javam.mail.MessagingException
	 * @param permanent
	 * @return boolean Whether the message failed fully and can be deleted
	 * @throws KnownError
	 * @throws MessagingException
	 */
	private TalkResult toBounce(Email mail, Exception ex, boolean permanent) {

		tobounce.more();

		logDelivery.logFailMessage(mail, ex, permanent);

		if (permanent)
			return TalkResult.failed;
		return TalkResult.retry;

		// if (!permanent) {
		// if (!mail.getState().equals(Email.ERROR)) {
		// mail.setState(Email.ERROR);
		// mail.setLastUpdated(new Date());
		// }
		//
		// int retries = 0;
		// try {
		// retries = Integer.parseInt(mail.getErrorMessage());
		// } catch (NumberFormatException e) {
		// // Something strange was happen with the errorMessage..
		// }
		//
		// if (retries < maxRetries) {
		// logRetry(retries);
		// ++retries;
		// mail.setLastUpdated(new Date());
		// return false;
		// } else {
		// logToBounce(retries);
		// }
		// }
		//
		// if (mail.getSender() == null) {
		// logDelivery.log("Null Sender: no bounce will be generated for " +
		// mail.getName());
		// return true;
		// }
		//
		// // if (bounceProcessor != null) {
		// // do the new DSN bounce
		// // setting attributes for DSN mailet
		// String cause;
		// if (ex instanceof MessagingException) {
		// cause = getErrorMsg((MessagingException) ex);
		// } else {
		// cause = ex.getMessage();
		// }
		// mail.deliveryError(cause);
		// // setAttribute("delivery-error", cause);
		// // mailQueueService.sendMail(mail);
		// // re-insert the mail into the spool for getting it passed to the
		// // dsn-processor
		// // MailetContext mc = getMailetContext();
		// // try {
		// // mc.sendMail(mail);
		// // } catch (KnownError e) {
		// // // we shouldn't get an exception, because the mail was already
		// // // processed
		// // logDelivery.log("Exception re-inserting failed mail: ", e);
		// // }
		// // } else {
		// // do an old style bounce
		// // bounce(mail, ex);
		// // }
		// return true;
	}

	private static final Astate deliver = mc.c("deliver");

	/**
	 * We can assume that the recipients of this message are all going to the
	 * same mail server. We will now rely on the DNS server to do DNS MX record
	 * lookup and try to deliver to the multiple mail servers. If it fails, it
	 * should throw an exception.
	 * 
	 * @param mail
	 *            org.apache.james.core.MailImpl
	 * @param session
	 *            javam.mail.Session
	 * @return boolean Whether the delivery was successful and the message can
	 *         be deleted
	 * @throws KnownError
	 * @throws MessagingException
	 */
	public TalkResult deliver(PrivateKey prKey) throws KnownError {

		try {
			deliver.more();
			if (isDebug) {
				logDelivery.log("Attempting to deliver " + mail.getName());
			}
			this.init(prKey);

			if (this.addr.length <= 0) {
				logDelivery.log("No recipients specified... not sure how this could have happened.");
				return TalkResult.success;
			}

			if (this.targetServers()) {
				return toBounce(this.mail, this.err, this.permanent);
			}

			MessagingException lastError = null;

			while (this.targetServers.hasNext()) {
				try {

					Properties props = session.getProperties();
					if (mail.getSender() == null) {
						props.put("mail.smtp.from", "<>");
					} else {
						String sender = mail.getSender().toString();
						props.put("mail.smtp.from", sender);
					}

					HostAddress outgoingMailServer = this.targetServers.next();

					if (isDebug) {
						logDelivery.logAttempt(outgoingMailServer, props.get("mail.smtp.from"), addr);
					}

					// Many of these properties are only in later JavaMail
					// versions
					// "mail.smtp.ehlo" //default true
					// "mail.smtp.auth" //default false
					// "mail.smtp.dsn.ret" //default to nothing... appended as
					// RET= after MAIL FROM line.
					// "mail.smtp.dsn.notify" //default to nothing...appended as
					// NOTIFY= after RCPT TO line.

					SMTPTransport transport = null;
					try {
						transport = session.getSmtpTransport(session, outgoingMailServer);
						try {

							transport.connect();
						} catch (MessagingException me) {
							// Any error on connect should cause the mailet to
							// attempt
							// to connect to the next SMTP server associated
							// with this
							// MX record. Just log the exception. We'll worry
							// about
							// failing the message at the end of the loop.

							// Also include the stacktrace if debug is enabled.
							// See JAMES-1257
							if (isDebug) {
								logDelivery.log(me.getMessage(), me.getCause());
							} else {
								logDelivery.log(me.getMessage());
							}
							continue;
						}

						// bilo: dkimsign ile ilintili
						// mesaji basta bir kere sign ediyoruz herkese
						// gonderiyoruz
						// gonderdigimiz bazi serverlar 8bit desteklemiyor
						// olabilir
						// o yuzden deliver oncesi 7 bit'e geri donduruyoruz
						// if (!transport.supportsExtension("8BITMIME")) {
						// this.convertTo7Bit();
						// }
						//						session.getDebug();
						transport.sendMessage(this.message, this.addr);
						GunlukGorevlisi.tek().response(mail.getRequestId(), true, "delivered");
					} finally {
						if (transport != null) {
							try {
								// James-899: transport.close() sends QUIT to
								// the server; if that fails
								// (e.g. because the server has already closed
								// the connection) the message
								// should be considered to be delivered because
								// the error happened outside
								// of the mail transaction (MAIL, RCPT, DATA).
								transport.close();
							} catch (MessagingException e) {
								if (isDebug)
									logDelivery.logCouldntCloseConnection(outgoingMailServer, e);

							}
							transport = null;
						}
					}
					if (isDebug)
						logDelivery.logSentSuccess(outgoingMailServer, props);

					return TalkResult.success;
				} catch (SMTPSendFailedException e) {
					logDelivery.logSendFailedException(e);
					int returnCode = e.getReturnCode();
					if (returnCode >= 500 && returnCode <= 599)
						throw e;
				} catch (SendFailedException sfe) {
					logDelivery.logSendFailedException(sfe);

					if (sfe.getValidSentAddresses() != null) {
						Address[] validSent = sfe.getValidSentAddresses();
						if (validSent.length > 0) {
							StringBuilder logMessageBuffer = new StringBuilder(256).append("Mail (")
									.append(mail.getName()).append(") sent successfully for ")
									.append(Arrays.asList(validSent));
							logDelivery.log(logMessageBuffer.toString());
						}
					}

					/*
					 * SMTPSendFailedException introduced in JavaMail 1.3.2, and
					 * provides detailed protocol reply code for the operation
					 */

					int returnCode = 0;
					if (sfe instanceof SMTPSendFailedException) {
						returnCode = ((SMTPSendFailedException) sfe).getReturnCode();
					}
					// else if (sfe instanceof SMTPAddressFailedException) {
					// returnCode =((SMTPAddressFailedException)
					// sfe).getReturnCode();
					// } else if (sfe instanceof SMTPSenderFailedException) {
					// returnCode =((SMTPSenderFailedException)
					// sfe).getReturnCode();
					// }

					if (returnCode >= 500 && returnCode <= 599)
						throw sfe;

					if (sfe.getValidUnsentAddresses() != null && sfe.getValidUnsentAddresses().length > 0) {
						if (isDebug)
							logDelivery.log("Send failed, " + sfe.getValidUnsentAddresses().length
									+ " valid addresses remain, continuing with any other servers");
						lastError = sfe;
						continue;
					} else {
						// There are no valid addresses left to send, so rethrow
						throw sfe;
					}
				} catch (MessagingException me) {
					// MessagingException are horribly difficult to figure out
					// what actually happened.
					logDelivery.logMessaginException(mail, me);
					if ((me.getNextException() != null) && (me.getNextException() instanceof java.io.IOException)) {
						// This is more than likely a temporary failure

						// If it's an IO exception with no nested exception,
						// it's probably
						// some socket or weird I/O related problem.
						lastError = me;
						continue;
					}
					// This was not a connection or I/O error particular to one
					// SMTP server of an MX set. Instead, it is almost certainly
					// a protocol level error. In this case we assume that this
					// is an error we'd encounter with any of the SMTP servers
					// associated with this MX record, and we pass the exception
					// to the code in the outer block that determines its
					// severity.
					throw me;
				}
			} // end while
				// If we encountered an exception while looping through,
				// throw the last MessagingException we caught. We only
				// do this if we were unable to send the message to any
				// server. If sending eventually succeeded, we exit
				// deliver() though the return at the end of the try
				// block.
			if (lastError != null) {
				throw lastError;
			}
		} catch (SendFailedException sfe) {
			deliver.failed();
			// this.logSendFailedException(sfe);

			// Copy the recipients as direct modification may not be possible
			Collection<MailAddress> recipients = new ArrayList<MailAddress>(mail.getRecipients());

			return lastSendFailed(mail, sfe, recipients);
		} catch (MessagingException ex) {
			deliver.failed();
			// We should do a better job checking this... if the failure is a
			// general
			// connect exception, this is less descriptive than more specific
			// SMTP command
			// failure... have to lookup and see what are the various Exception
			// possibilities

			// Unable to deliver message after numerous tries... fail
			// accordingly

			// We check whether this is a 5xx error message, which
			// indicates a permanent failure (like account doesn't exist
			// or mailbox is full or domain is setup wrong).
			// We fail permanently if this was a 5xx error
			return toBounce(mail, ex, ('5' == ex.getMessage().charAt(0)));
		} catch (Exception ex) {
			deliver.failed();
			// Generic exception = permanent failure
			return toBounce(mail, ex, true);
		}

		/*
		 * If we get here, we've exhausted the loop of servers without sending
		 * the message or throwing an exception. One case where this might
		 * happen is if we get a MessagingException on each transport.connect(),
		 * e.g., if there is only one server and we get a connect exception.
		 */
		return toBounce(mail, new MessagingException("No mail server(s) available at this time."), false);
	}

	
	private TalkResult lastSendFailed(Email mail, SendFailedException sfe, Collection<MailAddress> recipients)
			throws KnownError {
		TalkResult deleteMessage;

		/*
		 * If you send a message that has multiple invalid addresses, you'll get
		 * a top-level SendFailedException that that has the valid,
		 * valid-unsent, and invalid address lists, with all of the server
		 * response messages will be contained within the nested exceptions.
		 * [Note: the content of the nested exceptions is implementation
		 * dependent.]
		 * 
		 * sfe.getInvalidAddresses() should be considered permanent.
		 * sfe.getValidUnsentAddresses() should be considered temporary.
		 * 
		 * JavaMail v1.3 properly populates those collections based upon the 4xx
		 * and 5xx response codes to RCPT TO. Some servers, such as Yahoo! don't
		 * respond to the RCPT TO, and provide a 5xx reply after DATA. In that
		 * case, we will pick up the failure from SMTPSendFailedException.
		 */

		int returnCode = 0;
		if (sfe instanceof SMTPSendFailedException) {
			returnCode = ((SMTPSendFailedException) sfe).getReturnCode();
		} else {
			MessagingException me = sfe;
			Exception ne;
			while ((ne = me.getNextException()) != null && ne instanceof MessagingException) {
				me = (MessagingException) ne;
				if (me instanceof SMTPAddressFailedException) {
					// Sometimes we'll get a normal SendFailedException with
					// nested SMTPAddressFailedException, so use the latter
					// RetCode
					returnCode = ((SMTPAddressFailedException) me).getReturnCode();
				}
			}
		}
		// else if (sfe instanceof SMTPSenderFailedException) {
		// returnCode =((SMTPSenderFailedException) sfe).getReturnCode();
		// }
		deleteMessage = (returnCode >= 500 && returnCode <= 599) ? TalkResult.failed : TalkResult.retry;

		// log the original set of intended recipients
		if (isDebug)
			logDelivery.log("Recipients: " + recipients);

		if (sfe.getInvalidAddresses() != null) {
			Address[] address = sfe.getInvalidAddresses();
			if (address.length > 0) {
				recipients.clear();
				for (int i = 0; i < address.length; i++) {
					try {
						recipients.add(new MailAddress(address[i].toString()));
					} catch (ParseException pe) {
						// this should never happen ... we should have
						// caught malformed addresses long before we
						// got to this code.
						logDelivery.log("Can't parse invalid address: " + pe.getMessage());
					}
				}
				// Set the recipients for the mail
				mail.setRecipients(recipients);

				if (isDebug)
					logDelivery.log("Invalid recipients: " + recipients);
				deleteMessage = toBounce(mail, sfe, true);
			}
		}

		if (sfe.getValidUnsentAddresses() != null) {
			Address[] address = sfe.getValidUnsentAddresses();
			if (address.length > 0) {
				recipients.clear();
				for (int i = 0; i < address.length; i++) {
					try {
						recipients.add(new MailAddress(address[i].toString()));
					} catch (ParseException pe) {
						// this should never happen ... we should have
						// caught malformed addresses long before we
						// got to this code.
						logDelivery.log("Can't parse unsent address: " + pe.getMessage());
					}
				}
				// Set the recipients for the mail
				mail.setRecipients(recipients);
				if (isDebug)
					logDelivery.log("Unsent recipients: " + recipients);
				SMTPSendFailedException smtp = logDelivery.getSmtp(sfe);
				if (smtp != null) {
					deleteMessage = toBounce(mail, sfe, smtp.getReturnCode() >= 500 && smtp.getReturnCode() <= 599);
				} else {
					deleteMessage = toBounce(mail, sfe, false);
				}
			}
		}

		return deleteMessage;
	}

	/**
	 * Utility method for getting the error message from the (nested) exception.
	 * 
	 * @param me
	 *            MessagingException
	 * @return error message
	 */
	protected String getErrorMsg(MessagingException me) {
		if (me.getNextException() == null) {
			return me.getMessage().trim();
		} else {
			Exception ex1 = me.getNextException();
			return ex1.getMessage().trim();
		}
	}

}
