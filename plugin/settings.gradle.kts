rootProject.name = "plugin"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

include(":confirm-compiler-plugin")
include(":confirm-gradle-plugin")