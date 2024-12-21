package io.github.betterclient.jsongraph;

import io.github.betterclient.jsongraph.renderer.GraphRenderer;
import io.github.betterclient.jsongraph.util.ExampleJSONDataUtilityClassFactoryUtilThingy;
import org.teavm.jso.dom.html.HTMLButtonElement;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLInputElement;

public class Main {
    public static void main(String[] args) {
        HTMLDocument document = HTMLDocument.current();
        HTMLButtonElement button = (HTMLButtonElement) document.getElementById("button");

        button.addEventListener("click", event -> {
            HTMLInputElement inputElement = (HTMLInputElement) document.getElementById("input");

            GraphGenerator.graph(inputElement.getFiles().get(0));
        });

        ExampleJSONDataUtilityClassFactoryUtilThingy.generate();

        GraphGenerator.renderer = new GraphRenderer(null, (HTMLCanvasElement) HTMLDocument.current().getElementById("canvas"));
        GraphGenerator.renderer.onAnimationFrame(0);
    }
}