plugins {
    alias(libs.plugins.kotlin.jvm)
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