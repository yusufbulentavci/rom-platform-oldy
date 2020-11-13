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

import com.bilgidoku.rom.smtp.SMTPRetCode;
import com.bilgidoku.rom.smtp.SmtpMailAddress;
import com.bilgidoku.rom.smtp.dsn.DSNStatus;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.HookReturnCode;
import com.bilgidoku.rom.smtp.hook.MailHook;
import com.bilgidoku.rom.smtp.SMTPSession;

/**
 * Add MFDNSCheck feature to SMTPServer. This handler reject mail from domains which have not an an valid MX record.  
 * 
 */
public abstract class ValidSenderDomainHandler implements MailHook { 


    /**
     * @see com.bilgidoku.rom.smtp.hook.MailHook#doMail(com.bilgidoku.rom.smtp.SMTPSession, org.apache.SmtpMailAddress.MailAddress)
     */
    public HookResult doMail(SMTPSession session, SmtpMailAddress sender) {
        if (sender != null  && !hasMXRecord(session,sender.getDomain())) {
            return new HookResult(HookReturnCode.DENY,SMTPRetCode.SYNTAX_ERROR_ARGUMENTS,DSNStatus.getStatus(DSNStatus.PERMANENT,DSNStatus.ADDRESS_SYNTAX_SENDER)+ " sender " + sender + " contains a domain with no valid MX records");
        } else {
            return HookResult.declined();
        }
    }
    
    protected abstract boolean hasMXRecord(SMTPSession session, String domain);
}
