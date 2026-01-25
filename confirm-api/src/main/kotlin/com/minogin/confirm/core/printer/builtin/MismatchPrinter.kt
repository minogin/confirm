//package com.minogin.confirm.core.printer.builtin
//
//import com.minogin.confirm.core.api.*
//import com.minogin.confirm.core.matcher.builtin.*
//import com.minogin.confirm.core.printer.PrinterConfig
//import com.minogin.confirm.core.printer.SplitConsole
//import com.minogin.confirm.core.util.*
//
//abstract class MismatchPrinter(
//    protected val console: SplitConsole,
//    protected val config: PrinterConfig
//) {
//    abstract fun print(mismatch: Mismatch)
//}
//
//class MismatchPrinter2(
//    private val config: PrinterConfig,
//    private val console: SplitConsole
//) {
//    private val valuePrinter = ValuePrinter(config)
//
//    fun print(mismatch: Mismatch) {
//        when (mismatch) {
//            is BuiltinMismatch -> printBuiltinMismatch(mismatch)
//            else -> TODO()  // Plugin
//        }
//    }
//
//    fun printBuiltinMismatch(mismatch: BuiltinMismatch) {
//        when (mismatch) {
//            is ValueMismatch -> printValueMismatch(mismatch)
//            is ListSizeMismatch -> printListSizeMismatch(mismatch)
//            is ListValueMismatch -> printListValueMismatch(mismatch)
//            is TypeMismatch -> printTypeMismatch(mismatch)
//        }
//    }
//
//    fun printValueMismatch(mismatch: ValueMismatch) {
//        console.print(
//            valuePrinter.print(mismatch.actual),
//            valuePrinter.print(mismatch.expected)
//        )
////        val expectedBlock = buildString {
////            val prefix = config.comment + "Expected: "
////            append(prefix)
////            append(expected.prependExceptFirstLine(" ".repeat(prefix.length)))
////        }
////        append(actual.appendAligned(expectedBlock))
//    }
//
//    fun printTypeMismatch(mismatch: TypeMismatch): String = buildString {
//        val actual = valuePrinter.print(mismatch.actual)
////        TODO
////        append(actual.append(buildString {
////            append(config.comment)
////            append("Expected type: ${mismatch.expectedType}, actual type: ${mismatch.actual?.let { it::class }}")
////        }))
//    }
//
//    fun printListSizeMismatch(mismatch: ListSizeMismatch): String = buildString {
//        val actual = valuePrinter.print(mismatch.actual)
//        val actualBlock = actual.appendToFirstLine(config.comment + "Size: ${mismatch.actual.size}")
//        val expected = valuePrinter.print(mismatch.expected)
//        val expectedBlock = expected.appendToFirstLine(config.comment + "Expected size: ${mismatch.expected.list.size}")
////        append(actualBlock.appendAligned(expectedBlock.prependIndent(config.indent)))
//    }
//
//    fun printListValueMismatch(mismatch: ListValueMismatch) {
//        console.print("[", "[")
//        console.indent(config.indent)
//
//        if (mismatch.index > 0) {
//            val dots = "... ${config.comment}Index: ${mismatch.index}"
//            console.print(dots, dots)
//        }
//
////        console.print("${config.comment}Index: ${mismatch.index}", "${config.comment}Index: ${mismatch.index}")
//        print(mismatch.mismatch)
//
//        if (mismatch.index < mismatch.actual.size - 1) {
//            console.print("...", "...")
//        }
//
//        console.indent(-config.indent)
//        console.print("]", "]")
//    }
//}