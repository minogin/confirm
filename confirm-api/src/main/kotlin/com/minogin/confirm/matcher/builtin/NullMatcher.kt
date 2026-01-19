package com.minogin.confirm.matcher.builtin

import com.minogin.confirm.matcher.*

data object NullMatcher : Matcher {
    context(resolver: MatcherResolver)
    override fun match(actual: Any?): MatchResult =
        when {
            actual == null -> Matches

            else -> ValueMismatch(
                actual = actual,
                expected = this,
                expectedValue = null
            )
        }
}