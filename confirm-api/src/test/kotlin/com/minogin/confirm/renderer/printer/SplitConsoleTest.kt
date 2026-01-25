package com.minogin.confirm.renderer.printer

import com.minogin.confirm.core.printer.SplitConsole
import org.junit.jupiter.api.Test
import kotlin.test.*

class SplitConsoleTest {
    @Test
    fun `can print`() {
        val sb = StringBuilder()
        val splitConsole = SplitConsole(sb, width = 17, margin = 3, indentChar = '.', marginChar = '-')
        splitConsole.indent(2)
        splitConsole.print("abc", "ABC")
        splitConsole.print("abcdef", "xyz")
        splitConsole.print("abc\ndef", "xyz")
        splitConsole.print("z", "abc\nxyzxyzxyz")

        assertEquals(
            """
..abc-----..ABC
..abcd↩---..xyz
..ef------..
..abc-----..xyz
..def-----..
..z-------..abc
..--------..xyzx↩
..--------..yzxy↩
..--------..z

        """.trimIndent(),
            sb.toString()
        )
    }
}