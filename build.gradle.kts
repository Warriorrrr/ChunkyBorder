plugins {
    id("java-library")
    id("maven-publish")
    alias(libs.plugins.shadow)
    id("chunkyborder.mod-conventions") apply false
}

val chunkyCommon = libs.chunky.common
val moddedTarget = libs.versions.modded.get()

val commitsSinceLastTag = commitsSinceLastTag().get()

subprojects {
    plugins.apply("java-library")
    plugins.apply("maven-publish")
    plugins.apply("com.gradleup.shadow")

    group = "${project.property("group")}"
    version = "${project.property("version")}.${commitsSinceLastTag}"

    repositories {
        mavenCentral()
        maven("https://repo.codemc.io/repository/maven-public/")
    }

    dependencies {
        compileOnly("org.apache.logging.log4j:log4j-api:2.14.1")
        compileOnly("com.google.code.gson:gson:2.8.9")
        compileOnly(chunkyCommon)
    }

    plugins.withId("chunkyborder.mod-conventions") {
        extensions.configure<ModConventionsExtension> {
            target = moddedTarget
        }
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(25))
        }
        withSourcesJar()
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
            options.release = 25
        }
        jar {
            archiveClassifier.set("noshade")
        }
        shadowJar {
            archiveClassifier.set("")
            archiveFileName.set("${project.property("artifactName")}-${project.version}.jar")
        }
        build {
            dependsOn(shadowJar)
        }
    }

    publishing {
        repositories {
            if (project.hasProperty("mavenUsername") && project.hasProperty("mavenPassword")) {
                maven {
                    credentials {
                        username = "${project.property("mavenUsername")}"
                        password = "${project.property("mavenPassword")}"
                    }
                    url = uri("https://repo.codemc.io/repository/maven-releases/")
                }
            }
        }
        publications {
            create<MavenPublication>("maven") {
                groupId = "${project.group}"
                artifactId = project.name
                version = "${project.version}"
                from(components["java"])
            }
        }
    }
}

fun commitsSinceLastTag(): Provider<String> {
    return providers.exec { commandLine("git", "describe", "--tags") }.standardOutput.asText.map {
        if (it.indexOf('-') < 0) {
            return@map "0"
        }
        return@map it.split('-')[1]
    }
}
