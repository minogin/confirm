package com.minogin.confirm.printer

import com.minogin.confirm.matcher.*
import com.minogin.confirm.matcher.builtin.*
import com.minogin.confirm.util.*

class MismatchPrinter(
    private val config: PrinterConfig
) {
    private val actualprintor = ActualPrinter(config)
    private val matcherprintor = MatcherPrinter(config)

    fun print(mismatch: Mismatch): String =
        when (mismatch) {
            is NullMismatch -> printNullMismatch(mismatch)
            is ValueMismatch -> printValueMismatch(mismatch)
            is ListSizeMismatch -> printListSizeMismatch(mismatch)
            is ListValueMismatch -> printListValueMismatch(mismatch)
            else -> TODO()
        }

    fun printValueMismatch(mismatch: ValueMismatch): String = buildString {
        append(mismatch.actual)
        append(config.comment)
        append(config.expected)
        append(matcherprintor.print(mismatch.expected))
    }

    fun printNullMismatch(mismatch: NullMismatch): String = buildString {
        append(mismatch.actual)
        append(config.comment)
        append(config.expected)
        append(matcherprintor.print(mismatch.expected))
    }

    fun printListSizeMismatch(mismatch: ListSizeMismatch): String = buildString {
        val actual = actualprintor.print(mismatch.actual)
        append(actual.appendFirst(buildString {
            append(config.comment)
            append("Actual list size ${mismatch.actual.size}, expected list size: ${mismatch.expected.list.size}")
        }))
    }

    fun printListValueMismatch(mismatch: ListValueMismatch): String = buildString {
        append("[")
        val list = mismatch.actual
        if (list.isNotEmpty()) {
            append(config.comment)
            append("List<${list[0]!!::class.simpleName}>")
        }
        appendLine()

        if (mismatch.index > 0) appendLine("  ...")

        val index = "${config.indent}${mismatch.index}: "

        append(index)
        append(print(mismatch.mismatch).prependIndentExceptFirst(" ".repeat(index.length)))
        appendLine()

        if (mismatch.index < mismatch.actual.size - 1) appendLine("${config.indent}...")
        append("]")
    }
}