package com.minogin.confirm.matcher

interface Matcher<T> {
    fun match(actual: T): MatchResult<T>
}

class M : Matcher<Int?> {
    override fun match(actual: Int?): MatchResult<Int?> {
        TODO("Not yet implemented")
    }

}


class M2 : Matcher<Int> {
    override fun match(actual: Int): MatchResult<Int> {
        TODO("Not yet implemented")
    }

}