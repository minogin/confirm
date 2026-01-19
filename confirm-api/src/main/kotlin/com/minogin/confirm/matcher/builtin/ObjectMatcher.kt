package com.minogin.confirm.matcher.builtin

import com.minogin.confirm.matcher.*
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
            actual == instance -> Matches

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

                Matches
            }
        }
}

data class PropertyValueMismatch(
    override val actual: Any?,
    override val expected: ObjectMatcher,
    val property: KProperty1<*, *>,
    val mismatch: Mismatch
) : Mismatch