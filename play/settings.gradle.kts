rootProject.name = "play"

pluginManagement {
    includeBuild("../plugin")
}

includeBuild("../confirm-api")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}