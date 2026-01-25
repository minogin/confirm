package com.minogin.confirm.core.printer

import com.minogin.confirm.core.api.*

interface MismatchPrinterPlugin {
    fun resolvePrinter(mismatch: Mismatch): MismatchPrinter?
}