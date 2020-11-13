methodparamencode(arg)::=<<
	%if(arg.typeHolder.map)%
	methodResp.addParam("%arg.name%",new MapCoder(new %arg.typeHolder.simpleJavaTypeName%Coder()).encode(%arg.name%));
	%elseif(arg.typeHolder.array)%
	methodResp.addParam("%arg.name%",new ArrayCoder(new %arg.typeHolder.simpleJavaTypeName%Coder()).encode(%arg.name%));
	%else%
	methodResp.addParam("%arg.name%",new %arg.typeHolder.simpleJavaTypeName%Coder().encode(%arg.name%));
	%endif%
>>