package com.bilgidoku.rom.gwt.client.widgets.colorpicker;

import java.util.Iterator;
import java.util.Vector;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Panel;

public class ColorPanel extends Composite
{
    private Panel container;
    private Grid colorPanel;
    private ColorButton[][] colorButtons;
    private ButtonStyleCommand buttonStyle;
    private Vector handlers = new Vector();
    private ColorButton selectedButton;
    private ColorPaletteDefinition paletteDef;
    
    
    public ColorPanel (ColorPaletteDefinition inPaletteDef)
    {
        this(inPaletteDef, null);
    }

    public ColorPanel (ColorPaletteDefinition inPaletteDef, ButtonStyleCommand inButtonStyle)
    {
    	if (inButtonStyle == null) {
            buttonStyle = new ButtonStyleCommand();
            buttonStyle.setBorder("none");
            buttonStyle.setHeight("10px");
            buttonStyle.setMargin("0");
            buttonStyle.setPadding("0");
            buttonStyle.setWidth("10px");
        }
        else {
            buttonStyle = inButtonStyle;
            buttonStyle.setBorder("none");
        }

        container = new FlowPanel();
        initWidget(container);

        paletteDef = inPaletteDef;
        redrawPalette();

        int y = paletteDef.getDefaultSelected() / colorPanel.getColumnCount();
        int x = paletteDef.getDefaultSelected() % colorPanel.getColumnCount();
        setButtonAsSelected(colorButtons[x][y]);
    }


    public void redrawPalette ()
    {
        String[] hcv = paletteDef.getHexColorValues();
        RGB[] rcv = paletteDef.getRGBVlues();
        
        boolean useRGB = (hcv == null);
        
        // clear panel if no colors defined
        if (useRGB && rcv == null) {
            resize(0, 0);
            return;
        }
        else {
            resize(paletteDef.getWidth(), paletteDef.getHeight());
        }
        
        int numColors = useRGB ? rcv.length : hcv.length;
                
        int col = 0;
        int row = 0;
        for (int i = 0; i < numColors; i++) {

            colorButtons[col][row].setVisible(true);
            
            if (useRGB) {
                RGB rgb = rcv[i];
                colorButtons[col][row].setColor(rgb.getRed(), rgb.getGreen(), rgb.getBlue());
            }
            else {
                colorButtons[col][row].setColor(hcv[i]);
            }
            
            col++;
            if ((i + 1) % paletteDef.getWidth() == 0) {
                col = 0;
                row++;
            }
        }

        // disable any extra color buttons
        for (int i = numColors; i < (paletteDef.getWidth() * paletteDef.getHeight()); i++) {

            colorButtons[col][row].setColor(100, 100, 100);
            colorButtons[col][row].setVisible(false);
            
            col++;
            if ((i + 1) % paletteDef.getWidth() == 0) {
                col = 0;
                row++;
            }
        }
    }

    
    /**
     * Resize panel to hold the specified number of color
     * options.
     * @param cols width
     * @param rows height
     * @param numOfColors number of colors in pallete
     */
    private void resize (int cols, int rows)
    {
        if (colorPanel != null
                && colorPanel.getColumnCount() == cols
                && colorPanel.getRowCount() == rows) {
            return;
        }
        
        Grid tmp = new Grid(rows, cols);
        tmp.setCellPadding(0);
        tmp.setCellSpacing(0);
        
        ensureCapacity(cols, rows);
        
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                tmp.setWidget(y, x, colorButtons[x][y]);
            }
        }

        if (colorPanel == null) {
            colorPanel = tmp;
            container.add(tmp);
        }
        else {
            container.remove(colorPanel);
            container.add(tmp);
            colorPanel.clear();
            colorPanel = tmp;
        }
    }
    
    
    private void ensureCapacity (int cols, int rows)
    {
        if (colorButtons == null) {
            colorButtons = new ColorButton[cols][rows];

            for (int x = 0; x < cols; x++) {
                for (int y = 0; y < rows; y++) {
                    colorButtons[x][y] = new ColorButton(buttonStyle, this, x, y);
                }
            }
            
        }
        else if (colorButtons.length < cols || colorButtons[0].length < rows) {
            int newColCount = Math.max(cols, colorButtons.length);
            int newRowCount = Math.max(rows, colorButtons[0].length);
            ColorButton[][] tmp = new ColorButton[newColCount][newRowCount];
            
            for (int x = 0; x < newColCount; x++) {
                for (int y = 0; y < newRowCount; y++) {
                    if (x < colorButtons.length && y < colorButtons[0].length) {
                        tmp[x][y] = colorButtons[x][y];
                    }
                    else {
                        tmp[x][y] = new ColorButton(buttonStyle, this, x, y);
                    }
                }
            }
            colorButtons = tmp;
        }
    }

    void triggerClickEvent (ColorButton button)
    {
        ColorSelectedEvent evt = new ColorSelectedEvent(this);
        evt.setRed(button.getRed());
        evt.setGreen(button.getGreen());
        evt.setBlue(button.getBlue());
        evt.setHexValue(button.getHexValue());
        evt.setPositionX(button.getPositionX() + 1);
        evt.setPositionY(button.getPositionY() + 1);
        evt.setPosition((button.getPositionY() 
                * colorPanel.getColumnCount()) + button.getPositionX() + 1);
        evt.setMaxPositionX(colorPanel.getColumnCount());
        evt.setMaxPositionY(colorPanel.getRowCount());

        setButtonAsSelected(button);
        
        for (Iterator i = handlers.iterator(); i.hasNext();) {
            ((ColorSelectedHandler) i.next()).onColorSelected(evt);
        }
    }

    private void setButtonAsSelected (ColorButton button)
    {
        if (selectedButton != null) {
            DOM.setStyleAttribute(selectedButton.getElement(), "borderStyle", "none");
            DOM.setStyleAttribute(selectedButton.getElement(), "margin", "0");
        }
        DOM.setStyleAttribute(button.getElement(), "borderStyle", "inset");
        DOM.setStyleAttribute(button.getElement(), "borderWidth", "1px");
        DOM.setStyleAttribute(button.getElement(), "borderColor", "#f2f2f2");
        DOM.setStyleAttribute(button.getElement(), "margin", "-1px");
        blur(button.getElement());
        selectedButton = button;
    }

    private native void blur (Element e) /*-{
        e.blur();
    }-*/;
    
    public void addColorSelectedHandler (ColorSelectedHandler handler)
    {
        handlers.add(handler);
    }

    public void removeColorSelectedHandler (ColorSelectedHandler handler)
    {
        handlers.remove(handler);
    }
}
