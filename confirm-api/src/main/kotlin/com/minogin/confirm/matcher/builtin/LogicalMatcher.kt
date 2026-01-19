package com.minogin.confirm.matcher.builtin

import com.minogin.confirm.matcher.*

enum class LogicalOperator {
    And, Or;
}

data class LogicalMatcher(
    val a: Any?,
    val b: Any?,
    val operator: LogicalOperator
) : Matcher {
    context(resolver: MatcherResolver)
    override fun match(actual: Any?): MatchResult =
        when (operator) {
            LogicalOperator.And -> {
                val resultA = resolver.resolve(a).match(actual)
                if (resultA is Mismatch) return AndMismatch(
                    actual = actual,
                    expected = this,
                    mismatchA = resultA,
                    mismatchB = null
                )

                val resultB = resolver.resolve(b).match(actual)
                if (resultB is Mismatch) return AndMismatch(
                    actual = actual,
                    expected = this,
                    mismatchA = null,
                    mismatchB = resultB
                )

                Matches
            }

            LogicalOperator.Or -> {
                val resultA = resolver.resolve(a).match(actual)
                if (resultA is Matches) return Matches

                val resultB = resolver.resolve(b).match(actual)
                if (resultB is Matches) return Matches

                OrMismatch(
                    actual = actual,
                    expected = this,
                    mismatchA = resultA as Mismatch,
                    mismatchB = resultB as Mismatch
                )
            }
        }
}

data class AndMismatch(
    override val actual: Any?,
    override val expected: Matcher,
    val mismatchA: Mismatch?,
    val mismatchB: Mismatch?
) : Mismatch

data class OrMismatch(
    override val actual: Any?,
    override val expected: Matcher,
    val mismatchA: Mismatch,
    val mismatchB: Mismatch
) : Mismatch