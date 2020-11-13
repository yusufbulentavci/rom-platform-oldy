package com.bilgidoku.rom.gwt.client.widgets.colorpicker;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.TextBox;

class DataBox extends TextBox
{
    public DataBox ()
    {
        super();
        setWidth("40px");
        setHeight("16px");
        this.getElement().getStyle().setFontSize(10, Unit.PX);
    }
}