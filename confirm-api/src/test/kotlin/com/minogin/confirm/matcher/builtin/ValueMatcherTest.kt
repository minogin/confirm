package com.minogin.confirm.matcher.builtin

import com.minogin.confirm.core.api.Match
import com.minogin.confirm.core.matcher.MatcherRegistry
import com.minogin.confirm.core.matcher.builtin.NullMismatch
import com.minogin.confirm.core.matcher.builtin.TypeMismatch
import com.minogin.confirm.core.matcher.builtin.ValueMatcher
import com.minogin.confirm.core.matcher.builtin.ValueMismatch
import org.junit.jupiter.api.Test
import kotlin.test.*

class ValueMatcherTest {
    @Test
    fun `can match equals`() = with(MatcherRegistry) {
        val valueMatcher = ValueMatcher(5)

        assertEquals(Match, valueMatcher.match(5))

        assertEquals(NullMismatch(valueMatcher), valueMatcher.match(null))

        assertEquals(
            TypeMismatch(actual = "abc", expected = valueMatcher, expectedType = Int::class),
            valueMatcher.match("abc")
        )

        assertEquals(
            ValueMismatch(actual = 3, expected = valueMatcher),
            valueMatcher.match(3)
        )
    }
}