package com.minogin.confirm.printer

import com.minogin.confirm.matcher.*

class MatchResultPrinter(
    private val config: PrinterConfig
) {
    fun print(result: MatchResult): String =
        when (result) {
            is Matches -> printMatches(result)
            is Mismatch -> printMismatch(result)
        }

    fun printMatches(matches: Matches): String = buildString {
        append("Match successful.")
    }

    fun printMismatch(mismatch: Mismatch): String = buildString {
        appendLine("Assertion failed")
        appendLine()
        appendLine("Actual:")

        append(MismatchPrinter(config).print(mismatch))
    }
}