package com.minogin.confirm.matcher

data class EqualsMatcher<T>(
    val expected: T
) : Matcher<T> {
    override fun match(actual: T): MatchResult<T> =
        when {
            expected is Matcher<*> -> (expected as Matcher<T>).match(actual)
            expected == actual -> Matches(actual = actual)
            actual == null -> NullMismatch(actual = actual)
            expected!!::class.java != actual::class.java -> TypeMismatch(
                actual = actual,
                expectedType = expected::class
            )

            else -> ValueMismatch(actual = actual, expected = expected)
        }
}

data class ValueMismatch<T>(
    override val actual: T,
    val expected: T,
) : Mismatch<T>(actual)