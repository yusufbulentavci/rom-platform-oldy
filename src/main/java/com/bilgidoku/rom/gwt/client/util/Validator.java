package com.bilgidoku.rom.gwt.client.util;

import java.util.LinkedList;
import java.util.List;

import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.shared.json.JsonUtil;
import com.google.gwt.core.client.GWT;

public class Validator {
	private final static ApplicationConstants cons = GWT
			.create(ApplicationConstants.class);

	private final Boolean canBeEmpty;
	private final Integer minSize;
	private final Integer maxSize;
	private final String code;//no,latinalphanumeric,latinalpha,alphanumeric,alpha,integer,decimal
	private final Boolean acceptWS;
	private final Boolean trimWS;
	private final String extraChars;
	private final String format;//lowercase,uppercase,titlecase,sentencecase,no
	private final boolean alphaLatin;
	private final boolean alpha;
	private final boolean digits;
	private final boolean noAlpha;

	interface CheckChar {
		boolean isValid(char c);

		String validCharacterText();
	}

	public Validator(JSONObject jo) throws RunException {
		code=JsonUtil.mstr(jo,"code");
		Boolean ne=JsonUtil.mbool(jo, "notempty");
		if(ne==null)
			canBeEmpty=null;
		else
			canBeEmpty=(!ne);
		acceptWS=JsonUtil.mbool(jo, "acceptws");
		trimWS=JsonUtil.mbool(jo, "trimws");
		extraChars=JsonUtil.mstr(jo, "extrachars");
		format=JsonUtil.mstr(jo, "format");
		minSize=JsonUtil.mint(jo, "minsize");
		maxSize=JsonUtil.mint(jo, "maxsize");
		if (code != null) {
			if (code.equals("latinalphanumeric")) {
				this.alphaLatin = true;
				this.alpha = false;
				this.digits = true;
				this.noAlpha = false;
			} else if (code.equals("alphanumeric")) {
				this.alphaLatin = false;
				this.alpha = true;
				this.digits = true;
				this.noAlpha = false;
			} else if (code.equals("latinalpha")) {
				this.alphaLatin = true;
				this.alpha = false;
				this.digits = false;
				this.noAlpha = false;
			} else if (code.equals("alpha")) {
				this.alphaLatin = false;
				this.alpha = true;
				this.digits = false;
				this.noAlpha = false;
			} else {
				this.noAlpha = true;
				this.alphaLatin = false;
				this.alpha = true;
				this.digits = false;
			}
		} else {
			this.noAlpha = true;
			this.alphaLatin = false;
			this.alpha = true;
			this.digits = false;
		}
	}

	public String[] validate(String fieldName, String value) {
		if (value == null) {
			if (this.canBeEmpty != null && this.canBeEmpty)
				return new String[] { null, null };
			return new String[] { cons.validationFailedIsEmpty(fieldName), null };
		}

		if (trimWS!=null && trimWS) {
			value = value.trim();
		}
		
		if(value.length()>0 && format!=null && !format.equals("no")){
			if(format.equals("lowercase")){
				value=value.toLowerCase();
			}else if(format.equals("uppercase")){
				value=value.toUpperCase();
			}else if(format.equals("titlecase")){
				StringBuilder sb=new StringBuilder();
				boolean now=true;
				char[] chars=value.toCharArray();
				for (char c : chars) {
					if(Character.isSpace(c)){
						now=true;
						sb.append(c);
					}else{
						if(now)
							sb.append(Character.toUpperCase(c));
						else
							sb.append(Character.toLowerCase(c));
						now=false;
					}
				}
				value=sb.toString();
			}else if(format.equals("sentencecase")){
				StringBuilder sb=new StringBuilder();
				boolean now=true;
				char[] chars=value.toCharArray();
				for (char c : chars) {
					if(Character.isSpace(c)){
						sb.append(c);
					}else{
						if(now)
							sb.append(Character.toUpperCase(c));
						else
							sb.append(Character.toLowerCase(c));
						now=false;
					}
				}
				value=sb.toString();
			}
		}

		if (this.canBeEmpty != null && this.canBeEmpty && value.length() == 0)
			return new String[] { null, "" };

		if (minSize != null) {
			if (value.length() < minSize) {
				return new String[] {
						cons.validationFailedShort(fieldName,
								minSize.toString()), null };
			}
		}

		if (maxSize != null) {
			if (value.length() > maxSize) {
				return new String[] {
						cons.validationFailedLong(fieldName, maxSize.toString()),
						null };
			}
		}

		if (acceptWS != null && !acceptWS) {
			for (int i = 0; i < value.length(); i++) {
				if (Character.isSpace(value.charAt(i))) {
					return new String[] {
							cons.validationFailedHasWS(fieldName), null };
				}
			}

		}

		if (code.equals("integer")) {
			try {
				Integer.parseInt(value);
			} catch (NumberFormatException e) {
				return new String[] {
						cons.validationFailed(fieldName, cons.integer()), null };
			}
		} else if (code.equals("decimal")) {
			try {
				Double.parseDouble(value);
			} catch (NumberFormatException e) {
				return new String[] {
						cons.validationFailed(fieldName, cons.decimal()), null };
			}
		} else if (code.equals("email")) {
			if (!value
					.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
				return new String[] {
						cons.validationFailed(fieldName, cons.email()), null };
			}

		} else if (code.equals("phoneno")) {
			int digitCount = 0;
			boolean error = false;
			for (int i = 0; i < value.length(); i++) {
				if (i == 0 && value.charAt(0) == '+') {
					continue;
				}
				if (value.charAt(i) == ' ')
					continue;
				if (Character.isDigit(value.charAt(i))) {
					digitCount++;
				}
				error = true;
				break;
			}
			if (error || digitCount < 11) {
				return new String[] {
						cons.validationFailed(fieldName, cons.phoneno()), null };
			}
		} else if (!noAlpha) {
			char[] extraCharArray = extraChars == null ? null : extraChars
					.toCharArray();
			for (int i = 0; i < value.length(); i++) {
				if (!isValid(value.charAt(i))) {
					if (extraChars != null) {
						boolean ecvalid = false;
						for (char ec : extraCharArray) {
							if (ec == value.charAt(i)) {
								ecvalid = true;
								break;
							}
						}
						if (!ecvalid) {
							return new String[] {
									cons.validationFailed(fieldName, cons
											.validationFailedChars(fieldName,
													validCharacterText())),
									null };
						}
					} else {
						return new String[] {
								cons.validationFailed(fieldName, cons
										.validationFailedChars(fieldName,
												validCharacterText())), null };
					}

				}

			}
		}
		return new String[] { null, value };
	}

	private boolean isValid(char c) {
		if (this.alphaLatin) {
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
				return true;
		}
		if (this.alpha && Character.isLetter(c))
			return true;

		if (this.digits && Character.isDigit(c)) {
			return true;
		}
		return false;
	}

	private String validCharacterText() {
		List<String> lst = new LinkedList<String>();
		if (this.alpha) {
			lst.add(cons.alpha());
		} else if (this.alphaLatin) {
			lst.add(cons.latinalpha());
		}

		if (this.digits) {
			lst.add(cons.digits());
		}

		if (extraChars != null) {
			lst.add(cons.extrachars() + this.extraChars);
		}

		switch (lst.size()) {
		case 0:
			return "";
		case 1:
			return lst.get(0);
		case 2:
			return lst.get(0) + " " + cons.and() + " " + lst.get(1);
		default:
			return lst.get(0) + "," + lst.get(1) + " " + cons.and() + " "
					+ lst.get(2);
		}
	}
}