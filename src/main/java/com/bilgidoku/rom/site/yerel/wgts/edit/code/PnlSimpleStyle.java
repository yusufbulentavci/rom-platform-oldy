package com.bilgidoku.rom.site.yerel.wgts.edit.code;

import java.util.Map;

import com.bilgidoku.rom.gwt.client.util.common.ClientUtil;
import com.bilgidoku.rom.gwt.client.widgets.InputChanged;
import com.bilgidoku.rom.gwt.client.widgets.InputChangedHandler;
import com.bilgidoku.rom.gwt.client.widgets.PasteEvent;
import com.bilgidoku.rom.gwt.client.widgets.PasteHandler;
import com.bilgidoku.rom.gwt.client.widgets.colorpicker.ColorTextBox;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.bilgidoku.rom.site.yerel.wgts.edit.input.types.PxContextBox;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;

public class PnlSimpleStyle extends Composite {

	private PxContextBox tbBorderWidth = new PxContextBox("bw", null, 51);

	private final ColorTextBox tbBorderColor = new ColorTextBox("#000000");
	private final ColorTextBox tbFontColor = new ColorTextBox("#000000");
	private final ColorTextBox tbBackClr = new ColorTextBox("#FFFFFF");
	private final ListBox lbBorderStyle = new ListBox(); 

	private NodePnlStyle parent;

	private final PxContextBox tbBorderRad1 = new PxContextBox("br1", null, 501);
	private final PxContextBox tbBorderRad2 = new PxContextBox("br2", null, 501);
	private final PxContextBox tbBorderRad3 = new PxContextBox("br3", null, 501);
	private final PxContextBox tbBorderRad4 = new PxContextBox("br4", null, 501);
	
	private final PxContextBox tbPad1 = new PxContextBox("pa1", null, 51);
	private final PxContextBox tbPad2 = new PxContextBox("pa2", null, 51);
	private final PxContextBox tbPad3 = new PxContextBox("pa3", null, 51);
	private final PxContextBox tbPad4 = new PxContextBox("pa4", null, 51);

	private FlexTable holder = new FlexTable();

	public PnlSimpleStyle(NodePnlStyle parent1) {

		this.parent = parent1;
		loadBorderStyles();
//		box1 = new HTML(SPACETEMPLATE.box(getPaddingStyles(), "0", "0", "0", "0"));
//		box1 = new HTML(SPACETEMPLATE.box(getPaddingStyles()));

		events();

		tbBorderColor.setWidth("65px");
		tbFontColor.setWidth("65px");
		tbBackClr.setWidth("65px");

		tbBorderColor.setBoxHeight("12px");
		tbBackClr.setBoxHeight("12px");

		tbBorderColor.setTitle(Ctrl.trans.border());
		tbBackClr.setTitle(Ctrl.trans.background());

		FlexTable ftBorderRad = new FlexTable();
		ftBorderRad.setCellSpacing(7);
		ftBorderRad.setWidget(0, 0, tbBorderRad1);
		ftBorderRad.setWidget(0, 1, tbBorderRad2);
		ftBorderRad.setWidget(1, 0, tbBorderRad3);
		ftBorderRad.setWidget(1, 1, tbBorderRad4);

		FlexTable ftPadding = new FlexTable();
		ftPadding.setWidget(0, 1, tbPad1);
		ftPadding.setWidget(1, 2, tbPad2);
		ftPadding.setWidget(2, 1, tbPad3);
		ftPadding.setWidget(1, 0, tbPad4);

		holder.setCellPadding(5);
		holder.setHTML(0, 0, "Background Color");
		holder.setWidget(0, 1, tbBackClr);

		holder.setHTML(1, 0, "Font Color");
		holder.setWidget(1, 1, tbFontColor);

		holder.setHTML(2, 0, "Border Width");
		holder.setWidget(2, 1, tbBorderWidth);

		holder.setHTML(3, 0, "Border Style");
		holder.setWidget(3, 1, lbBorderStyle);

		holder.setHTML(4, 0, "Border Color");
		holder.setWidget(4, 1, tbBorderColor);

		holder.setHTML(5, 0, "Corner Style");
		holder.setWidget(5, 1, ftBorderRad);

		holder.setHTML(6, 0, "Space In");
		holder.setWidget(6, 1, ftPadding);

		CaptionPanel capSimpleFrom = new CaptionPanel(Ctrl.trans.add(Ctrl.trans.style()));
		capSimpleFrom.add(holder);

		initWidget(capSimpleFrom);

	}

	private void loadBorderStyles() {
		lbBorderStyle.addItem("No Border", "none");
		lbBorderStyle.addItem("solid", "solid");
		lbBorderStyle.addItem("dotted", "dotted");
		lbBorderStyle.addItem("dashed", "dashed");
		lbBorderStyle.addItem("groove", "groove");
		lbBorderStyle.addItem("double", "double");
		lbBorderStyle.addItem("ridge", "ridge");
		lbBorderStyle.addItem("inset", "inset");
		lbBorderStyle.addItem("outset", "outset");
	}

	private void events() {
		InputChangedHandler ic = new InputChangedHandler() {
			@Override
			public void changed(InputChanged event) {
				parent.dataChanged("border-radius", tbBorderRad1.getPxValue() + " " + tbBorderRad2.getPxValue()
						+ " " + tbBorderRad3.getPxValue() + " " + tbBorderRad4.getPxValue());

			}
		};

		InputChangedHandler ic1 = new InputChangedHandler() {
			@Override
			public void changed(InputChanged event) {
				tbBorderRad2.setPxValue(tbBorderRad1.getPxValue());
				tbBorderRad3.setPxValue(tbBorderRad1.getPxValue());
				tbBorderRad4.setPxValue(tbBorderRad1.getPxValue());
				parent.dataChanged("border-radius", tbBorderRad1.getPxValue() + " " + tbBorderRad2.getPxValue()
						+ " " + tbBorderRad3.getPxValue() + " " + tbBorderRad4.getPxValue());

			}
		};

		tbBorderRad1.addInputChangedHandler(ic1);
		tbBorderRad2.addInputChangedHandler(ic);
		tbBorderRad3.addInputChangedHandler(ic);
		tbBorderRad4.addInputChangedHandler(ic);

		InputChangedHandler icBw = new InputChangedHandler() {
			@Override
			public void changed(InputChanged event) {
				parent.dataChanged("border-width", tbBorderWidth.getPxValue());
			}
		};
		tbBorderWidth.addInputChangedHandler(icBw);

		tbBorderColor.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				parent.dataChanged("border-color", tbBorderColor.getValue());

			}
		});
		tbBorderColor.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				parent.dataChanged("border-color", tbBorderColor.getValue());
			}
		}, PasteEvent.TYPE);

		tbBackClr.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				parent.dataChanged("background-color", tbBackClr.getValue());

			}
		});

		tbBackClr.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				parent.dataChanged("background-color", tbBackClr.getValue());
			}
		}, PasteEvent.TYPE);

		
		tbFontColor.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				parent.dataChanged("color", tbFontColor.getValue());

			}
		});
		tbFontColor.addHandler(new PasteHandler() {
			@Override
			public void paste(PasteEvent event) {
				parent.dataChanged("color", tbFontColor.getValue());
			}
		}, PasteEvent.TYPE);

		InputChangedHandler pc = new InputChangedHandler() {
			@Override
			public void changed(InputChanged event) {
				setCodePaddings();

			}
		};

		InputChangedHandler pc1 = new InputChangedHandler() {
			@Override
			public void changed(InputChanged event) {
				tbPad2.setPxValue(tbPad1.getPxValue());
				tbPad3.setPxValue(tbPad1.getPxValue());
				tbPad4.setPxValue(tbPad1.getPxValue());
				setCodePaddings();
			}
		};

		tbPad1.addInputChangedHandler(pc1);
		tbPad2.addInputChangedHandler(pc);
		tbPad3.addInputChangedHandler(pc);
		tbPad4.addInputChangedHandler(pc);
		
		lbBorderStyle.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				parent.dataChanged("border-style", lbBorderStyle.getValue(lbBorderStyle.getSelectedIndex()));
				
			}
		});

	}

	protected void setCodePaddings() {
		parent.dataChanged("padding-top", tbPad1.getPxValue());
		parent.dataChanged("padding-right", tbPad2.getPxValue());
		parent.dataChanged("padding-bottom", tbPad3.getPxValue());
		parent.dataChanged("padding-left", tbPad4.getPxValue());
	}

	public void resetForm() {
		tbBorderRad1.setPxValue("0px");
		tbBorderRad2.setPxValue("0px");
		tbBorderRad3.setPxValue("0px");
		tbBorderRad4.setPxValue("0px");

		tbPad1.setPxValue("0px");
		tbPad2.setPxValue("0px");
		tbPad3.setPxValue("0px");
		tbPad4.setPxValue("0px");

		tbBorderColor.setHexValue("#000000");
		tbBackClr.setHexValue("#FFFFFF");
		tbBorderWidth.setPxValue("0px");
	}

	public void showSimpleData() {

		Map<String, String> styles = parent.getCode().getStyleByType("defaultstyle");
		
		if (styles == null)
			return;
		
		if (styles.get("border-width") != null) {
			tbBorderWidth.setPxValue(styles.get("border-width"));
		}

		if (styles.get("padding") != null) {
			String mr = styles.get("padding");
			String[] vals = mr.split(" ");
			if (vals.length == 1) {
				loadPaddingValues(vals[0], vals[0], vals[0], vals[0]);
			} else if (vals.length == 2) {
				loadPaddingValues(vals[0], vals[1], vals[0], vals[1]);
			} else if (vals.length == 3) {
				loadPaddingValues(vals[0], vals[1], vals[0], vals[2]);
			} else if (vals.length == 4) {
				loadPaddingValues(vals[0], vals[1], vals[2], vals[3]);
			}
		} 
		
		if (styles.get("padding-top") != null) {
			String mr = styles.get("padding-top");
			tbPad1.setPxValue(mr);
		}
		if (styles.get("padding-right") != null) {
			String mr = styles.get("padding-right");
			tbPad2.setPxValue(mr);
		}

		if (styles.get("padding-bottom") != null) {
			String mr = styles.get("padding-bottom");
			tbPad3.setPxValue(mr);
		}

		if (styles.get("padding-left") != null) {
			String mr = styles.get("padding-left");
			tbPad4.setPxValue(mr);
		}

		if (styles.get("background-color") != null)
			tbBackClr.setHexValue(styles.get("background-color"));

		if (styles.get("border-color") != null)
			tbBorderColor.setHexValue(styles.get("border-color"));

		if (styles.get("border-style") != null)
			lbBorderStyle.setSelectedIndex(ClientUtil.findIndex(lbBorderStyle, styles.get("border-style")));
		
		if (styles.get("border-radius") != null) {
			String br = styles.get("border-radius");
			String[] vals = br.split(" ");
			if (vals.length == 1) {
				setRadiusValues(vals[0], vals[0], vals[0], vals[0]);
			} else if (vals.length == 2) {
				setRadiusValues(vals[0], vals[1], vals[0], vals[1]);
			} else if (vals.length == 3) {
				setRadiusValues(vals[0], vals[1], vals[0], vals[2]);
			} else if (vals.length == 4) {
				setRadiusValues(vals[0], vals[1], vals[2], vals[3]);
			}
		}
	}

	private void loadPaddingValues(String s1, String s2, String s3, String s4) {
		tbPad1.setPxValue(s1);
		tbPad2.setPxValue(s2);
		tbPad3.setPxValue(s3);
		tbPad4.setPxValue(s4);
	}

	private void setRadiusValues(String s1, String s2, String s3, String s4) {
		tbBorderRad1.setPxValue(s1);
		tbBorderRad2.setPxValue(s2);
		tbBorderRad3.setPxValue(s3);
		tbBorderRad4.setPxValue(s4);
	}

}
