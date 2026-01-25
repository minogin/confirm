package com.minogin.confirm.core.matcher.builtin

import com.minogin.confirm.core.api.Match
import com.minogin.confirm.core.api.MatchResult
import com.minogin.confirm.core.api.Matcher
import com.minogin.confirm.core.api.MatcherResolver

data class ValueMatcher(
    val value: Any
) : Matcher {
    context(resolver: MatcherResolver)
    override fun match(actual: Any?): MatchResult =
        when {
            actual == value -> Match
            actual == null -> NullMismatch(expected = this)

            actual::class != value::class -> TypeMismatch(
                actual = actual,
                expected = this,
                expectedType = value::class
            )

            else -> ValueMismatch(
                actual = actual,
                expected = this,
            )
        }
}

