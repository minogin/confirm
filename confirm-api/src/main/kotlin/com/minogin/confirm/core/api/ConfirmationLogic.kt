package com.minogin.confirm.core.api

class ConfirmationLogic(
    private val resolver: MatcherResolver,
    private val renderer: MatchResultRenderer,
) {
    fun confirm(actual: Any?, expected: Any?) {
        val result = with(resolver) {
            val matcher = resolve(expected)
            matcher.match(actual)
        }
        renderer.render(result)
    }
}