package com.minogin.confirm.core.matcher.builtin

import com.minogin.confirm.core.api.Match
import com.minogin.confirm.core.api.MatchResult
import com.minogin.confirm.core.api.Matcher
import com.minogin.confirm.core.api.MatcherResolver
import com.minogin.confirm.core.api.Mismatch
import kotlin.reflect.*
import kotlin.reflect.full.*

data class ObjectMatcher(
    val kClass: KClass<*>,
    val properties: Map<KProperty1<*, *>, *>,
    val instance: Any?
) : Matcher {
    constructor(v: Any) : this(
        kClass = v::class,
        properties = v::class.memberProperties
            .filter { it.visibility == KVisibility.PUBLIC }
            .associateWith { it.getter.call(v) },
        instance = v
    )

    context(resolver: MatcherResolver)
    override fun match(actual: Any?): MatchResult =
        when {
            actual == instance -> Match

            actual == null -> NullMismatch(expected = this)

            actual::class != kClass -> TypeMismatch(
                actual = actual,
                expected = this,
                expectedType = kClass
            )

            else -> {
                properties.forEach { (property, e) ->
                    val a = property.call(actual)
                    val result = resolver.resolve(e).match(a)
                    if (result is Mismatch) return PropertyValueMismatch(
                        actual = actual,
                        expected = this,
                        property = property,
                        mismatch = result
                    )
                }

                Match
            }
        }
}

data class PropertyValueMismatch(
    override val actual: Any?,
    override val expected: ObjectMatcher,
    val property: KProperty1<*, *>,
    val mismatch: Mismatch
) : Mismatch