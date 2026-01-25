package com.minogin.confirm.matcher.builtin

import com.minogin.confirm.core.api.Match
import com.minogin.confirm.core.matcher.MatcherRegistry
import com.minogin.confirm.core.matcher.builtin.ListMatcher
import com.minogin.confirm.core.matcher.builtin.ListSizeMismatch
import com.minogin.confirm.core.matcher.builtin.ListValueMismatch
import com.minogin.confirm.core.matcher.builtin.NullMismatch
import com.minogin.confirm.core.matcher.builtin.TypeMismatch
import com.minogin.confirm.core.matcher.builtin.ValueMatcher
import com.minogin.confirm.core.matcher.builtin.ValueMismatch
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
                mismatch = ValueMismatch(actual = 20, expected = ValueMatcher(2))
            ),
            listMatcher.match(listOf(1, 20, 3))
        )

        assertEquals(
            Match,
            listMatcher.match(listOf(1, 2, 3))
        )
    }
}