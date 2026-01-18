package com.minogin.confirm.api

import com.minogin.confirm.matcher.*

fun <T> confirmThat(actual: () -> T) = Confirmation(actual)

class Confirmation<T>(
    private val actual: () -> T
) {
    infix fun deepMatches(expected: MatcherScope<T>.() -> T) {
        val actualValue = actual()
        val matcherScope = MatcherScope<T>()
        val matcher = matcherScope.expected() as Matcher<Any?>
        val result = matcher.match(actualValue as Any?)
        println(result)
    }
}

class MatcherScope<T> {
    fun eq(value: T): T = apply(EqualsMatcher(value))

    private fun <T> apply(matcher: Matcher<T>): T {
        throw IllegalStateException("Implemented by IR")
    }
}