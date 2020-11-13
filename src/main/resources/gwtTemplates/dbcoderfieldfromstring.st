dbcoderfieldfromstring(att)::=<<

%if(att.typeHolder.array)%
val.%att.name%=new ArrayCoder<%att.typeHolder.simpleJavaTypeName%>(new %att.typeHolder.simpleJavaTypeName%Coder()).fromString(ms[i++]);
%elseif(att.typeHolder.map)%
if(true)throw new RuntimeException("dont call");
%else%
val.%att.name%=new %att.typeHolder.simpleJavaTypeName%Coder().fromString(ms[i++]);
%endif%

>>