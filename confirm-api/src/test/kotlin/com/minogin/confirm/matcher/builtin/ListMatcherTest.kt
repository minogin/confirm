package com.minogin.confirm.matcher.builtin

import com.minogin.confirm.matcher.*
import org.junit.jupiter.api.Test
import kotlin.test.*

class ListMatcherTest {
    @Test
    fun `can match list`() = with(MatcherRegistry) {
        val listMatcher = ListMatcher(1, 2, 3)

        assertEquals(
            NullMismatch(expected = listMatcher),
            listMatcher.match(null)
        )

        assertEquals(
            TypeMismatch(actual = "abc", expected = listMatcher, expectedType = List::class),
            listMatcher.match("abc")
        )

        assertEquals(
            ListSizeMismatch(actual = listOf(1, 2), expected = listMatcher),
            listMatcher.match(listOf(1, 2))
        )

        assertEquals(
            ListValueMismatch(
                actual = listOf(1, 20, 3),
                expected = listMatcher,
                index = 1,
                mismatch = ValueMismatch(actual = 20, expected = ValueMatcher(2), expectedValue = 2)
            ),
            listMatcher.match(listOf(1, 20, 3))
        )

        assertEquals(
            Matches,
            listMatcher.match(listOf(1, 2, 3))
        )
    }
}