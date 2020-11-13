dbcoderfieldencode(att)::=<<

%if(att.typeHolder.array)%
js.put("%att.name%",new ArrayCoder<%att.typeHolder.simpleJavaTypeName%>(new %att.typeHolder.simpleJavaTypeName%Coder()).encode(db3));
%elseif(att.typeHolder.map)%
js.put("%att.name%",new MapCoder().encode(db3));
%else%
js.put("%att.name%",new %att.typeHolder.simpleJavaTypeName%Coder().encode(db3));
%endif%

>>