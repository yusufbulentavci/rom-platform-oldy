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


package com.bilgidoku.rom.spf.terms;

import java.util.List;

import com.bilgidoku.rom.dns.DNSRequest;
import com.bilgidoku.rom.dns.DNSResponse;
import com.bilgidoku.rom.dns.DnsImplement;
import com.bilgidoku.rom.dns.IPAddr;
import com.bilgidoku.rom.dns.TimeoutException;
import com.bilgidoku.rom.dns.exceptions.NeutralException;
import com.bilgidoku.rom.dns.exceptions.NoneException;
import com.bilgidoku.rom.dns.exceptions.PermErrorException;
import com.bilgidoku.rom.dns.exceptions.TempErrorException;
import com.bilgidoku.rom.spf.core.DNSLookupContinuation;
import com.bilgidoku.rom.spf.core.MacroExpand;
import com.bilgidoku.rom.spf.core.SPFChecker;
import com.bilgidoku.rom.spf.core.SPFCheckerDNSResponseListener;
import com.bilgidoku.rom.spf.core.SPFSession;
import com.bilgidoku.rom.spf.core.SPFTermsRegexps;

/**
 * This class represent the ptr mechanism
 * 
 */
public class PTRMechanism extends GenericMechanism implements SPFCheckerDNSResponseListener {

    private final class ExpandedChecker implements SPFChecker {
        private CleanupChecker cleanupChecker = new CleanupChecker();

        private final class CleanupChecker implements SPFChecker {

            /**
             * @see org.apache.james.jspf.core.SPFChecker#checkSPF(org.apache.james.jspf.core.SPFSession)
             */
            public DNSLookupContinuation checkSPF(SPFSession spfData)
                    throws PermErrorException, TempErrorException,
                    NeutralException, NoneException {
                spfData.setDomainList(null);
                spfData.setCurrentDomain(null);
                return null;
            }
        }

        /**
         * @see org.apache.james.jspf.core.SPFChecker#checkSPF(org.apache.james.jspf.core.SPFSession)
         */
        public DNSLookupContinuation checkSPF(SPFSession spfData) throws PermErrorException,
                TempErrorException, NeutralException, NoneException {

            // Get PTR Records for the ipAddress which is provided by SPF1Data
            IPAddr ip = IPAddr.getAddress(spfData.getIpAddress());

            // Get the right host.
            String host = expandHost(spfData);
            
            spfData.setExpandedHost(host);
            
            spfData.pushChecker(cleanupChecker);

            return new DNSLookupContinuation(new DNSRequest(ip.getReverseIP(), DNSRequest.PTR), PTRMechanism.this);
        }
    }

    private static final String ATTRIBUTE_CURRENT_DOMAIN = "PTRMechanism.currentDomain";

    private static final String ATTRIBUTE_EXPANDED_HOST = "PTRMechanism.expandedHost";

    private static final String ATTRIBUTE_DOMAIN_LIST = "PTRMechanism.domainListCheck";

    /**
     * ABNF: PTR = "ptr" [ ":" domain-spec ]
     */
    public static final String REGEX = "[pP][tT][rR]" + "(?:\\:"
            + SPFTermsRegexps.DOMAIN_SPEC_REGEX + ")?";
    

    private SPFChecker expandedChecker = new ExpandedChecker();

    /**
     * @see org.apache.james.jspf.core.SPFChecker#checkSPF(org.apache.james.jspf.core.SPFSession)
     */
    public DNSLookupContinuation checkSPF(SPFSession spfData) throws PermErrorException,
            TempErrorException, NeutralException, NoneException {
        // update currentDepth
        spfData.increaseCurrentDepth();

        spfData.pushChecker(expandedChecker);
        return macroExpand.checkExpand(getDomain(), spfData, MacroExpand.DOMAIN);
    }

    /**
     * @see org.apache.james.jspf.core.SPFCheckerDNSResponseListener#onDNSResponse(com.coreks.rom.dns.james.jspf.core.DNSResponse, org.apache.james.jspf.core.SPFSession)
     */
    @SuppressWarnings("unchecked")
	public DNSLookupContinuation onDNSResponse(DNSResponse response, SPFSession spfSession)
            throws PermErrorException, TempErrorException, NoneException, NeutralException {
        
        List<String> domainList = spfSession.getDomainList();
        try {
            if (domainList == null) {
            
                domainList = response.getResponse();
                
                // No PTR records found
                if (domainList == null) {
                    spfSession.setMechResult(Boolean.FALSE);
                    return null;
                }
        
                // check if the maximum lookup count is reached
                if (DnsImplement.one().getRecordLimit() > 0 && domainList.size() > DnsImplement.one().getRecordLimit()) {
                    // Truncate the PTR list to getRecordLimit.
                    // See #ptr-limit rfc4408 test
                    domainList = domainList.subList(0, DnsImplement.one().getRecordLimit()-1);
                    // throw new PermErrorException("Maximum PTR lookup count reached");
                }
                
                spfSession.setDomainList(domainList);
                
            } else {

                String compareDomain = spfSession.getCurrentDomain();
                String host = spfSession.getExpandedHost();
    
                List<String> aList = response.getResponse();
    

                if (aList != null) {
                    for (int j = 0; j < aList.size(); j++) {
                        // Added the IPAddr parsing/toString to have matching in IPV6 multiple ways to 
                        if (IPAddr.getAddress((String) aList.get(j)).getIPAddress().equals(IPAddr.getAddress(spfSession.getIpAddress()).getIPAddress())) {
                            
                            if (compareDomain.equals(host)
                                    || compareDomain.endsWith("." + host)) {
                                spfSession.setMechResult(Boolean.TRUE);
                                return null;
                            }
                        }
                    }
                }
            
            }
        } catch (TimeoutException e) {
            throw new TempErrorException("Timeout querying the dns server");
        }
        

        if (domainList.size() > 0) {
            String currentDomain = (String) domainList.remove(0);
    
            DNSRequest dnsRequest;
            // check if the connecting ip is ip6. If so lookup AAAA record
            if (IPAddr.isIPV6(spfSession.getIpAddress())) {
                // Get aaaa record for this
                dnsRequest = new DNSRequest(currentDomain, DNSRequest.AAAA);
            } else {
                // Get a record for this
                dnsRequest = new DNSRequest(currentDomain, DNSRequest.A);
            }
            
            spfSession.setCurrentDomain(currentDomain);
            
            return new DNSLookupContinuation(dnsRequest, PTRMechanism.this);
        } else {
            spfSession.setMechResult(Boolean.FALSE);
            return null;
        }

    }


}
