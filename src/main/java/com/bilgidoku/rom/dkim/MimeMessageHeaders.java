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

package com.bilgidoku.rom.dkim;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.dkim.api.Headers;
import com.bilgidoku.rom.epostatemel.javam.mail.MessagingException;
import com.bilgidoku.rom.epostatemel.javam.mail.internet.MimeMessage;

/**
 * An adapter to let DKIMSigner read headers from MimeMessage
 */
final public class MimeMessageHeaders implements Headers {

    private final Map<String, List<String>> headers;
    private final List<String> fields;

    @SuppressWarnings("unchecked")
    public MimeMessageHeaders(MimeMessage message)
            throws MessagingException {
        headers = new HashMap<String, List<String>>();
        fields = new LinkedList<String>();
        for (Enumeration<String> e = message.getAllHeaderLines(); e
                .hasMoreElements();) {
            String head = e.nextElement();
            int p = head.indexOf(':');
            if (p <= 0)
                throw new MessagingException("Bad header line: " + head);
            String headerName = head.substring(0, p).trim();
            String headerNameLC = headerName.toLowerCase();
            fields.add(headerName);
            List<String> strings = headers.get(headerNameLC);
            if (strings == null) {
                strings = new LinkedList<String>();
                headers.put(headerNameLC, strings);
            }
            strings.add(head);
        }
    }

    public List<String> getFields() {
        return fields;
    }

    public List<String> getFields(String name) {
        return headers.get(name.toLowerCase());
    }
}