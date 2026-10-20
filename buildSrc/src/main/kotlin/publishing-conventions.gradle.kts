plugins {
    id("java-conventions")
    `maven-publish`
    id("de.chojo.publishdata")
}

publishData {
    eldoNexus()
}
