package com.bilgidoku.rom.pg.dict.types;

import java.util.Hashtable;

public class SqlOidTypeFactory {
	
    public static final int UNSPECIFIED = 0;
    public static final int INT2 = 21;
    public static final int INT2_ARRAY = 1005;
    public static final int INT4 = 23;
    public static final int INT4_ARRAY = 1007;
    public static final int INT8 = 20;
    public static final int INT8_ARRAY = 1016;
    public static final int TEXT = 25;
    public static final int TEXT_ARRAY = 1009;
    public static final int NUMERIC = 1700;
    public static final int NUMERIC_ARRAY = 1231;
    public static final int FLOAT4 = 700;
    public static final int FLOAT4_ARRAY = 1021;
    public static final int FLOAT8 = 701;
    public static final int FLOAT8_ARRAY = 1022;
    public static final int BOOL = 16;
    public static final int BOOL_ARRAY = 1000;
    public static final int DATE = 1082;
    public static final int DATE_ARRAY = 1182;
    public static final int TIME = 1083;
    public static final int TIME_ARRAY = 1183;
    public static final int TIMETZ = 1266;
    public static final int TIMETZ_ARRAY = 1270;
    public static final int TIMESTAMP = 1114;
    public static final int TIMESTAMP_ARRAY = 1115;
    public static final int TIMESTAMPTZ = 1184;
    public static final int TIMESTAMPTZ_ARRAY = 1185;
    public static final int BYTEA = 17;
    public static final int BYTEA_ARRAY = 1001;
    public static final int VARCHAR = 1043;
    public static final int VARCHAR_ARRAY = 1015;
    public static final int OID = 26;
    public static final int OID_ARRAY = 1028;
    public static final int BPCHAR = 1042;
    public static final int BPCHAR_ARRAY = 1014;
    public static final int MONEY = 790;
    public static final int MONEY_ARRAY = 791;
    public static final int NAME = 19;
    public static final int NAME_ARRAY = 1003;
    public static final int BIT = 1560;
    public static final int BIT_ARRAY = 1561;
    public static final int VOID = 2278;
    public static final int INTERVAL = 1186;
    public static final int INTERVAL_ARRAY = 1187;
    public static final int CHAR = 18; // This is not char(N), this is "char" a single byte type.
    public static final int CHAR_ARRAY = 1002;
    public static final int VARBIT = 1562;
    public static final int VARBIT_ARRAY = 1563;
    public static final int UUID = 2950;
    public static final int UUID_ARRAY = 2951;
    public static final int XML = 142;
    public static final int XML_ARRAY = 143;

//    private static final Object types[][] = {
//    	{"text", new Integer(TEXT), new Integer(Types.VARCHAR), "java.lang.String", new Integer(TEXT_ARRAY)},
//    	{"varchar", new Integer(VARCHAR), new Integer(Types.VARCHAR), "java.lang.String", new Integer(VARCHAR_ARRAY)},
//        {"int2", new Integer(INT2), new Integer(Types.SMALLINT), "java.lang.Integer", new Integer(INT2_ARRAY)},
//        {"int4", new Integer(INT4), new Integer(Types.INTEGER), "java.lang.Integer", new Integer(INT4_ARRAY)},
//        {"bool", new Integer(BOOL), new Integer(Types.BIT), "java.lang.Boolean", new Integer(BOOL_ARRAY)},
//        {"int8", new Integer(INT8), new Integer(Types.BIGINT), "java.lang.Long", new Integer(INT8_ARRAY)},
//        {"date", new Integer(DATE), new Integer(Types.DATE), "java.sql.Date", new Integer(DATE_ARRAY)},
//        {"time", new Integer(TIME), new Integer(Types.TIME), "java.sql.Time", new Integer(TIME_ARRAY)},
//        {"timestamp", new Integer(TIMESTAMP), new Integer(Types.TIMESTAMP), "java.sql.Timestamp", new Integer(TIMESTAMP_ARRAY)},
//        {"money", new Integer(MONEY), new Integer(Types.DOUBLE), "java.lang.Double", new Integer(MONEY_ARRAY)},
//        {"name", new Integer(NAME), new Integer(Types.VARCHAR), "java.lang.String", new Integer(NAME_ARRAY)},
//        {"numeric", new Integer(NUMERIC), new Integer(Types.NUMERIC), "java.math.BigDecimal", new Integer(NUMERIC_ARRAY)},
//        {"float4", new Integer(FLOAT4), new Integer(Types.REAL), "java.lang.Float", new Integer(FLOAT4_ARRAY)},
//        {"float8", new Integer(FLOAT8), new Integer(Types.DOUBLE), "java.lang.Double", new Integer(FLOAT8_ARRAY)},
//        {"char", new Integer(CHAR), new Integer(Types.CHAR), "java.lang.String", new Integer(CHAR_ARRAY)},
//        {"bpchar", new Integer(BPCHAR), new Integer(Types.CHAR), "java.lang.String", new Integer(BPCHAR_ARRAY)},
//        {"bytea", new Integer(BYTEA), new Integer(Types.BINARY), "[B", new Integer(BYTEA_ARRAY)},
//        {"bit", new Integer(BIT), new Integer(Types.BIT), "java.lang.Boolean", new Integer(BIT_ARRAY)},
//        {"oid", new Integer(OID), new Integer(Types.BIGINT), "java.lang.Long", new Integer(OID_ARRAY)},
//        {"timetz", new Integer(TIMETZ), new Integer(Types.TIME), "java.sql.Time", new Integer(TIMETZ_ARRAY)},
//        {"timestamptz", new Integer(TIMESTAMPTZ), new Integer(Types.TIMESTAMP), "java.sql.Timestamp", new Integer(TIMESTAMPTZ_ARRAY)}
//    };
//	
	
	private static SqlOidTypeFactory one;
	
	
	private final Hashtable<Integer,TypeAdapter> typeTable=new Hashtable<Integer,TypeAdapter>();
	
	private SqlOidTypeFactory(){
		typeTable.put(BOOL,new BoolType());
		typeTable.put(INT8,new Int8Type());
		typeTable.put(INT4,new Int4Type());
		typeTable.put(INT2,new Int2Type());
		typeTable.put(TIME,new TimeType());
		typeTable.put(DATE,new DateType());
		typeTable.put(TIMESTAMP,new TimestampType());
		typeTable.put(FLOAT4,new Float4Type());
		typeTable.put(FLOAT8,new Float8Type());
		typeTable.put(TEXT,new TextType());
		typeTable.put(VARCHAR,new TextType());
		typeTable.put(CHAR,new TextType());
	}
	
	public static TypeAdapter getType(String name){
		return getType(Integer.parseInt(name));		
	}
	
	public static TypeAdapter getType(Integer oid){
		if(one==null){
			one=new SqlOidTypeFactory();
		}
		TypeAdapter st=one.typeTable.get(oid);
		if(st==null){
			st=one.typeTable.get(25);
		}
		return st;
	}
	
	public static TypeAdapter getTypeNullable(Integer oid){
		if(one==null){
			one=new SqlOidTypeFactory();
		}
		TypeAdapter st=one.typeTable.get(oid);
		
		return st;		
	}
	
}
