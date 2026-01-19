package com.minogin.confirm.matcher

interface Matcher {
    context(resolver: MatcherResolver)
    fun match(actual: Any?): MatchResult
}