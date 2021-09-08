package org.kotlin.server

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class CleverServer() {

    suspend fun calculateNumber(): Double {
        val deferred = GlobalScope.async(Dispatchers.Default) {
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

}