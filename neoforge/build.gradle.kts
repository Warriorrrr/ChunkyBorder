plugins {
    alias(libs.plugins.loom)
    id("chunkyborder.mod-conventions")
}

val shade = configurations.create("shade")

repositories {
    maven("https://maven.neoforged.net/releases/")
    maven("https://repo.mikeprimm.com")
    maven("https://jitpack.io")
    maven("https://api.modrinth.com/maven") {
        mavenContent { includeGroup("maven.modrinth") }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.4")
    mappings(loom.officialMojangMappings())
    neoForge("net.neoforged:neoforge:21.4.10-beta")
    modImplementation(libs.chunky.neoforge)
    compileOnly(libs.dynmap)
    compileOnly(libs.bluemap)
    compileOnly(libs.squaremap)
    compileOnly(libs.pl3xmap)
    implementation(project(":chunkyborder-common"))
    shade(project(":chunkyborder-common"))
}

tasks {
    jar {
        manifest {
            attributes(
                mapOf(
                    "Implementation-Title" to rootProject.name,
                    "Implementation-Version" to project.version,
                    "Implementation-Vendor" to project.property("author")
                )
            )
        }
    }
    shadowJar {
        configurations = listOf(shade)
        archiveClassifier = "dev"
        archiveFileName = null
    }
    remapJar {
        inputFile.set(shadowJar.get().archiveFile)
        archiveFileName.set("${project.property("artifactName")}-NeoForge-${project.version}.jar")
    }
}
