package ru.tetraquark.pathfinderlib.ext

import kotlin.system.measureNanoTime

actual fun measureTime(block: () -> Unit): Long =
    measureNanoTime(block)