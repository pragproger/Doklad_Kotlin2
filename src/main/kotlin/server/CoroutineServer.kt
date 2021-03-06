package org.kotlin.server

import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class CoroutineServer(val context: ExecutorCoroutineDispatcher) {

    suspend fun calculateNumber(): Double {
        val deferred = GlobalScope.async(context) {
            delay(1000)

            var result = 0.0
            for (i in 0..9) {
                result += (i / 2).toDouble()
            }
            result *= Math.random()
            result
        }

        return deferred.await()
    }

    suspend fun calculateQuickNumber(): Double {
        val deferred = GlobalScope.async(context) {
            var result = Math.random() * 100
            result
        }

        return deferred.await()
    }

}