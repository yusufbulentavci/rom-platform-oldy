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

package com.bilgidoku.rom.smtp.core.fastfail;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;
import java.util.StringTokenizer;

import com.bilgidoku.rom.smtp.SmtpMailAddress;
import com.bilgidoku.rom.smtp.dsn.DSNStatus;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.HookReturnCode;
import com.bilgidoku.rom.smtp.hook.RcptHook;
import com.bilgidoku.rom.smtp.SMTPSession;
import com.bilgidoku.rom.protokol.protocols.ProtocolSession.State;

/**
  * Connect handler for DNSRBL processing
  *   <!-- This command handler check against RBL-Lists -->
            <!-- If getDetail is set to true it try to retrieve information from TXT Record -->
            <!-- why the ip was blocked. Default to false -->

  *      <!-- STOP - before you uncomment out the DNS RBL handler,
                  please take a moment to review each block list.  We
                  have included some that various JAMES committers use,
                  but you must decide which, if any, are appropriate
                  for your environment.  The mail servers hosting
                  @apache.org mailing lists, for example, use a
                  slightly different list than we have included below.
                  And it is likely that most JAMES committes also have
                  slightly different sets of lists.  The SpamAssassin
                  user's list would be one good place to discuss the
                  measured quality of various block lists.

                  NOTA BENE: the domain names, below, are terminated
                  with '.' to ensure that they are absolute names in
                  DNS lookups.  Under some circumstances, names that
                  are not explicitly absolute could be treated as
                  relative names, leading to incorrect results.  This
                  has been observed on *nix and MS-Windows platforms
                  by users of multiple mail servers, and is not JAMES
                  specific.  If you are unsure what this means for you,
                  please speak with your local system/network admins.

  */
public class DNSRBLHandler implements RcptHook {

	/**
	 * The lists of rbl servers to be checked to limit spam
	 */
	private String[] whitelist;
	private String[] blacklist;

	private boolean getDetail = false;

	private String blocklistedDetail = null;

	public static final String RBL_BLOCKLISTED_MAIL_ATTRIBUTE_NAME = "org.apache.james.smtpserver.rbl.blocklisted";

	public static final String RBL_DETAIL_MAIL_ATTRIBUTE_NAME = "org.apache.james.smtpserver.rbl.detail";

	/**
	 * Set the whitelist array
	 * 
	 * @param whitelist The array which contains the whitelist
	 */
	public void setWhitelist(String[] whitelist) {
		// We need to copy the String array because of possible security issues.
		// Similar to https://issues.apache.org/jira/browse/PROTOCOLS-18
		if (whitelist != null) {
			this.whitelist = new String[whitelist.length];
			for (int i = 0; i < whitelist.length; i++) {
				this.whitelist[i] = new String(whitelist[i]);
			}
		}
		this.whitelist = whitelist;
	}

	/**
	 * Set the blacklist array
	 * 
	 * @param blacklist The array which contains the blacklist
	 */
	public void setBlacklist(String[] blacklist) {
		// We need to copy the String array because of possible security issues.
		// Similar to https://issues.apache.org/jira/browse/PROTOCOLS-18
		if (blacklist != null) {
			this.blacklist = new String[blacklist.length];
			for (int i = 0; i < blacklist.length; i++) {
				this.blacklist[i] = new String(blacklist[i]);
			}
		}
	}

	/**
	 * Set for try to get a TXT record for the blocked record. 
	 * 
	 * @param getDetail Set to ture for enable
	 */
	public void setGetDetail(boolean getDetail) {
		this.getDetail = getDetail;
	}

	/**
	 *
	 * This checks DNSRBL whitelists and blacklists.  If the remote IP is whitelisted
	 * it will be permitted to send e-mail, otherwise if the remote IP is blacklisted,
	 * the sender will only be permitted to send e-mail to postmaster (RFC 2821) or
	 * abuse (RFC 2142), unless authenticated.
	 */

	public void checkDNSRBL(SMTPSession session, String ipAddress) {
		
		ServerAttitude sa = ZenSpam.learn(ipAddress);
		

		/*
		 * don't check against rbllists if the client is allowed to relay..
		 * This whould make no sense.
		 */

//		if (whitelist != null || blacklist != null) {
//			StringBuffer sb = new StringBuffer();
//			StringTokenizer st = new StringTokenizer(ipAddress, " .", false);
//			while (st.hasMoreTokens()) {
//				sb.insert(0, st.nextToken() + ".");
//			}
//			String reversedOctets = sb.toString();
//
//			if (whitelist != null) {
//				String[] rblList = whitelist;
//				for (int i = 0; i < rblList.length; i++) {
//					if (resolve(reversedOctets + rblList[i])) {
//						//                        if (session.getLogger().isInfoEnabled()) {
//						//                            session.getLogger().info("Connection from " + ipAddress + " whitelisted by " + rblList[i]);
//						//                        }
//
//						return;
//					} else {
//						//                        if (session.getLogger().isDebugEnabled()) {
//						//                            session.getLogger().debug("IpAddress " + session.getRemoteAddress().getAddress()  + " not listed on " + rblList[i]);
//						//                        }
//					}
//				}
//			}
//
//			if (blacklist != null) {
//				String[] rblList = blacklist;
//				for (int i = 0; i < rblList.length; i++) {
//					if (resolve(reversedOctets + rblList[i])) {
//						//                        if (session.getLogger().isInfoEnabled()) {
//						//                            session.getLogger().info("Connection from " + ipAddress + " restricted by " + rblList[i] + " to SMTP AUTH/postmaster/abuse.");
//						//                        }
//
//						// we should try to retrieve details
//						if (getDetail) {
//							Collection<String> txt = resolveTXTRecords(reversedOctets + rblList[i]);
//
//							// Check if we found a txt record
//							if (!txt.isEmpty()) {
//								// Set the detail
//								String blocklistedDetail = txt.iterator().next().toString();
//
//								session.setRblDetailMailAttName(blocklistedDetail);
//							}
//						}
//
//						session.setRblBlockListedMailAttName("true");
//						return;
//					} else {
//						// if it is unknown, it isn't blocked
//						//                        if (session.getLogger().isDebugEnabled()) {
//						//                            session.getLogger().debug("unknown host exception thrown:" + rblList[i]);
//						//                        }
//					}
//
//				}
//			}
//		}
	}

	/**
	 * Check if the given ipaddress is resolvable. 
	 * 
	 * This implementation use {@link InetAddress#getByName(String)}. Sub-classes may override this with a more performant solution
	 * 
	 * @param ip
	 * @return canResolve
	 */
	protected ServerAttitude resolve(String ip) {
		return ZenSpam.learn(ip);
	}

	/**
	 * Return a {@link Collection} which holds all TXT records for the ip. This is most times used to add details for a RBL entry.
	 * 
	 * This implementation always returns an empty {@link Collection}. Sub-classes may override this.
	 * 
	 * @param ip
	 * @return txtRecords
	 */
	protected Collection<String> resolveTXTRecords(String ip) {
		return Collections.<String> emptyList();
	}

	/**
	 * @see com.bilgidoku.rom.smtp.hook.RcptHook#doRcpt(com.bilgidoku.rom.smtp.SMTPSession, org.apache.SmtpMailAddress.MailAddress, org.apache.SmtpMailAddress.MailAddress)
	 */
	public HookResult doRcpt(SMTPSession session, SmtpMailAddress sender, SmtpMailAddress rcpt) {
		// Do not check for out local users
		if(session.getProtocolUser()!=null)
			return HookResult.declined();
		
		ServerAttitude sa = ZenSpam.learn(session.getRemoteAddress().getAddress().getHostAddress());
		
		session.setServerAttitude(sa);

//		if (blocklisted != null) { // was found in the RBL
//			if (blocklistedDetail == null) {
//				return new HookResult(HookReturnCode.DENY, DSNStatus.getStatus(DSNStatus.PERMANENT,
//						DSNStatus.SECURITY_AUTH)
//						+ " Rejected: unauthenticated e-mail from "
//						+ session.getRemoteAddress().getAddress()
//						+ " is restricted.  Contact the postmaster for details.");
//			} else {
//				return new HookResult(HookReturnCode.DENY, DSNStatus.getStatus(DSNStatus.PERMANENT,
//						DSNStatus.SECURITY_AUTH) + " " + blocklistedDetail);
//			}
//		}
		return HookResult.declined();
	}
}
