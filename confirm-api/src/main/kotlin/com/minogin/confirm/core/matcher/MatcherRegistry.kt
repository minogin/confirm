package com.minogin.confirm.core.matcher

import com.minogin.confirm.core.api.Matcher
import com.minogin.confirm.core.api.MatcherResolver
import com.minogin.confirm.core.matcher.builtin.ListMatcher
import com.minogin.confirm.core.matcher.builtin.NullMatcher
import com.minogin.confirm.core.matcher.builtin.ObjectMatcher
import com.minogin.confirm.core.matcher.builtin.ValueMatcher
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

    override fun resolve(value: Any?): Matcher {
        manualPlugins.firstNotNullOfOrNull { it.resolveMatcher(value) }?.let { return it }

        loadedPlugins.firstNotNullOfOrNull { it.resolveMatcher(value) }?.let { return it }

        return builtinMatcher(value)
    }

    private fun builtinMatcher(v: Any?): Matcher = when {
        v == null -> NullMatcher
        v is Matcher -> v
        v is List<*> -> ListMatcher(v)
        v::class.java.isPrimitive || v::class.java.isEnum || v is String -> ValueMatcher(v)
        v::class.memberProperties.any { it.visibility == PUBLIC } -> ObjectMatcher(v)

        else -> ValueMatcher(v)
    }
}