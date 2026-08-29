plugins {
    id("net.kyori.blossom")
}

repositories {
    maven("https://repo.mikeprimm.com")
    maven("https://jitpack.io")
    maven("https://api.modrinth.com/maven") {
        mavenContent { includeGroup("maven.modrinth") }
    }
}

dependencies {
    compileOnly(libs.dynmap)
    compileOnly(libs.bluemap)
    compileOnly(libs.squaremap)
    compileOnly(libs.pl3xmap)
}

sourceSets.main {
    blossom {
        resources {
            trimNewlines = false

            property("version", project.version.toString())
            property("target", libs.versions.chunky)
        }
    }
}
