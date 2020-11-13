package com.bilgidoku.rom.code;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.bilgidoku.rom.pg.dict.HttpCallMethod;
import com.bilgidoku.rom.pg.dict.HttpCallServiceDeclare;
import com.bilgidoku.rom.pg.dict.RetStream;
import com.bilgidoku.rom.pg.dict.hcp;
import com.bilgidoku.rom.pg.dict.types.SrvAtt;
import com.bilgidoku.rom.pg.dict.types.SrvInfo;
import com.bilgidoku.rom.pg.dict.types.SrvMethod;
import com.bilgidoku.rom.pg.dict.types.TypeFactory;
import com.bilgidoku.rom.pg.dict.types.TypeHolder;

public class CallResolver {

	public SrvInfo resolve(Class hcsc) {
		SrvInfo srv = new SrvInfo();
		Annotation[] as = hcsc.getAnnotations();
		for (Annotation an : as) {
			if (an instanceof HttpCallServiceDeclare) {
				HttpCallServiceDeclare csd = (HttpCallServiceDeclare) an;
				srv.uri = csd.uri();
				srv.name = csd.name();
				srv.custom=csd.custom();
				srv.norom=csd.norom();
				srv.net=csd.net();
				srv.cpu=csd.cpu();
				String[] x = csd.roles().split(",");
				if(x.length!=0)
					if(x.length!=1 || x[0].length()>0)
						srv.roles=x;
				srv.setPack(csd.paket());
				if(srv.custom){
					return srv;
				}
				break;
			}
		}
		if (srv.uri == null)
			throw new RuntimeException("No annotation for service class");

		Method[] mths = hcsc.getMethods();
		for (Method method : mths) {
			SrvMethod md = new SrvMethod(hcsc.getCanonicalName(),method.getName(), method, srv.getName());
			md.name = method.getName();
			md.net=srv.net;
			as = method.getAnnotations();
			boolean gotAnno = false;
			for (Annotation an : as) {
//				syso(an);
				if (an instanceof HttpCallMethod) {
					HttpCallMethod csd = (HttpCallMethod) an;
					md.http = csd.http();
					Set<String> t=new HashSet<String>();
					if(srv.roles!=null){
						for (String string : srv.roles) {
							t.add(string);
						}
					}
					if(md.roles!=null){
						String[] rs=csd.roles().split(",");
						if(rs.length!=0 && (rs.length!=1 || rs[0].length()>0)){
							for (String string : rs) {
								t.add(string);
							}
						}
					}
					if(t.size()>0){
						md.roles=t.toArray(new String[t.size()]);
					}
					if(!csd.contenttype().equals("text/html; charset=UTF-8")){
						md.contenttype=csd.contenttype();
					}
					md.cpu=csd.cpu();
					gotAnno = true;
					if(csd.file()!=0)
						md.setRetFile(true);
					
					String au=csd.audit();
					if(au.equals("")){
						md.setAudits(new String[0]);
					}else if(!au.equals("_")){
						String[] auditNames=au.split(",");
						String[] ret=new String[auditNames.length];
						for(int i=0; i<ret.length; i++){
							ret[i]=auditNames[i].trim();
						}
						md.setAudits(ret);
					}
					break;
				}
			}
			if (!gotAnno)
				continue;
			if (md.http == null)
				md.http = "get";

			srv.addMethod(md);

			Class<?> rt = method.getReturnType();
			if(rt.equals(RetStream.class)){
				md.enableReturnsStream();
			}else{
				rt.isPrimitive();
				md.setRetJavaType(method.getReturnType());
				md.retTypeHolder = TypeFactory.one.getTypeHolder(method.getReturnType(), method.getGenericReturnType());
			}

			Class<?>[] prmtypes = method.getParameterTypes();
			Annotation[][] ass = method.getParameterAnnotations();
			for (int i = 0; i < prmtypes.length; i++) {
				Class<?> pt = prmtypes[i];
				for (Annotation an : ass[i]) {
					if (an instanceof hcp) {
						hcp csd = (hcp) an;
//						syso(csd.n());
						if(csd.n().startsWith("f_")){
							md.hasFileArg=true;
						}
						
						SrvAtt att = csd.n().startsWith("p_")?new SrvAtt(csd.n(), 
								TypeFactory.one.getTypeHolder(pt, null),csd.nullable()):new SrvAtt(csd.n(), new TypeHolder(pt), csd.nullable());
						md.addArg(att);
						break;
					}
				}
			}
		}
		return srv;
	}
}
