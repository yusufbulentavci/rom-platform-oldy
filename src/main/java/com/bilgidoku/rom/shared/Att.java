package com.bilgidoku.rom.shared;

import com.bilgidoku.rom.shared.json.JSONValue;

public interface Att {
	public static final int INT = 0;
	public static final int STRING = 1;
	public static final int DOUBLE = 2;
	public static final int BOOL = 3;
	public static final int OBJECT = 4;
	public static final int ARRAY = 5;
	public static final int NULL = 6;

	public static final int CONTEXT_NULL = 0;
	public static final int CONTEXT_ROUTINE = 1;
	public static final int CONTEXT_LIST = 2;
	public static final int CONTEXT_LINK = 3;
	public static final int CONTEXT_FILE = 4;
	public static final int CONTEXT_IMG = 5;
	public static final int CONTEXT_CONTAINER = 6;
	public static final int CONTEXT_RECT = 7;
	public static final int CONTEXT_TICK = 8;
	public static final int CONTEXT_FONT = 9;
	public static final int CONTEXT_WIDTH = 10;
	public static final int CONTEXT_TEXTAREA = 11;
	public static final int CONTEXT_YOUTUBE = 12;
	public static final int CONTEXT_PAGE = 13;
	public static final int CONTEXT_TRANS = 14;
	public static final int CONTEXT_INT = 15;
	public static final int CONTEXT_ARRAY = 16;
	public static final int CONTEXT_HEIGHT = 17;
	public static final int CONTEXT_SVG = 18;
	public static final int CONTEXT_HTMLTEXT = 19;
	public static final int CONTEXT_BOOLEAN = 20;
	public static final int CONTEXT_TAG = 21;

	String getNamed();

	boolean isMethod();

	int getType();

	String[] getEnumeration();

	String getSummary();

	boolean isDeclare();

	int getContext();

	Att cloneObj() throws RunException;

	String getDefvalue();

	boolean isReq();

	JSONValue store() throws RunException;

	boolean isFixed();
}
