package com.minogin.confirm.core.api

interface Matcher {
    context(resolver: MatcherResolver)
    fun match(actual: Any?): MatchResult
}