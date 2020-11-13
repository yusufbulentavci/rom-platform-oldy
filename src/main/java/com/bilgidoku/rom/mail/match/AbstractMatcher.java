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


package com.bilgidoku.rom.mail.match;

import com.bilgidoku.rom.mail.MailProcessContext;
import com.bilgidoku.rom.mail.Matcher;

/**
 * <p>GenericMatcher implements the Matcher and MatcherConfig interfaces.</p>
 * <p>GenericMatcher makes writing matchers easier. It provides simple versions of
 * the lifecycle methods init and destroy and of the methods in the MatcherConfig
 * interface. GenericMatcher also implements the log method, declared in the
 * MatcherContext interface.</p>
 * 
 * <p>To write a generic matcher, you need only override the abstract match method.</p>
 *
 * @version 1.0.0, 24/04/1999
 */
public abstract class AbstractMatcher implements Matcher {
	final MailProcessContext context;
	final MatcherConfig config;

	public AbstractMatcher(MailProcessContext context, MatcherConfig config) {
		super();
		this.context = context;
		this.config = config;
	}

}
