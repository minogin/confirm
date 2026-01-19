package com.minogin.confirm.matcher.builtin

import com.minogin.confirm.matcher.*

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
                actualSize = actual.size,
                expectedSize = list.size
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

                Matches
            }
        }
}

data class ListSizeMismatch(
    override val actual: Any?,
    override val expected: Matcher,
    val actualSize: Int,
    val expectedSize: Int,
) : Mismatch

data class ListValueMismatch(
    override val actual: List<Any?>,
    override val expected: Matcher,
    val index: Int,
    val mismatch: Mismatch
) : Mismatch