publishData {
    addBuildData()
    useEldoNexusRepos()
    publishComponent("java")
}

dependencies {
    compileOnly(libs.paper)
    compileOnly(libs.bundles.fawe)
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
