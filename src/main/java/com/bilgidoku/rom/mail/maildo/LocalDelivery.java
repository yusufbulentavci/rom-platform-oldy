/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/

package com.bilgidoku.rom.mail.maildo;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import com.bilgidoku.rom.epostatemel.javam.mail.MessagingException;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.MailAddress;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.mail.Email;
import com.bilgidoku.rom.mail.MailProcessContext;
import com.bilgidoku.rom.min.Sistem;

/**
 * Receives a Mail from the Queue and takes care of delivery of the message to
 * local inboxes.
 * 
 * This mailet is a composition of RecipientRewriteTable, SieveMailet and
 * MailboxManager configured to mimic the old "LocalDelivery" James 2.3
 * behavior.
 */
public class LocalDelivery extends AbstractMailDo {

//	private static final HostService host = ServiceDiscovery.getService(HostService.class);
//	private static final MailboxService mailboxManager = ServiceDiscovery.getService(MailboxService.class);
//	private static final FileService fileSystem = ServiceDiscovery.getService(FileService.class);
//
//	private RecipientRewrite recipientRewriteTable = ServiceDiscovery.getService(MailRedirectService.class); // Mailet
																									// that
	public LocalDelivery(MailProcessContext context) {
		super(context, null);
	}
																									// applies
	// RecipientRewriteTable
	/**
	 * The delivery header
	 */
	private String deliveryHeader;
	/**
	 * resetReturnPath
	 */
	private boolean resetReturnPath;


	/**
	 * Delivers a mail to a local mailbox.
	 * 
	 * @param mail
	 *            the mail being processed
	 * @throws KnownError 
	 * @throws MessagingException 
	 */
	public void service(Email mail) throws KnownError {
		// recipientRewriteTable.service(mail);
		// if (mail.getState() == Mail.GHOST) {
		// return;
		// }
		Collection<MailAddress> recipients = mail.getRecipients();
		Collection<MailAddress> errors = new Vector<MailAddress>();

//		MimeMessage message = null;
//		if (deliveryHeader != null || resetReturnPath) {
//			message = mail.getMessage();
//		}

//		if (resetReturnPath) {
//			// Set Return-Path and remove all other Return-Path headers from the
//			// message
//			// This only works because there is a placeholder inserted by
//			// MimeMessageWrapper
//			message.setHeader(RFC2822Headers.RETURN_PATH, (mail.getSender() == null ? "<>" : "<" + mail.getSender()
//					+ ">"));
//		}

		Enumeration headers;
//		InternetHeaders deliveredTo = new InternetHeaders();
//		if (deliveryHeader != null) {
//			// Copy any Delivered-To headers from the message
//			headers = message.getMatchingHeaders(new String[] { deliveryHeader });
//			while (headers.hasMoreElements()) {
//				Header header = (Header) headers.nextElement();
//				deliveredTo.addHeader(header.getName(), header.getValue());
//			}
//		}

		for (Iterator<MailAddress> i = recipients.iterator(); i.hasNext();) {
			MailAddress recipient = i.next();
			try {
//				if (deliveryHeader != null) {
//					// Add qmail's de facto standard Delivered-To header
//					message.addHeader(deliveryHeader, recipient.toString());
//				}

				storeMail(mail.getSender(), recipient, mail);

//				if (deliveryHeader != null) {
//					if (i.hasNext()) {
//						// Remove headers but leave all placeholders
//						message.removeHeader(deliveryHeader);
//						headers = deliveredTo.getAllHeaders();
//						// And restore any original Delivered-To headers
//						while (headers.hasMoreElements()) {
//							Header header = (Header) headers.nextElement();
//							message.addHeader(header.getName(), header.getValue());
//						}
//					}
//				}
			} catch (Exception ex) {
				Sistem.printStackTrace(ex, "Error while storing mail.");
				errors.add(recipient);
			}
		}

		if (!errors.isEmpty()) {
			// If there were errors, we redirect the email to the ERROR
			// processor.
			// In order for this server to meet the requirements of the SMTP
			// specification, mails on the ERROR processor must be returned to
			// the sender. Note that this email doesn't include any details
			// regarding the details of the failure(s).
			// In the future we may wish to address this.
			context.deliveryFailed(mail, errors);
		}
	}

	/**
	 * Return a string describing this mailet.
	 * 
	 * @return a string describing this mailet
	 */
	public String getMailetInfo() {
		return "Local Delivery Mailet";
	}


	public void storeMail(MailAddress sender, MailAddress recipient, Email mail) throws KnownError, MessagingException {
		if (recipient == null) {
			throw new IllegalArgumentException("Recipient for mail to be spooled cannot be null.");
		}
//		if (mail.getMessage() == null) {
//			throw new IllegalArgumentException("Mail message to be spooled cannot be null.");
//		}

		// sieveMessage(recipient, mail);
		context.storeMail(recipient, mail);

	}
	// protected void sieveMessage(MailAddress recipient, Mail aMail) throws
	// MessagingException {
	// String username = getUsername(recipient);
	// try {
	// final InputStream ins = locator.get(getScriptUri(recipient));
	// sieveMessageEvaluate(recipient, aMail, ins);
	// } catch (Exception ex) {
	// SIEVE is a mail filtering protocol.
	// Rejecting the mail because it cannot be filtered
	// seems very unfriendly.
	// So just log and store in INBOX
	// if (isInfoLoggingOn()) {
	// log("Cannot evaluate Sieve script. Storing mail in user INBOX.", ex);
	// }
	// storeMessageInbox(username, aMail.getMessage());
	// }
	// }

	@Override
	public String getMailetName() {
		return "LocalDelivery";
	}

	// protected void storeMessageInbox(String username, MimeMessage message)
	// throws MessagingException {
	// String url = "mailbox://" + username + "/";
	// poster.post(url, message);
	// }
	
//	public void post(String url, MimeMessage mail) throws MessagingException, KnownError {
//
//		final int endOfScheme = url.indexOf(':');
//
//		if (endOfScheme < 0) {
//			throw new MessagingException("Malformed URI");
//		}
//
//		else {
//
//			final String scheme = url.substring(0, endOfScheme);
//			if ("mailbox".equals(scheme)) {
//				final int startOfUser = endOfScheme + 3;
//				final int endOfUser = url.indexOf('@', startOfUser);
//				if (endOfUser < 0) {
//					// TODO: When user missing, append to a default location
//					throw new MessagingException("Shared mailbox is not supported");
//				} else {
//					// lowerCase the user - see
//					// https://issues.apache.org/jira/browse/JAMES-1369
//					String user = url.substring(startOfUser, endOfUser).toLowerCase();
//					final int startOfHost = endOfUser + 1;
//					final int endOfHost = url.indexOf('/', startOfHost);
//					final String host = url.substring(startOfHost, endOfHost);
//					final String urlPath;
//					final int length = url.length();
//					if (endOfHost + 1 == length) {
//						urlPath = this.folder;
//					} else {
//						urlPath = url.substring(endOfHost, length);
//					}
//
//					// Check if we should use the full email address as username
//					// if (usersRepos.supportVirtualHosting()) {
//					user = user + "@" + host;
//					// }
//
//					MailboxSession session;
//					// try {
//					session = mailboxManager.createSystemSession(user);
//					// } catch (BadCredentialsException e) {
//					// throw new
//					// MessagingException("Unable to authenticate to mailbox",
//					// e);
//					// } catch (MailboxException e) {
//					// throw new MessagingException("Can not access mailbox",
//					// e);
//					// }
//
//					// Start processing request
//					mailboxManager.startProcessingRequest(session);
//
//					// This allows Sieve scripts to use a standard delimiter
//					// regardless of mailbox implementation
//					String destination = urlPath.replace('/', session.getPathDelimiter());
//
//					if (destination == null || "".equals(destination)) {
//						destination = this.folder;
//					}
//					if (destination.startsWith(session.getPathDelimiter() + ""))
//						destination = destination.substring(1);
//
//					// Use the MailboxSession to construct the MailboxPath - See
//					// JAMES-1326
//					final MailboxPath path = new MailboxPath(MailboxConstants.USER_NAMESPACE, user, this.folder);
//					try {
//						if (this.folder.equalsIgnoreCase(destination) && !(mailboxManager.mailboxExists(path, session))) {
//							mailboxManager.createMailbox(path, session);
//						}
//						final MessageManager mailbox = mailboxManager.getMailbox(path, session);
//						if (mailbox == null) {
//							final String error = "Mailbox for user " + user + " was not found on this server.";
//							throw new MessagingException(error);
//						}
//
//						mailbox.appendMessage(new MimeMessageInputStream(mail), new Date(), session, true, null);
//
//					} catch (MailboxException e) {
//						throw new MessagingException("Unable to access mailbox.", e);
//					} finally {
//						session.close();
//						try {
//							mailboxManager.logout(session, true);
//						} catch (KnownError e) {
//							throw new MessagingException("Can logout from mailbox", e);
//						}
//
//						// Stop processing request
//						mailboxManager.endProcessingRequest(session);
//
//					}
//				}
//
//			} else {
//				// TODO: add support for more protocols
//				// TODO: - for example mailto: for forwarding over SMTP
//				// TODO: - for example xmpp: for forwarding over Jabber
//				throw new MessagingException("Unsupported protocol");
//			}
//		}
//	}

}