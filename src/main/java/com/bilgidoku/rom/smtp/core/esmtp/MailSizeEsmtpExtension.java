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

package com.bilgidoku.rom.smtp.core.esmtp;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.smtp.MailEnvelope;
import com.bilgidoku.rom.smtp.SMTPRetCode;
import com.bilgidoku.rom.smtp.core.DataLineFilter;
import com.bilgidoku.rom.smtp.dsn.DSNStatus;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.HookReturnCode;
import com.bilgidoku.rom.smtp.hook.MailParametersHook;
import com.bilgidoku.rom.smtp.hook.MessageHook;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.protokol.protocols.handler.LineHandler;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.smtp.SMTPSession;
import com.bilgidoku.rom.protokol.protocols.ProtocolSession.State;


/**
 * Handle the ESMTP SIZE extension.
 */
public class MailSizeEsmtpExtension implements MailParametersHook, EhloExtension, DataLineFilter, MessageHook {
	private static final MC mc=new MC(MailSizeEsmtpExtension.class);

//    private final static String MESG_SIZE = "MESG_SIZE"; // The size of the
    private final static String MESG_FAILED = "MESG_FAILED";   // Message failed flag
    private final static String[] MAIL_PARAMS = { "SIZE" };
    
    private static final HookResult SYNTAX_ERROR = new HookResult(HookReturnCode.DENY, SMTPRetCode.SYNTAX_ERROR_ARGUMENTS, DSNStatus.getStatus(DSNStatus.PERMANENT, DSNStatus.DELIVERY_INVALID_ARG) + " Syntactically incorrect value for SIZE parameter");
    private static final HookResult QUOTA_EXCEEDED = new HookResult(HookReturnCode.DENY, SMTPRetCode.QUOTA_EXCEEDED, DSNStatus.getStatus(DSNStatus.PERMANENT, DSNStatus.SYSTEM_MSG_TOO_BIG) + " Message size exceeds fixed maximum message size");



    public HookResult doMailParameter(SMTPSession session, String paramName,
            String paramValue) {
        HookResult res = doMailSize(session, paramValue);
        return res;
    }

    public String[] getMailParamNames() {
        return MAIL_PARAMS;
    }

    @SuppressWarnings("unchecked")
    public List<String> getImplementedEsmtpFeatures(SMTPSession session) {
        // Extension defined in RFC 1870
        long maxMessageSize = session.getSmtpConfiguration().getMaxMessageSize();
        if (maxMessageSize > 0) {
            return Arrays.asList("SIZE " + maxMessageSize);
        } else {
            return Collections.EMPTY_LIST;
        }
    }


    private static final Astate incorsize=mc.c("esmtp-incorrect-size-param");
    private static final Astate quota=mc.c("quota-exceeded");
    /**
     * Handles the SIZE MAIL option.
     * 
     * @param session
     *            SMTP session object
     * @param mailOptionValue
     *            the option string passed in with the SIZE option
     * @param tempSender
     *            the sender specified in this mail command (for logging
     *            purpose)
     * @return true if further options should be processed, false otherwise
     */
    private HookResult doMailSize(SMTPSession session,
            String mailOptionValue) {
        int size = 0;
        try {
            size = Integer.parseInt(mailOptionValue);
        } catch (NumberFormatException pe) {
            incorsize.more(mailOptionValue);
            // This is a malformed option value. We return an error
            return SYNTAX_ERROR;
        }
        long maxMessageSize = session.getSmtpConfiguration().getMaxMessageSize();
        if ((maxMessageSize > 0) && (size > maxMessageSize)) {
            // Let the client know that the size limit has been hit.
//            StringBuilder errorBuffer = new StringBuilder(256).append(
//                    "Rejected message from ").append(
//                    tempSender != null ? tempSender : null).append(
//                    " from ")
//                    .append(session.getRemoteAddress().getAddress().getHostAddress()).append(" of size ")
//                    .append(size).append(
//                            " exceeding system maximum message size of ")
//                    .append(maxMessageSize).append("based on SIZE option.");
        	quota.more();
            return QUOTA_EXCEEDED;
        } else {
            // put the message size in the message state so it can be used
            // later to restrict messages for user quotas, etc.
            session.setMesgSize(Integer.valueOf(size));
        }
        return null;
    }


    private static final Astate maxmsgsize=mc.c("esmtp-max-msg-size");
    public Response onLine(SMTPSession session, ByteBuffer line, LineHandler<SMTPSession> next) throws KnownError {
        Response response = null;
    	Boolean failed = session.getMesgFailed();
        // If we already defined we failed and sent a reply we should simply
        // wait for a CRLF.CRLF to be sent by the client.
        if (failed != null && failed.booleanValue()) {
            // TODO
        } else {
            if (line.remaining() == 3 && line.get() == 46) {
                line.rewind();
                response = next.onLine(session, line);
            } else {
                line.rewind();
                Long currentSize = session.getCurrentSize();
                Long newSize;
                if (currentSize == null) {
                    newSize = Long.valueOf(line.remaining());
                } else {
                    newSize = Long.valueOf(currentSize.intValue()+line.remaining());
                }
                
                if (session.getSmtpConfiguration().getMaxMessageSize() > 0 && newSize.intValue() > session.getSmtpConfiguration().getMaxMessageSize()) {
                    // Add an item to the state to suppress
                    // logging of extra lines of data
                    // that are sent after the size limit has
                    // been hit.
                    session.setMesgFailed(Boolean.TRUE);
                    // then let the client know that the size
                    // limit has been hit.
                    response = next.onLine(session, ByteBuffer.wrap(".\r\n".getBytes()));
                    maxmsgsize.more();
                } else {
                    line.rewind();
                    response = next.onLine(session, line);
                }
                
                session.setMesgSize(newSize);
            }
        }
        return response;
    }

    public HookResult onMessage(SMTPSession session, MailEnvelope mail) {
        Boolean failed = session.getMesgFailed();
        if (failed != null && failed.booleanValue()) {
            
//            StringBuilder errorBuffer = new StringBuilder(256).append(
//                    "Rejected message from ").append(
//                    session.getAttachment(SMTPSession.SENDER, State.Transaction).toString())
//                    .append(" from ").append(session.getRemoteAddress().getAddress().getHostAddress())
//                    .append(" exceeding system maximum message size of ")
//                    .append(
//                            session.getConfiguration().getMaxMessageSize());
//            session.getLogger().error(errorBuffer.toString());
        	quota.more();
            return QUOTA_EXCEEDED;
        } else {
            return HookResult.declined();
        }
    }

}
