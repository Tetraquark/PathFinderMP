import kotlinx.cinterop.*
import libncurses.*

fun printStr(text: String) {
    printw(text)
}

fun printStrL(text: String) {
    printStr("$text\n")
}
