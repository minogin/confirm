package com.minogin.confirm.core.printer

import com.minogin.confirm.core.api.*
import com.minogin.confirm.core.matcher.builtin.*
import com.minogin.confirm.core.printer.builtin.*
import java.util.*

object MismatchPrinterRegistry : MismatchPrinterResolver {
    private val loadedPlugins: List<MismatchPrinterPlugin> =
        ServiceLoader.load(MismatchPrinterPlugin::class.java).toList()

    private val manualPlugins = mutableListOf<MismatchPrinterPlugin>()

    fun register(plugin: MismatchPrinterPlugin) {
        manualPlugins.add(plugin)
    }

    override fun resolve(mismatch: Mismatch): MismatchPrinter {
        manualPlugins.firstNotNullOfOrNull { it.resolvePrinter(mismatch) }?.let { return it }

        loadedPlugins.firstNotNullOfOrNull { it.resolvePrinter(mismatch) }?.let { return it }

        return builtinPrinter(mismatch)
    }

    private fun builtinPrinter(mismatch: Mismatch): MismatchPrinter = when {
        mismatch is ListValueMismatch -> ListValueMismatchPrinter(mismatch)

        else -> throw IllegalArgumentException("No printer found for mismatch: $mismatch")
    }
}