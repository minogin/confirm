plugins {
    alias(libs.plugins.kotlin.jvm)
    application
    id("com.minogin.confirm")
}

kotlin {
    jvmToolchain(libs.versions.java.get().toInt())
}

application {
    mainClass.set("com.minogin.confirm.test.MainKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.confirm.api)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter(libs.versions.junit.get())

            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlin.test.junit5)
            }
        }
    }
}


tasks.test {
    useJUnitPlatform()
}