package com.minogin.confirm.core.printer

import com.minogin.confirm.core.api.*

interface MismatchPrinterResolver {
    fun resolve(mismatch: Mismatch): MismatchPrinter
}