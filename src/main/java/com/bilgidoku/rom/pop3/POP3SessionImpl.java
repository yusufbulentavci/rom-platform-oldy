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
package com.bilgidoku.rom.pop3;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bilgidoku.rom.mail.EpostaGorevlisi;
import com.bilgidoku.rom.mail.MailOnDb;
import com.bilgidoku.rom.protokol.protocols.ProtocolConfiguration;
import com.bilgidoku.rom.protokol.protocols.ProtocolSessionImpl;
import com.bilgidoku.rom.protokol.protocols.ProtocolTransport;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.shared.err.KnownError;

/**
 * {@link POP3Session} implementation which use Netty
 */
public class POP3SessionImpl extends ProtocolSessionImpl<POP3SessionActivity> implements POP3Session{

    private static final Response LINE_TOO_LONG = new POP3Response(POP3Response.ERR_RESPONSE, "Exceed maximal line length").immutable();
    private int handlerState;
	private final Set<Integer> deleted=new HashSet<Integer>();
	private List<MailOnDb> mails=null;
	private String apopTimestamp;

    POP3SessionImpl(ProtocolTransport transport, ProtocolConfiguration configData) {
        super(transport, configData);
    }

    /**
     * @see com.bilgidoku.rom.session.james.pop3server.POP3Session#getHandlerState()
     */
    public int getHandlerState() {
        return handlerState;
    }

    /**
     * @see com.bilgidoku.rom.session.james.pop3server.POP3Session#setHandlerState(int)
     */
    public void setHandlerState(int handlerState) {
        this.handlerState = handlerState;
    }

    @Override
    public void resetState() {
    	super.resetState();
        setHandlerState(AUTHENTICATION_READY);
    }

    @Override
    public Response newLineTooLongResponse() {
        return LINE_TOO_LONG;
    }

    @Override
    public Response newFatalErrorResponse() {
        return POP3Response.ERR;
    }

	@Override
	public void delete(int num) {
		this.deleted.add(num-1);
	}

	@Override
	public void stat() throws KnownError {
		this.deleted.clear();
		mails=EpostaGorevlisi.tek().listInbox(this.getIntraHostId(), this.romUser.getName());
	}
	
	@Override
	public void commit() throws KnownError {
		for (Integer num : deleted) {
			MailOnDb mail=mails.get(num);
			EpostaGorevlisi.tek().deleteMail(this.getIntraHostId(), mail.uri);
			
		}
	}


	@Override
	public int mailCount() {
		return mails.size()-deleted.size();
	}

	@Override
	public long mailSize() throws KnownError {
		long size=0;
		for(MailOnDb mod:mails){
			size+=mod.getSize();
		}
		return size;
	}

	@Override
	public boolean isDeleted(int num) {
		return deleted.contains(num-1);
	}

	@Override
	public InputStream getInputStream(int num) throws KnownError {
		MailOnDb mail=mails.get(num-1);
		if(mail==null)
			return null;
		return mail.getInputStream();
	}

	@Override
	public List<MailOnDb> mails() {
		return mails;
	}

	@Override
	public MailOnDb mail(int num) {
		if(mails.size()<num)
			return null;
		return mails.get(num-1);
	}

	@Override
	public String protocolName() {
		return "pop3";
	}


	@Override
	public String getApopTimestamp() {
		return apopTimestamp;
	}

	@Override
	public void setApopTimestamp(String string) {
		this.apopTimestamp=string;
	}

	@Override
	public boolean isSmtp() {
		return false;
	}

	@Override
	public String getCountry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public POP3SessionActivity createActivity() {
		return new POP3SessionActivity();
	}

	@Override
	public int getRole() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getRoleMask() {
		// TODO Auto-generated method stub
		return 0;
	}
}
