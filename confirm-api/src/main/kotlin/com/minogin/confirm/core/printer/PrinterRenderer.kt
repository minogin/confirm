package com.minogin.confirm.core.printer

import com.minogin.confirm.core.api.*

class PrinterRenderer(
    private val config: PrinterConfig
) : MatchResultRenderer {
    private val sb = StringBuilder()

    override fun render(result: MatchResult) {
        when (result) {
            is Match -> printMatch()
            is Mismatch -> printMismatch(result)
        }
    }

    val result: String
        get() = sb.toString()

    private fun printMatch() {
        sb.append("Match successful")
    }

    private fun printMismatch(mismatch: Mismatch) {
        sb.appendLine("Assertion failed")
        sb.appendLine()
        val console = SplitConsole(
            sb = sb,
            width = config.consoleWidth,
            margin = config.consoleMargin
        )
        console.print("Actual:", "Expected:")
        console.print("", "")

        with(MismatchPrinterRegistry) {
            resolve(mismatch).print(console, config)
        }
    }
}