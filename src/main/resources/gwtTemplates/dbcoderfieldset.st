dbcoderfieldset(att)::=<<

%if(att.typeHolder.array)%
new ArrayCoder<%att.typeHolder.simpleJavaTypeName%>(new %att.typeHolder.simpleJavaTypeName%Coder()).setDbValue(db3,val.%att.name%);
%elseif(att.typeHolder.map)%
new MapCoder().setDbValue(db3,val.%att.name%);
%else%
new %att.typeHolder.simpleJavaTypeName%Coder().setDbValue(db3,val.%att.name%);
%endif%

>>