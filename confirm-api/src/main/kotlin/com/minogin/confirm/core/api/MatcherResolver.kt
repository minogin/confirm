package com.minogin.confirm.core.api

interface MatcherResolver {
    fun resolve(value: Any?): Matcher
}