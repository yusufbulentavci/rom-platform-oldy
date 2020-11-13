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

import java.util.Collection;

import com.bilgidoku.rom.smtp.SMTPRetCode;
import com.bilgidoku.rom.smtp.SmtpMailAddress;
import com.bilgidoku.rom.smtp.dsn.DSNStatus;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.HookReturnCode;
import com.bilgidoku.rom.smtp.hook.RcptHook;
import com.bilgidoku.rom.smtp.SMTPSession;

/**
 * 
 * This handler can be used to just ignore duplicated recipients. 
 */
public class SupressDuplicateRcptHandler implements RcptHook {

    /**
     * @see com.bilgidoku.rom.smtp.hook.RcptHook#doRcpt(com.bilgidoku.rom.smtp.SMTPSession, org.apache.SmtpMailAddress.MailAddress, org.apache.SmtpMailAddress.MailAddress)
     */
    @SuppressWarnings("unchecked")
    public HookResult doRcpt(SMTPSession session, SmtpMailAddress sender, SmtpMailAddress rcpt) {
        Collection<SmtpMailAddress> rcptList = session.getRcptList();
    
        // Check if the recipient is already in the rcpt list
        if(rcptList != null && rcptList.contains(rcpt)) {
            StringBuilder responseBuffer = new StringBuilder();
        
            responseBuffer.append(DSNStatus.getStatus(DSNStatus.SUCCESS, DSNStatus.ADDRESS_VALID))
                          .append(" Recipient <")
                          .append(rcpt.toString())
                          .append("> OK");
//            session.getLogger().debug("Duplicate recipient not add to recipient list: " + rcpt.toString());
            return new HookResult(HookReturnCode.OK,SMTPRetCode.MAIL_OK, responseBuffer.toString());
        }
        return HookResult.declined();
    }
}
