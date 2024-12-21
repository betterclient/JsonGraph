package io.github.betterclient.jsongraph;

import io.github.betterclient.jsongraph.renderer.GraphRenderer;
import org.json.JSONObject;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.file.File;

public class GraphGenerator {
    public static GraphRenderer renderer = null;

    public static void graph(File file) {
        try {
            if (file == null) return;
            var stream = file.stream();
            if (stream == null) return;
            var reader = stream.getReader();
            if (reader == null) return;
            reader.read().then(readableStreamReadResult -> {
                var a = readableStreamReadResult.getValue();
                byte[] bites = new byte[a.getByteLength()];
                for (int i = 0; i < a.getByteLength(); i++) {
                    bites[i] = a.get(i);
                }
                GraphGenerator.graph(new String(bites));
                return null;
            });
        } catch (Exception ignored) {
            //Errorless return
        }
    }

    public static void graph(String s) {
        JSONObject obj;
        try {
            obj = new JSONObject(s);
        } catch (Exception e) {
            obj = null; //invalid input
        }

        if (renderer != null) {
            renderer.stop();
        }

        renderer = new GraphRenderer(obj, (HTMLCanvasElement) HTMLDocument.current().getElementById("canvas"));

        //Start
        renderer.onAnimationFrame(0);
    }
}
