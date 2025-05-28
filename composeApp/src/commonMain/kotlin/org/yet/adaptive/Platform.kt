package org.yet.adaptive

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform