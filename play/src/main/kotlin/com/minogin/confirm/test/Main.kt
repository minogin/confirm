package com.minogin.confirm.test

import com.minogin.confirm.api.*
import com.minogin.confirm.matcher.builtin.*
import com.minogin.confirm.matcher.builtin.ComparisonOperator.*

class A(
    val x: Int,
    val y: String
)

fun main() {
//    confirmThat { 10 } deepMatches { 20 }
//    confirmThat { listOf(1, 2, listOf("a", "b")) } deepMatches { listOf(1, 2, listOf("a", "B")) }
//    confirmThat { 10 as Any } deepMatches { EqualsMatcher(20) }

//    val expectedList = listOf(1, 20)
//    confirmThat { listOf(1, 2) } deepMatches { expectedList }

//    confirmThat { 10 } deepMatches { 20 }

//    confirmThat { listOf(1, 2) } deepMatches { listOf(10, 20) }

//    confirmThat { (1..3).toList() } deepMatches { listOf(1, 2, 3) }
//    confirmThat { (1..3).toList() } deepMatches { listOf(1, 2, 3) }

//    confirmThat { (1..3).toList() } deepMatches {
//        ListMatcher(
//            ValueMatcher(1),
//            ValueMatcher(2),
//            ValueMatcher(3)
//        )
//    }

//    confirmThat { A(10, "abc") } deepMatches { A(10, "xyz") }

//    val l = listOf(1, 2, 3)
//    confirmThat { (1..3).toList() } deepMatches { l }

//    confirmThat { 10 as Any } deepMatches { LogicalMatcher(5, 10, LogicalOperator.Or) }
//
//    confirmThat { 10 } deepMatches { 5 OR 10 }

    val a = A(10, "abc")

    confirmThat { a } deepMatches { A(lessThan(5), "abc") }
//    confirmThat { a as Any } deepMatches {
//        ObjectMatcher(
//            A::class,
//            mapOf(
//                A::x to ComparableMatcher(5 as Comparable<Any>, LessThan),
//                A::y to "abc"
//            ),
//            instance = null
//        )
//    }
}