package com.minogin.confirm.printer

import com.minogin.confirm.matcher.*
import com.minogin.confirm.matcher.builtin.*
import org.junit.jupiter.api.*

class PrinterTest {
    private val printer = PrinterImpl()

    @Test
    fun `can print null mismatch`() {
//        println(
//            printer.print(
//                NullMismatch(
//                    expected = ValueMatcher(5)
//                )
//            )
//        )
//
//        println()
//        println()

        println(
            printer.print(
                ValueMismatch(
                    actual = 3,
                    expected = ValueMatcher(5),
                    expectedValue = 5
                )
            )
        )
    }

    @Test
    fun `can print nested list mismatch`() {
        val mismatch = ListValueMismatch(
            actual = listOf(1, listOf(21, 220, 23), 3),
            expected = ListMatcher(1, listOf(21, 22, 23), 3),
            index = 1,
            mismatch = ListValueMismatch(
                actual = listOf(21, 220, 23),
                expected = ListMatcher(21, 22, 23),
                index = 1,
                mismatch = ValueMismatch(
                    actual = 220,
                    expected = ValueMatcher(22),
                    expectedValue = 22
                )
            )
        )

        println(printer.print(mismatch))
    }

    @Test
    fun `can print nested size mismatch`() {
        val mismatch = ListValueMismatch(
            actual = listOf(1, listOf(21, listOf(221, 222, 223, 224), 23, 24), 3),
            expected = ListMatcher(1, listOf(21, 22, 23), 3),
            index = 1,
            mismatch = ListSizeMismatch(
                actual = listOf(21, listOf(221, 222, 223, 224), 23, 24),
                expected = ListMatcher(21, 22, 23),
            )
        )

        println(printer.print(mismatch))
    }

    @Test
    fun `can print nested object mismatch`() {
        data class B(
            val v: Int,
            val w: String
        )

        data class A(
            val x: Int,
            val y: String,
            val z: B
        )

        val actual = A(
            x = 3,
            y = "abc",
            z = B(
                v = 10,
                w = "foo"
            )
        )
        val expected = A(
            x = 3,
            y = "abc",
            z = B(
                v = 10,
                w = "bar"
            )
        )

        val mismatch = PropertyValueMismatch(
            actual = actual,
            expected = ObjectMatcher(expected),
            property = A::z,
            mismatch = PropertyValueMismatch(
                actual = actual.z,
                expected = ObjectMatcher(expected.z),
                property = B::w,
                mismatch = ValueMismatch(
                    actual = "foo",
                    expected = ValueMatcher("bar"),
                    expectedValue = "bar"
                )
            )
        )

        println(printer.print(mismatch))
    }

    @Test
    fun `can print comparison mismatch`() {
        println(
            printer.print(
                ComparisonMismatch(
                    actual = 10,
                    expected = ComparableMatcher(5, ComparisonOperator.LessThan)
                )
            )
        )
    }
}