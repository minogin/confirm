package com.minogin.confirm.util

import com.minogin.confirm.matcher.*
import com.minogin.confirm.matcher.builtin.*

interface Printer {
    fun prettyPrint(result: MatchResult): String
}

class PrinterImpl : Printer {
    override fun prettyPrint(result: MatchResult): String = when (result) {
        is Matches -> "Match successful."
        is Mismatch -> prettyPrintMismatch(result)
    }

    fun prettyPrintMismatch(mismatch: Mismatch): String = buildString {
        when (mismatch) {
            is NullMismatch -> {
                appendLine("Expected: ${mismatch.expected}")
                appendLine("Actual: null")
            }

            is TypeMismatch -> {
                appendLine("Expected type: ${mismatch.expectedType}")
                appendLine("Actual: ${mismatch.actual} of type ${mismatch.actual?.let { it::class }}")
            }

            is ValueMismatch -> {
                append(mismatch.actual)
                appendLine("  // Expected: ${mismatch.expectedValue}")
            }

            is ListSizeMismatch -> {
                // TODO Pretty print list
                // TODO Truncate large list with option to show full?
                appendLine("${mismatch.actual} // Actual list size ${mismatch.actualSize}, expected list size: ${mismatch.expectedSize}")
            }

            is ListValueMismatch -> {
                appendLine("[")
                if (mismatch.index > 0) appendLine("  ...")

                val index = "  ${mismatch.index}: "

                append(index)
                appendLine(prettyPrint(mismatch.mismatch).prependIndentExceptFirst(" ".repeat(index.length)))

                if (mismatch.index < mismatch.actual.size - 1) appendLine("  ...")
                appendLine("]")
            }

            is PropertyValueMismatch -> {
                append("{")
                appendLine("  // Class: ${mismatch.expected.kClass.simpleName}")
                appendLine("  ...")
                val property = "  ${mismatch.property.name}: "
                append(property)
                appendLine(prettyPrint(mismatch.mismatch).prependIndentExceptFirst(" ".repeat(property.length)))
                appendLine("  ...")
                appendLine("}")
            }
        }
    }

    private fun String.prependIndentExceptFirst(indent: String): String =
        trimEnd()
            .lineSequence()
            .mapIndexed { i, s ->
                when {
                    i == 0 -> s
//
//                    s.isBlank() -> {
//                        when {
//                            s.length < indent.length -> indent
//                            else -> s
//                        }
//                    }

                    else -> indent + s
                }
            }
            .joinToString("\n")
}