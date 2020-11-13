srvmethodrender(method)::=
<<
//srvmethodrender
public static void %method.name%(%method.params:methodargrender();separator=","%  %if(method.hasParams)%,%endif% 
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
		
		%if(method.hasParams)%
			%method.params:methodparamencode();wrap%
		%endif%
		
		%if(method.formPosting)%
		methodResp.postNow("%method.servuri%/%method.name%.rom");
		%else%
		methodResp.getNow("%method.servuri%/%method.name%.rom");
		%endif%
}
>>