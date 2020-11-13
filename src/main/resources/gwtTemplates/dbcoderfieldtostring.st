dbcoderfieldtostring(att)::=<<

%if(att.typeHolder.array)%
sb.append(new ArrayCoder<%att.typeHolder.simpleJavaTypeName%>(new %att.typeHolder.simpleJavaTypeName%Coder()).quoted(val.%att.name%));
%elseif(att.typeHolder.map)%
if(true)throw new RuntimeException("dont call");
%else%
sb.append(new %att.typeHolder.simpleJavaTypeName%Coder().quoted(val.%att.name%));
%endif%

>>