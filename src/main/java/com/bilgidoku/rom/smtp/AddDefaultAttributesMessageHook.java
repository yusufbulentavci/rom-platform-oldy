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
package com.bilgidoku.rom.smtp;

import com.bilgidoku.rom.mail.Email;
import com.bilgidoku.rom.mail.EmailImpl;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.HookReturnCode;

/**
 * This hook adds the default attributes to the just created Mail
 */
public class AddDefaultAttributesMessageHook implements JamesMessageHook {

    /**
     * The mail attribute holding the SMTP AUTH user name, if any.
     */
    private final static String SMTP_AUTH_USER_ATTRIBUTE_NAME = "org.apache.james.SMTPAuthUser";

    /**
     * The mail attribute which get set if the client is allowed to relay
     */
    private final static String SMTP_AUTH_NETWORK_NAME = "org.apache.james.SMTPIsAuthNetwork";

    public HookResult onMessage(SMTPSession session, Email mail) {
        if (mail instanceof EmailImpl) {

//            final EmailImpl mailImpl = (EmailImpl) mail;
//            mailImpl.setRemoteHost(session.getRemoteAddress().getHostName());
//            mailImpl.setRemoteAddr(session.getRemoteAddress().getAddress().getHostAddress());
//            if (session.getUser() != null) {
////                mail.setAttribute(SMTP_AUTH_USER_ATTRIBUTE_NAME, session.getUser());
//                mail.setSmtpAuthUser(session.getUser());
//            }
//
//            if (session.isRelayingAllowed()) {
////                mail.setAttribute(SMTP_AUTH_NETWORK_NAME, "true");
//                mail.setSmtpAuthNetworkName(true);
//            }
        }
        return new HookResult(HookReturnCode.DECLINED);
    }

}
