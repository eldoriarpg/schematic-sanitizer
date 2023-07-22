publishData {
    addBuildData()
    useEldoNexusRepos()
    publishComponent("java")
}

dependencies {
    compileOnly("io.papermc.paper", "paper-api", "1.20-R0.1-SNAPSHOT")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core:2.7.0")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit:2.7.0")
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
