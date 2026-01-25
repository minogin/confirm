package com.minogin.confirm.printer

import com.minogin.confirm.util.*

class ActualPrinter(
    private val config: PrinterConfig
) {
    fun print(actual: Any?): String =
        when (actual) {
            is List<*> -> printList(actual)
            else -> printValue(actual)
        }

    fun printList(actual: List<*>): String = buildString {
        appendLine("[")
        val printedSize = actual.size.coerceAtMost(config.listLimit)
        actual.take(printedSize).forEachIndexed { index, value ->
            append(config.indent)
            append(print(value).prependIndentExceptFirst(config.indent))
            if (index < actual.size - 1) append(",")
            appendLine()
        }
        if (actual.size > printedSize) {
            append(config.indent)
            append("... ${actual.size - printedSize} more")
            appendLine()
        }
        append("]")
    }

    fun printValue(actual: Any?): String = buildString {
        append(
            when (actual) {
                is String -> "\"$actual\""
                else -> "$actual"
            }
        )
    }
}