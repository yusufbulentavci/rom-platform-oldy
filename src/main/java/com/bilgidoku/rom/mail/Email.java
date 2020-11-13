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


package com.bilgidoku.rom.mail;
import java.io.InputStream;
import java.util.Collection;

import com.bilgidoku.rom.epostatemel.javam.mail.internet.MailAddress;
import com.bilgidoku.rom.epostatemel.mail.smtp.SMTPMessage;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.json.JSONObject;

/**
 * Wrap a MimeMessage with routing information (from SMTP) such
 * as SMTP specified recipients, sender, and ip address and hostname
 * of sending server.  It also contains its state which represents
 * which processor in the mailet container it is currently running.
 * Special processor names are "root" and "error".
 *
 * @version CVS $Revision: 534853 $ $Date: 2007-05-03 16:39:16 +0300 (Thu, 03 May 2007) $
 */
public interface Email {
    
    String[] dbfs() throws KnownError;
    
    /**
     * Returns the message name of this message
     * 
     * @return the message name
     * @since Mailet API v2.3
     */
    String getName();
    
    /**
     * Returns a Collection of MailAddress objects that are recipients of this message
     *
     * @return a Collection of MailAddress objects that are recipients of this message
     */
    Collection<MailAddress> getRecipients();
    /**
     * Method setRecipients.
     * @param recipients a Collection of MailAddress Objects representing the recipients of this message
     * @since Mailet API v3.0-unstable
     */
    void setRecipients(Collection<MailAddress> recipients);
    /**
     * The sender of the message, as specified by the MAIL FROM header, or internally defined
     *
     * @return a MailAddress of the sender of this message
     */
    MailAddress getSender();
    
    /**
     * The remote hostname of the server that connected to send this message
     *
     * @return a String of the hostname of the server that connected to send this message
     */
    String getRemoteHost();
    /**
     * The remote ip address of the server that connected to send this message
     *
     * @return a String of the ip address of the server that connected to send this message
     */
    String getRemoteAddr();
    

	public JSONObject getJson() throws KnownError;

	void setFromRepository(boolean b);

//	MailPrioritySupport.HIGH_PRIORITY
	void setPriority(int highPriority);

	String getDeliveryError();

//	void setInfected(boolean b);

	InputStream getInputStream() throws KnownError;
	long getMailSize() throws KnownError;
	void deliveryError(String cause);
	SMTPMessage getInputMessage() throws KnownError;

	void symLink(int intra) throws KnownError;

	JSONObject getMimeJson();

	void summary(StringBuilder sb) throws KnownError;
	
	Integer getHostId();
	String getUserName();
	int spam();
	
	String getRequestId();
}
