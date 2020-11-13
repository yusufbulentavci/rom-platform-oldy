package com.bilgidoku.rom.site.kamu.graph.client.ui;

import com.bilgidoku.rom.site.kamu.graph.client.EditState;
import com.bilgidoku.rom.site.kamu.graph.client.GraphicEditor;
import com.bilgidoku.rom.site.kamu.graph.client.ImgUtil;
import com.bilgidoku.rom.site.kamu.graph.client.change.Back;
import com.bilgidoku.rom.site.kamu.graph.client.change.Change;
import com.bilgidoku.rom.site.kamu.graph.client.change.CreateBezier;
import com.bilgidoku.rom.site.kamu.graph.client.change.CreateImg;
import com.bilgidoku.rom.site.kamu.graph.client.change.CreateLine;
import com.bilgidoku.rom.site.kamu.graph.client.change.MoveCp1;
import com.bilgidoku.rom.site.kamu.graph.client.change.MoveCp2;
import com.bilgidoku.rom.site.kamu.graph.client.change.MoveDp;
import com.bilgidoku.rom.site.kamu.graph.client.change.RmElem;
import com.bilgidoku.rom.site.kamu.graph.client.change.Rotate;
import com.bilgidoku.rom.site.kamu.graph.client.constants.GraphConstants;
import com.bilgidoku.rom.site.kamu.graph.client.db.Elem;
import com.bilgidoku.rom.site.kamu.graph.client.draw.EditElemUi;
import com.bilgidoku.rom.site.kamu.graph.client.images.Images;
import com.bilgidoku.rom.site.kamu.graph.client.ui.dlgs.DlgBrowseImages;
import com.bilgidoku.rom.site.kamu.graph.client.ui.dlgs.DlgImageSearch;
import com.bilgidoku.rom.site.kamu.graph.client.ui.dlgs.DlgTextForm;
import com.bilgidoku.rom.site.kamu.graph.client.ui.dlgs.DlgUploadForm;
import com.bilgidoku.rom.site.kamu.graph.client.ui.forms.ImageEditor;
import com.bilgidoku.rom.site.kamu.graph.client.ui.forms.TextEditor;
import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.util.common.SiteButton;
import com.bilgidoku.rom.min.geo.Point;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class ChangeHandling implements ChangeCallback {

	// public static StatusBar status = StatusBar.getOne();

	private final static GraphConstants trans = GWT.create(GraphConstants.class);

	private final Images images = GWT.create(Images.class);
	private final Button line = new Button(ClientUtil.imageItemHTML(images.new_line(), "New line"));
	private final Button bezier = new Button(ClientUtil.imageItemHTML(images.new_bezier(), "New curve"));
	private final SiteButton btnText = new SiteButton(images.text(), trans.addText(), "", "big");
	private final SiteButton btnImage = new SiteButton(images.star(), trans.addImage(), "", "big");
	private final SiteButton btnUpload = new SiteButton(images.upload(), trans.uploadImage(), "", "big");
	private final SiteButton btnSearch = new SiteButton(images.search_(), trans.imageSearch(), "", "big");
	private final SiteButton btnZoom = new SiteButton(images.zoom_in(), trans.zoomIn(), "", "big");

	private final Button[] createButtons = { btnText, btnImage, btnUpload, btnSearch, btnZoom };

	private final EditElemUi editElemUi = new EditElemUi();

	private final Button[] transforms = { editElemUi.back, editElemUi.del, editElemUi.edit, line, bezier,
			editElemUi.cp1, editElemUi.cp2};

	private final Button[] changeButtons = { editElemUi.back, editElemUi.del, editElemUi.edit, editElemUi.cp1,
			editElemUi.cp2, };

	private final SimplePanel formPanel = new SimplePanel();

	private Elem curElem;

	private GraphicEditor caller;

	private Point editLocation;

	public ChangeHandling(GraphicEditor ge) {
		this.caller = ge;
		this.editLocation=ge.db.getEditLocation();
		windUp(GraphicEditor.editState);
	}

	private void windUp(final EditState ge) {
		for (ButtonBase button : transforms) {
			button.addStyleName("btn-sml");
			button.addMouseDownHandler(new MouseDownHandler() {
				@Override
				public void onMouseDown(MouseDownEvent event) {
					newTransform((Button) event.getSource(), null);
				}
			});
			button.addTouchStartHandler(new TouchStartHandler() {

				@Override
				public void onTouchStart(TouchStartEvent event) {
					Touch t = event.getTouches().get(0);
					newTransform((Button) event.getSource(), null);
				}
			});
		}

		forLine();
		forText();
		forImages();
		forImageUpload();
		forImageSearch();
		forZoom();
	}

	private boolean zoomed = false;

	private void forZoom() {
		btnZoom.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (zoomed) {
					zoomed = false;
					btnZoom.setHTML(SiteButton.imageItemHTML(images.zoom_in(), trans.zoomIn()));
					GraphicEditor.viewing.setViewScale(1);
					GraphicEditor.drawer.scheduleRedraw();
				} else {
					zoomed = true;
					btnZoom.setHTML(SiteButton.imageItemHTML(images.zoom_out(), trans.zoomOut()));
					GraphicEditor.viewing.setViewScale(2);
					GraphicEditor.drawer.scheduleRedraw();
				}
			}
		});

	}

	private void forLine() {
		line.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				newTransform((Button) event.getSource(), null);
			}
		});
	}

	private void forText() {
		btnText.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				noForm();
				new DlgTextForm(ChangeHandling.this);
			}
		});

	}

	protected void selectButton(SiteButton text2) {
		for (int i = 0; i < createButtons.length; i++) {
			Button b = createButtons[i];
			b.removeStyleName("selected-btn");
		}
		if (text2 != null)
			text2.addStyleName("selected-btn");
	}

	private void forImageUpload() {
		btnUpload.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				noForm();
				new DlgUploadForm(ChangeHandling.this, GraphicEditor.userDir);

			}
		});

	}

	private void forImages() {
		btnImage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				noForm();
				new DlgBrowseImages(ChangeHandling.this, GraphicEditor.clipArtDir, GraphicEditor.userDir);
			}
		});
	}

	private void forImageSearch() {
		btnSearch.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				noForm();
				new DlgImageSearch(ChangeHandling.this, GraphicEditor.userDir);
			}
		});
	}

	public Button[] getCreateButtons() {
		return this.createButtons;
	}

	public void show(Elem cur) {
		this.curElem = cur;
		GraphicEditor.viewing.showButtons(editElemUi, cur);
	}

	public void hide() {
		for (ButtonBase b : changeButtons) {
			b.setVisible(false);
		}
		this.curElem = null;
	}

	protected void newTransform(Object source, Change change2) {
		newTransform(source, change2, false);
	}
	
	protected void newTransform(Object source, Change change2, boolean oneStp){
	
		Change change = change2;
		boolean oneStep = oneStp;

		if (change == null) {
			if (source == btnImage) {
				String url = "http://www.cizgiy.com/f/images/path4140.png";
				change = new CreateImg(url, 0, 0, 150, 100);
			} else if (source == line) {
				change = new CreateLine(this.curElem, 0, 0);
			} else if (source == bezier) {
				change = new CreateBezier(this.curElem, 0, 0, 0, 0, 0, 0);
			}

			if (curElem == null)
				return;

			if (source == editElemUi.del) {
				change = new RmElem(curElem);
				GraphicEditor.editState.unselect();
				oneStep = true;
			} else if (source == editElemUi.back) {
				change = new Back(curElem);
				GraphicEditor.editState.redraw();
				oneStep = true;
			} else if (source == editElemUi.edit) {
				if (curElem.getType() == 'i') {
					final String imgUri = curElem.getImg();
					if (imgUri != null) {
						new ImageEditor(new SimpleChangeListener() {
							@Override
							public void changed(Composite source, Integer oldone, Integer newone) {
							}

							@Override
							public void changed(String source) {
								curElem.reloadImg(source);
								GraphicEditor.db.commit();
								GraphicEditor.drawer.scheduleRedraw();
							}
						}, imgUri);
					}
				} else if (curElem.getType() == 'r') {
					TextEditor te = new TextEditor(new ChangeCallback() {
						@Override
						public void textChanged(Change change) {
							if (change != null)
								GraphicEditor.editState.change(change);
						}

						@Override
						public void newImage(String imgUri) {
						}

						@Override
						public void imageChanged(String imgUrl) {
						}

						@Override
						public void setStatus(String text) {

						}
					}, curElem);

					te.addCloseHandler(new CloseHandler<PopupPanel>() {
						@Override
						public void onClose(CloseEvent<PopupPanel> event) {
							GraphicEditor.editState.unselect();
						}
					});
				}
				oneStep = true;
			} else if (source == editElemUi.cp1) {
				change = new MoveCp1(this.curElem, 0, 0);
			} else if (source == editElemUi.cp2) {
				change = new MoveCp2(this.curElem, 0, 0);
			}
		}

		if (change == null)
			return;

		if (!change.isTransform()) {
			// String fc = fillColor.getValue();
			// if (fc != null && fc.length() > 0) {
			// change.setFillColor(fc);
			// }
			// String sc = strokeColor.getValue();
			// if (fc != null && sc.length() > 0) {
			// change.setStrokeColor(sc);
			// }
			// Integer lw = strokeWidth.getValue();
			// if (lw != null) {
			// change.setStrokeWidth(lw);
			// }
			// String sc = "#dedede";
			// if (sc != null && sc.length() > 0) {
			//// change.setFillColor("yellow");
			// change.setStrokeColor("#a0a0a0");
			//
			// }
			// Integer lw = 3;
			// if (lw != null) {
			// change.setStrokeWidth(lw);
			// }

		}
		if (oneStep) {
			GraphicEditor.editState.change(change);
		} else {
			GraphicEditor.editState.toChanging(change);
		}
	}

	public ButtonBase[] getButtons() {
		return changeButtons;
	}

	public SimplePanel getFormPanel() {
		return formPanel;
	}

	@Override
	public void textChanged(Change change) {
		newTransform(btnText, change, true);
		GraphicEditor.editState.showGetInput();
	}

	@Override
	public void newImage(String imgUri) {
		final String url = imgUri;
		caller.setStatus("Yükleniyor...");
		ImgUtil.size(imgUri, new AsyncCallback<Point>() {
			@Override
			public void onSuccess(Point result) {

//				if (GraphicEditor.type.equals(GraphicEditor.A3))
//				 	result = ImgUtil.fitA3(result);
//				else				
//					result = ImgUtil.fitCup(result);
				
				result=ImgUtil.fit(GraphicEditor.db.getEditSize(), result);
				

				Change change = new CreateImg(url, editLocation.getX(), editLocation.getY(), result.getX(), result.getY());
				newTransform(null, change, true);

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void imageChanged(String imgUrl) {
	}

	public void noForm() {
		selectButton(btnText);
		formPanel.setVisible(false);
		formPanel.clear();
	}

	@Override
	public void setStatus(String text) {
		caller.setStatus(text);
	}

}
