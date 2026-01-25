package com.minogin.confirm.printer

import com.minogin.confirm.matcher.*
import com.minogin.confirm.matcher.builtin.*

class MatcherPrinter(
    private val config: PrinterConfig
) {
    fun print(matcher: Matcher): String =
        when (matcher) {
            is NullMatcher -> printNullMatcher(matcher)
            is ValueMatcher -> printValueMatcher(matcher)
            else -> TODO()
        }

    fun printNullMatcher(matcher: NullMatcher): String = buildString {
        append("null")
    }

    fun printValueMatcher(matcher: ValueMatcher): String = buildString {
        when (val value = matcher.value) {
            is String -> append("\"$value\"")
            else -> append(value)
        }
    }
}