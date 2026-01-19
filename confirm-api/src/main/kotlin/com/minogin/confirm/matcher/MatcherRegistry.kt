package com.minogin.confirm.matcher

import com.minogin.confirm.matcher.builtin.*
import java.util.*
import kotlin.reflect.KVisibility.*
import kotlin.reflect.full.*

object MatcherRegistry : MatcherResolver {
    private val loadedPlugins: List<MatcherPlugin> =
        ServiceLoader.load(MatcherPlugin::class.java).toList()

    private val manualPlugins = mutableListOf<MatcherPlugin>()

    fun register(plugin: MatcherPlugin) {
        manualPlugins.add(plugin)
    }

    override fun resolve(v: Any?): Matcher {
        manualPlugins.firstNotNullOfOrNull { it.tryMatch(v) }?.let { return it }

        loadedPlugins.firstNotNullOfOrNull { it.tryMatch(v) }?.let { return it }

        return defaultMatcher(v)
    }

    private fun defaultMatcher(v: Any?): Matcher = when {
        v == null -> NullMatcher
        v is Matcher -> v
        v is List<*> -> ListMatcher(v)
        v::class.java.isPrimitive || v::class.java.isEnum || v is String -> ValueMatcher(v)
        v::class.memberProperties.any { it.visibility == PUBLIC } -> ObjectMatcher(v)

        else -> ValueMatcher(v)
    }
}