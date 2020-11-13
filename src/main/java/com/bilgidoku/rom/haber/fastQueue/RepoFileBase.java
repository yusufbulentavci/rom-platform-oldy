package com.bilgidoku.rom.haber.fastQueue;

import java.io.File;

public class RepoFileBase {
	final File base;


	public RepoFileBase(File base) {
		this.base=base;
	}

	protected int lastIndex() {
		String[] ids = base.list();
		int last=-1;
		if (ids != null && ids.length > 0) {
			for (String string : ids) {
				try {
					int k = Integer.parseInt(string);
					if (k >= last) {
						last = k;
					}
				} catch (Exception e) {
				}
			}
		}
		return last;
	}
	
	protected int firstIndex() {
		String[] ids = base.list();
		int first=Integer.MAX_VALUE;
		if (ids != null && ids.length > 0) {
			for (String string : ids) {
				try {
					int k = Integer.parseInt(string);
					if (k <= first) {
						first = k;
					}
				} catch (Exception e) {
				}
			}
		}
		return first;
	}

}