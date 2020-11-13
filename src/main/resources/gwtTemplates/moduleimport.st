moduleimport(arg,isclient)::=
<<
import com.bilgidoku.rom.gwt.araci.%if(isclient)%client%else%server%endif%.%arg%.*;

>>