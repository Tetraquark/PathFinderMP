package ru.tetraquark.pathfinderlib.coroutines

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

actual val AppDispatcher : CoroutineContext = Dispatchers.Main