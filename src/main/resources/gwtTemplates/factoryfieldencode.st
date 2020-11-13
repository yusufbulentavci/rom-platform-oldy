factoryfieldencode(att)::=<<

%if(att.typeHolder.map)%
js.put("%att.name%",new MapCoder().encode(obj.%att.name%));
%elseif(att.typeHolder.array)%
js.put("%att.name%",new ArrayCoder<%att.typeHolder.simpleJavaTypeName%>(new %att.typeHolder.simpleJavaTypeName%Coder()).encode(obj.%att.name%));
%else%
js.put("%att.name%",new %att.typeHolder.simpleJavaTypeName%Coder().encode(obj.%att.name%));
%endif%

>>