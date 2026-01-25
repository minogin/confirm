package com.minogin.confirm.core.printer

import com.minogin.confirm.core.util.wrapTo
import com.minogin.confirm.core.util.zipAll

class SplitConsole(
    private val sb: StringBuilder,
    width: Int,
    private val margin: Int,
    private val indentChar: Char = ' ',
    private val marginChar: Char = ' ',
) {
    private val blockWidth = (width - margin) / 2

    private var indent: Int = 0
    private var indentString: String = ""

    fun indent(indent: Int) {
        this.indent += indent
        this.indentString = indentChar.toString().repeat(this.indent)
    }

    fun print(left: String, right: String) {
        val textWidth = blockWidth - indent

        val leftLines = left.lineSequence()
            .flatMap { it.wrapTo(textWidth) }
        val rightLines = right.lineSequence()
            .flatMap { it.wrapTo(textWidth) }

        leftLines.zipAll(rightLines).forEach { (ll, rl) ->
            val leftPart = (indentString + (ll ?: ""))
                .padEnd(blockWidth + margin, marginChar)
            val rightPart = indentString + (rl ?: "")
            sb.appendLine(leftPart + rightPart)
        }
    }
}