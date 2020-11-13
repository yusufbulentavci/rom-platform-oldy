package com.bilgidoku.rom.gwt.client.widgets.colorpicker;

public class RGB
{
    int red;
    int green;
    int blue;



    public RGB ()
    {
    }

    public RGB (int red, int green, int blue)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getBlue ()
    {
        return blue;
    }

    public void setBlue (int blue)
    {
        this.blue = blue;
    }

    public int getGreen ()
    {
        return green;
    }

    public void setGreen (int green)
    {
        this.green = green;
    }

    public int getRed ()
    {
        return red;
    }

    public void setRed (int red)
    {
        this.red = red;
    }
}
