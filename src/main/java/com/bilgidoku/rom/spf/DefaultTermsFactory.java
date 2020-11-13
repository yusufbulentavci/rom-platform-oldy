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

package com.bilgidoku.rom.spf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.bilgidoku.rom.dns.exceptions.PermErrorException;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.spf.parser.TermDefinition;
import com.bilgidoku.rom.spf.parser.TermsFactory;
import com.bilgidoku.rom.spf.terms.AMechanism;
import com.bilgidoku.rom.spf.terms.AllMechanism;
import com.bilgidoku.rom.spf.terms.Configuration;
import com.bilgidoku.rom.spf.terms.ConfigurationEnabled;
import com.bilgidoku.rom.spf.terms.ExistsMechanism;
import com.bilgidoku.rom.spf.terms.ExpModifier;
import com.bilgidoku.rom.spf.terms.IP4Mechanism;
import com.bilgidoku.rom.spf.terms.IP6Mechanism;
import com.bilgidoku.rom.spf.terms.IncludeMechanism;
import com.bilgidoku.rom.spf.terms.MXMechanism;
import com.bilgidoku.rom.spf.terms.PTRMechanism;
import com.bilgidoku.rom.spf.terms.RedirectModifier;
import com.bilgidoku.rom.spf.terms.UnknownModifier;
import com.bilgidoku.rom.spf.wiring.WiringServ;
import com.bilgidoku.rom.spf.wiring.WiringServiceException;
import com.bilgidoku.rom.spf.wiring.WiringServiceTable;

/**
 * The default implementation of the TermsFactory
 */
public class DefaultTermsFactory implements TermsFactory {

	private String termFile = "org/apache/james/jspf/parser/jspf.default.terms";

	private Collection<TermDefinition> mechanismsCollection;

	private Collection<TermDefinition> modifiersCollection;

	private WiringServ wiringService;

	public DefaultTermsFactory() {
		this.wiringService = new WiringServiceTable();
		init();
	}

	public DefaultTermsFactory(WiringServ wiringService) {
		this.wiringService = wiringService;
		init();
	}

	/**
	 * mechanisms=org.apache.james.jspf.terms.AllMechanism,org.apache.james.jspf
	 * .terms.AMechanism,
	 * org.apache.james.jspf.terms.ExistsMechanism,org.apache.
	 * james.jspf.terms.IncludeMechanism,
	 * org.apache.james.jspf.terms.IP4Mechanism
	 * ,org.apache.james.jspf.terms.IP6Mechanism,
	 * org.apache.james.jspf.terms.MXMechanism
	 * ,org.apache.james.jspf.terms.PTRMechanism
	 * 
	 * modifiers=org.apache.james.jspf.terms.ExpModifier,org.apache.james.jspf.
	 * terms.RedirectModifier, org.apache.james.jspf.terms.UnknownModifier
	 * 
	 * Initialize the factory and the services
	 */
	private void init() {
		try {
			Collection<TermDefinition> l = new ArrayList<TermDefinition>();
			l.add(new DefaultTermDefinition(AllMechanism.class));
			l.add(new DefaultTermDefinition(AMechanism.class));
			l.add(new DefaultTermDefinition(ExistsMechanism.class));
			l.add(new DefaultTermDefinition(IncludeMechanism.class));
			l.add(new DefaultTermDefinition(IP4Mechanism.class));
			l.add(new DefaultTermDefinition(IP6Mechanism.class));
			l.add(new DefaultTermDefinition(MXMechanism.class));
			l.add(new DefaultTermDefinition(PTRMechanism.class));

			mechanismsCollection = Collections.synchronizedCollection(l);
			l = new ArrayList<TermDefinition>();
			l.add(new DefaultTermDefinition(ExpModifier.class));
			l.add(new DefaultTermDefinition(RedirectModifier.class));
			l.add(new DefaultTermDefinition(UnknownModifier.class));
			modifiersCollection = Collections.synchronizedCollection(l);
		} catch (IllegalArgumentException | SecurityException
				| IllegalAccessException | NoSuchFieldException e) {
			Sistem.printStackTrace(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Create a collection of term definitions supported by this factory.
	 * 
	 * 
	 * @param classes
	 *            classes to analyze
	 * @param staticFieldName
	 *            static field to concatenate
	 * @return map <Class,Pattern>
	 */
	private Collection<TermDefinition> createTermDefinitionCollection(
			Class<?>[] classes) {
		Collection<TermDefinition> l = new ArrayList<TermDefinition>();
		// for (int j = 0; j < classes.length; j++) {
		// try {
		// l.add(new DefaultTermDefinition(classes[j]));
		// } catch (Exception e) {
		// // log.debug("Unable to create the term collection", e);
		// throw new IllegalStateException(
		// "Unable to create the term collection");
		// }
		// }

		return Collections.synchronizedCollection(l);
	}

	/**
	 * @see org.apache.james.jspf.parser.TermsFactory#createTerm(java.lang.Class,
	 *      org.apache.james.jspf.terms.Configuration)
	 */
	public Object createTerm(Class<?> termDef, Configuration subres)
			throws PermErrorException, InstantiationException {
		try {
			Object term = termDef.newInstance();

			try {
				wiringService.wire(term);
			} catch (WiringServiceException e) {
				throw new InstantiationException(
						"Unexpected error adding dependencies to term: "
								+ e.getMessage());
			}

			if (term instanceof ConfigurationEnabled) {
				if (subres == null || subres.groupCount() == 0) {
					((ConfigurationEnabled) term).config(null);
				} else {
					((ConfigurationEnabled) term).config(subres);
				}
			}
			return term;
		} catch (IllegalAccessException e) {
			throw new InstantiationException("Unexpected error creating term: "
					+ e.getMessage());
		}
	}

	/**
	 * @see org.apache.james.jspf.parser.TermsFactory#getMechanismsCollection()
	 */
	public Collection<TermDefinition> getMechanismsCollection() {
		return mechanismsCollection;
	}

	/**
	 * @see org.apache.james.jspf.parser.TermsFactory#getModifiersCollection()
	 */
	public Collection<TermDefinition> getModifiersCollection() {
		return modifiersCollection;
	}

}
