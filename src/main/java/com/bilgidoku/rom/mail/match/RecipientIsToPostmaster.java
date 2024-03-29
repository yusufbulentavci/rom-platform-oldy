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



package com.bilgidoku.rom.mail.match;

import com.bilgidoku.rom.epostatemel.javam.mail.internet.MailAddress;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.mail.MailProcessContext;

/**
 * Matches mail where the recipient is local.
 * @version 1.0.0, 24/04/1999
 */
public class RecipientIsToPostmaster extends AbstractRecipientMatcher {

    public RecipientIsToPostmaster(MailProcessContext context) {
		super(context, null);
	}

	public boolean matchRecipient(MailAddress recipient) throws KnownError {
		String local = recipient.getLocalPart();
		return (local.equalsIgnoreCase("postmaster") || local.equalsIgnoreCase("abuse"));
    }
}
