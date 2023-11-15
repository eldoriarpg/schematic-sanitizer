import io.papermc.hangarpublishplugin.model.Platforms
import java.util.*

plugins {
    alias(libs.plugins.shadow)
    alias(libs.plugins.pluginyml)
    alias(libs.plugins.hangar)
}

publishData {
    addBuildData()
    useEldoNexusRepos()
    publishComponent("java")
}

dependencies {
    implementation(project(":api"))
    compileOnly(libs.paper)
    implementation("net.kyori:adventure-platform-bukkit:4.3.1") {
        exclude("org.jetbrains")
    }
    compileOnly(libs.bundles.fawe)
    compileOnly("org.jetbrains:annotations:24.1.0")

    bukkitLibrary(libs.bundles.eldoutil)
}

publishing {
    publications.create<MavenPublication>("maven") {
        publishData.configurePublication(this)
    }

    repositories {
        maven {
            authentication {
                credentials(PasswordCredentials::class) {
                    username = System.getenv("NEXUS_USERNAME")
                    password = System.getenv("NEXUS_PASSWORD")
                }
            }

            setUrl(publishData.getRepository())
            name = "EldoNexus"
        }
    }
}

hangarPublish {
    publications.register("plugin") {
        version.set(publishData.getVersion())
        id = "SchematicSanitizer"
        channel = System.getenv("HANGAR_CHANNEL")

        apiKey = System.getenv("HANGAR_KEY")

        platforms {
            register(Platforms.PAPER) {
                jar.set(tasks.shadowJar.flatMap { it.archiveFile })
                platformVersions.set(listOf("1.16.5-1.20.2"))
                this.dependencies {
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
        propFile.reader().let { props.load(it) }
        val path = props.getProperty("targetDir") ?: ""
        if (path.isEmpty()) {
            println("targetDir is not set in gradle properties")
            return@register
        }
        from(shadowJar)
        destinationDir = File(path)
    }
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveBaseName.set("schematic-sanitizer")
        archiveVersion.set(publishData.getVersion())
        val mapping = mapOf(
                "de.eldoria.eldoutilities" to "utils",
                "de.eldoria.jacksonbukkit" to "jacksonbukkit",
                "org.yaml" to "yaml",
                "net.kyori" to "adventure",
                "com.fasterxml.jackson" to "jackson"
        )
        if (publishData.isPublicBuild()) {
            println("relocating")
            val base = "de.eldoria.schematicsanitizer.libs."
            for ((pattern, name) in mapping) {
                println("relocating ${pattern} to ${base}${name}")
                relocate(pattern, "${base}${name}")
            }

        }
    }
}

bukkit {
    name = "SchematicSanitizer"
    main = "de.eldoria.schematicsanitizer.SanitizerPlugin"
    apiVersion = "1.16"
    version = publishData.getVersion(true)
    website = "https://github.com/eldoriarpg/schematic-sanitizer"


    commands {
        register("schematicsanitizer") {
            aliases = listOf("schemsan", "sanitizer")
        }
    }
}
