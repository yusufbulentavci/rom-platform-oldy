factoryfielddecode(att)::=<<
%if(att.typeHolder.map)%
c.%att.name%=new MapCoder().decode(json.get("%att.name%"));
%elseif(att.typeHolder.array)%
c.%att.name%=new ArrayCoder<%att.typeHolder.simpleJavaTypeName%>(new %att.typeHolder.simpleJavaTypeName%Coder()).decode(json.get("%att.name%"));
%else%
c.%att.name%=new %att.typeHolder.simpleJavaTypeName%Coder().decode(json.get("%att.name%"));
%endif%

>>