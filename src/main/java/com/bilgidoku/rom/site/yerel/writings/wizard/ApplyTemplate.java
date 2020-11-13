package com.bilgidoku.rom.site.yerel.writings.wizard;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.site.WritingsDao;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.writings.TemplateReady;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

public class ApplyTemplate implements TemplateReady {

	private boolean bodyReady = false;
//	private boolean spotReady = false;
	private boolean commentReady = false;

	public final String title;
	private final PageReady caller;
	public final String writingUri;
	public final String parentUri;
	public final String parentName;
	private String lang;

	public ApplyTemplate(final String parentUri, String parentName, final String title, final String writingUri,
			final String template, final PageReady caller, String lang) {

		this.parentUri = parentUri;
		this.parentName = parentName;
		this.title = title;
		this.caller = caller;
		this.writingUri = writingUri;
		this.lang = lang;

		new GetTemplate(ApplyTemplate.this, template, lang);

	}

	public ApplyTemplate(final String parentUri, String parentName, final String title, final String writingUri,
			final String template, final PageReady caller) {
		
		this(parentUri, parentName, title, writingUri, template, caller, Ctrl.info().langcodes[0]);

	}

	@Override
	public void templateReady(JSONObject obj) {
		bodyReady = false;
//		spotReady = false;
		commentReady = false;
		updateWriting(obj);
	}

	protected void updateWriting(JSONObject obj) {

		String comments = "";
		if (obj.get("dialog_uri") != null && obj.get("dialog_uri").isString() != null
				&& obj.get("dialog_uri").isString().stringValue() != null)
			comments = obj.get("dialog_uri").isString().stringValue();

		if (!comments.isEmpty()) {
			WritingsDao.createdialog(true, true, true, true, true, true, true, false, null, writingUri, new StringResponse() {
				@Override
				public void ready(String value) {
					commentReady = true;
					testReady(writingUri);
				}
			});
		} else {
			commentReady = true;
			testReady(writingUri);
		}

		JSONValue jo = obj.get("body").isArray().get(0);
		
		WritingsDao.body(lang, new Json(jo), writingUri.equals("/") ? "/_/writings" : writingUri, new StringResponse() {
			@Override
			public void ready(String value) {
				bodyReady = true;
				testReady(writingUri);
			}
		});

//		String medIcon = obj.get("medium_icon").isString().stringValue();
//		String largeIcon = obj.get("large_icon").isString().stringValue();
//		ContentsDao.largeicon(largeIcon, writingUri, new StringResponse());
//		ContentsDao.mediumicon(medIcon, writingUri, new StringResponse());
		testReady(writingUri);

	}

	protected void testReady(String writingUri) {
		if (!(bodyReady && commentReady))
			return;
		if (caller != null)
			caller.pageReady(title, writingUri, lang);

	}

}