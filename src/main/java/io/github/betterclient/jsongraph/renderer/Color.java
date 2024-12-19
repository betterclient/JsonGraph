package io.github.betterclient.jsongraph.renderer;

public class Color {
    //-------------DEFAULT COLORS-------------
    public static final Color RED = new Color(255, 0, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color BLACK = new Color(0, 0, 0);

    //-------------COLOR IMPLEMENTATION-------------
    private final int red, green, blue, alpha;

    public Color(int red, int green, int blue) {
        this(red, green, blue, 255);
    }

    public Color(int red, int green, int blue, int alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        testColorValueRange(red, green, blue, alpha);
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getAlpha() {
        return alpha;
    }

    private static void testColorValueRange(int r, int g, int b, int a) {
        boolean rangeError = false;
        String badComponentString = "";

        if ( a < 0 || a > 255) {
            rangeError = true;
            badComponentString = badComponentString + " Alpha";
        }
        if ( r < 0 || r > 255) {
            rangeError = true;
            badComponentString = badComponentString + " Red";
        }
        if ( g < 0 || g > 255) {
            rangeError = true;
            badComponentString = badComponentString + " Green";
        }
        if ( b < 0 || b > 255) {
            rangeError = true;
            badComponentString = badComponentString + " Blue";
        }
        if (rangeError) {
            throw new IllegalArgumentException("Color parameter outside of expected range:"
                    + badComponentString);
        }
    }
}
