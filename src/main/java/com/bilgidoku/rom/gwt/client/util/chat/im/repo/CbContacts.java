package com.bilgidoku.rom.gwt.client.util.chat.im.repo;

public interface CbContacts {
	
	public void contactAdded(ContactBla cba);

	public void contactRemove(ContactBla rem);

	public void resetList();

	public void presenceChanged(ContactBla param);

	public void dlgSay(ContactBla param, boolean mine, String text);

	public void dlgJoin(ContactBla param, boolean mine);

	public void dlgContactPhotoChanged(ContactBla param, boolean mine);

	public void dlgVideo(ContactBla param, boolean mine, String src, String docmd);

	public void dlgTvImg(ContactBla param, boolean mine, String src);

	public void dlgTvVideo(ContactBla param, boolean mine, String src);

	public void dlgTvMark(ContactBla param, boolean mine, int markx, int marky);

	public void dlgTvVideoCtrl(ContactBla param, boolean mine, Integer secs, String ctrl);

	public void dlgTvHeader(ContactBla param, boolean mine, String str);

	public void dlgTvText(ContactBla param, boolean mine, String str);

	public void dlgTvShow(ContactBla param, boolean mine, char str);

	public void dlgTvPresence(ContactBla param, boolean mine, String text);



}
