import java.util.*

plugins {
    alias(libs.plugins.shadow)
    alias(libs.plugins.pluginyml)
}

publishData {
    addBuildData()
    useEldoNexusRepos()
    publishComponent("java")
}

dependencies {
    implementation(project(":api"))
    compileOnly("io.papermc.paper", "paper-api", "1.20-R0.1-SNAPSHOT")
    implementation("net.kyori:adventure-platform-bukkit:4.3.0")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core:2.7.0")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit:2.7.0")

    implementation(libs.bundles.eldoutil) {
        exclude("net.kyori")
    }
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

tasks {
    register<Copy>("copyToServer") {
        val props = Properties()
        val propFile = file("build.properties")
        if (!propFile.exists()) propFile.createNewFile()
        file("build.properties").reader().let { props.load(it) }
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
        relocate("de.eldoria.eldoutilities", "de.eldoria.schematicsanitizer.libs.utils")
        relocate("de.eldoria.jacksonbukkit", "de.eldoria.schematicsanitizer.libs.jacksonbukkit")
        //relocate("com", "de.eldoria.schematicsanitizer.libs")
        //relocate("net", "de.eldoria.schematicsanitizer.libs")
    }
}

bukkit {
    name = "SchematicSanitizer"
    main = "de.eldoria.schematicsanitizer.SanitizerPlugin"
    apiVersion = "1.16"


    commands {
        register("schematicsanitizer") {
            usage = "schemsan fix|check <schematic> [new file]"
            aliases = listOf("schemsan", "sanitizer")
        }
    }
}
