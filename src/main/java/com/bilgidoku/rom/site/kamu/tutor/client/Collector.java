package com.bilgidoku.rom.site.kamu.tutor.client;

import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.site.Contents;
import com.bilgidoku.rom.gwt.araci.client.site.ContentsResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Files;
import com.bilgidoku.rom.gwt.araci.client.site.FilesDao;
import com.bilgidoku.rom.gwt.araci.client.site.FilesResponse;
import com.bilgidoku.rom.gwt.araci.client.site.ListsDao;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.shared.util.AsyncMethod;
import com.google.gwt.user.client.ui.Button;

public class Collector {
	private String url;
	private String lang;
	private Option[] options;
	private Button[] selects;
	private Img[] images;
	private AsyncMethod<InstrumentTutor, String> callback;
	private String btnsParams;
	private String code;
	private String summaryParams;
	private String containerUrl;
	private boolean isList;

	public Collector(String lang, String url, boolean isList, String btnsParams, String code, String summaryParams, AsyncMethod<InstrumentTutor, String> callback) {
		this.lang = lang;
		this.url = url;
		this.isList=isList;
		this.callback = callback;
		this.btnsParams=btnsParams;
		this.code=code;
		this.summaryParams=summaryParams;
	}

	public void run() {
		if(url.startsWith("/f/")){
			// File container
			FilesDao.list(lang, 0, 50, url, new FilesResponse() {

				public void array(List<Files> value) {
					options = new Option[value.size()];
					selects = new Button[value.size()];
					images = new Img[value.size()];
					for (int i = 0; i < options.length; i++) {
						Files c = value.get(i);
						options[i] = new Option(i, c.title[0], null, i, null, i, c.summary[0]);
						selects[i] = new Button(c.title[0]);
						images[i] = new Img(c.title[0], c.uri, new Vector2d(0,0), null);
					}
					done();
				}
			});
		}else if(url.startsWith("/_/writings/")){
			// Writing container
			WritingsDao.list(lang, null, url, new ContentsResponse() {

				public void array(List<Contents> value) {
					options = new Option[value.size()];
					selects = new Button[value.size()];
					images = new Img[value.size()];
					for (int i = 0; i < options.length; i++) {
						Contents c = value.get(i);
						options[i] = new Option(i, c.title[0], null, i, null, i, c.summary[0]);
						selects[i] = new Button(c.title[0]);
						images[i] = new Img(c.title[0], c.uri, new Vector2d(0,0), null);
					}
					done();
				}
			});			
		}else if(url.startsWith("/_/lists/")){
			// List container
			ListsDao.content_list(lang, url, new ContentsResponse() {

				public void array(List<Contents> value) {
					options = new Option[value.size()];
					selects = new Button[value.size()];
					images = new Img[value.size()];
					for (int i = 0; i < options.length; i++) {
						Contents c = value.get(i);
						options[i] = new Option(i, c.title[0], null, i, null, i, c.summary[0]);
						selects[i] = new Button(c.title[0]);
						images[i] = new Img(c.title[0], c.uri, new Vector2d(0,0), null);
					}
					done();
				}
			});			
		}
		

	}

	protected void done() {
		Level[] levels = new Level[] { new Level("all", (short)0, 0) };
		PtrStyle ptrStyle = new PtrStyle(2, 5, new Vector3d(255, 0, 0));
		LineStyle lineStyle = new LineStyle(2, new Vector3d(255, 0, 0));
		InstrumentTutor tutor = new InstrumentTutor(lang, url, images, options, selects, levels, ptrStyle,
				lineStyle, btnsParams, code, summaryParams);
		callback.run(tutor);
	}

}
