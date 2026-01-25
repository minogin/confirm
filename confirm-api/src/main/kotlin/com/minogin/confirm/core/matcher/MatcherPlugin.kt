package com.minogin.confirm.core.matcher

import com.minogin.confirm.core.api.Matcher

interface MatcherPlugin {
    fun resolveMatcher(value: Any?): Matcher?
}