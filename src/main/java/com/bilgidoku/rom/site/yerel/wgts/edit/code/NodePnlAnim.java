package com.bilgidoku.rom.site.yerel.wgts.edit.code;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.shared.code.Animation;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.tags.HtmlTagMap;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class NodePnlAnim extends Composite {

	public Code code;

	public boolean changed = false;

	private final TextBox tbDuration = new TextBox();
	private final TextBox tbDelay = new TextBox();
	private final TextBox tbIterationCount = new TextBox();
	private final TextBox tbTimingFunction = new TextBox();
	private final TextBox tbDirection = new TextBox();
	private final VerticalPanel frameRows = new VerticalPanel();
	private final ListBox listAnim = new ListBox();

	private final FlexTable pnlMore = new FlexTable();
	private final Button btnMore = new Button("More");

	private JSONObject readyAnimations;

	public NodePnlAnim() {

		tbDuration.setWidth("50px");
		tbDelay.setWidth("50px");
		tbIterationCount.setWidth("50px");

		forDurationChange();
		forDelayChange();
		forIterChange();
		forDirectionChange();
		forTimingChange();
		forListSelect();
		forMore();

		FlexTable pnlSimple = new FlexTable();
		pnlSimple.setStyleName("site-innerform");

		pnlSimple.setHTML(0, 0, Ctrl.trans.duration());
		pnlSimple.setWidget(0, 1, tbDuration);

		pnlSimple.setHTML(1, 0, Ctrl.trans.delay());
		pnlSimple.setWidget(1, 1, tbDelay);

		pnlSimple.setHTML(2, 0, Ctrl.trans.iteration());
		pnlSimple.setWidget(2, 1, tbIterationCount);

		pnlMore.setStyleName("site-innerform");
		pnlMore.setHTML(0, 0, Ctrl.trans.timingFunction());
		pnlMore.setWidget(0, 1, tbTimingFunction);

		pnlMore.setHTML(1, 0, Ctrl.trans.direction());
		pnlMore.setWidget(1, 1, tbDirection);

		pnlMore.setWidget(2, 1, addKeyForm());

		pnlMore.setWidget(3, 1, frameRows);

		pnlMore.setVisible(false);

		Anchor anims = new Anchor(Ctrl.trans.seeInAction());
		anims.addClickHandler(new ClickHandler() {			
			@Override
			public void onClick(ClickEvent event) {
				Window.open("http://daneden.github.io/animate.css/", "_blank", "");
			}
		});
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(listAnim);
		hp.add(anims);
		
		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(6);
		vp.add(hp);
		vp.add(pnlSimple);
		vp.add(btnMore);
		vp.add(pnlMore);

		initWidget(vp);
	}

	private void forMore() {
		btnMore.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (pnlMore.isVisible()) {
					pnlMore.setVisible(false);
					btnMore.setText(Ctrl.trans.more());
				} else {
					pnlMore.setVisible(true);
					btnMore.setText(Ctrl.trans.less());
				}

			}
		});

	}

	private void forListSelect() {
		listAnim.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				String key = listAnim.getSelectedValue();
				resetAll();
				changed = true;
				if (key == null || key.isEmpty()) {
					code.setAnimation(null);					
					return;
				}
				
				
				Animation animation = new Animation();
				code.setAnimation(animation);
				animation.inspire = key;
				animation.duration = 3000;
				animation.delay = 0;
				animation.iterationCount = 1;

				tbDuration.setValue("3");
				tbDelay.setValue("0");
				tbIterationCount.setValue("1");

				JSONObject jo = readyAnimations.get(key).isObject();
				loadReadyAnimFrames(jo);

			}
		});

	}

	protected void loadReadyAnimFrames(JSONObject jo) {

		JSONObject objFrames = jo.get("f").isObject();
		for (Iterator<String> iterator = objFrames.keySet().iterator(); iterator.hasNext();) {

			final String key = (String) iterator.next();
			String frmKey = key;
			JSONObject objFrame = objFrames.get(key).isObject();

			Map<String, String> map = new HashMap<String, String>();
			for (Iterator<String> iterator2 = objFrame.keySet().iterator(); iterator2.hasNext();) {
				String keyStyle = (String) iterator2.next();

				map.put(keyStyle, ClientUtil.getString(objFrame.get(keyStyle)));
			}
			code.getAnimation().keyFrames.put(frmKey, map);
		}

		loadKeyFrames();

	}

	private void forTimingChange() {
		tbTimingFunction.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				code.getAnimation().timingFunction = tbTimingFunction.getValue();
				changed = true;
			}
		});

	}

	private void forDirectionChange() {
		tbDirection.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if (code == null)
					return;
				code.getAnimation().direction = tbDirection.getValue();
				changed = true;
			}
		});

	}

	private void forIterChange() {
		tbIterationCount.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				try {
					code.getAnimation().iterationCount = Integer.parseInt(tbIterationCount.getValue());
					changed = true;
				} catch (Exception e) {
					Window.alert(e.getMessage());
				}

			}
		});

	}

	private void forDelayChange() {
		tbDelay.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				try {
					code.getAnimation().delay = Integer.parseInt(tbDelay.getValue());
					changed = true;
				} catch (Exception e) {
					Window.alert(e.getMessage());
				}

			}
		});

	}

	private void forDurationChange() {
		tbDuration.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				try {
					code.getAnimation().duration = Integer.parseInt(tbDuration.getValue());
					changed = true;
				} catch (Exception e) {
					Window.alert(e.getMessage());
				}

			}
		});
	}

	private Widget addKeyForm() {
		final TextBox tbKey = new TextBox();

		Button add = new Button("add");
		add.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String newKey = tbKey.getValue();
				Map<String, String> map = new HashMap<String, String>();
				code.getAnimation().keyFrames.put(newKey, map);
				loadKeyFrames();
				changed = true;

			}
		});

		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.add(new Label("Add Frame (0-100):"));
		hp.add(tbKey);
		hp.add(add);
		return hp;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public void show() {

		if (code.tag.startsWith("c:"))
			return;

		if (code == null)
			return;

		resetAll();
		loadReadyAnimList();
		
		Animation anim = code.getAnimation();
		if (anim == null) {
			return;
		}

		tbDuration.setValue((anim.duration) + "");
		tbDelay.setValue(anim.delay != null ? Integer.toString(anim.delay) : "");
		tbIterationCount.setValue(anim.iterationCount != null ? Integer.toString(anim.iterationCount) : "");
		tbTimingFunction.setValue(anim.timingFunction);
		tbDirection.setValue(anim.direction);
		loadKeyFrames();
		listAnim.setSelectedIndex(ClientUtil.findIndex(listAnim, anim.inspire));

	}

	private void loadReadyAnimList() {
		listAnim.addItem("-----No Animation----", "");
		for (Iterator<String> iterator = readyAnimations.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			listAnim.addItem(key, key);
		}
	}

	private void loadKeyFrames() {

		frameRows.clear();

		Animation anim = code.getAnimation();

		if (anim == null || anim.keyFrames == null || anim.keyFrames.size() <= 0) {
			return;
		}

		frameRows.add(ClientUtil.getHeader("Frameler Listesi:", null));

		for (Iterator<String> iterator = anim.keyFrames.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			addFrameRow(key, anim.keyFrames.get(key));
		}

	}

	private void resetAll() {
		tbDuration.setValue("");
		tbDelay.setValue("");
		tbIterationCount.setValue("");
		tbTimingFunction.setValue("");
		tbDirection.setValue("");
		frameRows.clear();
		// listAnim.clear();

	}

	private void addFrameRow(final String key, final Map<String, String> mapkey) {

		final VerticalPanel hp = new VerticalPanel();

		final TextBox tbKey = new TextBox();
		tbKey.setValue(key);

		Button change = new Button("change");
		change.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String newKey = tbKey.getValue();
				changeKey(key, newKey);
				changed = true;

			}
		});

		Button del = new Button("remove this frame");
		change.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String key = tbKey.getValue();
				code.getAnimation().keyFrames.put(key, null);
				changed = true;
				hp.getElement().removeFromParent();
				// hp.removeFromParent();
			}
		});

		VerticalPanel rowStyles = new VerticalPanel();

		Widget addStyleForm = getAddStyleForm(rowStyles, key);
		// addStyleForm.setStyleName("site-holder");
		// rowStyles.setStyleName("site-holder");

		HorizontalPanel h = new HorizontalPanel();
		h.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		h.add(new Label("Frame :"));
		h.add(tbKey);
		h.add(change);
		h.add(del);

		hp.setStyleName("site-innerform");
		hp.add(h);
		hp.add(addStyleForm);
		hp.add(rowStyles);

		populateStyles(rowStyles, key, mapkey);

		frameRows.add(hp);

	}

	protected void changeKey(String key, String newKey) {
		Animation animation = code.getAnimation();
		Map<String, String> map = animation.keyFrames.get(key);
		animation.keyFrames.put(newKey, map);
		animation.keyFrames.put(key, null);
	}

	private void loadOracle(MultiWordSuggestOracle oracleStyle) {
		for (String key : HtmlTagMap.one().getStyleMap().keySet()) {
			oracleStyle.add(key);
		}
	}

	private Widget getAddStyleForm(final VerticalPanel rowStyles, final String key) {

		final MultiWordSuggestOracle oracleStyle = new MultiWordSuggestOracle();
		final SuggestBox styleSuggest = new SuggestBox(oracleStyle);
		final TextBox styleText = new TextBox();

		loadOracle(oracleStyle);

		Button add = new Button("Add");
		add.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				addStyle(key, styleSuggest.getValue(), styleText.getValue());

				addStyleRow(rowStyles, key, styleSuggest.getValue(), styleText.getValue());

			}
		});

		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hp.setSpacing(5);
		hp.add(new Label(Ctrl.trans.add(Ctrl.trans.style()) + ":"));
		hp.add(styleSuggest);
		hp.add(new Label("   " + Ctrl.trans.value() + ":"));
		hp.add(styleText);
		hp.add(add);

		return hp;
	}

	protected void addStyle(String key, String style, String value2) {
		Map<String, String> map = code.getAnimation().keyFrames.get(key);
		if (map == null)
			map = new HashMap<String, String>();

		map.put(style, value2);

	}

	protected void addStyleRow(VerticalPanel rowStyles, final String key, final String style, String value) {

		final TextBox tb = new TextBox();
		tb.setValue(value);
		tb.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				styleChanged(key, style, tb.getValue());
			}
		});

		Label lbl = new Label(style);
		lbl.setWidth("120px");
		tb.setWidth("120px");

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(lbl);
		hp.add(tb);
		hp.add(getDelButton(key, style, hp, rowStyles));

		rowStyles.add(hp);

	}

	protected void styleChanged(String key, String style, String value) {
		code.getAnimation().keyFrames.get(key).put(style, value);
	}

	private void populateStyles(VerticalPanel rowStyles, final String key, Map<String, String> mapkey) {
		if (mapkey == null)
			return;

		for (Iterator<String> iterator = mapkey.keySet().iterator(); iterator.hasNext();) {
			final String styleKey = (String) iterator.next();

			final TextBox tb = new TextBox();
			tb.setValue(mapkey.get(styleKey));
			tb.addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					styleChanged(key, styleKey, tb.getValue());
				}
			});

			Label lbl = new Label(styleKey);
			lbl.setWidth("120px");
			tb.setWidth("120px");

			HorizontalPanel hp = new HorizontalPanel();
			hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			hp.add(lbl);
			hp.add(tb);
			hp.add(getDelButton(key, styleKey, hp, rowStyles));

			rowStyles.add(hp);

		}
	}

	private Widget getDelButton(final String key, final String styleKey, final HorizontalPanel hp,
			final VerticalPanel ft) {
		Button del = new Button("X");
		del.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				delStylefromKeyFrames(key, styleKey);
				ft.remove(hp);
			}
		});

		return del;
	}

	private void delStylefromKeyFrames(String key, String styleKey) {
		Animation anim = code.getAnimation();
		if (anim == null)
			return;
		
		if (anim.keyFrames == null)
			return;

		Map<String, String> map = anim.keyFrames.get(key);
		map.remove(styleKey);

		// TODO test it??
		anim.keyFrames.put(key, map);

	}

	public void setAnimations(JSONObject animations) {
		this.readyAnimations = animations;
	}

}
