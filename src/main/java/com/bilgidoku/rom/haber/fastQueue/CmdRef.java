package com.bilgidoku.rom.haber.fastQueue;

public class CmdRef {

	public final int hostId;
	public final String file;

	public CmdRef(int hostId, String file) {
		this.hostId = hostId;
		this.file = file;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + hostId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CmdRef other = (CmdRef) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (hostId != other.hostId)
			return false;
		return true;
	}
	
	public String refStr(){
		return "HostId:"+hostId+" File:"+file;
	}
	
	public String ref(){
		return hostId+"#"+file;
	}
	
	

}