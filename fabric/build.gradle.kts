plugins {
    alias(libs.plugins.loom)
    id("chunkyborder.mod-conventions")
}

val shade = configurations.create("shade")

repositories {
    maven("https://repo.mikeprimm.com")
    maven("https://jitpack.io")
    maven("https://api.modrinth.com/maven") {
        mavenContent { includeGroup("maven.modrinth") }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.4")
    mappings("net.fabricmc:yarn:1.21.4+build.1:v2")
    modImplementation("net.fabricmc:fabric-loader:0.16.7")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.111.0+1.21.4")
    modImplementation(libs.chunky.fabric)
    compileOnly(libs.dynmap)
    compileOnly(libs.bluemap)
    compileOnly(libs.squaremap)
    compileOnly(libs.pl3xmap)
    implementation(project(":chunkyborder-common"))
    shade(project(":chunkyborder-common"))
}

tasks {
    shadowJar {
        configurations = listOf(shade)
        archiveClassifier = "dev"
        archiveFileName = null
    }
    remapJar {
        inputFile.set(shadowJar.get().archiveFile)
        archiveFileName.set("${project.property("artifactName")}-Fabric-${project.version}.jar")
    }
}
