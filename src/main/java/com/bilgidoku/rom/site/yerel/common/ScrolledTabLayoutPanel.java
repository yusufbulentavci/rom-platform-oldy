package com.bilgidoku.rom.site.yerel.common;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A {@link TabLayoutPanel} that shows scroll buttons if necessary
 */
public class ScrolledTabLayoutPanel extends TabLayoutPanel {


	private LayoutPanel panel;
	private FlowPanel tabBar;
	private Image scrollLeftButton;
	private Image scrollRightButton;
	private HandlerRegistration windowResizeHandler;

	private ImageResource leftArrowImage;
	private ImageResource rightArrowImage;

	public ScrolledTabLayoutPanel(double barHeight, Unit barUnit, ImageResource leftArrowImage, ImageResource rightArrowImage) {
		super(barHeight, barUnit);

		this.leftArrowImage = leftArrowImage;
		this.rightArrowImage = rightArrowImage;

		// The main widget wrapped by this composite, which is a LayoutPanel
		// with the tab bar & the tab content
		panel = (LayoutPanel) getWidget();

		// Find the tab bar, which is the first flow panel in the LayoutPanel
		for (int i = 0; i < panel.getWidgetCount(); ++i) {
			Widget widget = panel.getWidget(i);
			if (widget instanceof FlowPanel) {
				this.setTabBar((FlowPanel) widget);
				break; // tab bar found
			}
		}

		initScrollButtons();
	}

	@Override
	public void add(Widget child, Widget tab) {
		super.add(child, tab);
		checkIfScrollButtonsNecessary();
	}

	@Override
	public boolean remove(Widget w) {
		boolean b = super.remove(w);
		checkIfScrollButtonsNecessary();
		return b;
	}

	@Override
	protected void onLoad() {
		super.onLoad();

		if (windowResizeHandler == null) {
			windowResizeHandler = Window.addResizeHandler(new ResizeHandler() {
				@Override
				public void onResize(ResizeEvent event) {
					checkIfScrollButtonsNecessary();
				}
			});
		}
	}

	@Override
	protected void onUnload() {
		super.onUnload();

		if (windowResizeHandler != null) {
			windowResizeHandler.removeHandler();
			windowResizeHandler = null;
		}
	}

	private ClickHandler createScrollClickHandler(final int diff) {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Widget lastTab = getLastTab();
				if (lastTab == null)
					return;

				int newLeft = parsePosition(getTabBar().getElement().getStyle().getLeft()) + diff;
				int rightOfLastTab = getRightOfWidget(lastTab);

				// Prevent scrolling the last tab too far away form the right
				// border,
				// or the first tab further than the left border position
				if (newLeft <= 0 && (getTabBarWidth() - newLeft < (rightOfLastTab + 20))) {
					scrollTo(newLeft);
				}
			}
		};
	}

	/** Create and attach the scroll button images with a click handler */
	private void initScrollButtons() {
		scrollLeftButton = new Image(leftArrowImage);
		int leftImageWidth = scrollLeftButton.getWidth();
		scrollLeftButton.addClickHandler(createScrollClickHandler(+20));
		scrollLeftButton.setVisible(true);

		scrollRightButton = new Image(rightArrowImage);
		scrollRightButton.addClickHandler(createScrollClickHandler(-20));
		scrollRightButton.setVisible(true);
		/*
		panel.insert(scrollLeftButton, 0);
		panel.setWidgetLeftWidth(scrollLeftButton, 0, Unit.PX, leftImageWidth, Unit.PX);
		panel.setWidgetTopHeight(scrollLeftButton, 0, Unit.PX, scrollLeftButton.getWidth(), Unit.PX);
		panel.insert(scrollRightButton, 0);
		panel.setWidgetLeftWidth(scrollRightButton, leftImageWidth + IMAGE_PADDING_PIXELS, Unit.PX, scrollRightButton.getWidth(), Unit.PX);
		panel.setWidgetTopHeight(scrollRightButton, 0, Unit.PX, scrollRightButton.getHeight(), Unit.PX);
		*/
		getTabBar().removeFromParent();
		getTabBar().setWidth("16384px");
		FlexTable left = new FlexTable();
		left.setWidget(0, 0, scrollLeftButton);
		left.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);

		FlexTable right = new FlexTable();
		//right.setCellPadding(0);
		right.setWidget(0, 0, scrollRightButton);
		right.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);

		DockLayoutPanel newTabBar = new DockLayoutPanel(Unit.PX);
		newTabBar.setSize("100%", "30px");
		newTabBar.addWest(left, 18);
		newTabBar.addEast(right, 18);
		newTabBar.add(getTabBar());
		panel.insert(newTabBar, 0);
		panel.setWidgetTopHeight(newTabBar, 0, Unit.PX, 30, Unit.PX);

	}

	private void checkIfScrollButtonsNecessary() {
		// Defer size calculations until sizes are available, when calculating
		// immediately after
		// add(), all size methods return zero
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {

			@Override
			public void execute() {
				boolean isScrolling = isScrollingNecessary();
				// When the scroll buttons are being hidden, reset the scroll
				// position to zero to
				// make sure no tabs are still out of sight
				if (scrollRightButton.isVisible() && !isScrolling) {
					resetScrollPosition();
				}
				scrollRightButton.setVisible(isScrolling);
				scrollLeftButton.setVisible(isScrolling);
			}

		});

	}

	private void resetScrollPosition() {
		scrollTo(0);
	}

	private void scrollTo(int pos) {
		getTabBar().getElement().getStyle().setLeft(pos, Unit.PX);
	}

	private boolean isScrollingNecessary() {
		Widget lastTab = getLastTab();
		if (lastTab == null)
			return false;

		return getRightOfWidget(lastTab) > getTabBarWidth();
	}

	private int getRightOfWidget(Widget widget) {
		return widget.getElement().getOffsetLeft() + widget.getElement().getOffsetWidth();
	}

	private int getTabBarWidth() {
		return getTabBar().getElement().getParentElement().getClientWidth();
	}

	private Widget getLastTab() {
		if (getTabBar().getWidgetCount() == 0)
			return null;

		return getTabBar().getWidget(getTabBar().getWidgetCount() - 1);
	}

	private static int parsePosition(String positionString) {
		int position;
		try {
			for (int i = 0; i < positionString.length(); i++) {
				char c = positionString.charAt(i);
				if (c != '-' && !(c >= '0' && c <= '9')) {
					positionString = positionString.substring(0, i);
				}
			}

			position = Integer.parseInt(positionString);
		} catch (NumberFormatException ex) {
			position = 0;
		}
		return position;
	}

	public FlowPanel getTabBar() {
		return tabBar;
	}

	public void setTabBar(FlowPanel tabBar) {
		this.tabBar = tabBar;
	}
}