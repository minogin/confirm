package com.minogin.confirm.core.matcher.builtin

import com.minogin.confirm.core.api.Match
import com.minogin.confirm.core.api.MatchResult
import com.minogin.confirm.core.api.Matcher
import com.minogin.confirm.core.api.MatcherResolver

data object NullMatcher : Matcher {
    context(resolver: MatcherResolver)
    override fun match(actual: Any?): MatchResult =
        when {
            actual == null -> Match

            else -> ValueMismatch(
                actual = actual,
                expected = this,
            )
        }
}