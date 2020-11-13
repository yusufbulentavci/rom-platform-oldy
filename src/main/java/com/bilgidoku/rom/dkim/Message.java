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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import com.bilgidoku.rom.dkim.api.Headers;
import com.bilgidoku.rom.mime4j.MimeException;
import com.bilgidoku.rom.mime4j.MimeIOException;
import com.bilgidoku.rom.mime4j.dom.MessageBuilder;
import com.bilgidoku.rom.mime4j.dom.MessageServiceFactory;
import com.bilgidoku.rom.mime4j.dom.MessageWriter;
import com.bilgidoku.rom.mime4j.dom.SingleBody;
import com.bilgidoku.rom.mime4j.io.EOLConvertingInputStream;
import com.bilgidoku.rom.mime4j.message.MessageServiceFactoryImpl;
import com.bilgidoku.rom.mime4j.stream.Field;
import com.bilgidoku.rom.mime4j.stream.MimeConfig;

/**
 * The header of an entity (see RFC 2045).
 */
public class Message implements Headers {

    private com.bilgidoku.rom.mime4j.dom.Message message;

    /**
     * Creates a new <code>Header</code> from the specified stream.
     *
     * @param is the stream to read the header from.
     * @throws IOException     on I/O errors.
     * @throws MimeIOException on MIME protocol violations.
     */
    public Message(InputStream is) throws IOException, MimeException {
        MessageBuilder mb = newMessageBuilder().newMessageBuilder();

        this.message = mb.parseMessage(new EOLConvertingInputStream(is));
    }

    private MessageServiceFactory newMessageBuilder() throws MimeException {
        MimeConfig mec = new MimeConfig();
        mec.setMaxLineLen(10000);
        mec.setMaxHeaderLen(30000);

        //bilo:
        MessageServiceFactory mbf = new MessageServiceFactoryImpl();
//        		MessageServiceFactory.newInstance();
        mbf.setAttribute("MimeEntityConfig", mec);
        mbf.setAttribute("FlatMode", true);
        mbf.setAttribute("ContentDecoding", false);

        return mbf;
    }

    public InputStream getBodyInputStream() {
        try {
            return ((SingleBody) message.getBody()).getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * @see org.apache.james.jdkim.api.Headers#getFields()
     */
    public List<String> getFields() {
        List<Field> res = message.getHeader().getFields();
        return convertFields(res);
    }

    private List<String> convertFields(List<Field> res) {
        List<String> res2 = new LinkedList<String>();
        MessageWriter mw;
        try {
            mw = newMessageBuilder().newMessageWriter();
        } catch (MimeException e1) {
            return res2;
        }
        for (Field f : res) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            String field = null;
            try {
                mw.writeField(f, bos);
                // writeField always ends with CRLF and we don't want it.
                byte[] fieldbytes = bos.toByteArray();
                field = new String(fieldbytes, 0, fieldbytes.length - 2);
            } catch (IOException e) {
            }
            res2.add(field);
        }
        return res2;
    }

    /**
     * @see org.apache.james.jdkim.api.Headers#getFields(java.lang.String)
     */
    public List<String> getFields(final String name) {
        return convertFields(message.getHeader().getFields(name));
    }

    /**
     * Return Header Object as String representation. Each headerline is
     * seperated by "\r\n"
     *
     * @return headers
     */
    public String toString() {
        return message.toString();
    }

    /**
     * Make sure to dispose the message once used.
     */
    public void dispose() {
        this.message.dispose();
    }
}