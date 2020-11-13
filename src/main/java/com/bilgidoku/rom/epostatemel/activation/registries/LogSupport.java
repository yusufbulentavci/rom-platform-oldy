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
 * @(#)LogSupport.java	1.4 05/11/16
 *
 * Copyright 2002-2005 Sun Microsystems, Inc. All Rights Reserved.
 */

package com.bilgidoku.rom.epostatemel.activation.registries;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.bilgidoku.rom.min.Sistem;

/**
 * Logging related methods.
 */
public class LogSupport {
	private static boolean debug = false;
	private static Logger logger;
	private static final Level level = Level.FINE;

	static {
//	try {
//	    debug = Boolean.getBoolean("javax.activation.debug");
//	} catch (Throwable t) {
//	    // ignore any errors
//	}
		logger = Logger.getLogger("javax.activation");
	}

	/**
	 * Constructor.
	 */
	private LogSupport() {
		// private constructor, can't create instances
	}

	public static void log(String msg) {
		if (debug)
			Sistem.outln(msg);
		logger.log(level, msg);
	}

	public static void log(String msg, Throwable t) {
		if (debug)
			Sistem.outln(msg + "; Exception: " + t);
		logger.log(level, msg, t);
	}

	public static boolean isLoggable() {
		return debug || logger.isLoggable(level);
	}
}
