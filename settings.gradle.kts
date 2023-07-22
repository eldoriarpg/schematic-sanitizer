rootProject.name = "schematic-sanitizer"

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven {
            name = "EldoNexus"
            url = uri("https://eldonexus.de/repository/maven-public/")
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version ("0.6.0")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("eldoutil", "2.0.1-DEV")
            library("eldoutil-plugin", "de.eldoria.util", "plugin").versionRef("eldoutil")
            library("eldoutil-configuration", "de.eldoria.util", "jackson-configuration").versionRef("eldoutil")
            library("jacksonbukkit","de.eldoria.jacksonbukkit:jackson-bukkit:1.2.0")
            bundle("eldoutil", listOf("eldoutil-plugin","eldoutil-configuration", "jacksonbukkit"))

            // plugins
            plugin("spotless", "com.diffplug.spotless").version("6.20.0")
            plugin("shadow", "com.github.johnrengelman.shadow").version("8.1.1")
            plugin("publishdata", "de.chojo.publishdata").version("1.2.4")
            plugin("pluginyml", "net.minecrell.plugin-yml.bukkit").version("0.6.0")
        }
    }
}

