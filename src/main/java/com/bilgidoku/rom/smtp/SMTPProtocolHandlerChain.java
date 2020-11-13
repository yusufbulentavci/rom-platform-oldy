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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.protokol.protocols.handler.CommandDispatcher;
import com.bilgidoku.rom.protokol.protocols.handler.ExtensibleHandler;
import com.bilgidoku.rom.protokol.protocols.handler.ProtocolHandler;
import com.bilgidoku.rom.protokol.protocols.handler.ProtocolHandlerChain;
import com.bilgidoku.rom.protokol.protocols.handler.ProtocolHandlerChainImpl;
import com.bilgidoku.rom.protokol.protocols.handler.WiringException;
import com.bilgidoku.rom.smtp.core.ExpnCmdHandler;
import com.bilgidoku.rom.smtp.core.HeloCmdHandler;
import com.bilgidoku.rom.smtp.core.HelpCmdHandler;
import com.bilgidoku.rom.smtp.core.NoopCmdHandler;
import com.bilgidoku.rom.smtp.core.PostmasterAbuseRcptHook;
import com.bilgidoku.rom.smtp.core.QuitCmdHandler;
import com.bilgidoku.rom.smtp.core.ReceivedDataLineFilter;
import com.bilgidoku.rom.smtp.core.RsetCmdHandler;
import com.bilgidoku.rom.smtp.core.VrfyCmdHandler;
import com.bilgidoku.rom.smtp.core.WelcomeMessageHandler;
import com.bilgidoku.rom.smtp.core.esmtp.AuthCmdHandler;
import com.bilgidoku.rom.smtp.core.esmtp.EhloCmdHandler;
import com.bilgidoku.rom.smtp.core.esmtp.MailSizeEsmtpExtension;
import com.bilgidoku.rom.smtp.core.esmtp.StartTlsCmdHandler;
import com.bilgidoku.rom.smtp.core.fastfail.SPFHandler;
import com.bilgidoku.rom.smtp.handlers.JamesDataCmdHandler;
import com.bilgidoku.rom.smtp.hook.AuthHook;

/**
 * This {@link ProtocolHandlerChain} implementation add all needed handlers to
 * the chain to act as full blown SMTPServer. By default messages will just get
 * rejected after the DATA command.
 * 
 */
public class SMTPProtocolHandlerChain extends ProtocolHandlerChainImpl {
	private static final MC mc = new MC(SMTPProtocolHandlerChain.class);

	private static final Astate x1 = mc.c("wiring-exception");

	public SMTPProtocolHandlerChain() throws WiringException {
		addAll(initDefaultHandlers());
		addHooks();
		wireExtensibleHandlers();

	}

	private void addHooks() {
//		add(new SPFHandler(false, true));
		add(new UsersRepositoryAuthHook());
		add(new AuthRequiredToRelayRcptHook());
		add(new SenderAuthIdentifyVerificationRcptHook());
		add(new PostmasterAbuseRcptHook());
		add(new ReceivedDataLineFilter());
		add(new DataLineJamesMessageHookHandler());
		add(new StartTlsCmdHandler());
//		add(new AddDefaultAttributesMessageHook());
		add(new ToProcessMailHook());
	}

	protected List<ProtocolHandler> initDefaultHandlers() {
		List<ProtocolHandler> defaultHandlers = new ArrayList<ProtocolHandler>();
		defaultHandlers.add(new WelcomeMessageHandler());
		defaultHandlers.add(new CommandDispatcher<SMTPSession>());
		defaultHandlers.add(new AuthCmdHandler());
		defaultHandlers.add(new JamesDataCmdHandler());
		defaultHandlers.add(new EhloCmdHandler());
		defaultHandlers.add(new ExpnCmdHandler());
		defaultHandlers.add(new HeloCmdHandler());
		defaultHandlers.add(new HelpCmdHandler());
		defaultHandlers.add(new JamesMailCmdHandler());
		defaultHandlers.add(new NoopCmdHandler());
		defaultHandlers.add(new QuitCmdHandler());
		defaultHandlers.add(new JamesRcptCmdHandler());
		defaultHandlers.add(new RsetCmdHandler());
		defaultHandlers.add(new VrfyCmdHandler());
		defaultHandlers.add(new MailSizeEsmtpExtension());

		// defaultHandlers.add(new UnknownCmdHandler());
		// defaultHandlers.add(new CommandHandlerResultLogger());
		return defaultHandlers;
	}

	private synchronized boolean checkForAuth(ProtocolHandler handler) {
		if (isReadyOnly()) {
			throw new UnsupportedOperationException("Read-Only");
		}
		if (handler instanceof AuthHook) {
			// check if we need to add the AuthCmdHandler
			List<ExtensibleHandler> handlers = getHandlers(ExtensibleHandler.class);
			for (ExtensibleHandler h : handlers) {
				if (h.getMarkerInterfaces().contains(AuthHook.class)) {
					return true;
				}
			}
			if (!add(new AuthCmdHandler())) {
				return false;
			}
		}
		return true;
	}

	public boolean add(ProtocolHandler handler) {
		checkForAuth(handler);
		return super.add(handler);
	}

	@Override
	public boolean addAll(Collection<? extends ProtocolHandler> c) {
		for (ProtocolHandler handler : c) {
			if (!checkForAuth(handler)) {
				return false;
			}
		}
		return super.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends ProtocolHandler> c) {
		for (ProtocolHandler handler : c) {
			if (!checkForAuth(handler)) {
				return false;
			}
		}
		return super.addAll(index, c);
	}

	@Override
	public void add(int index, ProtocolHandler element) {
		checkForAuth(element);
		super.add(index, element);
	}

}
