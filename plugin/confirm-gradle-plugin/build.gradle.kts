plugins {
    alias(libs.plugins.kotlin.jvm)
    `java-gradle-plugin`
    `maven-publish`
}

group = "com.minogin.confirm"
version = "0.0.1"

kotlin {
    jvmToolchain(libs.versions.java.get().toInt())
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(gradleApi())
    implementation(libs.kotlin.gradle.plugin.api)
}

gradlePlugin {
    plugins {
        create("confirmPlugin") {
            id = "com.minogin.confirm"
            implementationClass = "com.minogin.confirm.ConfirmGradlePlugin"
        }
    }
}

publishing {
    publications {
        afterEvaluate {
            named<MavenPublication>("pluginMaven") { // Gradle's default name for plugin publications
                artifactId = "confirm-gradle-plugin"
            }
        }
    }
}