package com.minogin.confirm.core.matcher.builtin

import com.minogin.confirm.core.api.Match
import com.minogin.confirm.core.api.MatchResult
import com.minogin.confirm.core.api.Matcher
import com.minogin.confirm.core.api.MatcherResolver
import com.minogin.confirm.core.api.Mismatch

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

                Match
            }

            LogicalOperator.Or -> {
                val resultA = resolver.resolve(a).match(actual)
                if (resultA is Match) return Match

                val resultB = resolver.resolve(b).match(actual)
                if (resultB is Match) return Match

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