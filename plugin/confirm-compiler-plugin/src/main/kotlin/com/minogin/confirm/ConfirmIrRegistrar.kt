package com.minogin.confirm

import org.jetbrains.kotlin.backend.common.extensions.*
import org.jetbrains.kotlin.compiler.plugin.*
import org.jetbrains.kotlin.config.*

@OptIn(ExperimentalCompilerApi::class)
class ConfirmIrRegistrar : CompilerPluginRegistrar() {
    override val pluginId: String = "com.minogin.confirm"
    override val supportsK2: Boolean = true

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        IrGenerationExtension.registerExtension(ConfirmIrGenerationExtension())
    }
}