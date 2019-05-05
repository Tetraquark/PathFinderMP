package ru.tetraquark.pathfinderlib.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.*
import platform.darwin.*

actual val AppDispatcher: CoroutineContext = MainLoopDispatcher

object MainLoopDispatcher: CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        val queue = dispatch_get_main_queue()
        dispatch_async(queue) {
            block.run()
        }
    }
}