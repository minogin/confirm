package com.minogin.confirm.matcher

interface MatcherPlugin {
    fun tryMatch(value: Any?): Matcher?
}