package com.bilgidoku.rom.gwt.client.widgets.colorpicker;

import java.util.EventObject;


public class ColorSelectedEvent extends EventObject
{
    private static final long serialVersionUID = 9154054520914299238L;
    private int red;
    private int green;
    private int blue;
    private String hexValue;
    private int position;
    private int positionX;
    private int positionY;
    private int maxPositionX;
    private int maxPositionY;


    public ColorSelectedEvent (Object source)
    {
        super(source);
    }

    void setBlue (int blue)
    {
        this.blue = blue;
    }

    void setGreen (int green)
    {
        this.green = green;
    }

    void setHexValue (String hexValue)
    {
        this.hexValue = hexValue;
    }

    void setPositionX (int positionX)
    {
        this.positionX = positionX;
    }

    void setPositionY (int positionY)
    {
        this.positionY = positionY;
    }

    void setPosition (int position)
    {
        this.position = position;
    }

    void setRed (int red)
    {
        this.red = red;
    }

    void setMaxPositionX (int maxPositionX)
    {
        this.maxPositionX = maxPositionX;
    }

    void setMaxPositionY (int maxPositionY)
    {
        this.maxPositionY = maxPositionY;
    }

    
    public int getBlue ()
    {
        return blue;
    }

    public int getGreen ()
    {
        return green;
    }

    public String getHexValue ()
    {
        return hexValue;
    }

    /**
     * Cols from left, starting at 1
     */
    public int getPositionX ()
    {
        return positionX;
    }

    /**
     * Rows from top, starting at 1
     */
    public int getPositionY ()
    {
        return positionY;
    }

    public int getRed ()
    {
        return red;
    }

    /**
     * Cell number that was clicked, starting with
     * (1) in the top-left corner, then incrementing
     * accross and down.
     */
    public int getPosition ()
    {
        return position;
    }

    public int getMaxPositionX ()
    {
        return maxPositionX;
    }

    public int getMaxPositionY ()
    {
        return maxPositionY;
    }

}
