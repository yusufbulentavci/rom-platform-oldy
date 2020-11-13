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

package com.bilgidoku.rom.mime4j.message;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import com.bilgidoku.rom.mime4j.codec.DecodeMonitor;
import com.bilgidoku.rom.mime4j.dom.Header;
import com.bilgidoku.rom.mime4j.dom.Message;
import com.bilgidoku.rom.mime4j.dom.address.Address;
import com.bilgidoku.rom.mime4j.dom.address.Mailbox;
import com.bilgidoku.rom.mime4j.dom.field.AddressListField;
import com.bilgidoku.rom.mime4j.dom.field.ContentDispositionField;
import com.bilgidoku.rom.mime4j.dom.field.ContentTransferEncodingField;
import com.bilgidoku.rom.mime4j.dom.field.ContentTypeField;
import com.bilgidoku.rom.mime4j.dom.field.DateTimeField;
import com.bilgidoku.rom.mime4j.dom.field.FieldName;
import com.bilgidoku.rom.mime4j.dom.field.MailboxField;
import com.bilgidoku.rom.mime4j.dom.field.MailboxListField;
import com.bilgidoku.rom.mime4j.dom.field.UnstructuredField;
import com.bilgidoku.rom.mime4j.field.ContentTransferEncodingFieldImpl;
import com.bilgidoku.rom.mime4j.field.ContentTypeFieldImpl;
import com.bilgidoku.rom.mime4j.field.Fields;
import com.bilgidoku.rom.mime4j.field.MimeVersionFieldLenientImpl;
import com.bilgidoku.rom.mime4j.stream.RawField;
import com.bilgidoku.rom.mime4j.util.MimeUtil;

/**
 * Default implementation of {@link Message}.
 */
public class MessageImpl extends AbstractMessage {

    /**
     * Creates a new empty <code>Message</code>.
     */
    public MessageImpl() {
        super();
        Header header = obtainHeader();
        RawField rawField = new RawField(FieldName.MIME_VERSION, "1.0");
        header.addField(MimeVersionFieldLenientImpl.PARSER.parse(rawField, DecodeMonitor.SILENT));
    }

    @Override
    protected String newUniqueBoundary() {
        return MimeUtil.createUniqueBoundary();
    }

    @Override
    protected UnstructuredField newMessageId(String hostname) {
        return Fields.messageId(hostname);
    }

    @Override
    protected DateTimeField newDate(Date date, TimeZone zone) {
        return Fields.date(FieldName.DATE, date, zone);
    }

    @Override
    protected MailboxField newMailbox(String fieldName, Mailbox mailbox) {
        return Fields.mailbox(fieldName, mailbox);
    }

    @Override
    protected MailboxListField newMailboxList(String fieldName,
            Collection<Mailbox> mailboxes) {
        return Fields.mailboxList(fieldName, mailboxes);
    }

    @Override
    protected AddressListField newAddressList(String fieldName,
            Collection<? extends Address> addresses) {
        return Fields.addressList(fieldName, addresses);
    }

    @Override
    protected UnstructuredField newSubject(String subject) {
        return Fields.subject(subject);
    }

    @Override
    protected ContentDispositionField newContentDisposition(
            String dispositionType, String filename, long size,
            Date creationDate, Date modificationDate, Date readDate) {
        return Fields.contentDisposition(dispositionType, filename, size,
                creationDate, modificationDate, readDate);
    }

    @Override
    protected ContentDispositionField newContentDisposition(
            String dispositionType, Map<String, String> parameters) {
        return Fields.contentDisposition(dispositionType, parameters);
    }

    @Override
    protected ContentTypeField newContentType(String mimeType,
            Map<String, String> parameters) {
        return Fields.contentType(mimeType, parameters);
    }

    @Override
    protected ContentTransferEncodingField newContentTransferEncoding(
            String contentTransferEncoding) {
        return Fields.contentTransferEncoding(contentTransferEncoding);
    }

    @Override
    protected String calcTransferEncoding(ContentTransferEncodingField f) {
        return ContentTransferEncodingFieldImpl.getEncoding(f);
    }

    @Override
    protected String calcMimeType(ContentTypeField child, ContentTypeField parent) {
        return ContentTypeFieldImpl.getMimeType(child, parent);
    }

    @Override
    protected String calcCharset(ContentTypeField contentType) {
        return ContentTypeFieldImpl.getCharset(contentType);
    }

}
