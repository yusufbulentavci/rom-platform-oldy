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

package com.bilgidoku.rom.pop3;

import java.io.InputStream;
import java.util.List;

import com.bilgidoku.rom.mail.MailOnDb;
import com.bilgidoku.rom.protokol.protocols.ProtocolSession;
import com.bilgidoku.rom.shared.err.KnownError;

/**
 * All the handlers access this interface to communicate with POP3Handler object
 */

public interface POP3Session extends ProtocolSession<POP3SessionActivity> {

//    final static String UID_LIST = "UID_LIST";
//    final static String DELETED_UID_LIST = "DELETED_UID_LIST";
//    final static String APOP_TIMESTAMP = "APOP_TIMESTAMP";

    // Authentication states for the POP3 interaction
    /** Waiting for user id */
    final static int AUTHENTICATION_READY = 0;
    /** User id provided, waiting for password */
    final static int AUTHENTICATION_USERSET = 1;
    /**
     * A valid user id/password combination has been provided. In this state the
     * client can access the mailbox of the specified user.
     */
    final static int TRANSACTION = 2;

    /**
     * Returns the current handler state
     * 
     * @return handler state
     */
    int getHandlerState();

    /**
     * Sets the new handler state
     * 
     * @param handlerState
     *            state
     */
    void setHandlerState(int handlerState);

	void delete(int num);

	void stat() throws KnownError;

	int mailCount();

	long mailSize() throws KnownError;

	boolean isDeleted(int num);

	InputStream getInputStream(int num) throws KnownError;

	List<MailOnDb> mails();
	
	MailOnDb mail(int num);

	void commit() throws KnownError;

	// Connection
	String getApopTimestamp();

	void setApopTimestamp(String string);
}
