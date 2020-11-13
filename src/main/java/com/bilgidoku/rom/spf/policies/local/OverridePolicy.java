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
import com.bilgidoku.rom.spf.core.SPFRecordParser;
import com.bilgidoku.rom.spf.policies.Policy;

public class OverridePolicy extends FallbackPolicy implements Policy {

    public OverridePolicy(SPFRecordParser parser) {
        super(parser);
    }
    

    /**
     * @see org.apache.james.jspf.policies.Policy#getSPFRecord(java.lang.String)
     */
    public SPF1Record getSPFRecord(String currentDomain)
            throws PermErrorException, TempErrorException, NoneException,
            NeutralException {
        return getMySPFRecord(currentDomain);
    }

}
