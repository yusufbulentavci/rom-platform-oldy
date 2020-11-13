package com.bilgidoku.rom.site.yerel.medias;

import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.site.FilesDao;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.widgets.colorpicker.ColorTextBox;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.common.SiteIntegerBox;
import com.bilgidoku.rom.site.yerel.common.SiteToolbarButton;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TabToolbar extends VerticalPanel implements HasWidgets {
	private final CommandPanel pnlComm = new CommandPanel();

	private final SiteToolbarButton btnUndo = new SiteToolbarButton("/_local/images/common/image_undo.png",
			Ctrl.trans.undo(), "", "edit-gerial.mp4");

	private final SiteToolbarButton btnRotateRight = new SiteToolbarButton(
			"/_local/images/common/image_rotate_right.png", Ctrl.trans.rotateRight(), Ctrl.trans.rotateRight(),
			"edit-saga.m4");

	private final SiteToolbarButton btnRotateLeft = new SiteToolbarButton(
			"/_local/images/common/image_rotate_left.png", Ctrl.trans.rotateLeft(), Ctrl.trans.rotateLeft(),
			"edit-sola.m4");

	private final SiteToolbarButton btnCancelCrop = new SiteToolbarButton("/_local/images/common/scissors_minus.png",
			Ctrl.trans.cancelCrop(), Ctrl.trans.cancelCrop(), "");

	private final SiteToolbarButton btnCrop = new SiteToolbarButton("/_local/images/common/scissors.png",
			Ctrl.trans.crop(), Ctrl.trans.crop(), "edit-kirp.mp4");

	private final SiteToolbarButton btnCropNow = new SiteToolbarButton("/_local/images/common/scissors.png",
			Ctrl.trans.cropNow(), Ctrl.trans.cropNow(), "");
	// blur
	private final SiteToolbarButton btnBlur = new SiteToolbarButton("/_local/images/common/image_blur.png",
			Ctrl.trans.blur(), Ctrl.trans.blur(), "edit-bulanikkeskin.mp4");

	private final SiteToolbarButton btnSharpen = new SiteToolbarButton("/_local/images/common/image_sharpen.png",
			Ctrl.trans.sharpen(), Ctrl.trans.sharpen(), "edit-bulanikkeskin.mp4");

	private final SiteToolbarButton btnBorder = new SiteToolbarButton(Ctrl.trans.border(), Ctrl.trans.border(),
			"edit-cerceve.mp4");

	// private final SiteToolbarButton btnMore = new
	// SiteToolbarButton(Ctrl.trans.more(), Ctrl.trans.more(), "");
	// resize
	private final SiteToolbarButton btnShowResize = new SiteToolbarButton("/_local/images/common/image_resize.png",
			Ctrl.trans.resize(), Ctrl.trans.resize(), "edit-tekrarboyutlandir.mp4");

	private final SiteIntegerBox txtWidth = new SiteIntegerBox();
	private final SiteIntegerBox txtHeigth = new SiteIntegerBox();
	private final SiteIntegerBox txtX = new SiteIntegerBox();
	private final SiteIntegerBox txtY = new SiteIntegerBox();
	private final ToggleButton lockWidthHeight = new ToggleButton(new Image("/_local/images/common/link.png"));
	private final ColorTextBox txtColor = new ColorTextBox();
	private final SiteToolbarButton btnResize = new SiteToolbarButton(Ctrl.trans.change(), Ctrl.trans.resize(), "");
	private final SiteToolbarButton btnCropOnDlg = new SiteToolbarButton(Ctrl.trans.cropNow(), Ctrl.trans.cropNow(), "");

	private final SiteToolbarButton btnShowBorder = new SiteToolbarButton("/_local/images/common/image_select.png",
			Ctrl.trans.border(), Ctrl.trans.border(), "edit-cerceve.mp4");

	private final SiteToolbarButton btnClose = new SiteToolbarButton(Ctrl.trans.close(), Ctrl.trans.close(), "");

	private final TabMedia tabMedia;
	private Image image;

//	private Files objFile = null;
	// final PageLangList pnlLang = new PageLangList(this);
	final TextBox txtTitle = new TextBox();
	final TextArea txtSummary = new TextArea();
//	private Widget pnlContent = getContentPanel();
	private int oldWidth = 1;

	public TabToolbar(TabMedia inTabMedia, boolean isImage) {
		this.tabMedia = inTabMedia;
		if (isImage) {
			btnCropNow.setVisible(false);
			lockWidthHeight.setDown(true);
			lockWidthHeight.setWidth("11px");

			setTitles();

			forWidthChange();
			forClose();
			forShowBorder();
			
			
			
			forBorder();
			forCrop();
			forCropNow();
			forCancelCrop();
			forShowResize();
			
			forBlur();
			forResize();
			forSharpen();
			forUndo();
			forRotateRight();
			forRotateLeft();
			forCropOnDlg();
			ui();
		}
	}

	private void forCropNow() {
		btnCropNow.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (tabMedia.isSelectorActive()) {
					Integer x = tabMedia.getSelectorX();
					Integer y = tabMedia.getSelectorY();
					Integer xx = tabMedia.getSelectorXX();
					Integer yy = tabMedia.getSelectorYY();
					crop(x, y, xx, yy);
				}
			}
		});

	}

	private void forBorder() {
		btnBorder.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cancelCrop();
				String w = txtWidth.getValue();
				String h = txtHeigth.getValue();
				int c = txtColor.getIntValue();
				if (w.isEmpty() || h.isEmpty() || c == 0) {
					Window.alert(Ctrl.trans.someValuesEmpty());
					return;
				}
				Ctrl.startWaiting();
				FilesDao.imageborder(new Integer(w), new Integer(h), new Integer(c), tabMedia.getUrl(),
						new StringResponse() {
							@Override
							public void ready(String value) {
								Ctrl.stopWaiting();
								tabMedia.imageReload();
							}
						});
			}
		});
	}

	private void forWidthChange() {
		txtWidth.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				if (!lockWidthHeight.isDown())
					return;
				if (txtWidth.getValue().isEmpty() || txtHeigth.getValue().isEmpty() || oldWidth == 1 || oldWidth == 0)
					return;

				try {

					String nwWidth = txtWidth.getValue();
					int newWidth = Integer.parseInt(nwWidth);

					String oldH = txtHeigth.getValue();
					int oldHeight = Integer.parseInt(oldH);

					int newHeigth = (newWidth * oldHeight) / oldWidth;
					txtHeigth.setValue(newHeigth + "");

					oldWidth = newWidth;

				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		});

	}

	private void forResize() {
		btnResize.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cancelCrop();
				String w = txtWidth.getValue();
				String h = txtHeigth.getValue();
				if (w.isEmpty() || h.isEmpty()) {
					Window.alert(Ctrl.trans.someValuesEmpty());
					return;
				}
				Ctrl.startWaiting();
				FilesDao.imageresize(new Integer(w), new Integer(h), tabMedia.getUrl(), new StringResponse() {
					@Override
					public void ready(String value) {
						Ctrl.stopWaiting();
						tabMedia.imageReload();
					}
				});
			}
		});
	}

	public void setImage(Image img) {
		this.image = img;
	}

	private void ui() {
		btnClose.setStyleName("site-closebutton");
		btnCancelCrop.setEnabled(false);

		this.setWidth("122px");
		Widget[] btns = { btnUndo, btnRotateLeft, btnRotateRight, btnCrop, btnCropNow, btnCancelCrop, btnShowResize,
				btnBlur, btnSharpen, btnShowBorder, btnUndo, pnlComm };
		// f.setHeight("50px");
		for (Widget btn : btns) {
			btn.setWidth("120px");
			this.add(btn);
		}

		// this.add(f);

		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		// this.setWidth("100%");
		this.setStyleName("gwt-RichTextToolbar");
	}

	private void forClose() {
		btnClose.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				pnlComm.clear();
				pnlComm.hide();
			}
		});
	}

	private void cancelCrop() {

		tabMedia.hideSelector();
		btnCancelCrop.setEnabled(false);
		btnCrop.setVisible(true);
		btnCropNow.setVisible(false);

	}

	private void forCancelCrop() {
		btnCancelCrop.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cancelCrop();
			}
		});
	}

	private void forCrop() {
		btnCrop.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!tabMedia.isSelectorActive()) {
					// /show selector
					btnCrop.setVisible(false);
					btnCropNow.setVisible(true);

					tabMedia.showSelector();
					btnCancelCrop.setEnabled(true);
					// show dialog
					oldWidth = image.getWidth();
					
					getSelectorValues();
					
					
					pnlComm.addWidget(TabToolbar.this.getCropForm());
					pnlComm.setText(Ctrl.trans.crop());
					pnlComm.show();
				}
			}
		});
	}

	protected void getSelectorValues() {
		txtX.setValue(tabMedia.getSelectorX() + "");
		txtY.setValue(tabMedia.getSelectorY() + "");
		
		txtWidth.setValue((image.getWidth() - (tabMedia.getSelectorX() + tabMedia.getSelectorXX())) + "");
		txtHeigth.setValue((image.getHeight() - (tabMedia.getSelectorY() + tabMedia.getSelectorYY())) + "");

	}

	protected void crop(Integer x, Integer y, Integer xx, Integer yy) {
		FilesDao.imagecrop(x, y, xx, yy, tabMedia.getUrl(), new StringResponse() {
			@Override
			public void ready(String value) {
				btnCrop.setVisible(true);
				btnCropNow.setVisible(false);
				Ctrl.stopWaiting();
				tabMedia.imageReload();
				tabMedia.hideSelector();
				btnCancelCrop.setEnabled(false);
				pnlComm.hide();

			}

			@Override
			public void err(int statusCode, String statusText, Throwable exception) {
				super.err(statusCode, statusText, exception);
				Ctrl.stopWaiting();
			}
		});
	}

	private void forShowResize() {
		btnShowResize.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cancelCrop();
				oldWidth = image.getWidth();
				txtWidth.setValue(image.getWidth() + "");
				txtHeigth.setValue(image.getHeight() + "");
				pnlComm.setText(Ctrl.trans.resize());
				pnlComm.addWidget(TabToolbar.this.getResizeForm());
				pnlComm.show();
			}
		});
	}

	private void forCropOnDlg() {
		btnCropOnDlg.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Integer x = Integer.valueOf(txtX.getValue());
				Integer y = Integer.valueOf(txtY.getValue());
				Integer w = Integer.valueOf(txtWidth.getValue());
				Integer h = Integer.valueOf(txtHeigth.getValue());
				Integer xx = image.getWidth() - (x + w);
				Integer yy = image.getHeight() - (y + h);
				if (xx <= 0 && yy <= 0) {
					Window.alert(Ctrl.trans.cropAlert());
					return;
				}
				crop(x, y, xx, yy);

			}
		});
	}

	private void forBlur() {
		btnBlur.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cancelCrop();
				Ctrl.startWaiting();
				FilesDao.imageblur(tabMedia.getUrl(), new StringResponse() {
					@Override
					public void ready(String value) {
						Ctrl.stopWaiting();
						tabMedia.imageReload();
					}
				});
			}
		});
	}

	private void forSharpen() {
		btnSharpen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cancelCrop();
				Ctrl.startWaiting();
				FilesDao.imagesharpen(tabMedia.getUrl(), new StringResponse() {
					@Override
					public void ready(String value) {
						Ctrl.stopWaiting();
						tabMedia.imageReload();
					}
				});
			}
		});
	}

	private void forShowBorder() {
		btnShowBorder.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cancelCrop();
				oldWidth = 5;
				txtWidth.setValue("5");
				txtHeigth.setValue("5");
				pnlComm.setText(Ctrl.trans.border());
				pnlComm.addWidget(TabToolbar.this.getBorderForm());
				pnlComm.show();
			}
		});
	}

	private void forUndo() {
		btnUndo.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cancelCrop();
				Ctrl.startWaiting();
				FilesDao.undo(tabMedia.getUrl(), new StringResponse() {
					@Override
					public void ready(String value) {
						Ctrl.stopWaiting();
						tabMedia.imageReload();
					}
				});
			}
		});
	}

	private FlexTable getBorderForm() {
		FlowPanel btns = new FlowPanel();
		btns.add(btnBorder);
		btns.add(btnClose);
		

		FlexTable borderPanel = new FlexTable();
		borderPanel.setSize("118px", "50px");
		borderPanel.setHTML(0, 0, Ctrl.trans.width());
		borderPanel.setWidget(0, 1, txtWidth);
		borderPanel.setWidget(0, 2, lockWidthHeight);

		borderPanel.setHTML(1, 0, Ctrl.trans.height());
		borderPanel.setWidget(1, 1, txtHeigth);

		borderPanel.setHTML(2, 0, Ctrl.trans.color());
		borderPanel.setWidget(2, 1, txtColor);

		borderPanel.setWidget(3, 0, btns);
		borderPanel.getFlexCellFormatter().setColSpan(3, 0, 3);
		borderPanel.getFlexCellFormatter().setColSpan(2, 1, 2);
		borderPanel.getFlexCellFormatter().setWidth(0, 1, "30px");

		borderPanel.getFlexCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_CENTER);
		return borderPanel;
	}

	private FlexTable getResizeForm() {
		FlowPanel btns = new FlowPanel();
		btns.add(btnResize);
		btns.add(btnClose);
		FlexTable resizePanel = new FlexTable();
		resizePanel.setSize("118px", "50px");
		resizePanel.setHTML(0, 0, Ctrl.trans.width());
		resizePanel.setWidget(0, 1, txtWidth);
		resizePanel.setWidget(0, 2, lockWidthHeight);
		resizePanel.setHTML(1, 0, Ctrl.trans.height());
		resizePanel.setWidget(1, 1, txtHeigth);
		resizePanel.setWidget(2, 0, btns);
		resizePanel.getFlexCellFormatter().setColSpan(2, 0, 2);
		resizePanel.getFlexCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		return resizePanel;
	}

	private FlexTable getCropForm() {
		FlowPanel btns = new FlowPanel();
		btns.add(btnCropOnDlg);
		btns.add(btnClose);
		FlexTable resizePanel = new FlexTable();
		resizePanel.setSize("118px", "50px");
		resizePanel.setHTML(0, 0, "X:");
		resizePanel.setWidget(0, 1, txtX);
		resizePanel.setHTML(1, 0, "Y:");
		resizePanel.setWidget(1, 1, txtY);
		resizePanel.setHTML(2, 0, Ctrl.trans.width());
		resizePanel.setWidget(2, 1, txtWidth);
		resizePanel.setWidget(2, 2, lockWidthHeight);
		resizePanel.setHTML(3, 0, Ctrl.trans.height());
		resizePanel.setWidget(3, 1, txtHeigth);
		resizePanel.setWidget(4, 0, btns);
		resizePanel.getFlexCellFormatter().setColSpan(4, 0, 2);
		resizePanel.getFlexCellFormatter().setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_CENTER);
		return resizePanel;
	}

	private class CommandPanel extends Composite {
		private VerticalPanel holder = new VerticalPanel();
		private HTML header = new HTML();
		public CommandPanel() {
			Button cancel = new Button();
			cancel.setStyleName("site-closebutton");
			cancel.addClickHandler(new ClickHandler() {				
				@Override
				public void onClick(ClickEvent event) {
					holder.setVisible(false);					
				}
			});
			holder.add(cancel);
			holder.add(header);
			holder.setStyleName("site-innerform");
			holder.setVisible(false);
			initWidget(holder);
		}
		public void show() {
			holder.setVisible(true);
			
		}
		public void setText(String text) {
			header.setHTML(ClientUtil.getHeader(text));
			
		}
		public void addWidget(FlexTable wdt) {
			if (holder.getWidgetCount() ==3)
				holder.remove(2);
			holder.add(wdt);
			
		}
		public void hide() {
			holder.setVisible(false);
			
		}
		public void clear() {
			if (holder.getWidgetCount() ==3)
				holder.remove(2);
			
		}
	}

	private void setTitles() {
		btnUndo.setTitle(Ctrl.trans.undoDesc());
		btnRotateLeft.setTitle(Ctrl.trans.rotateLeftDesc());
		btnRotateRight.setTitle(Ctrl.trans.rotateRightDesc());
		btnCrop.setTitle(Ctrl.trans.cropDesc());
		btnCropNow.setTitle(Ctrl.trans.cropNowDesc());
		btnCancelCrop.setTitle(Ctrl.trans.cancelCropDesc());
		btnBlur.setTitle(Ctrl.trans.blurDesc());
		btnShowResize.setTitle(Ctrl.trans.resizeDesc());
		btnSharpen.setTitle(Ctrl.trans.sharpenDesc());
		btnShowBorder.setTitle(Ctrl.trans.borderDesc());
	}

	private void forRotateRight() {
		btnRotateRight.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Ctrl.startWaiting();
				FilesDao.imagerotate(new Integer(-90), tabMedia.getUrl(), new StringResponse() {
					@Override
					public void ready(String value) {
						Ctrl.stopWaiting();
						tabMedia.imageReload();
					}
				});
			}
		});
	}

	private void forRotateLeft() {
		btnRotateLeft.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cancelCrop();
				Ctrl.startWaiting();
				FilesDao.imagerotate(new Integer(90), tabMedia.getUrl(), new StringResponse() {
					@Override
					public void ready(String value) {
						Ctrl.stopWaiting();
						tabMedia.imageReload();
					}
				});
			}
		});
	}

	public void resized() {
		getSelectorValues();
	}

}
