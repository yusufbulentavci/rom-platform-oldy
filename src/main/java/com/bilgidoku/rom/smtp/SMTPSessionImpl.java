package com.bilgidoku.rom.smtp;
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


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import com.bilgidoku.rom.ilk.json.JSONArray;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.protokol.protocols.ProtocolConfiguration;
import com.bilgidoku.rom.protokol.protocols.ProtocolSessionImpl;
import com.bilgidoku.rom.protokol.protocols.ProtocolTransport;
import com.bilgidoku.rom.protokol.protocols.Response;
import com.bilgidoku.rom.smtp.core.fastfail.ServerAttitude;

/**
 * {@link SMTPSession} implementation
 */
public class SMTPSessionImpl extends ProtocolSessionImpl<SMTPSessionActivity> implements SMTPSession {

	private static final Response LINE_LENGTH_EXCEEDED = new SMTPResponse(
			SMTPRetCode.SYNTAX_ERROR_COMMAND_UNRECOGNIZED, "Line length exceeded. See RFC 2821 #4.5.3.1.").immutable();
	private static final Response FATAL_ERROR = new SMTPResponse(SMTPRetCode.LOCAL_ERROR, "Unable to process request")
			.immutable();

	private boolean relayingAllowed;
	private SmtpMailAddress sender;
	private List<SmtpMailAddress> rcptList;
	private String curHeloName;
	private String heloMode;
	private SmtpMailAddress curRcpt;
	private MailEnvelopeImpl mailEnvelope;
	private Boolean headersComplete;
	private OutputStream mimeStreamSource;
	private File mimeFile;
	private Long mesgSize;
	private Boolean mesgFailed;
	private Long currentSize;
	private String headerSpfText;
	private String spfDetail;
	private Boolean spfBlockListed;
	private Boolean spfTempBlockListed;
	private String headerSuffixAdded;
	private String headerPrefixAdded;
	private String command;
	private ServerAttitude serverAttitude;
	private Integer unknownCommandCount;
	private Boolean badEhlo;
	private String dkimHeader;

	// private SMTPConfiguration smtpConfiguration;

	SMTPSessionImpl(ProtocolTransport transport, ProtocolConfiguration config) {
		super(transport, config);
		// relayingAllowed=true;
		relayingAllowed = getSmtpConfiguration().isRelayingAllowed(getRemoteAddress().getAddress().getHostAddress());
	}

	public boolean isRelayingAllowed() {
		return relayingAllowed;
	}

	@Override
	public void resetState() {
		sender = null;
		rcptList = null;
		curHeloName = null;
		curRcpt=null;
		mailEnvelope=null;
		headersComplete=null;
		mimeStreamSource=null;
		mimeFile=null;
		mesgSize=null;
		mesgFailed=null;
		currentSize=null;
		headerSpfText=null;
		spfDetail=null;
		spfBlockListed=null;
		spfTempBlockListed=null;
		headerSuffixAdded=null;
		headerPrefixAdded=null;
		command=null;
		serverAttitude=null;
		unknownCommandCount=null;
		badEhlo=null;
	}
	
	@Override
	public JSONObject toReport() {
		JSONObject ret = super.toReport();
		if(sender != null){
			ret.safePut("sndr", sender.toString());
		}
		if(rcptList != null){
			JSONArray ja=new JSONArray();
			for (SmtpMailAddress it : rcptList) {
				ja.put(it.toString());
			}
			ret.safePut("rcpt", ja);
		}
		if(curHeloName!=null){
			ret.safePut("hl", curHeloName);
		}
		if(curRcpt!=null){
			ret.safePut("crcpt", curRcpt);
		}
//		headersComplete=null;
		if(mimeFile!=null){
			ret.safePut("f", mimeFile.getPath());
		}
		if(mesgSize!=null){
			ret.safePut("sz", mesgSize);
		}
		if(mesgFailed!=null){
			ret.safePut("mfailed",mesgFailed);
		}
		if(currentSize!=null){
			ret.safePut("csz", currentSize);
		}
		if(dkimHeader!=null){
			ret.safePut("dkimh", dkimHeader);
		}
		if(headerSpfText!=null){
			ret.safePut("spfh", headerSpfText);
		}
		if(spfDetail!=null){
			ret.safePut("spfd", spfDetail);
		}
		if(spfBlockListed!=null){
			ret.safePut("spfbl",spfBlockListed);
		}
		if(spfTempBlockListed!=null){
			ret.safePut("spftbl",spfTempBlockListed);
		}
//		headerSuffixAdded=null;
//		headerPrefixAdded=null;
//		command=null;
//		if(blockListedDetailMailAttname!=null){
//			ret.safePut("bl",blockListedDetailMailAttname);
//		}
		if(serverAttitude!=null){
			ret.safePut("rbl",serverAttitude);
		}
		if(unknownCommandCount!=null){
			ret.safePut("unkc", unknownCommandCount);
		}
		if(badEhlo!=null){
			ret.safePut("badehlo",badEhlo);
		}
		badEhlo=null;
		return ret;
	}

	public int getRcptCount() {
		if (rcptList == null) {
			return 0;
		}

		return rcptList.size();
	}

	public boolean isAuthSupported() {
		return true;
		// return
		// getConfiguration().isAuthRequired(getRemoteAddress().getAddress().getHostAddress());
	}

	public void setRelayingAllowed(boolean relayingAllowed) {
		this.relayingAllowed = relayingAllowed;
	}

	public boolean verifyIdentity() {
		return ((SMTPConfiguration) config).verifyIdentity();
	}

	@Override
	public Response newLineTooLongResponse() {
		return LINE_LENGTH_EXCEEDED;
	}

	@Override
	public Response newFatalErrorResponse() {
		return FATAL_ERROR;
	}

	@Override
	public ProtocolConfiguration getConfiguration() {
		return config;
	}

	@Override
	public SmtpMailAddress getSender() {
		return this.sender;
	}

	@Override
	public List<SmtpMailAddress> getRcptList() {
		return this.rcptList;
	}

	@Override
	public String getCurHeloName() {
		return this.curHeloName;
	}

	@Override
	public void setHeloMode(String commandName) {
		this.heloMode = commandName;
	}

	@Override
	public void setHeloName(String parameters) {
		this.curHeloName = parameters;
	}

	@Override
	public String getHeloMode() {
		return this.heloMode;
	}

	@Override
	public void setSender(SmtpMailAddress senderAddress) {
		this.sender = senderAddress;
	}

	@Override
	public void setRcptList(List<SmtpMailAddress> rcptColl) {
		this.rcptList=rcptColl;
	}

	@Override
	public SmtpMailAddress getCurRcpt() {
		return this.curRcpt;
	}

	@Override
	public void setCurRcpt(SmtpMailAddress recipientAddress) {
		this.curRcpt=recipientAddress;
	}

	@Override
	public String protocolName() {
		return "smtp";
	}

	@Override
	public void setMailEnvelope(MailEnvelopeImpl env) {
		this.mailEnvelope=env;
	}

	@Override
	public MailEnvelopeImpl getMailEnvelope() {
		return mailEnvelope;
	}

	@Override
	public void setHeadersComplete(Boolean true1) {
		this.headersComplete=true1;
	}

	@Override
	public Boolean getHeadersComplete() {
		return headersComplete;
	}

	@Override
	public OutputStream getMimeStreamSource() {
		return this.mimeStreamSource;
	}

	@Override
	public File getMimeFile() {
		return this.mimeFile;
	}

	@Override
	public void setMimeStreamSource(FileOutputStream os) {
		this.mimeStreamSource=os;
	}

	@Override
	public void setMimeFile(File f) {
		this.mimeFile=f;
	}

	@Override
	public Object getMail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean getMesgFailed() {
		return this.mesgFailed;
	}

	@Override
	public Long getCurrentSize() {
		return this.currentSize;
	}

	@Override
	public void setMesgFailed(Boolean true1) {
		this.mesgFailed=true1;
	}

	@Override
	public void setMesgSize(long newSize) {
		this.mesgSize=newSize;
	}

	@Override
	public void setSpfHeader(String headerText) {
		this.headerSpfText=headerText;
	}

	@Override
	public void setSpfDetail(String explanation) {
		this.spfDetail=explanation;
	}

	@Override
	public void setSpfBlockListed(Boolean string) {
		this.spfBlockListed=string;
	}

	@Override
	public void setSpfTempBlockListed(Boolean string) {
		this.spfTempBlockListed=string;
	}

	@Override
	public Boolean getSpfBlockListed() {
		return spfBlockListed;
	}

	@Override
	public Boolean getSpfTempBlockListed() {
		return spfTempBlockListed;
	}

	@Override
	public void setHeaderSuffixAdded(String headersSuffixAdded) {
		this.headerSuffixAdded=headersSuffixAdded;
	}
	
	@Override
	public void setHeaderPrefixAdded(String headersPrefixAdded) {
		this.headerPrefixAdded=headersPrefixAdded;
	}


	@Override
	public String getHeaderSuffixAdded() {
		return headerSuffixAdded;
	}

	@Override
	public String getHeaderPrefixAdded() {
		return this.headerPrefixAdded;
	}

	@Override
	public void setCurCommand(String command) {
		this.command=command;
	}

	@Override
	public String getCurCommand() {
		return this.command;
	}

	@Override
	public ServerAttitude getRblBlockListedMailAttName() {
		return this.serverAttitude;
	}

	@Override
	public Integer getUnknownCommandCount() {
		return this.unknownCommandCount;
	}

	@Override
	public void setUnknownCommandCount(Integer count) {
		this.unknownCommandCount=count;
	}

	@Override
	public void setBadEhlo(Boolean true1) {
		this.badEhlo=true1;
	}

	@Override
	public Boolean getBadEhlo() {
		return this.badEhlo;
	}

	@Override
	public void setDkimHeader(String string) {
		this.dkimHeader=string;
	}

	@Override
	public Integer optHostId() {
		if(romUser==null)
			return null;
		return getIntraHostId();
	}

	@Override
	public String optUserName() {
		if(romUser==null)
			return null;
		return getUserName();
	}

	@Override
	public boolean isSmtp() {
		return true;
	}

	@Override
	public SMTPConfiguration getSmtpConfiguration() {
		return (SMTPConfiguration) getConfiguration();
	}

	@Override
	public void setServerAttitude(ServerAttitude sa) {
		this.serverAttitude=sa;
	}

	@Override
	public SMTPSessionActivity createActivity() {
		return new SMTPSessionActivity();
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
