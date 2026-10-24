plugins {
    id("schematic-sanitizer.java-conventions")
    `maven-publish`
    id("de.chojo.publishdata")
}

publishing {
    publications.withType<MavenPublication>().configureEach {
        pom {
            url = "https://github.com/eldoriarpg/schematic-sanitizer"
            developers {
                developer {
                    name = "Eldoria"
                }
            }
            scm {
                url = "https://github.com/eldoriarpg/schematic-sanitizer"
            }
        }
    }
}
