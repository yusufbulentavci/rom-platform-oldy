dbcoderfieldget(att)::=<<

%if(att.typeHolder.array)%
val.%att.name%=new ArrayCoder<%att.typeHolder.simpleJavaTypeName%>(new %att.typeHolder.simpleJavaTypeName%Coder()).getDbValue(db3);
%elseif(att.typeHolder.map)%
val.%att.name%=new MapCoder().getDbValue(db3);
%else%
val.%att.name%=new %att.typeHolder.simpleJavaTypeName%Coder().getDbValue(db3);
%endif%

>>