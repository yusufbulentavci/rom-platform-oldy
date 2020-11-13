package com.bilgidoku.rom.gwt.client.widgets.colorpicker;



public class HSVPaletteDefinition implements ColorPaletteDefinition
{
    private final int KEY_HUE = 1;
    private final int KEY_SAT = 2;
    private final int KEY_VAL = 3;
    private int keyOn = KEY_HUE;
    private double staticHue = 1.0;
    private double staticSaturation;
    private double staticValue;

    
    
    public int getHeight ()
    {
        return 16;
    }

    public int getWidth ()
    {
        return 16;
    }

    public String[] getHexColorValues ()
    {
        return null;
    }

    public RGB[] getRGBVlues ()
    {
        RGB[] result = new RGB[256];

        int c = 0;
        for (double y = 0; y <= 255; y += 17) {
            for (double x = 0; x <= 255; x += 17) {

                int[] rgb = null;
                switch (keyOn) {
                    case KEY_HUE:
                        rgb = ColorUtils.HSVtoRGB(staticHue, x/255, 1.0 - y/255);
                        break;
                    case KEY_SAT:
                        rgb = ColorUtils.HSVtoRGB(x/255, staticSaturation, 1.0 - y/255);
                        break;
                    case KEY_VAL:
                        rgb = ColorUtils.HSVtoRGB(x/255, 1.0 - y/255, staticValue);
                        break;
                }
                
                result[c] = new RGB(rgb[0], rgb[1], rgb[2]);
                c++;
            }
        }

        return result;
    }
    
    

    public void setStaticHue (double staticHue)
    {
        keyOn = KEY_HUE;
        this.staticHue = staticHue;
    }

    public void setStaticSaturation (double staticSaturation)
    {
        keyOn = KEY_SAT;
        this.staticSaturation = staticSaturation;
    }

    public void setStaticValue (double staticValue)
    {
        keyOn = KEY_VAL;
        this.staticValue = staticValue;
    }

    public int getDefaultSelected ()
    {
        return 15;
    }

    
}