dbargmapper(att)::=
<<
	%if(att.param)%
	val=request.getParam("%att.suffix%", null, null, false);
	%att.suffix%sqlType.setDbValue(db3, %att.suffix%sqlType.decode(val));
	
	%elseif(att.cookie)%
	db3.setString(cookieService.getCookieByName(request.getCookie(), "%att.suffix%"));
	
	%elseif(att.host)%
	
	db3.setInt(request.getHostId());
	
	
	%elseif(att.self)%
	db3.setString(request.getUri());

	%elseif(att.cont)%
	db3.setBoolean(request.isContainer());
		
	%elseif(att.contact)%
	{
		String cid=request.getSession().getCid();
		if(cid==null)
			db3.setNull(java.sql.Types.VARCHAR);
		else
			db3.setString(cid);
	}
	%elseif(att.contactno)%
	{
		String cid=request.getSession().getCid();
		if(cid==null)
			db3.setNull(java.sql.Types.VARCHAR);
		else
			db3.setString(cid.substring(6));
	}
	
	%elseif(att.mask)%
	db3.setString(request.getMask());
	
	%elseif(att.lang)%
	String lang=request.getParam("lng", null, null, false);
	if(lang==null){
			lang=request.getReqLang();
	}
	db3.setString(lang);
	
	%elseif(att.roles)%
	int roles=request.getSession().getRole();
	db3.setInt(roles);

	%elseif(att.ip)%
	String x = request.getSession().getIpAddress();
	db3.setString(x);
	
	%elseif(att.sid)%
	String x = request.getSession().getSid();
	db3.setString(x);
	
	%elseif(att.sint)%
	String x = request.getSession().getSint();
	db3.setString(x);
	
	%elseif(att.rid)%
	db3.setString(request.getRid());
	
	%elseif(att.file)%
	{
		String fi=getFileItem(request, "%att.suffix%");
		if(fi==null)
			db3.setNull(java.sql.Types.VARCHAR);
		else
			db3.setString(fi);
	}
	
	%else%
	ERROR no map for att.name
	%endif%
>>