package com.minogin.confirm.printer

data class PrinterConfig(
    val indent: String = "  ",
    val listLimit: Int = 3,
    val comment: String = "    // ",
    val expected: String = "Expected: ",
)