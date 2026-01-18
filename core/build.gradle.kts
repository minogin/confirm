plugins {
    alias(libs.plugins.kotlin.jvm)
//    id("com.minogin.deep.gradle-plugin") version "1.0.0-2"
}

kotlin {
    jvmToolchain(libs.versions.java.get().toInt())

    compilerOptions {
        // Dumps IR after your specific transformer has finished
        freeCompilerArgs.add("-Xdump-directory=${layout.buildDirectory.get()}/ir-dumps")
        freeCompilerArgs.add("-Xdump-ir-after=com.minogin.deep.MyIrTransformer")
    }
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable:2.3.0")
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