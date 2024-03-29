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

import java.io.StringReader;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.bilgidoku.rom.mime4j.codec.DecodeMonitor;
import com.bilgidoku.rom.mime4j.dom.FieldParser;
import com.bilgidoku.rom.mime4j.dom.field.ContentDispositionField;
import com.bilgidoku.rom.mime4j.field.contentdisposition.parser.ContentDispositionParser;
import com.bilgidoku.rom.mime4j.field.contentdisposition.parser.ParseException;
import com.bilgidoku.rom.mime4j.field.contenttype.parser.TokenMgrError;
import com.bilgidoku.rom.mime4j.field.datetime.parser.DateTimeParser;
import com.bilgidoku.rom.mime4j.stream.Field;


/**
 * Represents a <code>Content-Disposition</code> field.
 */
public class ContentDispositionFieldImpl extends AbstractField implements ContentDispositionField {

    private boolean parsed = false;

    private String dispositionType = "";
    private Map<String, String> parameters = new HashMap<String, String>();
    private ParseException parseException;

    private boolean creationDateParsed;
    private Date creationDate;

    private boolean modificationDateParsed;
    private Date modificationDate;

    private boolean readDateParsed;
    private Date readDate;

    ContentDispositionFieldImpl(Field rawField, DecodeMonitor monitor) {
        super(rawField, monitor);
    }

    /**
     * Gets the exception that was raised during parsing of the field value, if
     * any; otherwise, null.
     */
    @Override
    public ParseException getParseException() {
        if (!parsed)
            parse();

        return parseException;
    }

    /**
     * @see com.bilgidoku.rom.mime4j.dom.field.ContentDispositionField#getDispositionType()
     */
    public String getDispositionType() {
        if (!parsed)
            parse();

        return dispositionType;
    }

    /**
     * @see com.bilgidoku.rom.mime4j.dom.field.ContentDispositionField#getParameter(java.lang.String)
     */
    public String getParameter(String name) {
        if (!parsed)
            parse();

        return parameters.get(name.toLowerCase());
    }

    /**
     * @see com.bilgidoku.rom.mime4j.dom.field.ContentDispositionField#getParameters()
     */
    public Map<String, String> getParameters() {
        if (!parsed)
            parse();

        return Collections.unmodifiableMap(parameters);
    }

    /**
     * @see com.bilgidoku.rom.mime4j.dom.field.ContentDispositionField#isDispositionType(java.lang.String)
     */
    public boolean isDispositionType(String dispositionType) {
        if (!parsed)
            parse();

        return this.dispositionType.equalsIgnoreCase(dispositionType);
    }

    /**
     * @see com.bilgidoku.rom.mime4j.dom.field.ContentDispositionField#isInline()
     */
    public boolean isInline() {
        if (!parsed)
            parse();

        return dispositionType.equals(DISPOSITION_TYPE_INLINE);
    }

    /**
     * @see com.bilgidoku.rom.mime4j.dom.field.ContentDispositionField#isAttachment()
     */
    public boolean isAttachment() {
        if (!parsed)
            parse();

        return dispositionType.equals(DISPOSITION_TYPE_ATTACHMENT);
    }

    /**
     * @see com.bilgidoku.rom.mime4j.dom.field.ContentDispositionField#getFilename()
     */
    public String getFilename() {
        return getParameter(PARAM_FILENAME);
    }

    /**
     * @see com.bilgidoku.rom.mime4j.dom.field.ContentDispositionField#getCreationDate()
     */
    public Date getCreationDate() {
        if (!creationDateParsed) {
            creationDate = parseDate(PARAM_CREATION_DATE);
            creationDateParsed = true;
        }

        return creationDate;
    }

    /**
     * @see com.bilgidoku.rom.mime4j.dom.field.ContentDispositionField#getModificationDate()
     */
    public Date getModificationDate() {
        if (!modificationDateParsed) {
            modificationDate = parseDate(PARAM_MODIFICATION_DATE);
            modificationDateParsed = true;
        }

        return modificationDate;
    }

    /**
     * @see com.bilgidoku.rom.mime4j.dom.field.ContentDispositionField#getReadDate()
     */
    public Date getReadDate() {
        if (!readDateParsed) {
            readDate = parseDate(PARAM_READ_DATE);
            readDateParsed = true;
        }

        return readDate;
    }

    /**
     * @see com.bilgidoku.rom.mime4j.dom.field.ContentDispositionField#getSize()
     */
    public long getSize() {
        String value = getParameter(PARAM_SIZE);
        if (value == null)
            return -1;

        try {
            long size = Long.parseLong(value);
            return size < 0 ? -1 : size;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private Date parseDate(String paramName) {
        String value = getParameter(paramName);
        if (value == null) {
            monitor.warn("Parsing " + paramName + " null", "returning null");
            return null;
        }

        try {
            return new DateTimeParser(new StringReader(value)).parseAll()
                    .getDate();
        } catch (com.bilgidoku.rom.mime4j.field.datetime.parser.ParseException e) {
            if (monitor.isListening()) {
                monitor.warn(paramName + " parameter is invalid: " + value,
                        paramName + " parameter is ignored");
            }
            return null;
        } catch (TokenMgrError e) {
            monitor.warn(paramName + " parameter is invalid: " + value,
                    paramName + "parameter is ignored");
            return null;
        }
    }

    private void parse() {
        String body = getBody();

        ContentDispositionParser parser = new ContentDispositionParser(
                new StringReader(body));
        try {
            parser.parseAll();
        } catch (ParseException e) {
            parseException = e;
        } catch (TokenMgrError e) {
            parseException = new ParseException(e.getMessage());
        }

        final String dispositionType = parser.getDispositionType();

        if (dispositionType != null) {
            this.dispositionType = dispositionType.toLowerCase(Locale.US);

            List<String> paramNames = parser.getParamNames();
            List<String> paramValues = parser.getParamValues();

            if (paramNames != null && paramValues != null) {
                final int len = Math.min(paramNames.size(), paramValues.size());
                for (int i = 0; i < len; i++) {
                    String paramName = paramNames.get(i).toLowerCase(Locale.US);
                    String paramValue = paramValues.get(i);
                    parameters.put(paramName, paramValue);
                }
            }
        }

        parsed = true;
    }

    public static final FieldParser<ContentDispositionField> PARSER = new FieldParser<ContentDispositionField>() {

        public ContentDispositionField parse(final Field rawField, final DecodeMonitor monitor) {
            return new ContentDispositionFieldImpl(rawField, monitor);
        }

    };
}
