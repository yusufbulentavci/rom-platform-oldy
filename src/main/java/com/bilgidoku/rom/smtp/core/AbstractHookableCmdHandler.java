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
import java.util.List;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.protokol.protocols.Request;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.protokol.protocols.handler.CommandHandler;
import com.bilgidoku.rom.protokol.protocols.handler.ExtensibleHandler;
import com.bilgidoku.rom.smtp.SMTPConfiguration;
import com.bilgidoku.rom.smtp.SMTPResponse;
import com.bilgidoku.rom.smtp.SMTPRetCode;
import com.bilgidoku.rom.smtp.SMTPSession;
import com.bilgidoku.rom.smtp.hook.Hook;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.HookResultHook;
import com.bilgidoku.rom.smtp.hook.HookReturnCode;

/**
 * Abstract class which Handle hook-aware CommanHandler.
 * 
 */
public abstract class AbstractHookableCmdHandler<HOOK extends Hook> implements CommandHandler<SMTPSession>, ExtensibleHandler {


    private List<HOOK> hooks;
    private List<HookResultHook> rHooks;
    

    /**
     * Handle command processing
     * @throws KnownError 
     * 
     * @see com.bilgidoku.rom.protokol.protocols.handler.CommandHandler
     * #onCommand(com.bilgidoku.rom.protokol.protocols.ProtocolSession, Request)
     */
    public Response onCommand(SMTPSession session, Request request) throws KnownError {
        String command = request.getCommand();
        String parameters = request.getArgument();
        Response response = doFilterChecks(session, command, parameters);

        if (response == null) {

            response = processHooks(session, command, parameters);
            if (response == null) {
                return doCoreCmd(session, command, parameters);
            } else {
                return response;
            }
        } else {
            return response;
        }

    }

    /**
     * Process all hooks for the given command
     * 
     * @param session
     *            the SMTPSession object
     * @param command
     *            the command
     * @param parameters
     *            the paramaters
     * @return SMTPResponse
     * @throws KnownError 
     */
    private Response processHooks(SMTPSession session, String command,
            String parameters) throws KnownError {
        List<HOOK> hooks = getHooks();
        if (hooks != null) {
            int count = hooks.size();
            for (int i = 0; i < count; i++) {
                HOOK rawHook = hooks.get(i);
//                session.getLogger().debug("executing hook " + rawHook.getClass().getName());
                long start = System.currentTimeMillis();
                
                HookResult hRes = callHook(rawHook, session, parameters);
                long executionTime = System.currentTimeMillis() - start;

                if (rHooks != null) {
                    for (int i2 = 0; i2 < rHooks.size(); i2++) {
                        Object rHook = rHooks.get(i2);
//                        session.getLogger().debug("executing hook " + rHook);
                        hRes = ((HookResultHook) rHook).onHookResult(session, hRes, executionTime, rawHook);
                    }
                }
                
                // call the core cmd if we receive a ok return code of the hook so no other hooks are executed
                if ((hRes.getResult() & HookReturnCode.OK) == HookReturnCode.OK) {
                    final Response response = doCoreCmd(session, command, parameters);
                    if ((hRes.getResult() & HookReturnCode.DISCONNECT) == HookReturnCode.DISCONNECT) {
                        return new Response() {
                            
                            /*
                             * (non-Javadoc)
                             * @see com.bilgidoku.rom.protokol.protocols.Response#isEndSession()
                             */
                            public boolean isEndSession() {
                                return true;
                            }
                            
                            /*
                             * (non-Javadoc)
                             * @see com.bilgidoku.rom.protokol.protocols.Response#getRetCode()
                             */
                            public String getRetCode() {
                                return response.getRetCode();
                            }
                            
                            /*
                             * (non-Javadoc)
                             * @see com.bilgidoku.rom.protokol.protocols.Response#getLines()
                             */
                            public List<CharSequence> getLines() {
                                return response.getLines();
                            }

							@Override
							public boolean isSuccess() {
								return response.isSuccess();
							}

							@Override
							public boolean isImportant() {
								return response.isImportant();
							}

							@Override
							public JSONObject toReport() throws JSONException {
								return response.toReport();
							}
                        };
                    }
                    return response;
                } else {
                    SMTPResponse res = calcDefaultSMTPResponse(hRes);
                    if (res != null) {
                        return res;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Must be implemented by hookable cmd handlers to make the effective call to an hook.
     * 
     * @param rawHook the hook
     * @param session the session
     * @param parameters the parameters
     * @return the HookResult, will be calculated using HookResultToSMTPResponse.
     * @throws KnownError 
     */
    protected abstract HookResult callHook(HOOK rawHook, SMTPSession session, String parameters) throws KnownError;

    /**
     * Convert the HookResult to SMTPResponse using default values. Should be override for using own values
     * 
     * @param result HookResult
     * @return SMTPResponse
     */
    public static SMTPResponse calcDefaultSMTPResponse(HookResult result) {
        if (result != null) {
            int rCode = result.getResult();
            String smtpRetCode = result.getSmtpRetCode();
            String smtpDesc = result.getSmtpDescription();
    
            if ((rCode &HookReturnCode.DENY) == HookReturnCode.DENY) {
                if (smtpRetCode == null)
                    smtpRetCode = SMTPRetCode.TRANSACTION_FAILED;
                if (smtpDesc == null)
                    smtpDesc = "Email rejected";
    
                SMTPResponse response =  new SMTPResponse(smtpRetCode, smtpDesc);
                if ((rCode & HookReturnCode.DISCONNECT) == HookReturnCode.DISCONNECT) {
                    response.setEndSession(true);
                }
                return response;
            } else if (rCode == HookReturnCode.DENYSOFT) {
                if (smtpRetCode == null)
                    smtpRetCode = SMTPRetCode.LOCAL_ERROR;
                if (smtpDesc == null)
                    smtpDesc = "Temporary problem. Please try again later";
    
                SMTPResponse response = new SMTPResponse(smtpRetCode, smtpDesc);
                if ((rCode & HookReturnCode.DISCONNECT) == HookReturnCode.DISCONNECT) {
                    response.setEndSession(true);
                }
                return response;
            } else if ((rCode & HookReturnCode.OK) == HookReturnCode.OK) {
                if (smtpRetCode == null)
                    smtpRetCode = SMTPRetCode.MAIL_OK;
                if (smtpDesc == null)
                    smtpDesc = "Command accepted";
    
                SMTPResponse response = new SMTPResponse(smtpRetCode, smtpDesc);
                if ((rCode & HookReturnCode.DISCONNECT) == HookReturnCode.DISCONNECT) {
                    response.setEndSession(true);
                }
                return response;
            } else if ((rCode & HookReturnCode.DISCONNECT) == HookReturnCode.DISCONNECT) {
                SMTPResponse response = new SMTPResponse(smtpRetCode, smtpDesc);
                response.setEndSession(true);
                return response;
            } else {
                // Return null as default
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Execute Syntax checks and return a SMTPResponse if a syntax error was
     * detected, otherwise null.
     * 
     * @param session
     * @param command
     * @param parameters
     * @return smtp response if a syntax error was detected, otherwise <code>null</code>
     * @throws KnownError 
     */
    protected abstract Response doFilterChecks(SMTPSession session,
            String command, String parameters) throws KnownError;

    /**
     * Execute the core commandHandling.
     * 
     * @param session
     * @param command
     * @param parameters
     * @return smtp response
     */
    protected abstract Response doCoreCmd(SMTPSession session,
            String command, String parameters);
    

    /**
     * @see com.bilgidoku.rom.protokol.protocols.handler.ExtensibleHandler#getMarkerInterfaces()
     */
    public List<Class<?>> getMarkerInterfaces() {
        List<Class<?>> classes = new ArrayList<Class<?>>(2);
        classes.add(getHookInterface());
        classes.add(HookResultHook.class);
        return classes;
    }

    /**
     * Return the interface which hooks need to implement to hook in
     * 
     * @return interface
     */
    protected abstract Class<HOOK> getHookInterface();

    /**
     * @see com.bilgidoku.rom.protokol.protocols.handler.ExtensibleHandler#wireExtensions(java.lang.Class,
     *      java.util.List)
     */
    @SuppressWarnings("unchecked")
	public void wireExtensions(Class<?> interfaceName, List<?> extension) {
        if (getHookInterface().equals(interfaceName)) {
            this.hooks = (List<HOOK>) extension;
        } else if (HookResultHook.class.equals(interfaceName)) {
            this.rHooks = (List<HookResultHook>) extension;
        }

    }

    /**
     * Return a list which holds all hooks for the cmdHandler
     * 
     * @return list containing all hooks for the cmd handler
     */
    protected List<HOOK> getHooks() {
        return hooks;
    }

}
