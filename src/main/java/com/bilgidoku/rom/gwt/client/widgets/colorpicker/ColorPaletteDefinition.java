package com.bilgidoku.rom.gwt.client.widgets.colorpicker;


public interface ColorPaletteDefinition
{
    int getWidth ();

    int getHeight ();

    int getDefaultSelected ();

    String[] getHexColorValues ();

    RGB[] getRGBVlues ();
}
