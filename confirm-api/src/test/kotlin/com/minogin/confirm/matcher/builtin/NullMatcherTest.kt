package com.minogin.confirm.matcher.builtin

import com.minogin.confirm.core.api.Match
import com.minogin.confirm.core.matcher.MatcherRegistry
import com.minogin.confirm.core.matcher.builtin.NullMatcher
import com.minogin.confirm.core.matcher.builtin.ValueMismatch
import org.junit.jupiter.api.Test
import kotlin.test.*

class NullMatcherTest {
    @Test
    fun `can match null`() = with(MatcherRegistry) {
        val nullMatcher = NullMatcher

        assertEquals(Match, nullMatcher.match(null))
        assertEquals(
            ValueMismatch(actual = 3, expected = nullMatcher),
            nullMatcher.match(3)
        )
    }
}