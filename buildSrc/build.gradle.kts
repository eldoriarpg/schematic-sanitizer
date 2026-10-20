plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven("https://eldonexus.de/repository/maven-public/")
}

dependencies {
    implementation(libs.gradle.publishdata)
    implementation(libs.gradle.spotless)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
