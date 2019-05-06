package ru.tetraquark.pathfindermp.linuxapp

import kotlinx.cinterop.*
import libncurses.*

import ru.tetraquark.pathfinderlib.core.TestHello
import ru.tetraquark.pathfinderlib.presentation.main.MainPresenter
import ru.tetraquark.pathfinderlib.coroutines.AppDispatcher

fun main() {
    println(TestHello().multiplatformHello())

    /*
    // Hello world example

    initscr()
    printw("Input width: \n")

    var width = 0
    memScoped {
        val widthVar = alloc<IntVar>()
        scanw("%d", widthVar.ptr)
        width = widthVar.value
        println("1 width = $width")
    }

    println("2 width = $width")
    getch()
    endwin()
    */

    val app = CliApp(MainPresenter(AppDispatcher))
    app.startApp()
    getch()
    app.stopApp()
}

