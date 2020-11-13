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
package com.bilgidoku.rom.smtp;

import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.protokol.protocols.ProtocolSessionExtension;
import com.bilgidoku.rom.session.OturumGorevlisi;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.smtp.hook.AuthHook;
import com.bilgidoku.rom.smtp.hook.HookResult;
import com.bilgidoku.rom.smtp.hook.HookReturnCode;


public class UsersRepositoryAuthHook implements AuthHook {
	private static final MC mc = new MC(UsersRepositoryAuthHook.class);
	private static final ProtocolSessionExtension sessionExtension = (ProtocolSessionExtension) OturumGorevlisi.tek().getExtension("smtp");

	
	/**
	 * @throws KnownError 
	 * @see com.bilgidoku.rom.smtp.hook.AuthHook#doAuth(com.bilgidoku.rom.smtp.SMTPSession,
	 *      java.lang.String, java.lang.String)
	 */
	public HookResult doAuth(SMTPSession session, String username, String password) throws KnownError {
		
		int ret=sessionExtension.protocolLogin(session, username, password);
		if (ret==0) {
			session.setRelayingAllowed(true);
			return new HookResult(HookReturnCode.OK, "Authentication Successful");
		}else if(ret==-1){
			return new HookResult(HookReturnCode.DISCONNECT, SMTPRetCode.AUTH_FAILED);
		}
		
		
		return new HookResult(HookReturnCode.DECLINED, SMTPRetCode.AUTH_FAILED);
	}
}
