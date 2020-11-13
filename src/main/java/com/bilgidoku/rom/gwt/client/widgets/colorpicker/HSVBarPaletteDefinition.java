package com.bilgidoku.rom.gwt.client.widgets.colorpicker;


public class HSVBarPaletteDefinition implements ColorPaletteDefinition 
{
    private static final int LOCKED_FOR_HUE = 1;
    private static final int LOCKED_FOR_SAT = 2;
    private static final int LOCKED_FOR_VAL = 3;
    private int lockedFor = LOCKED_FOR_HUE;
    private double staticHue;
    private double staticSaturation = 1.0;
    private double staticValue = 1.0;

    
    public int getWidth ()
    {
        return 1;
    }

    public int getHeight ()
    {
        return 40;
    }

    public String[] getHexColorValues ()
    {
        return null;
    }

    public RGB[] getRGBVlues ()
    {
        RGB[] result = new RGB[40];
        int c = 0;

        for (double x = 0; x <= 255; x += 6.4) {
        
            int[] rgb = null;
            switch (lockedFor) {
                case LOCKED_FOR_HUE:
                    rgb = ColorUtils.HSVtoRGB(1.0 - x / 255, staticSaturation, staticValue);
                    break;
                case LOCKED_FOR_SAT:
                    rgb = ColorUtils.HSVtoRGB(staticHue, 1.0 - x / 255, staticValue);
                    break;
                case LOCKED_FOR_VAL:
                    rgb = ColorUtils.HSVtoRGB(staticHue, staticSaturation, 1.0 - x / 255);
                    break;
            }

            result[c] = new RGB(rgb[0], rgb[1], rgb[2]);
            c++;
        }
        return result;
    }


    public void lockForHue (int saturation, int value)
    {
        lockedFor = LOCKED_FOR_HUE;
        staticSaturation = saturation;
        staticValue = value;
    }

    public void lockForSaturation (int hue, int value)
    {
        lockedFor = LOCKED_FOR_SAT;
        staticHue = hue;
        staticValue = value;
    }

    public void lockForValue (int hue, int saturation)
    {
        lockedFor = LOCKED_FOR_VAL;
        staticHue = hue;
        staticSaturation = saturation;
    }

    public int getDefaultSelected ()
    {
        return 0;
    }
}
