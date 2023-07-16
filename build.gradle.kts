import java.util.*

plugins {
    java
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "de.eldoria"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://eldonexus.de/repository/maven-public/")
    maven("https://eldonexus.de/repository/maven-proxies/")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    compileOnly("io.papermc.paper", "paper-api", "1.20-R0.1-SNAPSHOT")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core:2.6.4")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit:2.6.4")

    implementation("net.kyori:adventure-platform-bukkit:4.3.0")
    implementation("de.eldoria.util", "plugin", "2.0.0-SNAPSHOT"){
        exclude("net.kyori")
    }
    implementation("de.eldoria.util", "jackson-configuration", "2.0.0-DEV")
    implementation("de.eldoria.jacksonbukkit", "jackson-bukkit", "1.2.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
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
        register("schematiccleaner") {
            usage = "schemcleaner fix|check <schematic> [new file]"
            aliases = listOf("schemclean")
        }
    }
}
