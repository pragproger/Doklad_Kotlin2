package org.kotlin

import org.kotlin.client.CleverClient
import org.kotlin.server.CleverServer
import org.server.StupidServer


suspend fun main(args: Array<String>) {
    val server = CleverServer()

    val client = CleverClient(server, StupidServer())
    client.doWork()
}

