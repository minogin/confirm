package com.minogin.confirm.printer

import org.junit.jupiter.api.Test
import kotlin.test.*

class ActualPrinterTest {
    private val printer = ActualPrinter(
        config = PrinterConfig(
            indent = "  ",
            listLimit = 3
        )
    )

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