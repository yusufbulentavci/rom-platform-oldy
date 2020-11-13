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

package com.bilgidoku.rom.smtp.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.protokol.protocols.handler.CommandHandler;
import com.bilgidoku.rom.smtp.SMTPResponse;
import com.bilgidoku.rom.smtp.SMTPRetCode;
import com.bilgidoku.rom.smtp.SMTPSession;
import com.bilgidoku.rom.smtp.SmtpMailAddress;
import com.bilgidoku.rom.smtp.dsn.DSNStatus;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.RcptHook;

/**
 * 
 * 
 * 
 * 
 * Handles RCPT command
 */
public class RcptCmdHandler extends AbstractHookableCmdHandler<RcptHook> implements
        CommandHandler<SMTPSession> {
	private static final MC mc=new MC(RcptCmdHandler.class);

//    public static final String CURRENT_RECIPIENT = "CURRENT_RECIPIENT"; // Current
                                                                        // recipient
    private static final Collection<String> COMMANDS = Collections.unmodifiableCollection(Arrays.asList("RCPT"));
    private static final Response MAIL_NEEDED = new SMTPResponse(SMTPRetCode.BAD_SEQUENCE, DSNStatus.getStatus(DSNStatus.PERMANENT, DSNStatus.DELIVERY_OTHER) + " Need MAIL before RCPT").immutable();
    private static final Response SYNTAX_ERROR_ARGS = new SMTPResponse(SMTPRetCode.SYNTAX_ERROR_ARGUMENTS, DSNStatus.getStatus(DSNStatus.PERMANENT, DSNStatus.DELIVERY_SYNTAX) + " Usage: RCPT TO:<recipient>").immutable();
    private static final Response SYNTAX_ERROR_DELIVERY = new SMTPResponse(SMTPRetCode.SYNTAX_ERROR_ARGUMENTS, DSNStatus.getStatus(DSNStatus.PERMANENT, DSNStatus.DELIVERY_SYNTAX) + " Syntax error in parameters or arguments").immutable();
    private static final Response SYNTAX_ERROR_ADDRESS = new SMTPResponse(SMTPRetCode.SYNTAX_ERROR_MAILBOX, DSNStatus.getStatus(DSNStatus.PERMANENT, DSNStatus.ADDRESS_SYNTAX) + " Syntax error in recipient address").immutable();
    /**
     * Handler method called upon receipt of a RCPT command. Reads recipient.
     * Does some connection validation.
     * 
     * 
     * @param session
     *            SMTP session object
     * @param command
     *            command passed
     * @param parameters
     *            parameters passed in with the command by the SMTP client
     */
    @SuppressWarnings("unchecked")
    protected Response doCoreCmd(SMTPSession session, String command,
            String parameters) {
        List<SmtpMailAddress> rcptColl = session.getRcptList();
        if (rcptColl == null) {
            rcptColl = new ArrayList<SmtpMailAddress>();
        }
        SmtpMailAddress recipientAddress = session.getCurRcpt();
        
        rcptColl.add(recipientAddress);
        
        session.setRcptList(rcptColl);
        
        StringBuilder response = new StringBuilder();
        response
                .append(
                        DSNStatus.getStatus(DSNStatus.SUCCESS,
                                DSNStatus.ADDRESS_VALID))
                .append(" Recipient <").append(recipientAddress).append("> OK");
        return new SMTPResponse(SMTPRetCode.MAIL_OK, response);

    }

    private final static Astate mailneeded=mc.c("mail-needed");
    private final static Astate seres=mc.c("syntax-error-recipient");
    private final static Astate noto=mc.c("syntax-error-no-to");
    private final static Astate unop=mc.c("unknown-option");
    
    /**
     * 
     * RCPT TO:<alice@example.com>
     * 
     * @param session
     *            SMTP session object
     * @param argument
     *            the argument passed in with the command by the SMTP client
     * @throws KnownError 
     */
    protected Response doFilterChecks(SMTPSession session, String command,
            String argument) throws KnownError {
        String recipient = null;
        if ((argument != null) && (argument.indexOf(":") > 0)) {
            int colonIndex = argument.indexOf(":");
            recipient = argument.substring(colonIndex + 1);
            argument = argument.substring(0, colonIndex);
        }
        if (session.getSender() == null) {
        	mailneeded.more();
            return MAIL_NEEDED;
        } else if (argument == null
                || !argument.toUpperCase(Locale.US).equals("TO")
                || recipient == null) {
        	noto.more();
            return SYNTAX_ERROR_ARGS;
        }

        recipient = recipient.trim();
        int lastChar = recipient.lastIndexOf('>');
        // Check to see if any options are present and, if so, whether they
        // are correctly formatted
        // (separated from the closing angle bracket by a ' ').
        String rcptOptionString = null;
        if ((lastChar > 0) && (recipient.length() > lastChar + 2)
                && (recipient.charAt(lastChar + 1) == ' ')) {
            rcptOptionString = recipient.substring(lastChar + 2);

            // Remove the options from the recipient
            recipient = recipient.substring(0, lastChar + 1);
        }
        if (session.getSmtpConfiguration().useAddressBracketsEnforcement()
                && (!recipient.startsWith("<") || !recipient.endsWith(">"))) {
        	seres.more();
            return SYNTAX_ERROR_DELIVERY;
        }
        SmtpMailAddress recipientAddress = null;
        // Remove < and >
        if (session.getSmtpConfiguration().useAddressBracketsEnforcement()
                || (recipient.startsWith("<") && recipient.endsWith(">"))) {
            recipient = recipient.substring(1, recipient.length() - 1);
        }

//        if (recipient.indexOf("@") < 0) {
//            // set the default domain
//            recipient = recipient
//                    + "@"
//                    + getDefaultDomain();
//        }

        try {
            recipientAddress = new SmtpMailAddress(recipient);
        } catch (Exception pe) {
        	seres.more();
            /*
             * from RFC2822; 553 Requested action not taken: mailbox name
             * not allowed (e.g., mailbox syntax incorrect)
             */
            return SYNTAX_ERROR_ADDRESS;
        }

        if (rcptOptionString != null) {

            StringTokenizer optionTokenizer = new StringTokenizer(
                    rcptOptionString, " ");
            while (optionTokenizer.hasMoreElements()) {
                String rcptOption = optionTokenizer.nextToken();
                int equalIndex = rcptOption.indexOf('=');
                String rcptOptionName = rcptOption;
                String rcptOptionValue = "";
                if (equalIndex > 0) {
                    rcptOptionName = rcptOption.substring(0, equalIndex)
                            .toUpperCase(Locale.US);
                    rcptOptionValue = rcptOption.substring(equalIndex + 1);
                }
                // Unexpected option attached to the RCPT command

                unop.more(rcptOptionName);
                return new SMTPResponse(
                        SMTPRetCode.PARAMETER_NOT_IMPLEMENTED,
                        "Unrecognized or unsupported option: "
                                + rcptOptionName);
            }
            optionTokenizer = null;
        }

        session.setCurRcpt(recipientAddress);

        return null;
    }

    private String getContext(SMTPSession session, SmtpMailAddress recipientAddress, String recipient) {
        StringBuilder sb = new StringBuilder(128);
        if (null != recipientAddress) {
            sb.append(" [to:" + recipientAddress.toString() + "]");
        } else if (null != recipient) {
            sb.append(" [to:" + recipient + "]");
        }
        if (null != session.getSender()) {
            sb.append(" [from:" + ((SmtpMailAddress) session.getSender()).toString() + "]");
        }
        return sb.toString();
    }

    /**
     * @see com.bilgidoku.rom.protokol.protocols.handler.CommandHandler#getImplCommands()
     */
    public Collection<String> getImplCommands() {
    	return COMMANDS;
    }

    /**
     * @see com.bilgidoku.rom.smtp.core.AbstractHookableCmdHandler#getHookInterface()
     */
    protected Class<RcptHook> getHookInterface() {
        return RcptHook.class;
    }

    /**
     * {@inheritDoc}
     * @throws KnownError 
     */
    protected HookResult callHook(RcptHook rawHook, SMTPSession session,
            String parameters) throws KnownError {
        return rawHook.doRcpt(session,
                (SmtpMailAddress) session.getSender(),
                (SmtpMailAddress) session.getCurRcpt());
    }

//    protected String getDefaultDomain() throws KnownError {
//    	return "localhost";
//    }
}
