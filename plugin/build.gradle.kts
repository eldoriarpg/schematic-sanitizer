import io.papermc.hangarpublishplugin.model.Platforms
import java.util.Properties
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
import net.minecrell.pluginyml.paper.PaperPluginDescription.RelativeLoadOrder

plugins {
    id("schematic-sanitizer.publishing-conventions")
    id("de.chojo.publishdata.manifest")
    alias(libs.plugins.shadow)
    alias(libs.plugins.pluginyml.paper)
    alias(libs.plugins.hangar)
    alias(libs.plugins.runpaper)
    alias(libs.plugins.minotaur)
}

description = "Paper plugin that checks schematics and removes blocks, entities and NBT from them."

publishData {
    eldoNexus(dev = false)
    component = "shadow"
    buildData {
        put("lynaProduct", "32")
    }
}

pluginManifest {
    platforms("paper")
    requires("FastAsyncWorldEdit") { modrinth("fastasyncworldedit") }
}

publishing {
    publications.withType<MavenPublication>().configureEach {
        pom {
            licenses {
                license {
                    name = "AGPL-3.0-only"
                    url = "https://github.com/eldoriarpg/schematic-sanitizer/blob/main/LICENSE.md"
                }
            }
        }
    }
}

dependencies {
    implementation(project(":api"))
    compileOnly(libs.paper)
    compileOnly(libs.bundles.fawe)
    compileOnly(libs.jetbrains.annotations)
    testImplementation(libs.paper)

    paperLibrary(libs.bundles.utilities)
    paperLibrary(libs.bundles.jackson)
    testImplementation(libs.bundles.utilities)
    testImplementation(libs.bundles.jackson)
}

hangarPublish {
    publications.register("plugin") {
        version = project.version.toString()
        id = "SchematicSanitizer"
        channel = System.getenv("HANGAR_CHANNEL") ?: "Release"
        apiKey = System.getenv("HANGAR_KEY")

        platforms {
            register(Platforms.PAPER) {
                jar = tasks.shadowJar.flatMap { it.archiveFile }
                platformVersions = listOf("1.21.11-26.3")
                dependencies {
                    hangar("FastAsyncWorldEdit") {
                        required = true
                    }
                }
            }
        }
    }
}

modrinth {
    token = providers.environmentVariable("MODRINTH_TOKEN")
    projectId = "fPX2XM5q"
    versionNumber = project.version.toString()
    versionType = "release"
    uploadFile.set(tasks.shadowJar)
    gameVersions.addAll("1.21.11", "26.1", "26.1.1", "26.1.2", "26.2", "26.3")
    loaders.addAll("paper", "purpur")
    dependencies {
        required.project("fastasyncworldedit")
    }
    changelog = providers.environmentVariable("MODRINTH_CHANGELOG_FILE").map { file(it).readText() }.orElse("")
    syncBodyFrom = rootProject.file("README.md").readText()
}

tasks {
    register<Copy>("copyToServer") {
        val props = Properties()
        val propFile = file("build.properties")
        if (!propFile.exists()) propFile.createNewFile()
        propFile.reader().use { props.load(it) }
        val path = props.getProperty("targetDir") ?: ""
        if (path.isEmpty()) {
            println("targetDir is not set in gradle properties")
            return@register
        }
        from(shadowJar)
        destinationDir = File(path)
    }

    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveBaseName = "schematic-sanitizer"
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        mergeServiceFiles()
        addMultiReleaseAttribute = false
        exclude("META-INF/versions/**")
    }

    runServer {
        minecraftVersion("26.3")
        downloadPlugins {
            modrinth("fastasyncworldedit", libs.versions.fawe.get())
        }
        jvmArgs("-Dcom.mojang.eula.agree=true")
    }
}

paper {
    name = "SchematicSanitizer"
    main = "de.eldoria.schematicsanitizer.SanitizerPlugin"
    loader = "de.eldoria.schematicsanitizer.SanitizerPluginLoader"
    generateLibrariesJson = true
    apiVersion = "1.21.11"
    version = project.version.toString()
    description = "Checks schematics and removes unwanted blocks, entities and NBT."
    authors = listOf("RainbowDashLabs")
    website = "https://docs.eldoria.de/schematic-sanitizer"

    serverDependencies {
        register("FastAsyncWorldEdit") {
            load = RelativeLoadOrder.BEFORE
        }
    }

    permissions {
        register("schematicsanitizer.*") {
            description = "All permissions of Schematic Sanitizer"
            children = listOf(
                "schematicsanitizer.check.use",
                "schematicsanitizer.fix.use",
                "schematicsanitizer.fix.batch",
                "schematicsanitizer.admin",
                "schematicsanitizer.updates",
            )
        }
        register("schematicsanitizer.check.use") {
            description = "Check schematics and show reports"
            default = OP
        }
        register("schematicsanitizer.fix.use") {
            description = "Fix single schematics and show reports"
            default = OP
        }
        register("schematicsanitizer.fix.batch") {
            description = "Fix every schematic in a folder and show reports"
            default = OP
        }
        register("schematicsanitizer.admin") {
            description = "Upload debug reports"
            default = OP
        }
        register("schematicsanitizer.updates") {
            description = "Hear about new versions on join and download them"
            default = OP
        }
    }
}
