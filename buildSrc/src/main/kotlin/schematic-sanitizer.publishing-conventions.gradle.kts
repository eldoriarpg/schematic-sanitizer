plugins {
    id("schematic-sanitizer.java-conventions")
    `maven-publish`
    id("de.chojo.publishdata")
}

publishData {
    eldoNexus()
}
