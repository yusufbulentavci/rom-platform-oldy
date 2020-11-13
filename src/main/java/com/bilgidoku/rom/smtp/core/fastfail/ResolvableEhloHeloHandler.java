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

import com.bilgidoku.rom.smtp.SMTPRetCode;
import com.bilgidoku.rom.smtp.SmtpMailAddress;
import com.bilgidoku.rom.smtp.dsn.DSNStatus;
import com.bilgidoku.rom.smtp.hook.HeloHook;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.HookReturnCode;
import com.bilgidoku.rom.smtp.hook.RcptHook;
import com.bilgidoku.rom.smtp.SMTPSession;
import com.bilgidoku.rom.protokol.protocols.ProtocolSession.State;


/**
 * This CommandHandler can be used to reject not resolvable EHLO/HELO
 */
public class ResolvableEhloHeloHandler implements RcptHook, HeloHook {

//    public final static String BAD_EHLO_HELO = "BAD_EHLO_HELO";

    /**
     * Check if EHLO/HELO is resolvable
     * 
     * @param session
     *            The SMTPSession
     * @param argument
     *            The argument
     */
    protected void checkEhloHelo(SMTPSession session, String argument) {
        
        if (isBadHelo(session, argument)) {
            session.setBadEhlo(Boolean.TRUE);
        }
    }
    
    protected String resolve(String host) throws UnknownHostException {
        return InetAddress.getByName(host).getHostName();
    }
    /**
     * @param session the SMTPSession
     * @param argument the argument
     * @return true if the helo is bad.
     */
    protected boolean isBadHelo(SMTPSession session, String argument) {
        // try to resolv the provided helo. If it can not resolved do not
        // accept it.
        try {
            resolve(argument);
        } catch (UnknownHostException e) {
            return true;
        }
        return false;
        
    }

    protected boolean check(SMTPSession session,SmtpMailAddress rcpt) {
        // not reject it
        if (session.getBadEhlo() == null) {
            return false;
        }

        return true;
    }

    /**
     * @see com.bilgidoku.rom.smtp.hook.RcptHook#doRcpt(com.bilgidoku.rom.smtp.SMTPSession, org.apache.SmtpMailAddress.MailAddress, org.apache.SmtpMailAddress.MailAddress)
     */
    public HookResult doRcpt(SMTPSession session, SmtpMailAddress sender, SmtpMailAddress rcpt) {
        if (check(session,rcpt)) {
            return new HookResult(HookReturnCode.DENY,SMTPRetCode.SYNTAX_ERROR_ARGUMENTS,DSNStatus.getStatus(DSNStatus.PERMANENT, DSNStatus.DELIVERY_INVALID_ARG)
                    + " Provided EHLO/HELO " + session.getCurHeloName() + " can not resolved.");
        } else {
            return HookResult.declined();
        }
    }

    /**
     * @see com.bilgidoku.rom.smtp.hook.HeloHook#doHelo(com.bilgidoku.rom.smtp.SMTPSession, java.lang.String)
     */
    public HookResult doHelo(SMTPSession session, String helo) {
        checkEhloHelo(session, helo);
        return HookResult.declined();
    }

}
