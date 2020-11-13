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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.protokol.protocols.Request;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.smtp.SMTPResponse;
import com.bilgidoku.rom.smtp.SMTPRetCode;
import com.bilgidoku.rom.smtp.SMTPSession;
import com.bilgidoku.rom.smtp.SmtpMailAddress;
import com.bilgidoku.rom.smtp.dsn.DSNStatus;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.MailHook;
import com.bilgidoku.rom.smtp.hook.MailParametersHook;

/**
 * Handles MAIL command
 */
public class MailCmdHandler extends AbstractHookableCmdHandler<MailHook> {
	private static final MC mc=new MC(MailCmdHandler.class);
    private static final Collection<String> COMMANDS = Collections.unmodifiableCollection(Arrays.asList("MAIL"));
    private static final Response SENDER_ALREADY_SPECIFIED =  new SMTPResponse(SMTPRetCode.BAD_SEQUENCE, DSNStatus
            .getStatus(DSNStatus.PERMANENT, DSNStatus.DELIVERY_OTHER)
            + " Sender already specified").immutable();
    private static final Response EHLO_HELO_NEEDED =  new SMTPResponse(SMTPRetCode.BAD_SEQUENCE, DSNStatus
            .getStatus(DSNStatus.PERMANENT, DSNStatus.DELIVERY_OTHER)
            + " Need HELO or EHLO before MAIL").immutable();
    private static final Response SYNTAX_ERROR_ARG = new SMTPResponse(SMTPRetCode.SYNTAX_ERROR_ARGUMENTS,
            DSNStatus.getStatus(DSNStatus.PERMANENT,
                    DSNStatus.DELIVERY_INVALID_ARG)
                    + " Usage: MAIL FROM:<sender>").immutable();
    private static final Response SYNTAX_ERROR = new SMTPResponse(SMTPRetCode.SYNTAX_ERROR_ARGUMENTS,
            DSNStatus.getStatus(DSNStatus.PERMANENT,
                    DSNStatus.ADDRESS_SYNTAX_SENDER)
                    + " Syntax error in MAIL command").immutable();
    private static final Response SYNTAX_ERROR_ADDRESS = new SMTPResponse(SMTPRetCode.SYNTAX_ERROR_ARGUMENTS,
            DSNStatus.getStatus(DSNStatus.PERMANENT,
                    DSNStatus.ADDRESS_SYNTAX_SENDER)
                    + " Syntax error in sender address").immutable();
    /**
     * A map of parameterHooks
     */
    private Map<String, MailParametersHook> paramHooks;

    /**
     * @throws KnownError 
     * @see
     * com.bilgidoku.rom.smtp.core.AbstractHookableCmdHandler
     * #onCommand(SMTPSession, Request)
     */
    public Response onCommand(SMTPSession session, Request request) throws KnownError {
        Response response = super.onCommand(session, request);
        // Check if the response was not ok
        if (response.getRetCode().equals(SMTPRetCode.MAIL_OK) == false) {
            // cleanup the session
            session.setSender(null);
        }

        return response;
    }

	/**
     * Handler method called upon receipt of a MAIL command. Sets up handler to
     * deliver mail as the stated sender.
     * 
     * @param session
     *            SMTP session object
     * @param argument
     *            the argument passed in with the command by the SMTP client
     */
    private Response doMAIL(SMTPSession session, String argument) {
        StringBuilder responseBuffer = new StringBuilder();
        SmtpMailAddress sender = session.getSender();
        responseBuffer.append(
                DSNStatus.getStatus(DSNStatus.SUCCESS, DSNStatus.ADDRESS_OTHER))
                .append(" Sender <");
        if (sender != null) {
            responseBuffer.append(sender);
        }
        responseBuffer.append("> OK");
        return new SMTPResponse(SMTPRetCode.MAIL_OK, responseBuffer);
    }

    /**
     * @see com.bilgidoku.rom.protokol.protocols.handler.CommandHandler#getImplCommands()
     */
    public Collection<String> getImplCommands() {
    	return COMMANDS;
    }

    /**
     * @see com.bilgidoku.rom.smtp.core.AbstractHookableCmdHandler#doCoreCmd(com.bilgidoku.rom.smtp.SMTPSession,
     *      java.lang.String, java.lang.String)
     */
    protected Response doCoreCmd(SMTPSession session, String command,
            String parameters) {
        return doMAIL(session, parameters);
    }

    /**
     * @throws KnownError 
     * @see com.bilgidoku.rom.smtp.core.AbstractHookableCmdHandler#doFilterChecks(com.bilgidoku.rom.smtp.SMTPSession,
     *      java.lang.String, java.lang.String)
     */
    protected Response doFilterChecks(SMTPSession session, String command,
            String parameters) throws KnownError {
        return doMAILFilter(session, parameters);
    }

    
    private final static Astate mfsas=mc.c("mail-filter-sender-already-specified");
    private final static Astate mfsea=mc.c("mail-filter-syntax-error-arg");
    private final static Astate mfen=mc.c("mail-filter-ehlo-needed");
    private final static Astate mfueo=mc.c("mail-filter-unexpected-option");
    private final static Astate mfses=mc.c("mail-filter-syntax-error-sender");
    
    /**
     * @param session
     *            SMTP session object
     * @param argument
     *            the argument passed in with the command by the SMTP client
     * @throws KnownError 
     */
    private Response doMAILFilter(SMTPSession session, String argument) throws KnownError {
        String sender = null;

        if ((argument != null) && (argument.indexOf(":") > 0)) {
            int colonIndex = argument.indexOf(":");
            sender = argument.substring(colonIndex + 1);
            argument = argument.substring(0, colonIndex);
        }
        if (session.getSender() != null) {
        	mfsas.more();
            return SENDER_ALREADY_SPECIFIED;
        } else if (session.getHeloMode() == null
                && session.getSmtpConfiguration().useHeloEhloEnforcement()) {
        	mfen.more();
            return EHLO_HELO_NEEDED;
        } else if (argument == null
                || !argument.toUpperCase(Locale.US).equals("FROM")
                || sender == null) {
        	mfsea.more();
            return SYNTAX_ERROR_ARG;
        } else {
            sender = sender.trim();
            // the next gt after the first lt ... AUTH may add more <>
            int lastChar = sender.indexOf('>', sender.indexOf('<'));
            // Check to see if any options are present and, if so, whether they
            // are correctly formatted
            // (separated from the closing angle bracket by a ' ').
            if ((lastChar > 0) && (sender.length() > lastChar + 2)
                    && (sender.charAt(lastChar + 1) == ' ')) {
                String mailOptionString = sender.substring(lastChar + 2);

                // Remove the options from the sender
                sender = sender.substring(0, lastChar + 1);

                StringTokenizer optionTokenizer = new StringTokenizer(
                        mailOptionString, " ");
                while (optionTokenizer.hasMoreElements()) {
                    String mailOption = optionTokenizer.nextToken();
                    int equalIndex = mailOption.indexOf('=');
                    String mailOptionName = mailOption;
                    String mailOptionValue = "";
                    if (equalIndex > 0) {
                        mailOptionName = mailOption.substring(0, equalIndex)
                                .toUpperCase(Locale.US);
                        mailOptionValue = mailOption.substring(equalIndex + 1);
                    }

                    // Handle the SIZE extension keyword

                    if (paramHooks.containsKey(mailOptionName)) {
                        MailParametersHook hook = paramHooks.get(mailOptionName);
                        SMTPResponse res = calcDefaultSMTPResponse(hook.doMailParameter(session, mailOptionName, mailOptionValue));
                        if (res != null) {
                            return res;
                        }
                    } else {
                        // Unexpected option attached to the Mail command
                    	mfueo.more(mailOptionName);
                    }
                }
            }
            if (session.getSmtpConfiguration().useAddressBracketsEnforcement()
                    && (!sender.startsWith("<") || !sender.endsWith(">"))) {
                mfses.more();
                return SYNTAX_ERROR;
            }
            SmtpMailAddress senderAddress = null;

            if (session.getSmtpConfiguration().useAddressBracketsEnforcement()
                    || (sender.startsWith("<") && sender.endsWith(">"))) {
                // Remove < and >
                sender = sender.substring(1, sender.length() - 1);
            }

            if (sender.length() == 0) {
                // This is the <> case. Let senderAddress == null
            } else {

// bilo:
//                if (sender.indexOf("@") < 0) {
//                    sender = sender
//                            + "@"
//                            + getDefaultDomain();
//                }

                try {
                    senderAddress = new SmtpMailAddress(sender);
                } catch (Exception pe) {
                	mfses.more();
                    return SYNTAX_ERROR_ADDRESS;
                }
            }
            if (senderAddress == null) {
                senderAddress = SmtpMailAddress.nullSender();
            }
            // Store the senderAddress in session map
            session.setSender(senderAddress);
        }
        return null;
    }
    /**
     * @see com.bilgidoku.rom.smtp.core.AbstractHookableCmdHandler#getHookInterface()
     */
    protected Class<MailHook> getHookInterface() {
        return MailHook.class;
    }


    /**
     * {@inheritDoc}
     */
    protected HookResult callHook(MailHook rawHook, SMTPSession session, String parameters) {
        SmtpMailAddress sender = session.getSender();
        if (sender.isNullSender()) {
            sender = null;
        }
        return rawHook.doMail(session, sender);
    }

    
    /**
     * @see com.bilgidoku.rom.smtp.core.AbstractHookableCmdHandler#getMarkerInterfaces()
     */
    public List<Class<?>> getMarkerInterfaces() {
        List<Class<?>> l = super.getMarkerInterfaces();
        l.add(MailParametersHook.class);
        return l;
    }

    /**
     * @see com.bilgidoku.rom.smtp.core.AbstractHookableCmdHandler#wireExtensions(java.lang.Class, java.util.List)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void wireExtensions(Class interfaceName, List extension) {
        if (MailParametersHook.class.equals(interfaceName)) {
            this.paramHooks = new HashMap<String, MailParametersHook>();
            for (Iterator<MailParametersHook> i = extension.iterator(); i.hasNext(); ) {
                MailParametersHook hook =  i.next();
                String[] params = hook.getMailParamNames();
                for (int k = 0; k < params.length; k++) {
                    paramHooks.put(params[k], hook);
                }
            }
        } else {
            super.wireExtensions(interfaceName, extension);
        }
    }

//    /**
//     * Return the default domain to append if the sender contains none
//     * 
//     * @return defaultDomain
//     * @throws KnownError 
//     */
//    protected String getDefaultDomain() throws KnownError {
//        return "localhost";
//    }
    

}
