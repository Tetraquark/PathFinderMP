package ru.tetraquark.pathfinderlib.ext

import java.nio.charset.Charset
import kotlin.random.Random

actual fun Random.nextString(): String {
    val randBytes = Random.nextBytes(7)
    return String(randBytes, Charset.forName("UTF-8"))
}