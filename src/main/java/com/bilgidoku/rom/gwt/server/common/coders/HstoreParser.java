package com.bilgidoku.rom.gwt.server.common.coders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.shared.err.KnownError;

public class HstoreParser {

    private String value;
    private int ptr;
    private StringBuffer cur;
    private boolean escaped;

    private List<String> keys;
    private List<String> values;

    private final static int GV_WAITVAL = 0;
    private final static int GV_INVAL = 1;
    private final static int GV_INESCVAL = 2;
    private final static int GV_WAITESCIN = 3;
    private final static int GV_WAITESCESCIN = 4;

    private final static int WKEY = 0;
    private final static int WVAL = 1;
    private final static int WEQ = 2;
    private final static int WGT = 3;
    private final static int WDEL = 4;

    Map<String, String> parse(String val) throws KnownError{
            this.value = val;
            ptr = 0;
            keys = new ArrayList<String>();
            values = new ArrayList<String>();

            parseHStore();

            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < keys.size(); i++) {
                    map.put(keys.get(i), values.get(i));
            }

            return map;
    }

    private boolean getValue(boolean ignoreEqual) throws KnownError {
            int state = GV_WAITVAL;

            cur = new StringBuffer();
            escaped = false;

            while (true) {
                    boolean atEnd = (value.length() == ptr);
                    char c = '\0';
                    if (!atEnd) {
                            c = value.charAt(ptr);
                    }

                    if (state == GV_WAITVAL) {
                            if (c == '"') {
                                    escaped = true;
                                    state = GV_INESCVAL;
                            } else if (c == '\0') {
                                    return false;
                            } else if (c == '=' && !ignoreEqual) {
                                    throw new KnownError("Hstore parsing error");
                            } else if (c == '\\') {
                                    state = GV_WAITESCIN;
                            } else if (!Character.isWhitespace(c)) {
                                    cur.append(c);
                                    state = GV_INVAL;
                            }
                    } else if (state == GV_INVAL) {
                            if (c == '\\') {
                                    state = GV_WAITESCIN;
                            } else if (c == '=' && !ignoreEqual) {
                                    ptr--;
                                    return true;
                            } else if (c == ',' && ignoreEqual) {
                                    ptr--;
                                    return true;
                            } else if (Character.isWhitespace(c)) {
                                    return true;
                            } else if (c == '\0') {
                                    ptr--;
                                    return true;
                            } else {
                                    cur.append(c);
                            }
                    } else if (state == GV_INESCVAL) {
                            if (c == '\\') {
                                    state = GV_WAITESCESCIN;
                            } else if (c == '"') {
                                    return true;
                            } else if (c == '\0') {
                                    throw new KnownError("Hstore parsing error, unexpected end of string");
                            } else {
                                    cur.append(c);
                            }
                    } else if (state == GV_WAITESCIN) {
                            if (c == '\0') {
                                    throw new KnownError("Hstore parsing error, unexpected end of string");
                            }

                            cur.append(c);
                            state = GV_INVAL;
                    } else if (state == GV_WAITESCESCIN) {
                            if (c == '\0') {
                                    throw new KnownError("Hstore parsing error, unexpected end of string");
                            }

                            cur.append(c);
                            state = GV_INESCVAL;
                    } else {
                            throw new KnownError("Hstore parsing error");
                    }

                    ptr++;
            }
    }

    private void parseHStore() throws KnownError {
            int state = WKEY;
            escaped = false;

            while (true) {
                    char c = '\0';
                    if (ptr < value.length()) {
                            c = value.charAt(ptr);
                    }

                    if (state == WKEY) {
                            if (!getValue(false))
                                    return;

                            keys.add(cur.toString());
                            cur = null;
                            state = WEQ;
                    } else if (state == WEQ) {
                            if (c == '=') {
                                    state = WGT;
                            } else if (state == '\0') {
                                    throw new KnownError("hstore parsing error, unexpected end of string");
                            } else if (!Character.isWhitespace(c)) {
                                    throw new KnownError("hstore parsing error, syntax err");
                            }
                    } else if (state == WGT) {
                            if (c == '>') {
                                    state = WVAL;
                            } else if (c == '\0') {
                                    throw new KnownError("hstore parsing error, unexpected end of string");
                            } else {
                                    throw new KnownError("hstore parsing error, syntax err [" + c + "] at " + ptr);
                            }
                    } else if (state == WVAL) {
                            if (!getValue(true)) {
                                    throw new KnownError("hstore parsing error, unexpected end of string");
                            }

                            String val = cur.toString();
                            cur = null;
                            if (!escaped && "null".equalsIgnoreCase(val)) {
                                    val = null;
                            }

                            values.add(val);
                            state = WDEL;
                    } else if (state == WDEL) {
                            if (c == ',')
                            {
                                    state = WKEY;
                            } else if (c == '\0') {
                                    return;
                            } else if (!Character.isWhitespace(c)) {
                                    throw new KnownError("hstore parsing error, syntax err");
                            }
                    } else {
                            throw new KnownError("hstore parsing error unknown state");
                    }

                    ptr++;
            }
    }

}
