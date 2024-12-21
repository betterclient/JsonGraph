package io.github.betterclient.jsongraph.renderer;

import io.github.betterclient.jsongraph.util.Color;
import org.teavm.jso.browser.Window;
import org.teavm.jso.canvas.CanvasImageSource;
import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.dom.html.HTMLCanvasElement;

import java.util.HashMap;
import java.util.Map;

public class UIRenderer {
    private final CanvasRenderingContext2D context2D;
    private final HTMLCanvasElement canvas;
    private int curH;

    public UIRenderer(HTMLCanvasElement canvas) {
        this.context2D = (CanvasRenderingContext2D) canvas.getContext("2d");
        this.canvas = canvas;
    }

    /**
     * just leaving this here so I don't forget
     * <p>
     * "30px Arial"
     */
    public void setFont(String font) {
        this.context2D.setFont(font);

        String s = font.substring(0, 2);
        curH = Integer.parseInt(s);
    }

    public int getWidth() {
        return this.canvas.getWidth();
    }

    public int getHeight() {
        return this.canvas.getHeight();
    }

    private void setColor(Color color) {
        this.context2D.setFillStyle(
                color.toString()
        );

        this.context2D.setStrokeStyle(
                color.toString()
        );
    }

    int w = 0, h = 0;
    public void setSize() {
        w = Math.max(Window.current().getInnerWidth(), w);
        h = Math.max(600, h);
        this.canvas.setWidth(w);
        this.canvas.setHeight(h);
    }

    private void set(double endX, double endY) {
        if (endX > w) w = (int) endX;
        if (endY > h) h = (int) endY;

        sizes.forEach((o, ints) -> {
            if (endX > ints[0]) {
                ints[0] = (int) endX;
            } else if (endY > ints[1]) {
                ints[1] = (int) endY;
            }
        });
    }

    //--------------ACTUAL IMPLEMENTATIONS--------------

    public void fillRect(double x, double y, double endX, double endY, Color color) {
        this.setColor(color);
        this.context2D.fillRect(x, y, endX - x, endY - y);

        set(endX, endY);
    }

    public void fillRoundRect(double x, double y, double endX, double endY, Color color, float radius) {
        this.setColor(color);
        double width = endX - x;
        double height = endY - y;

        this.context2D.beginPath();
        this.context2D.moveTo(x + radius, y);
        this.context2D.lineTo(x + width - radius, y);
        this.context2D.arcTo(x + width, y, x + width, y + height, radius);
        this.context2D.lineTo(x + width, y + height - radius);
        this.context2D.arcTo(x + width, y + height, x + width - radius, y + height, radius);
        this.context2D.lineTo(x + radius, y + height);
        this.context2D.arcTo(x, y + height, x, y + height - radius, radius);
        this.context2D.lineTo(x, y + radius);
        this.context2D.arcTo(x, y, x + radius, y, radius);
        this.context2D.closePath();
        this.context2D.stroke();
        this.context2D.fill();
        set(endX, endY);
    }

    public void renderImage(double x, double y, double endX, double endY, CanvasImageSource image) {
        this.context2D.drawImage(image, x, y, endX - x, endY - y);
        set(endX, endY);
    }

    public int[] getMetrics(String text) {
        return new int[] {
                (int) this.context2D.measureText(text).getWidth(),
                this.curH
        };
    }

    public void renderText(String text, double x, double y, Color color) {
        this.setColor(color);
        this.context2D.fillText(text, x, y);

        int[] metrics = getMetrics(text);
        set(x + metrics[0], y + metrics[1]);
    }

    public void drawCircle(double x, double y, float radius, Color color) {
        this.setColor(color);

        this.context2D.beginPath();
        this.context2D.arc(x, y, radius * 2, 0, Math.PI * 2);
        this.context2D.fill();

        set(x + radius * 2, y + radius * 2);
    }

    public int renderWithOuterFill(String text, int x, int y) {
        return renderWithOuterFill(text, x, y, Color.BLACK);
    }

    public int renderWithOuterFill(String text, int x, int y, Color fc) {
        int[] a = getMetrics(text);

        this.fillRoundRect(x - 4, y - a[1] + 2, x + a[0] + 4, y + 4, Color.WHITE, 2f);
        this.renderText(text, x, y, fc);
        return a[0];
    }

    public void drawRectOutline(int sx, int sy, int endX, int endY, Color color) {
        this.setColor(color);
        this.context2D.setLineWidth(4);

        this.context2D.strokeRect(sx, sy, endX - sx, endY - sy);
        set(endX, endY);
    }

    public static Map<Object, int[]> sizes = new HashMap<>();

    public void startSize(Object o) {
        sizes.put(o, new int[] {0, 0});
    }

    public int[] endSize(Object o) {
        return sizes.remove(o);
    }
}
