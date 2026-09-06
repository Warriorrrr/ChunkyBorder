plugins {
    id("java")
    id("net.kyori.blossom")
}

val conventions = extensions.create<ModConventionsExtension>("modConventions")

sourceSets.main {
    blossom {
        resources {
            trimNewlines = false

            property("id", rootProject.name)
            property("version", project.version.toString())
            property("name", providers.gradleProperty("artifactName"))
            property("description", providers.gradleProperty("description"))
            property("author", providers.gradleProperty("author"))
            property("github", providers.gradleProperty("github"))
            property("target", conventions.target)
        }
    }
}
