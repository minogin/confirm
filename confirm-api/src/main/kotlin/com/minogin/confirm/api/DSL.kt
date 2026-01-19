package com.minogin.confirm.api

import com.minogin.confirm.impl.*

fun <T> confirmThat(actual: () -> T) = ConfirmationImpl(actual)

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