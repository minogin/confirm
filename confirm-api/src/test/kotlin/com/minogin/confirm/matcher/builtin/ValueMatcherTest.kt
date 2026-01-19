package com.minogin.confirm.matcher.builtin

import com.minogin.confirm.matcher.MatcherRegistry
import com.minogin.confirm.matcher.Matches
import com.minogin.confirm.matcher.NullMismatch
import com.minogin.confirm.matcher.TypeMismatch
import com.minogin.confirm.matcher.ValueMismatch
import org.junit.jupiter.api.Test
import kotlin.test.*

class ValueMatcherTest {
    @Test
    fun `can match equals`() = with(MatcherRegistry) {
        val valueMatcher = ValueMatcher(5)

        assertEquals(Matches, valueMatcher.match(5))

        assertEquals(NullMismatch(valueMatcher), valueMatcher.match(null))

        assertEquals(
            TypeMismatch(actual = "abc", expected = valueMatcher, expectedType = Int::class),
            valueMatcher.match("abc")
        )

        assertEquals(
            ValueMismatch(actual = 3, expected = valueMatcher, expectedValue = 5),
            valueMatcher.match(3)
        )
    }
}