package com.minogin.confirm.story

import com.minogin.confirm.dsl.*
import org.junit.jupiter.api.*

class StoryTest {
    @Test
    fun test() {
        confirmThat { listOf(1, listOf(21, 22, 23), 3) } deepMatches { listOf(1, listOf(21, lessThan(20), 23), 3) }
    }
}