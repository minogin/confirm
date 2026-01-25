package com.minogin.confirm.dsl

import com.minogin.confirm.core.api.*
import com.minogin.confirm.core.matcher.*
import com.minogin.confirm.core.printer.*

fun <T> confirmThat(actual: () -> T) = ConfirmThat(actual)

class ConfirmThat<T>(
    private val actual: () -> T
) : Confirmation<T> {
    override infix fun deepMatches(expected: () -> T) {
        ConfirmationLogic(
            resolver = MatcherRegistry,
            renderer = PrinterRenderer(PrinterConfig())
        ).confirm(actual(), expected())
    }
}

interface Confirmation<T> {
    infix fun deepMatches(expected: () -> T)
}

infix fun <T> Any?.either(value: T): T {
    throw IRImplementationException("either")
}

infix fun <T> Any?.both(value: T): T {
    throw IRImplementationException("both")
}

fun <T : Comparable<T>> lessThan(value: T): T {
    throw IRImplementationException("lessThan")
}

class IRImplementationException(function: String) :
    IllegalStateException("The '$function' function must be processed by the compiler plugin")