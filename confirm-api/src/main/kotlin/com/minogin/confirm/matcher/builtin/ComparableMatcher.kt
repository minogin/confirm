package com.minogin.confirm.matcher.builtin

import com.minogin.confirm.matcher.*

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
                        (actual as Comparable<Any>) < value -> Matches
                        else -> ComparisonMismatch(actual = actual, expected = this)
                    }
                }

                // TODO

                else -> Matches
            }
        }
}

data class ComparisonMismatch(
    override val actual: Any?,
    override val expected: ComparableMatcher,
) : Mismatch