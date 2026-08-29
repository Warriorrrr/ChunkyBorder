plugins {
    id("net.kyori.blossom")
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.mikeprimm.com")
    maven("https://jitpack.io")
    maven("https://api.modrinth.com/maven") {
        mavenContent { includeGroup("maven.modrinth") }
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.20.5-R0.1-SNAPSHOT")
    compileOnly(libs.chunky.bukkit)
    compileOnly(libs.chunky.folia)
    compileOnly(libs.dynmap)
    compileOnly(libs.bluemap)
    compileOnly(libs.squaremap)
    compileOnly(libs.pl3xmap)
    implementation("org.bstats:bstats-bukkit:3.2.1")
    implementation(project(":chunkyborder-common"))
}

tasks {
    shadowJar {
        minimize()
        relocate("org.bstats", "${project.group}.${rootProject.name}.lib.bstats")
        manifest {
            attributes("paperweight-mappings-namespace" to "mojang")
        }
        archiveFileName.set("${project.property("artifactName")}-Bukkit-${project.version}.jar")
    }
}

sourceSets.main {
    blossom {
        resources {
            trimNewlines = false

            property("version", project.version.toString())
            property("group", project.group.toString())
            property("author", providers.gradleProperty("author"))
            property("description", providers.gradleProperty("description"))
        }
    }
}
