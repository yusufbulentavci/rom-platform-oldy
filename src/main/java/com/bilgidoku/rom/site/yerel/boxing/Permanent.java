package com.bilgidoku.rom.site.yerel.boxing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.CodeEditor;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.RunZone;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.site.yerel.boxer;
import com.bilgidoku.rom.site.yerel.pagedlgs.Spinner;
import com.bilgidoku.rom.site.yerel.writings.ElementToCode;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;

public class Permanent {

	private final Map<String, BoxHolder> boxHolders = new HashMap<String, BoxHolder>();
	public final BoxerData data;
	private BoxerGui gui;

	private HTML html = new HTML();

	public final AbsolutePanel holder = new AbsolutePanel();

	public static final Spinner spinner = new Spinner();

	public Permanent(BoxerData data2) {

		this.data = data2;
		this.data.setCb(new BoxerDataCb() {

			@Override
			public void clearHolders() {
				html.setHTML("");
				for (BoxHolder bh : boxHolders.values()) {
					bh.detach();
				}
				boxHolders.clear();
			}

		});

		this.data.setDisplayTrigger(new Runnable() {
			public void run() {
				String mhtm = data.getHtml();
				html.setHTML(mhtm);

				Scheduler.get().scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {
						spinner.stop();
						htmlReady();

					}
				});
			}
		});

		holder.getElement().getStyle().setOverflow(Overflow.AUTO);

		holder.addHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				Window.alert("Resized");
			}
		}, ResizeEvent.getType());

		html.setSize("100%", "100%");
		holder.setSize("100%", "2000%");

		holder.add(html, 0, 0);
		holder.add(spinner, Window.getClientWidth() / 2, Window.getClientHeight() / 2);

		RomEntryPoint.one.addToRealRootPanel(holder);

		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				if (data == null)
					return;

				data.preview();
			}
		});

	}

	public void setGui(BoxerGui boxerGui) {
		this.gui = boxerGui;
	}

	public void htmlReady() {
		for (BoxHolder it : boxHolders.values()) {
			it.htmlReady();
		}
	}

	public BoxerGuiCb getGuiCb() {
		return new BoxerGuiCb() {

			@Override
			public void save() {

				data.save();
			}

			@Override
			public void pickWidgetFromWidgetList(String widgetName) {
				try {

					final Code wcode = data.getRunner().createWidgetCode(widgetName);
					// wcode.setBox();

					// String title =
					// ClientUtil.getTranslation(wcode.tag.replace("w:", "_"),
					// data.getSelectedTrans());
					String title = boxer.getTranslation(wcode.tag.replace("w:", "_"));

					DlgWidgetEdit dlgEditWidget = new DlgWidgetEdit(boxer.selectedTrans, title);
					dlgEditWidget.center();
					dlgEditWidget.update(wcode, true, new CodeEditCb() {
						@Override
						public void codeState(boolean isChanged) {
							if (!isChanged)
								return;
							try {
								wcode.ensureId();
							} catch (RunException e) {
								e.printStackTrace();
							}
							bhcbone.pushTransfer(wcode);
							RomEntryPoint.one.setCursor(Cursor.CROSSHAIR);
						}
					});
				} catch (RunException e) {
					Sistem.printStackTrace(e);
				}
			}

			@Override
			public void infoChanged() {
				data.infoChanged();
			}

			@Override
			public void preview() {
			}

			@Override
			public void addImageWidget(String uri) {
				try {
					final Code wcode = data.getRunner().createWidgetCode("w:image");
					wcode.setParam("_imageuri", uri);
					wcode.ensureId();
					bhcbone.pushTransfer(wcode);
					RomEntryPoint.one.setCursor(Cursor.CROSSHAIR);

				} catch (RunException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void selectAllBody() {
				for (BoxHolder w : boxHolders.values()) {
					if(w.clmn.equals("body")){
						w.selectAll();
						return;
					}
				}
				
			}

		};
	}

	public static void startSpinner() {
		spinner.start();
	}

	public static void stopSpinner() {
		spinner.stop();
	}

	private BoxHolderCb bhcbone = new BoxHolderCb() {

		private Code transfer = null;

		@Override
		public Code popTransfer() {
			Code ret = transfer;
			transfer = null;
			return ret;
		}

		@Override
		public void pushTransfer(Code code) {
			transfer = code;
		}

		@Override
		public void canvasResize() {
			for (BoxHolder bh : boxHolders.values()) {
				bh.relocate();
			}
		}

		@Override
		public JSONObject getSelectedTrans() {
			return boxer.selectedTrans;
		}

		@Override
		public List<Code> getBoxHtmlCode(String html2) {
			List<Code> newCode = ElementToCode.now(data.getRunner(), boxer.allCodeRepo, html2);
			return newCode;

		}

		@Override
		public void logoChanged(com.bilgidoku.rom.shared.json.JSONObject store) {
		}
	};

	public CodeEditor codeEditor = new CodeEditor() {

		public void addBoxer(RunZone rz, String nid, String lngcode, String uri, String tbl, String symbl, String clmn,
				Code code) {
			com.google.gwt.core.client.Scheduler.get().scheduleFixedDelay( ()-> {
					Object o=com.google.gwt.dom.client.Document.get().getElementById(nid);
					if(o==null)
						return true;

					boxHolders.put(nid, new BoxHolder(bhcbone, rz, holder, nid, lngcode, uri, tbl, symbl, clmn, code));
					return false;
					},
				100);

		}

		public Map<String, BoxHolder> boxHolders() {
			return boxHolders;
		}

		public boolean inPreviewMode() {
			return HtmlPreview.inPreviewMode;
		}

		@Override
		public void htmlReady() {
			htmlReady();
		}

	};

	// public void setSelectedTrans(JSONObject selectedTrans) {
	// this.selectedTrans = selectedTrans;
	// }

}
