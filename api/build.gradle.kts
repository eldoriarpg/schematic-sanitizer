plugins {
    id("publishing-conventions")
}

dependencies {
    compileOnly(libs.paper)
    compileOnly(libs.bundles.fawe)

    testImplementation(libs.fawe.core)
}
