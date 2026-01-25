package com.minogin.confirm.core.matcher.builtin

import com.minogin.confirm.core.api.Match
import com.minogin.confirm.core.api.MatchResult
import com.minogin.confirm.core.api.Matcher
import com.minogin.confirm.core.api.MatcherResolver
import com.minogin.confirm.core.api.Mismatch

enum class ComparisonOperator {
    LessThan,
    LessThanOrEqual,
    GreaterThan,
    GreaterThanOrEqual
}

data class ComparableMatcher(
    val value: Comparable<*>,
    val operator: ComparisonOperator
) : Matcher {
    context(resolver: MatcherResolver)
    override fun match(actual: Any?): MatchResult =
        when {
            actual == null -> NullMismatch(expected = this)

            actual::class != value::class -> TypeMismatch(
                actual = actual,
                expected = this,
                expectedType = value::class
            )

            else -> when (operator) {
                ComparisonOperator.LessThan -> {
                    when {
                        (actual as Comparable<Any>) < value -> Match
                        else -> ComparisonMismatch(actual = actual, expected = this)
                    }
                }

                // TODO

                else -> Match
            }
        }
}

data class ComparisonMismatch(
    override val actual: Any?,
    override val expected: ComparableMatcher,
) : Mismatch