/* Generated By:JavaCC: Do not edit this line. ContentLanguageParserConstants.java */
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
package com.bilgidoku.rom.mime4j.field.language.parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ContentLanguageParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int WS = 3;
  /** RegularExpression Id. */
  int COMMENT = 5;
  /** RegularExpression Id. */
  int QUOTEDSTRING = 16;
  /** RegularExpression Id. */
  int DIGITS = 17;
  /** RegularExpression Id. */
  int ALPHA = 18;
  /** RegularExpression Id. */
  int ALPHANUM = 19;
  /** RegularExpression Id. */
  int DOT = 20;
  /** RegularExpression Id. */
  int QUOTEDPAIR = 21;
  /** RegularExpression Id. */
  int ANY = 22;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int INCOMMENT = 1;
  /** Lexical state. */
  int NESTED_COMMENT = 2;
  /** Lexical state. */
  int INQUOTEDSTRING = 3;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\",\"",
    "\"-\"",
    "<WS>",
    "\"(\"",
    "\")\"",
    "<token of kind 6>",
    "\"(\"",
    "<token of kind 8>",
    "<token of kind 9>",
    "\"(\"",
    "\")\"",
    "<token of kind 12>",
    "\"\\\"\"",
    "<token of kind 14>",
    "<token of kind 15>",
    "\"\\\"\"",
    "<DIGITS>",
    "<ALPHA>",
    "<ALPHANUM>",
    "\".\"",
    "<QUOTEDPAIR>",
    "<ANY>",
  };

}
