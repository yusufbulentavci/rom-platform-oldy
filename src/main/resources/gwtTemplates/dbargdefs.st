dbargdefs(att)::=
<<
	%if(att.typeHolder.map)%
	ERROR no map type for db: %att.name%
	%elseif(att.typeHolder.array)%
	TypeCoder %att.suffix%sqlType=new ArrayCoder(new %att.typeHolder.simpleJavaTypeName%Coder());
	%else%
	TypeCoder %att.suffix%sqlType=new %att.typeHolder.simpleJavaTypeName%Coder();
	%endif%
>>