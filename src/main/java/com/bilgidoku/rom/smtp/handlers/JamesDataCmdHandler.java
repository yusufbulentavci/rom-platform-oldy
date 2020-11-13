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
package com.bilgidoku.rom.smtp.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.concurrent.atomic.AtomicInteger;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.pg.dbfs.DbfsGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.smtp.SMTPResponse;
import com.bilgidoku.rom.smtp.SMTPRetCode;
import com.bilgidoku.rom.smtp.SMTPSession;
import com.bilgidoku.rom.smtp.core.DataCmdHandler;

/**
 * handles DATA command
 */
public class JamesDataCmdHandler extends DataCmdHandler {
	private static final MC mc = new MC(JamesDataCmdHandler.class);

	
	private static AtomicInteger count = new AtomicInteger();
	private static final Astate x1 = mc.c("mime-message-gen-error");

	/**
	 * Handler method called upon receipt of a DATA command. Reads in message
	 * data, creates header, and delivers to mail server service for delivery.
	 * 
	 * @param session
	 *            SMTP session object
	 * @param argument
	 *            the argument passed in with the command by the SMTP client
	 */
	protected SMTPResponse doDATA(SMTPSession session, String argument) {
		try {
			File f = DbfsGorevlisi.tek().tempFile(".m.txt");
			FileOutputStream os;
			os = new FileOutputStream(f);
			// MimeMessageInputStreamSource mmiss = new
			// MimeMessageInputStreamSource(count.getAndIncrement()+".tempmail");
			session.setMimeStreamSource(os);
			session.setMimeFile(f);

			// out = new PipedOutputStream(messageIn);
			session.pushLineHandler(getLineHandler());
		} catch (FileNotFoundException | KnownError e) {
			return new SMTPResponse(SMTPRetCode.LOCAL_ERROR, "Unexpected error preparing to receive DATA.");
		}

		return new SMTPResponse(SMTPRetCode.DATA_READY, "Ok Send data ending with <CRLF>.<CRLF>");
	}

}
