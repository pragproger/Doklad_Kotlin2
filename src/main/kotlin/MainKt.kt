package org.kotlin

import kotlinx.coroutines.newFixedThreadPoolContext
import org.kotlin.client.CoroutineClient
import org.kotlin.server.CoroutineServer
import org.server.ThreadedServer

suspend fun main(args: Array<String>) {
    val context = newFixedThreadPoolContext(10, "My context")

    val server = CoroutineServer(context)

    val client = CoroutineClient(server, ThreadedServer(), context)
    client.doWork()
}

