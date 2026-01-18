package com.minogin.confirm

import org.gradle.api.provider.*
import org.jetbrains.kotlin.gradle.plugin.*

class ConfirmGradlePlugin : KotlinCompilerPluginSupportPlugin {
    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean = true

    override fun getCompilerPluginId(): String = "com.minogin.confirm"

    override fun getPluginArtifact(): SubpluginArtifact = SubpluginArtifact(
        groupId = "com.minogin.confirm",
        artifactId = "confirm-compiler-plugin",
        version = "0.0.1"
    )

    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> {
        val project = kotlinCompilation.target.project
        return project.provider { emptyList() } // Pass options to the compiler here if needed
    }
}