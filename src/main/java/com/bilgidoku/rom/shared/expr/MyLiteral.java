package com.bilgidoku.rom.shared.expr;

import com.bilgidoku.rom.shared.Portable;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONArray;
import com.bilgidoku.rom.shared.json.JSONNumber;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JSONParser;
import com.bilgidoku.rom.shared.json.JSONString;
import com.bilgidoku.rom.shared.json.JSONValue;
import com.bilgidoku.rom.shared.util.Casing;
import com.bilgidoku.rom.shared.util.RomCurrency;

public class MyLiteral implements Comparable {

	public static final int INT = 0;//'i';
	public static final int STRING = 1;//'s';
	public static final int FLOAT = 2;//'f';
	public static final int BOOL = 3;//'b';
	public static final int OBJECT = 4;//'o';
	public static final int ARRAY = 5;//'a';
//	public static final int NULL = 6;//'n';

	public final int type;
	private Object num;

	// jo.put("t", type);
	public MyLiteral(int t, JSONValue o) throws RunException {
		type = t;
		if (o == null || o.ntv==null || o.isNull()!=null){
			return;
		}
		switch (type) {
		case INT:
			num = (int) o.isNumber().doubleValue();
			break;
		case STRING:
			num = o.isString().stringValue();
			break;
		case BOOL:
			num = o.isBoolean().booleanValue();
			break;
		case FLOAT:
			num = o.isNumber().doubleValue();
			break;
		case OBJECT:
			num = o.isObject();
			break;
		case ARRAY:
			num = o.isArray();
			break;
		default:
			throw new RunException("Unknown MyLiteral type:" + type);
		}
	}

	public MyLiteral(Integer i) {
		this.num = i;
//		if (i == null) {
//			this.type = NULL;
//			return;
//		}
		this.type = INT;
	}

	public MyLiteral(Double f) {
		this.num = f;
		this.type = FLOAT;
	}

	public MyLiteral(String o) {
		this.num = o;
//		if (o == null) {
//			this.type = NULL;
//			return;
//		}
		this.type = STRING;
	}

	public MyLiteral(Boolean o) {
		this.num = o;
		this.type = BOOL;
	}

	public MyLiteral() {
		this.num = null;
		this.type = STRING;
	}

	public MyLiteral(char type) {
		this.num = null;
		this.type = type;
	}

	public MyLiteral(JSONValue jo) {
		if (jo.isString() != null) {
			this.num = jo.isString().stringValue();
			this.type = STRING;
		} else if (jo.isArray() != null) {
			this.num = jo;
			this.type = ARRAY;
		} else if (jo.isObject() != null) {
			this.num = jo;
			this.type = OBJECT;
		} else if (jo.isBoolean() != null) {
			this.type = BOOL;
			this.num = jo.isBoolean().booleanValue();
		} else {
			this.num = jo.isNumber().doubleValue();
			this.type = FLOAT;
		}
	}

	public MyLiteral(JSONObject jo) {
		this.num = jo;
		this.type = OBJECT;
	}

	public MyLiteral(JSONArray jo) {
		this.num = jo;
		this.type = ARRAY;
	}

	// public JSONValue toJson() {
	// if (num == null)
	// return JSONNull.getInstance();
	// switch (type) {
	// case NUMBER:
	// if (isInteger)
	// return new JSONNumber((Integer) num);
	// return new JSONNumber((Double) num);
	// case STRING:
	// return new JSONString((String) num);
	// case BOOL:
	// return JSONBoolean.getInstance((Boolean) num);
	// case NULL:
	// return JSONNull.getInstance();
	// default:
	// throw new RunException("Unknown MyLiteral type:" + type);
	// }
	// }

	@Override
	public String toString() {
		if (num == null)
			return "";
		return num.toString();
	}

	public MyLiteral add(MyLiteral b) throws RunException {
		checkNumber();
		b.checkNumber();
		if (b.isInteger() && this.isInteger()) {
			MyLiteral a = new MyLiteral(this.getInteger() + b.getInteger());
			return a;
		}

		Double fb = b.isInteger() ? b.getInteger().doubleValue() : b.getDouble();
		Double ft = this.isInteger() ? this.getInteger().doubleValue() : this.getDouble();

		return new MyLiteral(fb + ft);
	}

	private void checkNumber() throws RunException {
		if (type != INT && type != FLOAT)
			throw new RunException("Type is not number;" + toDesc());

	}

	public MyLiteral sub(MyLiteral b) throws RunException {
		checkNumber();
		b.checkNumber();
		if (b.isInteger() && this.isInteger()) {
			MyLiteral a = new MyLiteral(this.getInteger() - b.getInteger());
			return a;
		}

		Double fb = b.isInteger() ? b.getInteger().doubleValue() : b.getDouble();
		Double ft = this.isInteger() ? this.getInteger().doubleValue() : this.getDouble();

		return new MyLiteral(ft - fb);
	}

	public MyLiteral mul(MyLiteral b) throws RunException {
		checkNumber();
		b.checkNumber();
		if (b.isInteger() && this.isInteger()) {
			MyLiteral a = new MyLiteral(this.getInteger() * b.getInteger());
			return a;
		}

		Double fb = b.isInteger() ? b.getInteger().doubleValue() : b.getDouble();
		Double ft = this.isInteger() ? this.getInteger().doubleValue() : this.getDouble();

		return new MyLiteral(fb * ft);
	}

	public MyLiteral div(MyLiteral b) throws RunException {
		checkNumber();
		b.checkNumber();
		if (b.isInteger() && this.isInteger()) {
			MyLiteral a = new MyLiteral(this.getInteger() / b.getInteger());
			return a;
		}

		Double fb = b.isInteger() ? b.getInteger().doubleValue() : b.getDouble();
		Double ft = this.isInteger() ? this.getInteger().doubleValue() : this.getDouble();

		return new MyLiteral(fb / ft);
	}

	public MyLiteral mod(MyLiteral b) throws RunException {
//		if (type == NULL)
//			return new MyLiteral("");

		if (type == STRING) {

			if (num == null)
				return new MyLiteral("");

			if (!b.isInteger())
				return new MyLiteral("Unexpected string conversion:" + num + "%" + b);

			String f = getString();
			int cmd = b.getInt();

			if (cmd == 1) {
				if (f.length() == 0) {
					return new MyLiteral("Conversion to ts failed, source string length 0");
				}
				int ind = f.indexOf('.');
				if (ind > 0)
					return new MyLiteral(f.substring(0, ind));
			}

			return new MyLiteral("Unexpected string conversion" + num + "%" + b);
		}

		checkNumber();
		b.checkNumber();
		if (b.isInteger() && this.isInteger()) {
			if(b.getInt()==0)
				return new MyLiteral(Integer.MAX_VALUE);
			MyLiteral a = new MyLiteral(this.getInteger() % b.getInteger());
			return a;
		}

		Double fb = b.isInteger() ? b.getInteger().doubleValue() : b.getDouble();
		Double ft = this.isInteger() ? this.getInteger().doubleValue() : this.getDouble();

		return new MyLiteral(fb % ft);
	}

	Integer getInteger() {
		return (Integer) num;
	}

	public Double getDouble() {
		return (Double) num;
	}

	@Override
	public int compareTo(Object arg) {
		MyLiteral b = (MyLiteral) arg;

		if (b.isInteger() && this.isInteger()) {
			return this.getInteger().compareTo(b.getInteger());
		}
		Double fb = b.isInteger() ? b.getInteger().doubleValue() : b.getDouble();
		Double ft = this.isInteger() ? this.getInteger().doubleValue() : this.getDouble();

		return ft.compareTo(fb);
	}

	MyLiteral negate() throws RunException {
		checkNumber();
		if (!this.isNumber()) {
			throw new RunException("Negating not number component");
		}
		if (this.isInteger())
			return new MyLiteral(-this.getInteger());

		return new MyLiteral(-this.getDouble());
	}

	MyLiteral not() throws RunException {
		if (!this.isBoolean()) {
			throw new RunException("Notting not boolean component");
		}

		return new MyLiteral(!this.getBoolean());
	}
	Boolean getBoolean() throws RunException {
		if (!this.isBoolean()) {
			throw new RunException("Want to get boolean but it is not");
		}
		return (Boolean) num;
	}

	private boolean isBoolean() {
		return type == BOOL;
	}

	private boolean isNumber() {
		return type == INT || type == FLOAT;
	}

	@Override
	public boolean equals(Object obj) {
		MyLiteral o = (MyLiteral) obj;
		return (o.type == type && ((o.num == null && num == null) || (o.num != null && num != null && o.num.equals(num))));
	}

	public boolean isInteger() {
		return type == INT;
	}

	public boolean getNull() {
		return num == null;
	}

	public Integer getInt() throws RunException {
		if (type == INT) {
			return (Integer) num;
		}
		if (type == FLOAT) {
			Double n = (Double) num;
			return n.intValue();
		}
		if (num == null)
			return 0;

		if (type == STRING) {
			try {
				int n = Integer.parseInt((String) num);
				return n;
			} catch (NumberFormatException e) {

			}
		}

		throw new RunException("Casting to int failed; literal is not a number. Literal is " + toDesc());
	}

	public String toDesc() {
		return "Type:" + type + " val:" + num;
	}

	public Boolean getBool() throws RunException {
		if (type == BOOL) {
			return (Boolean) num;
		}
		if (num == null)
			return false;
		throw new RunException("Casting to bool failed; literal is not bool. Literal is " + toDesc());
	}

	public String getString() throws RunException {
		if (type == STRING) {
			return (String) num;
		}
		if (num == null)
			return "";
		throw new RunException("Casting to string failed; literal is not string. Literal is " + toDesc());
	}

	public boolean isObj() {
		return type == OBJECT;
	}

	public boolean isArray() {
		return type == ARRAY;
	}

	public Object getValue() {
		return num;
	}

	public JSONArray getArray() throws RunException {
		
		if (num == null) {
			return null;
		}
		
		if (type == ARRAY) {
			return (JSONArray) num;
		}
		
		throw new RunException("Casting to jsonarray failed; literal is not jsonarray. Literal is " + toDesc());
	}

	public JSONObject getObj() throws RunException {
		if (type == OBJECT) {
			return (JSONObject) num;
		}
		throw new RunException("Casting to jsonobject failed; literal is not jsonobject. Literal is " + toDesc());
	}

	public boolean isBool() {
		return type == BOOL;
	}

	public MyLiteral cast(int to) throws RunException {
//		if (to == NULL)
//			return new MyLiteral();
		if (this.type == to)
			return this;
		if (to == STRING) {
			if (num == null)
				return new MyLiteral("");
			return new MyLiteral(num.toString());
		}
		switch (this.type) {
		case STRING:
			switch (to) {
			case INT:
				if (num == null || num.toString().length()==0)
					return new MyLiteral(0);
				return new MyLiteral(Integer.parseInt((String) num));
			case FLOAT:
				if (num == null)
					return new MyLiteral(0.0);
				return new MyLiteral(Double.parseDouble((String) num));
			case BOOL:
				if (num == null)
					return new MyLiteral(false);
				return new MyLiteral(Boolean.parseBoolean((String) num));
			case OBJECT:
				if (num == null)
					return new MyLiteral(new JSONObject());
				String str = (String) num;
				str = str.trim();
				if (str.length() == 0) {
					return new MyLiteral();
				}
				JSONValue val = JSONParser.parseStrict((String) num);
				if (val.isObject() != null)
					return new MyLiteral(val.isObject());
				break;
			case ARRAY:
				if (num == null  || num.toString().length()==0)
					return new MyLiteral(new JSONArray());
				JSONValue vala = JSONParser.parseStrict((String) num);
				if (vala.isArray() != null)
					return new MyLiteral(vala.isArray());
				break;
			}

			break;
		case INT:
			if (to == FLOAT) {
				if (num == null)
					return new MyLiteral(0.0);
				return new MyLiteral(new Double((Integer) num));
			}
			break;
		case FLOAT:
			if (to == INT) {
				if (num == null)
					return new MyLiteral(0);
				return new MyLiteral(((Double) num).intValue());
			}
			break;
		}
		throw new RunException("Cast from " + type + " to " + to + " is not possible or unsuccessful:" + num);

	}

	public JSONArray store() throws RunException {
		JSONArray jo = new JSONArray();
		jo.set(0, new JSONNumber(type));
		if (num == null)
			return jo;
		switch (type) {
		case INT:
			jo.set(1, new JSONNumber((Integer) num));
			break;
		case STRING:
			jo.set(1, new JSONString((String) num));
			break;
		case BOOL:
			jo.set(1, Portable.one.jsonBooleanGetInstance((Boolean) num));
			break;
		case FLOAT:
			jo.set(1, new JSONNumber((Double) num));
			break;
		case OBJECT:
			jo.set(1, (JSONObject) num);
			break;
		case ARRAY:
			jo.set(1, (JSONArray) num);
			break;
		default:
			throw new RunException("Unknown MyLiteral type:" + type);
		}
		return jo;
	}

	public MyLiteral len() throws RunException {
		if (num == null) {
			return new MyLiteral(0);
		}

		if (type == ARRAY) {
			return new MyLiteral(getArray().size());
		}
		if (type == OBJECT) {
			return new MyLiteral(getObj().size());
		}

		if (type == STRING) {
			return new MyLiteral(getString().length());
		}

		throw new RunException("len is not available for this type; Literal is " + toDesc());
	}
	
	public MyLiteral urlEncode() throws RunException {
		if (num == null) {
			return new MyLiteral(0);
		}

		if (type == ARRAY||type == OBJECT) {
			throw new RunException("UrlEncode for this type not implemented yet " + toDesc());
		}

		if (type == STRING) {
			return new MyLiteral(Portable.one.urlEncode(getString()));
		}
		
		return new MyLiteral(Portable.one.urlEncode(cast(STRING).getString()));
	}


	public MyLiteral isEmpty() throws RunException {
		return new MyLiteral(this.num == null || (isString() && getString().length() == 0));
	}
	
	public boolean empty() throws RunException {
		return (this.num == null);
	}

	public boolean isString() {
		return type == STRING;
	}

	public MyLiteral upperCase(MyLiteral r) throws RunException {
		if (num == null) {
			return new MyLiteral(0);
		}

		return new MyLiteral(Casing.upperCase(cast(STRING).getString(), r.getString()));
	}

	public MyLiteral lowerCase(MyLiteral r) throws RunException {
		if (num == null) {
			return new MyLiteral(0);
		}

		return new MyLiteral(Casing.lowerCase(cast(STRING).getString(), r.getString()));
	}

	public MyLiteral capitalize(MyLiteral r) throws RunException {
		if (num == null) {
			return new MyLiteral(0);
		}

		return new MyLiteral(Casing.capitalize(cast(STRING).getString(), r.getString()));
	}

	public MyLiteral money() throws RunException {
		if(num==null)
			return new MyLiteral("");
		MyLiteral k = cast(ARRAY);
		JSONArray ar = k.getArray();
		if(ar==null || ar.isNull()!=null)
			return new MyLiteral("");
		int am=ar.get(0).isNumber().intValue();
		String curcode=ar.get(1).isString().stringValue();
		
		return new MyLiteral(RomCurrency.toText(am,curcode));
	}

}
