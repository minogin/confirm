package com.minogin.confirm.core.printer

data class PrinterConfig(
    val consoleWidth: Int = 80,
    val consoleMargin: Int = 4,
    val indent: Int = 2,
    val listLimit: Int = 3,
    val comment: String = "// ",
)