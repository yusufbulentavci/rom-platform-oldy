srvargmapper(att)::=
<<
	%if(att.param)%
	val=request.getParam("%att.suffix%", null, null, false);
	args[ind++]=%att.suffix%sqlType.decode(val);
	
	%elseif(att.cookie)%
	args[ind++]=cookieService.getCookieByName(request.getCookie(), "%att.suffix%");
	
	%elseif(att.self)%
	args[ind++]=request.getUri();
	
	%elseif(att.host)%
	args[ind++]=request.getHostId();
	
	%elseif(att.mask)%
	args[ind++]=request.getMask();
	
	%elseif(att.contact)%
	args[ind++]=request.getSession().getCid();
	
	%elseif(att.contactno)%
	args[ind++]=request.getSession().getCid()==null?null:request.getSession().getCid().substring(6);
	
	%elseif(att.lang)%
	String lang=request.getParam("lng", null, null, false);
	if(lang==null){
			lang=request.getReqLang();
	}
	args[ind++]=lang;
	
	%elseif(att.roles)%
	args[ind++]=request.getSession().getRole();
	
	%elseif(att.ip)%
	args[ind++]=request.getSession().getIpAddress();

	%elseif(att.session)%
	args[ind++]=request.getSession();

	%elseif(att.domain)%
	args[ind++]=request.getDomain();
		
	%elseif(att.sid)%
	args[ind++]=request.getSession().getSid();
	
	%elseif(att.sint)%
	args[ind++]=request.getSession().getSint();
	
	
	%elseif(att.rid)%
	args[ind++]=request.getRid();
	
	%elseif(att.user)%
	args[ind++]=request.getSession().getUser();

	%elseif(att.file)%
	io.netty.handler.codec.http.multipart.FileUpload item = request.getFileParam("%att.suffix%", false);
	try{
	args[ind++]=(item==null)?null:item.getFile();
	}catch(java.io.IOException e){
		throw new KnownError(e);
	}
	
	%else%
	ERROR no map for att.name
	%endif%
>>