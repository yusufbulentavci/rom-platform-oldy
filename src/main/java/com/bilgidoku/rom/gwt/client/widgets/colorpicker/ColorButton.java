package com.bilgidoku.rom.gwt.client.widgets.colorpicker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;


public class ColorButton extends HTML implements ClickHandler
{
    private int red, green, blue;
    private ColorPanel parentPanel;
    private int positionX;
    private int positionY;


    public ColorButton ()
    {
        super();
    }

    ColorButton (ButtonStyleCommand buttonStyle, ColorPanel parentPanel, int positionX,
            int positionY)
    {
        super();
        this.parentPanel = parentPanel;
        this.positionX = positionX;
        this.positionY = positionY;
        buttonStyle.applyStyle(getElement());
        addClickHandler(this);
    }

    public void setColor (int red, int green, int blue)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.setStyleName("");
        DOM.setStyleAttribute(this.getElement(), "backgroundColor", "#" + getHexValue());
    }

    public void setColor (String hex)
    {
        long longVal = Long.parseLong(hex, 16);
        
        blue = (int) (longVal % 256);
        int rawGreen = (int) (longVal % (256*256) - blue);
        green = rawGreen / 256;
        red = (int) ((longVal - blue - rawGreen) / (256*256));
        
        GWT.log(hex, null);
        DOM.setStyleAttribute(this.getElement(), "backgroundColor", "#" + hex);
    }

    public int getRed ()
    {
        return red;
    }

    public int getGreen ()
    {
        return green;
    }

    public int getBlue ()
    {
        return blue;
    }

    public String getHexValue ()
    {
        return (
                pad(Integer.toHexString(red))
                + pad(Integer.toHexString(green))
                + pad(Integer.toHexString(blue))
        ).toUpperCase();
    }
    
    private String pad (String in)
    {
        if (in.length() == 0) {
            return "00";
        }
        if (in.length() == 1) {
            return "0" + in;
        }
        return in;
    }

    public int getPositionX ()
    {
        return positionX;
    }

    public int getPositionY ()
    {
        return positionY;
    }

    @Override
	public void onClick(ClickEvent event) {
    	parentPanel.triggerClickEvent(this);
	}
	
}
