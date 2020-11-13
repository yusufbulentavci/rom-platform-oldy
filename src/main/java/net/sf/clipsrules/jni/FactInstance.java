package net.sf.clipsrules.jni;

import java.util.ArrayList;
import java.util.List;

public class FactInstance {
	private Long typeAddress;
	private String name;
	private String relationName;
	private List<SlotValue> slotValues;

	/*****************/
	/* FactInstance: */
	/*****************/
	public FactInstance() {
		this(0, "", "", null);
	}

	/*****************/
	/* FactInstance: */
	/*****************/
	public FactInstance(long theTypeAddress, String theName, String theRelationName, List<SlotValue> theSlotValues) {
		typeAddress = theTypeAddress;
		name = theName;
		relationName = theRelationName;
		slotValues = theSlotValues;
	}

	/******************/
	/* getTypeAddress */
	/******************/
	public long getTypeAddress() {
		return typeAddress;
	}

	/***********/
	/* getName */
	/***********/
	public String getName() {
		return name;
	}

	/*******************/
	/* getRelationName */
	/*******************/
	public String getRelationName() {
		return relationName;
	}

	/*****************/
	/* getSlotValues */
	/*****************/
	public List<SlotValue> getSlotValues() {
		return slotValues;
	}

	/*******************/
	/* searchForString */
	/*******************/
	public boolean searchForString(String searchString) {
		if (relationName.toLowerCase().contains(searchString.toLowerCase())) {
			return true;
		}

		if (name.toLowerCase().contains(searchString.toLowerCase())) {
			return true;
		}

		for (SlotValue theSV : slotValues) {
			String svString = relationName + " " + theSV.getSlotName() + " " + theSV.getSlotValue();

			if (svString.toLowerCase().contains(searchString.toLowerCase())) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("Fact:");
		sb.append(name);
		sb.append("-");
		sb.append(relationName);
		sb.append("(");
		for (SlotValue theSV : slotValues) {
			String svString = theSV.getSlotName() + ":" + theSV.getSlotValue();
			sb.append(svString);
		}
		sb.append(")");
		return sb.toString();
	}
}
