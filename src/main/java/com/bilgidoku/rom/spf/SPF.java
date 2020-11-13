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

import java.util.Iterator;
import java.util.LinkedList;

import com.bilgidoku.rom.dns.DnsImplement;
import com.bilgidoku.rom.dns.exceptions.NeutralException;
import com.bilgidoku.rom.dns.exceptions.NoneException;
import com.bilgidoku.rom.dns.exceptions.PermErrorException;
import com.bilgidoku.rom.dns.exceptions.SPFErrorConstants;
import com.bilgidoku.rom.dns.exceptions.SPFResultException;
import com.bilgidoku.rom.dns.exceptions.TempErrorException;
import com.bilgidoku.rom.spf.core.DNSLookupContinuation;
import com.bilgidoku.rom.spf.core.MacroExpand;
import com.bilgidoku.rom.spf.core.MacroExpandEnabled;
import com.bilgidoku.rom.spf.core.SPF1Record;
import com.bilgidoku.rom.spf.core.SPF1Utils;
import com.bilgidoku.rom.spf.core.SPFCheckEnabled;
import com.bilgidoku.rom.spf.core.SPFChecker;
import com.bilgidoku.rom.spf.core.SPFCheckerExceptionCatcher;
import com.bilgidoku.rom.spf.core.SPFRecordParser;
import com.bilgidoku.rom.spf.core.SPFSession;
import com.bilgidoku.rom.spf.executor.FutureSPFResult;
import com.bilgidoku.rom.spf.executor.SPFExecutor;
import com.bilgidoku.rom.spf.executor.SPFResult;
import com.bilgidoku.rom.spf.executor.SynchronousSPFExecutor;
import com.bilgidoku.rom.spf.parser.RFC4408SPF1Parser;
import com.bilgidoku.rom.spf.policies.InitialChecksPolicy;
import com.bilgidoku.rom.spf.policies.NeutralIfNotMatchPolicy;
import com.bilgidoku.rom.spf.policies.NoSPFRecordFoundPolicy;
import com.bilgidoku.rom.spf.policies.ParseRecordPolicy;
import com.bilgidoku.rom.spf.policies.Policy;
import com.bilgidoku.rom.spf.policies.PolicyPostFilter;
import com.bilgidoku.rom.spf.policies.SPFRetriever;
import com.bilgidoku.rom.spf.policies.SPFStrictCheckerRetriever;
import com.bilgidoku.rom.spf.policies.local.BestGuessPolicy;
import com.bilgidoku.rom.spf.policies.local.DefaultExplanationPolicy;
import com.bilgidoku.rom.spf.policies.local.FallbackPolicy;
import com.bilgidoku.rom.spf.policies.local.OverridePolicy;
import com.bilgidoku.rom.spf.policies.local.TrustedForwarderPolicy;
import com.bilgidoku.rom.spf.wiring.WiringServiceTable;

/**
 * This class is used to generate a SPF-Test and provided all intressting data.
 */
public class SPF implements SPFChecker {

    private static final class SPFRecordChecker implements SPFChecker {
        
        /**
         * @see org.apache.james.jspf.core.SPFChecker#checkSPF(org.apache.james.jspf.core.SPFSession)
         */
        public DNSLookupContinuation checkSPF(SPFSession spfData)
                throws PermErrorException, TempErrorException,
                NeutralException, NoneException {
            
            SPF1Record spfRecord = spfData.getSpf1Record();
            // make sure we cleanup the record, for recursion support
            spfData.setSpf1Record(null);
            
            LinkedList<SPFChecker> policyCheckers = new LinkedList<SPFChecker>();
            
            Iterator<SPFChecker> i = spfRecord.iterator();
            while (i.hasNext()) {
                SPFChecker checker = i.next();
                policyCheckers.add(checker);
            }

            while (policyCheckers.size() > 0) {
                SPFChecker removeLast = policyCheckers.removeLast();
                spfData.pushChecker(removeLast);
            }

            return null;
        }
    }

    private static final class PolicyChecker implements SPFChecker {
        
        private LinkedList<SPFChecker> policies;
        
        public PolicyChecker(LinkedList<SPFChecker> policies) {
            this.policies = policies;
        }
        
        /**
         * @see org.apache.james.jspf.core.SPFChecker#checkSPF(org.apache.james.jspf.core.SPFSession)
         */
        public DNSLookupContinuation checkSPF(SPFSession spfData)
                throws PermErrorException, TempErrorException,
                NeutralException, NoneException {
            
            while (policies.size() > 0) {
                SPFChecker removeLast = policies.removeLast();
                spfData.pushChecker(removeLast);
            }
            
            return null;
        }
    }

    private static final class SPFPolicyChecker implements SPFChecker {
        private Policy policy;

        /**
         * @param policy
         */
        public SPFPolicyChecker(Policy policy) {
            this.policy = policy;
        }

        /**
         * @see org.apache.james.jspf.core.SPFChecker#checkSPF(org.apache.james.jspf.core.SPFSession)
         */
        public DNSLookupContinuation checkSPF(SPFSession spfData)
                throws PermErrorException, TempErrorException,
                NeutralException, NoneException {
            SPF1Record res = spfData.getSpf1Record();
            if (res == null) {
                res = policy.getSPFRecord(spfData.getCurrentDomain());
                spfData.setSpf1Record(res);
            }
            return null;
        }
        
        public String toString() {
            return "PC:"+policy.toString();
        }
    }

    private static final class SPFPolicyPostFilterChecker implements SPFChecker {
        private PolicyPostFilter policy;

        /**
         * @param policy
         */
        public SPFPolicyPostFilterChecker(PolicyPostFilter policy) {
            this.policy = policy;
        }

        /**
         * @see org.apache.james.jspf.core.SPFChecker#checkSPF(org.apache.james.jspf.core.SPFSession)
         */
        public DNSLookupContinuation checkSPF(SPFSession spfData)
                throws PermErrorException, TempErrorException,
                NeutralException, NoneException {
            SPF1Record res = spfData.getSpf1Record();
            res = policy.getSPFRecord(spfData.getCurrentDomain(), res);
            spfData.setSpf1Record(res);
            return null;
        }
        
        public String toString() {
            return "PFC:"+policy.toString();
        }

    }


    private SPFRecordParser parser;

    private String defaultExplanation = null;
    
    private boolean useBestGuess = false;

    private FallbackPolicy fallBack;
    
    private OverridePolicy override;
    
    private boolean useTrustedForwarder = false;
    
    private boolean mustEquals = false;

    private MacroExpand macroExpand;

    private SPFExecutor executor;

    /**
     * Uses passed logger and passed dnsServicer
     * 
     * @param dnsProbe the dns provider
     * @param logger the logger to use
     */
    public SPF() {
        super();
        WiringServiceTable wiringService = new WiringServiceTable();
        this.macroExpand = new MacroExpand();
        wiringService.put(MacroExpandEnabled.class, this.macroExpand);
        this.parser = new RFC4408SPF1Parser(new DefaultTermsFactory(wiringService));
        // We add this after the parser creation because services cannot be null
        wiringService.put(SPFCheckEnabled.class, this);
        this.executor = new SynchronousSPFExecutor();
    }
    
    
    /**
     * Uses passed services
     * 
     * @param dnsProbe the dns provider
     * @param parser the parser to use
     * @param logger the logger to use
     */
    public SPF(SPFRecordParser parser, MacroExpand macroExpand, SPFExecutor executor) {
        super();
        this.parser = parser;
        this.macroExpand = macroExpand;
        this.executor = executor;
    }

    
    private static final class DefaultSPFChecker implements SPFChecker, SPFCheckerExceptionCatcher {
        

        /**
         * @see org.apache.james.jspf.core.SPFChecker#checkSPF(org.apache.james.jspf.core.SPFSession)
         */
        public DNSLookupContinuation checkSPF(SPFSession spfData)
                throws PermErrorException, TempErrorException,
                NeutralException, NoneException {
            if (spfData.getCurrentResultExpanded() == null) {
                String resultChar = spfData.getCurrentResult() != null ? spfData.getCurrentResult() : "";
                String result = SPF1Utils.resultToName(resultChar);
                spfData.setCurrentResultExpanded(result);
            }
            return null;
        }
        

        /**
         * @see org.apache.james.jspf.core.SPFCheckerExceptionCatcher#onException(java.lang.Exception, org.apache.james.jspf.core.SPFSession)
         */
        public void onException(Exception exception, SPFSession session)
                throws PermErrorException, NoneException, TempErrorException,
                NeutralException {

            String result;
            if (exception instanceof SPFResultException) {
                result = ((SPFResultException) exception).getResult();
//                if (!SPFErrorConstants.NEUTRAL_CONV.equals(result)) {
//                    mc.c(exception.getMessage(),exception);
//                }
            } else {
                // this should never happen at all. But anyway we will set the
                // result to neutral. Safety first ..
//                mc.c(exception.getMessage(),exception);
                result = SPFErrorConstants.NEUTRAL_CONV;
            }
            session.setCurrentResultExpanded(result);
        }

    }
    
    /**
     * Run check for SPF with the given values.
     * 
     * @param ipAddress
     *            The ipAddress the connection is comming from
     * @param mailFrom
     *            The mailFrom which was provided
     * @param hostName
     *            The hostname which was provided as HELO/EHLO
     * @return result The SPFResult
     */
    public SPFResult checkSPF(String ipAddress, String mailFrom, String hostName) {
        SPFSession spfData = null;

        // Setup the data
        spfData = new SPFSession(mailFrom, hostName, ipAddress);
      

        SPFChecker resultHandler = new DefaultSPFChecker();
        
        spfData.pushChecker(resultHandler);
        spfData.pushChecker(this);
        
        FutureSPFResult ret = new FutureSPFResult();
        
        executor.execute(spfData, ret);

        // if we call ret.getResult it waits the result ;-)
//        //log.info("[ipAddress=" + ipAddress + "] [mailFrom=" + mailFrom
//                + "] [helo=" + hostName + "] => " + ret.getResult());

        return ret;

    }


    /**
     * @see org.apache.james.jspf.core.SPFChecker#checkSPF(org.apache.james.jspf.core.SPFSession)
     */
    public DNSLookupContinuation checkSPF(SPFSession spfData) throws PermErrorException,
            NoneException, TempErrorException, NeutralException {

        // if we already have a result we don't need to add further processing.
        if (spfData.getCurrentResultExpanded() == null && spfData.getCurrentResult() == null) {
            SPFChecker policyChecker = new PolicyChecker(getPolicies());
            SPFChecker recordChecker = new SPFRecordChecker();
            
            spfData.pushChecker(recordChecker);
            spfData.pushChecker(policyChecker);
        }
        
        return null;
    }

    /**
     * Return a default policy for SPF
     */
    public LinkedList<SPFChecker> getPolicies() {

        LinkedList<SPFChecker> policies = new LinkedList<SPFChecker>();
        
        if (override != null) {
            policies.add(new SPFPolicyChecker(override));
        }

        policies.add(new InitialChecksPolicy());

        if (mustEquals) {
            policies.add(new SPFStrictCheckerRetriever());
        } else {
            policies.add(new SPFRetriever());
        }

        if (useBestGuess) {
            policies.add(new SPFPolicyPostFilterChecker(new BestGuessPolicy()));
        }
        
        policies.add(new SPFPolicyPostFilterChecker(new ParseRecordPolicy(parser)));
        
        if (fallBack != null) {
            policies.add(new SPFPolicyPostFilterChecker(fallBack));
        }

        policies.add(new SPFPolicyPostFilterChecker(new NoSPFRecordFoundPolicy()));
        
        // trustedForwarder support is enabled
        if (useTrustedForwarder) {
            policies.add(new SPFPolicyPostFilterChecker(new TrustedForwarderPolicy()));
        }

        policies.add(new SPFPolicyPostFilterChecker(new NeutralIfNotMatchPolicy()));

        policies.add(new SPFPolicyPostFilterChecker(new DefaultExplanationPolicy(defaultExplanation, macroExpand)));
        
        return policies;
    }
    
    /**
     * Set the amount of time (in seconds) before an TermError is returned when
     * the dnsserver not answer. Default is 20 seconds.
     * 
     * @param timeOut The timout in seconds
     */
    public synchronized void setTimeOut(int timeOut) {
//        log.debug("TimeOut was set to: " + timeOut);
        DnsImplement.one().setTimeOut(timeOut);
    }
    
    /**
     * Set the default explanation which will be used if no explanation is found in the SPF Record
     *  
     * @param defaultExplanation The explanation to use if no explanation is found in the SPF Record
     */
    public synchronized void setDefaultExplanation(String defaultExplanation) {
        this.defaultExplanation = defaultExplanation;      
    }
    
    /**
     * Set to true for using best guess. Best guess will set the SPF-Record to "a/24 mx/24 ptr ~all" 
     * if no SPF-Record was found for the doamin. When this was happen only pass or netural will be returned.
     * Default is false.
     * 
     * @param useBestGuess true to enable best guess
     */
    public synchronized void setUseBestGuess(boolean useBestGuess) {
        this.useBestGuess  = useBestGuess;
    }
    
    
    /**
     * Return the FallbackPolicy object which can be used to 
     * provide default spfRecords for hosts which have no records
     * 
     * @return the FallbackPolicy object
     */
    public synchronized FallbackPolicy getFallbackPolicy() {
        // Initialize fallback policy
        if (fallBack == null) {
            this.fallBack =  new FallbackPolicy(parser);
        }
        return fallBack;
    }
    
    /**
     * Set to true to enable trusted-forwarder.org whitelist. The whitelist will only be queried if
     * the last Mechanism is -all or ?all. 
     * See http://trusted-forwarder.org for more informations
     * Default is false.
     * 
     * @param useTrustedForwarder true or false
     */
    public synchronized void setUseTrustedForwarder(boolean useTrustedForwarder) {
        this.useTrustedForwarder = useTrustedForwarder;
    }
    
    /**
     * Return the OverridePolicy object which can be used to
     * override spfRecords for hosts
     * 
     * @return the OverridePolicy object
     */
    public synchronized OverridePolicy getOverridePolicy() {
        if (override == null) {
            override = new OverridePolicy(parser);
        }
        return override;
    }
    
    /**
     * Set to true if a PermError should returned when a domain publish a SPF-Type
     * and TXT-Type SPF-Record and both are not equals. Defaults false
     * 
     * @param mustEquals true or false
     */
    public synchronized void setSPFMustEqualsTXT(boolean mustEquals) {
        this.mustEquals = mustEquals;
    }


}
