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
    id("org.gradle.toolchains.foojay-resolver-convention") version ("0.7.0")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("eldoutil", "2.0.3")
            library("eldoutil-plugin", "de.eldoria.util", "plugin").versionRef("eldoutil")
            library("eldoutil-configuration", "de.eldoria.util", "jackson-configuration").versionRef("eldoutil")
            library("jacksonbukkit", "de.eldoria.jacksonbukkit:jackson-bukkit:1.2.0")
            library("jacksonyaml", "com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.16.0")
            bundle("eldoutil", listOf("eldoutil-plugin", "eldoutil-configuration", "jacksonbukkit", "jacksonyaml"))

            version("fawe", "2.8.2")
            library("fawe-core", "com.fastasyncworldedit", "FastAsyncWorldEdit-Core").versionRef("fawe")
            library("fawe-bukkit", "com.fastasyncworldedit", "FastAsyncWorldEdit-Bukkit").versionRef("fawe")
            bundle("fawe", listOf("fawe-core", "fawe-bukkit"))

            library("paper", "io.papermc.paper:paper-api:1.20-R0.1-SNAPSHOT")

            // plugins
            plugin("spotless", "com.diffplug.spotless").version("6.22.0")
            plugin("shadow", "com.github.johnrengelman.shadow").version("8.1.1")
            plugin("publishdata", "de.chojo.publishdata").version("1.2.5")
            plugin("pluginyml", "net.minecrell.plugin-yml.bukkit").version("0.6.0")
            plugin("hangar", "io.papermc.hangar-publish-plugin").version("0.1.0")
        }
    }
}
include("api")
include("plugin")
