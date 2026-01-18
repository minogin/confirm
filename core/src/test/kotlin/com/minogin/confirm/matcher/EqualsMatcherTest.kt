package com.minogin.confirm.matcher

import org.junit.jupiter.api.Test
import kotlin.test.*

class EqualsMatcherTest {
    @Test
    fun `can match equals`() {
        assertEquals(Matches(5), EqualsMatcher(5).match(5))
        assertEquals(NullMismatch<Int?>(null), EqualsMatcher<Int?>(5).match(null))
        assertEquals(
            TypeMismatch(actual = "abc", expectedType = Int::class),
            (EqualsMatcher(5) as EqualsMatcher<String>).match("abc")
        )
        assertEquals(ValueMismatch(actual = 3, expected = 5), EqualsMatcher(5).match(3))
    }
}