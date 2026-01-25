package com.minogin.confirm.printer

import com.minogin.confirm.matcher.*

interface Printer {
    fun print(result: MatchResult): String
}

