package org.kotlin.client

import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.kotlin.server.CoroutineServer
import org.server.ThreadedServer
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CoroutineClient(
    val cleverServer: CoroutineServer,
    val stupidServer: ThreadedServer,
    val context: ExecutorCoroutineDispatcher
) {

    suspend fun doWork() {
        val results = HashMap<Int, Double>()

        val list = mutableListOf<Job>()

        val timeStart = System.currentTimeMillis()
        for (i in 0..9) {
            val quickNumber = cleverServer.calculateQuickNumber()

            list.add(getNumber(cleverServer, results, i, quickNumber))
        }

        list.joinAll()

        val timeEnd = System.currentTimeMillis()
        println(results)
        println("Time is: " + (timeEnd - timeStart))
    }

    private suspend fun getNumber(server: CoroutineServer, map: HashMap<Int, Double>, i: Int, quickNum: Double): Job {
        return GlobalScope.launch(context) {
            val cleverDef = GlobalScope.async(context) {
                server.calculateNumber()
            }

            val stupidDef = GlobalScope.async(context) {
                getStupidNumber()
            }

            map.put(i, cleverDef.await() + stupidDef.await() + quickNum)
        }
    }


    private suspend fun getStupidNumber(): Double {
        return suspendCoroutine { cont ->
            stupidServer.calculateNumber({ value ->
                cont.resume(value)
            })
        }
    }

}