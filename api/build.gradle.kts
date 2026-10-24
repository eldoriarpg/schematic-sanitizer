plugins {
    id("schematic-sanitizer.publishing-conventions")
}

description = "API to check schematics and remove blocks, entities and NBT from them."

publishData {
    eldoNexus(dev = false)
}

publishing {
    publications.withType<MavenPublication>().configureEach {
        pom {
            licenses {
                license {
                    name = "LGPL-3.0-or-later"
                    url = "https://github.com/eldoriarpg/schematic-sanitizer/blob/main/api/LICENSE.md"
                }
            }
        }
    }
}

dependencies {
    compileOnly(libs.paper)
    compileOnly(libs.bundles.fawe)

    testImplementation(libs.fawe.core)
}
