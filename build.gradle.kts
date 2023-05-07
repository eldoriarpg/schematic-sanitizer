plugins {
    java
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"

}

group = "de.eldoria"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://eldonexus.de/repository/maven-public/")
    maven("https://eldonexus.de/repository/maven-proxies/")
}

dependencies {
    compileOnly("io.papermc.paper", "paper-api", "1.19.4-R0.1-SNAPSHOT")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core:2.5.2")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit:2.5.2")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks {
    register<Copy>("copyToServer") {
        val path = project.property("targetDir") ?: ""
        if (path.toString().isEmpty()) {
            println("targetDir is not set in gradle properties")
            return@register
        }
        from(jar)
        destinationDir = File(path.toString())
    }
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

bukkit {
    name = "SchematicSanitizer"
    main = "de.eldoria.schematicsanitizer.Sanitizer"

    commands {
        register("sanpaste")
    }
}
