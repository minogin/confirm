//package com.minogin.confirm.renderer
//
//import com.minogin.confirm.matcher.builtin.*
//import com.minogin.confirm.renderer.printer.PrinterConfig
//import org.junit.jupiter.api.*
//
//class PrinterStoryTest {
//    private val config = PrinterConfig()
//    private val printer = MatchResultPrinter(config)
//
//    @Test
//    fun `can print complex mismatch`() {
//        val result = ValueMismatch(
//            actual = listOf(1, 2, 3, 4, 5),
//            expected = ListMatcher(listOf(10, 20, 30, 40, 50))
//        )
//
//        val output = printer.print(result)
//        println(output)
//    }
//}