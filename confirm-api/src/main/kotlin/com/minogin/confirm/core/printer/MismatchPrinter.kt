package com.minogin.confirm.core.printer

interface MismatchPrinter {
    context(resolver: MismatchPrinterResolver)
    fun print(console: SplitConsole, config: PrinterConfig)
}