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

package com.bilgidoku.rom.spf.policies.local;

import com.bilgidoku.rom.dns.exceptions.NeutralException;
import com.bilgidoku.rom.dns.exceptions.NoneException;
import com.bilgidoku.rom.dns.exceptions.PermErrorException;
import com.bilgidoku.rom.dns.exceptions.TempErrorException;
import com.bilgidoku.rom.spf.core.SPF1Record;
import com.bilgidoku.rom.spf.policies.PolicyPostFilter;
import com.bilgidoku.rom.spf.terms.Directive;
import com.bilgidoku.rom.spf.terms.IncludeMechanism;


/**
 * PolicyPostFilter which implements trusted forwared. 
 * See http://www.trusted-forwarder.org for more informations
 *
 */
public class TrustedForwarderPolicy implements PolicyPostFilter {

    /**
     * The hostname to include
     */
    public static final String TRUSTED_FORWARDER_HOST = "spf.trusted-forwarder.org";

    

    /**
     * @see org.apache.james.jspf.policies.PolicyPostFilter#getSPFRecord(java.lang.String, org.apache.james.jspf.core.SPF1Record)
     */
    public SPF1Record getSPFRecord(String currentDomain, SPF1Record spfRecord) throws PermErrorException, TempErrorException, NoneException, NeutralException {
        if (spfRecord == null) return null;
        String mechanism = ((Directive) spfRecord.getDirectives().get(spfRecord.getDirectives().size())).toString();
        if (mechanism.equals("-all") || mechanism.equals("?all")) {
//            log.debug("Add TrustedForwarderPolicy = include:"+TRUSTED_FORWARDER_HOST);
            try {
                IncludeMechanism trusted = new IncludeMechanism() {
                    /**
                     * Set the host to use 
                     * 
                     * @param host the host to include
                     */
                    public synchronized IncludeMechanism setHost(String host) {
                        this.host = host;
                        return this;
                    }
                }.setHost(TRUSTED_FORWARDER_HOST);
                spfRecord.getDirectives().add(spfRecord.getDirectives().size()-1, new Directive(null, trusted));
            } catch (PermErrorException e) {
                // will never happen
            }
        }
        return spfRecord;
    }
}