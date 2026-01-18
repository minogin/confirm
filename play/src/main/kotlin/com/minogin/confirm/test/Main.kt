package com.minogin.confirm.test

import com.minogin.confirm.api.*
import com.minogin.confirm.matcher.*

fun main() {
    confirmThat { 10 } deepMatches { 20 }
//    confirmThat { 10 as Any } deepMatches { EqualsMatcher(20) }
}