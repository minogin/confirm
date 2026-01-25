package com.minogin.confirm.core.util

fun <T, R> Sequence<T>.zipAll(other: Sequence<R>): Sequence<Pair<T?, R?>> = sequence {
    val first = iterator()
    val second = other.iterator()

    while (first.hasNext() || second.hasNext()) {
        val v1 = if (first.hasNext()) first.next() else null
        val v2 = if (second.hasNext()) second.next() else null
        yield(v1 to v2)
    }
}

fun <T> Sequence<T>.withLast(): Sequence<Pair<T, Boolean>> =
    windowed(2, step = 1, partialWindows = true).map { window ->
        when (window.size) {
            1 -> window[0] to true
            2 -> window[0] to false
            else -> throw IllegalStateException("Unexpected window size: ${window.size}")
        }
    }
