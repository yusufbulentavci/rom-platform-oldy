package com.bilgidoku.rom.gwt.client.widgets.colorpicker;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;


public class ButtonStyleCommand implements StyleCommand
{
    private String width;
    private String height;
    private String padding;
    private String margin;
    private String border;



    public void applyStyle (Element element)
    {
        if (width != null)
            DOM.setStyleAttribute(element, "width", width);
        if (height != null)
            DOM.setStyleAttribute(element, "height", height);
        if (padding != null)
            DOM.setStyleAttribute(element, "padding", padding);
        if (margin != null)
            DOM.setStyleAttribute(element, "margin", margin);
        if (border != null)
            DOM.setStyleAttribute(element, "border", border);
    }

    public String getBorder ()
    {
        return border;
    }

    public void setBorder (String border)
    {
        this.border = border;
    }

    public String getHeight ()
    {
        return height;
    }

    public void setHeight (String height)
    {
        this.height = height;
    }

    public String getMargin ()
    {
        return margin;
    }

    public void setMargin (String margin)
    {
        this.margin = margin;
    }

    public String getPadding ()
    {
        return padding;
    }

    public void setPadding (String padding)
    {
        this.padding = padding;
    }

    public String getWidth ()
    {
        return width;
    }

    public void setWidth (String width)
    {
        this.width = width;
    }
}
