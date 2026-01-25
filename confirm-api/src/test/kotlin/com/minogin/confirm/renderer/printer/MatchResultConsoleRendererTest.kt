package com.minogin.confirm.renderer.printer

import com.minogin.confirm.core.matcher.builtin.ListMatcher
import com.minogin.confirm.core.matcher.builtin.ListValueMismatch
import com.minogin.confirm.core.matcher.builtin.ValueMatcher
import com.minogin.confirm.core.matcher.builtin.ValueMismatch
import com.minogin.confirm.core.printer.PrinterRenderer
import com.minogin.confirm.core.printer.PrinterConfig
import org.junit.jupiter.api.*

class MatchResultConsoleRendererTest {
    @Test
    fun `can render value mismatch`() {
        val sb = StringBuilder()
        val renderer = PrinterRenderer(config = PrinterConfig(), sb)
        renderer.render(
            ValueMismatch(
                actual = listOf(1, 2, 3, 4, 5),
                expected = ListMatcher(listOf(10, 20, 30, 40, 50))
            )
        )
        println(sb)
    }

    @Test
    fun `can render list mismatch`() {
        val sb = StringBuilder()
        val renderer = PrinterRenderer(config = PrinterConfig(), sb)
        renderer.render(
            ListValueMismatch(
                actual = listOf(1, 2, listOf(31, 32, 33), 4, 5),
                expected = ListMatcher(listOf(1, 2, ListMatcher(listOf(31, 99, 33)), 4, 5)),
                index = 2,
                mismatch = ListValueMismatch(
                    actual = listOf(31, 32, 33),
                    expected = ListMatcher(listOf(31, 99, 33)),
                    index = 1,
                    mismatch = ValueMismatch(
                        actual = 32,
                        expected = ValueMatcher(99)
                    )
                )
            )
        )
        println(sb)
    }
}