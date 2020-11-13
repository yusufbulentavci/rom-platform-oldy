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

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.dns.DNSRequest;
import com.bilgidoku.rom.dns.DNSResponse;
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
 * This class represent the mx mechanism
 * 
 */
public class MXMechanism extends AMechanism implements SPFCheckerDNSResponseListener {

    private final class ExpandedChecker implements SPFChecker {
        
        /**
         * @see org.apache.james.jspf.core.SPFChecker#checkSPF(org.apache.james.jspf.core.SPFSession)
         */
        public DNSLookupContinuation checkSPF(SPFSession spfData) throws PermErrorException,
                TempErrorException, NeutralException, NoneException {

            // Get the right host.
            String host = expandHost(spfData);
            
            return new DNSLookupContinuation(new DNSRequest(host, DNSRequest.MX), MXMechanism.this);
        }
    }

    private static final String ATTRIBUTE_MX_RECORDS = "MXMechanism.mxRecords";
    private static final String ATTRIBUTE_CHECK_RECORDS = "MXMechanism.checkRecords";
    /**
     * ABNF: MX = "mx" [ ":" domain-spec ] [ dual-cidr-length ]
     */
    public static final String REGEX = "[mM][xX]" + "(?:\\:"
            + SPFTermsRegexps.DOMAIN_SPEC_REGEX + ")?" + "(?:"
            + DUAL_CIDR_LENGTH_REGEX + ")?";
    
    private SPFChecker expandedChecker = new ExpandedChecker();
    
    /**
     * @see org.apache.james.jspf.terms.AMechanism#checkSPF(org.apache.james.jspf.core.SPFSession)
     */
    public DNSLookupContinuation checkSPF(SPFSession spfData) throws PermErrorException,
            TempErrorException, NeutralException, NoneException{

        // update currentDepth
        spfData.increaseCurrentDepth();

        spfData.pushChecker(expandedChecker);
        return macroExpand.checkExpand(getDomain(), spfData, MacroExpand.DOMAIN);
    }

    /**
     * @see org.apache.james.jspf.terms.AMechanism#onDNSResponse(com.coreks.rom.dns.james.jspf.core.DNSResponse, org.apache.james.jspf.core.SPFSession)
     */
    @SuppressWarnings("unchecked")
	public DNSLookupContinuation onDNSResponse(DNSResponse response, SPFSession spfSession)
        throws PermErrorException, TempErrorException, NoneException, NeutralException {
        try {
            
            List<String> records = spfSession.getCheckRecords();
            List<String> mxR = spfSession.getMxRecords();

            if (records == null) {
            
                records = response.getResponse();

                if (records == null) {
                    // no mx record found
                    spfSession.setMechResult(Boolean.FALSE);
                    return null;
                }
                
                spfSession.setCheckRecords(records);
                
            } else {
                
                List<String> res = response.getResponse();

                if (res != null) {
                    if (mxR == null) {
                        mxR = new ArrayList<String>();
                        spfSession.setMxRecords(mxR);
                    }
                    mxR.addAll(res);
                }
                
            }

            // if the remote IP is an ipv6 we check ipv6 addresses, otherwise ip4
            boolean isIPv6 = IPAddr.isIPV6(spfSession.getIpAddress());

            String mx;
            while (records.size() > 0 && (mx = records.remove(0)) != null && mx.length() > 0) {
//                log.debug("Add MX-Record " + mx + " to list");

                return new DNSLookupContinuation(new DNSRequest(mx, isIPv6 ? DNSRequest.AAAA : DNSRequest.A), MXMechanism.this);
                
            }
                
            // no mx record found
            if (mxR == null || mxR.size() == 0) {
                spfSession.setMechResult(Boolean.FALSE);
                return null;
            }

            // get the ipAddress
            IPAddr checkAddress;
            checkAddress = IPAddr.getAddress(spfSession.getIpAddress(), isIPv6 ? getIp6cidr() : getIp4cidr());
            
            // clean up attributes
            spfSession.setCheckRecords(null);
            spfSession.setMxRecords(null);
            spfSession.setMechResult(Boolean.valueOf(checkAddressList(checkAddress, mxR, getIp4cidr())));
            return null;
            
        } catch (TimeoutException e) {
        	spfSession.setCheckRecords(null);
            spfSession.setMxRecords(null);
            throw new TempErrorException("Timeout querying the dns server");
        }
    }

    /**
     * @see org.apache.james.jspf.terms.AMechanism#toString()
     */
    public String toString() {
        return super.toString("mx");
    }

}
