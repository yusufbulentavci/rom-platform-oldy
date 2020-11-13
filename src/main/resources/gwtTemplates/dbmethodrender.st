dbmethodrender(method)::=
<<
// dbmethodrender
public static void %method.nameJavaForm%(%method.args:methodargrender();separator=","%  %if(method.hasArgs)%,%endif% 
%if(method.retType.array)%
ArrayResponse<%method.retType.simpleJavaTypeName%> methodResp
%elseif(method.retType.map)%
MapResponse<%method.retType.simpleJavaTypeName%> methodResp
%else%
%method.retType.simpleJavaTypeName%Response methodResp
%endif%
) {
	
		methodResp.setCoder(
		%if(method.retType.array)%
		new ArrayCoder<%method.retType.simpleJavaTypeName%>(new %method.retType.simpleJavaTypeName%Coder())
		%elseif(method.retType.map)%
		new MapCoder<%method.retType.simpleJavaTypeName%>(new %method.retType.simpleJavaTypeName%Coder())
		%else%
		new %method.retType.simpleJavaTypeName%Coder()
		%endif%
		);
		
		%if(method.retset)%
		methodResp.enableArray();
		%endif%
		
		%if(method.breed)%
			final String self="/_/c";
			final String schema="%method.schemaName%";
			final String table="%method.tableName%";
			methodResp.addParam("table",new JSONString("%method.tableName%"));
		%endif%
		
		%if(method.hasParams)%
			%method.params:methodparamencode();wrap%
		%endif%
		
		%if(method.formPosting)%
		methodResp.postNow(self+"%method.uriPostfix%");
		%elseif(method.formDeleting)%
		methodResp.deleteNow(self);
		%else%
		methodResp.getNow(self+"%method.uriPostfix%");
		%endif%
	}
		
>>