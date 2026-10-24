import io.papermc.hangarpublishplugin.model.Platforms
import java.util.Properties

plugins {
    id("schematic-sanitizer.publishing-conventions")
    alias(libs.plugins.shadow)
    alias(libs.plugins.pluginyml)
    alias(libs.plugins.hangar)
    alias(libs.plugins.runpaper)
}

publishData {
    buildData()
}

dependencies {
    implementation(project(":api"))
    compileOnly(libs.paper)
    compileOnly(libs.bundles.fawe)
    compileOnly(libs.jetbrains.annotations)
    testImplementation(libs.paper)

    bukkitLibrary(libs.adventure.bukkit) {
        exclude("org.jetbrains")
    }
    bukkitLibrary(libs.bundles.eldoutil)
}

hangarPublish {
    publications.register("plugin") {
        version = project.version.toString()
        id = "SchematicSanitizer"
        channel = System.getenv("HANGAR_CHANNEL")
        apiKey = System.getenv("HANGAR_KEY")

        platforms {
            register(Platforms.PAPER) {
                jar = tasks.shadowJar.flatMap { it.archiveFile }
                platformVersions = listOf("1.21-26.2")
                dependencies {
                    hangar("FastAsyncWorldEdit") {
                        required = true
                    }
                }
            }
        }
    }
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
    }

    runServer {
        minecraftVersion("26.2")
        downloadPlugins {
            modrinth("fastasyncworldedit", libs.versions.fawe.get())
        }
        javaLauncher = project.javaToolchains.launcherFor {
            languageVersion = JavaLanguageVersion.of(25)
        }
        jvmArgs("-Dcom.mojang.eula.agree=true")
    }
}

bukkit {
    name = "SchematicSanitizer"
    main = "de.eldoria.schematicsanitizer.SanitizerPlugin"
    apiVersion = "1.21"
    version = project.version.toString()
    website = "https://github.com/eldoriarpg/schematic-sanitizer"
    depend = listOf("FastAsyncWorldEdit")

    commands {
        register("schematicsanitizer") {
            aliases = listOf("schemsan", "sanitizer")
        }
    }
}
