package com.bilgidoku.rom.pg.sqlunit.model;

public class DataColumn{
	public Field field;
	public String named;
	public boolean autoInc=false;
	public boolean parametric=false;
	public boolean resourceColumn=false;
	public boolean onlyInsert=false;
	
	public String toString(){
		return named;
	}
}