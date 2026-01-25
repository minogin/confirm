package com.minogin.confirm.test

import com.minogin.confirm.api.*

class I(
    val x: Int
)

class A(
    val i: I
)

fun main() {
//    confirmThat { A(I(10)) } deepMatches { A(I(lessThan(5))) }
    confirmThat { listOf(1, listOf(21, 22, 23), 3) } deepMatches { listOf(1, listOf(21, lessThan(10), 23), 3) }
}