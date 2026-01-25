package com.minogin.confirm.renderer

import com.minogin.confirm.core.matcher.builtin.NullMatcher
import com.minogin.confirm.core.matcher.builtin.ValueMatcher
import com.minogin.confirm.core.printer.PrinterConfig
import com.minogin.confirm.core.printer.builtin.ValuePrinter
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class ValuePrinterTest {
    private val printer = ValuePrinter(
        config = PrinterConfig(
            indent = 2,
            listLimit = 3
        )
    )

    @Test
    fun `can print null matcher`() {
        assertEquals("null", printer.print(NullMatcher))
    }

    @Test
    fun `can print value matcher`() {
        assertEquals("42", printer.print(ValueMatcher(42)))
        assertEquals("\"hello\"", printer.print(ValueMatcher("hello")))
    }

    @Test
    fun `can print list`() {
        assertEquals(
            """
[
  1,
  2
]
            """.trimIndent(),
            printer.printList(listOf(1, 2))
        )

        assertEquals(
            """
[
  1,
  2,
  3,
  ... 2 more
]
            """.trimIndent(),
            printer.printList(listOf(1, 2, 3, 4, 5))
        )
    }

    @Test
    fun `can print nested list`() {
        assertEquals(
            """
[
  1,
  [
    21,
    22,
    23,
    ... 1 more
  ],
  [
    31,
    32
  ],
  ... 1 more
]
            """.trimIndent(),
            printer.printList(listOf(1, listOf(21, 22, 23, 24), listOf(31, 32), 42))
        )
    }
}