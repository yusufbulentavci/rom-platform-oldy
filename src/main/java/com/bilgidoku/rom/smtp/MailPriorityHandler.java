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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.bilgidoku.rom.epostatemel.javam.mail.internet.MailAddress;

import com.bilgidoku.rom.mail.Email;
import com.bilgidoku.rom.protokol.protocols.handler.LifecycleAwareProtocolHandler;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.HookReturnCode;


/**
 * Handler which set a configured {@link Email} priority for the mail.
 * 
 * if the {@link Email} has more then one recipient, then the highest priority
 * (which was found) is set
 */
public class MailPriorityHandler implements JamesMessageHook, LifecycleAwareProtocolHandler {

    private Map<String, Integer> prioMap = new HashMap<String, Integer>();

    public MailPriorityHandler(){
//    	 List<HierarchicalConfiguration> entries = ((HierarchicalConfiguration)config).configurationsAt("priorityEntries.priorityEntry");
//         for (int i = 0; i < entries.size(); i++) {
//             HierarchicalConfiguration prioConf = entries.get(i);
//             String domain = prioConf.getString("domain");
//             int prio = prioConf.getInt("priority", MailPrioritySupport.NORMAL_PRIORITY);
//             if (prio > MailPrioritySupport.HIGH_PRIORITY || prio < MailPrioritySupport.LOW_PRIORITY) {
//                 throw new ConfigurationException("configured priority must be >= " + MailPrioritySupport.LOW_PRIORITY + " and <= " + MailPrioritySupport.HIGH_PRIORITY);
//             }
//             prioMap.put(domain, prio);
//         }        
    }
    /**
     * @see
     * org.apache.james.smtpserver.JamesMessageHook#onMessage(com.bilgidoku.rom.smtp.SMTPSession,
     *  com.Email.bilgidoku.rom.mail.mailprocess.Mail)
     */
    @SuppressWarnings("unchecked")
    public HookResult onMessage(SMTPSession session, Email mail) {
        Iterator<MailAddress> rcpts = mail.getRecipients().iterator();

        Integer p = null;

        while (rcpts.hasNext()) {
            String domain = rcpts.next().getDomain();
            Integer prio = null;
            if (domain != null) {
                prio = prioMap.get(domain);
                if (prio != null) {
                    if (p == null || prio > p) {
                        p = prio;
                    }

                    // already the highest priority
//                    if (p == MailPrioritySupport.HIGH_PRIORITY)
//                        break;
                }
            }
        }

        // set the priority if one was found
        if (p != null){
//        	mail.setAttribute(MailPrioritySupport.MAIL_PRIORITY, p);
        	mail.setPriority(p);
        }
        return new HookResult(HookReturnCode.DECLINED);
    }


    @Override
    public void destroy() {
        // nothing todo
    }

}
