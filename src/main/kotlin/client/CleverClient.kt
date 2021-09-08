package org.kotlin.client

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.kotlin.server.CleverServer
import org.server.StupidServer
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CleverClient(val cleverServer: CleverServer,
    val stupidServer: StupidServer) {

    suspend fun doWork() {
        val results = HashMap<Int, Double>()

        val list = mutableListOf<Job>()

        val timeStart = System.currentTimeMillis()
        for(i in 0..9) {
            list.add(getNumber(cleverServer, results, i))
        }

        list.joinAll()

        val timeEnd = System.currentTimeMillis()
        println(results)
        println("Time is: " + (timeEnd - timeStart))
    }

    private suspend fun getNumber(server: CleverServer, map: HashMap<Int, Double>, i: Int): Job {
        return GlobalScope.launch {
            val result = server.calculateNumber() + getStupidNumber()
            map.put(i, result)
        }
    }

    private suspend fun getStupidNumber(): Double {
        return suspendCoroutine { cont -> stupidServer.calculateNumber( { value ->
                cont.resume(value)
            })
        }
    }

}