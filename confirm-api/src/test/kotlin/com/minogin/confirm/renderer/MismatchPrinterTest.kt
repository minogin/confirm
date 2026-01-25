//package com.minogin.confirm.renderer
//
//import com.minogin.confirm.matcher.builtin.*
//import com.minogin.confirm.renderer.printer.MismatchPrinter
//import com.minogin.confirm.renderer.printer.PrinterConfig
//import org.junit.jupiter.api.*
//import org.junit.jupiter.api.Assertions.*
//
//class MismatchPrinterTest {
//    private val printer = MismatchPrinter(config = PrinterConfig())
//
//    @Test
//    fun `can print value mismatch`() {
//        val mismatch = ValueMismatch(
//            actual = 10,
//            expected = ValueMatcher(5)
//        )
//
//        assertEquals(
//            "10    // Expected: 5",
//            printer.printValueMismatch(mismatch)
//        )
//    }
//
//    @Test
//    fun `can print multiline value mismatch`() {
//        val mismatch = ValueMismatch(
//            actual = listOf(1, 2, 3),
//            expected = ValueMatcher(listOf(10, 20, 30, 40, 50))
//        )
//
//        assertEquals(
//            """
//                [       // Expected: [
//                  1,                   10,
//                  2,                   20,
//                  3                    30,
//                ]                      ... 2 more
//                                     ]
//            """.trimIndent(),
//            printer.printValueMismatch(mismatch)
//        )
//    }
//
//    @Test
//    fun `can print list size mismatch`() {
//        val mismatch = ListSizeMismatch(
//            actual = listOf(1, 2, 3, 4, 5),
//            expected = ListMatcher(10, 20, 30),
//        )
//
//        assertEquals(
//            """
//[    // Size: 5  [    // Expected size: 3
//  1,               10,
//  2,               20,
//  3,               30
//  ... 2 more     ]
//]
//            """.trimIndent(),
//            printer.printListSizeMismatch(mismatch)
//        )
//    }
//
//    @Test
//    fun `can print nested size mismatch`() {
//        val mismatch = ListValueMismatch(
//            actual = listOf(1, listOf(21, listOf(221, 222, 223, 224), 23, 24), 3),
//            expected = ListMatcher(1, listOf(21, 22, 23), 3),
//            index = 1,
//            mismatch = ListSizeMismatch(
//                actual = listOf(21, listOf(221, 222, 223, 224), 23, 24),
//                expected = ListMatcher(21, 22, 23),
//            )
//        )
//
//        println(printer.print(mismatch))
//    }
//}