package com.minogin.confirm.impl

import com.minogin.confirm.api.*
import com.minogin.confirm.matcher.*
import com.minogin.confirm.printer.*

class ConfirmationImpl<T>(
    private val actual: () -> T
) : Confirmation<T> {
    override infix fun deepMatches(expected: () -> T) = with(MatcherRegistry) {
        val actualValue = actual()
        val expectedValue = expected()
        val matcher = resolve(expectedValue)
        val result = matcher.match(actualValue)

        println()
        println("######################################################")
        println()
        println(result)
        println()
        println("######################################################")
        println()

        println(PrinterImpl().print(result))
    }
}

class IRImplementationException(function: String) :
    IllegalStateException("The '$function' function must be processed by the compiler plugin")