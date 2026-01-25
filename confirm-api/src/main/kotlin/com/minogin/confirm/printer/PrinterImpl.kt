package com.minogin.confirm.printer

import com.minogin.confirm.matcher.*

class PrinterImpl : Printer {
    private val config = PrinterConfig()

    override fun print(result: MatchResult): String = buildString {
        append(MatchResultPrinter(config).print(result))
        appendLine()
    }


//    fun prettyPrintMismatch(mismatch: Mismatch): String = buildString {
//        when (mismatch) {
//            is NullMismatch -> {
//                append(mismatch.actual)
//                append(comment)
//                append(expected(mismatch))
//            }
//
//            is TypeMismatch -> {
//                appendLine("Expected type: ${mismatch.expectedType}")
//                appendLine("Actual: ${mismatch.actual} of type ${mismatch.actual?.let { it::class }}")
//            }
//
//            is ValueMismatch -> {
//                append(mismatch.actual)
//                appendLine("$indent// Expected: ${mismatch.expectedValue}")
//            }
//
//            is ListSizeMismatch -> {
//                // TODO Pretty print list
//                // TODO Truncate large list with option to show full?
//                appendLine("${mismatch.actual} // Actual list size ${mismatch.actual.size}, expected list size: ${mismatch.expected.list.size}")
//            }
//
//            is ListValueMismatch -> {
//                appendLine("[")
//                if (mismatch.index > 0) appendLine("  ...")
//
//                val index = "$indent${mismatch.index}: "
//
//                append(index)
//                appendLine(this@PrinterImpl.print(mismatch.mismatch).prependIndentExceptFirst(" ".repeat(index.length)))
//
//                if (mismatch.index < mismatch.actual.size - 1) appendLine("$indent...")
//                appendLine("]")
//            }
//
//            is PropertyValueMismatch -> {
//                append("{")
//                appendLine("$indent// Class: ${mismatch.expected.kClass.simpleName}")
//                appendLine("$indent...")
//                val property = "  ${mismatch.property.name}: "
//                append(property)
//                appendLine(
//                    this@PrinterImpl.print(mismatch.mismatch).prependIndentExceptFirst(" ".repeat(property.length))
//                )
//                appendLine("  ...")
//                appendLine("}")
//            }
//
//            is ComparisonMismatch -> {
//                append(mismatch.actual)
//                val operator = when (mismatch.expected.operator) {
//                    ComparisonOperator.LessThan -> "<"
//                    ComparisonOperator.LessThanOrEqual -> "<="
//                    ComparisonOperator.GreaterThan -> ">"
//                    ComparisonOperator.GreaterThanOrEqual -> ">="
//                }
//                appendLine("  // Expected: $operator ${mismatch.expected.value}")
//            }
//        }
//    }
//
//    private fun comment(comment: String): String =
//        "$commentIndent// $comment"
//
//    private fun expected(mismatch: Mismatch): String =
//        buildString {
//            val expected = mismatch.expected
//
//            append("Expected: ")
//            append(
//                when (expected) {
//                    is String -> "\"$expected\""
//                    is ValueMatcher -> when (val v = expected.value) {
//                        is String -> "\"$v\""
//                        else -> "$v"
//                    }
//
//                    else -> "$expected"
//                }
//            )
//        }
//
//    private fun value(value: Any?): String =
//        buildString {
//            append(
//                when (value) {
//                    is String -> "\"$value\""
//                    is ValueMatcher -> value(value.value)
//                    else -> "$expected"
//                }
//            )
//        }
}