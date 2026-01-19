package com.minogin.confirm.matcher

interface MatcherResolver {
    fun resolve(value: Any?): Matcher
}