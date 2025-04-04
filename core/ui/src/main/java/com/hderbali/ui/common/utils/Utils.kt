package com.hderbali.ui.common.utils

fun formatCount(count: Int): String {
    return when {
        count < 1000 -> count.toString()
        count < 10000 -> String.format("%.1fK", count / 1000.0)
        count < 1000000 -> String.format("%dK", count / 1000)
        else -> String.format("%.1fM", count / 1000000.0)
    }
}

fun formatTimestamp(timestamp: String): String {
    return "Il y a 2h"
}
