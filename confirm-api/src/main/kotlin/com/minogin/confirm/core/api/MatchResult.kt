package com.minogin.confirm.core.api

sealed interface MatchResult

data object Match : MatchResult

interface Mismatch : MatchResult {
    val actual: Any?
    val expected: Matcher
}