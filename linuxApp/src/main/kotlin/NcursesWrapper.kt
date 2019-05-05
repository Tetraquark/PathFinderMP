package ru.tetraquark.pathfindermp.linuxapp

import kotlinx.cinterop.*
import libncurses.*

internal fun enableWin() {
    initscr()
}

internal fun disableWin() {
    endwin()
}

internal fun refreshWin() {
    refresh()
}

internal fun clearWin() {
    clear()
}

internal fun printStr(text: String) {
    printw(text)
}

internal fun printStrL(text: String) {
    printStr("$text\n")
}

internal fun drawChar(x: Int, y: Int, charCode: chtype) {
    mvaddch(y, x, charCode)
}

internal fun drawStr(x: Int, y: Int, str: String) {
    mvprintw(y, x, str)
}

internal inline fun withColor(colorIndex: Int, body: () -> Unit) {
    attron(COLOR_PAIR(colorIndex))
    body()
    attroff(COLOR_PAIR(colorIndex))
}