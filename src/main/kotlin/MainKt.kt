package org.kotlin

import kotlinx.coroutines.newFixedThreadPoolContext
import org.kotlin.client.CleverClient
import org.kotlin.server.CleverServer
import org.server.StupidServer


suspend fun main(args: Array<String>) {
    val context = newFixedThreadPoolContext(10, "My context")

    val server = CleverServer(context)

    val client = CleverClient(server, StupidServer(), context)
    client.doWork()
}

