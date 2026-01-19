package com.minogin.confirm.matcher

import com.minogin.confirm.api.*
import com.minogin.confirm.matcher.builtin.ListMatcher
import com.minogin.confirm.matcher.builtin.ValueMatcher
import org.junit.jupiter.api.*

class DSLTest {
    @Test
    fun dsl() {
        confirmThat { 10 } deepMatches { 20 }
//        confirmThat { 10 } deepMatches { "abc" }    // syntax error
    }

    @Test
    fun test() {
//        confirmThat { listOf(10, listOf(20)) } deepMatches {
//            listOf(
//                10,
//                listOf(
//                    20
//                )
//            )
//        }

        confirmThat { listOf(10, listOf(20)) as Any } deepMatches {
            ListMatcher(
                ValueMatcher(10),
                ListMatcher(
                    ValueMatcher(10)
                )
            )
        }
    }
}