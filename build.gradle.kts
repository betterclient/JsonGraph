import java.io.FileOutputStream
import java.util.zip.ZipFile

plugins {
    java
    war
    id("org.teavm") version "0.11.0"
}

group = "io.github.betterclient"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(teavm.libs.jsoApis)
    implementation("org.json:json:20240303")
}

teavm.js {
    addedToWebApp = true
    mainClass = "io.github.betterclient.jsongraph.Main"

    targetFileName = "jsongraph.js"
    obfuscated = false
}

var extract = task("extract") {
    doLast {
        val jf = ZipFile(file("build/libs/JSONGraph-1.0.war"))
        val entries = jf.stream().toList()
        for (entry in entries) {
            if (entry.name == "js/jsongraph.js") {
                val iss = jf.getInputStream(entry)
                val data = iss.readAllBytes()
                iss.close()
                val f = file("out/jsongraph.js")
                f.delete()
                val os = FileOutputStream(f)
                os.write(data)
                os.close()
            }
        }
        jf.close()
    }
}

tasks.named("build") {
    finalizedBy(extract)
}