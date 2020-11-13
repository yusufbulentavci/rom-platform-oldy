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
import com.bilgidoku.rom.smtp.hook.RcptHook;
import com.bilgidoku.rom.smtp.SMTPSession;

public class MaxRcptHandler implements RcptHook {

    private static final HookResult MAX_RCPT = new HookResult(HookReturnCode.DENY, SMTPRetCode.SYSTEM_STORAGE_ERROR, DSNStatus.getStatus(DSNStatus.NETWORK, DSNStatus.DELIVERY_TOO_MANY_REC)
            + " Requested action not taken: max recipients reached");
    private int maxRcpt = 0;


    /**
     * Set the max rcpt for wich should be accepted
     * 
     * @param maxRcpt
     *            The max rcpt count
     */
    public void setMaxRcpt(int maxRcpt) {
        this.maxRcpt = maxRcpt;
    }
   
    /**
     * @see com.bilgidoku.rom.smtp.hook.RcptHook#doRcpt(com.bilgidoku.rom.smtp.SMTPSession, org.apache.SmtpMailAddress.MailAddress, org.apache.SmtpMailAddress.MailAddress)
     */
    public HookResult doRcpt(SMTPSession session, SmtpMailAddress sender, SmtpMailAddress rcpt) {
        if ((session.getRcptCount() + 1) > maxRcpt) {
//            session.getLogger().info("Maximum recipients of " + maxRcpt + " reached");
            
            return MAX_RCPT;
        } else {
            return HookResult.declined();
        }
    }
}
