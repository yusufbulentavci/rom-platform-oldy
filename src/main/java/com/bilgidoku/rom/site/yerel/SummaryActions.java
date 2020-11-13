package com.bilgidoku.rom.site.yerel;

import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.site.yerel.admin.DataReady;
import com.bilgidoku.rom.site.yerel.admin.HitsActions;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;

public class SummaryActions implements DataReady {

	private HitsActions ha;

	public SummaryActions(boolean stats) {
		if (stats)
			this.ha = new HitsActions(this);
	}

	public void openSummary() {
		final Runnable onLoadCallback = new Runnable() {
			public void run() {
				ha.getWeeklyData();
			}
		};
		VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);
	}

	@Override
	public void hitsDataReady(final DataTable lineData, final DataTable mapData, final DataTable pieData,
			final boolean hasNextWeek) {
		Summary sum = new Summary(lineData, mapData, pieData, hasNextWeek);
		Ctrl.openTab("summary", Ctrl.trans.summary(), sum, Data.DEFAULT_COLOR);

	}

	int total = -1;
	int iIssMine = -1;

//	public void summaryNoStats() {
//
//		CommentsDao.listwaitingapproval(null, "/_/_comments", new CommentsResponse() {
//			@Override
//			public void array(List<Comments> value) {
//				total = 0;
//				for (int i = 0; i < value.size(); i++) {
//					Comments comm = value.get(i);
//					if (comm.delegated.startsWith(Data.ISSUE_ROOT))
//						continue;
//					total++;
//
//				}
//
//				setStatus();
//
//			}
//		});
//
//		IssuesDao.list(true, true, "/_/issues", new IssuesResponse() {
//			public void array(List<Issues> value) {
//				iIssMine = 0;
//				for (int i = 0; i < value.size(); i++) {
//					Issues iss = value.get(i);
//					if (iss.assigned_to.equals(one.userContactId))
//						iIssMine++;
//					else
//						continue;
//				}
//				setStatus();
//
//			}
//		});
//
//		setStatus();
//
//	}
//
//	protected void setStatus() {
//
//		if (total < 0 || iIssMine < 0)
//			return;
//		
//		StringBuffer sb = new StringBuffer(Ctrl.trans.welcome() + "  " + one.userName);
//		if (total > 0) {
//			sb.append("  | " + total + " " + Ctrl.trans.commentsWaiting() + " " + Ctrl.trans.comments());
//		}
//		if (iIssMine > 0) {
//			sb.append("  | " + iIssMine + " " + Ctrl.trans.issuesWaiting());
//		}
//		
//		Ctrl.setStatus("<b>" + sb.toString() + "</b>");
//
////		btnComments.setHTML(total + " " + Ctrl.trans.commentsWaiting() + " " + Ctrl.trans.comments());
////		btnComments.setText(iIssMine + " " + Ctrl.trans.issuesWaiting());
//
//	}

}