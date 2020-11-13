package com.bilgidoku.rom.pg.sqlunit.model;




public class Command{
	private boolean isJava=false;
	private boolean isClp=false;
	private boolean isJs=false;
	private String command;
	private int lineNumber;


	public Command(String command, int line, boolean isJava) {
		this.setCommand(command.trim());
		this.lineNumber=line;
		this.isJava=true;
	}
	
	public Command(String command, int line) {
		this.setCommand(command.trim());
		this.lineNumber=line;
//		System.out.println(command);
	}

	public String getCommand() {
		return command;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append("Command->\n");
		sb.append("\nline:");
		sb.append(lineNumber);
		sb.append("\ncmd:");
		sb.append(getCommand());
		sb.append("<-Command");
		return sb.toString();
	}

	public String toScript() {
		if(isJava){
			return "Java call to->"+getCommand();
		}
		return getCommand();
	}
	
	public Command clone(){
		Command c=new Command(getCommand(),lineNumber);
		return c;
	}

	public void setCommand(String command) {
		this.command = command;
//		System.out.println(command);
	}

	public boolean isClp() {
		return isClp;
	}

	public boolean isSql() {
		return !isClp() && !isJs();
	}

	public boolean isJs() {
		return isJs;
	}

	public void setClp(boolean isClp) {
		this.isClp = isClp;
	}
	public void setJs(boolean isJs) {
		this.isJs = isJs;
	}
	
}
