package net.sf.clipsrules.jni;

public class AkilClipsRouter implements Router {

	@Override
	public int getPriority() {
		return 10;
	}

	@Override
	public String getName() {
		return "stdout";
	}

	@Override
	public boolean query(String logicalName) {
		return logicalName.equals("stdout");
	}

	@Override
	public void write(String logicalName, String writeString) {
		System.out.println("LocgicalName:" + logicalName + " " + writeString);
	}

	@Override
	public int read(String logicalName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int unread(String logicalName, int theChar) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void exit(boolean failure) {

	}

}
