package com.bilgidoku.rom.site.kamu.tutor.client;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.min.Sistem;

public class Circle {
	final private List<Short> array=new ArrayList<Short>();
	int index=-1;
	
	
	public List<Short> toList() {
		return array;
	}
	public void insertFirst(short val) {
		Sistem.outln("if");
		array.add(val);
		if(index==-1)
			index=0;
	}
	public void clear() {
		array.clear();
		index=-1;
	}
	public String toString(){
		StringBuilder sb=new StringBuilder("ind:"+index+"array: ");
		for(int i=0; i<array.size(); i++){
			int integer=array.get(i);
			if(i==index){
				sb.append("|");
				sb.append(integer);
				sb.append("|");
			}else{
				sb.append(integer);
			}
			sb.append(",");
		}
		return sb.toString();
		
	}
	public boolean goToNextElement() {
		if(array.size()==0)
			return false;
		nextIndex();
		return true;
	}
	private void nextIndex() {
		index=(index+1)%array.size();
		Sistem.outln("index:"+index+" of "+toString());
	}
	private void fixIndex() {
		if(array.size()==0){
			index=-1;
			return;
		}
		index=(index)%array.size();
	}
	public short getActualElementData() {
		return array.get(index);
	}
	public void insertBeforeActual(short which) {
		if(array.contains(which))
			return;
		if(index==-1){
			array.add(which);
			index=0;
			return;
		}
		array.add(index, which);
		nextIndex();
	}
	public void insertAfterActual(short i) {
		if(array.contains(i))
			return;
		if(index==-1){
			array.add(i);
			index=0;
			return;
		}
		
		if(index==(array.size()-1)){
			array.add(i);
		}else{
			array.add(index+1, i);
		}
			
	}
	public void deleteActualElement() {
		if(index==-1){
			return;
		}
		array.remove(index);
		Sistem.outln("deleted:"+index);
		fixIndex();
	}
	public int getNumberOfElements() {
		return array.size();
	}
	public boolean contains(int i) {
		return array.contains(i);
	}
	
//	public void randomize() {
//		for(int i=array.size()-2; i>=0; i=i-2){
//			Short val=array.remove(i);
//			array.add(val);
//		}
//	}
	

}
