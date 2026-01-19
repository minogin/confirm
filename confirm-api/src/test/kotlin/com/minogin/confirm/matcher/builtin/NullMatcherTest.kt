package com.minogin.confirm.matcher.builtin

import com.minogin.confirm.matcher.MatcherRegistry
import com.minogin.confirm.matcher.Matches
import com.minogin.confirm.matcher.ValueMismatch
import org.junit.jupiter.api.Test
import kotlin.test.*

class NullMatcherTest {
    @Test
    fun `can match null`() = with(MatcherRegistry)  {
        val nullMatcher = NullMatcher

        assertEquals(Matches, nullMatcher.match(null))
        assertEquals(
            ValueMismatch(actual = 3, expected = nullMatcher, expectedValue = null),
            nullMatcher.match(3)
        )
    }
}