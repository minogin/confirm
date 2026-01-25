package com.minogin.confirm.core.matcher.builtin

import com.minogin.confirm.core.api.Match
import com.minogin.confirm.core.api.MatchResult
import com.minogin.confirm.core.api.Matcher
import com.minogin.confirm.core.api.MatcherResolver
import com.minogin.confirm.core.api.Mismatch

data class ListMatcher(
    val list: List<*>
) : Matcher {
    constructor(vararg expected: Any?) : this(expected.toList())

    context(resolver: MatcherResolver)
    override fun match(actual: Any?): MatchResult =
        when {
            actual == null -> NullMismatch(expected = this)

            actual !is List<*> -> TypeMismatch(
                actual = actual,
                expected = this,
                expectedType = List::class
            )

            actual.size != list.size -> ListSizeMismatch(
                actual = actual,
                expected = this,
            )

            else -> {
                actual.zip(list).forEachIndexed { i, pair ->
                    val (a, e) = pair
                    val result = resolver.resolve(e).match(a)
                    if (result is Mismatch) return ListValueMismatch(
                        actual = actual,
                        expected = this,
                        index = i,
                        mismatch = result
                    )
                }

                Match
            }
        }
}

data class ListSizeMismatch(
    override val actual: List<*>,
    override val expected: ListMatcher,
) : BuiltinMismatch

data class ListValueMismatch(
    override val actual: List<*>,
    override val expected: ListMatcher,
    val index: Int,
    val mismatch: Mismatch
) : BuiltinMismatch