package com.minogin.confirm.core.matcher.builtin

import com.minogin.confirm.core.api.*
import kotlin.reflect.*

sealed interface BuiltinMismatch : Mismatch

data class TypeMismatch(
    override val actual: Any?,
    override val expected: Matcher,
    val expectedType: KClass<*>
) : BuiltinMismatch

data class ValueMismatch(
    override val actual: Any?,
    override val expected: Matcher,
) : BuiltinMismatch

fun NullMismatch(
    expected: Matcher
) = ValueMismatch(
    actual = null,
    expected = expected
)