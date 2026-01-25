package com.minogin.confirm.core.printer.builtin

import com.minogin.confirm.core.matcher.builtin.*
import com.minogin.confirm.core.printer.*

class ListValueMismatchPrinter(
    private val mismatch: ListValueMismatch
) : MismatchPrinter {
    context(resolver: MismatchPrinterResolver)
    override fun print(console: SplitConsole, config: PrinterConfig) {
        console.print("[", "[")
        console.indent(config.indent)

        if (mismatch.index > 0) {
            val dots = "... ${config.comment}Index: ${mismatch.index}"
            console.print(dots, dots)
        }

        print(mismatch.mismatch)

        if (mismatch.index < mismatch.actual.size - 1) {
            console.print("...", "...")
        }

        console.indent(-config.indent)
        console.print("]", "]")
    }
}