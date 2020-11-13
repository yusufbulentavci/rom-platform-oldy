package com.bilgidoku.rom.pg.dict.mapper;
public class SqlJsonConverter {
	String string;
	int len = 0;
	int index = 0;
	
	public SqlJsonConverter(String s){
		this.string=s;
		len=string.length();
	}
	
	public final String expectString(char delim) {
		char d = string.charAt(index);
		StringBuilder sb = new StringBuilder();
		int last=index;
		boolean notNull=false;
		if (d == '"') {
			notNull=true;
			for (int i = ++index; i < len; i++) {
				char c = string.charAt(i);
				if (c == '"') {
					if (i + 1 < len && string.charAt(i + 1) == '"') {
						i = i + 1;
						sb.append("\"");
						last=i;
						continue;
					}
					last=i+1;
					break;
				}
				sb.append(c);
				last=i;
			}
			for(int i=last; i<len; i++){
				char c = string.charAt(i);
				if (c == delim) {
					last=i+1;
					break;
				}
			}
		} else {
			for (int i = index; i < len; i++) {
				char c = string.charAt(i);
				if (c == delim) {
					last=i+1;
					break;
				}
				last=i;
				if(c!=' '){
					sb.append(c);
					notNull=true;
				}
			}
		}
//		syso(sb.toString());
		index=last;
		if(!notNull){
			return null;
		}
		String k=sb.toString();
		return k;
	}

	

	public final void expectChar(char ec) throws SqlJsonException {
		char c = string.charAt(index);
		if (c != ec)
			throw new SqlJsonException(string, index, "Should be " + ec
					+ " but " + c);
		index++;
	}
}
