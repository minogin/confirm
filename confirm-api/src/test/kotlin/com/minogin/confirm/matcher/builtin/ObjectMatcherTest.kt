package com.minogin.confirm.matcher.builtin

import com.minogin.confirm.matcher.MatcherRegistry
import com.minogin.confirm.matcher.NullMismatch
import com.minogin.confirm.matcher.TypeMismatch
import com.minogin.confirm.matcher.ValueMismatch
import org.junit.jupiter.api.Test
import kotlin.test.*

class ObjectMatcherTest {
    class B(
        val x: Int,
    )

    class A(
        val x: Int,
        val y: String,
        val b: B
    )

    private val b = B(3)
    private val a = A(x = 10, y = "abc", b = b)
    private val objectMatcher = ObjectMatcher(a)

    @Test
    fun `can match null`() = with(MatcherRegistry) {
        assertEquals(
            NullMismatch(expected = objectMatcher),
            objectMatcher.match(null)
        )
    }

    @Test
    fun `can match type`() = with(MatcherRegistry) {
        assertEquals(
            TypeMismatch(actual = "abc", expected = objectMatcher, expectedType = A::class),
            objectMatcher.match("abc")
        )
    }

    @Test
    fun `can match object`() = with(MatcherRegistry) {
        val actual = A(x = 10, y = "def", b = b)
        assertEquals(
            PropertyValueMismatch(
                actual = actual,
                expected = objectMatcher,
                property = A::y,
                mismatch = ValueMismatch(actual = "def", expected = ValueMatcher("abc"), expectedValue = "abc")
            ),
            objectMatcher.match(actual)
        )
    }

    @Test
    fun `can match nested object`() = with(MatcherRegistry) {
        val actual = A(x = 10, y = "abc", b = B(5))
        assertEquals(
            PropertyValueMismatch(
                actual = actual,
                expected = objectMatcher,
                property = A::b,
                mismatch = PropertyValueMismatch(
                    actual = actual.b,
                    expected = ObjectMatcher(b),
                    property = B::x,
                    mismatch = ValueMismatch(actual = 5, expected = ValueMatcher(3), expectedValue = 3)
                )
            ),
            objectMatcher.match(actual)
        )
    }
}