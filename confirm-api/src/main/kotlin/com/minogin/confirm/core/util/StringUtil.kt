package com.minogin.confirm.core.util

fun String.prependExceptFirstLine(indent: String): String =
    lineSequence()
        .mapIndexed { i, s ->
            when {
                i == 0 -> s
                else -> indent + s
            }
        }
        .joinToString("\n")

fun String.appendToFirstLine(s: String): String =
    lineSequence()
        .mapIndexed { i, line ->
            when (i) {
                0 -> line + s
                else -> line
            }
        }
        .joinToString("\n")

fun String.appendAligned(s: String): String {
    val maxLength = lineSequence().maxOfOrNull { it.length } ?: 0

    return lineSequence().zipAll(s.lineSequence())
        .map { (s1, s2) ->
            val paddedS1 = (s1 ?: "").padEnd(maxLength, ' ')
            paddedS1 + (s2 ?: "")
        }
        .joinToString("\n")
}

fun String.slice(size: Int): Sequence<String> = sequence {
    require(size > 0) { "Size must be positive, but was $size" }

    val l = length
    if (l == 0) {
        yield("")
        return@sequence
    }
    (0..<l step size).forEach { start ->
        val end = (start + size).coerceAtMost(l)
        yield(substring(start, end))
    }
}

fun String.wrapTo(width: Int, wrapChar: Char = '↩'): Sequence<String> =
    slice(width - 1)
        .withLast()
        .map { (line, isLast) ->
            when {
                !isLast -> line + wrapChar
                else -> line
            }
        }

fun String.indent(spaces: Int, indentChar: Char = ' '): String = indentChar.toString().repeat(spaces) + this