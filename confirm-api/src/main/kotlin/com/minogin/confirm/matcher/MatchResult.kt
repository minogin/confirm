package com.minogin.confirm.matcher

import kotlin.reflect.*

interface MatchResult<T> {
    val matches: Boolean
    val actual: T
}

data class Matches<T>(override val actual: T) : MatchResult<T> {
    override val matches: Boolean = true
}

abstract class Mismatch<T>(override val actual: T) : MatchResult<T> {
    final override val matches: Boolean = false
}

data class NullMismatch<T>(override val actual: T) : Mismatch<T>(actual)

data class TypeMismatch<T>(override val actual: T, val expectedType: KClass<*>) : Mismatch<T>(actual)