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
            property("name", providers.gradleProperty("artifactName").get())
            property("description", providers.gradleProperty("description").get())
            property("author", providers.gradleProperty("author").get())
            property("github", providers.gradleProperty("github").get())
            property("target", conventions.target)
        }
    }
}
