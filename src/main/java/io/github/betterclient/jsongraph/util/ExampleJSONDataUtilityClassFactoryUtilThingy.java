package io.github.betterclient.jsongraph.util;

import io.github.betterclient.jsongraph.GraphGenerator;
import org.teavm.jso.dom.html.HTMLDocument;

/**
 * This name was necessary.
 */
public class ExampleJSONDataUtilityClassFactoryUtilThingy {
    public static void generate() {
        HTMLDocument document = HTMLDocument.current();

        document.getElementById("example0").addEventListener("click", event -> GraphGenerator.graph(
                """
                {
                    "value": "Example data!",
                    "list": [
                        "Example list!",
                        "Element 2",
                        "Element 3"
                    ],
                    "object": {
                        "val0": "Lorem ipsum",
                        "somedata": 2137519,
                        "boolean": true
                    }
                }
               """));

        document.getElementById("example1").addEventListener("click", event -> GraphGenerator.graph(
                """
                         {
                                     "user": {
                                       "id": 1,
                                       "name": "Alice",
                                       "tasks": [
                                         {
                                           "id": 101,
                                           "title": "Complete onboarding",
                                           "status": "completed"
                                         },
                                         {
                                           "id": 102,
                                           "title": "Submit report",
                                           "status": "pending"
                                         }
                                       ]
                                     }
                                   }
                        """));
    }
}
