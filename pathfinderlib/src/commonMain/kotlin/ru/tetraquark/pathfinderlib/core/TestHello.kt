package ru.tetraquark.pathfinderlib.core

expect object Platform {
    val name: String
}

class TestHello {
    fun multiplatformHello(): String = "Hello from ${Platform.name}"
}