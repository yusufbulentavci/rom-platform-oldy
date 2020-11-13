package com.bilgidoku.rom.gwt.client.widgets.colorpicker;

import com.google.gwt.user.client.ui.RootPanel;


public class Main// implements EntryPoint
{
    public void onModuleLoad ()
    {
        HSVColorPicker p = new HSVColorPicker();
        RootPanel.get().add(p);
    }
    
}
