package io.github.betterclient.jsongraph.util;

public class SwitchingColorUtil {
    private final Color a, b;
    private final int duration = 1000;
    private long startTime;

    public SwitchingColorUtil(Color a, Color b) {
        this.a = a;
        this.b = b;
        startTime = System.currentTimeMillis();
    }

    public Color getA() {
        long ct = System.currentTimeMillis();
        long elapsed = (ct - startTime) % (2 * duration);

        double prog = (elapsed < duration) ?
                (double) elapsed / duration :
                1.0 - (double) (elapsed - duration) / duration;

        return ic(a, b, prog);
    }

    public Color getB() {
        long ct = System.currentTimeMillis();
        long elapsed = (ct - startTime) % (2 * duration);

        double prog = (elapsed < duration) ?
                (double) elapsed / duration :
                1.0 - (double) (elapsed - duration) / duration;

        return ic(b, a, prog);
    }

    private Color ic(Color c1, Color c2, double progress) {
        int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * progress);
        int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * progress);
        int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * progress);

        return new Color(r, g, b);
    }
}
