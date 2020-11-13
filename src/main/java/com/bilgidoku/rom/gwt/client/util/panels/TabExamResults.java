package com.bilgidoku.rom.gwt.client.util.panels;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.gwt.client.common.Json;
import com.bilgidoku.rom.gwt.client.common.resp.JsonResponse;
import com.bilgidoku.rom.gwt.araci.client.site.Contents;
import com.bilgidoku.rom.gwt.araci.client.site.ContentsResponse;
import com.bilgidoku.rom.gwt.araci.client.site.ExamsDao;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.constants.ApplicationConstants;
import com.bilgidoku.rom.shared.RunException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;

public class TabExamResults extends Composite {

	private final static ApplicationConstants trans = GWT.create(ApplicationConstants.class);
	private Map<String, Contents> allExams = new HashMap<String, Contents>();
	FlexTable holder = new FlexTable();

	public TabExamResults() {
		initWidget(holder);
		addHeader();
		getData();
	}

	private void addHeader() {

		holder.setHTML(0, 0, trans.exam());
		holder.setHTML(0, 1, trans.date());
		holder.setHTML(0, 2, trans.duration());
		holder.setHTML(0, 3, trans.trues());
		holder.setHTML(0, 4, trans.falses());
		holder.setHTML(0, 5, trans.empties());
		holder.setHTML(0, 6, trans.grade());

		for (int i = 0; i < 7; i++) {
			holder.getCellFormatter().setStyleName(0, i, "cell-header");
		}
		
	}

	private void getData() {
		ExamsDao.list("en", "/_/exams", new ContentsResponse() {
			@Override
			public void array(List<Contents> myarr) {
				for (int i = 0; i < myarr.size(); i++) {
					allExams.put(myarr.get(i).uri, myarr.get(i));
				}

				ExamsDao.myresults("/_/exams", new JsonResponse() {
					@Override
					public void array(List<Json> myarr) {
						for (int i = 0; i < myarr.size(); i++) {
							String s = i%2 == 0 ? "row row-even" : "row row-odd";
							holder.getRowFormatter().setStyleName(i+1, s);
							
							JSONObject obj = myarr.get(i).getValue().isObject();
							
							Contents examInfo = getExamInfo(obj);
							if (examInfo != null) {
								holder.setHTML(i+1, 0, examInfo.title[0]);
							}
							// f3=> starttime f4=> endtime
							if (ClientUtil.getString(obj.get("f3")).isEmpty()
									|| ClientUtil.getString(obj.get("f4")).isEmpty()) {
								// skip this exam
								continue;
							}

							Date sd = ClientUtil.getDate(ClientUtil.getString(obj.get("f3")));
							Date ed = ClientUtil.getDate(ClientUtil.getString(obj.get("f4")));
							
							holder.setHTML(i+1, 1, ClientUtil.fmtShortDate(ClientUtil.getString(obj.get("f3"))));

							long diff = ed.getTime() - sd.getTime();
							long diffSeconds = diff / 1000 % 60;
							if (diffSeconds < 60) {
								holder.setHTML(i+1, 2, diffSeconds + " secs");
							} else {
								long diffMinutes = diff / (60 * 1000) % 60;
								holder.setHTML(i+1, 2, diffMinutes + " mins");
							}
							
							double[] falseArray = ClientUtil.getNumericArray(obj.get("f7"));
							double[] emptyArray = ClientUtil.getNumericArray(obj.get("f8"));

							holder.setHTML(i+1, 3, ClientUtil.getNumber(obj.get("f6")) + ""); // =>truecount							
							holder.setHTML(i+1, 4, falseArray != null ? falseArray.length + "" : ""); // =>falses
							holder.setHTML(i+1, 5, emptyArray != null ? emptyArray.length + "" : "") ; // =>empties
							holder.setHTML(i+1, 6, ClientUtil.getNumber(obj.get("f9")) + "");// =>grade

						}
						
					}
				});

			}
		});

	}

	private Contents getExamInfo(JSONObject obj) {
		try {
			String examUri = ClientUtil.getString(obj, "f2"); // uri
			return allExams.get(examUri);
		} catch (RunException e) {
			e.printStackTrace();
		}
		return null;

	}

}
