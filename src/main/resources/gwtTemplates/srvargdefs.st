srvargdefs(att)::=
<<
%if(att.param)%	
	%if(att.typeHolder.map)%
	TypeCoder %att.suffix%sqlType=new MapCoder(new %att.typeHolder.simpleJavaTypeName%Coder());
	%elseif(att.typeHolder.array)%
	TypeCoder %att.suffix%sqlType=new ArrayCoder(new %att.typeHolder.simpleJavaTypeName%Coder());
	%else%
	TypeCoder %att.suffix%sqlType=new %att.typeHolder.simpleJavaTypeName%Coder();
	%endif%
%endif%
>>