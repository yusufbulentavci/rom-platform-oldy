package com.bilgidoku.rom.gwt.client.common;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import com.bilgidoku.rom.gwt.client.common.coders.TypeCoder;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;

public class ObjectArray<K> implements List<K> {

	private JSONArray jsonArray;
	private int size;
	private TypeCoder<K> coder;

	public ObjectArray(JSONValue jsonValue, TypeCoder<K> coder) {
		this.jsonArray = jsonValue.isArray();
		this.coder = coder;
		this.size = jsonArray.size();
	}

	//

	public void add(int index, K element) {
		// TODO Auto-generated method stub

	}

	public boolean addAll(Collection<? extends K> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean addAll(int index, Collection<? extends K> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public void clear() {
		// TODO Auto-generated method stub

	}

	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	public K get(int ind) {
		JSONValue va = jsonArray.get(ind);
		if (va == null || va instanceof JSONNull)
			return null;
		return coder.decode(va);
	}

	public int indexOf(Object o) {
		throw new RuntimeException("Indexof not implemented");
	}

	public boolean isEmpty() {
		return jsonArray.size() == 0;
	}

	// public K next() {
	// index+=1;
	// K k = factory.create(jsonArray.get(index));
	// return k;
	// }
	//
	// public boolean hasNext() {
	// // nextindex<=lastindex
	// return (index+1)<=(size-1);
	// }
	//
	// public int nextIndex() {
	// index+=1;
	// return 0;
	// }

	private class ObjectArrayIterator implements Iterator<K>, ListIterator<K> {
		int index; // index of next element to return

		ObjectArrayIterator() {
			index = 0;
		}

		ObjectArrayIterator(int from) {
			index = from;
		}

		public boolean hasNext() {
			return (index + 1) <= (size - 1);
		}

		public K next() {
			if (index >= size)
				throw new NoSuchElementException();

			index += 1;

			JSONValue va = jsonArray.get(index);
			if (va == null || va instanceof JSONNull)
				return null;
			return coder.decode(va);
		}

		public void remove() {
		}

		
		public K previous() {
			return null;
		}

		public boolean hasPrevious() {
			return index != 0;
		}

		public int nextIndex() {
			return index;
		}

		public int previousIndex() {
			return index - 1;
		}

		
		public void set(K e) {

		}

		
		public void add(K e) {

		}
	}

	
	public Iterator<K> iterator() {
		return new ObjectArrayIterator();
	}

	
	public int lastIndexOf(Object o) {
		return indexOf(o);
	}

	
	public ListIterator<K> listIterator() {
		return new ObjectArrayIterator();
	}

	
	public ListIterator<K> listIterator(int index) {
		return new ObjectArrayIterator(index);
	}

	
	public boolean remove(Object o) {
		return false;
	}

	
	public K remove(int index) {
		return null;
	}

	
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	
	public K set(int index, K element) {
		return null;
	}

	
	public int size() {
		return jsonArray.size();
	}

	
	public List<K> subList(int fromIndex, int toIndex) {
		return null;
	}

	
	public Object[] toArray() {
		return null;
	}

	
	public <T> T[] toArray(T[] a) {
		return null;
	}
	
	
	public boolean add(K e) {
		// TODO Auto-generated method stub
		return false;
	}

		

}
