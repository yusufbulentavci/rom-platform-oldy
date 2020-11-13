package com.bilgidoku.rom.gwt.client.widgets.timeline;

import java.util.Date;

import com.bilgidoku.rom.shared.code.Animation;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.gwt.client.widgets.timeline.Timeline;
import com.bilgidoku.rom.gwt.client.widgets.timeline.Timeline.Options.SCALE;
import com.bilgidoku.rom.gwt.client.widgets.timeline.events.AddHandler;
import com.bilgidoku.rom.gwt.client.widgets.timeline.events.ChangeHandler;
import com.bilgidoku.rom.gwt.client.widgets.timeline.events.DeleteHandler;
import com.bilgidoku.rom.gwt.client.widgets.timeline.events.EditHandler;
import com.bilgidoku.rom.gwt.client.widgets.timeline.events.TimeChangeHandler;
import com.bilgidoku.rom.gwt.client.widgets.timeline.events.AddHandler.AddEvent;
import com.bilgidoku.rom.gwt.client.widgets.timeline.events.ChangeHandler.ChangeEvent;
import com.bilgidoku.rom.gwt.client.widgets.timeline.events.DeleteHandler.DeleteEvent;
import com.bilgidoku.rom.gwt.client.widgets.timeline.events.EditHandler.EditEvent;
import com.bilgidoku.rom.gwt.client.widgets.timeline.events.TimeChangeHandler.TimeChangeEvent;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;

import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.events.RangeChangeHandler;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.events.RangeChangeHandler.RangeChangeEvent;
import com.google.gwt.visualization.client.events.SelectHandler.SelectEvent;

public class TimeLineDlg extends DialogBox {

	DataTable data = null;
	Timeline.Options options = null;
	Timeline timeline = null;
	
	final Date start=new Date(0L);

	DateTimeFormat dtf = DateTimeFormat.getFormat("yyyy-MM-dd");

	public TimeLineDlg() {
		
		options = Timeline.Options.create();
		options.setStyle(Timeline.Options.STYLE.BOX);
		options.setStart(start);
		options.setEnd(start);
		//options.setHeight("200px");
		options.setHeight("auto");
		options.setWidth("75%");
		options.setEditable(true);
		options.setShowCustomTime(true);
		options.setShowNavigation(true);
		// options.setAxisOnTop(true);
		// options.setShowMajorLabels(false);
		// options.setShowMinorLabels(false);
		options.setScale(SCALE.MILLISECOND, 50);
		
		options.setMin(dtf.parse("2012-01-01"));         // lower limit of visible range
		options.setMax(dtf.parse("2012-12-31"));         // upper limit of visible range
        options.setZoomMin(1000L * 2L ); // 3 seconds
        options.setZoomMax(1000L * 60L * 10L);  // 10 minutes
        
        
        data = DataTable.create();
		data.addColumn(DataTable.ColumnType.DATETIME, "startdate");
		data.addColumn(DataTable.ColumnType.DATETIME, "enddate");
		data.addColumn(DataTable.ColumnType.STRING, "content");

		
		
		timeline = new Timeline(data, options);
		// add event handlers
		timeline.addSelectHandler(createSelectHandler(timeline));
		timeline.addRangeChangeHandler(createRangeChangeHandler(timeline));
		timeline.addChangeHandler(createChangeHandler(timeline));
		timeline.addAddHandler(createAddHandler(timeline));
		timeline.addEditHandler(createEditHandler(timeline));
		timeline.addDeleteHandler(createDeleteHandler(timeline));
		timeline.addTimeChangeHandler(createTimeChangeHandler(timeline));
		this.add(timeline);
	}
	
	public void startEnum(int size){
		data.addRows(size);
	}
	
//	public void box(int ind, Box box, Animation ani){
//		data.setValue(ind, 0, new Date(ani.delay));
//		data.setValue(ind, 1, new Date(ani.delay+ani.duration));
//		data.setValue(ind, 2, "box"+ind);
//	}
//	
	public void endEnum(){
		
	}
	
	/**
	 * add a select handler (the select event occurs when the user clicks on an
	 * event)
	 * 
	 * @param timeline
	 * @return
	 */
	private SelectHandler createSelectHandler(final Timeline timeline) {
		return new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				JsArray<Selection> sel = timeline.getSelections();

				if (sel.length() > 0) {
					int row = sel.get(0).getRow();
					String info = "Select event " + String.valueOf(row)
							+ " selected";
					RomEntryPoint.one.addToRootPanel("lblInfo", new Label(info));
				} else {
					String info = "Select event &lt;nothing&gt; selected";
					RomEntryPoint.one.addToRootPanel("lblInfo", new Label(info));
				}
			}
		};
	}

	/**
	 * create a RangeChange handler (this event occurs when the user changes the
	 * visible range by moving or scrolling the Timeline).
	 * 
	 * @param timeline
	 * @return
	 */
	private RangeChangeHandler createRangeChangeHandler(final Timeline timeline) {
		return new RangeChangeHandler() {
			@Override
			public void onRangeChange(RangeChangeEvent event) {
//				getRange();
			}
		};
	}

	/**
	 * create a change handler (this event occurs when the user changes the
	 * position of an event by dragging it).
	 * 
	 * @param timeline
	 * @return
	 */
	private ChangeHandler createChangeHandler(final Timeline timeline) {
		return new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				// retrieve the row number of the changed event
//				JsArray<Selection> sel = timeline.getSelections();
//				if (sel.length() > 0) {
//					int row = sel.get(0).getRow();
//
//					boolean confirmChanges = chkConfirmChange.getValue();
//
//					// request confirmation
//					boolean applyChange = confirmChanges ? Window
//							.confirm("Are you sure you want to change this event?")
//							: true;
//
//					if (applyChange) {
//						String info = "Change event " + String.valueOf(row)
//								+ " changed";
//						RootPanel.get("lblInfo").add(new Label(info));
//					} else {
//						timeline.cancelChange();
//						String info = "Change event " + String.valueOf(row)
//								+ " cancelled";
//						RootPanel.get("lblInfo").add(new Label(info));
//					}
//				}
			}
		};
	}

	/**
	 * create an add handler (this event occurs when the user creates a new
	 * event).
	 * 
	 * @param timeline
	 * @return
	 */
	private AddHandler createAddHandler(final Timeline timeline) {
		return new AddHandler() {
			@Override
			public void onAdd(AddEvent event) {
				// retrieve the row number of the changed event
				JsArray<Selection> sel = timeline.getSelections();
				if (sel.length() > 0) {
					int row = sel.get(0).getRow();

					// request confirmation
					String title = Window.prompt(
							"Enter a title for the new event", "New event");

					if (title != null) {
						// apply the new title
						data.setValue(row, 2, title);

						String info = "Add event " + String.valueOf(row)
								+ " applied";
						RomEntryPoint.one.addToRootPanel("lblInfo", new Label(info));
					} else {
						// cancel creating new event
						timeline.cancelAdd();
						String info = "Add event " + String.valueOf(row)
								+ " cancelled";
						RomEntryPoint.one.addToRootPanel("lblInfo", new Label(info));
					}
				}
			}
		};
	}

	/**
	 * create a time change handler (this event occurs when the user draggs
	 * the custom time bar).
	 * 
	 * @param timeline
	 * @return
	 */
	private TimeChangeHandler createTimeChangeHandler(final Timeline timeline) {
		return new TimeChangeHandler() {
			@Override
			public void onTimeChange(TimeChangeEvent event) {
//				Date time = event.getTime();
//				lblCustomTime.setText(time.toString());
			}
		};
	}
	

	/**
	 * create an edit handler (this event occurs when the user double clicks 
	 * an event).
	 * 
	 * @param timeline
	 * @return
	 */
	private EditHandler createEditHandler(final Timeline timeline) {
		return new EditHandler() {
			@Override
			public void onEdit(EditEvent event) {
				// retrieve the row number of the changed event
				JsArray<Selection> sel = timeline.getSelections();
				if (sel.length() > 0) {
					int row = sel.get(0).getRow();

					// request confirmation
					String title = Window.prompt(
							"Enter a new title", 
							data.getValueString(row, 2));

					if (title != null) {
						// apply the new title
						data.setValue(row, 2, title);

						String info = "Edit event " + String.valueOf(row)
								+ " applied";
						RomEntryPoint.one.addToRootPanel("lblInfo", new Label(info));
					} else {
						// edit nothing
						String info = "Edit event " + String.valueOf(row)
								+ " cancelled";
						RomEntryPoint.one.addToRootPanel("lblInfo", new Label(info));
					}
				}
			}
		};
	}	
	
	/**
	 * create a delete handler (this event occurs when the user clicks the
	 * delete button on the top right of an event).
	 * 
	 * @param timeline
	 * @return
	 */
	private DeleteHandler createDeleteHandler(final Timeline timeline) {
		return new DeleteHandler() {
			@Override
			public void onDelete(DeleteEvent event) {
				// retrieve the row number of the changed event
//				JsArray<Selection> sel = timeline.getSelections();
//				if (sel.length() > 0) {
//					int row = sel.get(0).getRow();
//
//					boolean confirmChanges = chkConfirmChange.getValue();
//
//					// request confirmation
//					boolean applyDelete = confirmChanges ? Window
//							.confirm("Are you sure you want to delete this event?")
//							: true;
//
//					if (applyDelete) {
//						String info = "Deleted event " + String.valueOf(row);
//						RootPanel.get("lblInfo").add(new Label(info));
//					} else {
//						timeline.cancelDelete();
//						String info = "Delete event " + String.valueOf(row)
//								+ " cancelled";
//						RootPanel.get("lblInfo").add(new Label(info));
//					}
//				}
			}
		};
	}
	

}
