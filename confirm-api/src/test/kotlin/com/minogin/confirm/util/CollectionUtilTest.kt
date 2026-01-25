package com.minogin.confirm.util

import com.minogin.confirm.core.util.withLast
import com.minogin.confirm.core.util.zipAll
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class CollectionUtilTest {
    @Test
    fun `can zip all`() {
        assertEquals(
            emptyList<Any>(),
            emptySequence<Int>().zipAll(emptySequence<String>()).toList()
        )

        assertEquals(
            listOf(
                1 to "a",
                2 to "b",
                3 to null
            ),
            sequenceOf(1, 2, 3).zipAll(sequenceOf("a", "b")).toList()
        )

        assertEquals(
            listOf(
                1 to "a",
                2 to "b",
                null to "c"
            ),
            sequenceOf(1, 2).zipAll(sequenceOf("a", "b", "c")).toList()
        )
    }

    @Test
    fun `can iterate with last`() {
        assertEquals(
            listOf(
                1 to false,
                2 to false,
                3 to true
            ),
            sequenceOf(1, 2, 3).withLast().toList()
        )

        assertEquals(
            listOf(
                1 to true
            ),
            sequenceOf(1).withLast().toList()
        )

        assertEquals(
            emptyList<Pair<Int, Boolean>>(),
            emptySequence<Int>().withLast().toList()
        )
    }
}