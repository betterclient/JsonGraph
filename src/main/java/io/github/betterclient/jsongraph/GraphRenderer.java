package io.github.betterclient.jsongraph;

import io.github.betterclient.jsongraph.renderer.Color;
import io.github.betterclient.jsongraph.renderer.UIRenderer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.teavm.jso.browser.AnimationFrameCallback;
import org.teavm.jso.browser.Window;
import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.dom.html.HTMLCanvasElement;

public class GraphRenderer implements AnimationFrameCallback {
    private boolean continuing;
    private final CanvasRenderingContext2D context;
    private final HTMLCanvasElement canvas;
    private final JSONObject object;

    public GraphRenderer(JSONObject object, HTMLCanvasElement canvas) {
        this.continuing = true;
        this.canvas = canvas;
        this.context = (CanvasRenderingContext2D) this.canvas.getContext("2d");
        this.object = object;

        //Set initial size
        UIRenderer renderer = new UIRenderer(this.canvas);
        render(renderer);
        renderer.setSize();
        this.context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public void onAnimationFrame(double timestamp) {
        //this.context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        UIRenderer renderer = new UIRenderer(this.canvas);
        renderer.fillRect(0, 0, canvas.getWidth(), canvas.getHeight(), Color.WHITE);

        render(renderer);

        if (this.continuing) {
            Window.requestAnimationFrame(this);
        }
    }

    public void stop() {
        continuing = false;
    }

    //This is where the rendering happens.
    private void render(UIRenderer renderer) {
        renderer.setFont("15px Arial");
        renderer.renderText("Renderer will try to fit everything onto the canvas, if it doesn't fit, you can scroll.", 10, 15, Color.WHITE);

        if (object == null) {
            renderer.renderText("Please input a valid json object.", 10, 30, Color.WHITE);
            return;
        }

        int curX = 15;
        int curY = 60;

        curX += renderer.renderWithOuterFill("Object", 15, 60) + 20;

        int[] p = new int[] {curX, curY};
        int index = 0;
        for (String s : object.keySet().stream().sorted().toList().reversed()) {
            renderObj(s, object, renderer, index, p);

            index++;
        }
    }

    private void renderObj(String s, JSONObject object, UIRenderer renderer, int index, int[] p) {
        Object four = object.get(s);
        String theStrYea = four + s + object.hashCode();
        int sx = p[0];
        int sy = p[1] + 10;
        renderer.startSize(theStrYea);

        if (index != 0)
            p[1] += 30;
        else
            sy-=40;

        p[0] += 30;

        renderCur0(renderer, s, p);
        renderCur0(renderer, four, p);

        int[] endSize = renderer.endSize(theStrYea);
        renderer.drawRectOutline(sx, sy, endSize[0], endSize[1] - 75, Color.WHITE);

        p[0] = sx;
    }

    private void renderCur0(UIRenderer renderer, Object toR, int[] pos) {
        String s;
        switch (toR) {
            case Integer b:
                s = b + "";
                break;
            case Boolean b:
                s = b + "";
                break;
            case String ss:
                s = "\"" + ss + "\"";
                break;
            case JSONObject obj:
                int index = 0;
                for (String ss : obj.keySet().stream().sorted().toList().reversed()) {
                    renderObj(ss, obj, renderer, index, pos);
                    index++;
                }
                return;
            case JSONArray objs:
                for (Object obj : objs) {
                    int sx = pos[0];
                    pos[0] += 30;
                    renderCur0(renderer, "Array", pos);
                    renderCur0(renderer, obj, pos);
                    pos[0] = sx;
                    pos[1] += 30;
                }
                return;
            default:
                throw new IllegalStateException("Unexpected value: " + toR);
        }

        pos[0] = renderer.renderWithOuterFill(s, pos[0], pos[1]) + pos[0] + 30;
    }
}
