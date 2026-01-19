package com.minogin.confirm.matcher.builtin

import com.minogin.confirm.matcher.*

data class ValueMatcher(
    val value: Any
) : Matcher {
    context(resolver: MatcherResolver)
    override fun match(actual: Any?): MatchResult =
        when {
            actual == value -> Matches
            actual == null -> NullMismatch(expected = this)

            actual::class != value::class -> TypeMismatch(
                actual = actual,
                expected = this,
                expectedType = value::class
            )

            else -> ValueMismatch(
                actual = actual,
                expected = this,
                expectedValue = value
            )
        }
}

