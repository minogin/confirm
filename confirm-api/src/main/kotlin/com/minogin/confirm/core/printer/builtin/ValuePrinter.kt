package com.minogin.confirm.core.printer.builtin

import com.minogin.confirm.core.api.Matcher
import com.minogin.confirm.core.matcher.builtin.ListMatcher
import com.minogin.confirm.core.matcher.builtin.NullMatcher
import com.minogin.confirm.core.matcher.builtin.ValueMatcher
import com.minogin.confirm.core.printer.PrinterConfig

class ValuePrinter(
    private val config: PrinterConfig
) {
    fun print(value: Any?): String =
        when (value) {
            is Matcher -> printMatcher(value)
            is String -> printString(value)
            is List<*> -> printList(value)
            else -> printValue(value)
        }

    fun printMatcher(matcher: Matcher): String =
        when (matcher) {
            is NullMatcher -> printValue(null)
            is ValueMatcher -> printValue(matcher.value)
            is ListMatcher -> printList(matcher.list)
            else -> TODO()
        }

    fun printList(list: List<*>): String = buildString {
        appendLine("[")
        val printedSize = list.size.coerceAtMost(config.listLimit)
        list.take(printedSize).forEachIndexed { index, value ->
//            append(config.indent)
//            append(print(value).prependExceptFirstLine(config.indent))
            if (index < list.size - 1) append(",")
            appendLine()
        }
        if (list.size > printedSize) {
//            append(config.indent)
            append("... ${list.size - printedSize} more")
            appendLine()
        }
        append("]")
    }

    fun printString(s: String): String = "\"$s\""

    fun printValue(value: Any?): String = "$value"
}