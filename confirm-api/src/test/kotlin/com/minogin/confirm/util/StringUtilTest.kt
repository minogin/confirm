package com.minogin.confirm.util

import com.minogin.confirm.core.util.appendAligned
import com.minogin.confirm.core.util.appendToFirstLine
import com.minogin.confirm.core.util.prependExceptFirstLine
import com.minogin.confirm.core.util.slice
import com.minogin.confirm.core.util.wrapTo
import org.junit.jupiter.api.Test
import kotlin.test.*

class StringUtilTest {
    @Test
    fun `can prepend except first line`() {
        val original = """
            First line
            Second line
            Third line
        """.trimIndent()

        val expected = """
            First line
            > Second line
            > Third line
        """.trimIndent()

        assertEquals(expected, original.prependExceptFirstLine("> "))

        assertEquals("", "".prependExceptFirstLine("> "))
    }

    @Test
    fun `can append to first line`() {
        val original = """
            First line
            Second line
            Third line
        """.trimIndent()

        val expected = """
            First line [appended]
            Second line
            Third line
        """.trimIndent()

        assertEquals(expected, original.appendToFirstLine(" [appended]"))
    }

    @Test
    fun `can append aligned`() {
        val s1 = """
            abc
            xxxxxx
        """.trimIndent()

        val s2 = """
            1
            22
            333
            4444
        """.trimIndent()

        val expected = """
            abc   1
            xxxxxx22
                  333
                  4444
        """.trimIndent()

        assertEquals(expected, s1.appendAligned(s2))
    }

    @Test
    fun `can slice`() {
        assertEquals(
            listOf(
                "abc",
                "def",
                "ghi",
                "j"
            ),
            "abcdefghij".slice(3).toList()
        )

        assertEquals(
            listOf(
                "abc",
                "def",
                "ghi",
            ),
            "abcdefghi".slice(3).toList()
        )

        assertEquals(
            listOf(
                "abc"
            ),
            "abc".slice(10).toList()
        )

        assertEquals(listOf(""), "".slice(10).toList())
    }

    @Test
    fun `can wrap to`() {
        assertEquals(
            listOf(
                "abc↩",
                "def↩",
                "ghi↩",
                "j"
            ),
            "abcdefghij".wrapTo(4).toList()
        )

        assertEquals(
            listOf(""),
            "".wrapTo(3).toList()
        )
    }
}