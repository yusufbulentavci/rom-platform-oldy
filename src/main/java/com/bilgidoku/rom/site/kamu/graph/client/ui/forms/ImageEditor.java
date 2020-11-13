package com.bilgidoku.rom.site.kamu.graph.client.ui.forms;

import com.bilgidoku.rom.site.kamu.graph.client.GraphicEditor;
import com.bilgidoku.rom.site.kamu.graph.client.ImgUtil;
import com.bilgidoku.rom.site.kamu.graph.client.ui.SimpleChangeListener;
import com.bilgidoku.rom.gwt.client.common.resp.BooleanResponse;
import com.bilgidoku.rom.gwt.client.common.resp.StringResponse;
import com.bilgidoku.rom.gwt.araci.client.site.FilesDao;
import com.bilgidoku.rom.gwt.client.util.common.ActionBarDlg;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.gwt.client.widgets.colorpicker.ColorBox;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.min.geo.Point;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ImageEditor extends ActionBarDlg {

	private final SiteButton btnUndo = new SiteButton("/_public/images/bar/image_undo.png", GraphicEditor.trans.undo(),
			"", "sml");

	private final SiteButton btnRotateRight = new SiteButton("/_public/images/bar/image_rotate_right.png",
			GraphicEditor.trans.roateRight(), "", "sml");

	private final SiteButton btnRotateLeft = new SiteButton("/_public/images/bar/image_rotate_left.png",
			GraphicEditor.trans.rotateLeft(), "", "sml");

	private final SiteButton btnBlur = new SiteButton("/_public/images/bar/image_blur.png", GraphicEditor.trans.blur(),
			GraphicEditor.trans.blur(), "sml");

	private final SiteButton btnSharpen = new SiteButton("/_public/images/bar/image_sharpen.png",
			GraphicEditor.trans.sharpen(), GraphicEditor.trans.sharpen(), "sml");

	private final SiteButton btnCropNow = new SiteButton("/_public/images/bar/scissors.png", GraphicEditor.trans.crop(),
			"", "sml");

	private final SiteButton btnCancelCrop = new SiteButton("/_public/images/bar/scissors_minus.png",
			GraphicEditor.trans.cancelCrop(), "", "sml");

	private final SiteButton btnDelBackground = new SiteButton("/_public/images/bar/image_delcolor.png",
			GraphicEditor.trans.delBackground(), GraphicEditor.trans.delBackground(), "sml");

	private final SiteButton btnUse = new SiteButton("/_public/images/bar/check.png", GraphicEditor.trans.ok(),
			GraphicEditor.trans.ok(), "sml");

	private final SiteButton[] btns = { btnUse, btnRotateRight, btnRotateLeft, btnBlur, btnSharpen, btnDelBackground,
			btnCropNow, btnCancelCrop, btnUndo };
	private String imgUrl;
	private Image image = new Image();
	private final SplitLayoutPanel selector = new SplitLayoutPanel(8);
	private final SimplePanel leftSelect = new SimplePanel();
	private final SimplePanel topSelect = new SimplePanel();
	private final SimplePanel rightSelect = new SimplePanel();
	private final SimplePanel bottomSelect = new SimplePanel();

	private SimpleChangeListener caller = null;
	final TextBox txtWidth = new TextBox();
	final TextBox txtHeigth = new TextBox();
	private int oldWidth = 0;
	private int oldHeight = 0;

	private final FlexTable toolbar = new FlexTable();
	private float smallPercent = 0;

	public ImageEditor(final SimpleChangeListener caller, String imgUrl) {
		super(GraphicEditor.trans.changeImage() + ":" + ClientUtil.getTitleFromUri(imgUrl), null, null);
		this.caller = caller;
		this.imgUrl = ClientUtil.getLastUri(imgUrl);
		this.setGlassEnabled(true);
		this.setAutoHideEnabled(false);

		refreshGui(true);

		run();
	}

	@Override
	public Widget ui() {
		toolbar.setHeight("125px");
		HorizontalPanel hp = new HorizontalPanel();
		hp.setWidth("800px");
		for (int i = 0; i < btns.length; i++) {
			hp.add(btns[i]);
			btns[i].makeSml();
		}

		forBlur();
		forSharpen();
		forUndo();
		forRotateRight();
		forRotateLeft();
		forDelBackground();
		forCropNow();
		forCropCancel();
		forOK();

		CaptionPanel cpBorder = new CaptionPanel(GraphicEditor.trans.border());
		cpBorder.add(getBorderForm());
		cpBorder.setWidth("auto");

		CaptionPanel cpResize = new CaptionPanel(GraphicEditor.trans.resize());
		cpResize.add(getResizeForm());
		cpResize.setWidth("auto");

		toolbar.setStyleName("site-form");
		toolbar.setWidget(0, 0, hp);
		toolbar.setWidget(1, 0, cpBorder);
		toolbar.setWidget(1, 1, cpResize);
		toolbar.getFlexCellFormatter().setColSpan(0, 0, 2);

		FlowPanel im = new FlowPanel();
		im.getElement().getStyle().setPosition(Position.RELATIVE);
		im.getElement().getStyle().setBackgroundColor("lightgray");
		im.add(toolbar);
		im.add(image);
		im.add(selector);

		return im;
	}

	private void forOK() {
		btnUse.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				caller.changed(imgUrl);
				ImageEditor.this.hide();
			}
		});
	}

	private void forCropCancel() {
		btnCancelCrop.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selector.setVisible(false);
			}
		});
	}

	private void forDelBackground() {
		// TODO
		btnDelBackground.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (!imgUrl.toLowerCase().endsWith("png")) {
					final String uri = imgUrl.substring(0, imgUrl.lastIndexOf(".") + 1) + "png";
					FilesDao.imagemakepng(uri, imgUrl, new BooleanResponse() {
						@Override
						public void ready(Boolean value) {
							imgUrl = uri;
							// TODO nasıl olacak
							caller.changed(uri);
							FilesDao.imagemaketransparent(0, 0, 0, imgUrl, new StringResponse() {
								public void ready(String value) {
									Window.alert("ret" + value);
									Window.alert("oldu");
								};
							});
						}
					});
				}
			}
		});

	}

	private Widget getResizeForm() {

		final SiteButton btnResize = new SiteButton("/_public/images/bar/image_resize.png",
				GraphicEditor.trans.resize(), GraphicEditor.trans.resize(), "sml");

		btnResize.setStyleName("site-btn site-smlbtn");

		txtWidth.setStyleName("site-box");
		txtHeigth.setStyleName("site-box");

		txtWidth.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {

				if (txtWidth.getValue().isEmpty() || txtHeigth.getValue().isEmpty() || oldWidth == 0)
					return;

				try {

					String nwWidth = txtWidth.getValue();
					int newWidth = Integer.parseInt(nwWidth);

					String oldH = txtHeigth.getValue();
					int oldHeight = Integer.parseInt(oldH);

					int newHeigth = (newWidth * oldHeight) / oldWidth;
					txtHeigth.setValue(newHeigth + "");

					oldWidth = newWidth;
					oldHeight = newHeigth;

				} catch (Exception e) {

				}

			}
		});

		btnResize.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String w = txtWidth.getValue();
				String h = txtHeigth.getValue();
				if (w.isEmpty() || h.isEmpty()) {
					Window.alert(GraphicEditor.trans.someValuesAreEmpty());
					return;
				}

				if (w.equals(oldWidth) && h.equals(oldHeight)) {
					Window.alert(GraphicEditor.trans.noChange());
					return;
				}

				try {
					final int i = Integer.parseInt(w);
					final int j = Integer.parseInt(h);

					FilesDao.imageresize(new Integer(i), new Integer(j), ClientUtil.getOnlyUri(imgUrl),
							new StringResponse() {
								@Override
								public void ready(String value) {
									refreshGui(false);
								}
							});
				} catch (Exception e) {
					Sistem.log.outln(e.getMessage());

				}

			}
		});

		txtWidth.setWidth("40px");
		txtHeigth.setWidth("40px");

		FlexTable resizePanel = new FlexTable();
		resizePanel.setHTML(0, 0, GraphicEditor.trans.width());
		resizePanel.setHTML(0, 1, GraphicEditor.trans.height());
		resizePanel.setWidget(1, 0, txtWidth);

		resizePanel.setWidget(1, 1, txtHeigth);
		resizePanel.setWidget(1, 2, btnResize);
		return resizePanel;

	}

	private void forUndo() {
		btnUndo.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				FilesDao.undo(ClientUtil.getOnlyUri(imgUrl), new StringResponse() {
					@Override
					public void ready(String value) {
						refreshGui(false);
					}
				});
			}
		});
	}

	private Widget getBorderForm() {

		final ListBox lbTopBottom = new ListBox();
		final ListBox lbLeftRight = new ListBox();
		final ColorBox txtColor = new ColorBox();

		final SiteButton apply = new SiteButton("/_public/images/bar/image.png", GraphicEditor.trans.border(),
				GraphicEditor.trans.border(), "sml");

		apply.addStyleName("site-smlbtn");
		lbTopBottom.setStyleName("site-box");
		lbLeftRight.setStyleName("site-box");

		for (int i = 0; i < 21; i++) {
			lbTopBottom.addItem(i + "");
		}
		for (int i = 0; i < 21; i++) {
			lbLeftRight.addItem(i + "");
		}

		lbTopBottom.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				int i = Integer.parseInt(lbTopBottom.getSelectedValue());
				lbLeftRight.setSelectedIndex(i);
			}
		});

		apply.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String w = lbTopBottom.getSelectedValue();
				String h = lbLeftRight.getSelectedValue();
				int c = txtColor.getIntValue();
				if (w.isEmpty() || h.isEmpty()) {
					Window.alert(GraphicEditor.trans.someValuesAreEmpty());
					return;
				}
				FilesDao.imageborder(new Integer(w), new Integer(h), new Integer(c), ClientUtil.getOnlyUri(imgUrl),
						new StringResponse() {
							@Override
							public void ready(String value) {
								refreshGui(false);
							}
						});

			}
		});

		FlexTable borderPanel = new FlexTable();
		borderPanel.setHTML(0, 0, GraphicEditor.trans.topBottom());
		borderPanel.setHTML(0, 1, GraphicEditor.trans.leftRight());
		borderPanel.setHTML(0, 2, GraphicEditor.trans.color());

		borderPanel.setWidget(1, 0, lbTopBottom);
		borderPanel.setWidget(1, 1, lbLeftRight);
		borderPanel.setWidget(1, 2, txtColor);

		borderPanel.setWidget(1, 3, apply);
		return borderPanel;

	}

	public void refreshGui(final boolean show) {

		imgUrl = ClientUtil.getLastUri(imgUrl);
		image.setUrl(imgUrl);

		ImgUtil.size(imgUrl, new AsyncCallback<Point>() {
			@Override
			public void onSuccess(Point result) {
				txtWidth.setValue(result.getX() + "");
				txtHeigth.setValue(result.getY() + "");
				oldWidth = result.getX();
				oldHeight = result.getY();

				if (result.getX() > 800) {
					smallPercent = result.getX() / 800;
					image.setWidth("800px");
					ImageEditor.this.setHTML(ActionBarDlg.getDlgCaption(null, GraphicEditor.trans.changeImage() + ":"
							+ ClientUtil.getTitleFromUri(imgUrl) + " %" + smallPercent + " daha küçük"));

					image.addLoadHandler(new LoadHandler() {

						@Override
						public void onLoad(LoadEvent event) {
							buildSelector(800, image.getHeight());
						}
					});

				} else
					buildSelector(result.getX(), result.getY());
				if (show) {
					ImageEditor.this.show();
					ImageEditor.this.center();
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void forRotateRight() {
		btnRotateRight.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				FilesDao.imagerotate(new Integer(-90), ClientUtil.getOnlyUri(imgUrl), new StringResponse() {
					@Override
					public void ready(String value) {
						refreshGui(false);
					}
				});
			}
		});
	}

	private void forRotateLeft() {
		btnRotateLeft.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				FilesDao.imagerotate(new Integer(90), ClientUtil.getOnlyUri(imgUrl), new StringResponse() {
					@Override
					public void ready(String value) {
						refreshGui(false);

					}
				});
			}
		});
	}

	private void forBlur() {
		btnBlur.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				FilesDao.imageblur(ClientUtil.getOnlyUri(imgUrl), new StringResponse() {
					@Override
					public void ready(String value) {
						image.setUrl(ClientUtil.getLastUri(imgUrl));
					}
				});
			}
		});
	}

	private void forSharpen() {
		btnSharpen.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				FilesDao.imagesharpen(ClientUtil.getOnlyUri(imgUrl), new StringResponse() {
					@Override
					public void ready(String value) {
						image.setUrl(ClientUtil.getLastUri(imgUrl));
					}
				});
			}
		});
	}

	public void forCropNow() {
		btnCropNow.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (!selector.isVisible()) {
					selector.setVisible(true);
					return;
				}

				int x = leftSelect.getOffsetWidth();
				int y = topSelect.getOffsetHeight();
				int xx = rightSelect.getOffsetWidth();
				int yy = bottomSelect.getOffsetHeight();

				if (smallPercent > 1) {
					x = Math.round(x * smallPercent);
					y = Math.round(y * smallPercent);
					xx = Math.round(xx * smallPercent);
					yy = Math.round(yy * smallPercent);
				}

				FilesDao.imagecrop(x, y, xx, yy, ClientUtil.getOnlyUri(imgUrl), new StringResponse() {
					@Override
					public void ready(String value) {
						refreshGui(false);
						selector.setVisible(false);
					}
				});
			}
		});
	}

	private void buildSelector(int width, int height) {
		leftSelect.getElement().getStyle().setBackgroundColor("#EBEBEB");
		leftSelect.getElement().getStyle().setOpacity(0.6);

		rightSelect.getElement().getStyle().setBackgroundColor("#EBEBEB");
		rightSelect.getElement().getStyle().setOpacity(0.6);

		topSelect.getElement().getStyle().setBackgroundColor("#EBEBEB");
		topSelect.getElement().getStyle().setOpacity(0.6);

		bottomSelect.getElement().getStyle().setBackgroundColor("#EBEBEB");
		bottomSelect.getElement().getStyle().setOpacity(0.6);

		image.getElement().getStyle().setZIndex(0);

		selector.getElement().getStyle().setPosition(Position.ABSOLUTE);
		selector.getElement().getStyle().setTop(148, Unit.PX);
		selector.getElement().getStyle().setLeft(0, Unit.PX);

		selector.getElement().getStyle().setZIndex(5);

		selector.addNorth(topSelect, 50);
		selector.addSouth(bottomSelect, 50);
		selector.addEast(rightSelect, 50);
		selector.addWest(leftSelect, 50);
		selector.setSize(width + "px", height + "px");
		selector.setVisible(false);
	}

	@Override
	public void cancel() {
		caller.changed(imgUrl);
	}

	@Override
	public void ok() {
		caller.changed(imgUrl);
	}

}
