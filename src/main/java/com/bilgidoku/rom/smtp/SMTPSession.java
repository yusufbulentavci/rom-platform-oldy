package com.bilgidoku.rom.smtp;
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


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import com.bilgidoku.rom.protokol.protocols.ProtocolConfiguration;
import com.bilgidoku.rom.protokol.protocols.ProtocolSession;
import com.bilgidoku.rom.smtp.core.fastfail.ServerAttitude;

/**
 * All the handlers access this interface to communicate with
 * SMTPHandler object
 */

public interface SMTPSession extends ProtocolSession<SMTPSessionActivity>{

    // Keys used to store/lookup data in the internal state hash map
    /** Sender's email address */
//    final static String SENDER = "SENDER_ADDRESS";
//    /** The message recipients */
//    final static String RCPT_LIST = "RCPT_LIST";  
//    /** HELO or EHLO */
//    final static String CURRENT_HELO_MODE = "CURRENT_HELO_MODE";
//    final static String CURRENT_HELO_NAME = "CURRENT_HELO_NAME";

    
    /**
     * Set if reallying is allowed
     * 
     * @param relayingAllowed
     */
    void setRelayingAllowed(boolean relayingAllowed);

    /**
     * Returns whether Authentication is required or not
     *
     * @return authentication required or not
     */
    boolean isAuthSupported();

    
    /**
     * Returns the recipient count
     * 
     * @return recipient count
     */
    int getRcptCount();
    
	public boolean verifyIdentity();


	SmtpMailAddress getSender();


	List<SmtpMailAddress> getRcptList();


	String getCurHeloName();


	void setHeloMode(String commandName);


	void setHeloName(String parameters);


	String getHeloMode();


	void setSender(SmtpMailAddress senderAddress);


	void setRcptList(List<SmtpMailAddress> rcptColl);


	SmtpMailAddress getCurRcpt();


	void setCurRcpt(SmtpMailAddress recipientAddress);


	void setMailEnvelope(MailEnvelopeImpl env);


	MailEnvelopeImpl getMailEnvelope();


	void setHeadersComplete(Boolean true1);


	Boolean getHeadersComplete();


	OutputStream getMimeStreamSource();


	File getMimeFile();


	void setMimeStreamSource(FileOutputStream os);


	void setMimeFile(File f);


	Object getMail();


	Boolean getMesgFailed();


	Long getCurrentSize();


	void setMesgFailed(Boolean true1);


	void setMesgSize(long newSize);


	void setSpfHeader(String headerText);


	void setSpfDetail(String explanation);


	void setSpfBlockListed(Boolean true1);


	void setSpfTempBlockListed(Boolean string);


	Boolean getSpfBlockListed();


	Boolean getSpfTempBlockListed();


	void setHeaderSuffixAdded(String headersSuffixAdded);
	void setHeaderPrefixAdded(String headersPrefixAdded);


	String getHeaderSuffixAdded();


	Object getHeaderPrefixAdded();


	void setCurCommand(String command);


	String getCurCommand();


	ServerAttitude getRblBlockListedMailAttName();


	Integer getUnknownCommandCount();


	void setUnknownCommandCount(Integer count);


	void setBadEhlo(Boolean true1);


	Boolean getBadEhlo();


	void setDkimHeader(String string);


	Integer optHostId();
	String optUserName();

	SMTPConfiguration getSmtpConfiguration();

	void setServerAttitude(ServerAttitude sa);

}

