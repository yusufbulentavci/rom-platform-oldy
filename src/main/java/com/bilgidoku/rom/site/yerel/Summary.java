package com.bilgidoku.rom.site.yerel;

import java.util.List;

import com.bilgidoku.rom.gwt.araci.client.rom.Comments;
import com.bilgidoku.rom.gwt.araci.client.rom.CommentsDao;
import com.bilgidoku.rom.gwt.araci.client.rom.CommentsResponse;
import com.bilgidoku.rom.gwt.araci.client.rom.Issues;
import com.bilgidoku.rom.gwt.araci.client.rom.IssuesDao;
import com.bilgidoku.rom.gwt.araci.client.rom.IssuesResponse;
import com.bilgidoku.rom.gwt.client.util.common.Data;
import com.bilgidoku.rom.site.yerel.admin.TabHits;
import com.bilgidoku.rom.site.yerel.comment.TabComments;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.Gauge;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

public class Summary extends Composite {
	private final CaptionPanel pnlDaily = new CaptionPanel(Ctrl.trans.dailyHits());

	public Summary(final DataTable lineData, final DataTable mapData, final DataTable pieData, boolean hasNextWeek) {

		VerticalPanel holder = new VerticalPanel();
		holder.setSpacing(11);

		FlexTable ft = new FlexTable();
		ft.setCellSpacing(9);

		// ft.setHTML(0, 0, Ctrl.trans.commentsWaiting() + " " +
		// Ctrl.trans.comments());
		ft.setWidget(0, 1, getCommAppAnchor());

		// ft.setHTML(1, 0, Ctrl.trans.issuesWaiting());
		ft.setWidget(1, 1, getIssuesAppAnchor());

		// ft.setHTML(2, 0, Ctrl.trans.totalPageviewsToday());
		// ft.setWidget(2, 1, new HTML(getDailyHitsCount(lineData) + ""));

		// ft.setHTML(3, 0, Ctrl.trans.totalPageviewsLastweek());
		ft.setWidget(2, 1, getStatsThisWeek(lineData, mapData, pieData, hasNextWeek));

		//
		com.google.gwt.visualization.client.visualizations.corechart.Options lineHitsOptions = com.google.gwt.visualization.client.visualizations.corechart.Options
				.create();
		lineHitsOptions.setHeight(240);
		lineHitsOptions.setWidth(800);
		lineHitsOptions.setTitle(Ctrl.trans.statistics());

		LineChart lineHits = new LineChart(lineData, lineHitsOptions);
		pnlDaily.add(lineHits);

		holder.add(pnlDaily);
		holder.add(ft);

		initWidget(holder);

	}

	private Widget getStatsThisWeek(final DataTable lineData, final DataTable mapData, final DataTable pieData,
			final boolean hasNextWeek) {

		ClickHandler click = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				Ctrl.startWaiting();

				Runnable onLoadCallback = new Runnable() {
					public void run() {

						TabHits tabHits = new TabHits(lineData, mapData, pieData, hasNextWeek);
						Ctrl.openTab("dailyhits", Ctrl.trans.statistics(), tabHits, Data.DEFAULT_COLOR);

					}
				};
				VisualizationUtils.loadVisualizationApi(onLoadCallback, GeoMap.PACKAGE, PieChart.PACKAGE,
						Gauge.PACKAGE);
			}
		};

		SiteToolbarButton btnHits = new SiteToolbarButton("/_local/images/common/chart_.png",
				getWeeklyHitsCount(lineData) + "  " + Ctrl.trans.totalPageviewsLastweek(), Ctrl.trans.statistics(), "");
		btnHits.addClickHandler(click);

//		final Anchor an = new Anchor();
//		an.addClickHandler(click);
//		an.setText(getWeeklyHitsCount(lineData) + "  ");

//		HorizontalPanel hp = new HorizontalPanel();
//		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
//		hp.add(an);
//		hp.add(btnHits);

		return btnHits;
	}

	private int getWeeklyHitsCount(DataTable lineData) {
		int rows = lineData.getNumberOfRows();
		if (rows <= 0)
			return 0;

		int total = 0;
		for (int i = 1; i < 8; i++) {
			if (rows - i < 0)
				break;
			// Sistem.outln(lineData.getValueString(rows - i, 0));
			// Sistem.outln(lineData.getValueString(rows - i, 1));
			total = total + lineData.getValueInt(rows - i, 1);
		}

		return total;
	}

	// private int getDailyHitsCount(DataTable lineData) {
	// int rows = lineData.getNumberOfRows();
	// if (rows > 0) {
	//// Sistem.outln(lineData.getValueString(rows - 1, 0));
	//// Sistem.outln(lineData.getValueString(rows - 1, 1));
	// return lineData.getValueInt(rows - 1, 1);
	// }
	// return 0;
	// }

	private Widget getCommAppAnchor() {

		ClickHandler click = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				TabComments tw = new TabComments(null, Ctrl.trans.approvalWaiting(), "waitingapproval");
				Ctrl.openTab("waitingapproval", Ctrl.trans.approvalWaiting(), tw, Data.DEFAULT_COLOR);
			}
		};

		final SiteToolbarButton btnComments = new SiteToolbarButton("/_local/images/common/speech_bubble.png", "",
				Ctrl.trans.approvalWaiting(), "");

		// final Anchor an = new Anchor();
		// an.addClickHandler(click);

		btnComments.addClickHandler(click);

		// HorizontalPanel hp = new HorizontalPanel();
		// hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		// hp.add(an);
		// hp.add(btnComments);

		CommentsDao.listwaitingapproval(null, "/_/_comments", new CommentsResponse() {
			@Override
			public void array(List<Comments> value) {
				int total = 0;
				for (int i = 0; i < value.size(); i++) {
					Comments comm = value.get(i);
					if (comm.delegated.startsWith(Data.ISSUE_ROOT))
						continue;

					total++;

				}

				btnComments.setText(total + " " + Ctrl.trans.commentsWaiting() + " " + Ctrl.trans.comments());
				btnComments.setHTML(total + " " + Ctrl.trans.commentsWaiting() + " " + Ctrl.trans.comments());

				// an.setText(total + " ");
			}
		});

		return btnComments;
	}

	private Widget getIssuesAppAnchor() {

		// final HorizontalPanel hp = new HorizontalPanel();
		final ClickHandler click = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Ctrl.focusIssues();
			}
		};
		final SiteToolbarButton btnComments = new SiteToolbarButton("/_local/images/common/iss_grey.png",
				Ctrl.trans.issuesWaiting(), Ctrl.trans.approvalWaiting(), "");

		// final Anchor an = new Anchor();
		// an.addClickHandler(click);
		btnComments.addClickHandler(click);
		// hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		// hp.add(an);
		// hp.add(btnComments);

		IssuesDao.list(true, true, "/_/issues", new IssuesResponse() {
			public void array(List<Issues> value) {
				int iIssMine = 0;
				for (int i = 0; i < value.size(); i++) {
					Issues iss = value.get(i);
					if (iss.assigned_to.equals(one.userContactId))
						iIssMine++;
					else
						continue;
				}

				btnComments.setText(iIssMine + " " + Ctrl.trans.issuesWaiting());
			}
		});
		return btnComments;

	}

}