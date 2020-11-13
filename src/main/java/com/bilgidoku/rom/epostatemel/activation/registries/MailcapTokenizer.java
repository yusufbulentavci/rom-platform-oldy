/*
 * The contents of this file are subject to the terms 
 * of the Common Development and Distribution License 
 * (the "License").  You may not use this file except 
 * in compliance with the License.
 * 
 * You can obtain a copy of the license at 
 * glassfish/bootstrap/legal/CDDLv1.0.txt or 
 * https://glassfish.dev.java.net/public/CDDLv1.0.html. 
 * See the License for the specific language governing 
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL 
 * HEADER in each file and include the License file at 
 * glassfish/bootstrap/legal/CDDLv1.0.txt.  If applicable, 
 * add the following below this CDDL HEADER, with the 
 * fields enclosed by brackets "[]" replaced with your 
 * own identifying information: Portions Copyright [yyyy] 
 * [name of copyright owner]
 */

/*
 * @(#)MailcapTokenizer.java	1.6 05/11/16
 *
 * Copyright 1997-2005 Sun Microsystems, Inc. All Rights Reserved.
 */

package	com.bilgidoku.rom.epostatemel.activation.registries;

/**
 *	A tokenizer for strings in the form of "foo/bar; prop1=val1; ... ".
 *	Useful for parsing MIME content types.
 */
public class MailcapTokenizer {

    public static final int UNKNOWN_TOKEN = 0;
    public static final int START_TOKEN = 1;
    public static final int STRING_TOKEN = 2;
    public static final int EOI_TOKEN = 5;
    public static final int SLASH_TOKEN = '/';
    public static final int SEMICOLON_TOKEN = ';';
    public static final int EQUALS_TOKEN = '=';

    /**
     *  Constructor
     *
     *  @parameter  inputString the string to tokenize
     */
    public MailcapTokenizer(String inputString) {
	data = inputString;
	dataIndex = 0;
	dataLength = inputString.length();

	currentToken = START_TOKEN;
	currentTokenValue = "";

	isAutoquoting = false;
	autoquoteChar = ';';
    }

    /**
     *  Set whether auto-quoting is on or off.
     *
     *  Auto-quoting means that all characters after the first
     *  non-whitespace, non-control character up to the auto-quote
     *  terminator character or EOI (minus any whitespace immediatley
     *  preceeding it) is considered a token.
     *
     *  This is required for handling command strings in a mailcap entry.
     */
    public void setIsAutoquoting(boolean value) {
	isAutoquoting = value;
    }

    /**
     *  Set the auto-quote terminating character.
     */
    public void setAutoquoteChar(char value) {
	autoquoteChar = value;
    }

    /**
     *  Retrieve current token.
     *
     *  @returns    The current token value
     */
    public int getCurrentToken() {
	return currentToken;
    }

    /*
     *  Get a String that describes the given token.
     */
    public static String nameForToken(int token) {
	String name = "really unknown";

	switch(token) {
	    case UNKNOWN_TOKEN:
		name = "unknown";
		break;
	    case START_TOKEN:
		name = "start";
		break;
	    case STRING_TOKEN:
		name = "string";
		break;
	    case EOI_TOKEN:
		name = "EOI";
		break;
	    case SLASH_TOKEN:
		name = "'/'";
		break;
	    case SEMICOLON_TOKEN:
		name = "';'";
		break;
	    case EQUALS_TOKEN:
		name = "'='";
		break;
	}

	return name;
    }

    /*
     *  Retrieve current token value.
     *
     *  @returns    A String containing the current token value
     */
    public String getCurrentTokenValue() {
	return currentTokenValue;
    }

    /*
     *  Process the next token.
     *
     *  @returns    the next token
     */
    public int nextToken() {
	if (dataIndex < dataLength) {
	    //  skip white space
	    while ((dataIndex < dataLength) &&
		    (isWhiteSpaceChar(data.charAt(dataIndex)))) {
		++dataIndex;
	    }

	    if (dataIndex < dataLength) {
		//  examine the current character and see what kind of token we have
		char c = data.charAt(dataIndex);
		if (isAutoquoting) {
		    if (!isAutoquoteSpecialChar(c)) {
			processAutoquoteToken();
		    } else if ((c == ';') || (c == '=')) {
			currentToken = c;
			currentTokenValue = new Character(c).toString();
			++dataIndex;
		    } else {
			currentToken = UNKNOWN_TOKEN;
			currentTokenValue = new Character(c).toString();
			++dataIndex;
		    }
		} else {
		    if (isStringTokenChar(c)) {
			processStringToken();
		    } else if ((c == '/') || (c == ';') || (c == '=')) {
			currentToken = c;
			currentTokenValue = new Character(c).toString();
			++dataIndex;
		    } else {
			currentToken = UNKNOWN_TOKEN;
			currentTokenValue = new Character(c).toString();
			++dataIndex;
		    }
		}
	    } else {
		currentToken = EOI_TOKEN;
		currentTokenValue = null;
	    }
	} else {
	    currentToken = EOI_TOKEN;
	    currentTokenValue = null;
	}

	return currentToken;
    }

    private void processStringToken() {
	//  capture the initial index
	int initialIndex = dataIndex;

	//  skip to 1st non string token character
	while ((dataIndex < dataLength) &&
		isStringTokenChar(data.charAt(dataIndex))) {
	    ++dataIndex;
	}

	currentToken = STRING_TOKEN;
	currentTokenValue = data.substring(initialIndex, dataIndex);
    }

    private void processAutoquoteToken() {
	//  capture the initial index
	int initialIndex = dataIndex;

	//  now skip to the 1st non-escaped autoquote termination character
	boolean foundTerminator = false;
	while ((dataIndex < dataLength) && !foundTerminator) {
	    char c = data.charAt(dataIndex);
	    if (c != autoquoteChar) {
		++dataIndex;
	    } else {
		foundTerminator = true;
	    }
	}

	currentToken = STRING_TOKEN;
	currentTokenValue =
	    fixEscapeSequences(data.substring(initialIndex, dataIndex));
    }

    public static boolean isSpecialChar(char c) {
	boolean lAnswer = false;

	switch(c) {
	    case '(':
	    case ')':
	    case '<':
	    case '>':
	    case '@':
	    case ',':
	    case ';':
	    case ':':
	    case '\\':
	    case '"':
	    case '/':
	    case '[':
	    case ']':
	    case '?':
	    case '=':
		lAnswer = true;
		break;
	}

	return lAnswer;
    }

    public static boolean isAutoquoteSpecialChar(char c) {
	boolean lAnswer = false;

	switch(c) {
	    case ';':
	    case '=':
		lAnswer = true;
		break;
	}

	return lAnswer;
    }

    public static boolean isControlChar(char c) {
	return Character.isISOControl(c);
    }

    public static boolean isWhiteSpaceChar(char c) {
	return Character.isWhitespace(c);
    }

    public static boolean isStringTokenChar(char c) {
	return !isSpecialChar(c) && !isControlChar(c) && !isWhiteSpaceChar(c);
    }

    private static String fixEscapeSequences(String inputString) {
	int inputLength = inputString.length();
	StringBuffer buffer = new StringBuffer();
	buffer.ensureCapacity(inputLength);

	for (int i = 0; i < inputLength; ++i) {
	    char currentChar = inputString.charAt(i);
	    if (currentChar != '\\') {
		buffer.append(currentChar);
	    } else {
		if (i < inputLength - 1) {
		    char nextChar = inputString.charAt(i + 1);
		    buffer.append(nextChar);

		    //  force a skip over the next character too
		    ++i;
		} else {
		    buffer.append(currentChar);
		}
	    }
	}

	return buffer.toString();
    }

    private String  data;
    private int     dataIndex;
    private int     dataLength;
    private int     currentToken;
    private String  currentTokenValue;
    private boolean isAutoquoting;
    private char    autoquoteChar;

    /*
    public static void main(String[] args) {
	for (int i = 0; i < args.length; ++i) {
	    MailcapTokenizer tokenizer = new MailcapTokenizer(args[i]);

	    com.bilgidoku.rom.gunluk.Sistem.outln("Original: |" + args[i] + "|");

	    int currentToken = tokenizer.nextToken();
	    while (currentToken != EOI_TOKEN) {
		switch(currentToken) {
		    case UNKNOWN_TOKEN:
			com.bilgidoku.rom.gunluk.Sistem.outln("  Unknown Token:           |" + tokenizer.getCurrentTokenValue() + "|");
			break;
		    case START_TOKEN:
			com.bilgidoku.rom.gunluk.Sistem.outln("  Start Token:             |" + tokenizer.getCurrentTokenValue() + "|");
			break;
		    case STRING_TOKEN:
			com.bilgidoku.rom.gunluk.Sistem.outln("  String Token:            |" + tokenizer.getCurrentTokenValue() + "|");
			break;
		    case EOI_TOKEN:
			com.bilgidoku.rom.gunluk.Sistem.outln("  EOI Token:               |" + tokenizer.getCurrentTokenValue() + "|");
			break;
		    case SLASH_TOKEN:
			com.bilgidoku.rom.gunluk.Sistem.outln("  Slash Token:             |" + tokenizer.getCurrentTokenValue() + "|");
			break;
		    case SEMICOLON_TOKEN:
			com.bilgidoku.rom.gunluk.Sistem.outln("  Semicolon Token:         |" + tokenizer.getCurrentTokenValue() + "|");
			break;
		    case EQUALS_TOKEN:
			com.bilgidoku.rom.gunluk.Sistem.outln("  Equals Token:            |" + tokenizer.getCurrentTokenValue() + "|");
			break;
		    default:
			com.bilgidoku.rom.gunluk.Sistem.outln("  Really Unknown Token:    |" + tokenizer.getCurrentTokenValue() + "|");
			break;
		}

		currentToken = tokenizer.nextToken();
	    }

	    com.bilgidoku.rom.gunluk.Sistem.outln("");
	}
    }
    */
}
