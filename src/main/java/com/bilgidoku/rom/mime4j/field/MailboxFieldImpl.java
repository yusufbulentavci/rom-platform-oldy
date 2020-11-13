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

package com.bilgidoku.rom.mime4j.field;

import com.bilgidoku.rom.mime4j.codec.DecodeMonitor;
import com.bilgidoku.rom.mime4j.dom.FieldParser;
import com.bilgidoku.rom.mime4j.dom.address.Mailbox;
import com.bilgidoku.rom.mime4j.dom.field.MailboxField;
import com.bilgidoku.rom.mime4j.dom.field.ParseException;
import com.bilgidoku.rom.mime4j.field.address.AddressBuilder;
import com.bilgidoku.rom.mime4j.stream.Field;

/**
 * Mailbox field such as <code>Sender</code> or <code>Resent-Sender</code>.
 */
public class MailboxFieldImpl extends AbstractField implements MailboxField {
    private boolean parsed = false;

    private Mailbox mailbox;
    private ParseException parseException;

    MailboxFieldImpl(Field rawField, DecodeMonitor monitor) {
        super(rawField, monitor);
    }

    /**
     * @see com.bilgidoku.rom.mime4j.dom.field.MailboxField#getMailbox()
     */
    public Mailbox getMailbox() {
        if (!parsed)
            parse();

        return mailbox;
    }

    /**
     * @see com.bilgidoku.rom.mime4j.dom.field.MailboxField#getParseException()
     */
    @Override
    public ParseException getParseException() {
        if (!parsed)
            parse();

        return parseException;
    }

    private void parse() {
        String body = getBody();

        try {
            mailbox = AddressBuilder.DEFAULT.parseMailbox(body, monitor);
        } catch (ParseException e) {
            parseException = e;
        }

        parsed = true;
    }

    public static final FieldParser<MailboxField> PARSER = new FieldParser<MailboxField>() {

        public MailboxField parse(final Field rawField, final DecodeMonitor monitor) {
            return new MailboxFieldImpl(rawField, monitor);
        }

    };
}
