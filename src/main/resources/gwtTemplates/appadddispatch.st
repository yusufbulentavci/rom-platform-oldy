appadddispatch(method)::=<<
//appadddispatch
%if(method.service)%
add(true,false,"%method.uri%","%method.name%",0,new String[]{%method.roleStr%},
	HttpMethod.%if(method.formPosting)%POST%elseif(method.formDeleting)%DELETE%else%GET%endif%,%method.breed%,"%method.schemaName%","%method.tableName%",null,
	UriHierarychy.%if(method.hsc)%HSC%elseif(method.one)%ONE%else%SINGLE%endif%,Net.%method.net%,new %method.capTableName%_%method.nameJavaForm%(),%method.cpu%,%if(method.returnMime)%"%method.returnMime%"%else%null%endif%); 
%else%
add(false,false,"%if(method.breed)%/_/c%else%%method.prefix%%endif%","%if(!method.breed)%%method.named%%endif%",RoleMask.%method.accesslevel%,new String[]{%method.roleStr%},
	HttpMethod.%if(method.formPosting)%POST%elseif(method.formDeleting)%DELETE%else%GET%endif%,%method.breed%,"%method.schemaName%","%method.tableName%",%if(method.inherit)%"%method.inherit%"%else%null%endif%,
	UriHierarychy.%if(method.hsc)%HSC%elseif(method.one)%ONE%else%SINGLE%endif%,Net.%method.net%,new %method.capTableName%_%method.nameJavaForm%(),%method.cpu%,%if(method.returnMime)%"%method.returnMime%"%else%null%endif%); 
%endif%
>>