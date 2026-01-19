package com.minogin.confirm.matcher

import kotlin.reflect.*

sealed interface MatchResult

data object Matches : MatchResult

interface Mismatch : MatchResult {
    val actual: Any?
    val expected: Matcher
}

data class NullMismatch(
    override val expected: Matcher
) : Mismatch {
    override val actual: Any? = null
}

data class TypeMismatch(
    override val actual: Any?,
    override val expected: Matcher,
    val expectedType: KClass<*>
) : Mismatch

data class ValueMismatch(
    override val actual: Any?,
    override val expected: Matcher,
    val expectedValue: Any?
) : Mismatch