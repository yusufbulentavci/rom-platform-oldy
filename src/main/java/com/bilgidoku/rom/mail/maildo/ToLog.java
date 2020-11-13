/****************************************************************
 * Licensed to the Apache Software Foundation (ASsF) under one   *
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

import com.bilgidoku.rom.mail.Email;
import com.bilgidoku.rom.mail.MailProcessContext;

/**
 * Stores incoming Mail in the specified Repository.<br>
 * If the "passThrough" in confs is true the mail will be returned untouched in
 * the pipe. If false will be destroyed.
 * 
 * @version 1.0.0, 24/04/1999
 */
public class ToLog extends AbstractMailDo {


    /**
     * Whether this mailet should allow mails to be processed by additional
     * mailets or mark it as finished.
     */
//    = false;
	private final String to;

    
    public ToLog(MailProcessContext context, String to){
    	super(context,null);
    	this.to=to;
    }


    /**
     * Store a mail in a particular repository.
     * 
     * @param mail
     *            the mail to process
     */
    public void service(Email mail)  {
//        StringBuffer logBuffer = new StringBuffer(160).append("Storing mail ").append(mail.getName()).append(" in ").append(repositoryPath);
//        log(logBuffer.toString());
        context.log(to, mail);
        
    }


	@Override
	public String getMailetName() {
		return "ToLog";
	}
}
