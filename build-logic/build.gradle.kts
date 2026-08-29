plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("net.kyori.blossom:net.kyori.blossom.gradle.plugin:${libs.plugins.blossom.get().version}")
}
