package com.minogin.confirm.util

fun String.prependIndentExceptFirst(v: String): String =
    lineSequence()
        .mapIndexed { i, s ->
            when {
                i == 0 -> s
                else -> v + s
            }
        }
        .joinToString("\n")

fun String.appendFirst(v: String): String =
    lineSequence()
        .mapIndexed { i, s ->
            when {
                i == 0 -> s + v
                else -> s
            }
        }
        .joinToString("\n")
